# Mysql设置用户权限
#### 使root用户能外网访问
```
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;  

FLUSH PRIVILEGES;
```
#### 创建数据库
```
CREATE DATABASE `jbwz` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
```
#### 创建用户
```
CREATE USER 'hello'@'%' IDENTIFIED BY 'hello';
```
#### 给用户赋权限
```
GRANT ALL ON jbwz.* TO hello;
```
#### 收回用户权限
```
#收回hello用户的jbwz这个数据库的所有权限

REVOKE ALL ON jbwz.* FROM 'hello'@'%';

#收回hello用户的所有权限

REVOKE ALL ON *.* FROM 'hello'@'%';
```
#### 修改用户密码
```
use mysql;

UPDATE USER SET AUTHENTICATION_STRING = password('root'), PASSWORD_EXPIRED = 'N', PASSWORD_LAST_CHANGED = now() where user = 'root';

FLUSH PRIVILEGES;

#或者

ALTER USER 'root'@'localhost' IDENTIFIED BY 'root'
```
