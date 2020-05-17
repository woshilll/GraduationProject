<template>
  <div class="app-container">
    <!--查询开始---------------------------------------------------- -->
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-input
            placeholder="请输入用户名"
            v-model="listQuery.name"
            clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-input
            placeholder="请输入邮箱"
            v-model="listQuery.email"
            clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-input
            placeholder="请输入昵称"
            v-model="listQuery.nickName"
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
    <!--查询结束---------------------------------------------------- -->

    <el-divider content-position="center">我是一条华丽的分割线</el-divider>

    <!--table开始---------------------------------------------------- -->
    <el-table
      v-loading="listLoading"
      :data="userList"
      style="width: 1282px"
      :max-height="500"
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
      <el-table-column label="用户名" align="center" width="170" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" width="170" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.phone }}
        </template>
      </el-table-column>
      <el-table-column label="邮箱" width="180" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" width="150" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.nickName }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="100" align="center" :resizable="false">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.userIcon" :size="35"/>
        </template>
      </el-table-column>
      <el-table-column label="个人介绍" width="200" align="center" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.talk }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="created_at" label="上次登录时间" width="200" :resizable="false" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span>{{ scope.row.lastLoginTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="90" align="center" :resizable="false">
        <template slot-scope="scope">
          <el-tag :type="scope.row.banned | statusFilter">
            {{ scope.row.banned === 0 ? '正常' : scope.row.banned === 1 ? '禁言' : '封禁'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100"
        :resizable="false">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="showDialog(scope.row)"
            type="text"
            size="small">
            修改
          </el-button>
          <el-popconfirm
            @onConfirm="deleteHandler(scope.row.id, scope.row.name)"
            title="你确定确定删除吗？后果自负!"
            confirm-button-type="danger"
          >
            <el-button
              @click.native.prevent=""
              slot="reference"
              style="color: red"
              type="text"
              size="small">
              删除
            </el-button>
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


    <!--弹出框开始---------------------------------------------------- -->
    <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" :disabled="form.name != null"/>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="form.nickName" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="个人介绍" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="2" v-model="form.talk" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model=form.banned placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value=item.value
              :disabled="form.banned === item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像" :label-width="formLabelWidth">
          <el-avatar :src="form.userIcon" :size="40"/>
          <el-button type="primary" icon="upload" style="position: absolute;bottom: 15px;margin-left: 40px;"
                     @click="toggleShow" v-if="form.userIcon == null">
            上传头像
          </el-button>
          <image-cropper
            v-model="show"
            field="multipartFile"
            :width="300"
            :height="300"
            :url="url"
            :params="params"
            :headers="headers"
            img-format="png"
            @crop-success="cropSuccess"
            @crop-upload-success="cropUploadSuccess"
            @crop-upload-fail="cropUploadFail"
          />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateHandler()">提 交</el-button>
      </div>
    </el-dialog>
    <!--弹出框结束---------------------------------------------------- -->
  </div>
</template>

<script>
  import Pagination from '../../components/Pagination'
  import {selectAll, deleteById, modifyIcon, update} from '../../api/userManage'
  import ImageCropper from 'vue-image-crop-upload'
  import PanThumb from '../../components/PanThumb'
  import {getToken} from '../../utils/auth'

  export default {
    name: "UserList",
    components: {Pagination, ImageCropper, PanThumb},
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: 'success',
          1: 'info',
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
          name: '',
          email: '',
          nickName: ''
        },
        //修改或新增时的对话框
        dialogFormVisible: false,
        formLabelWidth: '120px',
        //用于更新或新增用户
        form: {
          email: '',
          nickName: '',
          talk: '',
          userIcon: '',
          banned: ''
        },
        //头像上传
        show: false,
        url: process.env.VUE_APP_BASE_API + '/upload',
        headers: {
          smail: '*_~'
        },
        params: {
          access_token: getToken()
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
       * 删除一行数据
       * @param id
       * @param name
       * @returns {boolean}
       */
      deleteHandler(id, name) {
        /**
         * 开始执行删除操作
         */
        this.listLoading = true;
        deleteById(id, this.$store.getters.name).then(response => {
          this.$message({
            message: response.message + '但是你永远也找不回来喽🥺',
            type: 'success'
          });
          this.listLoading = false;
          this.fetchData();
        })
        this.listLoading = false;
      },

      /**
       * 打开dialog框
       * @param data
       */
      showDialog(data) {
        Object.assign(this.form, data);
        this.dialogFormVisible = true;
      },

      //==================头像上传====================
      toggleShow() {
        this.show = !this.show
      },
      /**
       *
       * @param image
       * @param field
       */
      cropSuccess(image, field) {

      },
      /**
       * 上传成功
       * @param jsonData 服务器返回数据，已进行 JSON 转码
       * @param field
       */
      cropUploadSuccess(jsonData, field) {
        // 更新头像
        modifyIcon({
          name: this.form.name,
          path: jsonData.data.path
        }).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.form.userIcon = jsonData.data.path;
          this.show = !this.show;
        }).catch(() => {
        })
      },
      /**
       * 上传失败
       * @param status 服务器返回的失败状态码
       * @param field
       */
      cropUploadFail(status, field) {
        this.$message({
          message: status + '上传失败',
          type: 'error'
        });
      },
      /**
       * 更新用户信息
       */

      updateHandler() {
        update(this.form).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.fetchData();
        });
        this.dialogFormVisible = false;
      }
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
