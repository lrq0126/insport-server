/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
  <div class="cargo-list-model-view">
    <!--编辑信息 -->
    <el-dialog
      :visible.sync="dialogListInfo"
      :closeOnClickModal="false"
      title="编辑信息"
      class="dialog-box"
      width="80%"
    >
      <!-- 表格 -->
      <el-table
        :data="tableData"
        border
        v-loading="loading"
        style="width: 100%"
      >
        <el-table-column
          type="index"
          fixed="left"
          label="序号"
          width="50"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="customerName"
          label="客户"
          min-width="160"
          align="center"
        >
          <!-- <template slot-scope="scope">
            <span>{{
              scope.row.customerNo | filterCustomer(customerData)
            }}</span>
          </template> -->
        </el-table-column>

        <el-table-column
          prop="loginName"
          label="会员id"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="goodsName"
          label="货物名称"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column label="仓库地址" min-width="160" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.location | filterLocation }}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="deliveryName"
          label="快递公司"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="deliveryOrderNo"
          label="快递单号/唛头"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="packageType"
          label="包装类型"
          min-width="100"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="packageNum"
          label="货物件数"
          min-width="100"
          align="center"
        ></el-table-column>

        <!-- <el-table-column prop="vol"
                             label="体积（m³）"
                             min-width="100"
                             align="center"></el-table-column> -->

        <el-table-column
          prop="length"
          label="长度（CM）"
          min-width="100"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="width"
          label="宽度（CM）"
          min-width="100"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="height"
          label="高度（CM）"
          min-width="100"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="kg"
          label="重量（KG）"
          min-width="100"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="message"
          label="备注信息"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="operatorName"
          label="操作人名字"
          min-width="120"
          align="center"
        ></el-table-column>

        <!-- <el-table-column prop="inStorageTime"
                                 label="入库日期"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="outStorageTime"
                                 label="出库日期"
                                 min-width="160"
                                 align="center"></el-table-column> -->

        <el-table-column
          fixed="right"
          label="状态"
          min-width="120"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.goodsType == 1">入库</el-tag>
            <el-tag v-if="scope.row.goodsType == 2" type="success">出库</el-tag>
            <el-tag v-if="scope.row.goodsType == 3" type="warning"
              >预录入</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column label="操作" fixed="right" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              plain
              @click="handleSubmit(scope.row)"
              :disabled="
                scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                  ? false
                  : true
              "
              >确 定</el-button
            >
            <!-- <el-button type="danger"
                                   size="mini"
                                   plain
                                   @click="handleRemove(scope.row.goodsNo)"
                                   :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button v-waves type="info" plain @click="dialogListInfo = false"
          >关 闭</el-button
        >
        <!-- <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button> -->
      </span>
    </el-dialog>

    <!--客户列表信息 -->
    <el-dialog
      :visible.sync="dialogCustomerInfo"
      :closeOnClickModal="false"
      title="客户信息"
      class="dialog-box"
      width="50%"
    >
      <!-- 搜索 -->
      <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
        <el-form-item label="客户名称：">
          <el-input
            type="text"
            v-model="pageInfo.customerName"
            placeholder="请输入客户名称"
            @keyup.enter.native="handCustomerInfoModel(1)"
          />
        </el-form-item>

        <el-form-item label="会员id：">
          <el-input
            type="text"
            v-model="pageInfo.loginName"
            placeholder="请输入会员id"
            @keyup.enter.native="handCustomerInfoModel(1)"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handCustomerInfoModel(1)"
            >查询</el-button
          >&nbsp;
          <el-button @click="handleResetForm('searchForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <el-table
        :data="customerTableData"
        border
        v-loading="loading"
        style="width: 100%"
      >
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
          prop="createTime"
          label="注册时间"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="loginName"
          label="登录名"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column label="操作" fixed="right" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              plain
              @click="handleSubmitCustomer(scope.row)"
              :disabled="
                scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                  ? false
                  : true
              "
              >确 定</el-button
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
      <span slot="footer" class="dialog-footer">
        <el-button v-waves type="info" plain @click="dialogCustomerInfo = false"
          >关 闭</el-button
        >

      </span>
    </el-dialog>
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import Pagination from "@/components/Pagination";
import { updateCargoList } from "@/api/customer-management/cargo-list";
import { getClientList } from "@/api/customer-information/client-list";

