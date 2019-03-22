const CODES = {
  SUCCESS: '000000',
  ERROR_CODES: [
    // loginByUser
    {code: '600001', msg: '缺少字段name'},
    {code: '600002', msg: '缺少字段password'},
    {code: '600003', msg: '缺少字段name,password'},
    {code: '600004', msg: '账号密码错误'},
    // registry
    {code: '600011', msg: '缺少字段name'},
    {code: '600012', msg: '缺少字段password'},
    {code: '600013', msg: '用户名和密码不能为空'},
    {code: '600014', msg: '密码不能为空'},
    {code: '600015', msg: '用户名和密码不能为空'},
    {code: '600016', msg: '用户名已经被使用,不能使用'},
    // searchByKeywords
    {code: '600021', msg: '缺少字段keyword'},
    {code: '600022', msg: '缺少参数keyword'},
    /// searchByCategory
    {code: '600031', msg: '缺少字段cid'},
    {code: '600032', msg: '缺少参数cid'},
    {code: '600033', msg: '最大价格限制请输入数字'},
    {code: '600034', msg: '最小价格限制请输入数字'},
    // getProduct
    {code: '600041', msg: '缺少字段pid'},
    {code: '600042', msg: '缺少参数pid'},
    // getReviews
    {code: '600051', msg: '缺少字段pid'},
    {code: '600052', msg: '缺少参数pid'}

  ]
}
export default CODES
