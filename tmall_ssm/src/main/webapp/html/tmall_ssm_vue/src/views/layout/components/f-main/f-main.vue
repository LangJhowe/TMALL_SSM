<template>
  <main class="f_main">
    <category></category>
    <products-wall></products-wall>
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

</style>
