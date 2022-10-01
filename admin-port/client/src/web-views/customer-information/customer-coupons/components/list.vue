/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form :model="pageInfo" inline label-width="80px">
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

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查询</el-button
        >&nbsp;
        <el-button @click="handleResetForm()">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableData" border v-loading="loading" style="width: 100%">
      <el-table-column
        type="index"
        label="序号"
        width="50"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="groupName"
        label="客户分组"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="couponsName"
        label="优惠券名称"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="createName"
        label="发放人"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        label="发放时间"
        min-width="160"
        align="center"
      ></el-table-column>

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
        min-width="120"
        align="center"
      ></el-table-column>

        <el-table-column
        label="有效期限(天)/过期时间"
        min-width="200"
        align="center"
      >
      <template slot-scope="scope">
        <span v-if="scope.row.overdueType == 1">{{scope.row.timeOverdue}}</span>
        <span v-if="scope.row.overdueType == 2">{{scope.row.timeLimit}}</span>
        </template>
      </el-table-column>
    <el-table-column
        prop="customerGetNum"
        label="领取人数"
        min-width="120"
        align="center"
      ></el-table-column>

    <el-table-column
        prop="customerUsedNum"
        label="使用人数"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="操作" fixed="right" width="180" align="center">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            plain
            @click="handleRemove(scope.row.id)"
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
  getIssuedCouponsList,
  removeCouponsCenter,
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

      loading: false,
      tableData: [],
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数

        couponsName: "",
        couponsType: "",
        couponsAmount: "",
        overdueType: "",
        timeLimit: "",
        timeOverdue: "",

        customerGetNum: "",
        customerUsedNum: "",
        
        groupName: "",

        createName: "",
        createTime: "",
      },
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
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getIssuedCouponsList(this.pageInfo)
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
      this.pageInfo.groupName = "";
      this.pageInfo.couponsName = "";
      this.pageInfo.couponsType = "";
      this.pageInfo.createName = "";
    },

    /**
     * 删除
     * @param  {string} id {description}
     * @return {type} {description}
     */
    handleRemove(couponsCenterId) {
      this.$confirm("确定删除该发放的优惠券吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        removeCouponsCenter({ couponsCenterId }).then((res) => {
          if (res.code === 100) {
            this.pageInfo.page = 1;
            this.$message({
              message: "删除成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.handleSearch();
          }
        });
      });
    },
  },
  mounted() {
    this.handleSearch();
  },
};
</script>

