<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;">
    <el-table-column label="登录时间" width="170">
      <template slot-scope="scope">
        {{ scope.row.loginTime}}
      </template>
    </el-table-column>
    <el-table-column label="登录IP" width="130" align="center">
      <template slot-scope="scope">
        ¥{{ scope.row.ip}}
      </template>
    </el-table-column>
    <el-table-column label="登录地" width="120" align="center">
      <template slot-scope="scope">
          {{ scope.row.city}}
      </template>
    </el-table-column>
    <el-table-column label="登录名" width="130" align="center">
      <template slot-scope="scope">
        {{ scope.row.name}}
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getAdminLogs } from "../../../../api/admin";

export default {
  data() {
    return {
      list: null
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      getAdminLogs().then(response => {
        this.list = response.data;
      })
    }
  }
}
</script>
