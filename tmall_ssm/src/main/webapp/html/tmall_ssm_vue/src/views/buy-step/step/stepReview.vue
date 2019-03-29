<template>
  <div class="step-review">
    <div class="m_container">
      <div class="review-box" v-if="orderData.id">
        <div class="product-info" >
          <div class="p-img">
            <img :src="`/img/productSingle/${orderData.orderItems[0].product.firstProductImage.id}.jpg`" alt="">
          </div>
          <div class="p-main">
            <p class="name">{{orderData.orderItems[0].product.name}}</p>
            <p class="price">
              <span class="item">价格：</span> <strong>￥{{formatPrice(orderData.orderItems[0].product.promotePrice)}}</strong>元
            </p>
            <p class="delivery">
              <span class="item">配送：</span> <span>快递:0.00</span>
            </p>
            <p class="sales">
              <span class="item">月销量：</span> <strong>{{orderData.orderItems[0].product.saleCount}}</strong>件
            </p>
            <div class="buy-msg">
                <p><i class="reviewProductInfoRightBelowImg"></i><span>现在查看的是 您所购买商品的信息</span></p>
                <ul>
                  <li>于{{formatDateCN(orderData.createDate)}}下单购买了此商品</li>
                  <li>一共购买了{{orderData.totalNumber}}件</li>
                  <li>商品描述——{{orderData.orderItems[0].product.subTitle}}</li>
                </ul>
            </div>
          </div>
        </div>
        <div v-if="onlyReview" class="all-reviews">
          <div class="brow">
            <div class="top"></div>
            <p>累计评价 <span>{{orderData.orderItems[0].product.reviewCount}}条</span></p>
            <div class="line"></div>
          </div>
          <ul class="review-list" >
            <li v-for="r in reviewList" :key="r.id">
              <p>
                <span class="review-time">{{formatDAT(r.createDate)}}</span>
                <span class="review-content">{{r.content}}</span>
                <span class="review-user">{{r.username}} <span class="anonymous">(匿名)</span></span>
              </p>
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
        </div>
        <div v-else class="review-board">
          <div class="main">
            <div class="inner">
                <p>其他买家,需要哦你的建议哦！</p>
                <div class="text">
                <label class="name" for="review">评价商品</label>
                <div class="value">
                  <textarea v-model="review" name="review" id="review" cols="30" rows="10"></textarea>
                </div>
              </div>
            </div>
            <div class="submit-review">
              <button @click="submitReview()">提交评价</button>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getPayedOrder, review} from '@/api/user'
import {getReviews} from '@/api/home'
import {formatPrice, formatDateCN, formatDAT} from '@/util'

export default {
  data () {
    return {
      orderData: {},
      review: '',
      reviewList: [],

      pager: {
        total: 1,
        page: 1
      }
    }
  },
  computed: {
    oid () {
      return this.$route.query.oid
    },
    onlyReview () {
      if (!this.$route.query.onlyReview) return false
      return this.$route.query.onlyReview
    },
    pid () {
      return this.$route.query.pid
    }
  },
  mounted () {
    this.getPayedOrder()
    this.getReviews()
  },
  methods: {
    submitReview () {
      review({'oid': this.oid, 'review': this.review}).then(res => {
        const {data} = res
        if (this.$CODES.SUCCESS == data.code) {
          this.reviewList = data.data
          var pid = data.pid
          this.$router.push({path: '/buyStep/stepReview', query: {oid: this.oid, pid: pid, onlyReview: true}})
        }
      })
    },
    getReviews () {
      var pid = this.pid
      if (!pid || !this.onlyReview) return
      getReviews({pid: this.pid, page: this.pager.page}).then(res => {
        const {data} = res
        if (this.$CODES.SUCCESS == data.code) {
          this.pager.total = data.total
          this.reviewList = data.data.map(item => {
            return {createDate: item.createDate, content: item.content, username: item.user.name}
          })
        }
      })
    },
    getPayedOrder () {
      getPayedOrder({'oid': this.oid}).then(res => {
        const {data} = res
        if (this.$CODES.SUCCESS == data.code) {
          this.orderData = data.data
        }
      })
    },
    pagerChange (page) {
      this.pager.page = page
      this.getReviews()
    },
    formatPrice (price) {
      if (!price) return '0.00'
      return formatPrice(price, 2)
    },
    formatDateCN (time) {
      return formatDateCN(time)
    },
    formatDAT (time) {
      return formatDAT(time)
    }
  },
  watch: {
    '$route' () {
      console.log('change')
      if (this.$route.query.hasOwnProperty('onlyReview') && this.$route.query.onlyReview) {
        this.getReviews()
        this.getPayedOrder()
      }
    }
  }
}
</script>

