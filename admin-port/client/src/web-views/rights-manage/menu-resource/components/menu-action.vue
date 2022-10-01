/**
 * 添加菜单资源功能权限
 * @return {type} {description}
 */

<template>
    <div class="menu-action-view">
        <div class="search-con search-con-top"
             style="margin-bottom: 20px;text-align: center">
            <el-button-group>
                <el-button :disabled="value.menuId && value.menuId!=='0' && !value.hasChild && hasAuthority('systemMenuEdit')?false:true"
                           class="search-btn"
                           type="info"
                           plain
                           @click="handleModal()">
                    添加功能权限
                    <!-- <i type="search" />&nbsp;&nbsp; -->
                </el-button>
            </el-button-group>
        </div>
        <!-- 表格 -->
        <el-table :data="data"
                  style="width: 100%"
                  :loading="loading">
            <el-table-column fixed
                             label="功能名称">
                <template slot-scope="{row}">
                    <el-badge v-if="row.status===1"
                              is-dot
                              class="item"
                              type="success" />
                    <el-badge v-else=""
                              is-dot
                              class="item"
                              type="error" />
                    <span>{{ row.actionName }}</span>
                </template>
            </el-table-column>

            <el-table-column fixed
                             label="功能编码">
                <template slot-scope="{row}">
                    <span>{{ row.actionCode }}</span>
                </template>
            </el-table-column>

            <el-table-column fixed="right"
                             label="操作"
                             width="175">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button type="text"
                                   :disabled="hasAuthority('systemMenuEdit')?false:true"
                                   @click="handleModal(scope.row)">编辑</el-button>
                        <el-button type="text"
                                   :disabled="hasAuthority('systemMenuEdit')?false:true"
                                   @click="handleModal(scope.row,forms[1])">接口授权</el-button>
                        <el-button type="text"
                                   :disabled="hasAuthority('systemMenuEdit')?false:true"
                                   @click="handleRemove(scope.row)">删除</el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <!-- 表格end -->
        <el-dialog :visible.sync="modalVisible"
                   :title="modalTitle"
                   width="980px"
                   @on-cancel="handleReset">
            <el-form ref="form1"
                     v-show="current=='form1'"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-form-item label="所属菜单">
                    <el-input disabled
                              v-model="value.menuName"></el-input>
                </el-form-item>
                <el-form-item label="功能标识"
                              prop="actionCode">
                    <el-input v-model="formItem.actionCode"
                              placeholder="请输入内容"></el-input>
                    <span>菜单标识+自定义标识.默认后缀：View、Edit</span>
                </el-form-item>
                <el-form-item label="功能名称"
                              prop="actionName">
                    <el-input v-model="formItem.actionName"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="优先级">
                    <el-input-number v-model="formItem.priority"></el-input-number>
                </el-form-item>
                <el-formItem label="状态">
                    <el-radio-group v-model="formItem.status">
                        <el-radio label="0">禁用</el-radio>
                        <el-radio label="1">启用</el-radio>
                    </el-radio-group>
                </el-formItem>
                <el-form-item label="描述">
                    <el-input v-model="formItem.actionDesc"
                              type="textarea"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
            </el-form>
            <el-form ref="form2"
                     v-show="current=='form2'"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-alert type="warning"
                          description
                          show-icon>
                    <span>请注意：某一功能可能涉及到很多请求,需绑定相关接口资源,请求服务器时将验证接口访问权限!</span>
                    <el-button type="text"
                               style="font-size: 12px;">支持动态授权,无需重新登录或刷新</el-button>
                </el-alert>
                <el-form-item label="接口资源(选填)"
                              prop="authorities">
                    <el-transfer :data="selectApis"
                                 :titles="['选择接口', '已选择接口']"
                                 v-model="formItem.authorityIds"
                                 @change="handleTransferChange"
                                 filterable>
                        <span slot-scope="{ option }">
                            <el-tooltip class="item"
                                        effect="dark"
                                        :content="option.label"
                                        placement="top">
                                <span style="cursor: pointer">{{ option.label }}</span>
                            </el-tooltip>
                        </span>
                    </el-transfer>
                </el-form-item>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button type="info"
                           plain
                           @click="handleReset">取消</el-button>
                <el-button type="primary"
                           @click="handleSubmit"
                           :loading="saving">保存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {
    getActionsByMenu,
    getAuthorityApi,
    getAuthorityAction,
    addAction,
    updateAction,
    removeAction,
    grantAuthorityAction,} from '@/api/permissi'

