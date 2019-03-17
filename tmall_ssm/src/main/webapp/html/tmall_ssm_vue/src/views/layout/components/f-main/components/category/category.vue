<template>
  <div class="category">
    <nav class="cross-nav">
      <div class="m_container">
        <ul class="left-menu">
          <li><i class="glyphicon glyphicon-th-list" style="margin: 0 10px"></i>商品分类</li>
        </ul>
        <ul class="right-menu">
          <li
            v-for="CNItem in crossNavItem"
            :key="CNItem.name"
          >
            <a
              v-if="CNItem.hasOwnProperty('img')"
              href="#"
            ><img
                :src="CNItem.img"
                alt=""
              ></a>
            <a
              :href="CNItem.url"
              v-else
            >{{CNItem.name}}</a>
          </li>
        </ul>
      </div>

    </nav>

    <carousel></carousel>
  </div>
</template>

<script>
import Carousel from '@/components/carousel/carousel.vue'
import { mapGetters } from 'vuex'
export default {
  components: {
    Carousel
  },
  data () {
    return {
      originNavItems: [
        { name: '天猫超市', img: 'static/img/chaoshi.png', url: '' },
        { name: '天猫国际', img: 'static/img/guoji.png', url: '' }
      ]
    }
  },
  methods: {},
  computed: {
    crossNavItem () {
      var subItems = this.category.slice(0, 4)
      var extraItems = subItems.map(item => {
        return { name: item.name, url: '' }
      })
      return this.originNavItems.concat(extraItems)
    },
    ...mapGetters(['category'])
  }
}
</script>

<style lang="scss">
.category {
  font-size: 0.16rem;
  color: #fff;
  .cross-nav {
    height: 0.36rem;
    background: #dd2727;
    .m_container{display: flex;}
    ul {
      height: 0.36rem;
      display: flex;
      li {
        line-height: 0.36rem;
        a {
          color: #fff;
        }
      }
    }
    .left-menu {
      width: 2rem;
      background: #c60a0a;
    }
    .right-menu {
      flex: 1;
      li {
        cursor: pointer;
        padding: 0 0.2rem;
        a > img {
          height: 0.36rem;
        }
        position: relative;
        &::after {
          opacity: 0;
          content: "";
          background: url("/static/img/catear.png") no-repeat;
          background-size: 100%;
          width: 30px;
          height: 100%;
          position: absolute;
          top: 10px;
          left: 50%;
          transform: translate(-50%, -50%);
          transition: all 0.4s ease-in-out;
          z-index: -1;
        }
        &:hover::after {
          opacity: 1;
        }
      }
    }
  }
}
</style>
