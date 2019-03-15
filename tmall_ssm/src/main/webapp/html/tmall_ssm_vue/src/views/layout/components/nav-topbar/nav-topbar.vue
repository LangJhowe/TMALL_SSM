<template>
  <nav class="f_navbar">
    <div class="m_container clearfix">
      <ul class="left fl">
        <li
          class="nav-item fl"
          v-for="item in leftBarItem"
          :key="item.name"
        >
          <a
            v-if="item.hasOwnProperty('url')"
            href="#"
          >
            <span
              v-if="item.hasOwnProperty('logo')"
              :class="item.logo"
            ></span>
            {{item.name}}
          </a>
          <span v-else>{{item.name}}</span>
        </li>
      </ul>
      <ul class="right fr">
        <li class="nav-item fl"><a href="#">我的订单</a></li>
        <li class="nav-item fl"><a href="#">
            <i class="glyphicon glyphicon-shopping-cart redColor"></i>
            购物车0件
          </a></li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  data () {
    return {
      leftBarItem: [
        {
          name: '天猫首页',
          url: '#',
          logo: 'glyphicon glyphicon-home redColor'
        },
        { name: '喵,欢迎来到天猫' },
        { name: '测试用户1', url: '##' },
        { name: '退出', url: '#' }
      ],
      rightBarItem: [
        { name: '我的订单', url: '#' },
        { name: '购物车', logo: '' }
      ]
    }
  }
}
</script>

<style lang="scss">
.f_navbar {
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
      color: #c40000;
    }
  }
  .left {
  }
}
</style>
