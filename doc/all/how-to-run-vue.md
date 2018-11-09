# 如何使用vue
用浏览器运行从GitHub上download一个vue.js的开源项目

> 注意事项：运行cmd时候，全程使用管理员身份运行

此文档所使用的vue框架地址：https://github.com/lin-xin/vue-manage-system

#### 使用步骤：
  1. 首先下载安装node.js（安装过程中添加node到系统path）
  
         安装完以后测试：（以windows的cmd为例）
		  
        （1）、运行命令：node -v   查看node版本信息（失败我也不知道啥现象）
		
        （2）、运行命令：npm  -v   查看包管理器版本
      
  2. 修改依赖包的仓库，npm安装的话，经常失败。具体操作如下：
  
        `npm install -g cnpm --registry=http://registry.npm.taobao.org`
		
        安装成功后，后续均使用cnpm命令
        
  3. 安装webpack
        
        `cnpm install webpack -g`
   
   4. 安装vue-cli（vue的命令行工具）
        
       ` cnpm install vue-cli -g`
        
  5. 运行项目：先cd到项目目录，github下载的项目，然后分两步：
  
        `cnpm  install`
		
        `cnpm  run dev`
		
		(不出意外会有个： http://localhost:8080，浏览器打开运行)
  完了。  
  -(".")-
    
        
        
