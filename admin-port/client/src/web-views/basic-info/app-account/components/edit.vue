/**
 * 机构类型修改
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <el-form ref="ruleForm" label-width="200px" class="demo-ruleForm">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="上级归属机构">
            <tree-select></tree-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="归属机构">
            <tree-select></tree-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="登陆账号">
            <el-input></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="登陆人员">
            <tree-select></tree-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="登陆密码">
            <el-input></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="确认密码">
            <el-input></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="登陆状态">
            <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item>
            <el-button type="primary">保存</el-button>
            <el-button>重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import TreeSelect from "@/components/TreeSelect/tree-select";
export default {
  name: "Edit",
  components: { TreeSelect },
  data() {
    return {
      ruleForm: []
    };
  },
  created() {
    this.tempRoute = Object.assign({}, this.$route);
    this.setTagsViewTitle();
  },
  methods: {
    setTagsViewTitle() {
      const title = "区域编辑";
      const route = Object.assign({}, this.tempRoute, { title: `${title}` });
      this.$store.dispatch("tagsView/updateVisitedView", route);
    }
  }
};
</script>

<style scoped>
.tab-container {
  margin: 8px;
}
</style>
