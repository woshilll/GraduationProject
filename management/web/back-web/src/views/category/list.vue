<template>
  <div class="app-container">

    <el-divider content-position="center">共{{categoryList.length}}条数据, 该页面无法修改</el-divider>

    <!--table开始---------------------------------------------------- -->
    <el-table
      v-loading="listLoading"
      :data="categoryList"
      :max-height="600"
      element-loading-text="Loading"
      :border="true"
      :fit="true"
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="90" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column label="分类名" align="center" width="170" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" width="170" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.created }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" width="180" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.updated }}</span>
        </template>
      </el-table-column>


      <el-table-column label="状态" width="90" align="center" :resizable="false">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">
            {{ scope.row.status === 0 ? '正常' : '禁用'}}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
    <!--table结束---------------------------------------------------- -->



  </div>
</template>

<script>
  import {getAll} from '../../api/newsCategory'

  export default {
    name: "CategoryList",
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: 'success',
          1: 'danger'
        };
        return statusMap[status]
      },
    },
    data() {
      return {
        //加载动画
        listLoading: true,
        //表格数据
        categoryList: [],

      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      /**
       * 初始化table
       */
      fetchData() {
        this.listLoading = true;
        getAll().then(response => {
          this.categoryList = response.data;
          this.listLoading = false;
        }).catch(() => {
          this.listLoading = false;
          this.$message({
            message: '获取信息失败',
            type: 'error'
          })
        })
      },
    }
  }
</script>

<style scoped>
  .avatar {
    width: 200px;
    height: 200px;
    border-radius: 50%;
  }
</style>
