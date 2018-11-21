<template>
    <div id="content">
        <div class="table">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item><i class="el-icon-lx-favor"></i>{{businessName}}列表</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="container" style="border:0px">
                <div class="content-head-box">
                    <el-button type="primary" icon="add" class="head-left" @click="add">添加</el-button>
                    <el-input v-model="search_word" placeholder="姓名" class="search-input">
                    </el-input>
                    <el-button type="primary" icon="el-icon-search" @click="list">搜索</el-button>
                </div>
                <el-table v-loading="loading" element-loading-text="拼命加载中..."
                          element-loading-background="rgba(0, 0, 0, 0.8)" :data="tableData" highlight-current-row border
                          style="width: 100%">
                    <el-table-column
                        prop="userId"
                        label="id"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="userNo"
                        label="工号"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="userName"
                        label="姓名"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="nickName"
                        label="昵称"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="gender"
                        label="性别"
                        header-align="center"
                        align="center"
                    >
                        <template slot-scope="scope">
                            <el-tag
                                :type="scope.row.gender === '0' ? 'danger' : 'primary'"
                                disable-transitions>{{scope.row.gender==='0'?'女':'男'}}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="birthday"
                        label="生日"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="mobile"
                        label="手机号"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="email"
                        label="邮箱"
                        header-align="center"
                        align="center"
                        width="150px"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="entryDate"
                        label="入职时间"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="createDate"
                        label="创建时间"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        label="操作"
                        header-align="center"
                        align="center"
                        width="150px"
                    >
                        <template slot-scope="scope">
                            <el-button @click="detail(scope.row)" type="text" size="small">查看</el-button>
                            <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
                            <el-button @click="deleted(scope.row)" type="text" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination" v-if="total>0">
                    <el-pagination background @current-change="list" layout="prev, pager, next"
                                   :total="total" :page-size="$configData.pageSize">
                    </el-pagination>
                </div>
            </div>
        </div>


        <!--添加弹出框 -->
        <el-dialog :title="dialogTitle" @closed="dialogClosed" :visible.sync="formVisible"
                   width="45%">
            <el-form :rules="rules" ref="ruleForm" :model="ruleForm" size="medium"
                     label-width="100px">
                <el-form-item label="id" hidden="hidden" prop="userId">
                    <el-input type="hidden" v-model="ruleForm.userId"></el-input>
                </el-form-item>
                <el-form-item label="工号" prop="userNo">
                    <el-input v-model="ruleForm.userNo"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="userName">
                    <el-input v-model="ruleForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="昵称" prop="nickName">
                    <el-input v-model="ruleForm.nickName"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio v-model="ruleForm.gender" label="1">男</el-radio>
                    <el-radio v-model="ruleForm.gender" label="0">女</el-radio>
                </el-form-item>
                <el-form-item label="入职时间" prop="entryDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.entryDate"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="手机号" prop="mobile">
                    <el-input v-model="ruleForm.mobile"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>
                <el-form-item label="生日" prop="birthday">
                    <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.birthday"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancel('ruleForm')">取 消</el-button>
                <el-button type="primary" v-if="!isDetailShow" @click="save('ruleForm')">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                businessName: '用户',
                formVisible: false,
                dialogTitle: '',
                isDetailShow: false,
                ruleForm: {
                    userId: '',
                    userNo: '',
                    userName: '',
                    nickName: '',
                    gender: '',
                    mobile: '',
                    email: '',
                    birthday: '',
                    entryDate: ''
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
                    mobile: [
                        {required: true, message: '请输入手机号', trigger: 'blur'},
                        {pattern: /^1[3|5|6|7|8|9]\d{9}$/, message: '请输入正确的手机号'}
                    ]
                }
            }
        },
        created() {
            this.list()
        },
        methods: {
            list: function (currentPage) {
                this.loading = true;
                this.getPageTable(this, currentPage, 'user/page', {"userName": this.search_word})
            },
            add: function () {
                this.dialogTitle = "添加" + this.businessName;
                this.formVisible = true;
            },
            edit: function (row) {
                this.resetRuleForm(this)
                this.dialogTitle = "编辑" + this.businessName;
                this.setRuleFormData(row);
                this.formVisible = true;
            },
            detail: function (row) {
                this.resetRuleForm(this)
                this.isDetailShow = true;
                this.dialogTitle = this.businessName + "详情";
                this.setRuleFormData(row);
                this.formVisible = true;
            },
            setRuleFormData: function (row) {
                this.ruleForm.userId = row.userId;
                this.ruleForm.userName = row.userName;
                this.ruleForm.userNo = row.userNo;
                this.ruleForm.nickName = row.nickName;
                this.ruleForm.mobile = row.mobile;
                this.ruleForm.gender = row.gender;
                this.ruleForm.email = row.email;
                this.ruleForm.birthday = row.birthday;
                this.ruleForm.entryDate = row.entryDate;
            },
            deleted: function (row) {
                this.deleteOneRow(this, '');
            },
            cancel: function (formName) {
                this.formVisible = false;
            },
            save: function (formName) {
                this.commitRuleForm(this, formName, 'user/save')
            },
            dialogClosed: function () {
                this.closeDialogRuleForm(this);
            }
        }
    }
</script>

<style scoped>
</style>
