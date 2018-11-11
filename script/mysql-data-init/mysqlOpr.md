>Mysql使用步骤：
## 登陆
	mysql -u root -p 
输入密码
## 创建数据库
	create database jbwz default charset utf8;
## 创建用户
	create user 'jbwz'@'localhost' indentified by '123456';
## 授权
	grant  all privileges on jbwz.* to jbwz@localhost identified by '123456';
## 刷新
	flus privileges;
## 删除数据库
	drop database jbwz;
## 删除用户
	drop user jbwz@localhost;

## 其他
	show databases;
	select host,user from mysql.user;