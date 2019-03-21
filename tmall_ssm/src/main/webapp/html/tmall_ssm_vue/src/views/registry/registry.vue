<template>
      <div class="m_container">
        <div class="registry-box" v-if="!registrySuccess">
          <div class="m_message" :class="{m_show:!isValidate}" ref="msg"><p>{{message}}</p></div>
          <form class="registry-form" action="" @submit.prevent="onSubmit">
            <ul>
              <li class="title"><label for="i_name">设置会员名</label></li>
              <li><label for="i_name">登录名/会员名</label><input id="i_name" v-model="registryForm.name" type="text" placeholder="会员名一旦设置成功,无法修改" autocomplete="name"></li>
              <li class="title"><label for="i_password">设置登录密码</label><span>登陆时验证，保护账号信息</span></li>
              <li><label for="i_password">登录密码</label><input id="i_password" v-model="registryForm.password" type="password" placeholder="请设置你的登录密码" autocomplete="new-password"></li>
              <li><label for="i_confirmPwd" >密码确认</label><input id="i_confirmPwd" v-model="confirmPwd" type="password" placeholder="请再次输入你的密码" autocomplete="new-password"></li>
              <li><button @click="doRegistry()">提交</button></li>
            </ul>
          </form>
        </div>
        <div v-else class="registry-success">
          <div class="m_container"><img src="/static/img/registerSuccess.png" alt="">恭喜注册成功</div>

        </div>
      </div>
</template>

<script>
import {registry} from '@/api/loginRegistry'
import CODES from '@/api/config'
export default {
  data () {
    return {
      registryForm: {
        name: '',
        password: ''
      },
      confirmPwd: '',
      isValidate: true,
      message: 'hello',
      registrySuccess: false

    }
  },
  methods: {
    onSubmit () {
      return false
    },
    doRegistry () {
      let name = this.registryForm.name
      let pwd = this.registryForm.password
      let cPwd = this.confirmPwd
      if (name == '') {
        this.isValidate = false
        this.message = '请输入用户名'
        return false
      }
      if (pwd == '') {
        this.isValidate = false
        this.message = '请输入密码'
        return false
      }
      if (cPwd == '') {
        this.isValidate = false
        this.message = '请输入确认密码'
        return false
      }
      if (pwd !== cPwd) {
        this.isValidate = false
        this.message = '登录密码与确认密码不一致'
        return false
      }
      registry(this.registryForm).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.registrySuccess = true
          // this.$notify({
          //   type: 'success',
          //   message: '注册成功！欢迎你' + this.registryForm.name,
          //   duration: 3000
          // })
        } else {
          this.isValidate = false
          this.message = data.msg
        }
      })
    }
  }
}
</script>

<style lang="scss">
    .m_message{
      opacity: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 8rem;
      margin: 0 auto;
      height: 0.5rem;
      color: #a94442;
      background: #f2dede;
      border: 1px solid #ebccd1;
      border-radius: 4px;
      p{margin: 0}
    }
    .m_show{opacity: 1;}
    .registry-form{
      max-width: 5rem;
      margin: 0 auto;
      li{
        display: flex;
        padding: 0.1rem 0;
        height: 0.5rem;
        line-height: 0.3rem;
        label{
          width: 1.5rem;
          text-align: right;
          font-weight: normal;
          padding: 0 0.3rem;
          margin: 0;
        }
        input{
          width: 2.5rem;
        }
        button{
          width: 100%;
          outline: none;
          background:#c40000;
          border: none;
          color: #fff;
          border-radius: 4px;
          &:hover{
            opacity: 0.7;
          }
          &:active{
            opacity: 1;
          }
        }
      }
      li[class*="title"]{
        label{
          font-weight: bold;
        }
      }
    }
    .registry-success{
      background: #f3fdf6;
      border:1px solid #def3e6;
      padding: 0.2rem 0.7rem;
      font-size: 0.16rem;
      margin-bottom: 0.1rem;
    }
</style>
