<template>
  <div class="search-box">
    <form action="#" @submit.prevent="onSubmit">
      <div class="searchDiv">
        <input v-model="keyword" type="text" placeholder="时尚男鞋 太阳镜">
        <button type="submit" @click="search()">搜索</button>
        <ul class="recommend-searchs clearfix">
          <li v-for="(rsItem) in recommendSearchs" :key="rsItem.name"><router-link :to="{path:`/category`,query:{cid:`${rsItem.id}`}}">{{rsItem.name}}</router-link></li>
         </ul>
      </div>
    </form>
  </div>
</template>

<script>
// import {searchByKeywords} from '@/api/home'

export default {
  data () {
    return {
      keyword: '',
      recommendSearchs: []
    }
  },
  mounted () {
    this.$store.dispatch('fillData').then((res) => {
      const data = res.data
      if (this.$CODES.SUCCESS == data.code) {
        this.recommendSearchs = data.data.slice(0, 4)
      }
    })
  },
  methods: {
    onSubmit: function () {
      return false
    },
    search () {
      var keyword = this.keyword
      this.$router.push({path: '/search', query: {keyword: keyword}})
    }
  }
}
</script>

<style lang="scss">
.search-box{
  .recommend-searchs{
    display: flex;
    justify-content: flex-start;
    align-items: center;
    font-size: 0.16rem;
    li{padding:0.05rem 0.15rem;position: relative;}
    li+li::after{
      content: '';
      position: absolute;
      width: 1px;
      height: 8px;
      top:50%;
      transform: translateY(-50%);
      left: 0;
      background: #999;
    }
  }
}
</style>
