<template>
  <a class="hproduct-card">
    <div class="product-img"><img :src="`/img/productSingle/${item.firstProductImage.id}.jpg`" alt=""></div>
    <div class="product-name">{{item.name}}</div>
    <div class="product-price"><span class="unit">￥</span>{{item.promotePrice}}</div>
  </a>
</template>

<script>
export default {
  props: {
    item: Object
  }
}
</script>

<style lang="scss" scoped>
.hproduct-card{
  display: block;
  cursor: pointer;
  background: #fff;
  &:hover{
    outline: 3px solid red;
  }
  .product-img{
    img{
      display: block;
      width: 100%;
      height: 1.9rem;
      &:hover{
        opacity: 0.5;
      }
    }
  }
  .product-name{
    padding: 0.1rem;
    word-break: break-all;
    // .span{
      // overflow:hidden;
      // text-overflow:ellipsis;
      // display:-webkit-box;
      // -webkit-box-orient:vertical;
      // -webkit-line-clamp:2;
    // }

  }
  .product-price{
    padding: 0.05rem 0.1rem;
    color: red;
    font-size: 0.18rem;
    .unit{

    }
  }
}
</style>
