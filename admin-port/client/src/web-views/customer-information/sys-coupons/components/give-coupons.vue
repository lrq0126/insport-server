<template>
  <div class="coupons-detail-view">
    <el-dialog title="分发优惠券" :visible.sync="giveDialog" width="60%" :close-on-click-modal=false>
      <el-form :model="formItem" :rules="formItemRules" ref="formItem">
        <!-- style="float: left; width: 180px" -->
        <el-row :span="18">
          <el-col :span="8">
            <el-form-item label="优惠券：" style="width: 300px">
              <el-input
                v-model="formItem.couponsName"
                :readonly="true"
                style="width: 180px"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="描述：">
              <el-input
                v-model="formItem.couponsRemarks"
                type="textarea"
                rows="3"
                :readonly="true"
                placeholder="请输入优惠券描述内容"
                style="width: 300px"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <!-- 搜索 -->
          <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
            <!-- <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          placeholder="请输入客户名称" 
                           @keyup.enter.native="handleSearchGroup(1)"/>
            </el-form-item>
            <el-form-item label="会员ID">
                <el-input type="text"
                          v-model="pageInfo.loginName"
                          placeholder="请输入会员ID" 
                           @keyup.enter.native="handleSearchGroup(1)"/>
            </el-form-item> -->

            <el-form-item label="分组名称">
              <el-input
                type="text"
                v-model="pageInfo.groupName"
                placeholder="请输入分组名称"
                @keyup.enter.native="handleSearchGroup(1)"
              />
            </el-form-item>
            <el-form-item label="创建人">
              <el-input
                type="text"
                v-model="pageInfo.createName"
                placeholder="请输入创建人名称"
                @keyup.enter.native="handleSearchGroup(1)"
              />
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="handleSearchGroup(1)"
                >查询</el-button
              >&nbsp;
              <el-button @click="handleResetForm">重置</el-button>
            </el-form-item>
            <el-form-item style="float: right">
              <el-button :disabled="multipleSelectionGroup.length == 0" @click="batchGive" type="success">
                <span>分 发</span>
              </el-button>
        </el-form-item>
          </el-form>

          <el-table
            border
            :data="customerGroupData"
            v-loading="giveLoading"
            @selection-change="handleSelectionGroupChange"
          >
            <el-table-column type="selection" width="40"> </el-table-column>
            
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="groupName"
              label="分组名称"
              min-width="120"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="createTime"
              label="创建时间"
              min-width="120"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="createName"
              label="创建人"
              min-width="120"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="groupCustomerNum"
              label="成员人数"
              min-width="160"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="remarks"
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
        </el-form-item>
        
        <el-button @click="giveDialog = false" type="info">
          <span>关 闭</span>
        </el-button>
        
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import Pagination from "@/components/Pagination";
import { batchGiveCoupons } from "@/api/customer-information/sys-coupons";
import { getCustomerCouponsList } from "@/api/customer-information/customer-group";

export default {
  components: { waves, Pagination },

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

      couponsData: [], // 优惠券内
      customerGroupData: [], // 客户分组内容

      multipleSelectionGroup: [], // 选中的分组

      giveDialog: false,
      giveLoading: false,

      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数
        groupName: "",
        createName: "",
      }, // 页码传参数

      formItem: {
        couponsId: "",
        couponsName: "",
        couponsRemarks: "",

        groupIds: "",
      },

      formItemRules: {
        couponsName: [
          { required: true, message: "优惠券名称不能为空", trigger: "blur" },
        ],
        couponsAmount: [
          { required: true, message: "优惠券金额不能为空", trigger: "blur" },
        ],
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
        this.handleSearchGroup();
      } else {
        this.pageInfo.limit = pageObj.limit;
        this.handleSearchGroup(1);
      }
    },

    /**
     * 多选框事件
     * @param  {type} val {description}
     * @return {type} {description}
     */
    handleSelectionGroupChange(val) {
      this.multipleSelectionGroup = val;
    },

    batchGive() {
      let groupIds = "";
      this.multipleSelectionGroup.forEach((ele) => {
        groupIds = groupIds == "" ? ele.id : groupIds + "," + ele.id;
      });
      this.formItem.groupIds = groupIds;

      batchGiveCoupons(this.formItem).then((res) => {
        if (res.code == 100) {
          this.$message({
            type: "success",
            message: "分发优惠券成功",
            showClose: true,
            duration: 1000 * 1.5 * 3,
          });
          this.giveDialog = false;
        } else {
          this.$message({
            type: "error",
            message: res.message,
            showClose: true,
            duration: 1000 * 1.5 * 3,
          });
        }
      });
    },

    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearchGroup(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.giveLoading = true;
      getCustomerCouponsList(this.pageInfo)
        .then((res) => {
          this.customerGroupData = res.content;
          this.pageInfo.total = parseInt(res.data.total);
          this.pageInfo.page = parseInt(res.data.number);
          this.pageInfo.limit = parseInt(res.data.pageSize);
        })
        .finally(() => {
          setTimeout(() => {
            this.giveLoading = false;
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
      this.pageInfo.createName = "";
    },

    openGiveCoupons(data) {
      // this.resetForm();
      this.formItem.couponsId = data.id;
      this.formItem.couponsName = data.couponsName;
      this.formItem.couponsRemarks = data.remarks;

      this.handleSearchGroup(1);

      this.giveDialog = true;
    },
  },
};
</script>


<style lang="scss">
.coupons-detail-view {
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
