/**
 * 轨迹编辑详情
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="top-up-create">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card">
            <el-tab-pane label="轨迹编辑详情"
                         name="list">
                <keep-alive>
                    <div>

                        <!-- 搜索 -->
                        <el-form ref="searchForm"
                                 :model="pageInfo"
                                 inline
                                 label-width="75px">

                            <el-form-item>
                                <el-button type="primary"
                                           plain
                                           @click="handleAdd">新增轨迹详情</el-button>
                            </el-form-item>

                        </el-form>

                        <el-form ref="form"
                                 :model="formItem"
                                 :rules="formItemRules"
                                 label-width="120px">
                            <!-- 表格 -->
                            <el-table :data="tableData"
                                      border
                                      v-loading="loading"
                                      style="width: 100%">

                                <el-table-column type="index"
                                                 label="序号"
                                                 width="50"
                                                 align="center"></el-table-column>

                                <el-table-column prop="message"
                                                 label="轨迹内容"
                                                 min-width="250"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'formItem.' + scope.$index + '.message'"
                                                      :rules="{required: true, message: '请输入轨迹内容', trigger: 'change'}">
                                            <el-input v-model="scope.row.message"
                                                      type="textarea"
                                                      :rows="2"
                                                      placeholder="请输入轨迹内容"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="location"
                                                 label="轨迹站点"
                                                 min-width="250"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'formItem.' + scope.$index + '.location'"
                                                      :rules="{required: true, message: '请输入轨迹站点', trigger: 'change'}">
                                            <el-input v-model="scope.row.location"
                                                      type="textarea"
                                                      :rows="2"
                                                      placeholder="请输入轨迹站点"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="trackTime"
                                                 label="轨迹时间"
                                                 min-width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'formItem.' + scope.$index + '.trackTime'"
                                                      :rules="{required: true, message: '请输入轨迹时间', trigger: 'change'}">
                                            <el-date-picker v-model="scope.row.trackTime"
                                                        align="right"
                                                        type="datetime"
                                                        :rows="2"
                                                        placeholder="选择日期"
                                                        value-format="yyyy-MM-dd hh:mm:ss"
                                                        style="width: 250px;"></el-date-picker>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="orderNumber"
                                                 label="转单号(不可修改)"
                                                 min-width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'formItem.' + scope.$index + '.orderNumber'">
                                            <el-input v-model="scope.row.orderNumber"
                                                      type="text"
                                                      :rows="2"
                                                      readonly="true"
                                                      placeholder="请输入转单号"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="操作"
                                                 fixed="right"
                                                 width="180"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-button type="danger" v-if="isInside == 1"
                                                   size="mini"
                                                   plain
                                                   @click="handleDelete(scope.$index)"
                                                   :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button>
                                    </template>
                                </el-table-column>

                            </el-table>
                                                    <!-- 按钮 -->
                            <div style="margin: 20px 0 20px 0;text-align: center;">
                                <el-form-item label-width="280px">
                                    <el-button v-waves  v-if="isInside == 1"
                                            type="primary"
                                            @click="handleSubmit('form')">保 存</el-button>
                                    <!-- <el-button @click="GoBack">返回上一层</el-button> -->
                                </el-form-item>
                            </div>
                        </el-form>
                    </div>
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import { getTrackList } from '@/api/warehouse-management/warehouse-list'
import quillConfig from '@/utils/quill-config.js'
import Cookies from 'js-cookie'


export default {
    name: 'Create',
    components:{
        waves
    },
    data () {
        /**
         * 自定义验证规则
         */
        const validateEn = (rule, value, callback) => {
            let reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/
            if (value === '') {
                callback(new Error('充值金额不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允输入数字金额，并且最多保留两位小数。（正确示例：100.00）'))
            } else {
                callback()
            }
        }
        return {
            loading: false,
            saving: false,
            activeName: 'list',
            TagView: [],
            tableData: [],
            number:'',
            isInside:'',
            pageInfo: {
                customerName: '',
                loginName: '',
                orderNumber: '',
                agentNumber: '',
                agentName: '',
            },  // 页码传参数
            formItem: {
                orderNumber: '',
                message: '',
                location: '',
                trackTime: '',
            },
            agentList: [],//代理商
            formItemRules: {
                orderNumber: [
                    { required: true,  message: '请输入转单号', trigger: 'blur' },
                ],
                location: [
                    { required: true,  message: '请选择轨迹站点', trigger: 'blur' },
                ],
                trackTime: [
                    { required: true,  message: '请选择轨迹时间', trigger: 'blur' },
                ],
                message: [
                    { required: true, message: '请填写轨迹内容', trigger: 'blur' },
                ],
            },
        };
    },
    methods: {
        /**
         * @function {function name}
         * @param  {type} id {description}
         * @return {type} {description}
         */
        handleSearch (orderNumber,isInside) {
            this.number = orderNumber,
            this.isInside = isInside
            let obj = {
                orderNumber: orderNumber,
                desc: 2,
            }
            getTrackList(obj).then(res => {
                if (res.code === 100) {
                    this.tableData = res.content
                }
            })
        },

        handleAdd () {
            const obj = {
                orderNumber: this.number,
                message: '',
                location: '',
                trackTime: '',
            }
            this.tableData.push(obj);
        },

        /**
        * 删除小包裹
        * @return {type} {description}
        */
        handleDelete (index) {
            if (index > -1) {
                this.tableData.splice(index, 1);
            }
        },

        /**
        * 保存
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            console.log("formItem"+this.formItem);
            this.$refs[from].validate((valid) => {
                if (valid) {
                    alert("1")
                //     this.saving = true
                //     saveSingleAgentOrder(this.formItem).then(res => {
                //         if (res.code === 100) {
                //             this.$message({
                //                 message: '轨迹编辑详情',
                //                 type: 'success',
                //                 showClose: true,
                //                 duration: 1000 * 1.5 * 2
                //             });
                //             this.Goback()
                //             // this.handleReset('form')
                //         }
                //     }).finally(() => {
                //         this.saving = false
                //     })
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

        /**
         * 返回上一层
         * @return {type} {description}
         */
        Goback () {
            this.$router.push({
                name: 'TrackList'
            })
            this.$store.dispatch('tagsView/delView', this.TagView).then(({ visitedViews }) => {
                if (this.isActive(this.TagView)) {
                    this.toLastView(visitedViews, this.TagView)
                }
            })
        },

        /**
         * 修改Tabs-view内容
         * @return {type} {description}
         */
        setTagsViewTitle () {
            const title = '轨迹编辑详情'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },

    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach(ele => {
            if (ele.title == '轨迹编辑详情') {
                this.TagView = ele
            }
        })
        this.handleSearch(this.$route.query.orderNumber,this.$route.query.isInside)
    }
};
</script>

<style lang="scss" scope>
.top-up-create {
    margin: 10px;
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .el-input.is-disabled .el-input__inner {
        color: #333;
    }
}
</style>
