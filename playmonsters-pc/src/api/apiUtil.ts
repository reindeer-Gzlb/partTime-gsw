import axios from 'axios';
import Qs from 'qs';
import store from '../store/index';

// 创建axios实例
const createApiInstance = (config: any, method?: String) => {
  let _config: any = {
    withCredentials: false, // 跨域, 是否需要证书
    baseURL: '',
    timeout: 15000,
    closeLoading: false,
    headers: {},
  };
  if (method == 'formate') {
    _config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
  }
  console.log('config is ', Object.assign(_config, config));
  return axios.create(Object.assign(_config, config));
};

function sendApiInstance(method: String, url: String, params: any, config: any) {
  if (!url) {
    return;
  }
  if (!config.closeLoading) {
    // store.commit('showMaskView');
  }
  let instance: any = {};
  if (method == 'formate') {
    instance = createApiInstance(config, method);
  } else {
    instance = createApiInstance(config);
  }
  instance.interceptors.response.use(
    (response: any) => {
      let d = response.data || {};
      if (checkError(d)) {
        return d.data || {};
      }
    },
    (error: any) => {
      const { response: { status, statusText, data: { msg = '服务器发生错误' } } } = error
      const { response } = error
      console.log(response);
      if (/timeout/g.test(error)) {
        // Dialog.alert({ message: '请求超时，请稍后再试！！！' })
      }
      return null;
    }
  );
  if (method === 'get') {
    params = {
      params: params
    };
    return instance['get'](url, params, config);
  }
  if (method === 'formdata') {
    params = Qs.stringify(
      params,
      //  加入JS对象转换配置
      {
        allowDots: true
      }
    );
    return instance['post'](url, params, config);
  }
  return instance['post'](url, params, config);
}

const generateApiMap = (map: any) => {
  let facade: any = {};
  for (let key in map) {
    facade[key] = toMethod(map[key]);
  }
  return facade;
};

const toMethod = (options: any) => {
  options.method = options.method || 'post';
  return (params = {}, attachedParams = {}) => {
    let config = {
      closeLoading: false,
      checkRes: true,
      useSaveTrack: false
    };
    config = Object.assign({}, config, options.config, attachedParams);
    if (!config.closeLoading) {
      // $this.$loading(true)
    }
    return sendApiInstance(options.method, options.url, params, config);
  };
};

function checkError(response: any): Boolean {
  if (!response) {
    return false;
  }
  if (response.code === '0000') {
    return true;
  }
  // TODO 根据不同code信息，弹出response.msg错误信息
  return false;
}

export default {
  generateApiMap
};
