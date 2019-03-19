import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const Layout = (resolve) => { require(['@/views/layout'], resolve) }
const Login = (resolve) => { require(['@/views/login/login'], resolve) }
const Registry = (resolve) => { require(['@/views/registry/registry'], resolve) }
export default new Router({
  routes: [
    {path: '/', name: 'Home', component: Layout},
    {path: '/login', name: 'Login', component: Login},
    {path: '/registry', name: 'Registry', component: Registry}
  ]
})
