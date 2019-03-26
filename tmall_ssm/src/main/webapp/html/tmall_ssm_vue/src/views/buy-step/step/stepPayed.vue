<template>
  <div class="step-payed">
    <div class="m_container">
      <div class="text">
        <img src="/static/img/paySuccess.png" alt=""><span>你已成功付款</span>
      </div>
      <div class="order-info">
        <ul>
          <li class="address">收货地址: <strong>{{address}}</strong></li>
          <li class="total">实付款:<strong> ￥{{formatPrice(total)}}</strong></li>
          <li class="arrivalDate">预计08月08日送达</li>
        </ul>
        <div class="other-link">
          <span>您也可以</span> <router-link :to="{path:'/buyStep/bought',}">查看已买到的宝贝</router-link> <router-link to="/buyStep/bought">查看交易详情</router-link>
        </div>
      </div>
      <div class="safe-tips">
        <span class="warn"><img src="/static/img/warning.png" alt="">安全提醒</span> 下单后,<strong>用QQ给您发送链接办理退款的都是骗子！</strong><span>天猫不存在系统升级，订单异常等问题，谨防假冒客服电话诈骗！</span>
      </div>
    </div>
  </div>
</template>

<script>
import {formatPrice} from '@/util'
export default {
  data () {
    return {
    }
  },
  computed: {
    total () {
      return this.$route.query.total
    },
    address () {
      return this.$route.query.address
    }
  },
  methods: {
    formatPrice (num) {
      return formatPrice(num, 2)
    }
  }
}
</script>

<style lang="scss">
.step-payed{
  .m_container{
    border:1px solid #ccc;
    margin-bottom: 0.2rem;
    .text{
      display: flex;
      align-items: center;
      background: #ecffdc;
      height: 0.6rem;
      padding: 0.25rem;
      span{margin-left:0.1rem;font-size: 0.14rem;font-weight: bold;}
    }
    .order-info{
      padding: 0.2rem 0.3rem;
      ul{margin: 0 0.5rem;}
      li{list-style-type: disc;padding:0.05rem 0.05rem;}
      .address>strong{font-size: 0.14rem;color:#000;}
      .total>strong{font-size: 0.14rem;color:#c40000;}

      .other-link{
        padding: 0.05rem 0.35rem 0.3rem;
        border-bottom: 1px dotted #999;
        a{
          color:#2d8cba;
          &:hover{text-decoration: underline;}
        }
      }
    }
    .safe-tips{
      padding:0.15rem 0.25rem 0.25rem;
      .warn{font-weight: bold;}
      strong{color:#c40000;}
    }
  }

}
</style>
