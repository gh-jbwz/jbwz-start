<template>
    <div id="content">
        <div class="table">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item><i class="el-icon-lx-favor"></i>菜单管理</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="container" style="border:0px">
                <div class="content-head-box">
                    <el-button type="primary" icon="add" class="head-left" @click="add">添加</el-button>
                    <el-input v-model="search_word" placeholder="名称" class="search-input">
                    </el-input>
                    <el-button type="primary" icon="el-icon-search" @click="list">搜索</el-button>
                </div>
                <el-table v-loading="loading" element-loading-text="拼命加载中..."
                          element-loading-background="rgba(0, 0, 0, 0.8)" :data="tableData" highlight-current-row border
                          style="width: 100%">
                    <el-table-column
                        hidden="hidden"
                        prop="resourceId"
                        label="资源id"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="resourceName"
                        label="资源名称"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="resourceUrl"
                        label="资源URL"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="router"
                        label="界面路由"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="type"
                        label="类型"
                        header-align="center"
                        align="center"
                    >
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.type === '0' ? 'primary' : 'success'" disable-transitions>
                                {{scope.row.type==='0'?'菜单':'资源'}}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="icon"
                        label="图标"
                        header-align="center"
                        align="center"
                    >
                        <template slot-scope="scope">
                            <i :class='scope.row.icon'></i>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="createTime"
                        label="创建时间"
                        header-align="center"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        label="操作"
                        header-align="center"
                        align="center"
                    >
                        <template slot-scope="scope">
                            <el-button @click="detail(scope.row)" type="text" size="small">查看</el-button>
                            <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
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
        <el-dialog :title="dialogTitle" @closed="dialogClosed" :visible.sync="formVisible" width="45%">
            <el-form :rules="rules" ref="ruleForm" :model="ruleForm" size="medium"
                     label-width="100px">
                <el-form-item label="id" hidden="hidden" prop="resourceId">
                    <el-input type="hidden" v-model="ruleForm.resourceId"></el-input>
                </el-form-item>
                <el-form-item label="资源名称" prop="resourceName">
                    <el-input v-model="ruleForm.resourceName"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="type">
                    <el-radio v-model="ruleForm.type" label="0">菜单</el-radio>
                    <el-radio v-model="ruleForm.type" label="1">资源</el-radio>
                </el-form-item>
                <el-form-item label="访问URL" prop="resourceUrl">
                    <el-input v-model="ruleForm.resourceUrl"></el-input>
                </el-form-item>
                <el-form-item label="界面路由" prop="router">
                    <el-input v-model="ruleForm.router"></el-input>
                </el-form-item>
                <el-form-item label="图标" prop="icon">
                    <el-input v-model="ruleForm.icon"></el-input>
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
                formVisible: false,
                dialogTitle: '',
                isDetailShow: false,
                ruleForm: {
                    resourceId: '',
                    resourceName: '',
                    resourceUrl: '',
                    router: '',
                    icon: '',
                    type: ''
                },
                search_word: '',
                loading: false,
                tableData: [],
                total: 0,
                rules: {
                    resourceName: [
                        {required: true, message: '请输入资源名称', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择类型', trigger: 'blur'},
                    ]
                }
            }
        },
        created() {
            this.list()
        },
        methods: {
            list: function (pageNumber) {
                this.getPageTable(this, pageNumber, 'resource/page', {"resourceName": this.search_word})
            },
            add: function () {
                this.dialogTitle = "添加资源";
                this.formVisible = true;
            },
            edit: function (row) {
                this.dialogTitle = "编辑资源";
                this.setRuleFormData(row);
                this.formVisible = true;
            },
            detail: function (row) {
                this.isDetailShow = true;
                this.dialogTitle = "员工详情";
                this.setRuleFormData(row);
                this.formVisible = true;
            },
            setRuleFormData: function (row) {
                this.ruleForm.resourceId = row.resourceId;
                this.ruleForm.resourceName = row.resourceName;
                this.ruleForm.icon = row.icon;
                this.ruleForm.resourceUrl = row.resourceUrl;
                this.ruleForm.router = row.router;
                this.ruleForm.type = row.type;
            },
            deleted: function (row) {
                this.deleteOneRow(this,'');
            },
            cancel: function () {
                this.formVisible = false;
            },
            save: function (formName) {
                this.commitRuleForm(this, formName, 'resource/save')
            },
            dialogClosed: function () {
                this.closeDialogRuleForm(this);
            }
        }
    }
</script>

<style scoped>
</style>
