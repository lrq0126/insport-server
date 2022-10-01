<template>
  <div>
      <el-dialog :visible.sync="integralRuleDialog" table="积分规则信息" width="60%" :close-on-click-moda="false">
          <el-form :model="formItem" ref="form" :rules="formItemRules" label-width="100px">
              <el-form-item label="积分规则" prop="integralType">
                  <el-select v-model="formItem.integralType">
                      <el-option v-for="item in integralTypes" :key="item.id" :value="item.value" :label="item.name"/>
                  </el-select>
              </el-form-item>

              <el-form-item v-if="formItem.integralType == 'order' || formItem.integralType == 'childOrder'" label="订单金额" prop="orderAmount">
                  <el-input-number v-model="formItem.orderAmount"/>
                  <el-alert style="width: 60%" title="*这里指的是多少金额兑换多少积分，建议按多少金额赚取1积分这样设置" type="error"/>
              </el-form-item>

              <el-form-item label="积分数" prop="integrals">
                  <el-input-number :min="0" :precision="2" :step="0.5" v-model="formItem.integrals"/>
              </el-form-item>

              <el-form-item v-if="formItem.integralType == 'deductible'" label="抵扣金额" prop="deductibleAmount">
                  <el-input-number v-model="formItem.deductibleAmount"/>
                  <el-alert style="width: 60%" title="*这里指的是积分抵扣金额，建议按多少积分抵扣1元这样设置" type="error"/>
              </el-form-item>

              <el-form-item label="备注">
                  <el-input type="textarea" :rows="4" style="width: 60%" v-model="formItem.remarks"/>
              </el-form-item>
              <el-form-item>
                  <el-button @click="integralRuleDialog = flase">取 消</el-button>
                  <el-button type="primary" @click="submit('form')">保 存</el-button>
              </el-form-item>
          </el-form>
      </el-dialog>
  </div>
</template>

<script>

import {saveIntegralRule, getIntegralRule} from "@/api/operating-windows/integral-rule"

export default {
    components: {

    },
    props:{
        integralTypes: Array
    },
    data(){
        return{
            integralRuleDialog: false,

            formItem:{
                id: "",
                integralType: "",
                orderAmount: "",
                integrals:"",
                deductibleAmount:"",
                remarks:""
            },

            formItemRules: {
                integralType: [
                    { required: true, message: "积分规则类型不能为空", trigger: "blur" },
                ],
                orderAmount: [
                    { required: true, message: "下单金额不能为空", trigger: "blur" },
                ],
                integrals: [
                    { required: true, message: "积分数不能为空", trigger: "blur" },
                ],
                deductibleAmount: [
                    { required: true, message: "抵扣金额不能为空", trigger: "blur" },
                ],
            },

        }
    },

    methods:{
        openIntegralRuleInfo(data){
            this.integralRuleDialog = true;
            if(data){
                this.formItem.id = data.id
                this.getIntegralRuleInfo();
            }else{
                this.resetFormItem();
            }
        },
        getIntegralRuleInfo(){
            getIntegralRule(this.formItem.id).then((res) => {
                this.formItem = res.content
            })
        },
        submit(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    if(this.formItem.integralType == 'order' || this.formItem.integralType == 'childOrder'){
                        this.formItem.deductibleAmount = ""
                    }else if(this.formItem.integralType == 'deductible'){
                        this.formItem.orderAmount = ""
                    }
                    saveIntegralRule(this.formItem).then(res => {
                        if(res.code == 100){
                            this.$message({
                                type:"success",
                                message: res.content
                            })
                            this.integralRuleDialog = false;
                            this.$emit('reselect');
                        } 
                    }) 
                }
            })
            
        },

        resetFormItem(){
            this.formItem = {
                id: "",
                integralType: "",
                orderAmount: "",
                integrals:"",
                deductibleAmount:"",
                remarks:""
            }
        }
    }

}
</script>

<style>

</style>