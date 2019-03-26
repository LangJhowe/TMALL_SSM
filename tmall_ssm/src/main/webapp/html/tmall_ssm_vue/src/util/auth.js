const USER = 'user'
const TOKEN = 'token'
const CART_LIST = 'cartList'
const OID_LIST = 'oidList'
export function getUser () {
  return JSON.parse(sessionStorage.getItem(USER))
}

export function setUser (user) {
  sessionStorage.setItem(USER, JSON.stringify(user))
}

export function removeUser (user) {
  sessionStorage.removeItem(USER)
}

export function getToken () {
  return sessionStorage.getItem(TOKEN)
}

export function setToken (token) {
  sessionStorage.setItem(TOKEN, JSON.stringify(token))
}

export function removeToken (token) {
  sessionStorage.removeItem(TOKEN)
}

export function getCartList () {
  return JSON.parse(sessionStorage.getItem(CART_LIST))
}

export function setCartList (cartList) {
  sessionStorage.setItem(CART_LIST, JSON.stringify(cartList))
}

export function removeCartList () {
  sessionStorage.removeItem(CART_LIST)
}

export function getOidList () {
  return JSON.parse(sessionStorage.getItem(OID_LIST))
}

export function setOidList (oidList) {
  sessionStorage.setItem(OID_LIST, JSON.stringify(oidList))
}

export function removeOidList () {
  sessionStorage.removeItem(OID_LIST)
}
