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

      <el-tab-pane label="订单评价列表" name="listPane">
        <keep-alive>
          <list-pane ref="listPane" />
        </keep-alive>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
import listPane from "./components/list";

export default {
  name: "listTab",
  components: { 
    listPane
    },
  data() {
    return {
      activeName: "listPane",
    };
  },
  methods: {
    handTabClick(data) {
      if (data.name === "listPane") {
        this.$refs["listPane"].handleSearch();
      }
    },
  },

  mounted () {
        // this.handTabClick();
    }

};
</script>

<style scoped>
.tab-container {
  margin: 8px;
}
</style>
