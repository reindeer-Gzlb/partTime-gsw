import apiUtil from '@/api/apiUtil'
import customer from '@/api/customer'

/**
 * api 对象接口定义
 */
interface apiMap {
  method?: string;
  url: string,
  config?: {
    isCloseLoading: boolean,
  }
}

interface apiMaps {
  [propName: string]: apiMap
}

import HttpAxios from "@/api/axios";

const httpAxios = new HttpAxios({});

function generateApiMap(maps: apiMaps) {
  let methodMap: any = {};
  for (let key in maps) {
    methodMap[key] = toMethod(maps[key]);
  }
  return methodMap;
}

function toMethod(options: apiMap) {
  options.method = options.method || 'post';
  const { method = 'post', url, config } = options;
  return (param: any = {}) => {
    return httpAxios.sendRequest(url, param, method, config);
  }
}

const apis = generateApiMap({
  ...customer
})
export default {
  ...apis // 取出所有可遍历属性赋值在新的对象上
}