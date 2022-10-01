/**
 * 账号管理列表
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
            <el-form-item label="登录名"
                          prop="userName">
                <el-input type="text"
                          v-model="pageInfo.userName"
                          placeholder="请输入关键字" />
            </el-form-item>

            <el-form-item label="手机号"
                          prop="mobile">
                <el-input type="text"
                          v-model="pageInfo.mobile"
                          placeholder="请输入登陆账号" />
            </el-form-item>

            <el-form-item label="邮箱"
                          prop="email">
                <el-input type="text"
                          v-model="pageInfo.email"
                          placeholder="请输入登陆账号" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column prop="userName"
                             label="登录名"></el-table-column>
            <el-table-column prop="nickName"
                             label="昵称"></el-table-column>
            <el-table-column prop="email"
                             label="邮箱"></el-table-column>
            <el-table-column prop="mobile"
                             label="手机号"></el-table-column>
            <el-table-column label="人员">
                <template slot-scope="scope">
                    <span>{{scope.row.personnelId | filterPersonData(personData)}}</span>
                </template>
            </el-table-column>
            <el-table-column label="账号类型">
                <template slot-scope="scope">
                    <span>{{scope.row.accountType | filterAccountType(accountTypeData)}}</span>
                </template>
            </el-table-column>
            <el-table-column label="状态"
                             width="80"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == '2'"
                            type="info">锁定</el-tag>
                    <el-tag v-if="scope.row.status == '1'"
                            type="success">正常</el-tag>
                    <el-tag v-if="scope.row.status == '0'"
                            type="danger">禁用</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="registerTime"
                             label="注册时间"></el-table-column>
            <el-table-column prop="userDesc"
                             label="描述"></el-table-column>
            <el-table-column label="操作"
                             width="260">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleLoadRoles(scope.row.userId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">分配角色</el-button>
                    <el-dropdown v-show="hasAuthority('systemRoleEdit')"
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
                            <el-dropdown-item command="addUser">分配私人菜单</el-dropdown-item>
                            <el-dropdown-item command="password">修改密码</el-dropdown-item>
                            <el-dropdown-item command="email">发送到密保邮箱</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
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
                    :person-list="personData"
                    :account-type="accountTypeData"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getAccountManage, getAccountManageDetail, removeAccountManage, getUserRoles } from '@/api/basic-info'
import { getAllRoles } from '@/api/permissi';
import { setTimeout } from 'timers';

export default {
    components: {
        Pagination,
        ModelView
    },
    props: {
        personList: {
            type: Array,
            default: []
        },
        accountType: {
            type: Array,
            default: []
        }
    },
    watch: {
        personList () {
            this.personData = this.personList
        },
        accountType () {
            this.accountTypeData = this.accountType
        }
    },
    data () {
        return {
            loading: false,
            personData: [], // 人员数据
            accountTypeData: [], // 账号类型
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                userName: '',
                mobile: '',
                email: '',
            },  // 页码传参数
        };
    },
    filters: {
        /**
         * 数据筛选
         * @return {type} {description}
         */
        filterPersonData (value, array) {
            let typeName = ''
            if (value) {
                array.forEach(ele => {
                    if (ele.personnelId == value) {
                        typeName = ele.personnelName
                    }
                })
                return typeName
            } else {
                return '暂无'
            }
        },
        filterAccountType (value, array) {
            let typeName = ''
            if (value) {
                array.forEach(ele => {
                    if (ele.value == value) {
                        typeName = ele.label
                    }
                })
                return typeName
            } else {
                return '暂无'
            }
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
            getAccountManage(this.pageInfo).then(res => {
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
        handleModal (obj) {
            if (obj) {
                this.$refs['modelView'].handEditInfoModel(obj);
            }
        },

        /**
         * 分配角色
         * @return {type} {description}
         */
        handleLoadRoles (id) {
            if (!id) {
                return
            }
            const that = this
            const p1 = getAllRoles()
            const p2 = getUserRoles(id)
            Promise.all([p1, p2]).then(function (values) {
                let res1 = values[0]
                let res2 = values[1]
                // if (res1.code === 0) {
                //     that.selectRoles = res1.data
                // }
                if (res2.code === 0) {
                    let result = []
                    res2.data.map(item => {
                        result.push(item.roleId)
                    })
                    that.$refs['modelView'].handleLoadRolesModel(res1.data, id, result)
                }
            })
        },

        /**
         * 拉下菜单回调
         * @param  {string} name {属性名}
         * @param  {object} row  {数据对象}
         * @return {type} {description}
         */
        handleClick (name, row) {
            switch (name) {
                case 'password':
                    this.$refs['modelView'].handlePasswordModel(row)
                    break
                case 'addUser':
                    this.$message({
                        message: '分配私人菜单开发中。。。。。。',
                        type: 'success',
                        showClose: true,
                        duration: 1000 * 1.5
                    });
                    break
                case 'email':
                    this.$message({
                        message: '发送到密保邮箱。。。。。。',
                        type: 'success',
                        showClose: true,
                        duration: 1000 * 1.5
                    });
                    break
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
                removeAccountManage(id).then(res => {
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

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

