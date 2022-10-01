/**
 * 后台账号添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div style="margin-top: 50px">
    <el-dialog
      title="保险保价明细"
      :visible.sync="detailDialog"
      width="80%"
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="formItem"
        :rules="formItemRules"
        label-width="150px"
      >
        <el-row :gutter="8">
          <el-col :span="8">
            <el-form-item label="保险名称" prop="insuranceName">
              <el-input
                v-model="formItem.insuranceName"
                placeholder="请输入保险名称"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="8">
          <el-col :span="8">
            <el-form-item label="备注信息">
              <el-input
                type="textarea"
                v-model="formItem.remarks"
                placeholder="请输入备注信息"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="8">
          <el-col :span="8">
            <el-form-item label="理赔详情">
              <el-input
                type="textarea"
                v-model="formItem.claimsDetail"
                placeholder="请输入理赔详情"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="8">
          <el-col :span="23">
            <el-divider content-position="center">
              <i class="el-icon-tickets"></i>保险价格详情</el-divider
            >
            <el-form-item>
              <el-button
                type="primary"
                plain
                style="margin-bottom: 5px"
                icon="el-icon-circle-plus-outline"
                @click="addTopicBig"
                >新增保价明细</el-button
              >
              <el-button type="warning" plain @click="addPriceExcel"
                >Excel文本输入</el-button
              >
              <el-table
                :data="formItem.insurancePriceData"
                border
                fit
                v-loading="priceLoading"
                highlight-current-row
                style="width: 100%; margin-top: 20px"
              >
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center"
                ></el-table-column>

                <el-table-column prop="price" align="center" label="保险费用">
                  <template slot-scope="scope">
                    <el-form-item
                      style="margin-bottom: 15px"
                      :prop="'insurancePriceData.' + scope.$index + '.price'"
                      :rules="[
                        {
                          required: true,
                          message: '请输入正确的保险费用数据',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-input
                        v-model="scope.row.price"
                        type="number"
                        placeholder="请输入保险费用"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column
                  prop="ginsengPrice"
                  align="center"
                  label="参保价格"
                >
                  <template slot-scope="scope">
                    <el-form-item
                      style="margin-bottom: 15px"
                      :prop="
                        'insurancePriceData.' + scope.$index + '.ginsengPrice'
                      "
                      :rules="[
                        {
                          required: true,
                          message: '请输入正确的参保价格数据',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-input
                        v-model="scope.row.ginsengPrice"
                        type="number"
                        placeholder="请输入参保价格"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column
                  prop="claimsDetail"
                  align="center"
                  label="理赔详情"
                >
                  <template slot-scope="scope">
                    <el-form-item style="margin-bottom: 15px">
                      <el-input
                        v-model="scope.row.claimsDetail"
                        placeholder="请输入理赔详情"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column align="center" label="操作">
                  <template slot-scope="scope">
                    <el-form-item style="margin-bottom: 15px">
                      <el-button
                        type="danger"
                        @click="removePrice(scope.$index)"
                        >删除</el-button
                      >
                    </el-form-item>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label-width="260px">
              <el-button
                :loading="saving"
                type="primary"
                @click="handleSubmit('form')"
                >保 存</el-button
              >
              <el-button @click="handleReset()">重 置</el-button>
              <el-button type="info" @click="detailDialog = false"
                >取消</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <price-excel-view ref="priceExcelView" @updatePriceList="updatePriceList" />
  </div>
</template>

<script>
import {
  getInsuranceDetail,
  createInsurance,
  editInsurance,
} from "@/api/channel-management/insurance";
import PriceExcelView from "./price-excel";

export default {
  components: {
    PriceExcelView,
  },
  
  data() {
    return {

      formItemRules: {
        insuranceName: [
          { required: true, message: "保险名称不能为空", trigger: "blur" },
        ],
      },


      saving: false,
      priceLoading: false,
      detailDialog: false,
      formItem: {
        id: "",
        remarks: "",
        insuranceName: "",
        claimsDetail: "",

        insurancePriceData: [
          {
            price: "",
            ginsengPrice: "",
            claimsDetail: "",
          },
        ],
      },
      
    };
  },

  methods: {
    /**
     * 保存
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit(from) {
      this.$refs[from].validate((valid) => {
        if (valid) {
          this.saving = true;
          this.formItem.insurancePrices = JSON.stringify(this.formItem.insurancePriceData);
          if (this.formItem.id != "") {
            editInsurance(this.formItem).then((res) => {
              if (res.code == 100) {
                this.$message({
                  message: "编辑成功",
                  type: "success",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
                this.$emit("reSelect");

                this.saving = false;
                this.detailDialog = false;
              } else {
                this.$message({
                  message: res.message,
                  type: "error",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
              }
            });
          } else {
            createInsurance(this.formItem).then((res) => {
              if (res.code == 100) {
                this.$message({
                  message: "新增成功",
                  type: "success",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });

                this.$emit("reSelect");

                this.saving = false;
                this.detailDialog = false;
              } else {
                this.$message({
                  message: res.message,
                  type: "error",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
              }
            });
          }
        }
      });
    },

    createInfoModel() {
      this.detailDialog = true;
      this.saving = false;
      this.handleReset();
      this.formItem.id = "";
    },

    editInfoModel(id) {
      this.saving = false;
      getInsuranceDetail({ id }).then((res) => {
        if (res.code == 100) {
          this.formItem = res.content;
          this.formItem.insurancePriceData = res.content.insurancePrices;
        } else {
          this.$message({
            type: "error",
            message: "当前数据有问题，请刷新页面重试",
          });
        }
      });
      this.detailDialog = true;
    },

    removePrice(index) {
      if (index > -1) {
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.formItem.insurancePriceData.splice(index, 1);
        });
      }
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset() {
      this.formItem.remarks = "";
      this.formItem.insuranceName = "";
      this.formItem.claimsDetail = "";
      this.formItem.insurancePriceData = [
        {
          price: "",
          ginsengPrice: "",
          claimsDetail: "",
        },
      ];
    },

    addTopicBig() {
      let insurancePrice = {
        price: "",
        ginsengPrice: "",
        claimsDetail: "",
      };
      this.formItem.insurancePriceData.push(insurancePrice);
    },

    addPriceExcel() {
      this.$refs["priceExcelView"].openDialog();
    },

    updatePriceList(data) {
      this.priceLoading = true;
      this.$message({
        message: "Excel转换成功",
        type: "success",
      });

      setTimeout(() => {
        this.priceLoading = false;
      }, 1000);
      this.formItem.insurancePriceData = data;
    },
  },
};
</script>

<style>
</style>
