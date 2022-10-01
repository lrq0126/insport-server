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
      @tab-click="handTabClick"
    >
      <el-tab-pane label="活动奖励设置" name="listPane">
        <keep-alive>
          <list-pane ref="listPane" :integralTypes="integralTypes" :activityTypes="activityTypes" />
        </keep-alive>
      </el-tab-pane>

      <el-tab-pane label="海报设置" name="posterListPane">
        <keep-alive>
          <poster-list-pane ref="posterListPane" :posterTypes="posterTypes"/>
        </keep-alive>
      </el-tab-pane>

      <el-tab-pane label="邮件记录" name="mailModelListPane">
        <keep-alive>
          <mail-model-list-pane ref="mailModelListPane"/>
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getDictListBySdmCode } from "@/api/receipt-management/dict-manage";
import listPane from "./components/list";
import posterListPane from "./components/poster-list";
import MailModelListPane from './components/mail-model-list';
export default {
  name: "listTab",
  components: {
    listPane,
    posterListPane,
    MailModelListPane
  },
  data() {
    return {
      integralTypes: [],
      activityTypes: [],
      posterTypes: [],
      activeName: "listPane",
    };
  },
  methods: {
    handTabClick(data) {
      if (data.name === "listPane") {
        this.$refs["listPane"].handleSearch();
      }else if(data.name === "posterListPane"){
        this.$refs["posterListPane"].handleSearch();
      }else if(data.name === "mailModelListPane"){
        this.$refs["mailModelListPane"].handleSearch();
      }
    },

    getActivityType() {
      getDictListBySdmCode("activity_type").then((res) => {
        this.activityTypes = res.content;
      });
    },

    getIntegralTypes(){
      getDictListBySdmCode("integral_type").then((res) => {
        this.integralTypes = res.content;
      });
    },

    getPosterTypes(){
      getDictListBySdmCode("poster_type").then((res) => {
        this.posterTypes = res.content;
      });
    },

  },

  mounted() {
    this.getActivityType();
    this.getIntegralTypes();
    this.getPosterTypes();
  },
};
</script>

<style scoped>
.tab-container {
  margin: 8px;
}
</style>
