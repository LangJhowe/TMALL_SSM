export function formatPrice (number, decimals, descPoint, thousandsSep) {
  /*
        * 参数说明：
        * number：要格式化的数字
        * decimals：保留几位小数
        * dec_point：小数点符号
        * thousandsSep：千分位符号
        * */
  number = (number + '').replace(/[^0-9+-Ee.]/g, '')
  var n = !isFinite(+number) ? 0 : +number
  var prec = !isFinite(+decimals) ? 0 : Math.abs(decimals)
  var sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep
  var dec = (typeof descPoint === 'undefined') ? '.' : descPoint
  var s = ''
  var toFixedFix = function (n, prec) {
    var k = Math.pow(10, prec)
    return '' + Math.floor(n * k) / k
  }
  s = (prec ? toFixedFix(n, prec) : '' + Math.floor(n)).split('.')
  var re = /(-?\d+)(\d{3})/
  while (re.test(s[0])) {
    s[0] = s[0].replace(re, '$1' + sep + '$2')
  }

  if ((s[1] || '').length < prec) {
    s[1] = s[1] || ''
    s[1] += new Array(prec - s[1].length + 1).join('0')
  }
  return s.join(dec)
}

export function formatDate (dateStr) {
  var date = new Date(dateStr)
  var year = date.getFullYear()
  var month = parseFloat(date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)
  var day = parseFloat(date.getDate()) < 10 ? '0' + date.getDate() : date.getDate()
  var array = [year, month, day]
  return array.join('-')
}

export function formatTime (dateStr) {
  var date = new Date(dateStr)
  var hour = parseFloat(date.getHours()) < 10 ? '0' + date.getHours() : date.getHours()
  var min = parseFloat(date.getMinutes()) < 10 ? '0' + date.getMinutes() : date.getMinutes()
  var seconds = parseFloat(date.getSeconds()) < 10 ? '0' + date.getSeconds() : date.getSeconds()
  var array = [hour, min, seconds]
  return array.join(':')
}
