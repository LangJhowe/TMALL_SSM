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
        <login-box></login-box>
        <!-- <div class="login-box">
          <form class="login-form" action="/login" @submit.prevent="onSubmit">
            <h2>账户登录</h2>
            <div class="login-inputs">
              <div class="row">
                <span class="name-icon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" v-model="loginForm.name" placeholder="手机/会员名/邮箱"/>
              </div>
              <div class="row">
                <span class="password-icon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" v-model="loginForm.password" placeholder="密码" autocomplete="off">
              </div>
            </div>
            <p style="color:#a94442;margin:0;">非官网请勿输入真实账号密码</p>
            <div class="other clearfix">
              <router-link class="fl" to="/login" @click.native="goto()">忘记登录密码 </router-link>
              <router-link class="fr" to="/extra/registry">免费注册</router-link>
            </div>
            <button class="login-btn" @click="doLogin()">登录</button>
          </form>
        </div> -->
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
