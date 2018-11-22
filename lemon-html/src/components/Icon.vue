<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-emoji"></i> 自定义图标</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <h3 align="center"> 选择图标</h3>
            <div class="search-box">
                <el-input class="search" size="large" v-model="keyword" clearable placeholder="请输入图标名称"></el-input>
            </div>
            <ul class="icon-list">
                <li class="icon-li" v-for="(item,index) in list" :key="index">
                    <div class="icon-li-content" @dblclick="selectIcon(`${item}`)">
                        <i :class="`el-icon-lx-${item}`"></i>
                        <span class="icon-name">{{item}}</span>
                    </div>
                </li>
            </ul>
        </div>

    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                keyword: '',
                iconList: [
                    'attentionforbid',
                    'attentionforbidfill',
                    'attention',
                    'attentionfill',
                    'tag',
                    'tagfill',
                    'people',
                    'peoplefill',
                    'notice',
                    'noticefill',
                    'mobile',
                    'mobilefill',
                    'voice',
                    'voicefill',
                    'unlock',
                    'lock',
                    'home',
                    'homefill',
                    'delete',
                    'deletefill',
                    'notification',
                    'notificationfill',
                    'notificationforbidfill',
                    'like',
                    'likefill',
                    'comment',
                    'commentfill',
                    'camera',
                    'camerafill',
                    'warn',
                    'warnfill',
                    'time',
                    'timefill',
                    'location',
                    'locationfill',
                    'favor',
                    'favorfill',
                    'skin',
                    'skinfill',
                    'news',
                    'newsfill',
                    'record',
                    'recordfill',
                    'emoji',
                    'emojifill',
                    'message',
                    'messagefill',
                    'goods',
                    'goodsfill',
                    'crown',
                    'crownfill',
                    'move',
                    'add',
                    'hot',
                    'hotfill',
                    'service',
                    'servicefill',
                    'present',
                    'presentfill',
                    'pic',
                    'picfill',
                    'rank',
                    'rankfill',
                    'male',
                    'female',
                    'down',
                    'top',
                    'recharge',
                    'rechargefill',
                    'forward',
                    'forwardfill',
                    'info',
                    'infofill',
                    'redpacket',
                    'redpacket_fill',
                    'roundadd',
                    'roundaddfill',
                    'friendadd',
                    'friendaddfill',
                    'cart',
                    'cartfill',
                    'more',
                    'moreandroid',
                    'back',
                    'right',
                    'shop',
                    'shopfill',
                    'question',
                    'questionfill',
                    'roundclose',
                    'roundclosefill',
                    'roundcheck',
                    'roundcheckfill',
                    'global',
                    'mail',
                    'punch',
                    'exit',
                    'upload',
                    'read',
                    'file',
                    'link',
                    'full',
                    'group',
                    'friend',
                    'profile',
                    'addressbook',
                    'calendar',
                    'text',
                    'copy',
                    'share',
                    'wifi',
                    'vipcard',
                    'weibo',
                    'remind',
                    'refresh',
                    'filter',
                    'settings',
                    'scan',
                    'qrcode',
                    'cascades',
                    'apps',
                    'sort',
                    'searchlist',
                    'search',
                    'edit'
                ]
            }
        },
        methods: {
            selectIcon: function (icon) {
                let iconName = "el-icon-lx-" + icon;
                this.$emit("selected", iconName)
            }
        },
        computed: {
            list() {
                return this.iconList.filter((item) => {
                    return item.indexOf(this.keyword) !== -1;
                })
            }
        }
    }
</script>

<style scoped>
    .search-box {
        text-align: center;
        margin-top: 10px;
    }

    .search {
        width: 300px;
        margin-bottom: 7px;
    }

    .icon-list {
        overflow: hidden;
        list-style: none;
        padding: 0px;
        border: 1px solid #eaeefb;
    }

    .icon-name {
        margin-top: 10px;
        display: inline-block;
        padding: 0 3px;
        height: 1em;
        color: #787878;
        vertical-align: middle;
    }

    ul, li {
        list-style: none;
    }

    .icon-li {
        float: left;
        width: 16.66%;
        text-align: center;
        height: 120px;
        display: list-item;
        border-right: 1px solid #eee;
        border-bottom: 1px solid #eee;
        margin-right: -1px;
        margin-right: -1px;
    }

    .icon-li-content {
        display: flex;
        height: 100%;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        cursor: pointer;
    }

    .icon-li-content i {
        font-size: 26px;
        color: #606266;
    }


</style>
