# 前端html开发项目 目录结构
|-- build                            // webpack配置文件
|-- config                           // 项目打包路径
|-- src                              // 源码目录
|   |-- components                   // 组件文件夹
|       |-- common                   // 公共组件
|           |-- bus.js           	   // Event Bus
|           |-- Header.vue           // 公共头部
|           |-- Home.vue           	 // 公共路由入口
|           |-- Sidebar.vue          // 公共左边栏
|           |-- Tags.vue           	 // 页面切换标签组件
|       |-- page                   	 // 主要路由页面
|           |-- 403.vue
|           |-- 404.vue
|           |-- BaseCharts.vue       // 基础图表
|           |-- BaseForm.vue         // 基础表单
|           |-- BaseTable.vue        // 基础表格
|           |-- DashBoard.vue        // 系统首页
|           |-- DragList.vue         // 拖拽列表组件
|           |-- Icon.vue			       // 自定义图标组件
|           |-- Login.vue          	 // 登录
|           |-- Markdown.vue         // markdown组件
|           |-- Premission.vue       // 权限测试组件
|           |-- Upload.vue           // 图片上传
|           |-- VueEditor.vue        // 富文本编辑器
|   |-- App.vue                      // 页面入口文件
|   |-- main.js                      // 程序入口文件，加载各种公共组件
|-- .babelrc                         // ES6语法编译配置
|-- .editorconfig                    // 代码编写规格
|-- .gitignore                       // 忽略的文件
|-- index.html                       // 入口html文件
|-- package.json                     // 项目及工具的依赖配置文件
|-- README.md                        // 说明
