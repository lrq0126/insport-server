/**
 * 国家列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
      <el-form-item label="保险名称" prop="insuranceName">
        <el-input
          type="text"
          v-model="pageInfo.insuranceName"
          placeholder="请输入保险名称"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查 询</el-button
        >&nbsp;
        <el-button @click="handleResetForm('searchForm')">重 置</el-button>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="success" @click="handleCreateModal"
          >新增保险内容</el-button
        >
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
        label="保险价格详情"
        type="expand"
        align="center"
        width="120"
      >
        <template slot-scope="insurances">
          <el-table :data="insurances.row.insurancePrices" border>
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
            ></el-table-column>

            <el-table-column prop="price" align="center" label="保险费用">
            </el-table-column>

            <el-table-column
              prop="ginsengPrice"
              align="center"
              label="参保价格"
            >
            </el-table-column>

            <el-table-column
              prop="claimsDetail"
              align="center"
              label="理赔详情"
            >
              <template slot-scope="scope">
                <el-button type="primary" plain @click="openRemarks(scope.row.claimsDetail)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>

      <el-table-column
        prop="insuranceName"
        align="center"
        label="保险名称"
      ></el-table-column>

      <el-table-column
        prop="insuranceNo"
        align="center"
        label="保险编号"
      ></el-table-column>

      <el-table-column
        prop="createName"
        align="center"
        label="创建人"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        align="center"
        label="创建时间"
      ></el-table-column>

      <el-table-column
        prop="updateTime"
        align="center"
        label="更新时间"
      ></el-table-column>

      <el-table-column
        prop="remarks"
        align="center"
        label="备注信息"
      ></el-table-column>

      <el-table-column
        prop="claimsDetail"
        align="center"
        label="理赔详情"
      >
        <template slot-scope="scope">
          <el-button type="primary" plain @click="openRemarks(scope.row.claimsDetail)">查看详情</el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            plain
            @click="handleEditModal(scope.row.id)"
            >编辑</el-button
          >
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
    <!-- 弹窗信息 -->
    <insurance-detail-view
      ref="insuranceDetailView"
      @reSelect="handleSearch(1)"
    />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import InsuranceDetailView from "./insurance-detail";
import {
  getInsuranceList,
  deleteInsurance,
} from "@/api/channel-management/insurance";
import { setTimeout } from "timers";

export default {
  components: {
    Pagination,
    InsuranceDetailView,
  },
  data() {
    return {
      loading: false,
      tableData: [],
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数

        insuranceName: "",
      }, // 页码传参数
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
     * 获取列表信息
     * @param  {nnumber} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getInsuranceList(this.pageInfo)
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
      this.pageInfo.insuranceName = "";
    },

    /**
     * 启动新建弹窗
     * @return {type} {description}
     */
    handleCreateModal() {
      this.$refs["insuranceDetailView"].createInfoModel();
    },

    /**
     * 启动编辑弹窗
     * @return {type} {description}
     */
    handleEditModal(rowid) {
      this.$refs["insuranceDetailView"].editInfoModel(rowid);
    },

    openRemarks(remarks){
        this.$alert(remarks, '理赔详情', {
          confirmButtonText: '确定'
        });
    },

    /**
     * 删除
     * @param  {string} id {description}
     * @return {type} {description}
     */
    handleRemove(id) {
      this.$confirm("确定删除保单信息吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteInsurance(id).then((res) => {
          if (res.code === 100) {
            this.pageInfo.page = 1;
            this.$message({
              message: "删除成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5,
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

