export default [
    {
        path: '/',
        redirect: '/dashboard'
    },
    {
        path: '/',
        component: resolve => require(['./layout/Body.vue'], resolve),
        meta: {title: '说明页', requiredAuth: false},
        children: [
            {
                path: '/userList',
                component: resolve => require(['./view/user/list.vue'], resolve),
                meta: {title: '员工列表'}
            },
            {
                path: '/dashboard',
                component: resolve => require(['./view/Dashboard.vue'], resolve),
                meta: {title: '首页'}
            },
            {
                path: '/icon',
                component: resolve => require(['./view/Icon.vue'], resolve),
                meta: {title: '自定义图标'}
            },
            {
                path: '/table',
                component: resolve => require(['./view/BaseTable.vue'], resolve),
                meta: {title: '基础表格'}
            },
            {
                path: '/tabs',
                component: resolve => require(['./view/Tabs.vue'], resolve),
                meta: {title: 'tab选项卡'}
            },
            {
                path: '/form',
                component: resolve => require(['./view/BaseForm.vue'], resolve),
                meta: {title: '基本表单'}
            },
            {
                // 富文本编辑器组件
                path: '/editor',
                component: resolve => require(['./view/VueEditor.vue'], resolve),
                meta: {title: '富文本编辑器'}
            },
            {
                // markdown组件
                path: '/markdown',
                component: resolve => require(['./view/Markdown.vue'], resolve),
                meta: {title: 'markdown编辑器'}
            },
            {
                // 图片上传组件
                path: '/upload',
                component: resolve => require(['./view/Upload.vue'], resolve),
                meta: {title: '文件上传'}
            },
            {
                // vue-schart组件
                path: '/charts',
                component: resolve => require(['./view/BaseCharts.vue'], resolve),
                meta: {title: 'schart图表'}
            },
            {
                // 拖拽列表组件
                path: '/drag',
                component: resolve => require(['./view/DragList.vue'], resolve),
                meta: {title: '拖拽列表'}
            },
            {
                // 权限页面
                path: '/permission',
                component: resolve => require(['./view/Permission.vue'], resolve),
                meta: {title: '权限测试', permission: true}
            },
            {
                path: '/4041',
                component: resolve => require(['./view/404.vue'], resolve),
                meta: {title: '404', requiredAuth: false}
            },
            {
                path: '/403',
                component: resolve => require(['./view/403.vue'], resolve),
                meta: {title: '403', requiredAuth: false}
            }
        ]
    },
    {
        path: '/login',
        component: resolve => require(['./view/Login.vue'], resolve),
        meta: {requiredAuth: false}
    },
    {
        path: '/404',
        component: resolve => require(['./view/404.vue'], resolve),
        meta: {title: '404', requiredAuth: false}
    },
    {
        path: '*',
        redirect: '/4041'
    }
]

function lazyLoadView(AsyncView) {
    const AsyncHandler = () => ({
        component: AsyncView,
        // A component to use while the component is loading.
        // loading: require('@views/_loading').default,
        // Delay before showing the loading component.
        // Default: 200 (milliseconds).
        delay: 400,
        // A fallback component in case the timeout is exceeded
        // when loading the component.
        // error: require('@views/_timeout').default,
        // Time before giving up trying to load the component.
        // Default: Infinity (milliseconds).
        timeout: 10000,
    });

    return Promise.resolve({
        functional: true,
        render(h, {data, children}) {
            // Transparently pass any props or children
            // to the view component.
            return h(AsyncHandler, data, children)
        },
    })
}
