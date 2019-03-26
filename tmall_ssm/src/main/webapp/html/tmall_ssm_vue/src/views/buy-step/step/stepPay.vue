<template>
  <div class="step-pay">
    <div class="m_container">
      <p class="tips">扫一扫付款(元)</p>
      <p class="confirm-price"><strong>￥{{total}}</strong></p>
      <img class="QRCode" src="/static/img/alipay2wei.png" alt="">
      <button class="confirm-btn" @click="confirmPay()">确认付款</button>
    </div>
  </div>
</template>
<script>
import {confirmPay} from '@/api/user'
import {formatPrice} from '@/util'
export default {
  computed: {
    total () {
      return formatPrice(this.$route.query.total, 2)
    },
    oid () {
      return this.$route.query.oid
    }
  },
  methods: {
    confirmPay () {
      var oidList = this.$store.getters.oidList
      confirmPay(oidList).then(res => {
        const {data} = res
        var total = data.data.total
        var address = data.data.address
        var totalNumber = data.data.totalNumber
        if (this.$CODES.SUCCESS == data.code) {
          this.$router.push({path: '/buyStep/stepPayed', query: {total: total, address: address}})
          this.$store.dispatch('reduceCartNum', totalNumber)
        }
      })
    }
  }
}
</script>

<style lang="scss">
.step-pay{
  .m_container{
    color:#4d4d4d;
    display: flex;
    flex-direction: column;
    align-items: center;
    .confirm-price{
      font-size: 0.2rem;
      color: #ff6600;
    }
    .confirm-btn{
      color:#fff;
      font-size: 0.14rem;
      font-weight: bold;
      background:#00aaee;
      width: 1.07rem;
      height: 0.35rem;
      margin: 0.3rem 0 0.5rem 0;
    }
  }
}
</style>
