<template>
  <div>
    <el-select
      popper-class="virtualselect"
      class="virtual-select-custom-style"
      :value="defaultValue"
      filterable
      :filter-method="filterMethod"
      default-first-option
      clearable
      :placeholder="placeholder"
      :disabled="disabled"
      :multiple="isMultiple"
      :allow-create="allowCreate"
      @visible-change="visibleChange"
      v-on="$listeners"
      @clear="clearChange"
    >
      <virtual-list
        ref="virtualList"
        class="virtualselect-list"
        :data-key="value"
        :data-sources="selectArr"
        :data-component="itemComponent"
        :keeps="keepsParams"
        :extra-props="{
          label: label,
          value: value,
          isRight: isRight,
          isConcat: isConcat,
          concatSymbol: concatSymbol
        }"
      ></virtual-list>
    </el-select>
  </div>
</template>
<script>
  const validatenull = (val) => {
    if (typeof val === 'boolean') {
      return false
    }
    if (typeof val === 'number') {
      return false
    }
    if (val instanceof Array) {
      if (val.length===0) return true
    } else if (val instanceof Object) {
      if (JSON.stringify(val) === '{}') return true
    } else {
      if (val==='null' || val===null || val==='undefined' || val===undefined || val==='') return true
      return false
    }
    return false
  }
  import virtualList from 'vue-virtual-scroll-list'
  import ElOptionNode from './elOptionNode.vue'
  export default {
    name: 'VirtualSelect',
    components: {
      'virtual-list': virtualList
    },
    model: {
      prop: 'bindValue',
      event: 'change'
    },
    props: {
      // 数组
      list: {
        type: Array,
        default() {
          return []
        }
      },
      // 显示名称
      label: {
        type: String,
        default: ''
      },
      // 标识
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
      // 显示右边
      isRight: {
        type: Boolean,
        default: false
      },
      // 加载条数
      keepsParams: {
        type: Number,
        default: 10
      },
      // 绑定的默认值
      bindValue: {
        type: [String, Array,Number],
        default() {
          if (typeof this.bindValue === 'string') return ''
          return []
        }
      },
      // 是否多选
      isMultiple: {
        type: Boolean,
        default: false
      },
      // 输入框占位文本
      placeholder: {
        type: String,
        default: '请选择'
      },
      // 是否禁用
      disabled: {
        type: Boolean,
        default: false
      },
      // 是否允许创建条目
      allowCreate: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        itemComponent: ElOptionNode,
        selectArr: [],
        defaultValue: null // 绑定的默认值
      }
    },
    watch: {
      'list'() {
        this.init()
      },
      bindValue: {
        handler(val, oldVal) {
          this.defaultValue = this.bindValue
          if (validatenull(val)) this.clearChange()
          this.init()
        },
        immediate: false,
        deep: true
      }
    },
    mounted() {
      this.defaultValue = this.bindValue
      this.init()
    },
    methods: {
      init() {
        if(this.list.length==0){
          return
        }
        if (!this.defaultValue || this.defaultValue?.length === 0) {
          this.selectArr = this.list
        } else {
          // 回显问题
          // 由于只渲染固定keepsParams（10）条数据,当默认数据处于10条之外,在回显的时候会显示异常
          // 解决方法:遍历所有数据,将对应回显的那一条数据放在第一条
          this.selectArr = JSON.parse(JSON.stringify(this.list))
          let obj = {}
          if ( (typeof this.defaultValue === 'string'||typeof this.defaultValue === 'number') && !this.isMultiple) {
            if (this.allowCreate) {
              const arr = this.selectArr.filter(val => {
                return val[this.value] === this.defaultValue
              })
              if (arr.length === 0) {
                const item = {}
                item[this.value] = this.defaultValue
                item[this.label] = this.defaultValue
                item.allowCreate = true
                this.selectArr.push(item)
                this.$emit('selChange', item)
              } else {
                this.$emit('selChange', arr[0])
                this.selectArr.unshift(arr[0])
                this.$forceUpdate()
              }
            }

            // // 单选
            // for (let i = 0; i < this.selectArr.length; i++) {
            //   const element = this.selectArr[i]
            //   if (element[this.value] === this.defaultValue) {
            //     obj = element
            //     console.log("%c Line:191 🍡 element", "color:#4fff4B", element);
            //     this.selectArr?.splice(i, 1)
            //     break
            //   }
            // }
            // this.selectArr?.unshift(obj)
            // this.$forceUpdate()
          } else if (this.isMultiple) {
            if (this.allowCreate) {
              this.defaultValue.map(v => {
                const arr = this.selectArr.filter(val => {
                  return val[this.value] === v
                })
                if (arr?.length === 0) {
                  const item = {}
                  // item[this.value] = `Create-${v}`
                  item[this.value] = v
                  item[this.label] = v
                  item.allowCreate = true
                  this.selectArr.push(item)
                  this.$emit('selChange', item)
                } else {
                  this.$emit('selChange', arr[0])
                }
              })
            }

            // 多选
            for (let i = 0; i < this.selectArr.length; i++) {
              const element = this.selectArr[i]
              this.defaultValue?.map(val => {
                if (element[this.value]?.toLowerCase() === val?.toLowerCase()) {
                  obj = element
                  this.selectArr?.splice(i, 1)
                  this.selectArr?.unshift(obj)
                }
              })
            }
          }
        }
      },
      // 搜索
      filterMethod(query) {
        if (!validatenull(query?.trim())) {
          this.$refs.virtualList.scrollToIndex(0) // 滚动到顶部
          setTimeout(() => {
            this.selectArr = this.list.filter(item => {
              return this.isRight || this.isConcat
                ? (item[this.label].trim()?.toLowerCase()?.indexOf(query?.trim()?.toLowerCase()) > -1 || item[this.value]?.toLowerCase()?.indexOf(query?.trim()?.toLowerCase()) > -1)
                : item[this.label]?.toLowerCase()?.indexOf(query?.trim()?.toLowerCase()) > -1
            })
          }, 100)
        } else {
          setTimeout(() => {
            this.init()
          }, 100)
        }
      },
      visibleChange(bool) {
        if (!bool) {
          this.$refs.virtualList.reset()
          this.init()
        }
        this.$emit('visible-change', bool)
      },
      clearChange() {
        if (typeof this.defaultValue === 'string') {
          this.defaultValue = ''
        } else if (this.isMultiple) {
          this.defaultValue = []
        }
        this.visibleChange(false)
      }
    }
  }
</script>
<style scoped>
/* .virtual-select-custom-style ::v-deep .el-select-dropdown__item {
  // 设置最大宽度，超出省略号，鼠标悬浮显示
  // options 需写 :title="source[label]"
  width: 250px;
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
} */
.virtualselect {
  max-height:245px;
  overflow-y:auto;
  /* // 设置最大高度
  &-list {
    max-height:245px;
    overflow-y:auto;
  } */
}
.virtualselect-list {
  max-height:245px;
  overflow-y:auto;
}
.el-select {
  width: 100%;
}
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
  background-color: transparent;
  cursor: pointer;
  margin-right: 5px;
}
::-webkit-scrollbar-thumb {
  background-color: rgba(144,147,153,.3) !important;
  border-radius: 3px !important;
}
::-webkit-scrollbar-thumb:hover{
  background-color: rgba(144,147,153,.5) !important;
}
::-webkit-scrollbar-track {
  background-color: transparent !important;
  border-radius: 3px !important;
  -webkit-box-shadow: none !important;
}
::v-deep  .el-select__tags {
  flex-wrap: unset;
  overflow: auto;
}
</style>
<style>
.virtualselect .el-select-dropdown__item{
    display: block;
    max-width: 350px;
    overflow: visible;
}
</style>

