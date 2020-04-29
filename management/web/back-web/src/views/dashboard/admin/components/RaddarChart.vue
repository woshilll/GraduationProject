<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/macarons') ;// echarts theme
  import resize from './mixins/resize'
  import {analyzeNewsStatus} from "../../../../api/analyze";

  export default {
    mixins: [resize],
    props: {
      className: {
        type: String,
        default: 'chart'
      },
      width: {
        type: String,
        default: '100%'
      },
      height: {
        type: String,
        default: '300px'
      }
    },
    data() {
      return {
        chart: null,
      }
    },
    created() {
      this.fetchData();

    },
    mounted() {

    },
    beforeDestroy() {
      if (!this.chart) {
        return
      }
      this.chart.dispose();
      this.chart = null
    },
    methods: {
      initChart(legendDataTemp, seriesDataTemp) {
        this.chart = echarts.init(this.$el, 'macarons');

        this.chart.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            left: 'center',
            bottom: '10',
            data: legendDataTemp
          },
          series: [
            {
              name: '审核状态下新闻数',
              type: 'pie',
              roseType: 'radius',
              radius: [15, 95],
              center: ['50%', '38%'],
              data: seriesDataTemp,
              animationEasing: 'cubicInOut',
              animationDuration: 2600
            }
          ]
        })
      },
      fetchData() {
        analyzeNewsStatus().then(response => {
          let data = response.data;
          let legendDataTemp = ['未审核', '审核通过', '审核未通过'];
          let seriesDataTemp = [];
          for (let i = 0; i < data.length; i++) {
            if (data[i].status === 0) {
              seriesDataTemp.push({value: data[i].count, name: '未审核'});
            } else if (data[i].status === 1) {
              seriesDataTemp.push({value: data[i].count, name: '审核通过'});
            } else {
              seriesDataTemp.push({value: data[i].count, name: '审核未通过'});
            }

          }
          this.$nextTick(() => {
            this.initChart(legendDataTemp, seriesDataTemp);

          })
        });

      }
    }
  }
</script>
