webpackJsonp([12],{"+oyD":function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var s=e("vMJZ"),r=e("ZoQJ"),i={data:function(){return{orderData:{}}},computed:{oid:function(){return this.$route.query.oid},orderList:function(){if(this.orderData.id)return[this.orderData]}},mounted:function(){this.getPayedOrder()},methods:{confirmPay:function(){var t=this;Object(s.h)({oid:this.oid}).then(function(a){var e=a.data;t.$CODES.SUCCESS==e.code&&t.$router.push("/buyStep/stepSuccess")})},getPayedOrder:function(){var t=this;Object(s.l)({oid:this.oid}).then(function(a){var e=a.data;t.$CODES.SUCCESS==e.code&&(t.orderData=e.data)})},formatDAT:function(t){return t?Object(r.a)(t):"卖家还未发货"},formatPrice:function(t){return t?Object(r.d)(t,2):"0.00"}}},n={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"step-confirm"},[e("div",{staticClass:"container"},[e("div",{staticClass:"step-flow"},[e("img",{attrs:{src:"/static/img/comformPayFlow.png",alt:""}}),t._v(" "),e("ul",{staticClass:"times"},[e("li",{staticClass:"time"},[t._v(t._s(t.formatDAT(t.orderData.createDate)))]),t._v(" "),e("li",{staticClass:"time"},[t._v(t._s(t.formatDAT(t.orderData.payDate)))]),t._v(" "),e("li",{staticClass:"time"},[t._v(t._s(t.formatDAT(t.orderData.deliveryDate)))])])]),t._v(" "),t._m(0),t._v(" "),e("div",{staticClass:"order-info"},[e("h3",[t._v("订单信息")]),t._v(" "),e("el-table",{attrs:{data:t.orderList}},[e("el-table-column",{attrs:{label:"宝贝",align:"center",width:"500px"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticClass:"product-info"},[e("img",{attrs:{src:"/img/productSingle_middle/"+a.row.orderItems[0].product.firstProductImage.id+".jpg",alt:""}}),t._v(" "),e("router-link",{attrs:{to:{path:"/product",query:{pid:1}}}},[t._v(t._s(a.row.orderItems[0].product.name))])],1)]}}])}),t._v(" "),e("el-table-column",{attrs:{label:"单价",align:"center"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticClass:"product-price"},[e("p",[t._v("￥"+t._s(t.formatPrice(a.row.orderItems[0].product.promotePrice)))])])]}}])}),t._v(" "),e("el-table-column",{attrs:{label:"数量",align:"center"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticClass:"product-num"},[e("p",[t._v(t._s(a.row.totalNumber))])])]}}])}),t._v(" "),e("el-table-column",{attrs:{width:"150px",label:"商品总价",align:"center"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticClass:"product-total"},[e("p",[e("strong",[t._v("￥"+t._s(t.formatPrice(a.row.total)))])])])]}}])}),t._v(" "),e("el-table-column",{attrs:{label:"运费",align:"center"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticClass:"product-delivery"},[e("p",[t._v("快递 ： 0.00")])])]}}])})],1)],1),t._v(" "),e("div",{staticClass:"pay-price clearfix"},[e("p",{staticClass:"fr"},[t._v("实付款: "),e("strong",[t._v("￥"+t._s(t.formatPrice(t.orderData.total)))])])]),t._v(" "),e("ul",{staticClass:"other-info"},[e("li",[e("span",{staticClass:"info"},[t._v("订单编号：")]),t._v(" "),e("span",{staticClass:"value"},[t._v(t._s(t.orderData.orderCode)),e("img",{attrs:{src:"/static/img/confirmOrderTmall.png",alt:""}})])]),t._v(" "),t._m(1),t._v(" "),t._m(2),t._v(" "),e("li",[e("span",{staticClass:"info"},[t._v("成交时间：")]),e("span",{staticClass:"value"},[t._v(t._s(t.formatDAT(t.orderData.createDate)))])])]),t._v(" "),e("div",{staticClass:"confirm"},[t._m(3),t._v(" "),e("button",{on:{click:function(a){return t.confirmPay()}}},[t._v("确认支付")])])])])},staticRenderFns:[function(){var t=this.$createElement,a=this._self._c||t;return a("div",{staticClass:"agree"},[a("h2",[this._v("我已收到货，同意支付宝付款")])])},function(){var t=this.$createElement,a=this._self._c||t;return a("li",[a("span",{staticClass:"info"},[this._v("卖家昵称：")]),a("span",{staticClass:"value"},[this._v("天猫商品"),a("a",{staticClass:"orderItemWangWangGif",attrs:{href:"javascipt:void(0)"}})])])},function(){var t=this.$createElement,a=this._self._c||t;return a("li",[a("span",{staticClass:"info"},[this._v("收货信息：")]),a("span",{staticClass:"value"},[this._v("，，，")])])},function(){var t=this.$createElement,a=this._self._c||t;return a("p",[a("strong",[this._v("请收到货后，再确认收货！否则您可能钱货两空！")])])}]},o=e("C7Lr")(i,n,!1,function(t){e("AXsO")},null,null);a.default=o.exports},AXsO:function(t,a){}});