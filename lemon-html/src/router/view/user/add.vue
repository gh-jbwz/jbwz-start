<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-favor"></i>添加员工</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
        </div>
    </div>
</template>

<script>
    export default {
        name: "userAdd",
        data() {
            return {
                formData: {}
            }
        },
        created() {
            this.list()
        },
        methods: {
            save: function () {
                this.$axios.post('user/save', this.formData)
                    .then(function (res) {
                        this.$router.replace('user/list');
                    })
            },
        }
    }
</script>

<style scoped>

</style>
