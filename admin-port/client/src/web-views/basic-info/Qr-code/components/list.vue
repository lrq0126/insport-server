/**
 * 二维码列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="90px">
            <el-form-item label="二维码名称"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入关键字" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column label="二维码">
                <template slot-scope="scope">
                    <img :src="url + '/base/static' + scope.row.imgUrl"
                         alt="平安教育"
                         style="height:100px; width:100px;">
                </template>
            </el-table-column>
            <el-table-column prop="name"
                             label="二维码名称"></el-table-column>
            <el-table-column label="校园管控点">
                <template slot-scope="scope">
                    <span>{{ scope.row.monitorPointId | filterCampusContolPoints(CampusControlPoint) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="remark"
                             label="描述"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.qrCodeId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.qrCodeId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button>
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="download(scope.row.imgUrl)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">下载</el-button>
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
                    @updateList="handleSearch"
                    :campus-control-points="CampusControlPoint" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getQrCode, getQrCodeDetail, removeQrCode, downloadQrCode } from '@/api/basic-info'
import { setTimeout } from 'timers';
import { saveAs } from 'file-saver';
import Axios from 'axios'
import { getToken } from '@/utils/auth'
import config from '@/config'

export default {
    components: {
        Pagination,
        ModelView
    },
    props: {
        CampusControlPoints: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        CampusControlPoints () {
            this.CampusControlPoint = this.CampusControlPoints
        }
    },
    filters: {
        filterCampusContolPoints (value, array) {
            let name = ''
            if (value) {
                array.forEach(ele => {
                    if (ele.monitorPointId == value) {
                        name = ele.name
                    }
                });
                return name
            } else {
                return name
            }
        }
    },
    data () {
        return {
            url: '',
            loading: false,
            CampusControlPoint: [],  // 校园管控点
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                name: '',
                code: ''
            },  // 页码传参数
        };
    },
    created () {
        if (process.env.NODE_ENV === 'development') {
            this.url = config.baseUrl.dev
        } else {
            this.url = config.baseUrl.pro
        }
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
       * 获取列表信息
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getQrCode(this.pageInfo).then(res => {
                this.tableData = res.data.records
                this.pageInfo.total = parseInt(res.data.total)
                this.pageInfo.page = parseInt(res.data.current)
                this.pageInfo.limit = parseInt(res.data.size)
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        /**
        * 表单重置
        * @param  {string} form 
        * @return {type} {description} 
        */
        handleResetForm (form) {
            this.pageInfo.name = '';
            this.pageInfo.code = '';
            this.$refs[form].resetFields()
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getQrCodeDetail(id).then(res => {
                    if (res.code === 0) {
                        this.$refs['modelView'].handEditInfoModel(res.data);
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
        },

        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeQrCode(id).then(res => {
                    if (res.code === 0) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.handleSearch()
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        /**
         * 下载
         * @return {type} {description}
         */
        download (url) {
            const baseUrl =
                process.env.NODE_ENV === 'development'
                    ? config.baseUrl.dev
                    : config.baseUrl.pro
            return Axios({
                method: 'get',
                url: baseUrl + '/base/fileTransfer/download',
                // responseType必须值,否则文件下载后乱码
                responseType: 'arraybuffer',
                headers: {
                    Authorization: 'Bearer ' + getToken()
                },
                params: {
                    path: url
                }
            }).then(function (response) {
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
        }

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

