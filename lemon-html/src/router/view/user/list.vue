<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-favor"></i>员工列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="border:0px">
            <div class="handle-box">
                <el-button type="primary" icon="add" class="handle-add mr10" @click="add">添加</el-button>
                <el-input v-model="search_word" placeholder="姓名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="list">搜索</el-button>
            </div>
            <el-table v-loading="loading" :data="tableData" border align="center" style="width: 100%">
                <el-table-column
                    prop="userId"
                    label="id"
                >
                </el-table-column>
                <el-table-column
                    prop="userNo"
                    label="工号"
                >
                </el-table-column>
                <el-table-column
                    prop="userName"
                    label="姓名"
                >
                </el-table-column>
                <el-table-column
                    prop="nickName"
                    label="昵称"
                >
                </el-table-column>
                <el-table-column
                    prop="mobile"
                    label="手机号"
                >
                </el-table-column>
                <el-table-column
                    prop="sex"
                    label="性别"
                >
                </el-table-column>
                <el-table-column
                    prop="email"
                    label="邮箱"
                >
                </el-table-column>
                <el-table-column
                    label="操作"
                >
                    <template slot-scope="scope">
                        <el-button @click="detail(scope.row.userId)" type="text" size="small">查看</el-button>
                        <el-button @click="edit(scope.row.userId)" type="text" size="small">编辑</el-button>
                        <el-button @click="delete(scope.row.userId)" type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination" v-if="total>0">
                <el-pagination background @current-change="list" layout="prev, pager, next"
                               :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                formData: {},
                search_word: '',
                isAdd: false,
                loading: false,
                tableData: [],
                total: 0
            }
        },
        created() {
            this.list()
        },
        methods: {
            list: function (pageNumber) {
                let vm = this;
                this.loading = true;
                this.$axios.get('user/page', {
                    params: {
                        "page": this.CommonUtil.getPageNumber(pageNumber),
                        "size": this.ConfigData.pageSize,
                        "userName": this.search_word
                    }
                }).then(function (res) {
                    vm.CommonUtil.fillTable(vm, res);
                })
            },
            add: function () {

            },
            edit: function () {

            },
            delete: function (id) {

            },
            save: function () {
                this.$axios.post('user/save', this.formData)
                    .then(function (res) {
                    })
                this.isAdd = false;
            }
        }
    }
</script>

<style scoped>

</style>
