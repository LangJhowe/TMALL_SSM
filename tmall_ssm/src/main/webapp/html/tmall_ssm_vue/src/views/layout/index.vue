<template>
  <div>
    <nav-topbar></nav-topbar>
    <f-head></f-head>
    <f-main></f-main>
    <f-footer></f-footer>
  </div>

</template>

<script>
import {NavTopbar, FHead, FMain, FFooter} from './components'
export default{
  components: {
    NavTopbar,
    FHead,
    FMain,
    FFooter
  }
}
</script>

<style lang="scss">

</style>
