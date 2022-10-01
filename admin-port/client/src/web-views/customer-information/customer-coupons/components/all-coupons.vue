/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
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

      <el-form-item label="会员ID">
        <el-input
          type="text"
          v-model="pageInfo.loginName"
          placeholder="请输入登录名"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="优惠券名称">
        <el-input
          type="text"
          v-model="pageInfo.couponsName"
          placeholder="优惠券名称"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="优惠券类型">
        <el-select
          v-model="pageInfo.couponsType"
          placeholder="请选择优惠券类型"
          clearable
          @keyup.enter.native="handleSearch(1)"
        >
          <el-option
            v-for="(item, index) in couponsTypes"
            :key="index"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="使用状态">
        <el-select
          v-model="pageInfo.couponsStatus"
          placeholder="请选择类型"
          clearable
          width="50px"
          @keyup.enter.native="handleSearch(1)"
        >
          <el-option
            v-for="(item, index) in couponsStatus"
            :key="index"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查询</el-button
        >&nbsp;
        <el-button @click="handleResetForm()">重置</el-button>
      </el-form-item>

      <el-form-item style="float: right">
        <el-button
          :disabled="selectedData.length == 0"
          type="danger"
          style="float: right; width: 100px"
          @click="removeList()"
          >批量删除</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table
      :data="tableData"
      border
      v-loading="loading"
      style="width: 100%"
      @selection-change="selectChange"
    >
      <el-table-column
        fixed
        type="selection"
        width="50"
        align="center"
      ></el-table-column>
      <el-table-column
        fixed
        type="index"
        label="序号"
        width="50"
        align="center"
      ></el-table-column>
      <el-table-column
        fixed
        prop="couponsName"
        label="优惠券名称"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="customerName"
        label="客户名称"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="loginName"
        label="会员ID"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        label="上级用户"
        min-width="180"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.fromCustomerName">{{scope.row.fromCustomerName}}（{{scope.row.fromLoginName}}）</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="couponsType"
        label="优惠券类型"
        min-width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.couponsType == 1" type="primary"
            >新人券</el-tag
          >
          <el-tag v-if="scope.row.couponsType == 2" type="warning"
            >满减券</el-tag
          >
          <el-tag v-if="scope.row.couponsType == 3" type="success"
            >通用券</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column
        prop="couponsAmount"
        label="金额/元"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column label="使用状态" min-width="120" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.couponsStatus == 0" type="warinng"
            >未使用</el-tag
          >
          <el-tag v-if="scope.row.couponsStatus == 1" type="success"
            >已使用</el-tag
          >
          <el-tag v-if="scope.row.couponsStatus == 2" type="danger"
            >已过期</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column
        prop="createTime"
        label="领取时间"
        min-width="180"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="usedTime"
        label="使用时间"
        min-width="180"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="usedOrder"
        label="使用订单"
        min-width="140"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="giveName"
        label="发放人"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="操作" min-width="120" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            type="info"
            v-if="scope.row.couponsStatus == 1"
            disabled
            plain
            @click="remove(scope.row.id)"
            >已使用</el-button
          >
          <el-button type="danger" v-else plain @click="remove(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 页码 -->
    <pagination
      v-show="pageInfo.total > 0"
      :total="pageInfo.total"
      :current.sync="pageInfo.page"
      :limit.sync="pageInfo.limit"
      @pagination="pagination"
    />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getCustomerCouponsList,
  removeCustomerCoupons,
  removeCoupons,
} from "@/api/customer-information/customer-coupons";

export default {
  components: {
    Pagination,
  },
  data() {
    return {
      couponsTypes: [
        {
          name: "新客户优惠券",
          value: "1",
        },
        {
          name: "满减券",
          value: "2",
        },
        {
          name: "通用券",
          value: "3",
        },
      ],

      couponsStatus: [
        { name: "未使用", value: "0" },
        { name: "已使用", value: "1" },
        { name: "已过期", value: "2" },
      ],

      loading: false,
      tableData: [],
      selectedData: [],
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数

        customerName: "",
        loginName: "",
        couponsName: "",
        couponsType: "",
        couponsAmount: "",
        usedTime: "",
        usedOrder: "",
        giveName: "",
        createTime: "",
        couponsStatus: "",
      },
    };
  },
  methods: {
    selectChange(val) {
      console.log("val:", val);
      this.selectedData = val;
    },

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

    remove(id) {
      this.$confirm("是否删除这条优惠券?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          removeCoupons({ id })
            .then((res) => {
              if (res.code == 100) {
                this.$message({
                  type: "success",
                  message: "删除客户优惠券成功",
                  duration: 1000 * 1.5 * 2,
                });
                this.handleSearch(1);
              } else {
                this.$message({
                  type: "error",
                  message: res.message,
                  duration: 1000 * 1.5 * 2,
                });
              }
            })
            .finally(() => {
              setTimeout(() => {
                this.loading = false;
              }, 1000);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    removeList() {
      if (this.selectedData.length < 1) {
        this.$message({
          type: "danger",
          message: "请勾选要删除的优惠券内容",
          duration: 1000 * 1.5 * 2,
        });
      } else {
        this.$confirm("是否删除客户领取的优惠券?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            let customerCouponsIds = "";
            this.selectedData.forEach((ele) => {
              if (ele.couponsStatus != 1) {
                customerCouponsIds =
                  customerCouponsIds == ""
                    ? ele.id
                    : customerCouponsIds + "," + ele.id;
              }
            });

            removeCustomerCoupons({ customerCouponsIds })
              .then((res) => {
                if (res.code == 100) {
                  this.$message({
                    type: "success",
                    message: "批量删除客户优惠券成功",
                    duration: 1000 * 1.5 * 2,
                  });
                  this.handleSearch(1);
                } else {
                  this.$message({
                    type: "error",
                    message: res.message,
                    duration: 1000 * 1.5 * 2,
                  });
                }
              })
              .finally(() => {
                setTimeout(() => {
                  this.loading = false;
                }, 1000);
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
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
      getCustomerCouponsList(this.pageInfo)
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
      this.pageInfo.loginName = "";
    },
  },
  mounted() {
    this.handleSearch();
  },
};
</script>

