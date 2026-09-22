import store from '../store/index'

export function generateTitle(title) {
  const hasKey = this.$te('route.' + title)
  if (hasKey) {
    return this.$t('route.' + title)
  }
  return title
}

export function translateFileLanguageFromCode(res) {
  let msg = res.msg || ''
  let codeList = store.getters.cnAndEnArr    //获取存储的  code对比msg的对照表
  // console.log(codeList, 'codeList')
  codeList && codeList.forEach(value => {
    if (Number(value.code) == res.code) {
      msg = value.indexName ? value.indexName : msg
    }
  })
  return msg
}
