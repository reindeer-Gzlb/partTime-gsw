<template>
  <el-option
    :key="label+value"
    :label="concatString(source[label], source[value])"
    :value="source[value]"
    :disabled="source.disabled"
    :title="concatString(source[label], source[value])"
  >
    <span>{{ concatString(source[label], source[value]) }}</span>
    <span
      v-if="isRight"
      style="float:right;color:#939393"
    >{{ source[value] }}</span>
  </el-option>
</template>
<script>
  export default {
    name: 'ElOptionNode',
    props: {
      // 每一行的索引
      index: {
        type: Number,
        default: 0
      },
      // 每一行的内容
      source: {
        type: Object,
        default() {
          return {}
        }
      },
      // 需要显示的名称
      label: {
        type: String,
        default: ''
      },
      // 绑定的值
      value: {
        type: String,
        default: ''
      },
      // 是否拼接label | value
      isConcat: {
        type: Boolean,
        default: false
      },
      // 拼接label、value符号
      concatSymbol: {
        type: String,
        default: ' | '
      },
      // 右侧是否显示绑定的值
      isRight: {
        type: Boolean,
        default() {
          return false
        }
      }
    },
    methods: {
      concatString(a, b) {
        a = a || ''
        b = b || ''
        if (this.isConcat) {
          return a + ((a && b) ? this.concatSymbol : '') + b
        }
        return a
      }
    }
  }
</script>


