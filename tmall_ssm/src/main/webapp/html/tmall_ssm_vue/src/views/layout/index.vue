<template>
  <div>
    <nav-topbar></nav-topbar>
    <f-head></f-head>
    <main class="f_main">
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </main>
    <f-footer></f-footer>
  </div>

</template>

<script>
import {NavTopbar, FHead, FFooter} from './components'
export default{
  components: {
    NavTopbar,
    FHead,
    FFooter
  }
}
</script>

<style lang="scss">
.f-main{
  margin-bottom: 0.1rem;
}
</style>
