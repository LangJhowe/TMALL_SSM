webpackJsonp([5],{If12:function(t,a,s){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var i=s("6gxQ"),e=s("Uhvn"),n=s("xO/y"),o=s("vMJZ"),r=s("iF09"),c=s("ZoQJ"),l={components:{NavTopbar:i.c,LoginBox:e.a},data:function(){return{pid:"",productData:{},productPropertys:[],productReviews:[],choosenImgId:"0",currentTab:"productDeatil",loginBoxOpen:!1,buyForm:{uid:Object(r.c)()?Object(r.c)().id:"",pid:"",num:1},pager:{total:1,page:1}}},computed:{isLogin:function(){return!!Object(r.c)()},fPromotePrice:function(){return Object(c.d)(this.productData.promotePrice,2)||"0.00"},fOriginPrice:function(){return Object(c.d)(this.productData.originalPrice,2)||"0.00"}},mounted:function(){this.pid=this.$route.query.pid,this.buyForm.pid=this.$route.query.pid,this.getProductData(),this.getProductReview()},methods:{getProductData:function(){var t=this;Object(n.b)(this.pid).then(function(a){var s=a.data;t.$CODES.SUCCESS==s.code&&(t.productData=s.data.data,t.productPropertys=s.data.propertys,t.choosenImgId=s.data.data.productSingleImages[0].id)})},getProductReview:function(){var t=this;Object(n.e)({pid:this.pid,page:this.pager.page}).then(function(a){var s=a.data;t.$CODES.SUCCESS==s.code&&(t.productReviews=s.data,t.pager.total=s.total)})},handleChange:function(){},pagerChange:function(t){this.pager.page=t,this.getProductReview()},buyNow:function(){var t=this;this.needOpenLoginBox()||Object(o.c)(this.buyForm).then(function(a){var s=a.data;if(s.code==t.$CODES.SUCCESS){var i=[{oiid:s.oiid}];t.$store.dispatch("setCartList",i),t.$router.push({path:"/buyStep/step1"})}})},addToCart:function(){var t=this;this.needOpenLoginBox()||Object(o.a)(this.buyForm).then(function(a){if(a.data.code==t.$CODES.SUCCESS){var s=Object(r.c)().cartNum+t.buyForm.num;t.$store.dispatch("setCartNum",s),t.$notify({type:"success",message:"成功添加"+t.buyForm.num+"件商品"})}})},formatDate:function(t){return Object(c.b)(t)},selectBigImg:function(t){this.choosenImgId=t},needOpenLoginBox:function(){return null==Object(r.c)()&&(this.loginBoxOpen=!0,!0)},handleLoginSuccess:function(t){this.loginBoxOpen=!1,this.buyNow()}},watch:{$route:function(t,a){t.query.hasOwnProperty("pid")&&this.pid!=a.query.pid&&(this.pid=this.$route.query.pid,this.buyForm.pid=this.$route.query.pid,this.buyForm.num=1,this.getProductData(),this.getProductReview())}}},u={render:function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"product-page"},[s("div",{staticClass:"m_container"},[s("div",{staticClass:"category-img"},[t.productData.cid&&0!=t.productData.cid?s("img",{attrs:{src:"/tmall_ssm/img/category/"+t.productData.cid+".jpg",alt:""}}):t._e()]),t._v(" "),s("div",{staticClass:"product-wrap"},[s("div",{staticClass:"imgs-info"},[s("div",{staticClass:"p-imgs"},[t.choosenImgId&&0!=t.choosenImgId?s("img",{staticClass:"bigImg",attrs:{src:"/tmall_ssm/img/productSingle/"+t.choosenImgId+".jpg",alt:""}}):t._e(),t._v(" "),s("ul",{staticClass:"smallImgs"},t._l(t.productData.productSingleImages,function(a){return s("li",{key:a.id,on:{click:function(s){return t.selectBigImg(a.id)}}},[s("img",{attrs:{src:"/tmall_ssm/img/productSingle_small/"+a.id+".jpg",alt:""}})])}),0)]),t._v(" "),s("div",{staticClass:"p-info"},[s("h2",{staticClass:"title"},[t._v(t._s(t.productData.name))]),t._v(" "),s("p",{staticClass:"subTitle"},[t._v(t._s(t.productData.subTitle))]),t._v(" "),s("div",{staticClass:"price-box"},[t._m(0),t._v(" "),s("div",{staticClass:"product-price"},[t._m(1),t._v(" "),s("p",{staticClass:"origin-price"},[s("span",{staticClass:"text"},[t._v("价格")]),t._v("￥"),s("span",{staticClass:"value"},[t._v(t._s(t.fOriginPrice))])]),t._v(" "),s("p",{staticClass:"promote-price"},[s("span",{staticClass:"text"},[t._v("促销价")]),s("span",{staticClass:"unit"},[t._v("￥")]),s("strong",{staticClass:"value"},[t._v(t._s(t.fPromotePrice))])])])]),t._v(" "),s("div",{staticClass:"counts"},[s("p",{staticClass:"sale-count"},[s("span",{staticClass:"text"},[t._v("销量")]),s("strong",{staticClass:"value"},[t._v(t._s(t.productData.saleCount))])]),t._v(" "),s("p",{staticClass:"review-count"},[s("span",{staticClass:"text"},[t._v("累计评价")]),s("strong",{staticClass:"value"},[t._v(t._s(t.productData.reviewCount))])])]),t._v(" "),s("div",{staticClass:"numbers"},[s("span",[t._v("数量")]),t._v(" "),s("el-input-number",{attrs:{"controls-position":"right",min:1,max:t.productData.stock,size:"small"},on:{change:t.handleChange},model:{value:t.buyForm.num,callback:function(a){t.$set(t.buyForm,"num",a)},expression:"buyForm.num"}}),t._v(" "),s("span",[t._v("件 库存"+t._s(t.productData.stock)+"件")])],1),t._v(" "),t._m(2),t._v(" "),s("div",{staticClass:"buy"},[s("button",{staticClass:"buy-now",on:{click:function(a){return t.buyNow()}}},[t._v("立即购买")]),t._v(" "),s("button",{staticClass:"add-cart",on:{click:function(a){return t.addToCart()}}},[s("span",{staticClass:"glyphicon glyphicon-shopping-cart"}),t._v("加入购物车")])])])]),t._v(" "),s("el-tabs",{staticClass:"tabs",attrs:{type:"card"},model:{value:t.currentTab,callback:function(a){t.currentTab=a},expression:"currentTab"}},[s("el-tab-pane",{attrs:{label:"商品详情",name:"productDeatil"}},[s("div",{staticClass:"product-property"},[s("div",{staticClass:"title"},[t._v("产品参数:")]),t._v(" "),s("el-row",t._l(t.productPropertys,function(a){return s("el-col",{key:a.id,attrs:{span:8}},[s("span",{staticClass:"text"},[t._v(t._s(a.property.name)+":  ")]),s("span",{staticClass:"value"},[t._v(t._s(a.value))])])}),1)],1),t._v(" "),s("ul",{staticClass:"product-detail-imgs"},t._l(t.productData.productDetailImages,function(t){return s("li",{key:t.id},[s("img",{attrs:{src:"/tmall_ssm/img/productDetail/"+t.id+".jpg",alt:""}})])}),0)]),t._v(" "),s("el-tab-pane",{attrs:{label:"累计评价 "+t.productData.reviewCount,name:"review"}},[s("ul",{staticClass:"reviews"},t._l(t.productReviews,function(a){return s("li",{key:a.id,staticClass:"review"},[s("p",{staticClass:"content"},[t._v(t._s(a.content))]),t._v(" "),s("p",{staticClass:"info"},[s("span",[t._v(t._s(t.formatDate(a.createDate)))]),t._v(" "),s("span",[t._v(t._s(a.user.name)+"(匿名)")])])])}),0),t._v(" "),s("div",{staticClass:"pager"},[s("el-pagination",{attrs:{layout:"prev, pager, next",total:t.pager.total,"current-page":t.pager.page},on:{"current-change":t.pagerChange}})],1)])],1)],1)]),t._v(" "),s("el-dialog",{attrs:{visible:t.loginBoxOpen},on:{close:function(a){t.loginBoxOpen=!1}}},[s("login-box",{on:{loginSuccess:t.handleLoginSuccess}})],1)],1)},staticRenderFns:[function(){var t=this.$createElement,a=this._self._c||t;return a("div",{staticClass:"juhuasuan"},[a("p",[a("strong",{staticClass:"juhuasuanBig"},[this._v("聚划算")]),this._v("此商品即将参加聚划算"),a("strong",{staticClass:"remain-time"},[this._v("1天19小时")]),this._v("后开始,")])])},function(){var t=this.$createElement,a=this._self._c||t;return a("ul",{staticClass:"gouwujuans"},[a("li",{staticClass:"gwj"},[a("img",{attrs:{src:"/static/img/gouwujuan.png",alt:""}}),a("span",[this._v("全天猫实物商品通用")])])])},function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"commit"},[s("span",{staticClass:"text"},[t._v("服务承诺")]),t._v(" "),s("ul",[s("li",[t._v("正品保证")]),t._v(" "),s("li",[t._v("极速退款")]),t._v(" "),s("li",[t._v("赠运费险")]),t._v(" "),s("li",[t._v("七天无理由退款")])])])}]},p=s("C7Lr")(l,u,!1,function(t){s("STa6")},null,null);a.default=p.exports},"JD/u":function(t,a){},STa6:function(t,a){},Uhvn:function(t,a,s){"use strict";var i=s("H4cj"),e=s("iF09"),n={data:function(){return{loginForm:{name:"",password:""}}},methods:{onSubmit:function(){return!1},doLogin:function(){var t=this,a=this.loginForm;""!=a.name&&""!=a.password?Object(i.a)(a).then(function(a){var s=a.data;t.$CODES.SUCCESS==s.code?(t.$store.dispatch("fillUser",s.data),Object(e.i)(s.data),t.$notify({type:"success",message:"登陆成功！欢迎回来,"+s.data.name+"!"}),t.$emit("loginSuccess",s.data)):alert(s.msg)}):alert("请输入账号密码")},goto:function(){alert("这个功能木有做")}}},o={render:function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"login-box"},[s("form",{staticClass:"login-form",attrs:{action:"/login"},on:{submit:function(a){return a.preventDefault(),t.onSubmit(a)}}},[s("h2",[t._v("账户登录")]),t._v(" "),s("div",{staticClass:"login-inputs"},[s("div",{staticClass:"row"},[t._m(0),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.loginForm.name,expression:"loginForm.name"}],attrs:{type:"text",placeholder:"手机/会员名/邮箱"},domProps:{value:t.loginForm.name},on:{input:function(a){a.target.composing||t.$set(t.loginForm,"name",a.target.value)}}})]),t._v(" "),s("div",{staticClass:"row"},[t._m(1),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.loginForm.password,expression:"loginForm.password"}],attrs:{type:"password",placeholder:"密码",autocomplete:"off"},domProps:{value:t.loginForm.password},on:{input:function(a){a.target.composing||t.$set(t.loginForm,"password",a.target.value)}}})])]),t._v(" "),s("p",{staticStyle:{color:"#a94442",margin:"0"}},[t._v("非官网请勿输入真实账号密码")]),t._v(" "),s("div",{staticClass:"other clearfix"},[s("router-link",{staticClass:"fl",attrs:{to:"/login"},nativeOn:{click:function(a){return t.goto()}}},[t._v("忘记登录密码 ")]),t._v(" "),s("router-link",{staticClass:"fr",attrs:{to:"/extra/registry"}},[t._v("免费注册")])],1),t._v(" "),s("button",{staticClass:"login-btn",on:{click:function(a){return t.doLogin()}}},[t._v("登录")])])])},staticRenderFns:[function(){var t=this.$createElement,a=this._self._c||t;return a("span",{staticClass:"name-icon"},[a("i",{staticClass:"glyphicon glyphicon-user"})])},function(){var t=this.$createElement,a=this._self._c||t;return a("span",{staticClass:"password-icon"},[a("i",{staticClass:"glyphicon glyphicon-lock"})])}]},r=s("C7Lr")(n,o,!1,function(t){s("JD/u")},null,null);a.a=r.exports}});