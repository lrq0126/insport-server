/**
 * 查看所有包裹
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="top-up-create">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card">
            <el-tab-pane label="查看所有包裹"
                         name="list">
                <keep-alive>
                    <div>
                        <!-- 搜索 -->
                        <el-form ref="searchForm"
                                 :model="pageInfo"
                                 inline
                                 label-width="75px">
                            <el-form-item label="单号">
                                <el-input type="text"
                                          v-model="pageInfo.agentNumber"
                                          placeholder="请输入单号" />
                            </el-form-item>

                            <el-form-item label="扫面时间">
                                <el-date-picker v-model="pageInfo.dataTime"
                                                type="datetimerange"
                                                format="yyyy-MM-dd HH:mm:ss"
                                                value-format="yyyy-MM-dd HH:mm:ss"
                                                :picker-options="pickerOptions"
                                                @change="handlePicker"
                                                range-separator="至"
                                                start-placeholder="开始日期"
                                                end-placeholder="结束日期"
                                                align="right">
                                </el-date-picker>
                            </el-form-item>

                            <el-form-item>
                                <el-button type="primary"
                                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
                                <el-button type="warning"
                                           @click="Goback('searchForm')">返 回</el-button>
                            </el-form-item>

                        </el-form>

                        <!-- 表格 -->
                        <el-table :data="tableData"
                                  border
                                  v-loading="loading"
                                  style="width: 100%">

                            <el-table-column type="index"
                                             label="序号"
                                             width="50"
                                             align="center"></el-table-column>

                            <el-table-column prop="cname"
                                             align="center"
                                             min-width="250"
                                             label="批次名称"></el-table-column>

                            <el-table-column prop="agentNumber"
                                             align="center"
                                             min-width="180"
                                             label="单号"></el-table-column>

                            <el-table-column prop="createTime"
                                             align="center"
                                             min-width="180"
                                             label="扫面时间"></el-table-column>

                            <el-table-column prop="opreator"
                                             align="center"
                                             min-width="120"
                                             label="操作员"></el-table-column>

                            <el-table-column label="操作"
                                             fixed="right"
                                             width="200"
                                             align="center">
                                <template slot-scope="scope">
                                    <el-button type="primary"
                                               size="mini"
                                               plain
                                               @click="handleModal(scope.row.id)"
                                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">修改</el-button>

                                    <el-button type="danger"
                                               size="mini"
                                               plain
                                               @click="handleRemove(scope.row.id)"
                                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button>
                                </template>
                            </el-table-column>

                        </el-table>

                        <!-- 页码 -->
                        <pagination v-show="pageInfo.total>0"
                                    :total="pageInfo.total"
                                    :current.sync="pageInfo.page"
                                    :limit.sync="pageInfo.limit"
                                    @pagination="pagination" />

                        <!-- 弹窗信息 -->
                        <model-view ref="modelView"
                                    @updateList="handleSearch" />
                    </div>
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getBatchIncoming, removeBatchIncoming, selectBatchIncomingList, getBatchIncomingDetail } from '@/api/batch-management/batch-incoming'
import quillConfig from '@/utils/quill-config.js'
import Cookies from 'js-cookie'


export default {
    name: 'Create',
    components: {
        Pagination,
        ModelView
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
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                agentNumber: '',
                containerId: '',
                startTime: '',
                endTime: '',
                dataTime: '',
            },  // 页码传参数
            formItem: {
                customerName: '',
                loginName: '',
                customerNo: '',
                orderNumber: '',
                agentId: '',
                agentName: '',
                money: '',
                agentNumber: '',
                message: '',
                remarks: '',
                amount: '',
            },
            agentList: [],//代理商
            formItemRules: {
                money: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                agentId: [
                    { required: true, trigger: 'blur' },
                ],
                agentNumber: [
                    { required: true, message: '请填写转单号', trigger: 'blur' },
                ],
            },
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            },
        };
    },
    methods: {
        /**
         * 分页
         * @param {object} 
         * @return {type} {description}
         */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page
                this.handleSearch()
            } else {
                this.pageInfo.limit = pageObj.limit
                this.handleSearch(1)
            }
        },

        /**
         * 下拉选中数据
         * @param  {type} id {description}
         * @return {type} {description}
         */
        handleSelect () {
            selectBatchIncomingList().then(res => {
                if (res.code === 100) {
                    // this.tableData = res.content
                }
            })
        },

        /**
         * 列表数据
         * @param  {type} id {description}
         * @return {type} {description}
         */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getBatchIncoming(this.pageInfo).then(res => {
                if (res.code === 100) {
                    this.tableData = res.content
                    this.pageInfo.total = parseInt(res.map.page.total)
                    this.pageInfo.page = parseInt(res.map.page.number)
                    this.pageInfo.limit = parseInt(res.map.page.pageSize)
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 800);
            })
        },

        /**
        * 时间控件（change事件）
        * @return {type} {description}
        */
        handlePicker (data) {
            if (data) {
                this.pageInfo.startTime = data[0]
                this.pageInfo.endTime = data[1]
            } else {
                this.pageInfo.startTime = ''
                this.pageInfo.endTime = ''
            }
        },

        /**
       * 表单重置
       * @param  {string} form 
       * @return {type} {description} 
       */
        handleResetForm (form) {
            this.pageInfo.agentNumber = '';
            this.pageInfo.dataTime = '';
            this.pageInfo.startTime = '';
            this.pageInfo.endTime = '';
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            getBatchIncomingDetail(id).then(res => {
                this.$refs['modelView'].handDetailsInfoModel(res.content);
            })
        },

        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            let _this = this
            this.$confirm('确定删除信息吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = [id]
                removeBatchIncoming(ids).then(res => {
                    if (res.code === 100) {
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        _this.handleSearch()
                    }
                })
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
                    saveSingleAgentOrder(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '查看所有包裹',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.Goback()
                            // this.handleReset('form')
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        /**
         * 返回上一层
         * @return {type} {description}
         */
        Goback () {
            this.$router.push({
                name: 'BatchList'
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
            const title = '查看所有包裹'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },

    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach(ele => {
            if (ele.title == '查看所有包裹') {
                this.TagView = ele
            }
        })

        this.pageInfo.containerId = this.$route.query.id
        this.handleSelect()
        this.handleSearch()
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
