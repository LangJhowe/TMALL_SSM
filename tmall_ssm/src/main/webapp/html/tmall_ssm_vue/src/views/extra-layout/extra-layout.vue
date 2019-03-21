<template>
  <div class="registry-page">
    <nav-topbar></nav-topbar>
    <div class="f_header">
      <div class="m_container">
        <img src="/static/img/simpleLogo.png" alt="">
        <search></search>
      </div>
    </div>
    <div class="f_main">
      <!-- :class="{show:!isValidate}" -->
      <router-view></router-view>
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
    FFooter,
    Search
  }
}
</script>

<style lang="scss">
.registry-page{
  .f_header{
    .m_container{
      display: flex;
      justify-content: space-between;
      align-items: center;
      img{
        height: 0.2rem;
      }
    }
  }
}
</style>
