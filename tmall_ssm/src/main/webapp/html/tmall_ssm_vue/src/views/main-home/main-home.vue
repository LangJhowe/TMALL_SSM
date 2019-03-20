<template>
  <div class="home-main">
    <category></category>
    <products-wall></products-wall>
  </div>
</template>

<script>
import Category from '@/views/layout/components/f-main/components/category/category'
import ProductsWall from '@/views/layout/components/f-main/components/products-wall/products-wall'
export default {
  components: {
    Category,
    ProductsWall
  }
}
</script>
