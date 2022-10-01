/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <el-dialog
      title="客户信息"
      :visible.sync="cusDialog"
      :close-on-click-modal=false
      width="80%"
      append-to-body
    >
      <!-- 搜索 -->
      <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
        <el-form-item label="客户名称">
          <el-input
            type="text"
            v-model="pageInfo.customerName"
            placeholder="请输入客户名称"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>

        <!-- <el-form-item label="英文名称">
          <el-input
            type="text"
            v-model="pageInfo.englishName"
            placeholder="请输入英文名称"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item> -->

        <el-form-item label="登录名">
          <el-input
            type="text"
            v-model="pageInfo.loginName"
            placeholder="请输入登录名"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>

        <el-form-item label="手机号码">
          <el-input
            type="text"
            v-model="pageInfo.phoneNumber"
            placeholder="请输入手机号码"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch(1)">查询</el-button
          >&nbsp;
          <el-button @click="handleResetForm()">重置</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button
            type="success"
            @click="submit"
            :disabled="this.multipleSelection.length == 0"
            >确认添加成员</el-button
          >
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        border
        v-loading="loading"
        style="width: 100%"
        @selection-change="customerHandleSelectionChange"
      >
        <el-table-column type="selection" width="40"> </el-table-column>
        <el-table-column
          type="index"
          label="序号"
          width="50"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="customerName"
          label="客户名称"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="country"
          label="国家"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="englishName"
          label="英文名称"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="loginName"
          label="登录名"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="createTime"
          label="注册时间"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column prop="age" label="性别" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.gender == 1">男</span>
            <span v-else>女</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="phoneNumber"
          label="手机号码"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="callNumber"
          label="电话号码"
          min-width="160"
          align="center"
        ></el-table-column>

        <!-- 
            <el-table-column prop="zipCode"
                             label="邮政编码"
                             min-width="100"
                             align="center"></el-table-column> -->

        <el-table-column
          prop="email"
          label="邮箱"
          min-width="160"
          align="center"
        ></el-table-column>
        <!-- 
            <el-table-column prop="fullAddress"
                             label="收货地址"
                             min-width="160"
                             align="center"></el-table-column> -->

        <el-table-column
          prop="message"
          label="备注信息"
          min-width="160"
          align="center"
        ></el-table-column>
      </el-table>

      <!-- 页码 -->
      <pagination
        v-show="pageInfo.total > 0"
        :total="pageInfo.total"
        :current.sync="pageInfo.page"
        :limit.sync="pageInfo.limit"
        @pagination="pagination"
      />

      <el-button @click="cusDialog = false" type="info">关 闭</el-button>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getNotInGroupCustomerList } from "@/api/customer-information/client-list";
import { addGroupCustomer } from "@/api/customer-information/customer-group";
import Cookies from "js-cookie";

export default {
  components: {
    Pagination,
  },
  data() {
    return {
      cusDialog: false,
      loading: false,
      tableData: [],

      multipleSelection: [], // 勾选框内容

      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数
        customerName: "",
        englishName: "",
        loginName: "",
        phoneNumber: "",
        callNumber: "",
        fullAddress: "",
        groupId: "",
      }, // 页码传参数

      // 分组添加用户的参数
      customerIds: "",
    };
  },
  methods: {
    /**
     * 分页
     * @param {object}
     * @return {type} {description}
     */
    pagination(pageObj) {
      if (pageObj.bool) {
        this.pageInfo.page = pageObj.page;
        this.handleSearch();
      } else {
        this.pageInfo.limit = pageObj.limit;
        this.handleSearch(1);
      }
    },

    /**
     * 多选框事件
     * @param  {type} val {description}
     * @return {type} {description}
     */
    customerHandleSelectionChange(val) {
      this.multipleSelection = val;
    },

    submit() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          type: "warning",
          message: "请选择客户",
          showClose: true,
          duration: 1000 * 1.5 * 3,
        });
      } else {
        this.customerIds = "";
        
        this.multipleSelection.forEach((ele) => {
          this.customerIds =
            this.customerIds == ""
              ? ele.id
              : this.customerIds + "," + ele.id;
        });

        let data = {
          groupId: this.pageInfo.groupId,
          customerIds: this.customerIds,
        };

        addGroupCustomer(data).then((res) => {
            if(res.code == 100){
                this.$message({
                  type: "success",
                  message: "分组添加客户成功",
                  showClose: true,
                  duration: 1000 * 1.5 * 3,
                });

                this.cusDialog = false;

                this.$emit('groupId', this.pageInfo.groupId)
            }
        });
      }
    },

    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getNotInGroupCustomerList(this.pageInfo)
        .then((res) => {
          this.tableData = res.content;
          this.pageInfo.total = parseInt(res.data.total);
          this.pageInfo.page = parseInt(res.data.number);
          this.pageInfo.limit = parseInt(res.data.pageSize);
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm() {
      this.pageInfo.customerName = "";
      this.pageInfo.englishName = "";
      this.pageInfo.phoneNumber = "";
      this.pageInfo.callNumber = "";
      this.pageInfo.fullAddress = "";
      this.pageInfo.loginName = "";
    },

    handModel(groupId) {
      this.handleResetForm();
      this.pageInfo.groupId = groupId;
      this.handleSearch();
      this.cusDialog = true;
    },
  },
};
</script>

