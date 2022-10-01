<template>
  <div>
    <el-dialog :visible.sync="payReturnDialog" title="客户回访" width="40%" :close-on-click-modal="false">
            <el-form label-width="140px">
                <el-form-item label="订单号：">
                  <el-input style="width: 280px" v-model="evaluateData.orderNumber" :readonly="true"/>
                </el-form-item>
                <el-form-item label="客户名称：">
                  <el-input style="width: 280px" v-model="evaluateData.customerName" :readonly="true"/>
                </el-form-item>
                <el-form-item label="会员ID：">
                  <el-input style="width: 280px" v-model="evaluateData.loginName" :readonly="true"/>
                </el-form-item>
                <el-form-item label="国家：">
                  <el-input style="width: 280px" v-model="evaluateData.destination" :readonly="true"/>
                </el-form-item>
                <el-form-item label="渠道名称：">
                  <el-input style="width: 280px" v-model="evaluateData.routeName" :readonly="true"/>
                </el-form-item>
                <el-form-item label="打包速度：">
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingSpeed >= 1" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingSpeed >= 2" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingSpeed >= 3" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingSpeed >= 4" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingSpeed >= 5" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                </el-form-item>
                <el-form-item label="打包质量：">
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingQuality >= 1" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingQuality >= 2" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingQuality >= 3" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingQuality >= 4" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.packingQuality >= 5" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                </el-form-item>
                <el-form-item label="物流速度：">
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.logisticsSpeed >= 1" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.logisticsSpeed >= 2" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.logisticsSpeed >= 3" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.logisticsSpeed >= 4" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.logisticsSpeed >= 5" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                </el-form-item>
                <el-form-item label="客服态度：">
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.customerServerAttitude >= 1" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.customerServerAttitude >= 2" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.customerServerAttitude >= 3" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.customerServerAttitude >= 4" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.customerServerAttitude >= 5" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                </el-form-item>
                <el-form-item label="综合服务：">
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.comprehensiveServer >= 1" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.comprehensiveServer >= 2" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.comprehensiveServer >= 3" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.comprehensiveServer >= 4" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                  <div style="float: left; width: 10% ">
                      <img v-if="evaluateData.comprehensiveServer >= 5" src="../icon/startL.png" alt="" />
                      <img v-else src="../icon/start.png" alt="" />
                  </div>
                </el-form-item>
                <el-form-item label="评语：">
                  <el-input v-if="evaluateData.remarks && evaluateData.remarks != 'null'" 
                            type="textarea" :rows="3" v-model="evaluateData.remarks"
                            style="width: 360px"
                            :readonly="true" placeholder="暂无评语"/>
                </el-form-item>
                <el-form-item label="售后说明">
                  <el-input v-model="payReturnMessage" type="textarea" placeholder="请输入需要发送的信息" :rows="3" style="width: 360px"/>
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="remarks" type="textarea" :rows="3" style="width: 360px"/>
                </el-form-item>
            </el-form>
            <el-button type="primary" style="margin-left: 45%; margin-top: 20px" @click="payReturn">发 送</el-button>
        </el-dialog>
  </div>
</template>

<script>
import { payReturn } from '@/api/operating-windows/order-evaluate.js'
export default {
  components:{

  },
  data(){
    return{
      payReturnDialog: false,
      evaluateData:{},
      payReturnMessage: "",
      remarks: "感谢您的使用，如有问题，请添加客服进行咨询"
    }
  },

  methods:{
    openPayReturn(data){
      this.payReturnDialog = true
      this.evaluateData = data
    },
    payReturn(){
      let orderMessage = {
        orderNumber: this.evaluateData.orderNumber,
        customerId: this.evaluateData.customerId,
        payReturnMessage: this.payReturnMessage,
        remarks: this.remarks
      }
      payReturn(orderMessage).then((res) => {
        this.payReturnDialog = false
        if(res.code == 100){
          this.$message({
            message:"信息发送成功",
            type:"success"
          })
        }else{
          this.$message({
            message:res.message,
            type:"warning"
          })
        }
      })
    },
  }
}
</script>

<style>

</style>