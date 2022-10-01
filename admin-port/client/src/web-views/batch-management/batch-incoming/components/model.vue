/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="通知公告信息"
                   width="60%">
            <el-form ref="formPassword"
                     :model="formItem"
                     :rules="formItemRules">
                <el-row>
                    <el-col :span="24">
                        <el-form-item>
                            <el-input v-model="formItem.noticeContent"
                                      type="textarea"
                                      :rows="6"
                                      :disabled="true"
                                      placeholder="请输入内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <!-- <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">关 闭</el-button>
            </span> -->
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
    data () {
        return {
            saving: false,
            formItem: {
                noticeContent: ''
            },
            formItemRules: {
                userType: [
                    { required: true, message: '用户类型不能为空', trigger: 'blur' }
                ],
            },
            dialogEditInfo: false,
        };
    },
    methods: {
        /**
         * 控制编辑弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.noticeContent = data;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

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