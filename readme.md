1. 进入项目显示所有需要进行秒杀的秒杀活动
2. 点击秒杀活动进行手机校验
3. 校验成功显示具体的秒杀详情，并根据秒杀时间和当前时间进行倒计时显示
4. 倒计时结束请求暴露秒杀的URL，并返回一个根据秒杀编号进行MD5编码的MD5值，该URL中包含返回的MD5值
5. 点击秒杀进行秒杀，在后台需要校验传入的MD5只，秒杀的时间校验才开始进行秒杀操作
6. 秒杀操作是减少秒杀数量，生产秒杀成功的秒杀商品记录，返回秒杀成功的商品记录