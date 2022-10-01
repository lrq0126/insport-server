/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="480px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="所属机构："
                                              prop="officeId">
                                    <tree-select :options="treeData"
                                                 ref="parentTreeSelect"
                                                 :value="formItem.officeId"
                                                 :props="propsInstObj"
                                                 @getValue='getParentValue'
                                                 style="width: 300px" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="负责人："
                                              prop="principals">
                                    <el-select v-model="formItem.principals"
                                               filterable
                                               placeholder="请选择负责人"
                                               style="width: 300px;">
                                        <el-option v-for="item in personnelData"
                                                   :key="item.personnelId"
                                                   :value="item.personnelId"
                                                   :label="item.personnelName"></el-option>
                                    </el-select>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="位置名称："
                                              prop="name">
                                    <el-input v-model="formItem.name"
                                              placeholder="请输入位置名称"
                                              style="width: 300px">
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="位置信息："
                                              prop="places">
                                    <el-input v-model="formItem.places"
                                              placeholder="请输入内容"
                                              style="width: 300px"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="描述："
                                              prop="remark">
                                    <el-input v-model="formItem.remark"
                                              type="textarea"
                                              placeholder="请输入描述"
                                              style="width: 300px"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="类型："
                                              prop="type">
                                    <el-select v-model="formItem.type"
                                               placeholder="请选择类型"
                                               style="width: 300px;">
                                        <el-option v-for="item in pointType"
                                                   :key="item.value"
                                                   :label="item.label"
                                                   :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
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
    </div>
</template>

<script>
import TreeSelect from "@/components/TreeSelect/tree-select";
import waves from "@/directive/waves"; // 按钮水波纹
import { updateCampusControlPoint } from '@/api/basic-info';

export default {
    name: "ModelView",
    directives: {
        waves
    },
    components: {
        TreeSelect
    },
    props: {
        treeDatas: {
            type: Array,
            default () {
                return []
            }
        },
        pointTypes: {
            type: Array,
            default () {
                return []
            }
        },
        personnelDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        treeDatas () {
            this.treeData = this.treeDatas
        },
        pointTypes () {
            this.pointType = this.pointTypes
        },
        personnelDatas () {
            this.personnelData = this.personnelDatas
        }
    },
    data () {
        return {
            saving: false,
            treeData: [],  // 所属机构
            pointType: [], // 类型
            personnelData: [], // 负责人
            propsInstObj: {
                value: "baseOfficeId", // ID字段名
                label: "name", // 显示名称
                children: "children" // 子级字段名
            },
            formItem: {
                monitorPointId: '',
                officeId: '',
                principals: '',
                name: '',
                places: '',
                remark: '',
                type: '',
            },
            formItemRules: {
                officeId: [
                    { required: true, message: "请选择所属机构", trigger: "change" }
                ],
                principals: [
                    { required: true, message: '请选择负责人', trigger: 'change' },
                ],
                type: [
                    { required: true, message: '请选择类型', trigger: 'change' },
                ],
            },
            dialogEditInfo: false
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.monitorPointId = data.monitorPointId;
            this.formItem.officeId = data.officeId;
            this.formItem.principals = data.principals;
            this.formItem.name = data.name;
            this.formItem.places = data.places;
            this.formItem.remark = data.remark;
            this.formItem.type = data.type;
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
                    updateCampusControlPoint(this.formItem).then(res => {
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
            this.formItem.name = ''
            this.formItem.places = ''
            this.formItem.remark = ''
            this.$refs['parentTreeSelect'].clearHandle()
            this.$refs[from].resetFields()
        },

        /**
         * 树形选择子组件回传
         * @return {type} {description}
         */
        getParentValue (val) {
            this.formItem.officeId = val
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