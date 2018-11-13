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
        if (res.data.code) {
            //处理业务逻辑错误码
            console.info("code>>>", res);
            if (res.data.code == 0) {
                return res;
            } else {
                if (res.data.code == 2100) {
                    router.push({
                        path: "/login"
                    });
                }
                Message({
                    showClose: true,
                    message: res.data.msg,
                    type: "error"
                });
                return Promise.reject(res.data.msg);
            }
        }
    },
    error => {
        // if (localStorage.getItem("token")) {
        //     router.push({
        //         path: "/login"
        //     });
        // } else {
        //     // 若是有基础信息的情况下,判断时间戳和当前的时间,若是当前的时间大于服务器过期的时间
        //     // 乖乖的返回去登录页重新登录
        //     let lifeTime =
        //         JSON.parse(window.localStorage.getItem("loginUserBaseInfo")).lifeTime *
        //         1000;
        //     let nowTime = new Date().getTime(); // 当前时间的时间戳
        //     console.log(nowTime, lifeTime);
        //     console.log(nowTime > lifeTime);
        //     if (nowTime > lifeTime) {
        //         Message({
        //             showClose: true,
        //             message: "登录状态信息过期,请重新登录",
        //             type: "error"
        //         });
        //         router.push({
        //             path: "/login"
        //         });
        //     } else {
        //         // 下面是接口回调的satus ,因为我做了一些错误页面,所以都会指向对应的报错页面
        //         if (error.response.status === 403) {
        //             router.push({
        //                 path: "/error/403"
        //             });
        //         } else if (error.response.status === 500) {
        //             router.push({
        //                 path: "/error/500"
        //             });
        //         }
        //         if (error.response.status === 502) {
        //             router.push({
        //                 path: "/error/502"
        //             });
        //         }
        //         if (error.response.status === 404) {
        //             router.push({
        //                 path: "/error/404"
        //             });
        //         }
        //     }
        // }
        // 返回 response 里的错误信息
        let errorInfo = error.data ? error.data : error;
        return Promise.reject(errorInfo);
    }
);
export default Axios;
