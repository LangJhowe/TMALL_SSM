<template>
  <div class="products-wall">
    <div class="m_container">
      <div v-for="r in categoryDatas" :key="r.name" class="category-row">
        <div class="category-title"><h2>{{r.name}}</h2></div>
        <ul class="category-board">
          <li v-for="(item, index) in r.products" v-if="index<5" :key="item.name" class="product-item">
            <product-card :item="item"></product-card>
          </li>
        </ul>
      </div>
      <div class="endpng">
        <img src="/static/img/end.png" alt="">
      </div>
    </div>

  </div>
</template>

<script>
import ProductCard from '@/components/product-card/product-card'
import {getProductsByCategory} from '@/api/home.js'
import CODES from '@/api/config'
export default {
  components: {
    ProductCard
  },
  data () {
    return {
      categoryDatas: []
    }
  },
  activated () {
    getProductsByCategory().then((res) => {
      const {data} = res
      if (data.code == CODES.SUCCESS) {
        this.categoryDatas = data.data
      }
    })
  }
}
</script>

<style lang="scss" scoped>
.products-wall{
  margin-top: 0.1rem;
  .m_container{
    background: #f5f5f5;
    padding: 0.3rem 0.2rem;
    .category-row{

    }
    .category-title{
      h2{
        padding-left: 0.2rem;
        font-size: 0.16rem;
        font-weight: bolder;
        position: relative;
        &::before{
          content: '';
          position: absolute;
          height: 100%;
          width: 5px;
          left: 0;
          background: lightcoral;
        }
      }

    }
    .category-board{
      display: flex;
      justify-content: space-between;
      .product-item{
        width: 2.2rem;
      }
    }
    .endpng{
      display: flex;
      padding-top: 0.3rem;
      justify-content: center;
    }
  }

}
</style>
