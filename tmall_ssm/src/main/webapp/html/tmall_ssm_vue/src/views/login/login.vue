<template>
  <div class="login-page">
    <div class="f_head">
      <div class="m_container">
        <router-link to="/">
          <img src="/static/img/simpleLogo.png" alt="">
        </router-link>
      </div>
    </div>
    <div class="f_main">
      <div class="m_container">
        <img class="bgImg" src="/static/img/loginBackground.png" alt="">
        <login-box @loginSuccess="handleLoginSuccess"></login-box>
      </div>
    </div>
    <f-footer></f-footer>
  </div>
</template>

<script>
import LoginBox from '@/components/login-box/login-box'
import {FFooter} from '@/views/layout/components'
export default {
  components: {
    FFooter,
    LoginBox
  },
  methods: {
    handleLoginSuccess () {
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss">
.login-page{
  .f_head{
    padding: 0.32rem 0;
  }
  .f_main{
    height: 6rem;
    .m_container{
      height: 100%;
      position: relative;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      .bgImg{
        position: absolute;
        width: 100%;
        height: 6rem;
      }
    }
  }
}
</style>
