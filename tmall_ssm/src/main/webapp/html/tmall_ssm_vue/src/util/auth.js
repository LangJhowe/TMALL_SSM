const USER = 'user'
const TOKEN = 'token'
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
