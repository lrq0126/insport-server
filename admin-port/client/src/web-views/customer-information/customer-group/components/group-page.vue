<template>
  <div class="coupons-detail-view">
    <el-dialog title="分组" 
      :visible.sync="editDialog" 
      width="40%" :close-on-click-modal=false
      >
      <el-form :model="formItem" :rules="formItemRules" ref="formItem">
        <!-- style="float: left; width: 180px" -->
        <el-form-item label="分组名称：" prop="groupName">
          <el-input
            v-model="formItem.groupName"
            placeholder="请输入分组名称："
            style="width: 300px"
          ></el-input>
        </el-form-item>

        <el-form-item label="备注：">
          <el-input
            v-model="formItem.remarks"
            placeholder="请输入备注："
            style="width: 300px"
            type="textarea"
          ></el-input>
        </el-form-item>

        <el-button @click="submit" type="primary">
          <span v-if="formItem.id == ''">提 交</span>
          <span v-else>修 改</span>
        </el-button>

        <el-button @click="editDialog = false" type="info">取 消</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  addGroup,
  editGroup
} from "@/api/customer-information/customer-group";

export default {
  components: {},

  data() {
    return {
      editDialog: false,

      formItem: {
        id: "",
        groupName: "",
        remarks: "",
      },

      formItemRules: {
        groupName: [
          { required: true, message: "分组名称不能为空", trigger: "blur" },
        ],

      },
    };
  },

  methods: {
    submit() {
      if (this.formItem.id == '') {
        addGroup(this.formItem).then((res) => {
          if (res.code == 100) {
            this.$message({
              type: "success",
              message: "新增分组成功",
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });

            this.$emit('updateList')
            
            this.editDialog = false;

          }else{
            this.$message({
              type: "error",
              message: res.message,
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });
          }
        });
      } else {
        editGroup(this.formItem).then((res) => {
          if (res.code == 100) {
            this.$message({
              type: "success",
              message: "修改分组成功",
              showClose: true,
              duration: 1000 * 1.5 * 3,
            });

            this.$emit('updateList')

            this.editDialog = false;
          }else{
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

    handCreateModel() {
      this.resetForm();
      this.editDialog = true;
    },

    handEditModel(data) {
      this.resetForm();
      this.formItem = data;
      this.editDialog = true;
    },


    // 重置表单内容
    resetForm(){
      this.formItem = {
        id: "",
        groupName: "",
        remarks: "",
      }
    }
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
