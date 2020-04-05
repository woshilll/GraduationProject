<template>
  <el-dropdown :show-timeout="100" trigger="click">
    <el-button plain >
      {{ isDelete === 0 ?'是否删除: 未删除':'是否删除: 已删除' }}
      <i class="el-icon-caret-bottom el-icon--right" />
    </el-button>
    <el-dropdown-menu slot="dropdown" class="no-padding">
      <el-dropdown-item>
        <el-radio-group v-model="isDelete" style="padding: 10px;">
          <el-radio :label="0">
            未删除
          </el-radio>
          <el-radio :label="1">
            已删除
          </el-radio>
        </el-radio-group>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  props: {
    value: {
      type: Number,
      default: 0
    }
  },
  computed: {
    isDelete: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  }
}
</script>
