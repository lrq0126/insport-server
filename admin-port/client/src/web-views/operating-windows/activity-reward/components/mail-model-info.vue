<template>
  <div>
    <el-dialog :visible.sync="dialog" width="60%" :close-on-click-modal="false" title="邮件详情">
      <el-form ref="form" :model="formItem" :rules="formItemRules" label-width="120px">
          <el-form-item label="标题：" prop="title">
            <el-input v-model="formItem.title" style="width: 50%" placeholder="请输入想要发送的内容"/>
          </el-form-item>

          <el-form-item label="发送对象" prop="sendObjectType">
            <el-select v-model="formItem.sendObjectType" @change="changeSendObject">
              <el-option label="个人" value="personal"/>
              <el-option label="群发" value="group"/>
            </el-select>
          </el-form-item>

          <el-form-item v-if="formItem.sendObjectType == 'personal'" label="会员ID" v-model="formItem.sendObjectId" prop="sendObjectId">
            <el-input v-model="formItem.sendObjectName" style="width: 200px" readonly/>
            <el-button type="primary" @click="openCustomerList()">选择客户</el-button>
          </el-form-item>

          <el-form-item v-else  label="分组名称" v-model="formItem.sendObjectId" prop="sendObjectId">
            <el-input v-model="formItem.sendObjectName" style="width: 200px" readonly/>
            <el-button type="primary" @click="openCustomerGroup()">选择分组</el-button>
          </el-form-item>

          <el-form-item label="内容类型" prop="contentType">
            <el-select v-model="formItem.contentType" disabled>
              <el-option label="纯文字" value="text"/>
              <el-option label="HTML" value="html"/>
            </el-select>
          </el-form-item>
          <el-form-item label="内容：" prop="content">
            <div v-if="formItem.contentType == 'text'">
              <el-input type="textarea" v-model="formItem.content" :rows="8" style="width: 60%" placeholder="请输入想要发送的内容"/>
            </div>
            <div v-if="formItem.contentType == 'html'">
              <div style="width: 70%; float: left">
                <el-input type="textarea" v-model="formItem.content" :rows="8" style="width: 86%" placeholder="请输入想要发送的内容"/>
              </div>
              <div style="width: 30%; float: left">
                文本类型选择框
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="saveMail('form')">保 存</el-button>
            <el-button type="primary" @click="sendMail('form')">发 送</el-button>
            <el-button type="info" @click="dialog = false">取 消</el-button>
          </el-form-item>
      </el-form>
    </el-dialog>
    <customer-list-view ref="customerListView" @updateSendObject="updateSendObject"/>
    <customer-group-view ref="customergroupView" @updateSendObject="updateSendObject"/>
  </div>
</template>

<script>
import CustomerListView from './customer-list'
import CustomerGroupView from './customer-group'
import {getEmailInfo, saveEmail, sendEmail} from '@/api/operating-windows/activity-reward'
export default {
    components: {
      CustomerListView,
      CustomerGroupView
    },
    data() {
        return {
            dialog: false,
            formItem: {
              title: "",
              sendObjectType: "personal",
              sendObjectId: "",
              sendObjectName: "",
              contentType: "text",
              content: "",
            },
            formItemRules: {
                title: [
                    { required: true, message: "请输入邮件标题", trigger: "blur" },
                ],
                sendObjectType: [
                    { required: true, message: "请选择发送对象类型", trigger: "blur" },
                ],
                sendObjectId: [
                    { required: true, message: "请选择发送对象", trigger: "blur" },
                ],
                contentType: [
                    { required: true, message: "请选择内容类型", trigger: "blur" },
                ],
                content: [
                    { required: true, message: "请输入邮件内容", trigger: "blur" },
                ],
                
            },
        }
    },
    methods: {
      openDialog(mailId){
        if(mailId){
          getEmailInfo(mailId).then((res) => {
            if(res.code == 100) {
              this.formItem = res.content
            }
          })
        }else{
          this.formItem = {
            title: "",
            sendObjectType: "personal",
            sendObjectId: "",
            sendObjectName: "",
            contentType: "text",
            content: "",
          }
        }
        this.dialog = true;
      },

      changeSendObject(){
        this.formItem.sendObjectId = ""
        this.formItem.sendObjectName = ""
      },

      /**
       * 打开客户列表
       */
      openCustomerList(){
        this.$refs['customerListView'].openDialog();
      },

      /**
       * 打开客户分组
       */
      openCustomerGroup(){
        this.$refs['customergroupView'].openDialog();
      },

      updateSendObject(objectData){
        this.formItem.sendObjectId = objectData.sendObjectId;
        this.formItem.sendObjectName = objectData.sendObjectName;
      },

      saveMail(form){
        this.$refs[form].validate((valid) => {
          if (valid) {

            saveEmail(this.formItem).then((res) => {
              if(res.code == 100){
                this.$message({
                  message: "保存成功",
                  type: "success"
                })
              }
            });

            this.$emit('updateList');
            this.dialog = false;
          }
        })
      },

      sendMail(form){
        this.$refs[form].validate((valid) => {
          if (valid) {
            sendEmail(this.formItem);
            setTimeout(() => {
              this.dialog = false;
              this.$message({
                message: "邮件已在后台陆续发送，请后续查看发送结果",
                type: "success"
              })
              this.$notify({
                title: '邮件已发送',
                message: '邮件已在后台陆续发送，请后续查看发送结果',
                duration: 2000
              });
              this.$emit('updateList');
            }, 300)
          }
        })
      }
    }
}
</script>

<style>

</style>