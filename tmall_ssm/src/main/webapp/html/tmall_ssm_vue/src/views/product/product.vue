<template>
  <div class="product-page">
    <div class="m_container">
      <div class="category-img">
        <img
          v-if="productData.cid&&productData.cid!=0"
          :src="`http://localhost:8080/tmall_ssm/img/category/${productData.cid}.jpg`"
          alt=""
        >
      </div>
      <div class="product-wrap">
        <div class="imgs-info">
          <div class=p-imgs>
            <img
              v-if="choosenImgId&&choosenImgId!=0"
              class="bigImg"
              :src="`http://localhost:8080/tmall_ssm/img/productSingle/${choosenImgId}.jpg`"
              alt=""
            >
            <ul class="smallImgs">
              <li
                v-for="img in productData.productSingleImages"
                :key="img.id"
                @click="selectBigImg(img.id)"
              >
                <img
                  :src="`http://localhost:8080/tmall_ssm/img/productSingle_small/${img.id}.jpg`"
                  alt=""
                />
              </li>
            </ul>
          </div>
          <div class="p-info">
            <h2 class="title">{{productData.name}}</h2>
            <p class="subTitle">{{productData.subTitle}}</p>
            <div class="price-box">
              <div class="juhuasuan">
                <p><strong class="juhuasuanBig">聚划算</strong>此商品即将参加聚划算<strong class="remain-time">1天19小时</strong>后开始,</p>
              </div>
              <div class="product-price">
                <ul class="gouwujuans">
                  <li class="gwj"><img
                      src="/static/img/gouwujuan.png"
                      alt=""
                    ><span>全天猫实物商品通用</span></li>
                </ul>
                <p class="origin-price"><span class="text">价格</span>￥<span class="value">{{fOriginPrice}}</span></p>
                <p class="promote-price"><span class="text">促销价</span><span class="unit">￥</span><strong class="value">{{fPromotePrice}}</strong></p>

              </div>
            </div>
            <div class="counts">
              <p class="sale-count"><span class="text">销量</span><strong class="value">{{productData.saleCount}}</strong></p>
              <p class="review-count"><span class="text">累计评价</span><strong class="value">{{productData.reviewCount}}</strong></p>
            </div>
            <div class="numbers">
              <span>数量</span>
              <el-input-number
                v-model="buyForm.num"
                controls-position="right"
                @change="handleChange"
                :min="1"
                :max="productData.stock"
                size="small"
              ></el-input-number>
              <span>件 库存{{productData.stock}}件</span>
            </div>
            <div class="commit">
              <span class="text">服务承诺</span>
              <ul>
                <li>正品保证</li>
                <li>极速退款</li>
                <li>赠运费险</li>
                <li>七天无理由退款</li>
              </ul>
            </div>
            <div class="buy">
              <button
                class="buy-now"
                @click="buyNow()"
              >立即购买</button>
              <button
                class="add-cart"
                @click="addToCart()"
              ><span class="glyphicon glyphicon-shopping-cart"></span>加入购物车</button>
            </div>
          </div>
        </div>
        <el-tabs
          class="tabs"
          type="card"
          v-model="currentTab"
        >
          <el-tab-pane
            label="商品详情"
            name="productDeatil"
          >
            <div class="product-property">
              <div class="title">产品参数:</div>
              <el-row>
                <el-col :span="8" v-for="property in productPropertys" :key="property.id">
                  <span class="text">{{property.property.name}}:  </span><span class="value">{{property.value}}</span>
                </el-col>
              </el-row>
            </div>
            <ul class="product-detail-imgs">
              <li v-for="detailImg in productData.productDetailImages" :key="detailImg.id">
                <img :src="`http://localhost:8080/tmall_ssm/img/productDetail/${detailImg.id}.jpg`" alt="">
              </li>
            </ul>
          </el-tab-pane>
          <el-tab-pane
            :label="`累计评价 ${productData.reviewCount}`"
            name="review"
          >
            <ul class="reviews">
              <li class="review" v-for="r in productReviews" :key="r.id">
                <p class="content">{{r.content}}</p>
                <p class="info"><span>{{formatDate(r.createDate)}}</span> <span>{{r.user.name}}(匿名)</span></p>
              </li>
            </ul>
            <div class="pager">
              <el-pagination
                layout="prev, pager, next"
                :total="pager.total"
                :current-page="pager.page"
                @current-change="pagerChange">
              </el-pagination>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <el-dialog
      :visible="loginBoxOpen"
      @close="loginBoxOpen=false">
      <login-box @loginSuccess="handleLoginSuccess"></login-box>
    </el-dialog>
  </div>
