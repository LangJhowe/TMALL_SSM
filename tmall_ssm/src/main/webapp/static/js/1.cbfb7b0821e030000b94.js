webpackJsonp([1],{EUVW:function(t,a){},UkMr:function(t,a){},V1wY:function(t,a){},"rd+6":function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var s=e("4YfN"),r=e.n(s),i=e("R4Sj"),n=e("xO/y"),c={data:function(){return{imgData:[{name:"1",src:"/static/img/lunbo/1.jpg"},{name:"2",src:"/static/img/lunbo/2.jpg"},{name:"3",src:"/static/img/lunbo/3.jpg"},{name:"4",src:"/static/img/lunbo/4.jpg"}],open:!1,categoryData:[]}},computed:r()({},Object(i.b)(["category"])),methods:{showProduct:function(t){var a=this;Object(n.a)(t).then(function(t){var e=t.data;if(a.$CODES.SUCCESS==e.code){a.categoryData=[];for(var s=e.data,r=Math.ceil(s.length)/8,i=0;i<r;i++){a.categoryData[i]=[];for(var n=0;n<8;n++){var c=8*i+n;s[c]&&a.categoryData[i].push(s[c])}}}})}}},o={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"category-carousel"},[e("div",{staticClass:"m_container"},[e("div",{staticClass:"category-tab-content",on:{mouseleave:function(a){t.open=!1}}},[e("div",{staticClass:"category-nav"},[t._m(0),t._v(" "),e("ul",{staticClass:"main-menu",on:{mouseenter:function(a){t.open=!0}}},t._l(t.category,function(a){return e("li",{key:a.name,staticClass:"item",on:{mouseenter:function(e){return t.showProduct(a.id)}}},[e("router-link",{attrs:{to:{path:"/category",query:{cid:a.id}}}},[e("i",{staticClass:"glyphicon glyphicon-link"}),t._v(t._s(a.name))])],1)}),0)]),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:t.open,expression:"open"}],staticClass:"category-panel"},[e("div",{staticClass:"keywords-nav"},t._l(t.categoryData,function(a,s){return e("ul",{key:s,staticClass:"row"},t._l(a,function(a,s){return e("li",{key:s,staticClass:"col"},[e("router-link",{class:{hightlight:Math.random()<.2},attrs:{to:{path:"/extra/product",query:{pid:a.pid}}}},[t._v(t._s(a.title))])],1)}),0)}),0)])])]),t._v(" "),e("el-carousel",{staticClass:"carousel",attrs:{trigger:"click",height:"510px",interval:4e3}},t._l(t.imgData,function(t){return e("el-carousel-item",{key:t.name},[e("div",[e("img",{staticClass:"carousel-img",attrs:{src:t.src,alt:""}})])])}),1)],1)},staticRenderFns:[function(){var t=this.$createElement,a=this._self._c||t;return a("div",{attrs:{id:"t_logo"}},[a("img",{attrs:{src:"/static/img/logo.gif",alt:"t_logo"}})])}]},l={components:{Carousel:e("C7Lr")(c,o,!1,function(t){e("UkMr")},null,null).exports},data:function(){return{originNavItems:[{name:"天猫超市",img:"static/img/chaoshi.png",url:"javascript:void(0)"},{name:"天猫国际",img:"static/img/guoji.png",url:"javascript:void(0)"}]}},methods:{},computed:r()({subNavItems:function(){var t=this.category.slice(0,4).map(function(t){return{name:t.name,id:""+t.id,url:"/category"}});return t}},Object(i.b)(["category"]))},u={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"category"},[e("nav",{staticClass:"cross-nav"},[e("div",{staticClass:"m_container"},[t._m(0),t._v(" "),e("ul",{staticClass:"right-menu"},[t._l(t.originNavItems,function(a){return e("li",{key:a.name},[a.hasOwnProperty("img")?e("a",{attrs:{href:a.url}},[e("img",{attrs:{src:a.img,alt:""}})]):e("a",{attrs:{href:a.url}},[t._v(t._s(a.name))])])}),t._v(" "),t._l(t.subNavItems,function(a){return e("li",{key:a.id},[e("router-link",{attrs:{to:{path:a.url,query:{cid:a.id}}}},[t._v(t._s(a.name))])],1)})],2)])]),t._v(" "),e("carousel")],1)},staticRenderFns:[function(){var t=this.$createElement,a=this._self._c||t;return a("ul",{staticClass:"left-menu"},[a("li",[a("i",{staticClass:"glyphicon glyphicon-th-list",staticStyle:{margin:"0 10px"}}),this._v("商品分类")])])}]},d=e("C7Lr")(l,u,!1,function(t){e("V1wY")},null,null).exports,m={props:{item:Object}},g={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("a",{staticClass:"hproduct-card"},[e("div",{staticClass:"product-img"},[e("img",{attrs:{src:"/tmall_ssm/img/productSingle/"+t.item.firstProductImage.id+".jpg",alt:""}})]),t._v(" "),e("div",{staticClass:"product-name"},[t._v(t._s(t.item.name))]),t._v(" "),e("div",{staticClass:"product-price"},[e("span",{staticClass:"unit"},[t._v("￥")]),t._v(t._s(t.item.promotePrice))])])},staticRenderFns:[]},v={components:{HproductCard:e("C7Lr")(m,g,!1,function(t){e("tm99")},"data-v-3e3b8ece",null).exports},data:function(){return{categoryDatas:[]}},activated:function(){var t=this;Object(n.c)().then(function(a){var e=a.data;t.$CODES.SUCCESS==e.code&&(t.categoryDatas=e.data)})},methods:{toProductPage:function(t){this.$router.push({path:"/extra/product",query:{pid:t}})}}},p={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"products-wall"},[e("div",{staticClass:"m_container"},[t._l(t.categoryDatas,function(a){return e("div",{key:a.name,staticClass:"category-row"},[e("div",{staticClass:"category-title"},[e("h2",[t._v(t._s(a.name))])]),t._v(" "),e("ul",{staticClass:"category-board"},t._l(a.products,function(a,s){return s<5?e("li",{key:a.name,staticClass:"product-item",on:{click:function(e){return t.toProductPage(a.id)}}},[e("hproduct-card",{attrs:{item:a}})],1):t._e()}),0)])}),t._v(" "),t._m(0)],2)])},staticRenderFns:[function(){var t=this.$createElement,a=this._self._c||t;return a("div",{staticClass:"endpng"},[a("img",{attrs:{src:"/static/img/end.png",alt:""}})])}]},_={components:{Category:d,ProductsWall:e("C7Lr")(v,p,!1,function(t){e("EUVW")},"data-v-655e78da",null).exports}},f={render:function(){var t=this.$createElement,a=this._self._c||t;return a("div",{staticClass:"home-main"},[a("category"),this._v(" "),a("products-wall")],1)},staticRenderFns:[]},h=e("C7Lr")(_,f,!1,null,null,null);a.default=h.exports},tm99:function(t,a){}});