/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="75px">
      <el-form-item label="拼团订单号">
        <el-input
          type="text"
          v-model="pageInfo.orderNumber"
          placeholder="请输入拼团订单号"
          @keyup.enter.native="queryLiist(1)"
        />
      </el-form-item>

      <el-form-item label="团长会员ID">
        <el-input
          type="text"
          v-model="pageInfo.loginName"
          placeholder="请输入团长会员ID"
          @keyup.enter.native="queryLiist(1)"
        />
      </el-form-item>

      <el-form-item label="团长名称" label-width="105px">
        <el-input
          v-model="pageInfo.leaderName"
          placeholder="请输入团长名称"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="queryLiist(1)"
        />
      </el-form-item>

      <el-form-item label="渠道名称">
        <el-input
          type="text"
          v-model="pageInfo.routeName"
          placeholder="渠道名称"
          @keyup.enter.native="queryLiist(1)"
        />
      </el-form-item>

      <el-form-item label="国家">
        <el-select
          v-model="pageInfo.countryId"
          placeholder="请选择"
          filterable
          clearable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="(item, index) in countryData"
            :key="index"
            :value="item.id"
            :label="item.sddName"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="queryLiist(1)">查 询</el-button>&nbsp;
        <el-button @click="handleResetForm('searchForm')">重 置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table :data="pinData" border v-loading="loading" style="width: 100%">
      <el-table-column
        type="index"
        label="序号"
        width="50"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="pinName"
        label="拼团名称"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="orderNumber"
        label="拼团订单号"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="loginName"
        label="团长会员id"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="leaderName"
        label="团长名称"
        min-width="200"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="routeName"
        label="渠道"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="country"
        label="国家"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="targetWeight"
        label="目标重量"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="targetUnitPrice"
        label="目标单价"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="操作" fixed="right" width="220" align="center">
        <template slot-scope="scope">
          <el-button
            type="success"
            icon="el-icon-upload"
            size="mini"
            plain
            @click="operationAudit(scope.row.id)"
            >审 核</el-button
          >
          <el-button
            type="danger"
            icon="el-icon-upload"
            size="mini"
            plain
            @click="deletePin(scope.row.id)"
            >删除订单</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 页码 -->
    <pagination
      v-show="pageInfo.total > 0"
      :total="pageInfo.total"
      :current.sync="pageInfo.pageNumber"
      :pageSize.sync="pageInfo.pageSize"
      @pagination="pagination"
    />
    <!-- 弹窗信息 -->
    <!-- <pin-detail-view ref="modelView"
                    @updateList="queryLiist" /> -->
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
// import PinDetailView from './pin-detail';
import {
  getPinList,
  getCountryList,
  deletePin,
} from "@/api/pin-management/pin-main";

export default {
  components: {
    Pagination,
    // PinDetailView
  },
  data() {
    return {
      loading: false,
      countryData: [],
      pinData: [],
      pageInfo: {
        total: 0,
        pageNumber: 1, // 当前页码
        pageSize: 10, // 每页条数
        orderNumber: "",
        loginName: "",
        leaderName: "",
        routeName: "",
        countryId: "",
        pingType: 0,
      }, // 页码传参数
    };
  },
  methods: {
    /**
     * 下拉选择事件
     * @return {type} {description}
     */
    handleSelect() {
      this.queryLiist(1);
    },

    /**
     * 分页
     * @param {object}
     * @return {type} {description}
     */
    pagination(pageObj) {
      if (pageObj.bool) {
        this.pageInfo.pageNumber = pageObj.page;
        this.queryLiist();
      } else {
        this.pageInfo.pageSize = pageObj.limit;
        this.queryLiist(1);
      }
    },

    /**
     * 获取列表
     * @param  {number} pageNumber {初始化页码}
     * @return {type} {description}
     */
    queryLiist(pageNumber) {
      if (pageNumber) {
        this.pageInfo.pageNumber = pageNumber;
      }
      this.loading = true;
      getPinList(this.pageInfo)
        .then((res) => {
          if (res.content) {
            this.pinData = res.content;
          } else {
            this.pinData = [];
          }
          this.pageInfo.total = parseInt(res.data.total);
          this.pageInfo.pageNumber = parseInt(res.data.number);
          this.pageInfo.pageSize = parseInt(res.data.pageSize);
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
      this.pageInfo.orderNumber = "";
      this.pageInfo.loginName = "";
      this.pageInfo.leaderName = "";
      this.pageInfo.routeName = "";
      this.pageInfo.countryId = "";
      this.handleSelect();
    },

    /**
     * 审核开始
     * @return {type} {description}
     */
    operationAudit(pinId) {
      this.$router.push({ name: "PinDetail", query: { id: pinId } });
    },

    /**
     * 删除订单
     * @return {type} {description}
     */
    deletePin(pinId) {
      this.$confirm("确定要删除该订单吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deletePin({ pinId }).then((res) => {
          if (res.code == 100) {
            this.$message({
              type: "success",
              message: "拼团订单删除成功",
              duration: 1000 * 1.5 * 2,
            });
            this.queryLiist(1);
            setTimeout(() => {
              this.loading = false;
            }, 1000);
          }
        });
      });
    },

    // 修改table tr行的背景色
    tableRowStyle({ row, rowIndex }) {
      //   var myDate = new Date();
      //   myDate.setDate(myDate.getDate() - 2);
      //   if (Date.parse(myDate) > Date.parse(row.packTime)) {
      //     return "background-color: #99ccff";
      //   }
    },
    /**
     * 时间控件（change事件）
     * @return {type} {description}
     */
    handlePicker(data) {
      //   if (data) {
      //     this.pageInfo.startTime = data[0];
      //     this.pageInfo.endTime = data[1];
      //   } else {
      //     this.pageInfo.startTime = "";
      //     this.pageInfo.endTime = "";
      //   }
    },

    getCountry() {
      getCountryList()
        .then((res) => {
          this.countryData = res.content.country;
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },
  },
  mounted() {
    this.queryLiist(1);
    this.getCountry();
  },
};
</script>

<style lang="scss">
.el-date-editor .el-range-separator {
  padding: 0;
}
.el-date-editor .el-range-separator {
  width: 7%;
}
</style>

