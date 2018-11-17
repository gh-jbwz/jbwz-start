exports.getPageNumber = function (pageNumber) {
    return isNaN(pageNumber) || pageNumber == undefined ? 0 : pageNumber - 1;
}
exports.parseResponse = function (res) {
    let content = res.data.data.content;
    let total = res.data.data.totalElements;
    return {
        "content": content,
        "total": total
    }
}
exports.fillTableData = function (vm, res) {
    let data = this.parseResponse(res);
    vm.tableData = data.content;
    vm.total = data.total;
    vm.loading = false;
}
