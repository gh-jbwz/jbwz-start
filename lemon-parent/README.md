# 后端java开发项目
#### 项目结构
```javascript
|-- lemon-server
   |-- release                          // 项目发布目录结构,不需要关注
   |-- src                              // 源码目录
   |-- release-app.sh                   // 发布项目脚本,需要上线时执行这个脚本
   |-- lemon-server-x.x.x.tar.gz        // 最终项目上线包(由release-app.sh生成),把这个tar包上传到阿里云服务器 /jbwz/app 目录下    
|-- lemon-generator          // 自动生成代码项目配置
|-- generated                //自动生成的代码位置
```
