// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// import iView from 'iview';
// import 'iview/dist/styles/iview.css';    // 使用 CSS

// Vue.use(iView);

import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'
import Toast from 'muse-ui-toast'
import './assets/font/material-icons.css'

Vue.use(MuseUI)
Vue.use(Toast)

import axios from 'axios'
import VueAxios from 'vue-axios'

// import Qs from 'qs'

var axiosInstance = axios.create({
  baseURL: 'http://localhost:8888/',
  // baseURL: 'http://127.0.0.1:9009/',
  // transformRequest: function (data) {
  //   return Qs.stringify(data)
  // },
  // headers: {'Content-Type': 'application/x-www-form-urlencoded'}
})

Vue.use(VueAxios, axiosInstance)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
