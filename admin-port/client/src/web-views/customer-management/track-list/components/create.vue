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
                    <el-form-item label="转单号"
                                  prop="orderNumber">
                        <el-input v-model="formItem.orderNumber"
                                  placeholder="请输入转单号"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="24">
                    <el-form-item label="轨迹内容"
                                  prop="message">
                        <el-input v-model="formItem.message"
                                  placeholder="请输入轨迹内容"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="24">
                    <el-form-item label="轨迹站点"
                                  prop="location">
                        <el-input v-model="formItem.location"
                                  placeholder="请输入轨迹站点"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="24">
                    <el-form-item label="轨迹时间"
                                  prop="trackTime">
                    <el-date-picker v-model="formItem.trackTime"
                                    align="right"
                                    type="datetime"
                                    placeholder="选择日期"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    style="width: 300px;"></el-date-picker>
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
import { saveTrajectory } from '@/api/warehouse-management/warehouse-list'


export default {
    name: 'Create',
    data () {
        return {
            saving: false,
            formItem: {
                orderNumber: '',
                message: '',
                location: '',
                trackTime: '',
            },
            formItemRules: {
                orderNumber: [
                    { required: true, message: '转单号不能为空', trigger: 'blur' },
                ],
                message: [
                    { required: true, message: '轨迹内容不能为空', trigger: 'blur' },
                ],
                trackTime: [
                    { required: true, message: '轨迹时间不能为空', trigger: 'blur' },
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
                    saveTrajectory(this.formItem).then(res => {
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
