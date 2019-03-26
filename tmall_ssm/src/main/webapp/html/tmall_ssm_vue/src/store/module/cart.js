import {setCartList, getCartList, removeCartList, getOidList, setOidList, removeOidList} from '@/util/auth'
const cart = {
  state: {
    cartList: getCartList(),
    oidList: getOidList()
  },
  mutations: {
    SET_CART_LIST: (state, cartList) => {
      state.cartList = cartList
      setCartList(cartList)
    },
    REMOVE_CART_LIST: (state) => {
      removeCartList()
    },
    SET_OID_LIST: (state, oidList) => {
      state.oidList = oidList
      setOidList(oidList)
    },
    REMOVE_OID_LIST: (state) => {
      removeOidList()
    }
  },
  actions: {
    setCartList ({commit, state}, cartList) {
      commit('SET_CART_LIST', cartList)
    },
    removeCartList ({commit, state}) {
      commit('REMOVE_CART_LIST')
    },
    setOidList ({commit, state}, oidList) {
      commit('SET_OID_LIST', oidList)
    },
    removeOidList ({commit, state}) {
      commit('REMOVE_OID_LIST')
    }
  }
}

export default cart
