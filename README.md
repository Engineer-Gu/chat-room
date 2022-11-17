# 项目介绍

项目名：chat-room

项目类型：聊天室

项目所用技术：JavaSE、Maven、JDBC、MySQL5

开发周期：一星期

部署流程：

1、在resource目录下修改jdbc.properties文件的数据库连接信息

2、在resource目录下找到tb_user.sql文件，并在数据库中执行该sql文件

3、克隆项目到本地，把resource文件下的lib文件里的MySQL驱动装配到项目中

步骤如下：

- 选中MySQL驱动文件，右键，选择 Add as Library

- 在弹出来的弹窗中选择OK，即可加载完成驱动

启动：

在server的starter类中启动服务端，在client的starter类中启动客户端，如果要群聊，可以启动多个客户端