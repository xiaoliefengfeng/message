# message
1.游戏开发中(或其他cs程序开发中)自定义消息生成工具，可以生成自定义了二进制消息java消息bean和google protobuff消息出来bean,客户端生成cpp，lua
2.作者修改google protobuf的自定义生成工具主要是protobuf生成的java文件体积过于庞大，结构不太优美。作者不太喜欢。所以就实现了这个工具
    以此来生成一个比较漂亮的java的protobuf文件
3.目前java的protobuf文件只有一些简单的protobuf属性，如支持required,optional,repeated这3个配置属性。支持必要字段的属性校验isInitialized
  还有就是数据的序列化
4.目前protobuf消息生成工具不支持其他的扩张属性。。再次强调。这只是一个简单的protobuf工具。为的是能够生成漂亮一点的java bean
5.支持cpp 和lua的消息映射