import { getSystemCustomer } from "@/api/customer-management/warehouse-sorting";

import Cookies from "js-cookie";
import qs from "qs";

export default {
  name: "ModelView",
  components: {
    Pagination,
  },
  directives: {
    waves,
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
      this.customerData = this.selectData.customer;
      this.deliveryCompanyData = this.selectData.deliveryCompany;
      this.storageData = this.selectData.storage;
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
      systemName: "",
      loading: false,
      tableData: [],
      customerTableData: [],
      dialogListInfo: false,
      dialogCustomerInfo: false,
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
      }, // 页码传参数
    };
  },
  methods: {
    /**
     * 控制弹窗
     * @return {type} {description}
     */
    handListInfoModel(data) {
      this.tableData = data;

      this.$nextTick(() => {
        this.dialogListInfo = true;
      });
    },

    /**
     * 分页
     * @param {object}
     * @return {type} {description}
     */
    pagination(pageObj) {
      if (pageObj.bool) {
        this.pageInfo.page = pageObj.page;
        this.handCustomerInfoModel();
      } else {
        this.pageInfo.limit = pageObj.limit;
        this.handCustomerInfoModel(1);
      }
    },

    handCustomerInfoModel(page) {
      
      if(this.systemName){
        let baseUrl = 'http://jiyun.flycloudstorage.com';
        switch(this.systemName){
            case "FLY": baseUrl = 'http://jiyun.flycloudstorage.com:8620';
            break;
            case "HUAWEI": baseUrl = 'http://jiyun.huawei138.cn:8630';
            break;
            case "TAOMAO": baseUrl = 'http://gzdw.gdjiyun.com:9120';
            break;
            case "HUASU": baseUrl = 'http://huasu.huawei138.cn:9610';
            break;
            case "HUAYOU": baseUrl = 'http://huayou.flycloudstorage.com:9630'; 
            break;
        }
        let data = {
            pageNumber: page,
            pageSize: this.pageInfo.limit,
            customerName: this.pageInfo.customerName,
            loginName: this.pageInfo.loginName,
        }
        this.loading = true;
        let that = this;
        axios({
          method: "post",
          url: baseUrl+'/api/customer/find/all',
          data: qs.stringify(data)
        }).then(function (res) {
            console.log("res:",res.data);
            that.customerTableData =  res.data.content
            
            that.pageInfo.total = parseInt(res.data.data.total);
            that.pageInfo.page = parseInt(res.data.data.number);
            that.pageInfo.limit = parseInt(res.data.data.pageSize);
        }).finally(() => {
          setTimeout(() => {
            this.customerTableData = that.customerTableData;
            this.pageInfo.total = that.pageInfo.total;
            this.pageInfo.page = that.pageInfo.page;
            this.pageInfo.limit = that.pageInfo.limit;
            this.loading = false;
          }, 800);
        })
        
      }else{
        if (page) {
          this.pageInfo.page = page;
        }
        this.loading = true;
        getClientList(this.pageInfo).then((res) => {
          if (res.code === 100) {
            this.customerTableData = res.content;
            this.pageInfo.total = parseInt(res.data.total);
            this.pageInfo.page = parseInt(res.data.number);
            this.pageInfo.limit = parseInt(res.data.pageSize);
          }
          this.$nextTick(() => {
            this.dialogCustomerInfo = true;
          });
        })
        .finally(() => {
          this.loading = false;
        });
      }
    },

    handCustomerInfoModel1(data) {
      this.dialogCustomerInfo = true;
      if(data){
        // 重新加载所有会员信息
        this.pageInfo.customerName = data.customerName;
        this.pageInfo.loginName = data.loginName;
        this.systemName = data.systemName;
      }
      
      this.handCustomerInfoModel(1);
    },

    handleSubmitCustomer(data) {
      this.$emit("listTap", data);
      this.dialogCustomerInfo = false;
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm(form) {
      this.pageInfo.customerName = "";
      this.pageInfo.englishName = "";
      this.pageInfo.phoneNumber = "";
      this.pageInfo.callNumber = "";
      this.pageInfo.fullAddress = "";
      this.pageInfo.loginName = "";
      this.$refs[form].resetFields();
    },

    /**
     * 保存
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit(row) {
      this.$emit("updateForm", row);
      this.dialogListInfo = false;
    },
  },
};
</script>

<style lang="scss">
.cargo-list-model-view {
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