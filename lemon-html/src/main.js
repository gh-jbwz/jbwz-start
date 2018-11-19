import Vue from 'vue';
import App from './App';
import router from './router';
import axios from './util/Axios';
import ElementUI from 'element-ui';
import ConfigData from "./common/ConfigData";
import Cookies from "./common/Cookies";
import CommonUtil from "./util/CommonUtil";
import bus from "./components/common/bus";
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import 'static/css/font_830376_qzecyukz0s.css'; // 浅绿色主题
import 'static/css/index.css'; // 浅绿色主题
import 'static/css/icon.css';
import "babel-polyfill";

window.bus = bus;
Vue.use(ElementUI, {size: 'small'});
Vue.prototype.$axios = axios;
Vue.prototype.$cookies = Cookies;
Vue.prototype.$configData = ConfigData;
Vue.prototype.$commonUtil = CommonUtil;

const app = new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