<style lang="scss">
@import "@/common/style/variable.scss";
.step-review{
  p{margin: 0;}
  .review-box{
    .product-info{
      display: flex;
      .p-img{
        border:1px solid #ddd;
        width: 4.64rem;
        text-align: center;
        img{width: 4rem;height:4rem;}
      }
      .p-main{
        color:#999;
        border-top:1px solid #ddd;
        padding:0 0.3rem;
        .name{
          margin:0.2rem 0;
          font-size: 0.16rem;
          font-weight: bold;
        }
        p>.item{
          display: inline-block;
          width: 0.8rem;
          height: 0.2rem;
          line-height: 0.2rem;
        }
        .price>strong{color:#c40000;font-size: 0.2rem;font-weight: bold}
        .sales>strong{color:#c40000;font-size: 0.14rem;font-weight: bold}
        .buy-msg{
          background: #FDFBFA;
          border: 1px solid #F6F5F3;
          min-height: 2.5rem;
          margin-top: 0.2rem;
          p{
            border: 1px solid #E1E1E1;
            display: flex;
            font-size: 0.2rem;
            background: #FDFBFA;
            color: #f6c87f;
            span{line-height: 0.42rem;}
          }
          ul{
            padding: 0.05rem;
            li{
              margin: 0 0.2rem;
              list-style-type:decimal;
            }
          }
        }
      }
    }
    .all-reviews{
      margin-bottom:0.2rem;
      .brow{
        .top{width: 1.8rem;height: 4px;background: #c40000}
        .line{
          height: 15px;
          background: #f6f5f1;
          border: 1px solid #D5D4D4;

          }
        p{
          width: 1.8rem;
          background: #f6f5f1;
          text-align: center;
          font-size: 0.14rem;
          font-weight: bold;
          padding:0.1rem 0;
          border-left: 1px solid #D5D4D4;
          border-right: 1px solid #D5D4D4;
          position: relative;
          top:2px;
          span{color:#284ca5}
        }
      }
      .review-list{
        li{
          padding:0.2rem;
          border-bottom:1px solid #ccccdd;
          p{
            display: flex;
            .review-time{color:#ccccdd;width: 2rem;}
            .review-content{flex:1}
            .review-user{
              .anonymous{
                color:#ccccdd
              }
            }
          }
        }
      }
    }
    .review-board{
      margin-top:0.2rem;
      .main{
        margin-top:0.2rem;
        border: 1px solid #D5D4D4;
        border-bottom:none;
        background:#f6f5f1;
        .inner{
          padding: 0.3rem 0.5rem;
        }
        p{font-size: 0.16rem;font-weight: bold;margin-bottom:0.2rem}
        .text{
          display: flex;
          align-items:center;
          height: 1.5rem;
          position: relative;
          .name{
            margin:0;
            width: 0.8rem;
            height: 100%;
            padding: 0 0.1rem;
            line-height: 1.5rem;
            background: #fff;
            border:1px solid #ddd;
          }
          .value{
            height: 100%;
            width: 4.5rem;
            background: #fff;
            border:1px solid #ddd;
            border-left:none;
            padding:0.1rem;
            textarea{
              width: 4.3rem;
              height: 1.3rem;
              resize: none;
              border: none;
              // width: ;
            }
          }
        }
        .submit-review{
          border: 1px solid #D5D4D4;
          border-top:none;
          background:#fff;
          padding: 0.2rem 0;
          @include flexCenter();
          button{
            width: 72px;
            height: 26px;
            border-radius: 2px;
            background-color: #C40000;
            color: white;
            border-width: 0px;
            font-weight: bold;
          }
        }
      }

    }
  }

}
</style>
