<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :z-index="10" class-name="sub-navbar">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <el-button slot="reference" type="info">点我查看<i class="el-icon-caret-bottom el-icon--right"></i>
            </el-button>
           </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item class="clearfix">
              评论
              <el-badge class="mark" :value="postForm.commentCount"/>
            </el-dropdown-item>
            <el-dropdown-item class="clearfix">
              点赞
              <el-badge class="mark" :value="postForm.likeCount"/>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <IsDelete v-model="postForm.isDelete" style="margin-left: 10px"/>
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
          <el-input v-model="postForm.content" :rows="1" type="textarea" class="article-textarea" autosize
                    placeholder="请输入摘要"/>
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item>

        <el-form-item prop="content" style="margin-bottom: 30px;">
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
  import {getNewsById, updateNews} from '@/api/news'
  import {getAll} from "../../../api/newsCategory";
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
    components: {IsDelete, Tinymce, MDinput, Upload, Sticky},
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
          title: [{validator: validateRequire}],
          content: [{validator: validateRequire}],
        },
        tempRoute: {},
        categoryList: []
      }
    },
    computed: {
      contentShortLength() {
        return this.postForm.content.length
      },

    },
    created() {
      if (this.isEdit) {
        const id = this.$route.params && this.$route.params.id;
        this.fetchData(id)
      }
      this.tempRoute = Object.assign({}, this.$route)
    },
    methods: {
      fetchData(id) {
        getNewsById(id).then(response => {
          this.postForm = response.data;

          this.postForm.title += ``;
          this.postForm.content += ``;

          // set tagsview title
          this.setTagsViewTitle();

          // set page title
          this.setPageTitle()
        }).catch(err => {
          console.log(err)
        });
        getAll().then(response => {
          this.categoryList = response.data;
        })
      },
      setTagsViewTitle() {
        const title = '编辑新闻';
        const route = Object.assign({}, this.tempRoute, {title: `${title}-${this.postForm.id}`})
        this.$store.dispatch('tagsView/updateVisitedView', route)
      },
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
                duration: 0
              });
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
          })
          return
        }
        //设置更新的管理员名
        this.postForm.audit = this.$store.getters.name;
        updateNews(this.postForm).then(response => {
          this.$notify({
            title: '成功',
            message: '你更新了一个文章!',
            type: 'warning',
            duration: 0
          });
        })

      }

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
