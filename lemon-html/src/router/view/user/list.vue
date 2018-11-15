<template>
    <div id="content">
        <div class="table">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item><i class="el-icon-lx-favor"></i>员工列表</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="container" style="border:0px">
                <div class="content-head-box">
                    <el-button type="primary" icon="add" class="head-left" @click="add">添加</el-button>
                    <el-input v-model="search_word" placeholder="姓名" class="search-input">
                    </el-input>
                    <el-button type="primary" icon="el-icon-search" @click="list">搜索</el-button>
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
                        prop="gender"
                        label="性别"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="birthday"
                        label="生日"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="mobile"
                        label="手机号"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="email"
                        label="邮箱"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="createTime"
                        label="入职时间"
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
                                   :total="total" :page-size="ConfigData.pageSize">
                    </el-pagination>
                </div>
            </div>
        </div>


        <!--添加弹出框 -->
        <el-dialog title="添加员工" :visible.sync="formVisible" width="45%">
            <el-form :rules="rules" ref="ruleForm" :model="ruleForm" class="demo-ruleForm" size="medium"
                     label-width="100px">
                <el-form-item label="工号" prop="userNo">
                    <el-input v-model="ruleForm.userNo"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="userName">
                    <el-input v-model="ruleForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="ruleForm.nickName"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio v-model="ruleForm.gender" label="1">男</el-radio>
                    <el-radio v-model="ruleForm.gender" label="0">女</el-radio>
                </el-form-item>
                <el-form-item label="入职时间">
                    <el-date-picker type="datetime" placeholder="选择日期" v-model="ruleForm.createTime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="手机号" prop="mobile">
                    <el-input v-model="ruleForm.mobile"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>
                <el-form-item label="生日">
                    <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.birthday"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="formVisible = false">取 消</el-button>
                <el-button type="primary" @click="save('ruleForm')">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                formVisible: false,
                ruleForm: {
                    userId: '',
                    userNo: '',
                    userName: '',
                    nickName: '',
                    gender: '',
                    mobile: '',
                    createBy: 0,
                    email: '',
                    birthday: '',
                    createTime: ''
                },
                search_word: '',
                isAdd: false,
                loading: false,
                tableData: [],
                total: 0,
                rules: {
                    userNo: [
                        {required: true, message: '请输入员工工号', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    userName: [
                        {required: true, message: '请输入员工姓名', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    // createTime: [
                    //     {type: 'date', required: true, message: '请选择入职时间', trigger: 'change'}
                    // ],
                    mobile: [
                        {required: true, message: '请输入手机号', trigger: 'blur'}
                    ]
                }
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
                this.formVisible = true;
            },
            edit: function (id) {
                this.ruleForm.userId = id;
                this.formVisible = true;

            },
            delete: function (id) {

            },
            save: function (formName) {
                let vm = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$axios.post('user/save', this.ruleForm)
                            .then(function (res) {
                                //保存完成，重置所有输入框
                                vm.$refs[formName].resetFields();
                                vm.list(0);
                            })
                        this.formVisible = false;
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
</style>
