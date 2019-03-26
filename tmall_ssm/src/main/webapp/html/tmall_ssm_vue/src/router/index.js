import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

// ps 有些页面不支持回退 需要在需要禁止的页面 添加跳转属性 replace：true
const Layout = (resolve) => { require(['@/views/layout'], resolve) }
const MainHome = (resolve) => { require(['@/views/main-home/main-home/'], resolve) }
const MainSearch = (resolve) => { require(['@/views/main-search/main-search/'], resolve) }
const MainCategory = (resolve) => { require(['@/views/main-category/main-category'], resolve) }
const Login = (resolve) => { require(['@/views/login/login'], resolve) }
const Registry = (resolve) => { require(['@/views/registry/registry'], resolve) }
const ExtraLayout = (resolve) => { require(['@/views/extra-layout/extra-layout'], resolve) }

const Product = (resolve) => { require(['@/views/product/product'], resolve) }

const BuySteps = (resolve) => { require(['@/views/buy-step/buy-step'], resolve) }
const BuyStep1 = (resolve) => { require(['@/views/buy-step/step/step1'], resolve) }
const BuyStepPay = (resolve) => { require(['@/views/buy-step/step/stepPay'], resolve) }
const BuyStepPayed = (resolve) => { require(['@/views/buy-step/step/stepPayed'], resolve) }
const BuyStepConfirm = (resolve) => { require(['@/views/buy-step/step/stepConfirm'], resolve) }
const BuyStepSuccess = (resolve) => { require(['@/views/buy-step/step/stepSuccess'], resolve) }
const BuyStepReview = (resolve) => { require(['@/views/buy-step/step/stepReview'], resolve) }

const Bought = (resolve) => { require(['@/views/buy-step/step/bought'], resolve) }
const Cart = (resolve) => { require(['@/views/buy-step/step/cart'], resolve) }

export default new Router({
  routes: [
    {path: '/login', name: 'Login', component: Login},
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
    {
      path: '/extra',
      redirect: '/extra/registry',
      component: ExtraLayout,
      children: [
        {path: 'registry', name: 'registry', component: Registry},
        {path: 'product', name: 'Product', component: Product}
      ]
    },
    {
      path: '/buyStep',
      redirect: '/buyStep/step1',
      component: BuySteps,
      children: [
        {path: 'step1', name: 'step1', component: BuyStep1},
        {path: 'stepPay', name: 'stepPay', component: BuyStepPay},
        {path: 'stepPayed', name: 'stepPayed', component: BuyStepPayed},
        {path: 'stepConfirm', name: 'stepConfirm', component: BuyStepConfirm},
        {path: 'stepSuccess', name: 'stepSuccess', component: BuyStepSuccess},
        {path: 'stepReview', name: 'stepReview', component: BuyStepReview},
        {path: 'bought', name: 'bought', component: Bought},
        {path: 'cart', name: 'cart', component: Cart}

      ]
    }
  ]
})
