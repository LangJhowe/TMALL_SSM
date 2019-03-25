<template>
  <div class="step-confirm">
    <div class="container">
      <div class="step-flow">
        <img src="/static/img/comformPayFlow.png" alt="">
        <ul class="times">
          <li class="time">{{formatDAT(orderData.createDate)}}</li>
          <li class="time">{{formatDAT(orderData.payDate)}}</li>
          <li class="time">{{formatDAT(orderData.deliveryDate)}}</li>
        </ul>
      </div>
      <div class="agree">
        <h2>我已收到货，同意支付宝付款</h2>
      </div>
      <div class="order-info">
        <h3>订单信息</h3>
        <el-table :data="orderList">
          <el-table-column label="宝贝" align="center" width="500px">
            <template slot-scope="scope">
              <div class="product-info">
                <img :src="`http://localhost:8080/tmall_ssm/img/productSingle_middle/${scope.row.orderItems[0].product.firstProductImage.id}.jpg`" alt="">
                <router-link :to="{path:'/product',query:{pid:1}}">{{scope.row.orderItems[0].product.name}}</router-link>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="单价" align="center">
            <template slot-scope="scope">
              <div class="product-price">
                <p>￥{{formatPrice(scope.row.orderItems[0].product.promotePrice)}}</p>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="数量" align="center">
            <template slot-scope="scope">
              <div class="product-num">
                <p>{{scope.row.totalNumber}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column width="150px" label="商品总价" align="center">
            <template slot-scope="scope">
              <div class="product-total">
                <p><strong>￥{{formatPrice(scope.row.total)}}</strong></p>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="运费" align="center">
            <template slot-scope="scope">
              <div class="product-delivery">
                <p>快递 ： 0.00</p>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pay-price clearfix">
        <p class="fr">实付款: <strong>￥{{formatPrice(orderData.total)}}</strong></p>
      </div>

      <ul class="other-info">
        <li>
          <span class="info">订单编号：</span>
          <span class="value">{{orderData.orderCode}}<img src="/static/img/confirmOrderTmall.png" alt=""></span></li>
        <li><span class="info">卖家昵称：</span><span class="value">天猫商品<a class="orderItemWangWangGif" href="javascipt:void(0)"></a></span></li>
        <li><span class="info">收货信息：</span><span class="value">，，，</span></li>
        <li><span class="info">成交时间：</span><span class="value">{{formatDAT(orderData.createDate)}}</span></li>
      </ul>

      <div class="confirm">
        <p><strong>请收到货后，再确认收货！否则您可能钱货两空！</strong></p>
        <button @click="confirmPay()">确认支付</button>
      </div>
    </div>
  </div>
</template>

<script>
import {getPayedOrder, finalConfirmPay} from '@/api/user'
import CODES from '@/api/config'
import {formatDAT, formatPrice} from '@/util'

export default {
  data () {
    return {
      orderData: {}
    }
  },
  computed: {
    oid () {
      return this.$route.query.oid
    },
    orderList () {
      if (!this.orderData.id) return
      return [this.orderData]
    }
  },
  mounted () {
    this.getPayedOrder()
  },
  methods: {
    confirmPay () {
      finalConfirmPay({oid: this.oid}).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.$router.push('/buyStep/stepSuccess')
        }
      })
    },
    getPayedOrder () {
      getPayedOrder({'oid': this.oid}).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.orderData = data.data
          console.log(this.orderData)
        }
      })
    },
    formatDAT (time) {
      if (!time) return '卖家还未发货'
      return formatDAT(time)
    },
    formatPrice (price) {
      if (!price) return '0.00'
      return formatPrice(price, 2)
    }
  }
}
</script>

<style lang="scss">
.step-confirm{
  p{margin: 0;}
  .step-flow{
    display: flex;
    flex-direction: column;
    align-items: center;
    .times{
      display: flex;
      flex: 1;
      width: 100%;
      justify-content: flex-start;
      li{width:20%;text-align: center}
      li:nth-of-type(1){text-align: right;position: relative;right: 25px;}
      li:nth-of-type(2){text-align: right;position: relative;right: 40px;}
    }
  }
  .agree{
    border-bottom:1px solid #adc8e6;
    h2{
      font-size: 0.16rem;
      font-weight: bold;
    }
  }
  .order-info{
    h3{font-size: 14px}
  }
  .el-table{
    border: 1px solid #ddd;
    .is-leaf{background:#e8f2ff;border-bottom: 1px solid #ddd}
    .product-info{
      display: flex;
      align-items: center;
      img{width: 0.5rem;height:0.5rem;}
    a{flex:1}
    }
    .product-total{
      p{font-size: 18px;font-weight:bold;}
    }
  }

  .pay-price{
    font-size: 14px;
    font-weight: bold;
    padding:0.2rem 0.1rem;
    strong{color:#c40000;}
  }
  .other-info{
    width: 10.5rem;
    margin: 0 auto;
    padding: 0.1rem;
    font-size: 14px;
    border-top: 1px solid #ddd;
    span{display: inline-block;height:27px;line-height: 27px}
    .info{ width: 2.5rem;}
    a{display: inline-block;vertical-align: bottom}
  }

  .confirm{
    width: 11rem;
    margin: 0.2rem auto;
    border: 1px solid #F58B0F;
    padding: 0.2rem 1rem;
    color: red;
    p{font-size: 0.18rem}
    button{
      display: inline-block;
      margin-top: 0.2rem;
      width: 70px;
      height: 30px;
      border: 1px solid #E67C00;
      background-color: #F4A21D;
      border-radius: 4px;
      color: white;
    }
  }
}
</style>