</template>

<script>
import { NavTopbar } from '@/views/layout/components'
import LoginBox from '@/components/login-box/login-box'
import { getProduct, getReviews } from '@/api/home'
import { buyOneProduct, addToCart } from '@/api/user'
import { getUser } from '@/util/auth'
import { formatPrice, formatDate } from '@/util/index'
export default {
  components: {
    NavTopbar,
    LoginBox
  },
  data () {
    return {
      pid: '',
      productData: {},
      productPropertys: [],
      productReviews: [],
      choosenImgId: '0',
      currentTab: 'productDeatil',
      loginBoxOpen: false,

      buyForm: {
        uid: getUser() ? getUser().id : '',
        pid: '',
        num: 1
      },

      pager: {
        total: 1,
        page: 1
      }
    }
  },
  computed: {
    isLogin () {
      if (getUser()) return true
      return false
    },
    fPromotePrice () {
      return formatPrice(this.productData.promotePrice, 2) || '0.00'
    },
    fOriginPrice () {
      return formatPrice(this.productData.originalPrice, 2) || '0.00'
    }
  },
  mounted () {
    this.pid = this.$route.query.pid
    this.buyForm.pid = this.$route.query.pid
    this.getProductData()
    this.getProductReview()
  },
  methods: {
    getProductData () {
      getProduct(this.pid).then(res => {
        const { data } = res
        if (this.$CODES.SUCCESS == data.code) {
          this.productData = data.data.data
          this.productPropertys = data.data.propertys
          this.choosenImgId = data.data.data.productSingleImages[0].id
        }
      })
    },
    getProductReview () {
      getReviews({'pid': this.pid, 'page': this.pager.page}).then(res => {
        const { data } = res
        if (this.$CODES.SUCCESS == data.code) {
          this.productReviews = data.data
          this.pager.total = data.total
        }
      })
    },
    handleChange () {},
    pagerChange (page) {
      this.pager.page = page
      this.getProductReview()
    },
    buyNow () {
      if (!this.needOpenLoginBox()) {
        buyOneProduct(this.buyForm).then(res => {
          const {data} = res
          if (data.code == this.$CODES.SUCCESS) {
            var oiid = data.oiid
            var orderItemList = [{oiid: oiid}]
            this.$store.dispatch('setCartList', orderItemList)
            this.$router.push({path: '/buyStep/step1'})
          }
        })
      }
    },
    addToCart () {
      if (!this.needOpenLoginBox()) {
        addToCart(this.buyForm).then(res => {
          const {data} = res
          if (data.code == this.$CODES.SUCCESS) {
            var num = getUser().cartNum + this.buyForm.num
            console.log('total' + num)
            this.$store.dispatch('setCartNum', num)
            this.$notify({
              type: 'success',
              message: `成功添加${this.buyForm.num}件商品`
            })
          }
        })
      }
    },
    formatDate (date) {
      return formatDate(date)
    },
    selectBigImg (id) {
      this.choosenImgId = id
    },
    needOpenLoginBox () {
      if (getUser() == null) {
        this.loginBoxOpen = true
        return true
      }
      return false
    },
    handleLoginSuccess (data) {
      this.loginBoxOpen = false
      this.buyNow()
    }
  },
  watch: {
    $route (to, from) {
      if (!to.query.hasOwnProperty('pid')) return
      if (this.pid == from.query.pid) return
      this.pid = this.$route.query.pid
      this.buyForm.pid = this.$route.query.pid
      this.buyForm.num = 1
      this.getProductData()
      this.getProductReview()
    }
  }
}
</script>

