<template>
  <div class="cart">
    <div class="m_container">
      <div class="top-count">
        <p>已选商品(不含运费) <strong>￥{{formatPrice(total)}}</strong><button class="settle-btn" :class="{active:canSettle}" @click="settle()">结算</button></p>
      </div>
      <el-table
        :data="orderItemList">
        <el-table-column
          :render-header="renderSelectAll">
          <template slot-scope="scope">
            <div class="cell-inner">
              <el-checkbox v-model="scope.row.isSelected" @change="rowSelect"><img :src="`http://localhost:8080/tmall_ssm/img/productSingle_middle/${scope.row.data.product.firstProductImage.id}.jpg`"></el-checkbox>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="商品信息"
          width="450px"
          >
          <template slot-scope="scope">
            <div class="cell-inner">
              <div class="product-info">
                <router-link :to="{path:'/extra/product',query:{pid:scope.row.data.product.id}}">{{scope.row.data.product.name}}</router-link>
                <ul class="ensure">
                  <li><img src="/static/img/creditcard.png" alt=""></li>
                  <li><img src="/static/img/7day.png" alt=""></li>
                  <li><img src="/static/img/promise.png" alt=""></li>
                </ul>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="单价"
          align="center"
          >
          <template slot-scope="scope">
            <div class="cell-inner">
              <div class="single-price">
                <p class="original"><strong>￥{{formatPrice(scope.row.data.product.originalPrice)}}</strong></p>
                <p class="promote"><strong>￥{{formatPrice(scope.row.data.product.promotePrice)}}</strong></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="数量"
          align="center"
          >
          <template slot-scope="scope">
            <div class="num">
              <el-input-number v-model="scope.row.data.number" size="small"></el-input-number>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="金额"
          align="center"
          >
          <template slot-scope="scope">
            <div class="cell-inner">
              <div class="total">
                <p>￥{{formatPrice(scope.row.data.number*scope.row.data.product.promotePrice)}}</p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          >
          <template slot-scope="scope">
            <div class="cell-inner">
              <div class="operator">
                <el-button @click="deleteOrderItem(scope.row.data.id)">删除</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="bottom-count">
        <el-checkbox :indeterminate="isIndeterminate" v-model="isSelectedAll" @change="selectAll">全选</el-checkbox>
        <div class="count">
          <p>
            <span class="num">已选商品<strong>{{totalNum}}</strong>件 （不含运费）:</span>
            <span class="total"><strong>￥{{formatPrice(total)}}</strong></span>
          </p>
        </div>
        <button class="settle-btn" :class="{active:canSettle}" @click="settle()">结算</button>
      </div>
    </div>
  </div>
</template>

<script>
import {getCartsData, deleteOrderItem} from '@/api/user'
import {formatPrice} from '@/util'
import {getUser} from '@/util/auth'

