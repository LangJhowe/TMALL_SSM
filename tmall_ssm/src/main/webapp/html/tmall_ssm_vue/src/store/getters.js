const getters = {
  token: state => state.user.token,
  name: state => state.user.name,
  userInfo: state => state.user.userInfo,
  category: state => state.category.data
}

export default getters
