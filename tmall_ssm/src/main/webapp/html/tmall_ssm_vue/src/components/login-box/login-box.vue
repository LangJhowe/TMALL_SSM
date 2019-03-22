<template>
  <div class="login-box">
    <form
      class="login-form"
      action="/login"
      @submit.prevent="onSubmit"
    >
      <h2>账户登录</h2>
      <div class="login-inputs">
        <div class="row">
          <span class="name-icon"><i class="glyphicon glyphicon-user"></i></span>
          <input
            type="text"
            v-model="loginForm.name"
            placeholder="手机/会员名/邮箱"
          />
        </div>
        <div class="row">
          <span class="password-icon"><i class="glyphicon glyphicon-lock"></i></span>
          <input
            type="password"
            v-model="loginForm.password"
            placeholder="密码"
            autocomplete="off"
          >
        </div>
      </div>
      <p style="color:#a94442;margin:0;">非官网请勿输入真实账号密码</p>
      <div class="other clearfix">
        <router-link
          class="fl"
          to="/login"
          @click.native="goto()"
        >忘记登录密码 </router-link>
        <router-link
          class="fr"
          to="/extra/registry"
        >免费注册</router-link>
      </div>
      <button
        class="login-btn"
        @click="doLogin()"
      >登录</button>
    </form>
  </div>
</template>

<script>
import { loginByUser } from '@/api/loginRegistry'
import { setUser } from '@/util/auth'
import CODES from '@/api/config'
export default {
  data () {
    return {
      loginForm: {
        name: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit () {
      return false
    },
    doLogin () {
      let form = this.loginForm

      if (form.name == '' || form.password == '') {
        alert('请输入账号密码')
        return
      }
      loginByUser(form).then(res => {
        const { data } = res
        if (CODES.SUCCESS == data.code) {
          this.$store.dispatch('fillUser', data.data)
          setUser(data.data)
          this.$notify({
            type: 'success',
            message: `登陆成功！欢迎回来,${data.data.name}!`
          })
          this.$emit('loginSuccess', data.data)
        } else {
          alert(data.msg)
        }
      })
    },
    goto () {
      alert('这个功能木有做')
    }
  }
}
</script>

<style lang="scss">
.login-box {
  position: relative;
  width: 4rem;
  z-index: 10;
  background: #fff;
  // margin-top: 0.2rem;
  margin-right: 0.4rem;
  padding: 0.6rem 0.3rem 0.8rem;
  .login-form {
    display: flex;
    flex-direction: column;
    h2 {
      font-size: 0.16rem;
      font-weight: bold;
      margin: 0;
    }
    .login-inputs {
      .row {
        display: flex;
        margin: 0.2rem 0;
        span[class$="-icon"] {
          display: inline-block;
          height: 0.4rem;
          width: 0.4rem;
          display: flex;
          justify-content: center;
          align-items: center;
          background: #cbcbcb;
          i {
            font-size: 0.22rem;
          }
        }
        input {
          flex: 1;
        }
      }
    }
    .other {
      margin: 0.2rem 0;
    }
    .login-btn {
      height: 0.34rem;
      background: #c40000;
      outline: none;
      border: none;
      border-radius: 5px;
      color: #fff;
      font-weight: bolder;
      line-height: 0.34rem;
      font-size: 0.14rem;
    }
  }
}
</style>
