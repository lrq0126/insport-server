<template>
  <div class="coupons-detail-view">
    <el-dialog
      title="分组成员信息"
      :visible.sync="dialog"
      :close-on-click-modal="false"
      width="90%"
    >
      <el-form :model="formItem" :rules="formItemRules" ref="formItem">
        <!-- style="float: left; width: 180px" -->
        <el-form-item label="分组名称：" >
          <el-input
            :readonly="true"
            v-model="formItem.groupName"
            placeholder="请输入分组名称："
            style="width: 300px"
          ></el-input>

          <el-form style="float: right">

             <el-form-item style="width: 180px; float:left">
              <el-button @click="openRouteView" type="primary">
                <span>按国家渠道添加成员</span>
              </el-button>
            </el-form-item>

            <el-form-item style="width: 100px; float:left">
              <el-button @click="addGroupCustomer" type="primary">
                <span>添加成员</span>
              </el-button>
            </el-form-item>

            <el-form-item style="width: 100px; float: left">
              <el-button
                :disabled="removeCustomerData.length == 0"
                @click="removeCustomer"
                type="danger"
              >
                <span>移除成员</span>
              </el-button>
            </el-form-item>
          </el-form>
        </el-form-item>

        <el-form-item label="成员明细：">
          <el-table
            :data="formItem.customerData"
            border
            :loading="loading"
            @selection-change="editGroupHandleSelectionChange"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
            ></el-table-column>
            <el-table-column
              label="客户名称"
              prop="customerName"
              align="center"
            >
            </el-table-column>
            <el-table-column label="会员ID" prop="loginName" align="center">
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" align="center">
            </el-table-column>
            <el-table-column label="手机号码" prop="phoneNumber" align="center">
            </el-table-column>
            <el-table-column label="性别" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.gender == 1" type="primary">男</el-tag>
                <el-tag v-else type="warning">女</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="邮箱" prop="email" align="center">
            </el-table-column>
            <el-table-column label="备注" prop="message" align="center">
            </el-table-column>
          </el-table>
        </el-form-item>

        <el-button @click="closeDialog" type="info">关 闭</el-button>
      </el-form>

      <add-customer-view ref="addCustomerView" @groupId="reSelectTable" />
      <route-model-view ref="routeModelView" @reSelectTable="reSelectTable" />

      <!-- 页码 -->
      <pagination v-show="formItem.total>0"
                  :total="formItem.total"
                  :current.sync="formItem.page"
                  :limit.sync="formItem.limit"
                  @pagination="pagination" />

    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getGroupDetailById,
  deleteGroupCustomer,
} from "@/api/customer-information/customer-group";
import AddCustomerView from "./add-customer";
import RouteModelView from './route-model';
export default {
  components: {
    Pagination,
    AddCustomerView,
    RouteModelView,
  },

  data() {
    return {
      dialog: false,
      loading: false,
      formItem: {
        page: 1, 
        limit: 10,
        total:0,

        id: "",
        groupName: "",
        remarks: "",
        customerData: [],
      },

      removeCustomerData: [],

      formItemRules: {
        groupName: [
          { required: true, message: "分组名称不能为空", trigger: "blur" },
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
        pagination (pageObj) {
            if (pageObj.bool) {
                this.formItem.page = pageObj.page
                this.handModel()
            } else {
                this.formItem.limit = pageObj.limit
                this.handModel(1)
            }
        },

    /**
     * 多选框事件
     * @param  {type} val {description}
     * @return {type} {description}
     */
    editGroupHandleSelectionChange(val) {
      this.removeCustomerData = val;
    },

    removeCustomer() {
      let customerIds = "";
      this.removeCustomerData.forEach((ele) => {
        customerIds = customerIds == "" ? ele.id : customerIds + "," + ele.id;
      });

      let data = {
        customerIds: customerIds,
        groupId: this.formItem.id,
      };

      deleteGroupCustomer(data).then((res) => {
        if (res.code == 100) {
          this.$message({
            type: "success",
            message: "移除成员成功",
            showClose: true,
            duration: 1000 * 1.5 * 3,
          });

          setTimeout(() => {
            this.handModel();
            this.$emit("updateList");
          }, 800);
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

    closeDialog() {
      this.dialog = false;
    },

    addGroupCustomer() {
      this.$refs["addCustomerView"].handModel(this.formItem.id);
    },

    openRouteView(){
      this.$refs['routeModelView'].openRouteDialog(this.formItem.id);
    },

    openGroupCustomerDialog(id){
      this.formItem.id = id;
      this.loading = true;
      this.handModel(1);
      setTimeout(() => {
        this.loading = false;
      }, 500)
      this.dialog = true;
    },

    handModel(page) {
      if(page){
        this.formItem.page = page
      }
      getGroupDetailById(this.formItem).then((res) => {
        if (res.code == 100) {
          this.formItem = res.content;
          this.formItem.customerData = res.content.customer;

          this.formItem.page = res.data.pageNumber;
          this.formItem.limit = res.data.pageSize
          this.formItem.total = res.data.total
        }
      });

    },

    reSelectTable(groupId) {
      this.formItem.id = groupId;
      this.handModel(1);
      setTimeout(() => {
        this.$emit("updateList");
      }, 800);
    },

    // 重置表单内容
    resetForm() {
      this.formItem = {
        id: "",
        groupName: "",
        remarks: "",
      };
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
