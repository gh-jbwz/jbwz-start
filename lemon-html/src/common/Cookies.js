let tokenName = 'vuetoken';
let userName = 'userName';
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
