import { createApp } from 'vue';
import App from '@/App.vue';
import "@/utils/common.js"
import '@/assets/fonts/fonts.css'
import '@/assets/quill.core.css'

import router from '@/router/index';
import store from '@/store/index';
// 自己封装的
import myUI from './components'
import 'lib-flexible'

import 'viewerjs/dist/viewer.css'
import AnimatedNumber from 'animated-number-vue3'
import VueViewer from 'v-viewer'
createApp(App).use(router).use(store).use(VueViewer).use(AnimatedNumber).use(myUI).mount('#app');
