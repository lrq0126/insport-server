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
                 label-width="200px">
            <el-row style="margin-top: 50px;">
                <el-col :span="20">
                    <el-form-item label="用户名："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入用户名"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="20">
                    <el-form-item label="账号："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入账号"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="20">
                    <el-form-item label="密码："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入密码"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="20">
                    <el-form-item label="确认密码："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入确认密码"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="20">
                    <el-form-item label="管理权限：">
                        <el-radio-group v-model="formItem.gender">
                            <el-radio :label="1">操作员</el-radio>
                            <el-radio :label="2">管理员</el-radio>
                        </el-radio-group>
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
// import { addFacilityEquipment } from '@/api/safety-resources/facility-equipment'
import quillConfig from '@/utils/quill-config.js'


export default {
    name: 'Create',
    data () {
        return {
            saving: false,
            formItem: {
                name: '',
                typeId: '',
                fileUrl: [],
                file: '',
                schoolId: '',
                remark: '',
                gender: '',
                time: '',
            },
            formItemRules: {
                name: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' },
                ],
                typeId: [
                    { required: true, message: '账号不能为空', trigger: 'blur' },
                ],
                typeId: [
                    { required: true, message: '密码不能为空', trigger: 'blur' },
                ],
                typeId: [
                    { required: true, message: '确认密码不能为空', trigger: 'blur' },
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
                    this.formItem.fileUrl = this.fileList
                    this.saving = true
                    addFacilityEquipment(this.formItem).then(res => {
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
            this.fileList = []
            this.formItem.remark = ''
            this.formItem.fileUrl = []
            this.$refs[from].resetFields()
        },

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
