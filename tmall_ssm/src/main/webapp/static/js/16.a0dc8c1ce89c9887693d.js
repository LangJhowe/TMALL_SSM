webpackJsonp([16],{Ci6a:function(e,t){},kLxj:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("6gxQ"),i=s("YOyO"),n={components:{NavTopbar:a.c,Search:i.a,FFooter:a.a},data:function(){return{}},computed:{needSearch:function(){switch(this.$route.path){case"/buyStep/stepPayed":case"/buyStep/bought":case"/buyStep/stepConfirm":case"/buyStep/stepSuccess":case"/buyStep/cart":return!0;default:return!1}},needFlow:function(){switch(this.$route.path){case"/buyStep/stepPay":case"/buyStep/step1":return!0;default:return!1}}},mounted:function(){},methods:{},watch:{}},r={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"buy-step"},[t("nav-topbar"),this._v(" "),t("div",{staticClass:"f_head"},[t("div",{staticClass:"m_container"},[t("img",{staticClass:"logo",attrs:{src:"/static/img/simpleLogo.png",alt:""}}),this._v(" "),t("img",{directives:[{name:"show",rawName:"v-show",value:this.needFlow,expression:"needFlow"}],staticClass:"step-img",attrs:{src:"/static/img/buyflow.png",alt:""}}),this._v(" "),t("search",{directives:[{name:"show",rawName:"v-show",value:this.needSearch,expression:"needSearch"}]})],1)]),this._v(" "),t("div",{staticClass:"f_main"},[t("keep-alive",[t("router-view")],1)],1),this._v(" "),t("f-footer")],1)},staticRenderFns:[]},c=s("C7Lr")(n,r,!1,function(e){s("Ci6a")},null,null);t.default=c.exports}});