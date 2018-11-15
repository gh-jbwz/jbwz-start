import Vue from 'vue';
import VueRouter from 'vue-router';
import routers from './routers';

Vue.use(VueRouter);

const router = new VueRouter({
    routes: routers
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    let requiredAuth = to.meta.requiredAuth;

    //需要认证的再判断是否有token
    if (requiredAuth == undefined || requiredAuth) {
        if (localStorage.token) {  // 获取当前的token是否存在
            // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
            if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
                Vue.prototype.$alert('富文本编辑器不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                    confirmButtonText: '确定'
                });
            } else {
                next();
            }
        } else {
            next({
                path: '/login',
                query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
        }
    } else {
        next();
    }
});

export default router
