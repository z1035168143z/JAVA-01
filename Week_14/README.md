### 限流
1. 限定必须使用app进行秒杀抢购，前端使用app内嵌sdk进行验证；使用CDN静态资源缓存。
2. 前端限制第一次后，每次需要输入验证码；单个ip限制访问次数。
3. 网关对每台机器的单位时间访问次数做限制。

### 库存
1. 将总库存分为多份，按单元部署服务，各单元享有一部分库存。
2. 库存扣减成功，增加一条待生成订单prepare阶段。提交事务，发送消息；消息发送成功视为成功。
