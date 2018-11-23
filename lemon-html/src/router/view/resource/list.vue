<template>
    <div id="content">
        <div class="table">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item><i class="el-icon-lx-favor"></i>{{businessName}}管理
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="container" style="border:0px">
                <div class="content-head-box">
                    <el-button v-if="hasPermission('resource/save')" type="primary" icon="add" class="head-left"
                               @click="handleButtonAction('add')">添加
                    </el-button>
                    <el-input v-model="search_word" placeholder="名称" class="search-input">
                    </el-input>
                    <el-button type="primary" icon="el-icon-search" @click="list">搜索</el-button>
                </div>
                <el-table v-loading="loading" element-loading-text="拼命加载中..."
                          element-loading-background="rgba(0, 0, 0, 0.8)" :data="tableData" highlight-current-row border
                          style="width: 100%">
                    <el-table-column
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
                            <el-button @click="handleButtonAction('detail',scope.row)" type="text"
                                       icon="el-icon-tickets" size="small">查看
                            </el-button>
                            <el-button v-if="hasPermission('resource/update')"
                                       @click="handleButtonAction('edit',scope.row)" type="text"
                                       icon="el-icon-edit" size="small">编辑
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
        <el-dialog :title="dialogFormTitle" @closed="dialogClosed" :visible.sync="dialogFormVisible" width="45%">
            <el-form :rules="rules" ref="ruleForm" :model="ruleFormData" size="medium"
                     label-width="100px">
                <el-form-item label="id" hidden="hidden" prop="resourceId">
                    <el-input type="hidden" v-model="ruleFormData.resourceId"></el-input>
                </el-form-item>
                <el-form-item label="资源名称" prop="resourceName">
                    <el-input v-model="ruleFormData.resourceName"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="type">
                    <el-radio-group v-model="ruleFormData.type">
                        <el-radio label="0">菜单</el-radio>
                        <el-radio label="1">资源</el-radio>
                    </el-radio-group>
                </el-form-item>
                <!--如果是菜单再选择父菜单-->
                <el-form-item v-if="ruleFormData.type=='0'" label="父菜单" prop="pid">
                    <el-select v-model="ruleFormData.pid" filterable placeholder="请选择">
                        <el-option
                            v-for="item in menuOptions"
                            :key="item.resourceId"
                            :label="item.resourceName"
                            :value="item.resourceId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="资源URL" prop="resourceUrl">
                    <el-input v-model="ruleFormData.resourceUrl"></el-input>
                </el-form-item>
                <el-form-item label="界面路由" prop="router">
                    <el-select v-model="ruleFormData.router" filterable placeholder="请选择">
                        <el-option
                            v-for="item in allRouters"
                            :key="item.path"
                            :label="item.meta.title"
                            :value="item.path">
                        </el-option>
                    </el-select>

                </el-form-item>
                <!--如果是菜单再选择图标-->
                <el-form-item v-if="ruleFormData.type=='0'" label="图标" prop="icon">
                    <span> <i :class="ruleFormData.icon"></i></span>
                    <el-button style="margin-left:15px" size="small" v-if="!isDetailButton"
                               @click="iconDialogVisible=true">选择图标
                    </el-button>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button v-if="!isDetailButton" @click="dialogFormVisible=false">取 消</el-button>
                <el-button v-if="!isDetailButton" type="primary" @click="save">保 存</el-button>
            </span>
        </el-dialog>
        <!--Icon弹框 -->
        <el-dialog title="选择图标" :visible.sync="iconDialogVisible" width="65%">
            <icon @selected="selectIcon"></icon>
            <!--<span slot="footer" class="dialog-footer">-->
            <!--<el-button type="primary" v-if="!isDetailButton" @click="iconDialogVisible=false">确定</el-button>-->
            <!--</span>-->
        </el-dialog>
    </div>
</template>

<script>
    import Icon from '@/components/Icon';
    import routers from '@/router/routers.js';

    export default {
        components: {
            Icon
        },
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
                //另外ruleFormData,rules两个变量的名字也必须保持不变,内容需要跟据业务字段改变
                /*======  end  ========*/
                businessName: '资源',//功能名称
                ruleFormData: {//表单字段
                    pid: '',
                    resourceId: '',
                    resourceName: '',
                    resourceUrl: '',
                    router: '',
                    icon: '',
                    type: ''
                },
                rules: {//表单校验
                    resourceName: [
                        {required: true, message: '请输入资源名称', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择类型', trigger: 'blur'},
                    ],
                    pid: [
                        {required: true, message: '请选择父菜单', trigger: 'blur'},
                    ]
                },
                /*=========== 以下为本vue自定义字段 ===========*/

                menuOptions: [],
                allRouters: [],
                iconDialogVisible: false,
            }
        },
        created() {
            this.getMenuList();
            this.list()
            this.getAllRouters();
        }
        ,
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
                    url = 'resource/save';
                } else if (this.isEditButton) {
                    url = 'resource/update';
                }
                if (this.ruleFormData.type === '1') {
                    this.ruleFormData.pid = 0;
                }
                this.commitRuleForm(this, url, this.afterSave)
            },
            list: function (pageNumber) {
                this.getPageTable(this, pageNumber, 'resource/page', {"resourceName": this.search_word})
            },
            getMenuList: function () {
                let vm = this;
                this.menuOptions = [{'resourceId': 0, 'resourceName': '根菜单'}];
                this.$axios.get('resource/menu-list').then(function (res) {
                    vm.menuOptions = vm.menuOptions.concat(res.data.data);
                })
            },
            setRuleFormData: function (row) {
                this.ruleFormData.pid = row.pid;
                this.ruleFormData.resourceId = row.resourceId;
                this.ruleFormData.resourceName = row.resourceName;
                this.ruleFormData.icon = row.icon;
                this.ruleFormData.resourceUrl = row.resourceUrl;
                this.ruleFormData.router = row.router;
                this.ruleFormData.type = row.type;
            },
            afterSave: function (vm) {
                //新增菜单后,通知左侧菜单栏跟新菜单
                bus.$emit(this.$configData.busMenuName, "true");
                // 获取最新的菜单下拉框
                this.getMenuList()
            },
            getAllRouters: function () {
                let tmp = [{path: "", meta: {title: "请选择"}}];
                for (let i = 0; i < routers.length; i++) {
                    if (routers[i].children) {
                        // let routerTemps = this.allRouters.concat(tmp).concat(routers[i].children);
                        // this.allRouters = routers[i].children;
                        let all = tmp.concat(routers[i].children);
                        // 一定要先把上面的拼接完再赋值给主变量(allRouters),不然得到的不是数组对象而是字符串
                        this.allRouters = all;
                        // console.log("所有routers", all);
                        return this.allRouters;
                    }
                }
            },
            selectIcon: function (iconName) {
                this.ruleFormData.icon = iconName;
                this.iconDialogVisible = false;
            }
        }
    }
</script>

<style scoped>
    .container i, form i {
        font-size: 20px;
    }
</style>
