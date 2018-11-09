## （一）项目中的插件
### vue-schart
类似百度eCharts.js的图表插件，地址：[vue-schart](https://github.com/lin-xin/vue-schart)
### element-ui
基于vue.js 2.0的组件库。地址：[element](http://element.eleme.io/#/zh-CN/component/layout)
### Vue-Quill-Editor
基于Quill、适用于Vue2的富文本编辑器。地址：[vue-quill-editor](https://github.com/surmon-china/vue-quill-editor)
### mavonEditor
基于Vue的markdown编辑器。地址:[mavonEditor](https://github.com/hinesboy/mavonEditor)
### vue-cropper.js
一个封装了 cropperjs 的 Vue 组件，用于裁剪图片。地址：[vue-cropperjs](https://github.com/Agontuk/vue-cropperjs)
### Vue.Draggable
Vue.Draggable：基于 Sortable.js 的 Vue 拖拽组件。 地址：[Vue.Draggable](https://github.com/SortableJS/Vue.Draggable)

## （二）如何删除组件

### 具体步骤
1. 删除该组件的路由，在目录`src/router/index.js`中，找到引入该组件的路由，删除相关代码
``` 
 {
        // 富文本编辑器组件
        path: '/editor',
        component: resolve => require(['../components/page/VueEditor.vue'], resolve),
        meta: { title: '富文本编辑器' }
 },
```
 2. 在目录`src/components/page/`中删除该组件相关的`VueEditor.vue`文件。
 3. 执行以下命令，卸载该组件：
    `cnpm un vue-quill-editor -S`
