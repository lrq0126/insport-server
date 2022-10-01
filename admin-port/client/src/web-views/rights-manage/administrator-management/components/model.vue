/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="administrator-management-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="450px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="用户名："
                                      prop="username">
                            <el-input v-model="formItem.username"
                                      placeholder="请输入您的用户名"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="账号："
                                      prop="loginName">
                            <el-input v-model="formItem.loginName"
                                      placeholder="请输入您的账号"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <!-- <el-col :span="20">
                        <el-form-item label="管理权限："
                                      prop="level">
                            <el-radio-group v-model="formItem.level">
                                <el-radio :label="1">操作员</el-radio>
                                <el-radio :label="2">管理员</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col> -->
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button>
            </span>
        </el-dialog>

        <!--分配角色信息 -->
        <el-dialog :visible.sync="dialogRoleInfo"
                   title="分配角色信息"
                   width="350px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="选择角色："
                                      prop="roleId">
                            <el-radio-group v-model="formItem.roleId">
                                <el-radio v-for="(item, index) in roleArray"
                                          :key="index"
                                          :label="item.id">{{item.roleName}}</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogRoleInfo = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmitRole('form')">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateOperator, assignRolesOperator } from '@/api/administrator-management/operator'
import Cookies from 'js-cookie'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            formItem: {
                usercode: '',
                username: '',
                loginName: '',
                level: 1,
                userId: '',
                roleId: ''
            },
            formItemRules: {
                username: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' },
                ],
                loginName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' },
                ],
                level: [
                    { required: true, message: '请选择管理权限', trigger: 'change' },
                ],
                roleId: [
                    { required: true, message: '请选择角色', trigger: 'change' },
                ]
            },
            dialogEditInfo: false,
            roleArray: [],
            dialogRoleInfo: false
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.usercode = data.usercode;
            this.formItem.username = data.username;
            this.formItem.loginName = data.loginName;
            this.formItem.level = data.level;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
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
                    updateOperator(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                        }
                    }).finally(() => {
                        this.dialogEditInfo = false
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
            this.$refs[from].resetFields()
        },

        /**
         * 分配角色控制弹窗
         * @return {type} {description}
         */
        handRoleInfoModel (data, res) {
            this.roleArray = data;
            this.formItem.roleId = res.roleId;
            this.formItem.userId = res.id;
            this.$nextTick(() => {
                this.dialogRoleInfo = true;
            })
        },

        /**
         * 分配角色
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmitRole (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    assignRolesOperator(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '分配成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                        }
                    }).finally(() => {
                        this.dialogRoleInfo = false
                        this.saving = false
                    })
                }
            })
        }

    }
};
</script>

<style lang="scss">
.administrator-management-model-view {
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
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .el-radio {
        width: 180px !important;
        margin-top: 7px !important;
    }
}
</style>