/**
 * @author: xhuarui
 * @time 2021-11-29
 *
 * axios类二次封装
 */

import axios, { AxiosResponse, AxiosResponseTransformer, CancelToken } from 'axios';
import { AxiosInstance, AxiosRequestConfig } from 'axios';

import router from '@/router';

// 引入 element-plus loading及messageBox组件
import { ElLoading, ElMessage, ElMessageBox } from 'element-plus';
// import 'element-plus/packages/theme-chalk/src/base.scss'
import configs from './config'
const CODE_MESSAGE = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。'
};
let reLoginTimes = 0

class HttpAxios {
  // axios实例
  instance: AxiosInstance;

  // 超时时间
  timeout: number = 10000;

  // elementUI loading实例
  loadingInstance: any;

  // axios cancelToken 数组，方便处理取消请求
  cancelTokenArr: Array<CancelToken> = [];

  constructor(config: AxiosRequestConfig) {

    let instance: AxiosInstance = axios.create(config);

    // 设置请求拦截
    instance.interceptors.request.use(this._requestInterceptors, (error) => {
      return Promise.reject(error);
    });

    // 响应拦截器
    instance.interceptors.response.use(this._responseInterceptors, (error) => {
      // TODO 根据不同的状态码
      return Promise.reject(error);
    });

    this.instance = instance;
  }

  /**
   * 请求拦截，处理header部分传值及是否显示loading
   * @param config
   */

  _requestInterceptors = (config: AxiosRequestConfig) => {
    // 生产环境在nginx配置代理  截取 /api
    let url = config.url || ''
    if (process.env.NODE_ENV !== 'development') {
      // console.log('process.env.NODE_ENV--', process.env.NODE_ENV)
      // config.url = 'https://back.maimaizhanghao.com' + url.split('/api')[1]
      // config.url = 'https://back.feihengdaishou.com' + url.split('/api')[1]
      config.url = configs.apiBaseUrl + url.split('/api')[1]
      // config.url = 'http://back.yc6366.com' + url.split('/api')[1]
      // config.url = 'http://back.taohaozhijia.cn' + url.split('/api')[1]
    }


    let token = window.localStorage.getItem('token')
    if (token) {
      token = JSON.parse(token)
    } else {
      token = ''
    }

    let pagePath = location.href
    console.log('pagePath', pagePath);
    let resource = 'web'
    console.log('resource', resource);

    // console.log("%c Line:73 🥕 token", "color:#4fff4B", token);
    let _config = {
      withCredentials: false, // 处理跨域问题
      timeout: config.timeout ? config.timeout : this.timeout,

      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        Token: token,
        Authorization: token,
        pagePath,
        resource
      }
    };

    return Object.assign({}, config, _config);
  };

  /**
   * 返回拦截
   * @param response
   * @returns
   */
  _responseInterceptors = (response: AxiosResponse) => {

    if (this.loadingInstance) {

      this.loadingInstance.close();
      this.loadingInstance = null;
      // console.log('this.loadingInstance',this.loadingInstance)

    }

    if (response.status === 200 || response.statusText) {
      // console.log("%c Line:110 🍅 response.data.code", "color:#ea7e5c", response.data.code);
      if (response.data.code == 401 && reLoginTimes === 0) {
        // console.log("%c Line:110 🍅 response.data.code", "color:#ea7e5c", response.data.code);
        // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        window.localStorage.clear()
        reLoginTimes++
        // 登录超时
        ElMessageBox.confirm(
          '登录超时，请重新登录',
          '提示',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
        )
          .then(() => {

            reLoginTimes = 0
            router.push({
              path: '/login'
            })
          })
          .catch(() => {
            reLoginTimes = 0
          })



      } else if (response.data.code == 403) {
        // window.localStorage.clear()
      }
      return response.data || {};
    }
    // this._checkResponse(data.code);
    return null;
  };

  /**
   * 处理请求错误时情况
   * @param error
   */
  _checkResponseError = (error: any) => {
    if (/timeout/g.test(error)) {
      // Dialog.alert({ message: '请求超时，请稍后再试！！！' })
    }
    const {
      response: {
        status,
        statusText,
        data: { msg = '服务器发生错误' }
      }
    } = error;
    const { response } = error;
    // console.log(response);
    return Promise.reject(error);
  };

  /**
   * 后台返回错误处理
   * @param code 后台定义错误码
   */
  _checkResponse = (code: string) => { };

  /**
   * 发送请求
   * @param url
   * @param params
   * @param method
   * @param config
   * @returns
   */
  sendRequest = (url: string, params: any, method: string = 'post', config?: any) => {
    if (!this.instance) {
      return;
    }
    if (!config || !config.isCloseLoading) {
      // TODO show loading
      // this.loadingInstance = ElLoading.service({
      //   lock: true,
      //   background: 'transparent',
      // });
    }
    const _method = method.toLocaleLowerCase();
    if (_method === 'get') {
      params = {
        params: params
      };
      return this.instance.get(url, params);
    }
    if (_method === 'put') {
      return this.instance.put(url, params);
    }
    if (_method === 'formdata') {
      let reqData = new FormData();
      for (let key in params) {
        reqData.append(key, params[key]);
      }
      return this.instance.post(url, reqData, config);
    }
    return this.instance.post(url, params, config);
  };
}

export default HttpAxios;
