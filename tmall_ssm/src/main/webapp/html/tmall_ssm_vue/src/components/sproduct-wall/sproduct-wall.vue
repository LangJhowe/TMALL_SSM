<template>
  <div class="sproducts-wall">
    <div class="m_container">
      <div
        class="category-img"
        v-if="isCategory&&cid!=''"
      >
        <img

          :src="`/img/category/${cid}.jpg`"
          alt=""
        >
      </div>
      <div
        v-if="needSort"
        class="sorts"
      >
        <ul class="click-sort">
          <li
            v-for="sort in sortItems"
            :key="sort.name"
            :class="{active: selectedSort == sort.sort}"
            @click="chooseSort(sort)"
          >
            <span>{{sort.name}}</span><span class="glyphicon glyphicon-arrow-down"></span>
          </li>
        </ul>
        <div class="price-sort clearfix">
          <input
            class="fl"
            type="number"
            v-model.lazy="priceSelected.min"
            placeholder="请输入"
            @change="fillPrice"
          >
          <span class="splash fl">-</span>
          <input
            class="fl"
            type="number"
            placeholder="请输入"
            v-model.lazy="priceSelected.max"
            @change="fillPrice"
          >
        </div>
      </div>
      <ul class="products-wrap">
        <li
          class="product"
          v-for="p in productsData"
          @click="toProductPage(p.id)"
          :key="p.id"
        >
          <div class="info">
            <img
              :src="`/img/productSingle/${p.firstProductImage.id}.jpg`"
              alt=""
            >
            <p class="price">￥ {{p.promotePrice}}</p>
            <p>{{p.name}}</p>
            <p><a
                href="javascript:void(0)"
                style="text-decoration:underline"
              >天猫专卖</a></p>
          </div>
          <ul class="statics">
            <li class="sale">月成交 <span>{{p.saleCount}}笔</span></li>
            <li class="review">评价<span>{{p.reviewCount}}</span></li>
            <li class="wangwang"><img
                src="/static/img/wangwang.png"
                alt=""
              ></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    productsData: Array,
    needSort: {
      type: Boolean,
      default: false
    },
    isCategory: {
      type: Boolean,
      default: false
    },
    cid: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      sortItems: [
        { name: '综合', sort: 'all' },
        { name: '人气', sort: 'review' },
        { name: '新品', sort: 'date' },
        { name: '销量', sort: 'saleCount' },
        { name: '价格', sort: 'price' }
      ],
      selectedSort: 'all',
      priceSelected: {
        min: '',
        max: ''
      }
    }
  },
  methods: {
    chooseSort (sort) {
      if (this.selectedSort == sort.sort) {
        this.selectedSort = 'all'
      } else {
        this.selectedSort = sort.sort
      }
      this.$emit('chooseSort', sort.sort)
    },
    fillPrice () {
      this.$emit('fillPrice', this.priceSelected)
    },
    resetSortAndPrice () {
      this.priceSelected.min = ''
      this.priceSelected.max = ''
      this.selectedSort = 'all'
    },
    toProductPage (pid) {
      this.$router.push({path: '/extra/product', query: {pid: pid}})
    }
  }
}
</script>

<style lang="scss">
.category-img {
  img {
    display: block;
    margin: 0 auto;
    width: 10.4rem;
    height: 1.15rem;
  }
}
.sorts {
  width: 10.4rem;
  margin: 0.2rem auto;
  padding: 0.05rem 0;
  display: flex;
  line-height: 0.2rem;
  color: #806f66;
  background: #faf9f9;
  .click-sort {
    display: flex;
    border: 1px solid #ccc;
    li {
      cursor: pointer;
      width: 0.44rem;
      height: 0.22rem;
      padding: 0.03rem;
      & + li {
        border-left: 1px solid #ccc;
      }
      &.active,
      &:hover {
        color: #c40000;
        background: #f1edec;
      }
    }
  }
  .price-sort{
    margin-left: 0.05rem;
    height: 0.23rem;
    input{
      width: 0.5rem;
      height: 100%;
      border:1px solid #ccc;
      // border:1px solid #ccc;
    }
    .splash{
      padding: 0 0.03rem;
      background: #f1edec;
      border-top:1px solid #ccc;
      border-bottom:1px solid #ccc;
      height: 100%;
    }
  }
}
.products-wrap {
  display: flex;
  flex-wrap: wrap;
  width: 10.4rem;
  margin: 0 auto;
  .product {
    width: 2.35rem;
    margin: 0.12rem 0.1rem;
    border: 1px solid #eee;
    .info {
      cursor: pointer;
      display: flex;
      flex-direction: column;
      img {
        width: 2.33rem;
        height:2.33rem;
      }
      p {
        padding-left: 0.1rem;
        margin: 0.05rem 0;
      }
      .price {
        color: #c40000;
        font-size: 0.2rem;
      }
    }
    .statics {
      display: flex;
      padding-left: 0.1rem;
      justify-content: center;
      border-top: 1px solid #eee;
      li {
        text-align: center;
        padding: 0.05rem 0;
        color: #999;
        & + li {
          border-left: 1px solid #eee;
        }
      }
      .sale,
      .review {
        flex: 1;
      }
      .wangwang {
        width: 0.4rem;
      }
      .sale > span {
        color: #b57c5b;
      }
      .review > span {
        color: #3388bb;
      }
    }
    &:hover {
      outline: 3px solid #c40000;
      .info > img {
        opacity: 0.5;
      }
    }
  }
}
</style>
