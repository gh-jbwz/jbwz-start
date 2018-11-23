export default {
    install(Vue, options) {
        let ruleFormName = 'ruleForm';

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
            for (let item in vm.ruleFormData) {
                vm.ruleFormData[item] = '';
            }
            // vm.$refs['ruleForm'].resetFields();
        }
        Vue.prototype.deleteOneRow = function (vm, url) {
            vm.$message.success("几把娃子,这个功能等着你统一做来.");
        }
        Vue.prototype.closeDialogRuleForm = function (vm) {
            this.resetRuleForm(vm);
            vm.$refs[ruleFormName].clearValidate();
            if (vm.isDetailButton) {
                vm.isDetailButton = false;
            }
            if (vm.isEditButton) {
                vm.isEditButton = false;
            }
            if (vm.isAddButton) {
                vm.isAddButton = false;
            }
            vm.dialogFormVisible = false;
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
        Vue.prototype.commitRuleForm = function (vm, url, callBack) {
            vm.$refs[ruleFormName].validate((valid) => {
                if (valid) {
                    vm.$axios.post(url, vm.ruleFormData)
                        .then(function (res) {
                            //保存完成，重置所有输入框
                            if (callBack) {
                                callBack(vm);
                            }
                            vm.list(0);
                        })
                    vm.dialogFormVisible = false;
                } else {
                    return false;
                }
            });
        }
        /* 权限设置 */
        let permissionObj = ["user/save", "user/delete", "user/update", "resource/save", "resource/update"]
        Vue.prototype.permissionData = permissionObj;
        Vue.prototype.hasPermission = function (resourceUrl) {
            return this.permissionData.includes(resourceUrl);
        }

        /* 界面弹框方法 */
        Vue.prototype.$handleButtonAction = function (vm, action, row) {
            if (action) {
                if (action === "detail") {
                    vm.dialogFormTitle = "查看" + vm.businessName;
                    vm.isDetailButton = true;
                    vm.setRuleFormData(row);
                } else if (action === "edit") {
                    vm.dialogFormTitle = "编辑" + vm.businessName;
                    vm.isEditButton = true;
                    vm.setRuleFormData(row);
                } else if (action === "add") {
                    vm.dialogFormTitle = "添加" + vm.businessName;
                    vm.isAddButton = true;
                }
                vm.dialogFormVisible = true;
            }
        }
    }
}

