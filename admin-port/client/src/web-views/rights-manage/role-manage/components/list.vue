/**
 * 角色列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="role-manage-view">
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="80px">
            <el-form-item label="角色名称"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.roleName"
                          placeholder="请输入角色名称" />
            </el-form-item>
            <el-form-item label="角色标识"
                          prop="roleComment">
                <el-input type="text"
                          v-model="pageInfo.roleComment"
                          placeholder="请输入角色标识" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  v-loading="loading"
                  style="width: 100%;"
                  row-key="id"
                  border>
            <el-table-column prop="roleName"
                             min-width="160"
                             label="角色名称"></el-table-column>
            <el-table-column prop="roleComment"
                             min-width="120"
                             label="角色标识"></el-table-column>
            <el-table-column prop="type"
                             min-width="100"
                             label="状态">
                <template slot-scope="scope">
                    <div v-if="scope.row.isAvailable === 1">
                        <el-badge is-dot
                                  type="success"
                                  class="el-badge-item"></el-badge>
                        <span>启用</span>
                    </div>
                    <div v-else>
                        <el-badge is-dot
                                  class="el-badge-item"></el-badge>
                        <span>禁用</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="remark"
                             min-width="160"
                             label="描述"></el-table-column>
            <el-table-column prop="address"
                             align="center"
                             width="180"
                             label="操作">
                <template slot-scope="scope">
                    <!-- <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button> -->
                    <el-button v-if="filterPermissions('编辑角色/选择角色权限')"
                               type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row,forms[1])"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">分配菜单</el-button>
                    <el-dropdown v-if="filterPermissions('删除角色')"
                                 v-show="hasAuthority('systemRoleEdit')"
                                 transfer
                                 ref="dropdown"
                                 @command="handleClick($event,scope.row)">
                        <el-button type="info"
                                   size="mini"
                                   plain
                                   :disabled="scope.row.roleCode === 'all' ?true:false">
                            更多<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <!-- <el-dropdown-item command="addUser">添加人员</el-dropdown-item> -->
                            <el-dropdown-item command="remove">删除角色</el-dropdown-item>
                            <el-dropdown-item command="enable">{{scope.row.isAvailable == 1 ? '停用' : '启动'}}</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>

        <!-- 页码 -->
        <!-- <pagination v-show="total>0"
                    :total="total"
                    :page.sync="listQuery.page"
                    :limit.sync="listQuery.limit"
                    @pagination="getList" /> -->

        <!-- 弹窗 -->
        <el-dialog :visible.sync="modalVisible"
                   :title="modalTitle"
                   width="900px"
                   @close="handleReset">
            <el-form v-show="current == 'form1'"
                     ref="form1"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-form-item label="角色标识"
                              prop="roleCode">
                    <el-input v-model="formItem.roleCode"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="角色名称"
                              prop="roleName">
                    <el-input v-model="formItem.roleName"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="formItem.status">
                        <el-radio label="0">禁用</el-radio>
                        <el-radio label="1">启用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="formItem.roleDesc"
                              type="textarea"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
            </el-form>

            <!-- 编辑信息 -->
            <el-form v-show="current == 'form2'"
                     ref="form2"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-form-item label="功能菜单(选填)"
                              prop="grantMenus">

                    <!-- 树形表格 -->
                    <el-tree :data="selectMenus"
                             show-checkbox
                             node-key="id"
                             ref="tree"
                             highlight-current
                             @check-change="hadnleTree"
                             :default-checked-keys="formItem.permissionIds"
                             :props="defaultProps">
                    </el-tree>
                    

                </el-form-item>
            </el-form>

            <!-- 穿梭框 -->
            <el-form v-show="current == 'form3'"
                     ref="form3"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-form-item label="添加成员(选填)"
                              prop="authorities">
                    <el-transfer :data="selectUsers"
                                 :titles="['选择用户', '已选择用户']"
                                 v-model="formItem.userIds"
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

            <div slot="footer">
                <!-- <el-button v-show="current == 'form2'" @click="addMenu()">添加菜单</el-button> -->
                <el-button type="primary"
                           :loading="saving"
                           @click="handleSubmit">保存</el-button>&nbsp;
                <el-button type="default"
                           @click="handleReset">取消</el-button>
            </div>
            <add-menu-view ref="addMenuView" @reselect="getPermission"/>
        </el-dialog>
    </div>
</template>

<script>

import Pagination from "@/components/Pagination";
import { getRoleManagement, enableRoleManagement, permissionRoleManagement, bindingRoleManagement, removeRoleManagement } from '@/api/rights-manage/role-manage'
import { getAllUsers } from '@/api/user'
import { listConvertTree } from '@/utils/public';
import AddMenuView from './add-menu'

export default {
    name: "RoleManageList",
    components: {
        Pagination,
        AddMenuView
    },
    data () {
        /**
         * 自定义验证规则
         */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('角色标识不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            titles: ['选择接口', '已选择接口'],
            listStyle: {
                width: '240px',
                height: '500px'
            },
            loading: false,
            modalVisible: false,
            modalTitle: '',
            saving: false,
            current: 'form1',
            forms: [
                'form1',
                'form2',
                'form3'
            ],
            selectApis: [],
            selectMenus: [],
            defaultProps: {
                children: 'children',
                label: 'lable'
            }, // 树形
            selectUsers: [],
            pageInfo: {
                total: 0,
                page: 1,
                limit: 10,
                roleCode: '',
                roleName: '',
                roleComment: ''
            },
            formItemRules: {
                roleCode: [
                    { required: true, validator: validateEn, trigger: 'blur' }
                ],
                roleName: [
                    { required: true, message: '角色名称不能为空', trigger: 'blur' }
                ]
            },
            formItem: {
                id: '',
                roleId: '',
                permissionIds: [],
                roleCode: '',
                roleName: '',
                path: '',
                status: 1,
                menuId: '',
                priority: 0,
                roleDesc: '',
                grantMenus: [],
                grantActions: [],
                expireTime: '',
                isExpired: false,
                userIds: []
            },
            getHalfCheckedKeys: [],  // 半节点
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '角色名称',
                    key: 'roleName',
                    width: 300,
                },
                {
                    title: '角色标识',
                    key: 'roleCode',
                    width: 300,
                },
                {
                    title: '状态',
                    slot: 'status',
                    key: 'status',
                    filters: [
                        {
                            label: '禁用',
                            value: 0
                        },
                        {
                            label: '启用',
                            value: 1
                        }
                    ],
                    filterMultiple: false,
                    filterMethod (value, row) {
                        if (value === 0) {
                            return row.status === 0
                        } else if (value === 1) {
                            return row.status === 1
                        }
                    }
                },
                {
                    title: '描述',
                    key: 'roleDesc',
                    width: 450
                },
                {
                    title: '最后修改时间',
                    key: 'updateTime',
                    width: 200,
                },
                {
                    title: '操作',
                    slot: 'action',
                    fixed: 'right',
                    width: 200
                }
            ],
            columns2: [
                {
                    title: '菜单',
                    key: 'menuName',
                    minWidth: '250px',
                },
                {
                    title: '功能',
                    type: 'template',
                    template: 'operation',
                    minWidth: '200px'
                }
            ],
            tableData: [],  // 表格数据
            total: 0,
            listQuery: {
                page: 1,
                limit: 20,
                importance: "",
                title: undefined,
                type: "",
                sort: "+id"
            },
        };
    },
    methods: {
        hadnleTree (data) {
            this.formItem.permissionIds = this.$refs.tree.getCheckedKeys()  // 选中节点
            this.getHalfCheckedKeys = this.$refs.tree.getHalfCheckedKeys()  // 半节点
        },

        /**
         * 分页
         * @return {type} {description}
         */
        getList () {
            console.log('asd');
        },

        addMenu(){
            this.$refs['addMenuView'].openMenuDialog();
        },
        getPermission(){
            this.handleLoadRoleGranted(this.formItem.roleId)
        },
        /**
        * 获取角色列表
        * @param  {nnumber} page {初始化页码}
        * @return {type} {description}
        */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getRoleManagement(this.pageInfo).then(res => {
                if (res.code == 100) {
                    this.tableData = res.content
                }
                // this.pageInfo.total = parseInt(res.data.total)
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        /**
         * 添加/编辑角色/添加成员
         * @param  {type} data {description}
         * @param  {type} step {description}
         * @return {type} {description}
         */
        handleModal (data, step) {
            if (data) {
                this.formItem = Object.assign({}, this.formItem, data)
            }
            if (!step) {
                step = this.forms[0]
            }
            if (step === this.forms[0]) {
                this.modalTitle = data ? '编辑角色 - ' + data.roleName : '添加用户'
                this.modalVisible = true
            }
            if (step === this.forms[1]) {
                this.modalTitle = data ? '分配权限 - ' + data.roleName : '分配权限'
                this.handleLoadRoleGranted(data.id)
                this.formItem.roleId = data.id
            }
            // if (step === this.forms[2]) {
            //     this.modalTitle = data ? '添加成员 - ' + data.roleName : '添加成员'
            //     this.handleLoadRoleUsers(this.formItem.roleId)
            // }
            if (!step) {
                step = this.forms[0]
            }
            this.formItem.status = this.formItem.status + ''
            this.current = step
        },

        /**
         * 表单搜索重置
         * @param  {string} form 
         * @return {type} {description}
         */
        handleResetSearch (form) {
            this.pageInfo.roleComment = ''
            this.pageInfo.roleName = ''
        },

        /**
         * 表单重置
         * @param  {string} form 
         * @return {type} {description}
         */
        handleResetForm (form) {
            this.$refs[form].resetFields()
        },

        /**
         * 弹窗里面的表单重置
         * @return {type} {description}
         */
        handleReset () {
            const newData = {
                roleId: '',
                roleCode: '',
                roleName: '',
                path: '',
                status: 1,
                menuId: '',
                priority: 0,
                roleDesc: '',
                grantMenus: [],
                grantActions: [],
                expireTime: '',
                isExpired: false,
                userIds: []
            }
            this.formItem = newData
            //重置验证
            this.forms.map(form => {
                this.handleResetForm(form)
            })
            this.current = this.forms[0]
            this.formItem.grantMenus = []
            this.formItem.grantActions = []
            this.modalVisible = false
            this.saving = false
        },

        /**
         *  弹窗保存
         * @return {type} {description}
         */
        handleSubmit () {
            if (this.current === this.forms[0]) {
                this.$refs[this.current].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        // 编辑
                        if (this.formItem.roleId) {
                            updateRole(this.formItem).then(res => {
                                if (res.code === 0) {
                                    this.$message({
                                        message: "编辑成功",
                                        type: "success",
                                        showClose: true,
                                        duration: 1000 * 1.5
                                    });
                                    this.handleReset()
                                }
                                this.handleSearch()
                            }).finally(() => {
                                this.saving = false
                            })
                        } else {
                            // 保存
                            // addRole(this.formItem).then(res => {
                            //     if (res.code === 0) {
                            //         this.$message({
                            //             message: "保存成功",
                            //             type: "success",
                            //             showClose: true,
                            //             duration: 1000 * 1.5
                            //         });
                            //         this.handleReset()
                            //     }
                            //     this.handleSearch()
                            // }).finally(() => {
                            //     this.saving = false
                            // })
                        }
                    }
                })
            }
            // 角色授权
            if (this.current === this.forms[1]) {
                this.$refs[this.current].validate((valid) => {
                    if (valid) {
                        // const authorityIds = this.getCheckedAuthorities()
                        this.saving = true
                        // grantAuthorityRole({
                        //     roleId: this.formItem.roleId,
                        //     expireTime: this.formItem.expireTime ? this.formItem.expireTime : '',
                        //     authorityIds: authorityIds
                        // }).then(res => {

                        if (!this.formItem.permissionIds.length > 0) {
                            this.$message({
                                message: "请选择菜单",
                                type: "warning",
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.saving = true
                            return
                        }

                        // if (this.getHalfCheckedKeys.length > 0) {
                        //     this.getHalfCheckedKeys.forEach(ele => {
                        //         this.formItem.permissionIds.push(ele)
                        //     });
                        // }  // 构造数据
                        bindingRoleManagement(this.formItem).then(res => {
                            if (res.code == 100) {
                                this.$message({
                                    message: "授权成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                                this.handleReset()
                            }
                            this.handleSearch()
                        }).finally(() => {
                            this.saving = false
                        })
                    }
                })
            }

            if (this.current === this.forms[2]) {
                this.$refs[this.current].validate((valid) => {
                    if (valid) {
                        this.saving = true
                        addRoleUsers({
                            roleId: this.formItem.roleId,
                            userIds: this.formItem.userIds
                        }).then(res => {
                            if (res.code === 0) {
                                this.$message({
                                    message: "保存成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                                this.handleReset()
                            }
                            this.handleSearch()
                        }).finally(() => {
                            this.saving = false
                        })
                    }
                })
            }
        },

        /**
         * 页码
         * @param  {number} current {当前页码}
         * @return {type} {description}
         */
        handlePage (current) {
            this.pageInfo.page = current
            this.handleSearch()
        },

        /**
         * 切换每页条数时的回调
         * @param  {number} size {每一页条数}
         * @return {type} {description}
         */
        handlePageSize (size) {
            this.pageInfo.limit = size
            this.handleSearch()
        },

        /**
         * 删除角色
         * @param  {type} data {description}
         * @return {type} {description}
         */
        handleRemove (data) {
            this.$confirm('确定删除角色吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeRoleManagement(data.id).then(res => {
                    if (res.code === 100) {
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
                // this.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });
        },

        /**
         * @function {function name}
         * @return {type} {description}
         */
        handleEnable (data) {
            let obj = {
                roleId: data.id,
                isAvailable: 1,
            }
            if (data.isAvailable == 1) {
                obj.isAvailable = 2
            } else {
                obj.isAvailable = 1
            }

            this.$confirm('确定' + (data.isAvailable == 1 ? '停用' : '启动') + '角色吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                enableRoleManagement(obj).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: (data.isAvailable == 1 ? '停用' : '启动') + "成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.handleSearch()
                    }
                })
            }).catch(() => {

            });
        },

        /**
         * 数据处理
         * @return {type} {description}
         */
        getCheckedAuthorities () {
            const menus = this.$refs['tree'].getCheckedProp('authorityId')
            console.log(menus)
            console.log(menus.concat(this.formItem.grantActions))
            return menus.concat(this.formItem.grantActions)
        },

        /**
         * 权限分配数据处理
         * @param  {number} roleId {角色id}
         * @return {type} {description}
         */
        handleLoadRoleGranted (roleId) {
            if (!roleId) {
                return
            }
            const that = this
            const p1 = permissionRoleManagement(roleId)  // 获取菜单列表信息
            Promise.all([p1]).then(function (values) {
                let res1 = values[0]
                that.selectMenus = res1.content ? res1.content.permissionVos : []

                that.formItem.permissionIds = res1.content.permissiomIds ? res1.content.permissiomIds.split(',') : []  // 选中数据
                
                that.modalVisible = true
            })
        },

        /**
         * 添加角色（人员）
         * @param  {number} roleId {角色id}
         * @return {type} {description}
         */
        handleLoadRoleUsers (roleId) {
            if (!roleId) {
                return
            }
            const that = this
            const p1 = getAllUsers()  // 获取所有用户列表
            const p2 = getRoleUsers(roleId)  // 获取选中角色
            Promise.all([p1, p2]).then(function (values) {
                let res1 = values[0]
                let res2 = values[1]
                if (res1.code === 0) {
                    res1.data.map(item => {
                        item.key = item.userId
                        item.label = `${item.userName}(${item.nickName})`
                    })
                    that.selectUsers = res1.data
                }
                if (res2.code === 0) {
                    res2.data.map(item => {
                        that.formItem.userIds.push(item.userId)
                    })
                }
                that.modalVisible = true
            })
        },

        /**
         * 穿梭框每行数据渲染函数
         * @param  {function} h {方法}
         * @param {object} option {选中数据}
         * @return {type} {description}
         */
        transferRender (h, option) {
            return <span title='{option.label}'>{option.label}</span>
        },

        /**
         * 穿梭框改变事件
         * @param  {array} newTargetKeys {选中数组}
         * @param  {string} direction     {去左边/去右边}
         * @param  {array} moveKeys      {选中数组}
         * @return {undefined} {description}
         */
        handleTransferChange (newTargetKeys, direction, moveKeys) {
            this.formItem.userIds = newTargetKeys
        },

        /**
         * 拉下菜单回调
         * @param  {string} name {属性名}
         * @param  {object} row  {数据对象}
         * @return {type} {description}
         */
        handleClick (name, row) {
            switch (name) {
                case 'addUser':
                    this.handleModal(row, this.forms[2])
                    break
                case 'remove':
                    this.handleRemove(row)
                    break

                case 'enable':
                    this.handleEnable(row)
                    break
            }
        }
    },
    mounted () {
        this.handleSearch()
    }
};
</script>
<style lang="scss">
.role-manage-view {
    // 对话框
    .el-dialog {
        border-radius: 8px;
    }
    .el-dialog__body {
        padding: 20px 20px 0 20px;
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
            width: 300px;
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
    // 树形
    .el-tree-node__label {
        margin-left: 10px;
    }
}
</style>

<style lang="scss" scoped>
.role-manage-view {
    .el-dropdown {
        vertical-align: top;
    }
    .el-dropdown + .el-dropdown {
        margin-left: 15px;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }
    //标记
    .el-badge-item {
        top: 5px !important;
    }
}
</style>


