/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
  <div class="model-view">
    <!--编辑信息 -->
    <el-dialog :visible.sync="dialogEditInfo" title="通知公告信息" width="60%">
      <el-form
        ref="form"
        :model="formItem"
        :rules="formItemRules"
        label-width="150px"
        disabled
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
              <el-date-picker
                v-model="formItem.overdueTime"
                type="date"
                value-format="yyyy-MM-dd"
              >
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
    </el-dialog>
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import {
  updateAccountManage,
  addUserRoles,
  updatePassword,
} from "@/api/basic-info";
import { getNoticeDetail } from "@/api/notice-notice/notice-list";

export default {
  name: "ModelView",
  directives: {
    waves,
  },
  data() {
    return {
        
      dialogEditInfo: false,
      saving: false,
      formItem: {
        noticeContent: "",
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
  methods: {
    /**
     * 控制编辑弹窗
     * @return {type} {description}
     */
    handEditInfoModel(data) {
        this.dialogEditInfo = true;

      getNoticeDetail(data.id).then((res) => {
          this.formItem = res.content;
      });
    },
  },
};
</script>

<style lang="scss">
.model-view {
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
}
</style>