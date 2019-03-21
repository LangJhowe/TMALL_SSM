import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const Layout = (resolve) => { require(['@/views/layout'], resolve) }
const MainHome = (resolve) => { require(['@/views/main-home/main-home/'], resolve) }
const MainSearch = (resolve) => { require(['@/views/main-search/main-search/'], resolve) }
const MainCategory = (resolve) => { require(['@/views/main-category/main-category'], resolve) }
const Login = (resolve) => { require(['@/views/login/login'], resolve) }
const Registry = (resolve) => { require(['@/views/registry/registry'], resolve) }
const ExtraLayout = (resolve) => { require(['@/views/extra-layout/extra-layout'], resolve) }

const Product = (resolve) => { require(['@/views/product/product'], resolve) }

export default new Router({
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: 'home',
      children: [
        {path: 'home', name: 'home', component: MainHome},
        {path: 'search', name: 'search', component: MainSearch},
        {path: 'category', name: 'cateogry', component: MainCategory}

      ]
    },
    {path: '/login', name: 'Login', component: Login},

    // {
    //   path: '/registry',
    //   name: 'registry',
    //   component: Registry
    // }
    {
      path: '/extra',
      redirect: 'registry',
      component: ExtraLayout,
      children: [
        {path: 'registry', name: 'registry', component: Registry},
        {path: 'product', name: 'Product', component: Product}
      ]
    }

  ]
})
