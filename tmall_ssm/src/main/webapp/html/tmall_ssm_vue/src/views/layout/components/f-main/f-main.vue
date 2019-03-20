<template>
  <main class="f_main">
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </main>
</template>

<script>
import Category from './components/category/category'
import ProductsWall from './components/products-wall/products-wall'
export default {
  components: {
    Category,
    ProductsWall
  }
}
</script>

<style lang="scss">
.f-main{
  margin-bottom: 0.1rem;
}
</style>
