import {getUser, removeUser} from '@/util/auth'
const user = {
  state: {
    userInfo: '',
    user: getUser() && getUser().name,
    token: ''
  },
  mutations: {
    FILL_USER: (state, user) => {
      state.user = user
    },
    LOGOUT: (state) => {
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
    }
  }
}

export default user
