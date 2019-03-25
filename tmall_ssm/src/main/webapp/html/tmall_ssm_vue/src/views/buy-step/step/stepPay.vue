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
import CODES from '@/api/config'
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
      var form = {total: this.total, oid: this.oid}
      confirmPay(form).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.$router.push({path: '/buyStep/stepPayed', query: form})
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
