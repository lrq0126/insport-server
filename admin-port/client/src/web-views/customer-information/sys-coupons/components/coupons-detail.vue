<template>
  <div class="coupons-detail-view">
    <el-dialog title="优惠券" :visible.sync="dialog" width="40%" :close-on-click-modal=false>
      <el-form :model="formItem" :rules="formItemRules" ref="formItem">
        <!-- style="float: left; width: 180px" -->
        <el-form-item label="优惠券名称：" prop="couponsName">
          <el-input
            v-model="formItem.couponsName"
            placeholder="请输入优惠券名称"
            style="width: 300px"
          ></el-input>
        </el-form-item>

        <el-form-item label="优惠券类型：" prop="couponsType">
          <el-select
            v-model="formItem.couponsType"
            placeholder="请选择优惠券类型"
            style="width: 180px"
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

        <el-form-item
          label="使用条件："
          v-if="formItem.couponsType == 2"
          prop="minimumAmount"
        >
          <span>满 &nbsp;</span
          ><el-input
            v-model="formItem.minimumAmount"
            type="number"
            placeholder="请输入金额"
            style="width: 120px"
          ></el-input>
          <span>元可用</span>
        </el-form-item>

        <el-form-item label="优惠券金额：" prop="couponsAmount">
          <el-input
            v-model="formItem.couponsAmount"
            type="number"
            placeholder="请输入优惠券金额"
            style="width: 200px"
          ></el-input>
          <span>元</span>
        </el-form-item>

        <el-form-item label="是否可兑换：">
          <el-radio v-model="formItem.isExchange" label="1">是</el-radio>
          <el-radio v-model="formItem.isExchange" label="0">否</el-radio>
        </el-form-item>

        <el-form-item
          label="兑换所需积分："
          v-if="formItem.isExchange == '1'"
          prop="exchangeNum"
        >
          <el-input
            v-model="formItem.exchangeNum"
            type="number"
            placeholder="请输入兑换所需积分"
            style="width: 120px"
          ></el-input>
        </el-form-item>

        <el-form-item label="过期类型：">
          <el-radio v-model="formItem.overdueType" label="1">固定日期过期</el-radio>
          <el-radio v-model="formItem.overdueType" label="2">固定天数过期</el-radio>
        </el-form-item>

        <el-form-item v-if="formItem.overdueType == 1" label="过期日期：" prop="timeOverdue">
          <el-date-picker v-model="formItem.timeOverdue" value-format="yyyy-MM-dd" type="date" format="yyyy 年 MM 月 dd 日" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item v-if="formItem.overdueType == 2" label="过期天数：" prop="timeLimit">
          <el-input
            v-model="formItem.timeLimit"
            type="number"
            placeholder="请输入过期天数（从领取之日起算）"
            style="width: 250px"
          ></el-input>
          <span>天</span>
        </el-form-item>

        <el-form-item label="是否启用：">
          <el-radio v-model="formItem.isEnable" label="1">是</el-radio>
          <el-radio v-model="formItem.isEnable" label="0">否</el-radio>
        </el-form-item>
        <el-form-item label="描述：">
          <el-input
            v-model="formItem.remarks"
            type="textarea"
            rows="4"
            placeholder="请输入优惠券描述内容"
            style="width: 300px"
          ></el-input>
        </el-form-item>
        <el-button @click="submit" type="primary">
          <span v-if="formItem.id == ''">提 交</span>
          <span v-else>修 改</span>
        </el-button>

        <el-button @click="closeDialog" type="info">取 消</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import {
  createCoupons,
  editCoupons,
  getCouponsById,
} from "@/api/customer-information/sys-coupons";

export default {
  components: {},

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

      dialog: false,

      formItem: {
        id: "",
        version: "",
        isEnable: "1",
        couponsCode:"",
        couponsName: "",
        couponsType: "",
        couponsAmount: "",
        minimumAmount: "",
        isExchange: "0",
        exchangeNum: "",
        overdueType: "1",
        timeLimit: "",
        timeOverdue: "",
        remarks: "",
      },

      formItemRules: {
        couponsName: [
          { required: true, message: "优惠券名称不能为空", trigger: "blur" },
        ],
        couponsAmount: [
          { required: true, message: "优惠券金额不能为空", trigger: "blur" },
        ],
        minimumAmount: [
          {
            required: true,
            message: "请输入满减券的使用限额",
            trigger: "blur",
          },
        ],
        exchangeNum: [
          { required: true, message: "请输入兑换所需积分", trigger: "blur" },
        ],
        couponsType: [
          { required: true, message: "请选择优惠券的类型", trigger: "blur" },
        ],
        timeLimit: [
          { required: true, message: "请输入过期天数", trigger: "blur" },
        ],
        timeOverdue: [
          { required: true, message: "请选择过期时间", trigger: "blur" },
        ],
      },
    };
  },

  methods: {
    submit() {

      if (this.formItem.couponsType != 2) {
        this.formItem.minimumAmount = 0;
      }

      if (this.formItem.id == "") {
        createCoupons(this.formItem).then((res) => {
          if (res.code == 100) {
            this.$message({
              type: "success",
              message: "新增优惠券成功",
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });

            this.$emit("updateList");

            this.dialog = false;
          } else {
            this.$message({
              type: "error",
              message: res.message,
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });
          }
        });
      } else {
        editCoupons(this.formItem).then((res) => {
          if (res.code == 100) {
            this.$message({
              type: "success",
              message: "编辑优惠券成功",
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });

            this.$emit("updateList");

            this.dialog = false;
          } else {
            this.$message({
              type: "error",
              message: res.message,
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });
          }
        });
      }
    },

    closeDialog() {
      this.dialog = false;
    },

    handCreate() {
      this.resetForm();
      this.dialog = true;
    },

    handEdit(id) {
      this.resetForm();
      getCouponsById({ id }).then((res) => {
        if (res.code == 100) {
          this.dialog = true;
          this.formItem = res.content;
          this.formItem.isEnable = String(res.content.isEnable);
          this.formItem.isExchange = String(res.content.isExchange);
          this.formItem.overdueType = String(res.content.overdueType);
          this.formItem.couponsType = String(res.content.couponsType);
        } else {
          this.$message({
            type: "error",
            message: res.message,
            showClose: false,
            duration: 1000 * 1.5 * 2,
          });
        }
      });
    },

    // 重置表单内容
    resetForm() {
      this.formItem = {
        id: "",
        version: "",
        isEnable: "1",
        couponsCode:"",
        couponsName: "",
        couponsType: "",
        couponsAmount: "",
        minimumAmount: "",
        isExchange: "0",
        exchangeNum: "",
        overdueType: "1",
        timeOverdue: "",
        timeLimit: "",
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
