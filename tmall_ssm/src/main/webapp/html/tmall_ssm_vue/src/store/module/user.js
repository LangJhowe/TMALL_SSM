import {getUser, removeUser, setUser} from '@/util/auth'
const user = {
  state: {
    userInfo: '',
    user: getUser(),
    token: ''
  },
  mutations: {
    FILL_USER: (state, user) => {
      state.user = user
    },
    LOGOUT: (state) => {
      state.user = getUser()
    },
    SET_CART_NUM: (state, num) => {
      var newUser = getUser()
      newUser.cartNum = num
      setUser(newUser)
      state.user = getUser()
    },
    REDUCE_CART_NUM: (state, num) => {
      var newUser = getUser()
      newUser.cartNum = newUser.cartNum - num
      setUser(newUser)
      state.user = getUser()
    }
  },
  actions: {
    fillUser: ({commit, state}, user) => {
      commit('FILL_USER', user)
    },
    logout: ({commit, state}) => {
      removeUser()
      commit('LOGOUT')
    },
    setCartNum: ({commit, state}, num) => {
      commit('SET_CART_NUM', num)
    },
    reduceCartNum: ({commit, state}, num) => {
      commit('REDUCE_CART_NUM', num)
    }
  }
}

export default user
