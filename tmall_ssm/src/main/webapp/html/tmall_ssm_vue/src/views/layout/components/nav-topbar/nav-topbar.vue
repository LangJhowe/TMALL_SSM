<template>
  <nav class="f_navbar">
    <div class="m_container clearfix">
      <ul class="left fl">
        <li
          class="nav-item fl"
          v-for="item in leftBarItem"
          :key="item.name"
        >
          <router-link
            v-if="item.hasOwnProperty('url')"
            :to="item.url"
          >
            <span
              v-if="item.hasOwnProperty('logo')"
              :class="item.logo"
            ></span>
            {{item.name}}
          </router-link>
          <span v-else>{{item.name}}</span>
        </li>
        <li class="nav-item fl"><a @click="logout()">退出</a></li>
      </ul>
      <ul class="right fr">
        <li class="nav-item fl"><router-link to="/buyStep/bought">我的订单</router-link></li>
        <li class="nav-item fl">
          <router-link to="/buyStep/cart">
            <i class="glyphicon glyphicon-shopping-cart redColor"></i>
            购物车{{user?user.cartNum:0}}件
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import {mapGetters} from 'vuex'
import {getUser} from '@/util/auth'
export default {
  data () {
    return {
      leftBarItem: {
        toHome: {
          name: '天猫首页',
          url: '/',
          logo: 'glyphicon glyphicon-home redColor'
        },
        welcome: { name: '喵,欢迎来到天猫' },
        login: { name: (getUser() && getUser().name) || '请登录', url: '/login' }
        // logout: { name: '退出', url: '#' }
      },
      rightBarItem: [
        { name: '我的订单', url: '#' },
        { name: '购物车', logo: '' }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  methods: {
    logout () {
      this.$store.dispatch('logout')
    }
  },
  watch: {
    user (nval, oval) {
      if (nval && nval.name) {
        this.leftBarItem.login.name = nval.name
      } else {
        this.leftBarItem.login.name = '请登录'
      }
    }
  }
}
</script>

<style lang="scss">
.f_navbar {
  position: fixed;
  z-index: 1000;
  width: 100%;
  top: 0;
  padding: 0.05rem 0;
  color: #999;
  background: #f2f2f2;
  border-bottom: 0.01rem solid #e7e7e7;
  .nav-item {
    margin: 0 0.1rem;
  }
  a {
    color: #999;
    &:hover {
      cursor: pointer;
      color: #c40000;
    }
  }
  .left {
  }
}
</style>
