<template>
  <el-form :model="userParam" label-width="100px" :rules="rules" ref="userForm">
    <el-form-item label="用户名">
      <el-input v-model.trim="user.name" :disabled="true"/>
    </el-form-item>
    <el-form-item label="旧密码" prop="oldPassword">
      <el-input
        :key="passwordType"
        ref="password"
        v-model="userParam.oldPassword"
        :type="passwordType"
        placeholder="请输入密码"
        name="password"
        tabindex="2"
        auto-complete="on"
      >
        <svg-icon slot="suffix" @click="showPwd" :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
      </el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input
        :key="passwordType"
        ref="password"
        v-model="userParam.newPassword"
        :type="passwordType"
        placeholder="请输入密码"
        name="password"
        tabindex="2"
        auto-complete="on"
      >
        <svg-icon slot="suffix" @click="showPwd" :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
      </el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input
        :key="passwordType"
        ref="password"
        v-model="userParam.confirmPassword"
        :type="passwordType"
        placeholder="请输入密码"
        name="password"
        tabindex="2"
        auto-complete="on"
      >
        <svg-icon slot="suffix" @click="showPwd" :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click.native="submit('userForm')">更新密码</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import {validatePwd, updatePwd} from "../../../api/admin";

  export default {
    props: {
      user: {
        type: Object,
        default: () => {
          return {
            name: '',
            email: ''
          }
        }
      }
    },
    data() {
      let validatePassword = (rule, value, callback) => {
        validatePwd(this.user.name, value).then(response => {
          callback();
        }).catch(() => {
          callback(new Error('旧密码不正确!'));
        });
      };
      let validateNewPwdNotEqualsOld = (rule, value, callback) => {
        if (value === this.userParam.oldPassword) {
          callback(new Error('新密码不能和旧密码一样!'));
        } else {
          callback();
        }
      };
      let validateConfirmPassword = (rule, value, callback) => {
        if (value !== this.userParam.newPassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        userParam: {
          name: '',
          oldPassword: '',
          newPassword: '',
          confirmPassword: ''
        },
        passwordType: 'password',

        rules: {
          oldPassword: [
            {required: true, message: '请输入旧密码', trigger: 'blur'},
            {min: 6, max: 16, message: '旧密码长度在6-16之间', trigger: 'blur'},
            {validator: validatePassword, trigger: 'blur'},
          ],
          newPassword: [
            {required: true, message: '请输入新密码', trigger: 'blur'},
            {min: 6, max: 16, message: '新密码长度在6-16之间', trigger: 'blur'},
            {validator: validateNewPwdNotEqualsOld, trigger: 'blur'},
          ],
          confirmPassword: [
            {required: true, message: '请确认密码', trigger: 'blur'},
            {min: 6, max: 16, message: '确认密码长度在6-16之间', trigger: 'blur'},
            {validator: validateConfirmPassword, trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      submit(userForm) {
        this.$refs[userForm].validate((valid) => {
          if (valid) {
            updatePwd(this.user.name, this.userParam.newPassword).then(response => {
              this.$message({
                message: '您已成功修改密码,系统将在五秒内退出,请您重新登录!',
                type: 'error',
                duration: 5 * 1000
              });
              this.$emit("loading", true);
              let accountThis = this;
              setTimeout(async function () {
                await accountThis.$store.dispatch('user/logout');
                accountThis.$router.push(`/login?redirect=${accountThis.$route.fullPath}`)
              },4500);
            }).catch(()=>{
              this.$message({
                message: '我们遇到了一点问题,马上就好,等一下吧!',
                type: 'error',
                duration: 5 * 1000
              });
            })
          } else {
            return false;
          }
        });

      },
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
    }
  }
</script>
