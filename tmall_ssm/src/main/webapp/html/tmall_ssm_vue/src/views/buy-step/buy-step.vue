<template>
  <div class="buy-step">
    <nav-topbar></nav-topbar>
    <div class="f_head">
      <div class="m_container">
        <img class="logo" src="/static/img/simpleLogo.png" alt="">
        <img v-show="needFlow" class="step-img" src="/static/img/buyflow.png" alt="">
        <search v-show="needSearch"></search>
      </div>
    </div>
    <div class="f_main">
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>
    <f-footer></f-footer>
  </div>
</template>

<script>

import {NavTopbar, FFooter} from '@/views/layout/components'
import Search from '@/components/search/search'
export default {
  components: {
    NavTopbar,
    Search,
    FFooter
  },
  data () {
    return {
    }
  },
  computed: {
    needSearch () {
      var path = this.$route.path
      switch (path) {
        case '/buyStep/stepPayed': return true
        case '/buyStep/bought': return true
        default:return false
      }
    },
    needFlow () {
      var path = this.$route.path
      switch (path) {
        case '/buyStep/stepPay': return true
        case '/buyStep/step1': return true
        default:return false
      }
    }
  },
  mounted () {},
  methods: {},
  watch: {}
}
</script>

<style lang="scss">
.buy-step{
  .f_head{
    .m_container{
      display: flex;
      justify-content: space-between;
      align-items: center;
      .logo{position: relative;height:0.27rem;top:0;}
    }
  }

}
</style>
