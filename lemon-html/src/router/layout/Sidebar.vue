<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
                 text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in menuList">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <!--<el-submenu v-if="subItem.subs!= undefined && subItem.subs.length>0" :index="subItem.index"-->
                            <el-submenu v-if="subItem.subs" :index="subItem.index"
                                        :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem,i) in subItem.subs" :key="i" :route="threeItem.router"
                                              :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :route="subItem.router" :key="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :route="item.router" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                collapse: false,
                menuList: []
            }
        },
        computed: {
            onRoutes() {
                return this.$route.path.replace('/', '');
            }
        },
        created() {
            this.refreshMenuList();
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on(this.$configData.busCollapseName, msg => {
                this.collapse = msg;
            });
            bus.$on(this.$configData.busMenuName, msg => {
                if (msg === "true") {
                    this.refreshMenuList();
                }
            });
        },
        methods: {
            convertResourceToMenu: function (resource, i) {
                let menuObject = {};
                menuObject.id = resource.resourceId;
                menuObject.title = resource.resourceName;
                menuObject.index = "index" + resource.resourceId + i;
                menuObject.router = resource.router.replace("/", "");
                menuObject.icon = resource.icon;
                menuObject.subs = [];
                return menuObject;
            },
            removeZeroSubs: function (arra) {
                let finalMenus = arra;
                for (let key in finalMenus) {
                    if (finalMenus[key].subs.length == 0) {
                        delete finalMenus[key].subs;
                    } else {
                        this.removeZeroSubs(finalMenus[key].subs)
                    }
                }
            },
            refreshMenuList: function () {
                let vm = this;
                this.$axios.get('resource/menu-list').then(function (res) {
                    let arrayData = res.data.data;
                    let finalMenus = [];
                    //设置一级菜单
                    for (let i = 0; i < arrayData.length; i++) {
                        let resource = arrayData[i];
                        if (resource.pid == 0) {
                            finalMenus.push(vm.convertResourceToMenu(resource, i));
                            // console.log("菜单>>>>>", finalMenus); console.log("转换菜单>>>>>", vm.convertResourceToMenu(resource));
                            arrayData.splice(i, 1); //删不删好像性能没啥影响,删的时候也得占cpu吧???
                        }
                    }
                    for (let key in finalMenus) {
                        let menu = finalMenus[key];
                        //设置二级菜单
                        for (let i = 0; i < arrayData.length; i++) {
                            let resource = arrayData[i];
                            if (resource.pid == menu.id) {
                                let menuObj = vm.convertResourceToMenu(resource, i);
                                menu.subs.push(menuObj)
                                // arrayData.splice(i, 1); //删不删好像性能没啥影响,删的时候也得占cpu吧???
                            }
                        }
                    }
                    vm.removeZeroSubs(finalMenus);
                    let deft = [{icon: 'el-icon-lx-home', index: 'dashboard', title: '系统首页'}];
                    let all = deft.concat(finalMenus);
                    console.log("最终菜单列表:", all);
                    vm.menuList = all;
                })
            }
        }
    }
</script>
<!--
                    {
                        icon: 'el-icon-lx-home',
                        index: 'menu',
                        title: '系统设置',
                        subs: [
                            {
                                icon: '',
                                index: 'resource',
                                title: '资源管理'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-lx-home',
                        index: 'user',
                        title: '员工管理',
                        subs: [
                            {
                                icon: '',
                                index: 'user',
                                title: '员工列表'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-lx-home',
                        index: 'dashboard',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'table',
                        title: '基础表格'
                    },
                    {
                        icon: 'el-icon-lx-copy',
                        index: 'tabs',
                        title: 'tab选项卡'
                    },
                    {
                        icon: 'el-icon-lx-calendar',
                        index: '3',
                        title: '表单相关',
                        subs: [
                            {
                                index: 'form',
                                title: '基本表单'
                            },
                            {
                                index: '3-2',
                                title: '三级菜单',
                                subs: [
                                    {
                                        index: 'editor',
                                        title: '富文本编辑器'
                                    },
                                    {
                                        index: 'markdown',
                                        title: 'markdown编辑器'
                                    },
                                ]
                            },
                            {
                                index: 'upload',
                                title: '文件上传'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-lx-emoji',
                        index: 'icon',
                        title: '自定义图标'
                    },
                    {
                        icon: 'el-icon-lx-favor',
                        index: 'charts',
                        title: 'schart图表'
                    },
                    {
                        icon: 'el-icon-rank',
                        index: 'drag',
                        title: '拖拽列表'
                    },
                    {
                        icon: 'el-icon-lx-warn',
                        index: '6',
                        title: '错误处理',
                        subs: [
                            {
                                index: 'permission',
                                title: '权限测试'
                            },
                            {
                                index: '404',
                                title: '404页面'
                            }
                        ]
                    }
-->
<style scoped>
    .sidebar {
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom: 0;
        overflow-y: scroll;
    }

    .sidebar::-webkit-scrollbar {
        width: 0;
    }

    .sidebar-el-menu:not(.el-menu--collapse) {
        width: 250px;
    }

    .sidebar > ul {
        height: 100%;
    }
</style>
