<template>
  <div class="main-category">
    <div class="m_container">
      <sproduct-wall
        :productsData="productsData"
        :needSort="true"
        :isCategory="true"
        :cid="cid"
        @chooseSort="handleChooseSort">
        </sproduct-wall>
      <div class="pagination">
        <el-pagination
          layout="total,prev,pager,next"
          :total="pager.total"
          :current-page="pager.pageNum"
          :page-count="pager.pages"
          @current-change="changePage"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import SproductWall from '@/components/sproduct-wall/sproduct-wall'
import {searchByCategory} from '@/api/home'
import CODES from '@/api/config'
export default {
  components: {
    SproductWall
  },
  data () {
    return {
      productsData: [],
      pager: {
        pages: 1,
        page: 1,
        total: 0
      },
      searchForm: {
        cid: '',
        page: 1,
        sort: 'all',
        min: '',
        max: ''
      }
    }
  },
  mounted () {
    if (!this.$route.query.hasOwnProperty('cid')) return
    var cid = this.$route.query.cid
    if (cid == '') return
    this.searchForm.cid = cid
    this.flashProductData()
  },
  computed: {
    cid () {
      return this.$route.query.cid
    }
  },
  methods: {
    flashProductData () {
      searchByCategory(this.searchForm).then(res => {
        const {data} = res
        if (CODES.SUCCESS == data.code) {
          this.productsData = data.data
          this.pager.pages = data.pages
          this.pager.page = data.pageNum
          this.pager.total = data.total
        }
      })
    },
    handleChooseSort (sort) {
      this.searchForm.sort = sort
      this.flashProductData()
    },
    changePage (page) {
      this.searchForm.page = page
      this.flashProductData(this.searchForm)
    }
  },
  watch: {
    $route: {
      handler: function (val, oval) {
        if (!this.$route.query.hasOwnProperty('cid')) return
        console.log('change')
        var cid = this.$route.query.cid
        if (cid == '') return
        this.searchForm.cid = cid
        this.flashProductData()
      },
      deep: true
    }
  }
}
</script>

<style lang="scss">
.main-category{
  .pagination{
    display: flex;
    justify-content: center;
  }

}
</style>
