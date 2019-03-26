// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

import App from './App'
import router from './router'
import axios from 'axios'
import store from './store'
import 'normalize.css'
import CODES from './api/config'
// import './permission'
import 'babel-polyfill'
Vue.prototype.$axios = axios
Vue.prototype.$store = store
Vue.prototype.$CODES = CODES
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
