/**
 * 学生信息视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="tab-container">
    <el-tabs
      v-model="activeName"
      style="margin-top: 15px"
      type="border-card"
      @tab-click="handTabClick">

      <el-tab-pane label="积分管理列表" name="listPane">

        <keep-alive>
          <list-pane ref="listPane" :integralTypes="integralTypes" />
        </keep-alive>

      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
import listPane from "./components/list";

import { getDictListBySdmCode } from "@/api/receipt-management/dict-manage"

export default {
  name: "listTab",
  components: { 
      listPane
    },
  data() {
    return {

        
        integralTypes: [],

        activeName: "listPane",
    }
  },
  methods: {
    handTabClick(data) {
      if (data.name === "listPane") {
        this.$refs["listPane"].handleSearch();
      }
    },

    getDictList(){
        let data = "integral_type";
        getDictListBySdmCode(data).then((res) => {
          this.integralTypes = res.content
        })

    }

  },

  mounted () {
        this.getDictList();
    }

};
</script>

<style scoped>
.tab-container {
  margin: 8px;
}
</style>
