`（必做）思考和设计自定义MQ第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码，提交到github。`
1. 将消息保存在服务端，使用Map<String, Array> 存储具体的消息内容
2. 服务端消息Array中记录当前写的位置，同时记录 消费者消费位置 Map<ConsumerUnionId, Offset>
3. 消费者拉取指定topic消息，可以指定offset，不指定则使用服务端记录的offset