/**
 * 后台账号添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div style="margin-top: 50px">
    <el-form
      ref="form"
      :model="formItem"
      :rules="formItemRules"
      label-width="150px"
    >
      <el-row>
        <el-col :span="18">
          <el-form-item label="标题" prop="noticeTitle">
            <el-input
              v-model="formItem.noticeTitle"
              placeholder="请输入公告标题"
            ></el-input>
          </el-form-item>

          <el-form-item label="通知类型">
            <el-select v-model="formItem.noticeType">
              <el-option
                v-for="item in noticeTypes"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item
            label="通知对象"
            v-if="formItem.noticeType != 1"
            style="width: 50%; float: left"
            prop="readId"
          >
            <el-input
              v-model="formItem.readName"
              placeholder="请选择要通知的对象"
            ></el-input>
          </el-form-item>
          
          <el-form-item>
            <el-button
              v-if="formItem.noticeType == 2"
              type="primary"
              plain
              @click="selectedCustomer"
              >选择客户</el-button
            >
            <el-button
              v-if="formItem.noticeType == 3"
              type="primary"
              plain
              @click="selectedGroup"
              >选择分组</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>

      <!-- <el-row>
        <el-col :span="18">
          <el-form-item label="是否定时发布">
            <el-radio v-model="formItem.isTimingTask" label="1">否</el-radio>
            <el-radio v-model="formItem.isTimingTask" label="2">是</el-radio>
          </el-form-item>
          <el-form-item v-if="formItem.isTimingTask == 2" label="指定发布时间" prop="releaseTime">
            <el-date-picker v-model="formItem.releaseTime" type="datetime" value-format="yyyy-MM-dd HH" placeholder="选择时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row> -->

      <el-row>
        <el-col :span="18">
          <el-form-item label="过期时间" prop="overdueTime">
            <el-date-picker v-model="formItem.overdueTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="18">
          <el-form-item label="通知公告" prop="noticeContent">
            <el-input
              type="textarea"
              :rows="6"
              v-model="formItem.noticeContent"
              placeholder="请输入公告内容"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item>
        <el-button
          :loading="saving"
          type="primary"
          @click="handleSubmit('form')"
          >保 存</el-button
        >
        <el-button @click="handleReset('form')">重 置</el-button>
      </el-form-item>
    </el-form>

    <customer-model-view ref="customerModelView" @updateReadId="updateReadId" />

    <group-model-view ref="groupModelView" @updateReadId="updateReadId" />
  </div>
</template>

<script>
import { addNotificationAnnouncement } from "@/api/notice-notice/notice-list";
import Cookies from "js-cookie";
import CustomerModelView from "./customer-model";
import GroupModelView from "./group-model";

export default {
  name: "Create",
  components: {
    CustomerModelView,
    GroupModelView,
  },
  data() {
    return {
      saving: false,

      formItem: {
        noticeTitle: "",
        noticeType: 1,
        readId: "",
        readName: "",
        noticeContent: "",
        overdueTime: "",
        userCode: "",

        isTimingTask: "1",
        releaseTime:""
      },
      formItemRules: {
        noticeContent: [
          { required: true, message: "通知公告内容不能为空", trigger: "blur" },
        ],
        noticeTitle: [
          { required: true, message: "通知公告标题不能为空", trigger: "blur" },
        ],
        readId: [
          { required: true, message: "请选择要通知的对象", trigger: "blur" },
        ],
        overdueTime: [
          { required: true, message: "请选择过期时间", trigger: "blur" },
        ],
        releaseTime: [
          { required: true, message: "请选择定时发布时间", trigger: "blur" },
        ],
      },

      noticeTypes: [
        {
          value: 1,
          name: "系统通知",
        },
        {
          value: 2,
          name: "个人通知",
        },
        {
          value: 3,
          name: "分组通知",
        },
      ],
    };
  },
  created() {
    this.formItem.userCode = Cookies.get("usercode");
  },
  methods: {
    selectedCustomer() {
      console.log("选择客户");
      this.$refs["customerModelView"].openDialog();
    },

    selectedGroup() {
      console.log("选择分组");
      this.$refs["groupModelView"].openDialog();
    },

    updateReadId(data) {
      this.formItem.readId = data.id;

      if (data.customerName) {
        this.formItem.readName = data.customerName;
      } else {
        this.formItem.readName = data.groupName;
      }
    },

    /**
     * 保存
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit(from) {
      this.$refs[from].validate((valid) => {
        if (valid) {
          this.saving = true;
          addNotificationAnnouncement(this.formItem)
            .then((res) => {
              if (res.code === 100) {
                this.$message({
                  message: "发布成功",
                  type: "success",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
                this.handleReset("form");
              }
            })
            .finally(() => {
              this.saving = false;
            });
        }
      });
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset() {
      this.formItem = {
        noticeTitle: "",
        noticeType: 1,
        readId: "",
        readName: "",
        noticeContent: "",
        userCode: "",
        isTimingTask: "1",
        releaseTime:""
      };
    },
  },
};
</script>

<style>
</style>
