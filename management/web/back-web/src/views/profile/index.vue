<template>
  <div class="app-container">
    <div v-if="user" v-loading="loading" >
      <el-row :gutter="20">

        <el-col :span="6" :xs="24">
          <user-card :user="user" />
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="活动" name="activity">
                <activity />
              </el-tab-pane>
              <el-tab-pane label="时间线" name="timeline">
                <timeline :timeLines="timeLines"/>
              </el-tab-pane>
              <el-tab-pane label="账户" name="account">
                <account :user="user" @loading="getMag"/>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
import UserCard from './components/UserCard'
import Activity from './components/Activity'
import Timeline from './components/Timeline'
import Account from './components/Account'
import {info} from '../../api/admin'

export default {
  name: 'Profile',
  components: { UserCard, Activity, Timeline, Account },
  data() {
    return {
      user: {},
      activeTab: 'activity',
      timeLines: {
      },
      loading: false
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      info(this.$store.getters.name).then(response => {
        this.user = {
          name: response.data.name,
          nickName: response.data.nickName,
          email: response.data.email,
          adminIcon: response.data.adminIcon
        };
        this.timeLines = {
          registTime: response.data.registTime,
          lastLoginTime: response.data.lastLoginTime
        }
      })
    },
    getMag(data) {
      this.loading = data;
    }
  }
}
</script>
