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
                    <el-button v-if="hasPermission('user/save')" type="primary" icon="add" class="head-left"
                               @click="handleButtonAction('add')">添加
                    </el-button>
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
                        prop="createTime"
                        label="创建时间"
                        header-align="center"
                        align="center"
                        width="150px"
                    >
                    </el-table-column>
                    <el-table-column
                        label="操作"
                        header-align="center"
                        align="center"
                        width="230px"
                    >
                        <template slot-scope="scope">
                            <el-button @click="handleButtonAction('detail',scope.row)" type="text"
                                       icon="el-icon-tickets" size="small">查看
                            </el-button>
                            <el-button v-if="hasPermission('user/update')"
                                       @click="handleButtonAction('edit',scope.row)" type="text"
                                       icon="el-icon-edit" size="small">编辑
                            </el-button>
                            <el-button v-if="hasPermission('user/delete')" @click="deleted(scope.row)" type="text"
                                       icon="el-icon-delete" class="red"
                                       size="small">删除
                            </el-button>
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
        <el-dialog :title="dialogFormTitle" @closed="dialogClosed" :visible.sync="dialogFormVisible"
                   width="45%">
            <el-form :rules="rules" ref="ruleForm" :model="ruleFormData" size="medium"
                     label-width="100px">
                <el-form-item label="id" hidden="hidden" prop="userId">
                    <el-input type="hidden" v-model="ruleFormData.userId"></el-input>
                </el-form-item>
                <el-form-item label="工号" prop="userNo">
                    <el-input v-model="ruleFormData.userNo"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="userName">
                    <el-input v-model="ruleFormData.userName"></el-input>
                </el-form-item>
                <el-form-item label="昵称" prop="nickName">
                    <el-input v-model="ruleFormData.nickName"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio v-model="ruleFormData.gender" label="1">男</el-radio>
                    <el-radio v-model="ruleFormData.gender" label="0">女</el-radio>
                </el-form-item>
                <el-form-item label="入职时间" prop="entryDate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="ruleFormData.entryDate"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="手机号" prop="mobile">
                    <el-input v-model="ruleFormData.mobile"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="ruleFormData.email"></el-input>
                </el-form-item>
                <el-form-item label="生日" prop="birthday">
                    <el-date-picker type="date" placeholder="选择日期" v-model="ruleFormData.birthday"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button v-if="!isDetailButton" @click="dialogFormVisible=false">取 消</el-button>
                <el-button type="primary" v-if="!isDetailButton" @click="save">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                /* =====  start  =====  以下变量名称在每个vue文件中都必须保持不变 */
                dialogFormVisible: false,
                dialogFormTitle: '',
                isDetailButton: false,
                isAddButton: false,
                isEditButton: false,
                search_word: '',
                loading: false,
                tableData: [],
                total: 0,
                /* ======  end  ======== */
                //下面的ruleFormData,rules两个变量的名称也必须保持不变,内容需要跟据业务字段改变
                businessName: '用户',//功能名称
                ruleFormData: {
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
            dialogClosed: function () {
                this.closeDialogRuleForm(this);
            },
            //action=add|edit|detail
            handleButtonAction: function (action, row) {
                this.$handleButtonAction(this, action, row);
            },
            save: function () {
                let url = 'error';
                if (this.isAddButton) {
                    url = 'user/save';
                } else if (this.isEditButton) {
                    url = 'user/update';
                }
                this.$cookies.setUserName(this.ruleFormData.userName);
                this.commitRuleForm(this, url)
            },
            list: function (currentPage) {
                this.loading = true;
                this.getPageTable(this, currentPage, 'user/page', {"userName": this.search_word})
            },
            setRuleFormData: function (row) {
                this.ruleFormData.userId = row.userId;
                this.ruleFormData.userName = row.userName;
                this.ruleFormData.userNo = row.userNo;
                this.ruleFormData.nickName = row.nickName;
                this.ruleFormData.mobile = row.mobile;
                this.ruleFormData.gender = row.gender;
                this.ruleFormData.email = row.email;
                this.ruleFormData.birthday = row.birthday;
                this.ruleFormData.entryDate = row.entryDate;
            },
            deleted: function (row) {
                this.deleteOneRow(this, '');
            }
        }
    }
</script>

<style scoped>
    .red {
        color: #ff0000;
    }
</style>
