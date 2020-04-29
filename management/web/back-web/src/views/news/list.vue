<template>
  <div class="app-container">
    <!--查询开始---------------------------------------------------- -->
    <el-row :gutter="20">
      <el-col :span="5">
        <div class="grid-content bg-purple">
          <el-input
            placeholder="请输入标题"
            v-model="listQuery.title"
            clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="grid-content bg-purple">
          <el-select v-model=listQuery.status clearable placeholder="请选择状态">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value=item.value
            >
            </el-option>
          </el-select>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple">
          <el-button type="primary" icon="el-icon-search" @click="fetchData">搜索</el-button>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="10">
        <div class="grid-content bg-purple">
          <el-date-picker
            v-model="listQuery.times"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            @change="timeChange(listQuery.times)"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
      </el-col>
    </el-row>
    <!--查询结束---------------------------------------------------- -->

    <el-divider content-position="center">我是一条华丽的分割线</el-divider>

    <!--table开始---------------------------------------------------- -->
    <el-table
      v-loading="listLoading"
      :data="userList"
      style="width: 1302px"
      :max-height="650"
      element-loading-text="Loading"
      :border="true"
      :fit="true"
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="90" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="上传时间" width="200" :resizable="false"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span>{{ scope.row.postTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" width="170" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.authorName }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110" align="center" :resizable="false">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">
            {{ scope.row.status === 0 ? '待审核' : scope.row.status === 1 ? '审核通过' : '审核失败'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="标题" width="200" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'./edit/'+scope.row.id" class="link-type">
          <span>{{ scope.row.title }}</span>
          </router-link>
          <el-tag size="mini">{{scope.row.categoryName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="摘要" width="200" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评论数" width="100" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.commentCount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="点赞数" width="100" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.likeCount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核人" width="150" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.audit }}
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100"
        :resizable="false">
        <template slot-scope="scope">
          <router-link :to="'./edit/'+scope.row.id" class="link-type">
            <el-button type="primary" icon="el-icon-edit" circle size="small"></el-button>
          </router-link>
          <el-popconfirm
            @onConfirm="deleteById(scope.row.id)"
            title="你确定确定删除吗？后果自负!"
            confirm-button-type="danger"
          >
            <el-button slot="reference" type="danger" icon="el-icon-delete" circle size="small"></el-button>
          </el-popconfirm>
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
  import Pagination from '../../components/Pagination'
  import {selectAll, deleteById} from '@/api/news'
  import PanThumb from '../../components/PanThumb'

  export default {
    name: "NewsList",
    components: {Pagination, PanThumb},
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: 'info',
          1: 'success',
          2: 'danger'
        };
        return statusMap[status]
      },
    },
    data() {
      return {
        //加载动画
        listLoading: true,
        //表格数据
        userList: [],
        //分页及模糊查询条件
        total: 0,
        listQuery: {
          page: 1,
          limit: 10,
          title: '',
          status: '',
          start: "",
          end: "",
          times: ''
        },
        //select 选择框
        options: [{
          value: '0',
          label: '待审核'
        }, {
          value: '1',
          label: '审核通过'
        }, {
          value: '2',
          label: '审核失败'
        }],
        //时间选择器
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
      };
    },
    created() {
      this.fetchData()
    }
    ,
    methods: {
      /**
       * 初始化table
       */
      fetchData() {
        this.listLoading = true;
        selectAll(this.listQuery).then(response => {
          this.userList = response.data.data;
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
      /**
       *时间选择
       */
      timeChange(data) {
        if (data != null) {
          this.listQuery.start = data[0];
          this.listQuery.end = data[1]
        } else {
          this.listQuery.start = '';
          this.listQuery.end = ''
        }
      },
      /**
       * 删除新闻
       * @param id
       */
      deleteById(id) {
        deleteById(id).then(response => {
          this.$notify({
            title: '成功',
            message: '删除新闻成功!',
            type: 'success',
            duration: 0
          });
          this.fetchData();
        })
      }
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
    &:last-child {
     margin-bottom: 0;
   }
  }
  .el-col {
    border-radius: 4px;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
  }
  .link-type {
    color: #337ab7;
    cursor: pointer;
  }
</style>
