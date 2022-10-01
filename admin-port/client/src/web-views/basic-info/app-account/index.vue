/**
 * app帐号页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="tab-container">
    <!-- <el-tag>mounted times ：{{ createdTimes }}</el-tag> -->
    <!-- <el-alert :closable="false" style="width:200px;display:inline-block;vertical-align: middle;margin-left:30px;" title="Tab with keep-alive" type="success" /> -->
    <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane label="app帐号列表" name="list">
        <keep-alive>
          <!-- <tab-pane v-if="activeName==item.key" :type="item.key" @create="showCreatedTimes" /> -->
          <list-pane/>
        </keep-alive>
      </el-tab-pane>

      <el-tab-pane label="app帐号添加" name="create">
        <keep-alive>
          <!-- <tab-pane v-if="activeName==item.key" :type="item.key" @create="showCreatedTimes" /> -->
          <create-pane/>
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
// import tabPane from './components/TabPane'
import listPane from "./components/list";
import createPane from "./components/create";

export default {
  name: "Tab",
  components: { listPane, createPane },
  data() {
    return {
      tabMapOptions: [
        { label: "app帐号列表", key: "CN" },
        { label: "app帐号添加", key: "US" }
      ],
      activeName: "list",
      createdTimes: 0
    };
  },
  methods: {
    showCreatedTimes() {
      this.createdTimes = this.createdTimes + 1;
    }
  }
};
</script>

<style scoped>
.tab-container {
  margin: 8px;
}
</style>
