/**
 * 客户列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="75px">
      <el-form-item label="会员id">
        <el-input
          type="text"
          v-model="pageInfo.loginName"
          placeholder="请输入会员id"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="客户名称">
        <el-input
          type="text"
          v-model="pageInfo.customerName"
          placeholder="请输入您的客户名称"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="充值时间" label-width="45px;">
        <el-date-picker
          v-model="pageInfo.dataTime"
          type="daterange"
          align="right"
          unlink-panels
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handlePicker"
          :picker-options="pickerOptions"
          style="width: 380px"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="充值渠道">
        <el-select v-model="pageInfo.channel" placeholder="请选择">
          <el-option label="微信" value="1"> </el-option>
          <el-option label="支付宝" value="2"> </el-option>
          <el-option label="淘宝支付" value="5"> </el-option>
          <el-option label="天猫" value="10"> </el-option>

          <el-option label="银行卡转账" value="3"> </el-option>
          <el-option label="现金" value="4"> </el-option>
          <el-option label="优惠券" value="6"> </el-option>
          <el-option label="补运费" value="7"> </el-option>
          <el-option label="其他" value="8"> </el-option>
        </el-select>
      </el-form-item>

      <!-- <el-form-item label="余额小于">
                <el-input type="text"
                          v-model="pageInfo.ymtEnd"
                          placeholder="请输入您的余额"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item> -->

      <el-form-item label="总余额" prop="ymt">
        <span style="font-size: 22px; color: red; font-weight: 700"
          >￥{{ amount }}</span
        >
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查 询</el-button
        >&nbsp;
        <el-button @click="handleResetForm('searchForm')">重 置</el-button>
        <el-button v-if="exporting" type="warning" :disabled="true"
          >正在导出...</el-button
        >
        <el-tooltip
          effect="dark"
          content="充值时间默认最近一个月，如有需要可自行选择下单时间"
          placement="right-end"
        >
          <el-button v-if="!exporting" type="warning" @click="exportRecharge()"
            >导 出 记 录</el-button
          >
        </el-tooltip>
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
        prop="loginName"
        label="会员id"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="customerName"
        label="客户名称"
        min-width="160"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="customerRemarks"
        label="客户备注"
        min-width="160"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="createName"
        label="经办人"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="channel"
        label="充值渠道"
        min-width="160"
        align="center"
        :formatter="channelFormat"
      ></el-table-column>

      <el-table-column
        prop="amount"
        label="充值费用（￥）"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        label="充值时间"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="message"
        label="备注信息"
        min-width="160"
        align="center"
      ></el-table-column>

      <!-- <el-table-column prop="ymt"
                             label="余额（￥）"
                             min-width="120"
                             align="center"></el-table-column> -->

      <el-table-column label="操作" fixed="right" width="220" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="filterPermissions('修改充值金额')"
            type="primary"
            size="mini"
            plain
            @click="handleTopUp(scope.row.id)"
            :disabled="
              scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                ? false
                : true
            "
            >修 改</el-button
          >
          <el-button
            v-if="filterPermissions('修改充值金额')"
            type="info"
            size="mini"
            plain
            @click="handleRevocation(scope.row.id, scope.row.loginName)"
            :disabled="
              scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                ? false
                : true
            "
            >撤 销</el-button
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
    <!-- 弹窗信息 -->
    <model-view ref="modelView" @updateList="handleSearch" />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from "./model";
import {
  customerRechargeList,
  revocationOrder,
  rollbackRacharge,
} from "@/api/financial-management/top-up-records";
import { setTimeout } from "timers";
import config from "@/config";
import Cookies from 'js-cookie'
export default {
  components: {
    Pagination,
    ModelView,
  },
  data() {
    return {
      amount: 0, // 总余额
      loading: false,
      exporting: false,
      tableData: [],
      pageForm: {
        id: "",
      },
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数
        customerName: "",
        ymtStart: "",
        ymtEnd: "",
        dataTime: "",
        startTime: "",
        endTime: "",
        channel: "",
        costType: 1,
        loginName: "",
      }, // 页码传参数
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
    };
  },
  methods: {
    channelFormat(row, column) {
      if (row.channel === "1") {
        return "微信支付";
      } else if (row.channel === "2") {
        return "支付宝支付";
      } else if (row.channel === "3") {
        return "银行卡转账";
      } else if (row.channel === "4") {
        return "现金支付";
      } else if (row.channel === "5") {
        return "淘宝支付";
      } else if (row.channel === "6") {
        return "优惠券";
      } else if (row.channel === "7") {
        return "淘宝支付";
      } else if (row.channel === "10") {
        return "天猫支付";
      } else {
        return "其他";
      }
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
      customerRechargeList(this.pageInfo)
        .then((res) => {
          if (res.content) {
            this.tableData = res.content;
          }
          this.pageInfo.total = parseInt(res.map.page.total);
          this.pageInfo.page = parseInt(res.map.page.number);
          this.pageInfo.limit = parseInt(res.map.page.pageSize);
          this.amount = res.map.ymt;
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 时间控件（change事件）
     * @return {type} {description}
     */
    handlePicker(data) {
      if (data) {
        this.pageInfo.startTime = data[0];
        this.pageInfo.endTime = data[1];
      } else {
        this.pageInfo.startTime = "";
        this.pageInfo.endTime = "";
      }
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm(form) {
      this.pageInfo.customerName = "";
      this.pageInfo.ymtStart = "";
      this.pageInfo.ymtEnd = "";
      this.pageInfo.startTime = "";
      this.pageInfo.endTime = "";
      this.$refs[form].resetFields();
      this.pageInfo.channel = "";
      this.pageInfo.dataTime = "";
      this.pageInfo.loginName = "";
      // this.$refs[form].resetFields()
    },

    /**
     * 修 改
     * @param  {type} /** {description}
     * @return {type} {description}
     */
    handleTopUp(id) {
      this.$router.push({ name: "TopUpEdit", query: { id: id } });
    },

    /**
     * 撤单
     * @return {type} {description}
     */
    handleRollbackRacharge(id, loginName) {
      this.$confirm("撤除该充值订单后无法恢复，确定需要撤单吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let obj = {
          id: id,
          loginName: loginName,
        };
        rollbackRacharge(obj).then((res) => {
          if (res.code === 100) {
            this.pageInfo.page = 1;
            this.$message({
              message: "撤除该充值订单成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.handleSearch();
          } else {
            this.$message({
              message: "服务器异常，撤单失败",
              type: "error",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
          }
        });
      });
    },

    /**
     * 撤 销
     * @param  {string} id {description}
     * @return {type} {description}
     */
    handleRevocation(id, loginName) {
      this.$confirm("确定撤销吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let obj = {
          id: id,
          loginName: loginName,
        };
        revocationOrder(obj).then((res) => {
          if (res.code === 100) {
            this.$message({
              message: "撤销成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.handleSearch();
          }
        });
      });
    },

    // 导出金额记录
    exportRecharge() {
      let usercode = Cookies.get("usercode");
      let data = {
        usercode: usercode,
        costType: this.pageInfo.costType,
        startTime: this.pageInfo.startTime,
        endTime: this.pageInfo.endTime,
        channel: this.pageInfo.channel,
        loginName: this.pageInfo.loginName,
        customerName: this.pageInfo.customerName,
      };
      let _that = this;
      _that.exporting = true;
      const baseUrl =
        process.env.NODE_ENV === "development"
          ? config.baseUrl.dev
          : config.baseUrl.pro;
      axios
        .post(
          baseUrl + "/api/account/customerAccount/exportCustomerRecharge",
          data,
          { responseType: "arraybuffer" } // 必须添加项，告诉服务器需要的响应格式
        )
        .then(function (res) {
          _that.exporting = false;

          let blob = new Blob([res.data], {
            type: "application/vnd.ms-excel;charset=utf-8",
          }); // 将二进制流转为blob
          let a = document.createElement("a");
          let url = window.URL.createObjectURL(blob); // 创建新的url并指向file对象或blob对象的地址
          a.href = url;
          a.download = "财务充值记录"; // 设置下载文件名
          a.click();
          // window.URL.revokeObjectURL(url); // 释放内存
        });

    },
  },
  mounted() {
    this.handleSearch();
  },
};
</script>

<style lang="scss">
.el-date-editor .el-range-separator {
  padding: 0;
}
</style>

