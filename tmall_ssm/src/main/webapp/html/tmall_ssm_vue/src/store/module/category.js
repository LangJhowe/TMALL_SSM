import CODES from '@/api/config'
import {getRecommendSearch} from '@/api/home'
const category = {
  state: {
    data: []
  },
  getters: {
    test: function (state) {
      return (state.data)
    }
  },
  mutations: {
    FILL_DATA: (state, data) => {
      state.data = data
    }
  },
  actions: {
    fillData ({commit, state}) {
      return new Promise((resolve, reject) => {
        getRecommendSearch().then((res) => {
          const {data} = res
          if (data.code == CODES.SUCCESS) {
            commit('FILL_DATA', data.data)
            console.log(data.data)
          }
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default category
