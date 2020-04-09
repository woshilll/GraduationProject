<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :z-index="10" class-name="sub-navbar">
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
          发布
        </el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
                新闻标题
              </MDinput>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label-width="90px" style="margin-bottom: 40px;" label="新闻类型:" class="postInfo-container-item" prop="categoryId">
          <el-select v-model="postForm.categoryId" :clearable="true" placeholder="请选择">
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 40px;" label-width="60px" label="摘要:" prop="content">
          <el-input v-model="postForm.content" :rows="1" type="textarea" class="article-textarea" autosize
                    placeholder="请输入摘要"/>
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item>

        <el-form-item prop="contentHtml" style="margin-bottom: 30px;">
          <Tinymce ref="editor" v-model="postForm.contentHtml" :height="400"/>
        </el-form-item>

        <el-form-item prop="image" style="margin-bottom: 30px;">
          <Upload v-model="postForm.image"/>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
  import Tinymce from '@/components/Tinymce'
  import Upload from '@/components/Upload/UploadImage'
  import MDinput from '@/components/MDinput'
  import Sticky from '@/components/Sticky' // 粘性header组件
  import {getAll} from "../../api/newsCategory";
  import {validURL} from "../../utils/validate"
  import {addNews} from "../../api/news";

  const defaultForm = {
    status: '',
    title: '', // 文章题目
    contentHtml: '', // 文章内容
    content: '', // 文章摘要
    image: '', // 文章图片
    categoryId: ''

  };

  export default {
    name: 'CreateNews',
    components: { Tinymce, MDinput, Upload, Sticky},
    data() {
      const validateRequire = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(rule.field + '为必传项'))
        } else {
          callback()
        }
      };
      const validateUrl = (rule, value, callback) => {
        if (validURL(value)) {
          callback()
        }
        callback(new Error(rule.field + '不正确'))
      };

      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          title: [{validator: validateRequire}],
          content: [{validator: validateRequire}],
          contentHtml: [{validator: validateRequire}],
          categoryId: [{validator: validateRequire}],
          image: [{validator: validateUrl}],
        },
        tempRoute: {},
        categoryList: [],
      }
    },
    computed: {
      contentShortLength() {
        return this.postForm.content.length
      },

    },
    created() {
      this.tempRoute = Object.assign({}, this.$route)
      this.fetchData();
    },
    methods: {
      fetchData() {
        getAll().then(response => {
          this.categoryList = response.data;
        })
      },
      setTagsViewTitle() {
        const title = '新增新闻';
        const route = Object.assign({}, this.tempRoute, {title: `${title}`});
        this.$store.dispatch('tagsView/updateVisitedView', route)
      },
      setPageTitle() {
        const title = '新增 新闻';
        document.title = `${title}`
      },
      submitForm() {
        this.$refs.postForm.validate(valid => {
          if (valid) {
            this.loading = true;
            //设置更新的管理员名
            this.postForm.audit = this.$store.getters.name;
            addNews(this.postForm).then(response => {
              this.$notify({
                title: '成功',
                message: '发布文章成功',
                type: 'success',
                duration: 0
              });
              this.postForm = Object.assign({}, defaultForm)
            }).catch(() => {
              this.$notify({
                title: '失败',
                message: '遇到一些网络问题,等会再试好吗?',
                type: 'error',
                duration: 0
              });
            });
            this.loading = false
          } else {
            return false
          }
        });
        console.log(this.postForm)
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

