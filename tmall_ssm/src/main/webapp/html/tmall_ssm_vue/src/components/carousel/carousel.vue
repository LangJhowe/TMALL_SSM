<template>
  <div class="category-carousel">
    <div class="m_container">
      <div class="category-tab-content" @mouseleave="open=false">

        <div class="category-nav">
          <div id="t_logo">
            <img src="/static/img/logo.gif" alt="t_logo">
          </div>
          <ul class="main-menu"  @mouseenter="open=true">
            <li
              class="item"
              v-for="item in category"
              :key="item.name"
              @mouseenter="showProduct(item.id)"
            ><router-link :to="{path: '/category',query:{cid:item.id}}"><i class="glyphicon glyphicon-link"></i>{{item.name}}</router-link></li>
          </ul>
        </div>
        <div v-show="open" class="category-panel">
          <div class="keywords-nav">
            <ul v-for="(row,rIndex) in categoryData" :key="rIndex" class="row" >
              <li class="col" v-for="(col,cIndex) in row" :key="cIndex">
                  <router-link :class="{hightlight:Math.random()<0.2}" :to="{path: '/extra/product', query: {pid: col.pid}}">{{col.title}}</router-link>
              </li>
            </ul>
          </div>
          <!-- <div class="recommend-imgs">

          </div> -->
        </div>

      </div>
    </div>

    <el-carousel class="carousel" trigger="click" height="510px" :interval="4000">
      <el-carousel-item v-for="item in imgData" :key="item.name">
        <div><img class="carousel-img" :src="item.src" alt=""></div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCategorys } from '@/api/home'
export default {
  data () {
    return {
      imgData: [
        {name: '1', src: '/static/img/lunbo/1.jpg'},
        {name: '2', src: '/static/img/lunbo/2.jpg'},
        {name: '3', src: '/static/img/lunbo/3.jpg'},
        {name: '4', src: '/static/img/lunbo/4.jpg'}
      ],
      open: false,
      categoryData: []
    }
  },
  computed: {
    ...mapGetters(['category'])
  },
  methods: {
    showProduct (id) {
      getCategorys(id).then((res) => {
        const {data} = res
        if (this.$CODES.SUCCESS == data.code) {
          this.categoryData = []
          let keywordsData = data.data
          let eachRowsKeywords = 8
          let rows = Math.ceil(keywordsData.length) / eachRowsKeywords
          for (let i = 0; i < rows; i++) {
            this.categoryData[i] = []
            for (let j = 0; j < eachRowsKeywords; j++) {
              let keywordIndex = i * eachRowsKeywords + j
              if (keywordsData[keywordIndex]) {
                this.categoryData[i].push(keywordsData[keywordIndex])
              }
            }
          }
        }
      })
    }
  }
}
</script>

<style lang="scss">
.category-carousel{
  background:#e8e8e8;
  height: 5.1rem;
  position: relative;
  .m_container{
    .category-tab-content{
      display: flex;
      .category-nav{
        position: relative;
        width: 2rem;
        z-index: 15;
        #t_logo{
          position: absolute;
          // height:1.3rem;
          top:-1.66rem;
          left:50%;
          img{display:inline-block;transform: translateX(-50%)}
        }
        .main-menu{
          width: 2rem;
          background:rgba(0, 0, 0, 0.1);
          .item{
            height: 0.3rem;
            line-height: 0.3rem;
            &:hover{
              background: #fff;
              a{color: lightblue}
            }
            a{
              color: #000;
              font-size: 0.14rem;
              .glyphicon{padding: 0 0.1rem;}
            }
          }
        }
      }
      .category-panel{
        flex:1;
        display: flex;
        background: #fff;
        position: relative;
        z-index: 15;
        padding: 0.2rem 0.8rem;
        .keywords-nav{
          width: 100%;
          .row{
            display: flex;
            height: 0.5rem;
            line-height: 0.5rem;
            margin: 0;
            &+.row{border-top:1px dashed #999;}
            .col{
              padding: 0 0.1rem;
              .hightlight{
                color: lightblue;
              }
            }
          }
        }
        .recommend-imgs{
          width: 0.5rem;
          height: 100%;
          background: lightgreen;
        }
      }

    }

  }
  .carousel{
    position: absolute;
    z-index: 10;
    top:0;
    width: 100%;
    height: 100%;
    div{
      img{
        width: 12.6rem;
        height: 5.1rem;
        margin: 0 auto;
        display: block;
        height: 100%;
      }
    }
  }
}
</style>