export default {
    name: 'MenuAction',
    props: {
        value: Object
    },
    data () {
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('功能标识不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            modalVisible: false,
            saving: false,
            loading: false,
            current: 'form1',
            forms: [
                'form1',
                'form2'
            ],
            modalTitle: '',
            confirmModal: false,
            selectApis: [],
            formItemRules: {
                actionCode: [
                    { required: true, validator: validateEn, message: '功能编码不能为空', trigger: 'blur' }
                ],
                actionName: [
                    { required: true, message: '功能名称不能为空', trigger: 'blur' }
                ]
            },
            formItem: {
                actionId: '',
                actionCode: '',
                actionName: '',
                authorityIds: [],
                status: 1,
                menuId: '',
                priority: 0,
                actionDesc: ''
            },
            columns: [
                {
                    title: '功能名称',
                    slot: 'status',
                    width: 200
                },
                {
                    title: '功能编码',
                    key: 'actionCode',
                    width: 150
                },
                {
                    title: '操作',
                    slot: 'action',
                    fixed: 'right',
                    width: 150
                }
            ],
            data: [],
        }
    },
    methods: {
        /**
         * 控制弹窗
         * @param  {object} data {数据}
         * @param  {string} step {description}
         * @return {undefined} {description}
         */
        handleModal (data, step) {
            if (data) {
                this.formItem = Object.assign({}, this.formItem, data)
            }
            if (!step) {
                step = this.forms[0]
            }
            if (step === this.forms[0]) {
                this.modalTitle = data ? '编辑功能 - ' + this.value.menuName + ' > ' + data.actionName : '添加功能 - ' + this.value.menuName
                this.modalVisible = true
                this.formItem.actionCode = this.formItem.actionId ? this.formItem.actionCode : this.value.menuCode
            }
            if (step === this.forms[1]) {
                this.modalTitle = data ? '接口授权 - ' + this.value.menuName + ' > ' + data.actionName : '接口授权'
                this.handleLoadActionApi(this.formItem.actionId)
            }
            this.current = step
            this.formItem.status = this.formItem.status + ''
        },

        /**
         * 重置
         * @return {type} {description}
         */
        handleReset () {
            const newData = {
                actionId: '',
                actionCode: '',
                actionName: '',
                authorityIds: [],
                status: 1,
                priority: 0,
                actionDesc: ''
            }
            this.formItem = newData
            //重置验证
            this.forms.map(form => {
                this.$refs[form].resetFields()
            })
            this.current = this.forms[0]
            this.modalVisible = false
            this.saving = false
        },

        /**
         * 表单保存
         * @return {type} {description}
         */
        handleSubmit () {
            if (this.current === this.forms[0]) {
                this.$refs[this.current].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        if (this.formItem.actionId) {
                            updateAction(this.formItem).then(res => {
                                this.handleReset()
                                this.handleSearch()
                                if (res.code === 0) {
                                    this.$message({
                                        message: "保存成功",
                                        type: "success",
                                        showClose: true,
                                        duration: 1000 * 1.5
                                    });
                                }
                            }).finally(() => {
                                this.saving = false
                            })
                        } else {
                            addAction(this.formItem).then(res => {
                                this.handleReset()
                                this.handleSearch()
                                if (res.code === 0) {
                                    this.$message({
                                        message: "保存成功",
                                        type: "success",
                                        showClose: true,
                                        duration: 1000 * 1.5
                                    });
                                }
                            }).finally(() => {
                                this.saving = false
                            })
                        }
                    }
                })
            }
            if (this.current === this.forms[1]) {
                this.$refs[this.current].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        grantAuthorityAction({ actionId: this.formItem.actionId, authorityIds: this.formItem.authorityIds }).then(res => {
                            this.handleReset()
                            this.handleSearch()
                            if (res.code === 0) {
                                this.$message({
                                    message: "绑定成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                            }
                        }).finally(() => {
                            this.saving = false
                        })
                    }
                })
            }

        },

        /**
         * 获取功能权限信息
         * @return {type} {description}
         */
        handleSearch () {
            if (!this.value || !this.value.menuId) {
                return
            }
            this.formItem.menuId = this.value.menuId
            this.loading = true
            getActionsByMenu(this.formItem.menuId).then(res => {
                this.data = res.data
            }).finally(() => {
                this.loading = false
            })
        },

        /**
         * 删除功能权限
         * @param  {type} data {description}
         * @return {type} {description}
         */
        handleRemove (data) {
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeAction(data.actionId).then(res => {
                    this.handleSearch()
                    if (res.code === 0) {
                        // this.pageInfo.page = 1
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
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
         * 接口授权
         * @param  {String} actionId {授权id}
         * @return {type} {description}
         */
        handleLoadActionApi (actionId) {
            if (!actionId) {
                return
            }
            const that = this
            const p1 = getAuthorityApi('')
            const p2 = getAuthorityAction(actionId)
            Promise.all([p1, p2]).then(function (values) {
                let res1 = values[0]
                let res2 = values[1]
                if (res1.code === 0) {
                    res1.data.map(item => {
                        item.key = item.authorityId
                        item.label = `${item.prefix.replace('/**', '')}${item.path} - ${item.apiName}`
                        item.disabled = item.path === '/**'
                    })
                    that.selectApis = res1.data
                }
                if (res2.code === 0) {
                    const result = []
                    res2.data.map(item => {
                        result.push(item.authorityId)
                    })
                    that.formItem.authorityIds = result
                }
                that.modalVisible = true
            })
        },

        /**
         * 授权列表处理
         * @param  {type} item {description}
         * @return {type} {description}
         */
        transferRender (h, option) {
            // const str = document.createElement('span');
            // str.innerHTML = option.label;
            // str.setAttribute('title', option.label)
            // return str;
            return <span>{option.label}</span>
            // return <el-tooltip class="item" effect="dark" content='option.label' placement="top-start" >
            //     <span>{option.label}</span>
            // </el-tooltip >
        },

        /**
         * 授权改变事件
         * @param  {type} newTargetKeys {description}
         * @param  {type} direction     {description}
         * @param  {type} moveKeys      {description}
         * @return {type} {description}
         */
        handleTransferChange (newTargetKeys, direction, moveKeys) {
            if (newTargetKeys.indexOf('1') != -1) {
                this.formItem.authorityIds = ['1']
            } else {
                this.formItem.authorityIds = newTargetKeys
            }
        },
    },
    watch: {
        /**
         * 数据监听
         * @param  {string} val {description}
         * @return {undefined} {description}
         */
        value (val) {
            this.handleSearch()
        }
    },
    mounted: function () {
    }
}
</script>

<style lang="scss">
.menu-action-view {
    // 按钮
    .el-button--medium {
        padding: 10px 5px;
    }
    .search-btn {
        border: 1px solid #e9e9eb !important;
    }
    .el-table {
        border: 1px solid #ddd;
    }
    //表格
    .el-table thead tr,
    .el-table thead tr th {
        background: #f8f8f9;
    }
    // 对话框
    .el-dialog {
        border-radius: 8px;
    }
    .el-dialog__body {
        padding: 10px 20px 0 20px;
    }
    .el-dialog__header {
        padding-bottom: 20px;
        border-bottom: 1px solid #eee;
    }
    .el-dialog__title {
        line-height: 16px;
        font-size: 15px;
        color: #333;
        font-weight: bold;
    }
    .dialog-footer {
        .el-button--medium {
            padding: 10px 20px !important;
        }
    }
    .el-alert {
        padding: 0 15px;
        margin-bottom: 10px;
    }
    .el-alert__icon.is-big {
        font-size: 20px;
    }
    .el-alert .el-alert__description {
        margin: 0;
    }
    // 穿梭框
    .el-transfer {
        .el-transfer-panel {
            width: 360px;
        }
        .el-button--medium {
            padding: 10px !important;
        }
        .el-transfer-panel__body {
            height: 400px;
        }
        .el-transfer-panel__list.is-filterable {
            height: 350px;
        }
    }
}

.hoverSpan:hover {
}
</style>

