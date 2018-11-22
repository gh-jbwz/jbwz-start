import axios from "axios";
import qs from "qs";
import {Message} from "element-ui";
import router from "../router";

const Axios = axios.create({
    baseURL: "/oa",
    timeout: 10000,
    responseType: "json",
    withCredentials: true, // 是否允许带cookie这些
    headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
    }
});

//POST传参序列化(添加请求拦截器)
Axios.interceptors.request.use(
    config => {
        // 在发送请求之前做某件事
        if (config.method === "post") {// 序列化
            config.data = qs.stringify(config.data);
        }
        // if (localStorage.token) {
        //     config.headers.Authorization = localStorage.token;
        // }
        return config;
    },
    error => {
        Message({
            // element ui 的消息弹窗组件,类似toast
            showClose: true,
            message: error,
            type: "error.data.error.message"
        });
        return Promise.reject(error.data.error.message);
    }
);

//返回状态判断(添加响应拦截器)
Axios.interceptors.response.use(
    res => {
        console.info("服务器返回信息>>>", res);
        let code = res.data.code;
        let msg = res.data.msg;
        if (code != undefined) {
            //处理业务逻辑错误码
            if (code == 0) {
                return res;
            } else {
                if (code == 2001) {//密码错误
                    return res;
                }
                if (code == 2100) {//未登录
                    router.push({
                        path: "/login"
                    });
                }
                Message({
                    showClose: true,
                    message: msg,
                    type: "error"
                });
                return Promise.reject(msg);
            }
        } else {
            return res;
        }
    },
    error => {
        alert("服务器错误,请联系管理员");
        // 返回 response 里的错误信息
        let errorInfo = error.data ? error.data : error;
        return Promise.reject(errorInfo);
    }
);
export default Axios;
