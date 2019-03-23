<template>
  <div class="bought">
    <div class="m_container">
      <el-tabs v-model="activeTab" @tab-click="tabsClick">
        <el-tab-pane v-for="tab in tabsItem" :label="tab.label" :name="tab.name" :key="tab.name"></el-tab-pane>
      </el-tabs>
      <el-table :data="orderList"
        row-class-name="myrow">
        <el-table-column v-for="item in tableItem" :key="item.label"
          :label="item.label" :width="fitColumnWidth(item.label)" >

          <template slot-scope="scope">
            <div v-if="item.label=='宝贝'" class="order-column">
              <div class="column-head">
                <p class="tl">
                  <strong class="time">{{formatDate(scope.row.createDate)}} {{formatTime(scope.row.createDate)}}</strong>
                  <span>订单号:{{scope.row.orderCode}}</span>
                </p>
              </div>
              <div class="column-main">
                <div class="img-box"><img :src="`http://localhost:8080/tmall_ssm/img/productSingle_middle/${scope.row.orderItems[0].product.firstProductImage.id}.jpg`" alt=""></div>
                <div class="product-info">
                  <router-link :to="{path:'/extra/product',query:{pid:scope.row.orderItems[0].product.id}}">{{scope.row.orderItems[0].product.name}}</router-link>
                  <ul class="ensure">
                    <li><img src="/static/img/creditcard.png" alt=""></li>
                    <li><img src="/static/img/7day.png" alt=""></li>
                    <li><img src="/static/img/promise.png" alt=""></li>
                  </ul>
                </div>
              </div>
            </div>

            <div v-else-if="item.label=='单价'" class="order-column">
              <div class="column-head">
                <p><img src="/static/img/orderItemTmall.png" alt="">天猫商城</p>
              </div>
              <div class="column-main">
                <div class="price">
                  <p class="o-price">￥{{formatPrice(scope.row.orderItems[0].product.originalPrice)}}</p>
                  <p class="p-price">￥{{formatPrice(scope.row.orderItems[0].product.promotePrice)}}</p>
                </div>
              </div>
            </div>

            <div v-else-if="item.label=='数量'" class="order-column">
              <div class="column-head">
                <p>购买数量</p>
              </div>
              <div class="column-main">
                <div class="number">
                  <p>
                    {{scope.row.orderItems[0].number}}
                  </p>
                </div>
              </div>
            </div>

            <div v-else-if="item.label=='实付款'" class="order-column">
              <div class="column-head">
                <a class="orderItemWangWangGif" href="javascipt:void(0)"></a>
              </div>
              <div class="column-main">
                <div class="add-freight">
                  <p><strong>￥{{formatPrice(scope.row.orderItems[0].product.promotePrice)}}</strong></p>
                  <p>（含运费: ￥0.00）</p>
                </div>
              </div>
            </div>

            <div v-else-if="item.label=='交易操作'" class="order-column">
              <div class="column-head">
                <button class="delete-btn fr glyphicon glyphicon-trash"></button>
              </div>
              <div class="column-main">
                <div class="operator">
                  <el-button
                    :type="fitBtnType(scope.row.status)"
                    @click="fitDoAction(scope.row)">
                    {{fitOperateText(scope.row.status)}}</el-button>
                </div>
              </div>
            </div>
          </template>

        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {getOrders} from '@/api/user'
