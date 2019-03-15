<template>
  <div class="category-carousel">
    <div class="m_container">
      <ul class="main-menu">
        <li
          class="item"
          v-for="item in category"
          :key="item.name"
        ><a href=""><i class="glyphicon glyphicon-link"></i>{{item.name}}</a></li>
      </ul>
    </div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  computed: {
    ...mapGetters(['category'])
  },
  watch: {
    category (oldv, newv) {
      console.log('ssss')
    }
  }
}
</script>

<style lang="scss">
.category-carousel{
  background:#e8e8e8;
  .m_container{
    .main-menu{
      .item{
        width: 2rem;
        height: 0.3rem;
        line-height: 0.3rem;
        a{
          color: #000;
          font-size: 0.14rem;
          .glyphicon{padding: 0 0.1rem;}
        }
      }
    }
  }
}
</style>
