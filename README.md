#### 使用方式
[easy-iot SDK](https://gitee.com/damocode/easy-iot)

[easy-iot-spring-boot-starter spring boot 快速集成插件](https://gitee.com/damocode/easy-iot-spring-boot-starter)

- 下载上面两个项目并通过下面的命令依次进行编译

```
mvn clean install -DskipTests
```

- 导入sql,并启动项目

- 通过tcp 客户端工具连接，并发送指令，指令代码如下：
> 5a 00 00 00 11 21 09 22 17 40 01 00 85 37 00 00 00 00 00 00 00 00 03 59 45 00 20 43 00 84 38 13 74 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 58 58 58 58 58 58 58 58 58 58 58 58 58 58 58 58 58 58 58 58 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff 0d 0a

- 执行效果如下：
![执行效果图](https://images.gitee.com/uploads/images/2021/1009/220526_2aa777d3_1996367.jpeg "1.jpg")
