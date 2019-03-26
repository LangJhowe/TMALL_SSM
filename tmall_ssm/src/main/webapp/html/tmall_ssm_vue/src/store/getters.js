const getters = {
  token: state => state.user.token,
  name: state => state.user.name,
  userInfo: state => state.user.userInfo,
  user: state => state.user.user,
  category: state => state.category.data,
  cartList: state => state.cart.cartList,
  oidList: state => state.cart.oidList
}

export default getters
