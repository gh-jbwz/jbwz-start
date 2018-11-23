let tokenName = 'vuetoken';
let userName = 'userName';
let userImg = 'userImg';
exports.getToken = function () {
    return localStorage.getItem(tokenName);
}
exports.setToken = function (value) {
    localStorage.setItem(tokenName, value);
}
exports.getUserName = function () {
    return localStorage.getItem(userName);
}
exports.setUserName = function (value) {
    localStorage.setItem(userName, value);
}
exports.getUserImg = function () {
    return localStorage.getItem(userImg);
}
exports.setUserImg = function (value) {
    localStorage.setItem(userImg, value);
}
