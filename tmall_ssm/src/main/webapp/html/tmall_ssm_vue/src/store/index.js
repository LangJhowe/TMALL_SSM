import Vuex from 'vuex'
import user from './module/user'
import getters from './getters'
import permission from './module/permission'
import category from './module/category'
import cart from './module/cart'

export default new Vuex.Store({
  modules: {
    user,
    permission,
    category,
    cart
  },
  getters
})