import CODES from '@/api/config'
import {formatDate, formatTime, formatPrice} from '@/util'
import {getUser} from '@/util/auth'
export default {
  data () {
    return {
      activeTab: 'all',
      tabsItem: [
        {label: '所有订单', name: 'all'},
        {label: '待付款', name: 'waitPay'},
        {label: '待发货', name: 'waitDelivery'},
        {label: '待收货', name: 'waitConfirm'},
        {label: '待评价', name: 'waitReview'}

      ],
      tableItem: [
        {label: '宝贝'},
        {label: '单价'},
        {label: '数量'},
        {label: '实付款'},
        {label: '交易操作'}

      ],
      orderList: []
    }
  },
  activated () {
    this.getList()
  },
  methods: {
    tabsClick (value) {
      var sortType = value.name
      console.log(sortType)
    },
    getList () {
      if (!getUser()) return
      getOrders({'uid': getUser().id}).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.orderList = data.data
        }
      })
    },
    formatDate (date) {
      return formatDate(date)
    },
    formatTime (date) {
      return formatTime(date)
    },
    formatPrice (num) {
      return formatPrice(num, 2)
    },
    fitColumnWidth (label) {
      switch (label) {
        case '宝贝':return '600px'
        default:return ''
      }
    },
    fitBtnType (status) {
      switch (status) {
        case 'waitPay':return 'primary'
        case 'waitDelivery':return
        case 'waitConfirm':return 'primary'
        case 'waitReview':return 'info'
        case 'finish':return
        case 'delete':return
        default: return false
      }
    },
    fitOperateText (status) {
      switch (status) {
        case 'waitPay':return '待付'
        case 'waitDelivery':return '催卖家发货'
        case 'waitConfirm':return '确认收货'
        case 'waitReview':return '评价'
        case 'finish':return ''
        case 'delete':return ''
        default: return ''
      }
    },
    fitDoAction (row) {
      var status = row.status
      var oid = row.id
      var total = row.total
      switch (status) {
        case 'waitPay': var s = 1

          break
        case 'waitDelivery':return '催卖家发货'

          break
        case 'waitConfirm':return '确认收货'

          break
        case 'waitReview':return '评价'
          break

        case 'finish':return ''
        case 'delete':return ''
        default: return ''
      }
    }
  }
}
</script>

<style lang="scss">
$gray: rgb(236,236,236);
$deepGray: rgb(170,170,170);
@import '@/common/style/variable.scss';

.bought{
  .el-tabs{
    .el-tabs__item{
      padding: 0 0.2rem;
      font-weight: bold;
      font-size: 0.16rem;
      &:hover,&.is-active{
        color:$themeColor;
      }
    }
    .el-tabs__active-bar{background:$themeColor}
  }
  .el-table{
    .el-table__header{border:1px solid rgb(232,232,232);}
    .is-leaf{ background:#f5f5f5;}
    .el-table--enable-row-hover .el-table__body tr:hover>td{
      background-color: #212e3e !important;
    }
    .el-table__row{
      .cell{padding:0;}
    }
    .el-table__body{
      border-collapse: separate;
      border-spacing:0px 10px;
      .cell{height: 100%;}
      tr>td{padding:0.0rem;}
    }
  }
  .myrow{
    box-sizing: border-box;
    td{border-top: 2px solid $gray;border-bottom: 2px solid $gray;}
    td:first-of-type{ border-left: 2px solid $gray;}
    td:last-of-type{ border-right: 2px solid $gray;}

    &:hover{
      td{border-top: 2px solid $deepGray;border-bottom: 2px solid $deepGray;}
      td:first-of-type{ border-left: 2px solid $deepGray;}
      td:last-of-type{ border-right: 2px solid $deepGray;}
    }

      .order-column{
        display: flex;
        flex-direction: column;
        .column-head{
          background:#f1f1f1;
          padding:8px 10px;
          height: 0.39rem;
          p{
            margin: 0;
            text-align: center;
            img{width: 13px;height:13px;}
          }
           //删除
          .delete-btn{
            display: inline-block;
            color: #999;
            font-size: 15px;
            background: transparent;
            &:hover{
              color: #333;
            }
          }
        }
        .column-main{
          display: flex;
          height: 1rem;
          //宝贝列 样式
          .img-box{
            padding:0.1rem;
            img{width: 0.8rem;height:0.8rem;}
          }
          .product-info{
            display: flex;
            flex:1;
            flex-direction: column;
            justify-content: space-between;
            padding:0.05rem;
            .ensure{
              display: flex;
              li{margin: 0 0.02rem;}
            }
          }

          //天猫商场样式
          .price{
            flex: 1;
            display: flex;
            flex-direction: column;
            font-size: 16px;
            justify-content: center;
            align-items: center;
            p{margin:0;}
            .o-price{color:#999;text-decoration: line-through;}
            .p-price{color:#3c3c3c;}
          }

          //数量
          .number{
            flex:1;
            font-size: 0.2rem;
            @include flexCenter();
          }

          //和商家联系
          .add-freight{
            flex-direction: column;
            @include flexCenter();
            strong{
              font-size: 14px;
              font-weight: bold;
            }
          }

          //操作
          .operator{
            @include flexCenter();
          }
        }
      }
  }
}
</style>
