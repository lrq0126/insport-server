/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="student-management-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="1000px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="学生名称："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入学生名称"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="学号："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入学号"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="性别："
                                      prop="name">
                            <el-radio-group v-model="formItem.gender">
                                <el-radio :label="1">男</el-radio>
                                <el-radio :label="2">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="班级："
                                      prop="name">
                            <el-cascader :options="options"
                                         clearable
                                         style="width: 300px;"></el-cascader>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="住址：">
                            <el-input v-model="formItem.name"
                                      type="textarea"
                                      :rows="3"
                                      placeholder="请输入住址信息"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="身份证号："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入身份证号"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="出生日期："
                                      prop="name">
                            <el-date-picker v-model="formItem.time"
                                            type="date"
                                            placeholder="选择日期"
                                            style="width: 300px;">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="学生家长：">
                            <el-button type="info"
                                       plain
                                       style="margin-bottom: 5px;"
                                       @click="addTopic">添加家长</el-button>
                            <el-table :data="formItem.foodSafeContentListJson"
                                      border
                                      fit
                                      highlight-current-row
                                      style="width: 100%;margin-top: 20px;">
                                <el-table-column label="家长名称"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="foodContentName"
                                                      style="margin-bottom: 0">
                                            <el-input v-model="scope.row.name"
                                                      placeholder="请输入家长名称"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="联系电话"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="checkRules"
                                                      style="margin-bottom: 0">
                                            <el-input v-model="scope.row.phone"
                                                      placeholder="请输入联系电话"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="关系"
                                                 width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="checkRules"
                                                      style="margin-bottom: 0">
                                            <el-select v-model="scope.row.type"
                                                       placeholder="请选择">
                                                <el-option value="1"
                                                           label="父子"></el-option>
                                                <el-option value="2"
                                                           label="父女"></el-option>
                                                <el-option value="3"
                                                           label="母子"></el-option>
                                                <el-option value="4"
                                                           label="母女"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="操作"
                                                 width="120"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-button type="danger"
                                                   size="mini"
                                                   plain
                                                   @click="deleteTopic(scope.$index)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
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

        <!--查看内容信息 -->
        <el-dialog :visible.sync="dialogContentInfo"
                   title="学生家长信息"
                   width="1000px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="学生家长：">
                            <el-table :data="formItem.foodSafeContentListJson"
                                      border
                                      fit
                                      highlight-current-row
                                      style="width: 100%;margin-top: 20px;">
                                <el-table-column label="家长名称"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="foodContentName"
                                                      style="margin-bottom: 0">
                                            <el-input v-model="scope.row.name"
                                                      placeholder="请输入家长名称"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="联系电话"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="checkRules"
                                                      style="margin-bottom: 0">
                                            <el-input v-model="scope.row.phone"
                                                      placeholder="请输入联系电话"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="关系"
                                                 width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="checkRules"
                                                      style="margin-bottom: 0">
                                            <el-select v-model="scope.row.type"
                                                       placeholder="请选择">
                                                <el-option value="1"
                                                           label="父子"></el-option>
                                                <el-option value="2"
                                                           label="父女"></el-option>
                                                <el-option value="3"
                                                           label="母子"></el-option>
                                                <el-option value="4"
                                                           label="母女"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                            </el-table>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogContentInfo = false">关闭</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updatePostManage } from '@/api/basic-info'

export default {
    name: "ModelView",
    directives: {
        waves
    },
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
            dialogEditInfo: false,
            dialogContentInfo: false,
            options: [{
                value: '1',
                label: '一年级',
                children: [{
                    value: 'shejiyuanze',
                    label: '1班',
                }, {
                    value: 'daohang',
                    label: '2班',
                }]
            }, {
                value: '2',
                label: '二年级',
                children: [{
                    value: 'shejiyuanze',
                    label: '1班',
                }, {
                    value: 'daohang',
                    label: '2班',
                }]
            }],
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.jobId = data.jobId;
            this.formItem.name = data.name;
            this.formItem.identification = data.identification;
            this.formItem.remarks = data.remarks;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        handleContentModel () {
            this.dialogContentInfo = true
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
                    updatePostManage(this.formItem).then(res => {
                        if (res.code === 0) {
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
            this.formItem.remarks = ''
            this.$refs[from].resetFields()
        },

        /**
        * 新增家长
        * @return {type} {description}
        */
        addTopic () {
            const obj = {
                name: '',
                phone: '',
                type: ''
            }
            this.formItem.foodSafeContentListJson.push(obj);
        },

        /**
         * 删除人员
         * @return {type} {description}
         */
        deleteTopic (index) {
            if (index > -1) {
                this.formItem.foodSafeContentListJson.splice(index, 1);
            }
        },
    }
};
</script>

<style lang="scss">
.student-management-model-view {
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
}
</style>