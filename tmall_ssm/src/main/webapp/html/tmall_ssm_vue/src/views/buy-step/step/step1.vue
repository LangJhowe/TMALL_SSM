<template>
  <div class="step1" >
    <div class="m_container">
      <div class="form-box">
        <h2>输入收货地址</h2>
      <el-form ref="orderForm" class="order-form" :model="orderForm" :rules="rules" label-width="100px" size="small" label-position="left">
        <el-form-item label="详细地址" required prop="address">
          <el-input type="textarea" v-model="orderForm.address" placeholder="建议您如实填写详细收货地址，例如接到名称，门牌号码，楼层和房间号等信息"></el-input>
        </el-form-item>
        <el-form-item label="邮政编码" required prop="post">
          <el-input v-model="orderForm.post" placeholder="如果您不清楚邮递区号，请填写000000"></el-input>
        </el-form-item>
        <el-form-item label="收货人姓名" required prop="receiver">
          <el-input v-model="orderForm.receiver" placeholder="长度不超过25个字符"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" required prop="mobile">
          <el-input v-model="orderForm.mobile" placeholder="请输入11位手机号码"></el-input>
        </el-form-item>
      </el-form>
      </div>

      <div class="product-list">
        <h2>确认订单信息</h2>
        <el-table
        :data="orderList">
          <el-table-column
            prop="name"
            :render-header="renderHeader"
            width="600px">
            <template slot-scope="scope">
              <div class="product-box">
                <img :src="`/tmall_ssm/img/productSingle_middle/${scope.row.imgId}.jpg`" alt="">
                <div class="info">
                  <p>{{scope.row.name}}</p>
                  <ul class="ensure">
                    <li><img src="/static/img/creditcard.png" alt="" title="支持信用卡支付"></li>
                    <li><img src="/static/img/7day.png" alt="" title="消费者保障服务,承诺7天退货"></li>
                    <li><img src="/static/img/promise.png" alt="" title="消费者保障服务,承诺如实描述"></li>
                  </ul>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="price"
            label="单价"
            align="center">
            <template slot-scope="scope">
              <span>￥ </span>{{formatPrice(scope.row.price)}}
            </template>
          </el-table-column>
          <el-table-column
            prop="number"
            label="数量"
            align="center">
          </el-table-column>
          <el-table-column
            prop="count"
            label="小计"
            align="center">
            <template slot-scope="scope">
              <span style="color:#c40000;font-weight:bold;">￥{{formatPrice(scope.row.count)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="way"
            label="配送方式"
            width="320px"
            align="center">
            <template slot-scope="scope">
              <div class="delivery-way">
                <el-checkbox v-model="scope.row.normalDelivery" size="small">普通配送</el-checkbox>
                <el-select v-model="scope.row.deliveryWay" size="small">
                  <el-option label="快递 免邮费" value="1"></el-option>
                </el-select>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="more">
          <div class="leaveMsg">
            <span>给店家留言:</span><textarea v-model="orderForm.userMessage" name="leaveMsg" id="" placeholder="字数没做限制"></textarea>
          </div>
          <div class="sum">
            <p>店铺合计(含运费): <span>￥{{formatPrice(sum)}}</span></p>
          </div>
        </div>
      </div>
      <div class="submit">
        <div>
          <p>实付款: <strong class="actuallyPrice">￥{{formatPrice(sum)}}</strong></p>
          <button class="applyOrder red-btn" @click="applyOrder()">提交订单</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {formatPrice} from '@/util'
import {getUser} from '@/util/auth'

import {getOrderItem, createOrder} from '@/api/user'
export default {
  data () {
    return {
      orderForm: {
        uid: getUser() ? getUser().id : '',
        address: '',
        post: '',
        receiver: '',
        mobile: '',
        userMessage: ''
      },
      rules: {
        address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
        post: [{ required: true, message: '请输入邮政编码', trigger: 'blur' }],
        receiver: [{ required: true, message: '请输入收货人姓名(不超过25个字符)', max: 25, trigger: 'blur' }],
        mobile: [{ required: true, message: '请输入详手机号码(11位)', max: 11, trigger: 'blur' }]
      },

      orderList: []
    }
  },
  computed: {
    sum () {
      var sumPrice = 0
      this.orderList.forEach(item => {
        sumPrice += parseFloat(item.count)
      })
      return sumPrice
    },
    oiidList () {
      return this.$store.getters.cartList
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    onSubmit () {
      return false
    },
    getList () {
      if (this.$store.getters.cartList == null) return
      var orderItemList = this.$store.getters.cartList
      // var orderItemList = [{oiid: 30}]
      getOrderItem(orderItemList).then(res => {
        const { data } = res
        if (this.$CODES.SUCCESS == data.code) {
          this.orderList = data.data.map(item => {
            return {
              id: item.id,
              pid: item.pid,
              name: item.product.name,
              price: item.product.promotePrice,
              number: item.number,
              count: item.product.promotePrice * item.number,
              imgId: item.product.firstProductImage.id,
              normalDelivery: true,
              deliveryWay: '1'
            }
          })
        }
      })
    },
    renderHeader (h, { column, $index }) {
      return (
        <div class="table-name">
          <img src="/static/img/tmallbuy.png" alt=""/>
          <a class="store" href="javascript:void(0)">店铺: 天猫店铺</a>
          <a class="wangwangLink" href="javascript:void(0)"><span class="wangwangGif"></span></a>
        </div>
      )
    },
    formatPrice (num) {
      return formatPrice(num, 2)
    },
    applyOrder () {
      this.orderForm.orderList = this.orderList

      if (getUser() && getUser().id) {
        this.$refs.orderForm.validate(valid => {
          if (valid) {
            createOrder(this.orderForm).then(res => {
              const {data} = res
              if (this.$CODES.SUCCESS == data.code) {
                this.$store.dispatch('setOidList', data.data)
                this.$router.push({path: '/buyStep/stepPay', query: {total: data.total}})
              }
            })
          }
        })
      }
    }
  },
  watch: {
    'route' (to, from) {
      // if (this.$route.path == to.path)
      this.getList()
    }
  }

}
</script>

<style lang="scss">
.step1{
  .order-form{
    width: 6rem;
    margin-left: 0.5rem;
  }
  .el-table{
    border-bottom: 3px solid #b2d1ff;
    .is-leaf{
      padding-bottom:0;color:#333;
      border-bottom: 3px solid #b2d1ff;
    }
    .table-name{
      display: flex;
      height: 0.23rem;
      line-height: 0.23rem;
      img{height:100%;}
      .store{
        margin: 0 5px;
        &:hover{text-decoration: underline;}
      }
    }
    .product-box{
      display: flex;
      img{width: 50px;height:50px;}
      .info{
        p{margin:0;}
        .ensure{
          display: flex;
          li>img{height: 16px;width: auto;margin: 0 2px;}
        }
      }

    }
  }
  .more{
    display: flex;
    justify-content: space-between;
    background: #f2f6ff;
    padding: 0.3rem;
    color: #999;
    .leaveMsg{
      display: flex;
      textarea{
        margin-left: 0.1rem!important;
        height: 0.6rem!important;
        width: 3rem!important;
        resize: none;
      }
    }
  }
  .submit{
    display: flex;
    justify-content: flex-end;
    padding:0.3rem;
    p{
      color:#999;
      .actuallyPrice{
        font-size: 0.22rem;
        color:#c40000;
      }
    }
    .applyOrder{
      width: 1.8rem;
      height:0.4rem;
      font-weight: bold;
      font-size: 14px;
    }
  }
}
</style>
