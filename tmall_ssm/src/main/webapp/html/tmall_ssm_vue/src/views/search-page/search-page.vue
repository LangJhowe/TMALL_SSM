<template>
  <div class="search-page">
    <nav-topbar></nav-topbar>
    <div class="f_head">
      <div class="m_container">
        <search></search>
      </div>
    </div>
    <div class="f_main">
      <div class="m_container">
        <ul class="sproducts-wall">
            <li class="product" v-for="p in productsData" :key="p.id">
              <div class="info">
                <img :src="`http://localhost:8080/tmall_ssm/img/productSingle/${p.firstProductImage.id}.jpg`" alt="">
                <p class="price">￥ {{p.promotePrice}}</p>
                <p>{{p.name}}</p>
                <p><a href="javascript:void(0)" style="text-decoration:underline">天猫专卖</a></p>
              </div>
              <ul class="statics">
                <li class="sale">月成交 <span>{{p.saleCount}}笔</span></li>
                <li class="review">评价<span>{{p.reviewCount}}</span></li>
                <li class="wangwang"><img src="/static/img/wangwang.png" alt=""></li>
              </ul>
            </li>
        </ul>
      </div>
    </div>
    <f-footer></f-footer>
  </div>
</template>

<script>
import {NavTopbar, FFooter} from '@/views/layout/components'
import Search from '@/components/search/search'
import {searchByKeywords} from '@/api/home'
import CODES from '@/api/config'

export default {
  components: {
    NavTopbar,
    Search,
    FFooter
  },
  data () {
    return {
      productsData: []
    }
  },
  activated () {
    var keyword = this.$route.query.keyword
    searchByKeywords(keyword).then(res => {
      const {data} = res
      if (CODES.SUCCESS == data.code) {
        this.productsData = data.data
      }
    })
  }
}
</script>

<style lang="scss">
.search-page{
  .f_main{
    .sproducts-wall{
      display: flex;
      flex-wrap: wrap;
      width: 10.4rem;
      margin: 0 auto;
      .product{
        width: 2.35rem;
        margin: 0.12rem 0.1rem;
        .info{
          cursor: pointer;
          display: flex;
          flex-direction: column;
          img{
            width: 2.35rem;
          }
          p{padding-left: 0.1rem;margin:0.05rem 0;}
          .price{
            color: #c40000;
            font-size: 0.2rem;

          }

        }
        .statics{
          display: flex;
          padding-left: 0.1rem;
          justify-content: center;
          border-top:1px solid #eee;
          li{
            text-align: center;
            padding: 0.05rem 0;
            color: #999;
            &+li{
              border-left:1px solid #eee;
            }
          }
          .sale,.review{flex:1;}
          .wangwang{width: 0.4rem;}
          .sale>span{color:#b57c5b;}
          .review>span{color:#3388bb;}
        }
        &:hover{
          outline: 3px solid #c40000;
          .info>img{opacity: 0.5;}
        }
      }

    }
  }
}
</style>
