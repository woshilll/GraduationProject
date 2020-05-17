<template>
  <div class="app-container">
    <!--查询开始---------------------------------------------------- -->
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-input
            placeholder="请输入大概评论内容"
            v-model="listQuery.details"
            clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-button type="primary" icon="el-icon-search" @click="fetchData">搜索</el-button>
        </div>
      </el-col>
    </el-row>
    <el-divider content-position="center">华丽的分割线</el-divider>

    <!--table开始---------------------------------------------------- -->
    <el-table
      v-loading="listLoading"
      :data="commentList"
      :max-height="500"
      element-loading-text="Loading"
      :border="true"
      :fit="true"
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" :inline="false" class="demo-table-expand">
            <el-form-item label="新闻ID">
              <router-link :to="'/news/edit/' + props.row.newsId">
              <span>{{ props.row.newsId }}</span>
              </router-link>
            </el-form-item>
            <el-form-item label="新闻标题">
              <router-link :to="'/news/edit/' + props.row.newsId">
              <span>{{ props.row.newsTitle }}</span>
              </router-link>
            </el-form-item>
            <el-form-item label="新闻状态">
              <el-tag :type="props.row.newsStatus | statusFilter">
                {{ props.row.newsStatus === 0 ? '未删除' : '已删除'}}
              </el-tag>
            </el-form-item>
            <el-form-item label="用户ID">
              <span>{{ props.row.userId }}</span>
            </el-form-item>
            <el-form-item label="用户名">
              <span>{{ props.row.username }}</span>
            </el-form-item>
            <el-form-item label="用户状态">
              <el-select v-model=props.row.userStatus placeholder="请选择" @change="userStatusChange({id: props.row.userId, banned: props.row.userStatus})">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value=item.value
                  :disabled="props.row.userStatus === item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="center" label="序号" width="90" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column label="评论内容" align="center" width="250" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.details }}
        </template>
      </el-table-column>
      <el-table-column label="评论时间" align="center" width="170" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.commentDate }}
        </template>
      </el-table-column>

      <el-table-column label="评论状态" width="180" align="center" :resizable="false">
        <template slot-scope="scope">
          <el-radio-group v-model="scope.row.status" @change="commentStatusChange({id: scope.row.id, status: scope.row.status})">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1" style="color: red">违规</el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="fetchData"/>
    <!--table结束---------------------------------------------------- -->



  </div>
</template>

<script>
  import Pagination from '@/components/Pagination'
  import PanThumb from '@/components/PanThumb'
  import {getAll, updateComment} from '../../api/newsComment'
  import {update} from '../../api/userManage'

  export default {
    name: "CommentsList",
    components: {Pagination, PanThumb},
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
        commentList: [],
        //分页及模糊查询条件
        total: 0,
        listQuery: {
          page: 1,
          limit: 10,
          details: '',
        },
        //select 选择框
        options: [{
          value: 0,
          label: '正常'
        }, {
          value: 1,
          label: '禁言',
        }, {
          value: 2,
          label: '封禁'
        }],

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
        getAll(this.listQuery).then(response => {
          this.commentList = response.data.data;
          this.total = response.data.recordsTotal;
          this.listLoading = false;
        }).catch(() => {
          this.listLoading = false;
          this.$message({
            message: '获取信息失败',
            type: 'error'
          })
        })
      },
      commentStatusChange(data) {
        updateComment(data).then(response => {
          this.$message({
            message: '评论状态更新成功',
            type: 'success',
            duration: 5*1000
          })
        });
      },
      userStatusChange(data) {
        update(data).then(response => {
          this.$message({
            message: '用户状态更新成功',
            type: 'success',
            duration: 5*1000
          });
          this.fetchData();
        })
      },
      clickChange(data) {
        console.log(data)
      }
    }
  }
</script>
<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
</style>

<style scoped>
  .avatar {
    width: 200px;
    height: 200px;
    border-radius: 50%;
  }
</style>