export default {
  data () {
    return {
      count: 0,
      orderItemList: [],
      isIndeterminate: false,
      isSelectedAll: false
    }
  },
  computed: {
    uid () {
      if (!getUser() && !getUser().id) return
      return getUser().id
    },
    totalNum () {
      var totalNum = 0
      this.orderItemList.forEach(item => {
        if (item.isSelected) {
          totalNum += item.data.number
        }
      })
      return totalNum
    },
    total () {
      var total = 0
      this.orderItemList.forEach(item => {
        if (item.isSelected) {
          total += item.data.number * item.data.product.promotePrice
        }
      })
      return total
    },
    selectedOisList () {
      return this.orderItemList.filter(item => {
        return item.isSelected
      })
    },
    canSettle () {
      return this.selectedOisList.length != 0
    }
  },
  mounted () {
    this.getCartsData()
  },
  methods: {
    getCartsData () {
      getCartsData({uid: this.uid}).then(res => {
        const {data} = res
        if (this.$CODES.SUCCESS == data.code) {
          var totalNum = 0
          this.orderItemList = data.data.map(item => {
            totalNum += item.number
            return {isSelected: false, data: item}
          })
          this.$store.dispatch('setCartNum', totalNum)
        }
      })
    },
    renderSelectAll (h, {column, $index}) {
      // return h('div', {
      //   attrs: {
      //     class: 'cell-inner'
      //   }
      // }, [h('el-checkbox', {
      //   attrs: {
      //   },
      //   on: {
      //     change: this.selectAll
      //   },
      //   props: {
      //     value: this.isSelectedAll,
      //     indeterminate: this.isIndeterminate

      //   }
      // }, '全选')])
      return (
        <div class="cell-inner">
          <el-checkbox v-model={this.isSelectedAll} indeterminate={this.isIndeterminate} onChange={(e) => this.selectAll(e)}>全选</el-checkbox>
        </div>
      )
    },
    rowSelect () {
      this.checkIsIndeterminate()
    },
    checkIsIndeterminate () {
      var all = this.orderItemList.length
      var selectNum = 0
      this.orderItemList.forEach(item => {
        if (item.isSelected) {
          selectNum++
        }
      })
      this.isIndeterminate = selectNum > 0 && selectNum < all
      this.isSelectedAll = selectNum == all
    },
    selectAll (v) {
      this.orderItemList.forEach(item => {
        item.isSelected = v
      })
      this.isSelectedAll = v
    },
    deleteOrderItem (oiid) {
      this.$confirm('确认删除?', '从购物车删除该商品', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        deleteOrderItem({uid: this.uid, oiid: oiid}).then(res => {
          const {data} = res
          if (this.$CODES.SUCCESS == data.code) {
            this.getCartsData()
            this.resetCheckBox()
            this.$notify({
              type: 'success',
              message: `成功删除商品`
            })
          }
        })
      }).catch(() => {

      })
    },
    settle () {
      if (!this.canSettle) return
      var oiidList = this.selectedOisList.map(item => {
        return {oiid: item.data.id}
      })
      this.$store.dispatch('setCartList', oiidList)
      this.$router.push({path: '/buyStep/step1'})
    },
    formatPrice (p) {
      return formatPrice(p, 2)
    },
    resetCheckBox () {
      this.isSelectedAll = false
      this.isIndeterminate = false
    }

  },
  watch: {
    '$route' (to, from) {
      if (to.path == '/buyStep/cart') {
        this.getCartsData()
      }
    }
  }
}
</script>

<style lang="scss">
.el-table{
  .cell{display: flex;align-items: center;}
  .el-table__body{
    .cell-inner{display:flex;height: 0.9rem;}
  }
}
.el-checkbox{margin:0;height:auto;}
</style>

<style lang="scss">
.cart{
  .top-count{
    display: flex;
    justify-content: flex-end;
    strong{
      display: inline-block;
      margin: 0 0.05rem;
      color:#c40000;
      font-size: 0.14rem;
    }
    .settle-btn{
      border: 1px solid #AAAAAA;
      background: #AAAAAA;
      color: white;
      width: 53px;
      height: 25px;
      border-radius: 2px;
    }
  }
  .cell-inner{
    display: flex;
    width: 100%;
    align-items:center;
    padding:0;
    img{width:0.8rem;height:0.8rem;}
    .product-info{
      display: flex;
      height: 100%;
      flex:1;
      flex-direction: column;
      justify-content: space-between;
      padding:0.05rem;
      .ensure{
        display: flex;
        li{margin: 0 0.02rem;}
        img{width:0.16rem;height:0.16rem;}
      }
    }
    .single-price,.num,.total,.operator{
      display: flex;
      flex: 1;
      flex-direction: column;
      justify-content: center;
    }
    .single-price{
      font-weight:bold;
      // @include flexCenter()
      .original{
        color:#9c9c9c;
        text-decoration:line-through;
      }
      .promote{
        color:#c40000;
      }
    }
    .total{
      p{font-weight:bold;color:#c40000;font-size:0.14rem}
    }
  }
  .bottom-count{
    display: flex;
    justify-content: space-between;
    align-items:center;
    background:#e5e5e5;
    padding-left: .1rem;
    .count{
      flex: 1;
      text-align:right;
      padding: 0 0.05rem;
      strong{color:#c40000;font-weight:bold;}
      .num>strong{font-size:0.16rem;}
      .total>strong{font-size:0.2rem;}
    }
    .settle-btn{
      width: 1.2rem;
      height:0.5rem;
      font-size:0.2rem;
    }
  }
  .settle-btn{
    background:#aaa;
    color:#fff;
    &.active{background:#c40000;}
  }
}
</style>
