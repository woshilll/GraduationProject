<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :z-index="10" class-name="sub-navbar" >
        <el-popover
          placement="top-start"
          title="评论数"
          width="200"
          trigger="hover"
          :content="'共有'+postForm.commentCount+'个评论。'">
          <el-button slot="reference" type="info">评论数</el-button>
        </el-popover>
        <el-popover
          style="margin-left: 10px"
          placement="top-start"
          title="点赞数"
          width="200"
          trigger="hover"
          :content="'共有'+postForm.likeCount+'个点赞。'">
          <el-button slot="reference"  type="info">点赞数</el-button>
        </el-popover>
        <IsDelete v-model="postForm.isDelete" style="margin-left: 10px" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
          发布
        </el-button>
        <el-button v-loading="loading" type="warning" @click="draftForm">
          保存
        </el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-row>
<!--          <Warning />-->

          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
                新闻标题
              </MDinput>
            </el-form-item>

            <div class="postInfo-container">
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="60px" label="作者:" class="postInfo-container-item">
                    <el-input v-model="postForm.authorName" :disabled="true"/>
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="120px" label="推送时间:" class="postInfo-container-item">
                    <el-input v-model="postForm.postTime" :disabled="true"/>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label-width="90px" label="新闻类型:" class="postInfo-container-item">
                    <el-select v-model="postForm.categoryId" :clearable="true" placeholder="请选择">
                      <el-option
                        v-for="item in categoryList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>

        <el-form-item style="margin-bottom: 40px;" label-width="60px" label="摘要:">
          <el-input v-model="postForm.content" :rows="1" type="textarea" class="article-textarea" autosize placeholder="请输入摘要" />
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item>

        <el-form-item prop="content" style="margin-bottom: 30px;">
          <Tinymce ref="editor" v-model="postForm.contentHtml" :height="400" />
        </el-form-item>

        <el-form-item prop="image_uri" style="margin-bottom: 30px;">
          <Upload v-model="postForm.image" />
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import Upload from '@/components/Upload/SingleImage3'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky' // 粘性header组件
import { getNewsById , updateNews} from '@/api/news'
import {getAll} from "../../../api/newsCategory";
import Warning from './Warning'
import IsDelete from "./Dropdown/IsDelete";

const defaultForm = {
  status: '',
  title: '', // 文章题目
  contentHtml: '', // 文章内容
  content: '', // 文章摘要
  image: '', // 文章图片

};

export default {
  name: 'NewsDetail',
  components: {IsDelete, Tinymce, MDinput, Upload, Sticky, Warning },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        });
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    };

    return {
      postForm: Object.assign({}, defaultForm),
      loading: false,
      rules: {
        title: [{ validator: validateRequire }],
        content: [{ validator: validateRequire }],
      },
      tempRoute: {},
      categoryList: []
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content.length
    },
    displayTime: {
      // set and get is useful when the data
      // returned by the back end api is different from the front end
      // back end return => "2013-06-25 06:59:25"
      // front end need timestamp => 1372114765000
      get() {
        return (+new Date(this.postForm.display_time))
      },
      set(val) {
        this.postForm.display_time = new Date(val)
      }
    }
  },
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id;
      this.fetchData(id)
    }

    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221
    //this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    fetchData(id) {
      getNewsById(id).then(response => {
        this.postForm = response.data;

        // just for test
        this.postForm.title += ``;
        this.postForm.content += ``;

        // set tagsview title
        //this.setTagsViewTitle();

        // set page title
        this.setPageTitle()
      }).catch(err => {
        console.log(err)
      });
      getAll().then(response => {
        this.categoryList = response.data;
      })
    },
    // setTagsViewTitle() {
    //   const title = '编辑 新闻';
    //   const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` })
    //   this.$store.dispatch('tagsView/updateVisitedView', route)
    // },
    setPageTitle() {
      const title = '编辑 新闻';
      document.title = `${title} - ${this.postForm.id}`
    },
    submitForm() {
      console.log(this.postForm);
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true;
          //设置更新的管理员名
          this.postForm.audit = this.$store.getters.name;
          this.postForm.status = 1;
          updateNews(this.postForm).then(response => {
            this.$notify({
              title: '成功',
              message: '发布文章成功',
              type: 'success',
              duration: 2000
            });
          }).catch(() =>{
            this.$notify({
              title: '失败',
              message: '发布文章失败',
              type: 'error',
              duration: 2000
            });
          });
          this.loading = false
        } else {
          console.log('错误的提交!!');
          return false
        }
      })
    },
    draftForm() {
      if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        });
        return
      }
      this.$message({
        message: '保存成功',
        type: 'success',
        showClose: true,
        duration: 1000
      });
      this.postForm.status = 'draft'
    },

  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;

  .createPost-main-container {
    padding: 40px 45px 20px 50px;

    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;

      .postInfo-container-item {
        float: left;
      }
    }
  }

  .word-counter {
    width: 40px;
    position: absolute;
    right: 10px;
    top: 0px;
  }
}

.article-textarea /deep/ {
  textarea {
    padding-right: 40px;
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
}

</style>
