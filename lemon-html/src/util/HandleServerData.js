export default {
    install(Vue, options) {
        function handlePageNumber(pageNumber) {
            return isNaN(pageNumber) || pageNumber == undefined ? 0 : pageNumber - 1;
        }

        Vue.prototype.parseTableDataResponse = function (res) {
            let content = res.data.data.content;
            let total = res.data.data.totalElements;
            return {
                "content": content,
                "total": total
            }
        }
        Vue.prototype.resetRuleForm = function (vm) {
            vm.$refs['ruleForm'].resetFields();
        }
        Vue.prototype.deleteOneRow = function (vm, url) {
            vm.$message.success("几把娃子,这个功能等着你统一做来.");
        }
        Vue.prototype.closeDialogRuleForm = function (vm) {
            this.resetRuleForm(vm);
            if (vm.isDetailShow) {
                vm.isDetailShow = false;
            } else {
            }
            vm.formVisible = false;
        }
        Vue.prototype.fillTableData = function (vm, res) {
            let data = vm.parseTableDataResponse(res);
            vm.tableData = data.content;
            vm.total = data.total;
            vm.loading = false;
        }
        Vue.prototype.getPageTable = function (vm, currentPage, url, inParams) {
            vm.loading = true;
            let defaultParam = {
                "page": handlePageNumber(currentPage),
                "size": vm.$configData.pageSize
            }
            let params = Object.assign(defaultParam, inParams);
            vm.$axios.get(url, {
                params: params
            }).then(function (res) {
                vm.fillTableData(vm, res);
            })
        }
        Vue.prototype.commitRuleForm = function (vm, formName, url) {
            vm.$refs[formName].validate((valid) => {
                if (valid) {
                    vm.$axios.post(url, vm.ruleForm)
                        .then(function (res) {
                            //保存完成，重置所有输入框
                            vm.$refs[formName].resetFields();
                            vm.list(0);
                        })
                    vm.formVisible = false;
                } else {
                    return false;
                }
            });
        }
    }
}

