<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons'); // echarts theme
import resize from './mixins/resize'
import {analyzeCommentAndLikeCount} from "../../../../api/analyze";

const animationDuration = 6000;

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
      chart: null
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
    initChart(xData, commentData, likeData) {
      this.chart = echarts.init(this.$el, 'macarons');

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: xData,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: '评论数',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: commentData,
          animationDuration
        },{
          name: '点赞数',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: likeData,
          animationDuration
        }]
      })
    },
    fetchData() {
      let xData = [];
      let commentData = [];
      let likeData = [];
      analyzeCommentAndLikeCount().then(response => {
        let data = response.data;
        for (let i = 0; i < data.length; i++) {
          xData.push(data[i].categoryName);
          commentData.push(data[i].commentCount);
          likeData.push(data[i].likeCount);
        }
        this.$nextTick(() => {
          this.initChart(xData, commentData, likeData)
        })
      })
    }
  }
}
</script>
