/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="30%">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="90px">
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
                    <el-col :span="20">
                        <el-form-item label="人员"
                                      prop="personnelId">
                            <el-select v-model="formItem.personnelId"
                                       style="width: 400px"
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
                    <el-col :span="20">
                        <el-form-item label="账号类型"
                                      prop="personnelId">
                            <el-select v-model="formItem.accountType"
                                       style="width: 400px"
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
                    <el-col :span="20">
                        <el-form-item label="昵称"
                                      prop="nickName">
                            <el-input v-model="formItem.nickName"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="登录名"
                                      prop="userName">
                            <el-input :disabled="formItem.userId?true:false"
                                      v-model="formItem.userName"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item v-if="formItem.userId?false:true"
                                      label="登录密码"
                                      prop="password">
                            <el-input v-model="formItem.password"
                                      type="password"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item v-if="formItem.userId?false:true"
                                      label="再次确认密码"
                                      prop="passwordConfirm">
                            <el-input v-model="formItem.passwordConfirm"
                                      type="password"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="邮箱"
                                      prop="email">
                            <el-input v-model="formItem.email"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="手机"
                                      prop="mobile">
                            <el-input v-model="formItem.mobile"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="状态">
                            <el-radio-group v-model="formItem.status"
                                            style="width: 400px">
                                <el-radio label="0">禁用</el-radio>
                                <el-radio label="1">正常</el-radio>
                                <el-radio label="2">锁定</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="描述">
                            <el-input type="textarea"
                                      v-model="formItem.userDesc"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提交</el-button>
            </span>
        </el-dialog>

        <!-- 分配角色 -->
        <el-dialog :visible.sync="dialogLoadRoles"
                   title="分配角色"
                   width="30%">
            <el-form ref="formRoles"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="90px">
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-form-item label="分配角色："
                                      prop="grantRoles">
                            <el-checkbox-group v-model="formItem.grantRoles"
                                               style="width: 400px;">
                                <el-checkbox v-for="item in selectRoles"
                                             :key="item.roleId"
                                             :label="item.roleId"><span>{{ item.roleName }}</span></el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogLoadRoles = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('formRoles')">提交</el-button>
            </span>
        </el-dialog>

        <!-- 修改密码 -->
        <el-dialog :visible.sync="dialogPassword"
                   title="修改密码"
                   width="30%">
            <el-form ref="formPassword"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="登录名"
                                      prop="userName">
                            <el-input :disabled="formItem.userId?true:false"
                                      v-model="formItem.userName"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="登录密码"
                                      prop="password">
                            <el-input v-model="formItem.password"
                                      type="password"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="20">
                        <el-form-item label="再次确认密码"
                                      prop="passwordConfirm">
                            <el-input v-model="formItem.passwordConfirm"
                                      type="password"
                                      style="width: 400px"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogPassword = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('formPassword')">提交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateAccountManage, addUserRoles, updatePassword } from '@/api/basic-info';

export default {
    name: "ModelView",
    directives: {
        waves
    },
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
            selectRoles: [],  // 分配角色
            formItem: {
                userId: '',
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
            dialogEditInfo: false,
            dialogLoadRoles: false,
            dialogPassword: false
        };
    },
    methods: {
        /**
         * 控制编辑弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.userId = data.userId;
            this.formItem.personnelId = data.personnelId;
            this.formItem.accountType = data.accountType;
            this.formItem.nickName = data.nickName;
            this.formItem.userName = data.userName;
            this.formItem.password = data.password;
            this.formItem.passwordConfirm = data.password;
            this.formItem.email = data.email;
            this.formItem.mobile = data.mobile;
            this.formItem.status = String(data.status);
            this.formItem.userDesc = data.userDesc;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        /**
         * 控制分配角色弹窗
         * @return {type} {description}
         */
        handleLoadRolesModel (data, userId, value) {
            this.formItem.userId = userId;
            this.selectRoles = data
            this.formItem.grantRoles = value
            this.$nextTick(() => {
                this.dialogLoadRoles = true;
            })
        },

        /**
         * 控制密码弹窗
         * @return {type} {description}
         */
        handlePasswordModel (data) {
            this.formItem.userId = data.userId;
            this.formItem.userName = data.userName;
            this.formItem.password = ''
            this.$nextTick(() => {
                this.dialogPassword = true;
            })
        },

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (from) {
            if (from == 'from') {
                this.$refs[from].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        updateAccountManage(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.$message({
                                    message: '更新成功',
                                    type: 'success',
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                                this.$emit('updateList')
                                // this.handleReset('form')
                            }
                        }).finally(() => {
                            this.dialogEditInfo = false
                            this.saving = false
                        })
                    }
                })
            } else if (from == 'formRoles') {
                this.$refs[from].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        addUserRoles(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.$message({
                                    message: '分配角色成功',
                                    type: 'success',
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                                this.$emit('updateList')
                            }
                        }).finally(() => {
                            this.dialogLoadRoles = false
                            this.saving = false
                        })
                    }
                })
            } else if (from == 'formPassword') {
                this.$refs[from].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        updatePassword(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.$message({
                                    message: '修改密码成功',
                                    type: 'success',
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                                this.$emit('updateList')
                            }
                        }).finally(() => {
                            this.dialogPassword = false
                            this.saving = false
                        })
                    }
                })
            }
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.userDesc = ''
            this.formItem.passwordConfirm = ''
            this.formItem.status = '1'
            this.$refs[from].resetFields()
        }
    }
};
</script>

<style lang="scss">
.model-view {
    .filter-box {
        margin-bottom: 20px;
    }
    .table-number {
        float: left;
        font-size: 16px;
        font-weight: 700;
    }
    .el-textarea.is-disabled .el-textarea__inner,
    .el-input.is-disabled .el-input__inner {
        color: #666;
    }
}
</style>