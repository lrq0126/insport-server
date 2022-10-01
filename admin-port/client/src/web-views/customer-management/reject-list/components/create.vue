/**
 * 添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="warehouse-list-create">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="200px">
            <el-row style="margin-top: 50px;">
                <el-col :span="24">
                    <el-form-item label="快递单号"
                                  prop="deliveryOrderNo">
                        <el-input v-model="formItem.deliveryOrderNo"
                                  placeholder="请输入快递单号"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="24">
                    <el-form-item label="备注信息："
                                  prop="message">
                        <el-input v-model="formItem.message"
                                  type="textarea"
                                  placeholder="请输入备注信息，例如是哪个系统哪个客户的单号"
                                  style="width: 300px;height:100px;"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="20">
                    <el-row>
                        <el-col :span="20">
                            <el-form-item label-width="250px">
                                <el-button :loading="saving"
                                           type="primary"
                                           @click="handleSubmit('form')">保 存</el-button>
                                <el-button @click="handleReset('form')">重 置</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import { insertReject } from '@/api/warehouse-management/warehouse-list'


export default {
    name: 'Create',
    data () {
        return {
            saving: false,
            formItem: {
                deliveryOrderNo: '',
                message: '',
            },
            formItemRules: {
                deliveryOrderNo: [
                    { required: true, message: '快递单号不能为空', trigger: 'blur' },
                ]
            },
        };
    },
    methods: {
        //限制
        check_num(){
            this.formItem.storageArea = this.formItem.storageArea.replace(/[^\a-\z\A-\Z0-9\u9fe5_]/g, '');
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
                    insertReject(this.formItem).then(res => {
                        if (res.code === 100) {
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
        handleReset (from) {
            this.$refs[from].resetFields()
        },

    }
};
</script>

<style lang="scss" scope>
.warehouse-list-create {
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
}
</style>
