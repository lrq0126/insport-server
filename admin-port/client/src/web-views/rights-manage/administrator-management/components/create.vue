/**
 * 添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="student-management-create">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 style="margin-top: 70px"
                 label-width="200px">

            <el-form-item label="所属仓库区域："
                            prop="commercialAreaName">
                <el-input v-model="formItem.commercialAreaName"
                            placeholder="请选择所属仓库区域"
                            style="width: 300px;" :readonly="true"/>
                <el-button type="primary" @click="openDialog()">选择仓库地区</el-button>
            </el-form-item>

            <el-form-item label="用户名："
                            prop="username">
                <el-input v-model="formItem.username"
                            placeholder="请输入您的用户名"
                            style="width: 300px;"></el-input>
            </el-form-item>

            <el-form-item label="账号："
                            prop="loginName">
                <el-input v-model="formItem.loginName"
                            placeholder="请输入您的账号"
                            style="width: 300px;"></el-input>
            </el-form-item>

            <el-form-item label="密码："
                            prop="password">
                <el-input v-model="formItem.password"
                            placeholder="请输入您的密码"
                            type="password"
                            style="width: 300px;"></el-input>
            </el-form-item>

            <el-form-item label="确认密码："
                            prop="secondPassword">
                <el-input v-model="formItem.secondPassword"
                            placeholder="请输入您的确认密码"
                            type="password"
                            style="width: 300px;"></el-input>
            </el-form-item>

            <el-form-item label-width="250px" style="margin-top: 30px">
                <el-button :loading="saving"
                            type="primary"
                            @click="handleSubmit('form')">保 存</el-button>
                <el-button @click="handleReset('form')">重 置</el-button>
            </el-form-item>
        </el-form>
        <commercial-area-list-view ref="commercialAreaListView" @updateCommercialArea="updateCommercialArea"/>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import { addOperator } from '@/api/administrator-management/operator'
import CommercialAreaListView from './commercial-area-list'
import quillConfig from '@/utils/quill-config.js'

export default {
    name: 'Create',
    components:{
        waves,
        CommercialAreaListView,
        quillConfig
    },
    data () {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.formItem.secondPassword !== '') {
                    this.$refs.form.validateField('secondPassword');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.formItem.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            saving: false,
            formItem: {
                username: '',
                loginName: '',
                password: '',
                secondPassword: '',
                level: 1,
                commercialAreaId: "",
                commercialAreaName: "",
            },
            formItemRules: {
                username: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' },
                ],
                loginName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' },
                ],
                password: [
                    { validator: validatePass, trigger: 'blur' },
                ],
                secondPassword: [
                    { validator: validatePass2, trigger: 'blur' },
                ],
                level: [
                    { required: true, message: '请选择管理权限', trigger: 'change' },
                ],
                commercialAreaName: [
                    { required: true, message: '请选择仓库区域', trigger: 'change' },
                ],
            },
        };
    },
    methods: {
        openDialog(){
            this.$refs['commercialAreaListView'].openDialog();
        },
        updateCommercialArea(data){
            this.formItem.commercialAreaId = data.id;
            this.formItem.commercialAreaName = data.commercialAreaName;
        },
        /**
        * 保存
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    addOperator(this.formItem).then(res => {
                        if (res.code == 100) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.handleReset('form')
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset () {
            this.formItem = {
                username: '',
                loginName: '',
                password: '',
                secondPassword: '',
                level: 1,
            }
        },
    },

    mounted:{

    }
};
</script>

<style lang="scss" scope>
.student-management-create {
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
}
</style>
