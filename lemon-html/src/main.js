import Vue from 'vue';
import App from './App';
import router from './router';
import axios from './util/Axios';
import ElementUI from 'element-ui';
import ConfigData from "./common/ConfigData";
import CommonUtil from "./util/CommonUtil";
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import '../static/css/icon.css';
import "babel-polyfill";

Vue.use(ElementUI, {size: 'small'});
Vue.prototype.$axios = axios;
Vue.prototype.ConfigData = ConfigData;
Vue.prototype.CommonUtil = CommonUtil;

const app = new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
