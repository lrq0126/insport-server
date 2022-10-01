<template>
  <div>
    <el-dialog
      title="Excel文本输入"
      :visible.sync="dialog"
      width="90%"
      :close-on-click-modal="false"
    >
      <el-form :model="formItem" ref="formItem">
        <el-form-item>
          <el-input
            v-model="formItem.textArea"
            rows="12"
            type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="CopyExcelToTable">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,

      title: ["price", "ginsengPrice", "claimsDetail"],

      formItem: {
        textArea: "",
      },
    };
  },

  methods: {
    CopyExcelToTable() {
      let resultData = []; // 需要返回的列表数据
      let source = this.formItem.textArea;

      // 首先对源头进行解析
      let rows = source.split("\n"); // 拆成很多行
      for (let i = 0; i < rows.length; i++) {
        if (rows[i] != "") {
          // 如果某一行不是空，再按列拆分
          let columns = rows[i].split("\t"); // 已经按列划分
          let dataone = {}; // 声明一行数组
          for (let j = 0; j < columns.length; j++) {
            // 读取demo里的第j对应的key值
            // let keys = ; // key的名
            dataone[this.title[j]] = columns[j];
          }
          resultData.push(dataone);
        }
      }

      // console.log("转换后的数据：", resultData);
      let numreg = /^-?\d{1,16}(?:\.\d{1,4})?$/;
      let numRe = new RegExp(numreg);
      let change = true;
      resultData.forEach((ele) => {
        if (!numRe.test(ele.price)) {
          this.$message.error("存在不正确的保险价格数据,请检查");
          change = false;
        }

        if (!numRe.test(ele.ginsengPrice)) {
          this.$message.error("存在不正确的参保价格数据,请检查");
          change = false;
        }
      });

      // 关闭显示框，情况text表的数据
      if (change) {
        this.$emit("updatePriceList", resultData);
        this.dialog = false;
        this.formItem.textArea = "";
      }
    },

    openDialog() {
      this.dialog = true;
      this.formItem.textArea = "";
    },
  },
};
</script>
