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

      <el-tab-pane label="图表统计" name="pictureMarkerPane">
        <keep-alive>
          <picture-marker-pane ref="pictureMarkerPane" />
        </keep-alive>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
import pictureMarkerPane from "./components/picture-marker";

export default {
  name: "listTab",
  components: { 
    pictureMarkerPane 
    },
  data() {
    return {
      activeName: "pictureMarkerPane",
    };
  },
  methods: {
    handTabClick(data) {
     if(data.name === "pictureMarkerPane"){
        this.$refs["pictureMarkerPane"].getStatisticalChart();
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
