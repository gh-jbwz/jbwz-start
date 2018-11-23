<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChange">
            <i class="el-icon-menu"></i>
        </div>
        <div class="logo">jbwz们都不好好干</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>
                <!-- 消息中心 -->
                <div class="btn-bell">
                    <el-tooltip effect="dark" :content="messageCount>0?`有${messageCount}条未读消息`:`消息中心`"
                                placement="bottom">
                        <router-link to="/tabs">
                            <i class="el-icon-bell"></i>
                        </router-link>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="messageCount>0"></span>
                </div>
                <!-- 用户头像 -->
                <div class="user-avator">
                    <img v-bind:src="getImageUrl">
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{getUsername}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item divided command="personal">个人信息</el-dropdown-item>
                        <el-dropdown-item divided command="uppwd">修改密码</el-dropdown-item>
                        <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

        <el-dialog title="修改密码" :visible.sync="upPwdFormVisible" width="45%">
            <el-form :rules="pwdRules" ref="pwdRuleForm" :model="pwdRuleFormData" size="small"
                     label-width="100px">
                <el-form-item label="旧密码" prop="oldPwd">
                    <el-input type="password" autocomplete="off" v-model="pwdRuleFormData.oldPwd"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPwd">
                    <el-input type="password" autocomplete="off" v-model="pwdRuleFormData.newPwd"></el-input>
                </el-form-item>
                <el-form-item label="重复密码" prop="newPwd2">
                    <el-input type="password" autocomplete="off" v-model="pwdRuleFormData.newPwd2"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="upPwdFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="savePwd">提 交</el-button>
            </div>
        </el-dialog>
        <!--个人信息弹框 -->
        <el-dialog title="个人信息" :visible.sync="dialogFormVisible" width="45%">
            <el-form :rules="rules" ref="ruleForm" :model="ruleFormData" size="small"
                     label-width="100px">
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
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="savePersonal">保 存</el-button>
            </div>
        </el-dialog>

    </div>
</template>
<script>

    export default {
        data() {
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    callback();
                }
            };
            let validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.pwdRuleFormData.newPwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                dialogFormVisible: false,
                upPwdFormVisible: false,
                collapse: false,
                fullscreen: false,
                userName: '',
                imageUrl: '',
                messageCount: 0,
                pwdRuleFormData: {
                    oldPwd: '',
                    newPwd: '',
                    newPwd2: ''
                },
                pwdRules: {
                    oldPwd: [
                        {required: true, message: '请输入旧密码', trigger: 'blur'}
                    ],
                    newPwd: [
                        {validator: validatePass, trigger: 'blur'},
                        {min: 6, max: 16, message: '密码至少6到16位'},
                        {pattern: /^[A-Za-z0-9_@#~.\[\]\{\},<>?$%^!&*()]{6,16}$/, message: '只有1至9上面的特殊字符可用'}
                    ],
                    newPwd2: [
                        {validator: validatePass2, trigger: 'blur'},
                    ]
                },
                ruleFormData: {
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
        computed: {
            getImageUrl: function () {
                this.imageUrl = this.$cookies.getUserImg();
                return this.imageUrl;
            },
            getUsername: function () {
                this.userName = this.$cookies.getUserName();
                return this.userName;
            }
        },
        created() {
        },
        methods: {
            //更新用户信息
            getPersonal: function () {
                let vm = this;
                this.$axios.get('personal/get').then(function (res) {
                    vm.ruleFormData = res.data.data;
                    vm.dialogFormVisible = true;
                })
            },
            //图像更新
            savePwd: function () {
                let vm = this;
                this.$refs.pwdRuleForm.validate((valid) => {
                    if (valid) {
                        this.$axios.get('personal/upPwd').then(function (res) {
                            vm.handleCommand('logout');
                        })
                    } else {
                        return false;
                    }
                });
            },
            //更新个人信息
            savePersonal: function () {
                this.$cookies.setUserName(this.ruleFormData.userName);
                this.userName = this.ruleFormData.userName;
                this.commitRuleForm(this, 'personal/save', this.getPersonal)
            },
            // 用户名下拉菜单选择事件
            handleCommand(command) {
                if (command == 'logout') {
                    localStorage.clear();
                    this.$axios.post('logout').then(function (res) {
                        }
                    )
                    this.$router.push('/login');
                } else if (command == 'personal') {
                    this.getPersonal();
                } else if (command == 'uppwd') {
                    this.upPwdFormVisible = true;
                }
            },
            // 侧边栏折叠
            collapseChange() {
                this.collapse = !this.collapse;
                bus.$emit(this.$configData.busCollapseName, this.collapse);
            },
            // 全屏事件
            handleFullScreen() {
                let element = document.documentElement;
                if (this.fullscreen) {
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen();
                    }
                } else {
                    if (element.requestFullscreen) {
                        element.requestFullscreen();
                    } else if (element.webkitRequestFullScreen) {
                        element.webkitRequestFullScreen();
                    } else if (element.mozRequestFullScreen) {
                        element.mozRequestFullScreen();
                    } else if (element.msRequestFullscreen) {
                        // IE11
                        element.msRequestFullscreen();
                    }
                }
                this.fullscreen = !this.fullscreen;
            }
        },
        mounted() {
            if (document.body.clientWidth < 1500) {
                this.collapseChange();
            }
        }
    }
</script>
<style scoped>
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #fff;
    }

    .collapse-btn {
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }

    .header .logo {
        float: left;
        width: 250px;
        line-height: 70px;
    }

    .header-right {
        float: right;
        padding-right: 50px;
    }

    .header-user-con {
        display: flex;
        height: 70px;
        align-items: center;
    }

    .btn-fullscreen {
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }

    .btn-bell, .btn-fullscreen {
        position: relative;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 15px;
        cursor: pointer;
    }

    .btn-bell-badge {
        position: absolute;
        right: 0;
        top: -2px;
        width: 8px;
        height: 8px;
        border-radius: 4px;
        background: #f56c6c;
        color: #fff;
    }

    .btn-bell .el-icon-bell {
        color: #fff;
    }

    .user-name {
        margin-left: 10px;
    }

    .user-avator {
        margin-left: 20px;
    }

    .user-avator img {
        display: block;
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .el-dropdown-link {
        color: #fff;
        cursor: pointer;
    }

    .el-dropdown-menu__item {
        text-align: center;
    }
</style>
