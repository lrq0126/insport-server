/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
  <div class="cargo-list-model-view">
    <!--编辑题库信息 -->
    <el-dialog
      :visible.sync="dialogEditInfo"
      title="货物信息"
      class="dialog-box"
      width="80%"
    >
      <div>
        <!-- 搜索 -->
        <el-form ref="searchForm" :model="formItem" inline label-width="70px">
          <el-form-item label="货物名称">
            <el-input
              v-model="formItem.goodsName"
              placeholder="请输入货物名称"
              style="width: 200px"
              class="filter-item"
            />
          </el-form-item>

          <el-form-item label="快递单号/唛头" label-width="105px">
            <el-input
              v-model="formItem.deliveryOrderNos"
              placeholder="请输入您的快递单号"
              style="width: 200px"
              type="textarea"
              class="filter-item"
            />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="handSelectGoods">查 询</el-button>

            <el-button v-waves type="info" plain @click="dialogEditInfo = false"
              >取 消</el-button
            >
            <el-button v-waves type="primary" @click="handleSubmit()"
              >提 交</el-button
            >
          </el-form-item>
        </el-form>
      </div>

      <div>
        <!-- 表格 -->
        <el-table
          :data="tableData"
          border
          v-loading="loading"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            type="selection"
            align="center"
            fixed="left"
            width="55"
          ></el-table-column>

          <el-table-column
            type="index"
            label="序号"
            width="50"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="deliveryOrderNo"
            label="快递单号/唛头"
            min-width="160"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="customerName"
            label="客户"
            min-width="160"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="loginName"
            label="会员id"
            min-width="160"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="goodsName"
            label="货物名称"
            min-width="160"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="message"
            label="备注信息"
            min-width="120"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="inStorageTime"
            label="入库日期"
            min-width="160"
            align="center"
          ></el-table-column>

          <el-table-column label="仓库地址" min-width="160" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.location | filterLocation }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="packageType"
            label="包装类型"
            min-width="100"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="packageNum"
            label="货物件数"
            min-width="100"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="length"
            label="长度（CM）"
            min-width="100"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="width"
            label="宽度（CM）"
            min-width="100"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="height"
            label="高度（CM）"
            min-width="100"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="kg"
            label="重量（KG）"
            min-width="100"
            align="center"
          ></el-table-column>

          <el-table-column
            fixed="right"
            label="状态"
            min-width="120"
            align="center"
          >
            <template slot-scope="scope">
              <el-tag v-if="scope.row.goodsType == 1" type="success"
                >已入库</el-tag
              >
              <el-tag v-if="scope.row.goodsType == 2" type="info"
                >未入库</el-tag
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { getGoodsByCustomerId } from "@/api/pin-management/pin-main";
import { addGoodsInPack } from "@/api/package-management/unpackaged-task";
import Cookies from "js-cookie";

export default {
  name: "AddGoodsView",
  directives: {
    waves,
  },
  components: {},

  filters: {
    filterLocation(value) {
      if (value) {
        let location = value.split(",");
        if (location[0]) {
          return location[0] + "区" + location[1] + "排";
        } else {
          return "";
        }
      } else {
        return "";
      }
    },
  },

  data() {
    return {
      loading: false,
      saving: false,

      multipleSelection: [],
      formItem: {
        // 查询条件
        goodsName: "",
        deliveryOrderNo: "",
        deliveryOrderNos: "",

        // 默认条件
        customerId: "",
        goodsIds: "",
        packId: "",
      },

      tableData: [],
      dialogEditInfo: false,
    };
  },
  methods: {
    /**
     * 确认提交
     */
    handleSubmit() {
      let goodsIds = "";
      this.multipleSelection.forEach((goods) => {
          goodsIds = goodsIds == "" ? goods.id : goodsIds+","+goods.id;
      })
      let data = {
          id: this.formItem.packId,
          goodsIds: goodsIds,
          isPinPage: 1,
      }

      this.$emit("goodsList", this.multipleSelection);
            this.$nextTick(() => {
                this.dialogEditInfo = false;
            });
        
    //   addGoodsInPack(data).then((res) => {
    //       if(res.code == 100){
              
    //           this.$message({
    //             message: "包裹添加完成",
    //             type: "success",
    //             showClose: false,
    //             duration: 1000 * 1.5 * 2,
    //           });

            
    //       }else{
    //           this.$message({
    //             message: res.message,
    //             type: "error",
    //             showClose: false,
    //             duration: 1000 * 1.5 * 2,
    //           });
    //       }
    //   })
    },

    /**
     * 查询货物
     */
    handSelectGoods() {
      if (this.formItem.deliveryOrderNos) {
        this.formItem.deliveryOrderNos = this.formItem.deliveryOrderNos.replace(
          /^\s+|\s+$/gm,
          ""
        );
        let reg = new RegExp("\n", "g"); //g代表全部
        let newMsg = this.formItem.deliveryOrderNo.replace(reg, "|");
        this.formItem.deliveryOrderNo = newMsg;
      }

      getGoodsByCustomerId(this.formItem).then((res) => {
        this.tableData = res.content;
      });

    },

    /**
     * 控制弹窗
     * @return {type} {description}
     */
    handEditInfoModel(data) {
      // 重置查询条件
      this.handleReset();

      this.formItem = data;

      this.handSelectGoods();

      this.$nextTick(() => {
        this.dialogEditInfo = true;
      });
    },

    /**
     * 多选
     * @param  {type} val {description}
     * @return {type} {description}
     */
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset() {
        this.multipleSelection = [];
      this.formItem.goodsName = "";
      this.formItem.deliveryOrderNo = "";
      this.formItem.deliveryOrderNos = "";
      this.formItem.goodsIds = "";
      
    },
  },
};
</script>

<style lang="scss">
.cargo-list-model-view {
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