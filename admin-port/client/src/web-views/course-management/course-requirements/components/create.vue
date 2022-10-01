/**
 * 添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="teacher-management-create">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
            <el-row style="margin-top: 50px;">
                <el-col :span="20">
                    <el-form-item label="需求名称："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入需求名称"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="20">
                    <el-form-item label="截止时间："
                                  prop="name">
                        <el-date-picker v-model="formItem.time"
                                        type="date"
                                        style="width: 300px;"
                                        placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>

                </el-col>

                <el-col :span="20">
                    <el-form-item label="课程要求："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入课程要求信息"
                                  type="textarea"
                                  :rows="4"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

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
                time: ''
            },
            formItemRules: {
                name: [
                    { required: true, message: '关键字不能为空', trigger: 'blur' },
                ],
                typeId: [
                    { required: true, message: '标题不能为空', trigger: 'blur' },
                ],
                remark: [
                    { required: true, message: "请输入内容!", trigger: "change" }
                ]
            },
            editorOption: quillConfig,
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
.teacher-management-create {
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
}
</style>
