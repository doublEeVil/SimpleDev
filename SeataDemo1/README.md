## SeataDemo1运行说明
1. 启动SeataDemo1_eureka 作用和zookeeper效果一样
2. 启动Seata Server,由于其启动参数适用于JDK8，如果切换到OPEN JDK15，会出现问题（某些调优参数不存在）
3. 建立数据库和表。默认就是各个项目的数据库，此外，每个库还需要一张通用的undo_log的表
4. 启动order server
5. 启动account server
6. 启动storage server 