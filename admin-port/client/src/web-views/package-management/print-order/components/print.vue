<template>
  <div>
    <el-form
      ref="form"
      :model="formItem"
      :rules="formItemRules"
      label-width="30%"
    >
      <el-row style="margin-top: 50px">
        <el-col :span="24">
          <el-form-item v-if="contentMessage">
            <span style="font-size: 30px; color: red">{{
              contentMessage
            }}</span>
          </el-form-item>
          <el-form-item label="单号：" prop="orderNumber">
            <el-input
              style="width: 600px"
              v-model="formItem.orderNumber"
              placeholder="请输入单号"
            />
          </el-form-item>
          <el-form-item label="件数：" prop="number">
            <el-input
              style="width: 600px"
              v-model="formItem.number"
              placeholder="请输入件数"
            />
          </el-form-item>
          <el-form-item label="国家：">
            <el-input
              style="width: 600px"
              v-model="formItem.country"
              placeholder="请输入国家"
            />
          </el-form-item>
          <el-form-item label="收件人：">
            <el-input
              style="width: 600px"
              v-model="formItem.addressee"
              placeholder="请输入收件人"
            />
          </el-form-item>
          <el-form-item label="联系方式：">
            <el-input
              style="width: 600px"
              v-model="formItem.phoneNumber"
              placeholder="请输入联系方式"
            />
          </el-form-item>
          <el-form-item label="收货地址：">
            <el-input
              type="textarea"
              style="width: 600px"
              v-model="formItem.address"
              placeholder="请输入收货地址"
            />
          </el-form-item>

          <el-form-item>
            <el-button
              :loading="saving"
              type="primary"
              @click="printOrder('form')"
              >打印面单</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { orderPrint } from "@/api/package-management/print-order.js";

export default {
  components: {},
  data() {
    return {
      saving: false,
      contentMessage: "",
      formItem: {
        orderNumber: "",
        number: "",

        country: "",
        addressee: "",
        phoneNumber: "",
        address: "",
      },

      formItemRules: {
        orderNumber: [
          { required: true, message: "单号不能为空", trigger: "blur" },
        ],
        number: [{ required: true, message: "数量不能为空", trigger: "blur" }],
      },
    };
  },

  methods: {
    printOrder(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.saving = true;
          orderPrint(this.formItem).then((res) => {
            if (res.code == 100) {
              this.reSetForm();
              window.open("http://" + res.content.url);
            } else {
              this.contentMessage = res.content;
            }
          });

          setTimeout(() => {
            this.saving = false;
          }, 500);
        }
      });
    },

    reSetForm() {
      (this.contentMessage = ""),
        (this.formItem = {
          orderNumber: "",
          number: "",

          country: "",
          addressee: "",
          phoneNumber: "",
          address: "",
        });
    },
  },
};
</script>

<style scoped>
</style>
