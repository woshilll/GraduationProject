<template>
  <el-form ref="form" :model="crawlerNews" :rules="rules" label-width="80px" style="margin-top: 10px">
    <el-form-item label="新闻分类" prop="categoryId">
      <el-select v-model="crawlerNews.categoryId" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="解析地址" prop="newsUrls">
      <el-input type="textarea" v-model="crawlerNews.newsUrls" placeholder="仅支持凤凰网的新闻解析, 一次可解析多个新闻" clearable :rows="5"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit('form')">解析</el-button>
      <el-button @click="resetForm('form')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import {news} from "../../api/crawler";

  export default {
    name: "News",
    data() {
      const validateCategoryId = (rule, value, callback) => {
        if (value < 1 || value > 10) {
          callback(new Error('新闻分类错误!'))
        } else {
          callback();
        }
      };
      const validateNewsUrls = (rule, value, callback) => {
        if (!value.includes('https://') || !value.includes('ifeng.com')) {
          callback(new Error('新闻解析地址有误!'))
        } else {
          callback();
        }
      };
      return {
        crawlerNews: {
          categoryId: 1,
          newsUrls: '',
          audit: ''
        },
        options: [{
          value: 1,
          label: '推荐'
        }, {
          value: 2,
          label: '要闻'
        }, {
          value: 3,
          label: '综合'
        }, {
          value: 4,
          label: '教育'
        }, {
          value: 5,
          label: '军事'
        }, {
          value: 6,
          label: '文化'
        }, {
          value: 7,
          label: '党史'
        }, {
          value: 8,
          label: '国际'
        }, {
          value: 9,
          label: '热点'
        }, {
          value: 10,
          label: '娱乐'
        }],
        rules: {
          categoryId: [
            { required: true, message: '请输入新闻分类', trigger: 'blur' },
            { validator: validateCategoryId},
          ],
          newsUrls: [
            { required: true, message: '请输入解析新闻路径', trigger: 'blur' },
            { validator: validateNewsUrls}
          ]
        }
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.crawlerNews.audit = this.$store.getters.name;
            news(this.crawlerNews).then((response) => {
              this.$notify({
                message: response.message,
                type: 'success',
                duration: 0
              });
              this.resetForm(formName);
            })
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>

</style>
