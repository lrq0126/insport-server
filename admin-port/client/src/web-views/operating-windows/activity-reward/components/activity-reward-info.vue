<template>
  <div>
      <el-dialog :visible.sync="viewDialog" title="活动配置详情" :close-on-click-modal="false">
          <el-form :model="formItem" ref="form" :rules="formItemRules" label-width="100px">
              <el-form-item label="活动名称" prop="activityName">
                  <el-input style="width: 400px" v-model="formItem.activityName"/>
              </el-form-item>
              <el-form-item label="活动类型" prop="activityType">
                  <el-select v-model="formItem.activityType">
                      <el-option v-for="item in activityTypes" :key="item.id" :value="item.value" :label="item.name"/>
                  </el-select>
              </el-form-item>
              <el-form-item label="活动时间类型" prop="activityTimeType">
                  <el-select v-model="formItem.activityTimeType">
                      <el-option value="1" label="长期活动"/>
                      <el-option value="2" label="限时活动"/>
                  </el-select>
              </el-form-item>
              <el-form-item v-if="formItem.activityTimeType == 2" label="活动时间" prop="activityDateTime">
                  <el-date-picker
                    style="width: 400px"
                    v-model="formItem.activityDateTime"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    format="yyyy年MM月dd日"
                    value-format="yyyy-MM-dd"
                    @change="handlePicker">
                    </el-date-picker>
              </el-form-item>

              <el-form-item label="奖励类型" prop="rewardType">
                  <el-select v-model="formItem.rewardType" @change="cleanRewardContent">
                      <el-option value="coupons" label="优惠券"/>
                      <el-option value="integral" label="积分"/>
                  </el-select>
              </el-form-item>

              <el-form-item v-if="formItem.rewardType == 'coupons'" label="赠送内容" prop="rewardContentId">
                  <el-input v-model="rewardContent" style="width: 200px" readonly/>
                  <el-button @click="openSysCouponsDialog">选择优惠券</el-button>
              </el-form-item>

              <el-form-item v-if="formItem.rewardType == 'integral'" label="奖励内容" prop="rewardContentId">
                  <el-input v-model="rewardContent" style="width: 200px" readonly/>
                  <el-button @click="openIntegralDialog">选择积分</el-button>
              </el-form-item>
              
              <el-form-item label="是否启用" prop="isEnable">
                  <el-radio v-model="formItem.isEnable" label="1">启用</el-radio>
                  <el-radio v-model="formItem.isEnable" label="-1">停用</el-radio>
              </el-form-item>
        
              <el-form-item label-width="200px">
                  <el-button @click="viewDialog = !viewDialog">取 消</el-button>
                  <el-button type="primary" @click="submit('form')">提 交</el-button>
              </el-form-item>
          </el-form>

        <integral-rule-list-view ref="integralRuleListView" @updateRewardContent="updateRewardContentIntregral" :integralTypes="integralTypes"/>
        <sys-coupons-list-view ref="sysCouponsListView" @updateRewardContent="updateRewardContentCoupons"/>
      </el-dialog>
  </div>
</template>

<script>
import IntegralRuleListView from "./integral-rule-list";
import SysCouponsListView from "./sys-coupons-list";
import {saveActivityReward, getActivityReward} from '@/api/operating-windows/activity-reward.js'
export default {
    components:{
        IntegralRuleListView,
        SysCouponsListView
    },
    props:{
        integralTypes: Array,
        activityTypes: Array
    },
    data(){
        return{
            viewDialog: false,

            formItem:{
                id: "",
                isEnable: "1",
                activityName: "",
                activityType: "",
                activityTimeType: "",
                activityDateTime: "",
                startTime: "",
                endTime: "",
                rewardType: "",
                rewardContentId: "",
            },
            rewardContent:"",

            formItemRules: {
                activityName: [
                    { required: true, message: "活动名称不能为空", trigger: "blur" },
                ],
                activityType: [
                    { required: true, message: "活动类型不能为空", trigger: "blur" },
                ],
                rewardType: [
                    { required: true, message: "奖励类型不能为空", trigger: "blur" },
                ],
                activityTimeType: [
                    { required: true, message: "请选择活动时间类型", trigger: "blur" },
                ],
                activityDateTime: [
                    { required: true, message: "请选择活动时间", trigger: "blur" },
                ],
                rewardContentId: [
                    { required: true, message: "请选择赠送内容", trigger: "blur" },
                ],
            },

        }
    },

    methods:{

        openDialog(id){
            this.viewDialog = true;
            if(id){
                this.formItem.id = id;
                this.getActivityReward();
            }else{
                this.resetFormItem();
            }
        },

        cleanRewardContent(){
            this.rewardContent = ""
            this.formItem.rewardContentId = ""
        },

        /**
         * 打开积分规则界面
         */
        openIntegralDialog(){
            this.$refs['integralRuleListView'].openIntegralRule();
        },
        updateRewardContentIntregral(data){
            this.rewardContent = data.integralName;
            this.formItem.rewardContentId = data.id;
        },
        /**
         * 打开优惠券界面
         */
        openSysCouponsDialog(){
            this.$refs['sysCouponsListView'].openSysCouponsDialog();
        },
        updateRewardContentCoupons(data){
            this.rewardContent = data.couponsName;
            this.formItem.rewardContentId = data.id
        },

        getActivityReward(){
            getActivityReward(this.formItem.id).then((res) => {
                this.formItem = res.content
                this.formItem.isEnable = String(res.content.isEnable);
                this.formItem.activityTimeType = String(res.content.activityTimeType);
                if(res.content.integralType){
                    this.integralTypes.forEach((ele) => {
                        if(res.content.integralType == ele.value){
                            this.rewardContent = ele.name
                        }
                    })
                }else{
                    this.rewardContent = res.content.couponsName
                }
                
                
            })
        },

        /**
         * 时间控件（change事件）
         * @return {type} {description}
         */
        handlePicker (data) {
            if (data) {
                this.formItem.startTime = data[0]
                this.formItem.endTime = data[1]
            } else {
                this.formItem.startTime = ''
                this.formItem.endTime = ''
            }
        },

        submit(form){

            this.$refs[form].validate((valid) => {
                if (valid) {
                    if(this.formItem.activityTimeType == 1){
                        this.formItem.startTime = ""
                        this.formItem.endTime = ""
                    }
                    saveActivityReward(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.viewDialog = false;
                            this.$message({
                                type:"success",
                                message:"保存成功"
                            })
                            this.$emit('reselect');
                        }else{
                            this.$message({
                                type:"error",
                                message: res.content
                            })
                        }
                    })
                }
            })
        },

        resetFormItem(){
            this.formItem={
                id: "",
                isEnable: "1",
                activityName: "",
                activityType: "",
                activityTimeType: "",
                startTime: "",
                endTime: "",
                rewardType: "",
                rewardContentId: "",
            }
        },

    },


}
</script>

<style>

</style>