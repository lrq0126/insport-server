<template>
    <div class="customer-model-view">

        <el-dialog :visible.sync="dialogCustomer"
                   :closeOnClickModal="false"
                   title="客户信息"
                   class="dialog-box"
                   width="80%">

            <!-- 搜索 -->
            <el-form ref="searchForm"
                     :model="pageInfo"
                     inline
                     label-width="70px">

                <el-form-item label="客户名称">
                    <el-input v-model="pageInfo.customerName"
                              placeholder="请输入客户名称"
                              style="width: 200px;"
                              @keyup.enter.native="handleSearch(1)"
                              class="filter-item" />
                </el-form-item>

                <el-form-item label="会员ID">
                    <el-input v-model="pageInfo.loginName"
                              placeholder="请输入会员ID"
                              style="width: 200px;"
                              @keyup.enter.native="handleSearch(1)"
                              class="filter-item" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"
                               @click="handleSearch(1)">查 询</el-button>
                </el-form-item>
            </el-form>

            <!--客户列表信息 -->
            <el-table :data="customerData"
                      border
                      v-loading="loading"
                      style="width: 100%">
                <el-table-column type="index"
                                 label="序号"
                                 width="50"
                                 align="center"></el-table-column>

                <el-table-column prop="customerName"
                                 label="客户名称"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="country"
                                 label="国家"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="englishName"
                                 label="英文名称"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="loginName"
                                 label="登录名"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="createTime"
                                 label="注册时间"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="age"
                                 label="性别"
                                 min-width="100"
                                 align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.gender == 1">男</span>
                        <span v-else>女</span>
                    </template>
                </el-table-column>

                <el-table-column prop="phoneNumber"
                                 label="手机号码"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="callNumber"
                                 label="电话号码"
                                 min-width="160"
                                 align="center"></el-table-column>

                <!-- 
            <el-table-column prop="zipCode"
                             label="邮政编码"
                             min-width="100"
                             align="center"></el-table-column> -->

                <el-table-column prop="email"
                                 label="邮箱"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="message"
                                 label="备注信息"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column label="操作"
                                 fixed="right"
                                 width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button type="primary"
                                   size="mini"
                                   plain
                                   @click="handleSubmitCustomer(scope.row)"
                                   :disabled="
                scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                  ? false
                  : true
              ">确 定</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 页码 -->
            <pagination v-show="pageInfo.total > 0"
                        :total="pageInfo.total"
                        :current.sync="pageInfo.page"
                        :limit.sync="pageInfo.limit"
                        @pagination="pagination" />
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogCustomer = false">关 闭</el-button>

            </span>
        </el-dialog>
    </div>
</template>


<script>
import waves from "@/directive/waves"; // 按钮水波纹
import Pagination from "@/components/Pagination";
import { getClientList } from '@/api/customer-information/client-list'

export default {
    name: "CustomerModelView",
    components: {
        Pagination,
    },
    directives: {
        waves
    },
    data () {
        return {
            customerData: [],
            loading: false,
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                customerName: "",
                englishName: "",
                loginName: "",
                phoneNumber: "",
                callNumber: ""
            },


            dialogCustomer: false, // 弹窗的隐藏状态
        };
    },



    methods: {

        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page;
            }
            this.loading = true;
            getClientList(this.pageInfo)
                .then((res) => {
                    if (res.code === 100) {
                        this.customerData = res.content;
                        this.pageInfo.total = parseInt(res.data.total);
                        this.pageInfo.page = parseInt(res.data.number);
                        this.pageInfo.limit = parseInt(res.data.pageSize);
                    }
                    this.$nextTick(() => {
                        this.dialogCustomer = true;
                    });
                }).finally(() => {
                    this.loading = false;
                });
        },

        /**
         * 打开弹窗求情
         */
        handCustomer () {
            this.pageInfo.page = 0;
            this.pageInfo.pageSize = 10;
            this.pageInfo.customerName = ''
            this.pageInfo.loginName = ''
            
            this.loading = true;
            getClientList(this.pageInfo).then(res => {
                this.customerData = res.content
                this.pageInfo.total = parseInt(res.data.total);
                this.pageInfo.page = parseInt(res.data.number);
                this.pageInfo.limit = parseInt(res.data.pageSize);
                this.$nextTick(() => {
                    this.dialogCustomer = true;
                })
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },
        

        handleSubmitCustomer (data) {
            this.$emit("customer", data);
            this.dialogCustomer = false;
        },

        /**
         * 分页
         * @param {object}
         * @return {type} {description}
         */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page;
                this.handleSearch();
            } else {
                this.pageInfo.limit = pageObj.limit;
                this.handleSearch(1);
            }
        },

    }

}
</script>

<style lang="scss">
.customer-model-view {
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
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
    .dialog-box {
        .el-dialog {
            margin-top: 5vh !important;
            margin-bottom: 30px;
        }
    }
    .el-form-item__label {
        padding: 0 6px 0 0;
    }
}
</style>