<template>
  <div class="app-container">
    <el-form
      ref="form"
      v-loading="formLoading"
      :data="form"
      element-loading-text="加载中..."
      :model="form"
      label-width="120px"
    >
      <el-form-item label="头像">
        <img :src="form.adminIcon" width="60" height="60">
      </el-form-item>
      <el-input type="hidden" v-model="form.id"/>
      <el-form-item label="账号">
        <el-input v-model="form.name" :disabled="true"/>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email"/>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickName"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.node"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="form.registTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="最后登录">
        <el-input v-model="form.lastLoginTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="是否启用">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">启用</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {info, updateInfo} from '@/api/admin'

  export default {
    name: "AdminInfo",
    data() {
      return {
        formLoading: true,
        form: {
          id: '',
          adminIcon: '',
          name: '',
          email: '',
          nickName: '',
          node: '',
          registTime: '',
          lastLoginTime: '',
          status: 0
        }
      }
    },
    created() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        info(this.$store.getters.name).then(response => {
          this.form = response.data;
          this.formLoading = false;
        })
      },
      onSubmit() {
        this.formLoading = true;
        updateInfo(this.form).then(response => {
          this.formLoading = false;
          this.$message({
            message: response.message,
            type: 'success'
          })
        }).catch(() => {
          this.formLoading = false;
        })
      }
    }
  }
</script>

<style scoped>

</style>