<style lang="scss">
.product-page {
  .m_container {
    width: 9.4rem;
  }
  .category-img {
    display: flex;
    justify-content: center;
    img {
      width: 10.4rem;
      height: 1.15rem;
    }
  }
  .product-wrap {
    margin-top: 0.4rem;
    color: #999;
    .imgs-info {
      display: flex;
      margin-bottom: 0.3rem;
      .p-imgs {
        .bigImg {
          display: block;
          width: 4.18rem;
          height: 4.18rem;
          border: 1px solid rgba(0, 0, 0, 0.05);
        }
        .smallImgs {
          flex: 1;
          display: flex;
          justify-content: center;
          li {
            margin: 0.1rem 0.1rem;
            &:hover,
            &.selected {
              outline: 2px solid #404040;
            }
          }
        }
      }
      .p-info {
        padding: 0 0.2rem;
        font-size: 0.12rem;
        .title {
          margin: 0 0.05rem;
          font-size: 0.16rem;
          font-weight: bold;
        }
        .subTitle {
          margin: 0.05rem;
          color: #dd2727;
        }
        .price-box {
          .juhuasuan {
            display: flex;
            justify-content: center;
            align-items: center;
            background: #2da77a;
            color: #fff;
            height: 0.42rem;
            p {
              margin: 0;
              .juhuasuanBig {
                font-size: 0.18rem;
              }
              .remain-time {
                color: #ffc057;
              }
            }
          }
          .product-price {
            background: url("/static/img/priceBackground.png") no-repeat;
            padding: 0.1rem;
            .gouwujuans {
              margin-top: 0.05rem;
              .gwj {
                img {
                  width: 0.56rem;
                }
              }
            }
            p[class$="-price"] {
              margin-top: 0.1rem;
              .text {
                display: inline-block;
                width: 0.68rem;
              }
              &.origin-price > .value {
                text-decoration: line-through;
              }
              &.promote-price {
                .text {
                  position: relative;
                  top: -0.1rem;
                }
                .unit {
                  font-size: 0.18rem;
                  color: #c40000;
                }
                .value {
                  font-size: 0.3rem;
                  color: #c40000;
                }
              }
            }
          }
        }
        .counts {
          display: flex;
          justify-content: center;
          color: #999;
          padding: 0.1rem 0;
          margin: 0.2rem 0;
          border-top: 1px dotted #999;
          border-bottom: 1px dotted #999;
          p {
            flex: 1;
            text-align: center;
            margin: 0;
            .value {
              color: #c40000;
            }
          }
          .sale-count {
            border-right: 1px solid #999;
          }
        }
        .commit {
          display: flex;
          margin: 0.2rem 0;
          ul {
            display: flex;
            color: #333;
            li {
              margin: 0 0.05rem;
            }
          }
        }
        .buy {
          display: flex;
          justify-content: center;
          button {
            width: 1.8rem;
            height: 0.4rem;
            font-size: 0.16rem;
            &:active {
              opacity: 0.5;
            }
          }
          .buy-now {
            color: #c40000;
            background: #ffeded;
            border: 1px solid #c40000;
          }
          .add-cart {
            color: #fff;
            background: #c40000;
            margin-left: 0.3rem;
            border: 1px solid transparent;
            .glyphicon {
              margin-right: 0.05rem;
            }
          }
        }
      }
    }
    .product-property{
      padding:0.3rem 0.4rem;
      border: 1px solid rgb(228,231,237);
      border-top:none;
      .title{
        margin: 0.2rem 0;
        font-weight: bold;
      }
      .el-col{
        margin: 0.05rem 0;
      }
    }
    .product-detail-imgs{
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    .reviews{
      padding-top: 0.4rem;
      .review{
        padding: 0.3rem 0.2rem;
        .content{
          color: #000;
        }
        .info{
          display: flex;
          justify-content: space-between;
        }
        &+li{
          border-top:1px solid rgb(228,231,237);
        }
      }
    }
    .el-tabs {
      .el-tabs__header{margin:0;}
      .el-tabs__nav {
        border-top-left-radius: 0;
        border-top-right-radius: 0;
        &hover {
          color: #c40000;
        }
        .el-tabs__item {
          position: relative;
          &:hover {
            color: #c40000;
          }
          &.is-active {
            color: #c40000;
            font-weight: bold;
            border-top: 1px solid #c40000;
          }
          &.is-active::after {
            content: "";
            position: absolute;
            width: 0;
            height: 0;
            border: 1px solid transparent;
            border-width: 5px;
            border-top-color: #c40000;
            left: 50%;
            transform: translateX(-50%);
          }
        }
      }
    }
  }
  .el-dialog{
    width:min-content;
    .login-box{margin-right:0;}
  }
}
</style>
