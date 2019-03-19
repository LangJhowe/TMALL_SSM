<template>
  <div class="f_footer">
    <div class="ensure">
      <div class="m_container">
        <a href="#nowhere" @click="goto()">
          <img src="/static/img/ensure.png" alt="">
        </a>
      </div>
    </div>
    <div class="descs">
      <div class="m_container">
        <div class="disc-item" v-for="item in descData" :key="item.name">
          <h3>{{item.name}}</h3>
          <ul>
            <li v-for="child in item.child" :key="child.name">
              <a v-if="!child.QRCodeImg" :href="child.url" @click="goto()">{{child.name}}</a>
              <a v-else :href="child.url" @click="goto()"><img :src="child.QRCodeImg" alt=""></a>
            </li>
          </ul>
        </div>
      </div>

    </div>
    <div class="copyright">
      <div class="m_container">
      <img class="cateye" src="/static/img/cateye.png" alt="">
        <ul v-for="item in copyrightsData" :class="item.class" :key="item.class">
          <li v-for="(child,index) in item.children" :key="child.name">
            <a :href="child.url" @click="goto()">{{child.name}}</a><span v-if="(item.class!='tmall-info')&&(index!= item.children.length-1)">|</span>
          </li>
        </ul>
        <div class="copyRightYear">© 2003-2016 TMALL.COM 版权所有</div>
        <div cllass="copyRightImgs">
          <img src="/static/img/copyRight1.jpg" alt="">
          <img src="/static/img/copyRight2.jpg" alt="">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      descData: [
        {name: '购物指南',
          child: [
            {name: '免费注册', url: '#nowhere'},
            {name: '开通支付宝', url: '#nowhere'},
            {name: '支付宝充值', url: '#nowhere'}
          ]
        }, {
          name: '天猫保障',
          child: [
            {name: '发票保障', url: '#nowhere'},
            {name: '售后规则', url: '#nowhere'},
            {name: '缺货赔付', url: '#nowhere'}
          ]
        }, {
          name: '支付方式',
          child: [
            {name: '快捷支付', url: '#nowhere'},
            {name: '信用卡', url: '#nowhere'},
            {name: '蚂蚁花呗', url: '#nowhere'},
            {name: '货到付款', url: '#nowhere'}

          ]
        }, {
          name: '商家服务',
          child: [
            {name: '商家入驻', url: '#nowhere'},
            {name: '商家中心', url: '#nowhere'},
            {name: '天猫智库', url: '#nowhere'},
            {name: '天猫规则', url: '#nowhere'},
            {name: '物流服务', url: '#nowhere'},
            {name: '喵言喵语', url: '#nowhere'},
            {name: '运营服务', url: '#nowhere'}
          ]
        }, {
          name: '手机天猫',
          child: [
            {name: '手机app二维码', QRCodeImg: '/static/img/ma.png', url: '#nowhere'}
          ]
        }
      ],
      copyrightsData: [
        {
          class: 'tmall-info',
          name: '天猫信息',
          children: [
            {name: '关于天猫', url: '#nowhere'},
            {name: '帮助中心', url: '#nowhere'},
            {name: '开放平台', url: '#nowhere'},
            {name: '诚聘英才', url: '#nowhere'},
            {name: '联系我们', url: '#nowhere'},
            {name: '网站合作', url: '#nowhere'},
            {name: '法律声明', url: '#nowhere'},
            {name: '知识产权', url: '#nowhere'},
            {name: '廉政举报', url: '#nowhere'}
          ]
        }, {
          class: 'other-link',
          name: '其他链接',
          children: [
            {name: '阿里巴巴集团', url: '#nowhere'},
            {name: '淘宝网', url: '#nowhere'},
            {name: '天猫', url: '#nowhere'},
            {name: '聚划算', url: '#nowhere'},
            {name: '全球速卖通', url: '#nowhere'},
            {name: '阿里巴巴国际交易市场', url: '#nowhere'},
            {name: '1688', url: '#nowhere'},
            {name: '阿里妈妈', url: '#nowhere'},
            {name: '阿里旅行·去啊', url: '#nowhere'},
            {name: '阿里云计算', url: '#nowhere'},
            {name: '阿里通信', url: '#nowhere'},
            {name: 'YunOS', url: '#nowhere'},
            {name: '万网', url: '#nowhere'},
            {name: '高德', url: '#nowhere'},
            {name: '优视', url: '#nowhere'},
            {name: '友盟', url: '#nowhere'},
            {name: '虾米', url: '#nowhere'},
            {name: '天天动听', url: '#nowhere'},
            {name: '来往', url: '#nowhere'},
            {name: '钉钉', url: '#nowhere'},
            {name: '支付宝', url: '#nowhere'}
          ]
        }, {
          class: 'license',
          name: '许可证',
          children: [
            {name: '增值电信业务经营许可证： 浙B2-20110446', url: '#nowhere'},
            {name: '网络文化经营许可证：浙网文[2015]0295-065号', url: '#nowhere'},
            {name: '互联网医疗保健信息服务 审核同意书 浙卫网审【2014】6号 互联网药品信息服务资质证书编号：浙-（经营性）-2012-0005', url: '#nowhere'}
          ]
        }
      ]
    }
  },
  methods: {
    goto () {
      alert('模仿天猫的连接，并没有跳转到实际的页面')
    }
  }
}
</script>

<style lang="scss">
.f_footer{
  position: relative;
  .ensure{
    padding: 0.30rem 0;
    border-top:1px solid #e7e7e7;
    .m_container{
      a{
        display: block;
        img{
          display: block;
          margin: 0 auto;
        }
      }
    }
  }

  .descs{
    border-top:1px solid #e7e7e7;
    .m_container{
      display: flex;
      justify-content: space-around;
    }
  }
  .copyright{

    .m_container{
      position: relative;
      .cateye{
        position: absolute;
        top:-33px;
        left: 0;
        margin: 0;
      }
      ul{
        display:flex;
        padding: 0.1rem 0;
        flex-wrap: wrap;
        li{
          padding-right: 0.1rem;
          a,span{color:#fff}
          span{padding-left: 0.1rem;}
        }
      }
      .license{
        li{
          a,span{color:#999;}
        }
      }
      .copyRightYear{color:#999;}
    }
  }
}
</style>
