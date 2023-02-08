/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="70px">
      <el-form-item label="货物名称">
        <el-input
          v-model="pageInfo.goodsName"
          placeholder="请输入货物名称"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="快递单号/唛头" label-width="105px">
        <el-input
          v-model="pageInfo.deliveryOrderNo"
          placeholder="请输入您的快递单号"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="会员id" label-width="105px">
        <el-input
          v-model="pageInfo.loginName"
          placeholder="会员id"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="客户名称">
        <el-input v-model="pageInfo.customerName"/>
        <!-- <el-select
          v-model="pageInfo.customerNo"
          placeholder="请输入客户名称"
          filterable
          clearable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="(item, index) in customerData"
            :key="index"
            :value="item.customerNo"
            :label="item.customerName"
          />
        </el-select> -->
      </el-form-item>
      <el-form-item label="国家/地区">
        <el-select
          v-model="pageInfo.commercialAreaId"
          placeholder="--请选择--"
          clearable
          filterable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="item in commercialAreaData"
            :key="item.id"
            :value="item.id"
            :label="item.commercialAreaName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="快递公司">
        <el-select
          v-model="pageInfo.deliveryNo"
          placeholder="--请选择--"
          clearable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="(item, index) in deliveryCompanyData"
            :key="index"
            :value="item.deliveryNo"
            :label="item.deliveryName"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查 询</el-button
        >&nbsp;
        <el-button @click="handleResetForm('searchForm')">重 置</el-button>
        <el-button type="warning" @click="handleBatchPreOrder('searchForm')"
          >批量预报<i class="el-icon-upload el-icon--right"></i
        ></el-button>
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
        prop="deliveryOrderNo"
        label="快递单号/唛头"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column label="客户" prop="customerName" min-width="160" align="center">
<!--           
        <template slot-scope="scope">
          <span>{{ scope.row.customerNo | filterCustomer(customerData) }}</span>
        </template> -->
      </el-table-column>

      <el-table-column
        prop="loginName"
        label="会员id"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="commercialAreaName"
        label="国家/地区"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="goodsName"
        label="货物名称"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        label="下单时间"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="message"
        label="备注信息"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="deliveryName"
        label="快递公司"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="packageNum"
        label="货物件数"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="operatorName"
        label="操作人名字"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        fixed="right"
        label="状态"
        min-width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.inStorageTime"
            active-color="#13ce66"
            inactive-color="#ddd"
            active-text="预录入"
            :disabled="!filterPermissions('入库')"
            @change="handleOutbound(scope.row.goodsNo, scope.$index)"
            inactive-text=""
          >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" fixed="right" width="160" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="filterPermissions('修改货物信息')"
            type="info"
            size="mini"
            plain
            @click="handleModal(scope.row)"
            :disabled="
              scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                ? false
                : true
            "
            >编辑</el-button
          >
          <el-button
            v-if="filterPermissions('删除货物信息')"
            type="danger"
            size="mini"
            plain
            @click="handleRemove(scope.row.goodsNo)"
            :disabled="
              scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                ? false
                : true
            "
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
    <model-view
      ref="modelView"
      :select-data="selectData"
      @updateList="handleSearch"
    />
    <!-- 弹窗信息 -->
    <update-model
      ref="UpdateModel"
      :select-data="selectData"
      @updateList="handleSearch"
    />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from "./model-preOrder";
import UpdateModel from "./model";
import {
  getInAdvanceEntryCargoList,
  removeCargoList,
  inAdvanceCargoList,
  getSelectCargoList
} from "@/api/customer-management/cargo-list";
import Cookies from "js-cookie";

export default {
  components: {
    Pagination,
    ModelView,
    UpdateModel,
  },
  props: {
    selectData: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  watch: {
    selectData() {
      // this.customerData = this.selectData.customer
      this.deliveryCompanyData = this.selectData.deliveryCompany
      // this.storageData = this.selectData.storage
      
      this.commercialAreaData = this.selectData.commercialAreaData;
    },
  },
  filters: {
    filterCustomer(value, array) {
      let name = "";
      if (value) {
        array.forEach((ele) => {
          if (ele.customerNo == value) {
            name = ele.customerName;
          }
        });
        return name;
      } else {
        return name;
      }
    },
    filterLocation(value) {
      if (value) {
        let location = value.split(",");
        if (location[0]) {
          return location[0] + "区" + location[1] + "排";
        } else {
          return "";
        }
      } else {
        return "";
      }
    },
  },
  data() {
    return {
      bool: true,
      customerData: [], // 客户信息
      deliveryCompanyData: [], // 快递公司
      commercialAreaData: [],
      storageData: [], // 区排
      loading: false,
      tableData: [],
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 30, // 每页条数
        goodsName: "",
        customerNo: "",
        deliveryNo: "",
        deliveryOrderNo: "",
        goodsType: 3,
        loginName: "",
        customerName: ""
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
     * 批量成本结算
     * @return {type} {description}
     */
    handleBatchPreOrder() {
      this.$refs["modelView"].handBatchPreOrderModel();
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

      getInAdvanceEntryCargoList(this.pageInfo)
        .then((res) => {
          this.tableData = res.content;
          this.pageInfo.total = parseInt(res.map.page.total);
          this.pageInfo.page = parseInt(res.map.page.number);
          this.pageInfo.limit = parseInt(res.map.page.pageSize);
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    getSelected(){
        // 下拉条件查询
        getSelectCargoList().then((res) => {
            // this.customerData = res.content.customer;
            this.deliveryCompanyData = res.content.deliveryCompany;
        });
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm(form) {
      this.pageInfo.goodsName = "";
      this.pageInfo.customerNo = "";
      this.pageInfo.deliveryNo = "";
      this.pageInfo.deliveryOrderNo = "";
      this.pageInfo.goodsType = 3;
      this.pageInfo.loginName = "";
      this.$refs[form].resetFields();
    },

    /**
     * 预录入
     * @return {type} {description}
     */
    handleOutbound(id, index) {
      let obj = {
        goodsNos: id,
        operatorName: Cookies.get("userName"),
      };
      if (this.bool) {
        this.bool = false;
        this.$confirm("货物是否需要入仓？确定后将不能修改!", "货物入仓提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            inAdvanceCargoList(obj)
              .then((res) => {
                if (res.code === 100) {
                  this.pageInfo.page = 1;
                  this.$message({
                    message: "入仓成功",
                    type: "success",
                    showClose: true,
                    duration: 1000 * 1.5 * 2,
                  });
                  this.handleSearch();
                }
                this.bool = true;
              })
              .finally(() => {
                this.tableData[index].outStorageTime = false;
                this.bool = true;
              });
          })
          .catch(() => {
            this.tableData[index].inStorageTime = false;
            this.bool = true;
          });
      }
    },

    /**
     * 启动编辑弹窗
     * @return {type} {description}
     */
    handleModal(row) {
      this.$refs["UpdateModel"].handEditInfoModel(row, "inAdvance");
      // if (id) {
      //     this.saving = true
      //     getPostManageDetail(id).then(res => {
      //         if (res.code === 0) {
      //         }
      //     }).finally(() => {
      //         this.saving = false
      //     })
      // }
    },

    /**
     * 删除
     * @param  {string} id {description}
     * @return {type} {description}
     */
    handleRemove(id) {
      let obj = {
        goodsNo: id,
        usercode: Cookies.get("usercode"),
      };
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        removeCargoList(obj).then((res) => {
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
    // this.handleSearch()
  },
};
</script>

