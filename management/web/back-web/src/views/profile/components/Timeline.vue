<template>
  <div class="block">
    <el-timeline>
      <el-timeline-item  :timestamp="new Date(timeLines.registTime).toLocaleDateString()"  placement="top">
        <el-card>
          <h4>加入时间</h4>
          <p>您在 {{timeLines.registTime}} 加入进了我们的大家庭</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item  :timestamp="new Date(timeLines.lastLoginTime).toLocaleDateString()"  placement="top">
        <el-card>
          <h4>上次登录时间</h4>
          <p>您上次的登录时间是 {{timeLines.lastLoginTime}} 如有问题,请联系洋哥</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item :timestamp="new Date().toLocaleDateString()"  placement="top">
        <el-card>
          <h4>现在</h4>
          <p>您已经加入我们 {{Number(((new Date().getTime() - new Date(timeLines.registTime).getTime()) / 1000 / 3600 / 24)).toFixed(0)}} 天!期待和你一直共事下去!</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
export default {
  props: {
    timeLines: {
      type: Object,
      // default: () => {
      //   return {
      //     registTime: '',
      //     lastLoginTime: ''
      //   }
      // }
    }
  },
  data() {
    return {
    }
  }
}
</script>
