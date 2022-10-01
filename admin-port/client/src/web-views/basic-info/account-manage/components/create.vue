/**
 * 后台账号添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
            <!-- <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="头像">
                        <el-select v-model="formItem.avatar"
                                   style="width: 300px"
                                   placeholder="请选择">
                            <el-option v-for="item in personData"
                                       :key="item.personnelId"
                                       :label="item.personnelName"
                                       :value="item.personnelId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row> -->

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="人员"
                                  prop="personnelId">
                        <el-select v-model="formItem.personnelId"
                                   style="width: 300px"
                                   placeholder="请选择">
                            <el-option v-for="item in personData"
                                       :key="item.personnelId"
                                       :label="item.personnelName"
                                       :value="item.personnelId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="账号类型"
                                  prop="personnelId">
                        <el-select v-model="formItem.accountType"
                                   style="width: 300px"
                                   placeholder="请选择">
                            <el-option v-for="item in accountTypeData"
                                       :key="item.value"
                                       :label="item.label"
                                       :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="昵称"
                                  prop="nickName">
                        <el-input v-model="formItem.nickName"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="登录名"
                                  prop="userName">
                        <el-input :disabled="formItem.userId?true:false"
                                  v-model="formItem.userName"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item v-if="formItem.userId?false:true"
                                  label="登录密码"
                                  prop="password">
                        <el-input v-model="formItem.password"
                                  type="password"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item v-if="formItem.userId?false:true"
                                  label="再次确认密码"
                                  prop="passwordConfirm">
                        <el-input v-model="formItem.passwordConfirm"
                                  type="password"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="邮箱"
                                  prop="email">
                        <el-input v-model="formItem.email"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="手机"
                                  prop="mobile">
                        <el-input v-model="formItem.mobile"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="状态">
                        <el-radio-group v-model="formItem.status">
                            <el-radio label="0">禁用</el-radio>
                            <el-radio label="1">正常</el-radio>
                            <el-radio label="2">锁定</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="描述">
                        <el-input type="textarea"
                                  v-model="formItem.userDesc"
                                  style="width: 300px"
                                  placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-col :span="20">
                <el-row>
                    <el-col :span="20">
                        <el-form-item>
                            <el-button :loading="saving"
                                       type="primary"
                                       @click="handleSubmit('form')">保存</el-button>
                            <el-button @click="handleReset('form')">重置</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-col>
        </el-form>
    </div>
</template>

<script>
import TreeSelect from "@/components/TreeSelect/tree-select";
import { addAccountManage, getAllUserSelect, getBaseDictByType } from '@/api/basic-info'
import { Promise } from 'q';
export default {
    name: "Create",
    components: { TreeSelect },
    props: {
        personList: {
            type: Array,
            default: []
        },
        accountType: {
            type: Array,
            default: []
        }
    },
    watch: {
        personList () {
            this.personData = this.personList
        },
        accountType () {
            this.accountTypeData = this.accountType
        }
    },
    data () {
        /**
           * 自定义验证规则
           */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            let reg2 = /^.{6,18}$/;
            // 长度为6到18个字符
            if (value === '') {
                callback(new Error('登录名不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else if (value !== '' && !reg2.test(value)) {
                callback(new Error('长度6到18个字符'))
            } else {
                callback()
            }
        }
        const validatePass = (rule, value, callback) => {
            let reg2 = /^.{6,18}$/;
            if (value === '') {
                callback(new Error('请输入密码'))
            } else if (value !== this.formItem.password) {
                callback(new Error('两次输入密码不一致'))
            } else if (value !== '' && !reg2.test(value)) {
                callback(new Error('长度6到18个字符'))
            } else {
                callback()
            }
        }

        const validatePassConfirm = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'))
            } else if (value !== this.formItem.password) {
                callback(new Error('两次输入密码不一致'))
            } else {
                callback()
            }
        }
        const validateMobile = (rule, value, callback) => {
            let reg = /^1\d{10}$/
            if (value !== '' && !reg.test(value)) {
                callback(new Error('手机号码格式不正确'))
            } else {
                callback()
            }
        }
        return {
            saving: false,
            personData: [],  // 人员数据
            accountTypeData: [], // 账号类型
            formItem: {
                userName: '',
                accountType: '',
                nickName: '',
                password: '',
                passwordConfirm: '',
                status: '1',
                companyId: '',
                email: '',
                mobile: '',
                userType: 'platform',
                userDesc: '',
                avatar: '',
                grantRoles: [],
                grantActions: [],
                grantMenus: [],
                expireTime: '',
                isExpired: false
            },
            formItemRules: {
                userType: [
                    { required: true, message: '用户类型不能为空', trigger: 'blur' }
                ],
                userName: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' },
                    { required: true, validator: validateEn, trigger: 'blur' }
                ],
                password: [
                    { required: true, validator: validatePass, trigger: 'blur' }
                ],
                passwordConfirm: [
                    { required: true, validator: validatePassConfirm, trigger: 'blur' }
                ],
                nickName: [
                    { required: true, message: '昵称不能为空', trigger: 'blur' }
                ],
                email: [
                    { required: false, type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
                ]
                ,
                mobile: [
                    { validator: validateMobile, trigger: 'blur' }
                ]
            },
        };
    },
    methods: {

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    addAccountManage(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
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
        handleReset (from) {
            this.formItem.userDesc = ''
            this.$refs[from].resetFields()
        },
    }
};
</script>

<style>
</style>
