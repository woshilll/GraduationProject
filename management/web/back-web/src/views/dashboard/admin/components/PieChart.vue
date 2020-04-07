<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/macarons') ;// echarts theme
  import resize from './mixins/resize'
  import {analyzeCategoryNewsCount} from "../../../../api/analyze";

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
              name: '新闻类别下的文章数',
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
        analyzeCategoryNewsCount().then(response => {
          let data = response.data;
          let legendDataTemp = [];
          let seriesDataTemp = [];
          for (let i = 0; i < data.length; i++) {
            legendDataTemp.push(data[i].categoryName);
            seriesDataTemp.push({value: data[i].newsCount, name: data[i].categoryName});
          }
          this.$nextTick(() => {
            this.initChart(legendDataTemp, seriesDataTemp);

          })
        });

      }
    }
  }
</script>
