/**
 * 添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <el-dialog
      :visible.sync="deliveryInfoDialog"
      width="70%"
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="formItem"
        :rules="formItemRules"
        label-width="200px"
      >
        <el-row style="margin-top: 50px">
          <el-col :span="20">
            <el-form-item label="任务名称：" prop="taskName" >
              <el-input v-model="formItem.taskName" :readonly="formItem.taskStatus == 2"/>
            </el-form-item>
          </el-col>

          <!-- <el-col :span="20">
            <el-form-item label="任务编码：" prop="taskOrder">
                <el-input v-model="formItem.taskOrder" readonly/>
            </el-form-item>
          </el-col> -->

          <el-col :span="20">
            <el-form-item label="任务数量：" prop="taskNumber" >
              <el-input v-model="formItem.taskNumber" readonly />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-row style="margin-top: 50px">
        <el-col :span="20" style="margin-left: 20px">
          <el-divider content-position="left">
            <i class="el-icon-mobile-phone"></i>订单明细</el-divider
          >

          <el-button 
          type="primary" 
          :disabled="formItem.taskStatus == 2"
          @click="addTaskDetail()"
            >添加订单</el-button
          >
          <el-button type="warning" @click="openExcelModel()" :disabled="formItem.taskStatus == 2"
            >Excel文本导入</el-button
          >
        </el-col>
        <!-- <el-button @click="handleReset('form')">重 置</el-button> -->
        <el-col :span="20" style="margin-top: 10px">
          <el-table border :data="taskDetail">
            <el-table-column align="center" type="index" label="序号" />
            <el-table-column align="center" label="订单号">
              <template slot-scope="scope">
                <el-input
                  placeholder="请输入订单号"
                  v-model="scope.row.orderNumber"
                  :readonly="formItem.taskStatus == 2"
                />
              </template>
            </el-table-column>
            <el-table-column align="center" label="数量">
              <template slot-scope="scope">
                <el-input
                  type="number"
                  placeholder="请输入包裹数量"
                  v-model="scope.row.packNumber"
                  :readonly="formItem.taskStatus == 2"
                />
              </template>
            </el-table-column>
            <el-table-column align="center" label="渠道名称">
              <template slot-scope="scope">
                <el-input
                  type="routeName"
                  placeholder="请输入包裹渠道名称"
                  v-model="scope.row.routeName"
                  :readonly="formItem.taskStatus == 2"
                />
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  size="mini"
                  :disabled="formItem.taskStatus == 2"
                  @click="deleteDetail(scope.$index)"
                  >删 除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>

      <el-row style="margin-top: 50px">
        <el-col :span="20" style="margin-left: 25%; margin-right: 25%">
          <el-button
            :loading="saving"
            type="primary"
            @click="handleSubmit('form')"
            :disabled="formItem.taskStatus == 2"
            >保 存</el-button
          >
          <el-button
            type="info"
            @click="deliveryInfoDialog = !deliveryInfoDialog"
            >关 闭</el-button
          >
          <el-button v-if="isCreate" @click="handleReset('form')"
            >重 置</el-button
          >
        </el-col>
      </el-row>
    </el-dialog>

    <excel-model-view
      ref="excelModelView"
      @updateDetailList="updateDetailList"
    />
  </div>
</template>

<script>
import {
  getDeliveryTaskById,
  saveDeliveryTask,
} from "@/api/package-management/delivery-task";
import ExcelModelView from "./excel-model";
import { type } from "jquery";
export default {
  name: "DeilveryInfoView",
  components: {
    ExcelModelView,
  },
  data() {
    return {
      deliveryInfoDialog: false,
      saving: false,
      isCreate: true,
      formItem: {
        id: "",
        taskOrder: "",
        taskNumber: "",
      },
      taskDetail: [],

      formItemRules: {
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" },
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
    handleSubmit() {
      let isSuccess = true;
      this.taskDetail.forEach((ele) => {
        if (!ele.packNumber) {
          isSuccess = false;
          this.$message({
            type: "error",
            message: "订单明细中存在不正确的数量内容，请修改",
          });
        }
      });
      if (isSuccess) {
        this.formItem.deliveryTaskDetails = JSON.stringify(this.taskDetail);
        this.saving = true;
        saveDeliveryTask(this.formItem)
          .then((res) => {
            if (res.code === 100) {
              this.$message({
                message: "保存成功",
                type: "success",
                showClose: true,
                duration: 1000 * 1.5,
              });
              setTimeout(() => {
                this.handleReset();
                this.deliveryInfoDialog = false;
                this.$emit("reSelect");
              }, 500);
            }
          })
          .finally(() => {
            this.saving = false;
          });
      }
    },

    addTaskDetail() {
      let detail = {
        deliveryTaskId: this.formItem.id,
        orderNumber: "",
        packNumber: 1,
      };
      this.taskDetail.push(detail);
      this.formItem.taskNumber = this.taskDetail.length;
    },

    deleteDetail(index) {
      if (index > -1) {
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.taskDetail.splice(index, 1);
          this.formItem.taskNumber = this.taskDetail.length;
        });
      }
    },

    openCreateTask() {
      this.isCreate = true;
      this.deliveryInfoDialog = true;
      this.handleReset();
      this.formItem.taskNumber = this.taskDetail.length;
    },

    openEditTask(id) {
      getDeliveryTaskById({ id }).then((res) => {
        if (res.code == 100) {
          this.formItem = res.content;
          this.taskDetail = res.content.deliveryTaskDetails;
        }
      });
      this.isCreate = false;
      this.deliveryInfoDialog = true;
    },

    openExcelModel() {
      this.$refs["excelModelView"].openDialog();
    },

    updateDetailList(data) {
      this.$message({
        message: "Excel转换成功",
        type: "success",
      });

      this.taskDetail = data;
      this.formItem.taskNumber = this.taskDetail.length;
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset() {
      this.formItem = {
        id: "",
        taskOrder: "",
        taskNumber: "",
      };
      this.taskDetail = [
        {
          orderNumber: "",
          packNumber: 1,
        },
      ];
    },
  },
};
</script>

<style lang="scss" scope>
.student-management-create {
  .quill-editor {
    .ql-container {
      height: 400px;
    }
  }
}
</style>
