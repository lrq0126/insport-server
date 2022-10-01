/**
 * 批次列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="80px">
            <el-form-item label="批次名称：">
                <el-select v-model="pageInfo.containerId"
                           filterable
                           placeholder="请选择批次名称"
                           style="width: 200px;">
                    <el-option v-for="(item,index) in arrayData"
                               :key="index"
                               :value="item.id"
                               :label="item.name" />
                </el-select>
            </el-form-item>

            <el-form-item label="单号"
                          label-width="40px">
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
                <el-button type="danger"
                           @click="handleAllRemove()">删 除</el-button>
                <el-button type="warning"
                           @click="handleExportExcel()">导出EXcel</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection"
                             width="55">
            </el-table-column>

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
                             label="创建时间"></el-table-column>

            <el-table-column prop="opreator"
                             align="center"
                             min-width="180"
                             label="操作员"></el-table-column>

            <!-- <el-table-column align="center"
                             min-width="100"
                             label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == '1'"
                            type="info">未出仓</el-tag>
                    <el-tag v-else
                            type="success">出仓</el-tag>
                </template>
            </el-table-column> -->

            <el-table-column label="操作"
                             align="center"
                             width="200">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('批次管理')"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.id)">修改</el-button>
                    <el-button v-if="filterPermissions('批次管理')"
                               type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.id)">删除</el-button>

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
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getBatchIncoming, removeBatchIncoming, selectBatchIncomingList, getBatchIncomingDetail, exportExcelBatchIncoming } from '@/api/batch-management/batch-incoming'
import { getAllRoles } from '@/api/permissi';
import { setTimeout } from 'timers';
import { saveAs } from 'file-saver';
import Axios from 'axios'
import { getToken } from '@/utils/auth'
import config from '@/config'
import { log } from 'util';

export default {
    components: {
        Pagination,
        ModelView
    },
    data () {
        return {
            loading: false,
            arrayData: [],
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
            excelInfo: {
                agentNumber: '',
                containerId: '',
                startTime: '',
                endTime: '',
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
            multipleSelection: []
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
                    this.arrayData = res.content
                }
            })
        },

        /**
       * 获取列表信息
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.excelInfo.agentNumber = this.pageInfo.agentNumber
            this.excelInfo.containerId = this.pageInfo.containerId
            this.excelInfo.startTime = this.pageInfo.startTime
            this.excelInfo.endTime = this.pageInfo.endTime

            this.loading = true
            getBatchIncoming(this.pageInfo).then(res => {
                this.tableData = res.content
                this.pageInfo.total = parseInt(res.map.page.total)
                this.pageInfo.page = parseInt(res.map.page.number)
                this.pageInfo.limit = parseInt(res.map.page.pageSize)
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 800)
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
            this.pageInfo.containerId = '';
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

        handleSelectionChange (val) {
            this.multipleSelection = val;
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
         * 批量删除
         * @return {type} {description}
         */
        handleAllRemove () {
            let _this = this
            if (this.multipleSelection.length > 0) {
                this.$confirm('确定删除选中信息吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = []
                    _this.multipleSelection.forEach(ele => {
                        ids.push(ele.id)
                    });
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
            } else {
                this.$message({
                    message: "请至少勾选一个选项!",
                    type: "error"
                });
            }
        },

        /**
         * 导出excel表格
         * @return {type} {description}
         */
        handleExportExcel () {
            let _this = this
            const baseUrl =
                process.env.NODE_ENV === 'development'
                    ? config.baseUrl.dev
                    : config.baseUrl.pro
            if (this.multipleSelection.length > 0) {
                this.$confirm('确定导出选中信息为excel表格吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let obj = {
                        ids: [],
                        agentNumber: _this.pageInfo.agentNumber,
                        containerId: _this.pageInfo.containerId,
                        startTime: _this.pageInfo.startTime,
                        endTime: _this.pageInfo.endTime,
                    }
                    _this.multipleSelection.forEach(ele => {
                        obj.ids.push(ele.id)
                    });
                    console.log(baseUrl + '/container/number/exportExcel?ids=' + obj.ids + '&agentNumber=' + _this.excelInfo.agentNumber + '&containerId=' + _this.excelInfo.containerId + '&startTime=' + _this.excelInfo.startTime + '&endTime=' + _this.excelInfo.endTime)
                    window.open(baseUrl + '/container/number/exportExcel?ids=' + obj.ids + '&agentNumber=' + _this.excelInfo.agentNumber + '&containerId=' + _this.excelInfo.containerId + '&startTime=' + _this.excelInfo.startTime + '&endTime=' + _this.excelInfo.endTime, '_blank')

                    // exportExcelBatchIncoming(obj).then(res => {
                    //     if (res.code === 100) {
                    //         this.$message({
                    //             message: "删除成功",
                    //             type: "success",
                    //             showClose: true,
                    //             duration: 1000 * 1.5
                    //         });
                    //         _this.handleSearch()
                    //     }
                    // })
                })
            } else {
                let obj = {
                    ids: [],
                    agentNumber: _this.pageInfo.agentNumber,
                    containerId: _this.pageInfo.containerId,
                    startTime: _this.pageInfo.startTime,
                    endTime: _this.pageInfo.endTime,
                }
                _this.multipleSelection.forEach(ele => {
                    obj.ids.push(ele.id)
                });
                console.log(baseUrl + '/container/number/exportExcel?ids=' + obj.ids + '&agentNumber=' + _this.excelInfo.agentNumber + '&containerId=' + _this.excelInfo.containerId + '&startTime=' + _this.excelInfo.startTime + '&endTime=' + _this.excelInfo.endTime)
                window.open(baseUrl + '/container/number/exportExcel?ids=' + obj.ids + '&agentNumber=' + _this.excelInfo.agentNumber + '&containerId=' + _this.excelInfo.containerId + '&startTime=' + _this.excelInfo.startTime + '&endTime=' + _this.excelInfo.endTime, '_blank')
            }
        },


        /**
         * 下载
         * @return {type} {description}
         */
        download (url) {
            let _this = this
            if (this.multipleSelection.length > 0) {
                this.$confirm('确定导出选中信息为excel表格吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let obj = {
                        ids: [],
                        agentNumber: _this.pageInfo.agentNumber,
                        containerId: _this.pageInfo.containerId,
                        startTime: _this.pageInfo.startTime,
                        endTime: _this.pageInfo.endTime,
                    }
                    _this.multipleSelection.forEach(ele => {
                        obj.ids.push(ele.id)
                    });

                    const baseUrl =
                        process.env.NODE_ENV === 'development'
                            ? config.baseUrl.dev
                            : config.baseUrl.pro
                    return Axios({
                        method: 'get',
                        url: baseUrl + '/container/number/exportExcel',
                        // responseType必须值,否则文件下载后乱码
                        responseType: 'arraybuffer',
                        headers: {
                            Authorization: 'Bearer ' + getToken()
                        },
                        params: obj
                    }).then(function (response) {
                        console.log(response)
                        let body = response.data;
                        let contentDisposition = response.headers["content-disposition"];
                        let fileName = ''
                        // 获取文件名
                        for (let key of contentDisposition.split(';')) {
                            let name = key.split('=')[0].trim();
                            if (name === 'filename') {
                                fileName = key.split('=')[1].trim();
                                // 解决中文乱码
                                fileName = decodeURIComponent(escape(fileName));
                                // 去除首尾多余双引号
                                fileName = fileName.replace(/"/g, '');
                            }
                        }
                        if (body) {
                            let file = new Blob([body], {
                                type: "application/octet-stream"
                            });
                            saveAs(file, fileName)
                        } else {
                            // 下载失败
                        }
                    }).catch(error => {
                        console.log(error)
                        // this.$log.log(error);
                    });

                })
            } else {
                this.$message({
                    message: "请至少勾选一个选项!",
                    type: "error"
                });
            }

        }

    },
    mounted () {
        this.handleSelect()
        this.handleSearch()
    }
};
</script>

