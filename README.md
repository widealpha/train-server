# train-server
火车售票系统服务器端

## springboot搭建
参照官网教程
https://spring.io/quickstart

## mysql安装
参照任何地方能用的mysql 8.0以上版本安装教程

## 初始化mysql数据库
1. 新建项目数据库(最好为train)
2. 执行该项目根目录下的init.sql初始化数据库
3. 用python执行根目录下的spider.py从12306爬取数据(完整数据库太大传不上git)
4. 用python执行根目录下的shrink.py格式化数据库爬到的数据

## 初始化支付宝沙箱
> 使用支付宝沙箱功能必须要有一台云服务器
> 如果没有云服务器,请将数据库中order_form表中默认payed的默认值设置为1
1. 申请支付宝开发者用户,并打开沙箱账户
2. 生成公钥密钥等文件,保存在服务器文件夹中(确保springboot项目有读取权限)
3. 修改config下的AlipayConfig将其中的信息变为你的支付宝沙箱信息
4. java8版本在支付的时候回提示缺少X509Cert类,切换为java11即可

## 运行
修改application.yaml,将其中的数据库用户,密码和地址切换为你的数据库
运行TrainApplication.java
