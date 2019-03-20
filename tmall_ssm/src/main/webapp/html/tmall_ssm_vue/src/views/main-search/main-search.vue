<template>
  <div class="main-search">
    <div class="m_container">
      <sproduct-wall :productsData="productsData"></sproduct-wall>
      <div class="pagination">
        <el-pagination
          layout="total, prev, pager, next"
          :page-count="pager.pages"
          :current-page="pager.pageNum"
          :total="pager.total"
          @current-change="changePage"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import SproductWall from '@/components/sproduct-wall/sproduct-wall'
import {searchByKeywords} from '@/api/home'
import CODES from '@/api/config'

export default {
  components: {
    SproductWall
  },
  data () {
    return {
      productsData: [],
      pager: {
        pages: 0,
        page: 1,
        total: 0
      },
      searchForm: {
        keyword: '',
        page: 1
      }
    }
  },
  mounted () {
    var keyword = this.$route.query.keyword
    if (keyword == '') return
    this.searchForm.keyword = keyword
    this.flashProductData(this.searchForm)
  },
  methods: {
    flashProductData () {
      searchByKeywords(this.searchForm).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.productsData = data.data
          this.pager.pages = data.pages
          this.pager.page = data.pageNum
          this.pager.total = data.total
        }
      })
    },
    changePage (page) {
      this.searchForm.page = page
      this.flashProductData(this.searchForm)
    }
  },
  watch: {
    $route: {
      handler: function (val, oval) {
        if (this.$route.query.hasOwnProperty('keyword')) {
          console.log(val)
          console.log(oval)
          var keyword = this.$route.query.keyword
          if (keyword == '') return
          this.searchForm.keyword = keyword
          this.flashProductData()
        }
      },
      deep: true
    }
  }
}
</script>

<style lang="scss">
.main-search{
  .pagination{
    display: flex;
    justify-content: center;
  }

}
</style>
