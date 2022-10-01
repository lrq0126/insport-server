/**
 * 菜单修改
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <el-form ref="ruleForm" label-width="200px" class="demo-ruleForm">
      <el-row style="margin-top: 50px;">
        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="归属菜单：" prop="column">
                <tree-select></tree-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="名称：" prop="column">
                <el-input placeholder="请输入名称"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="图标：" prop="column">
                <el-popover placement="right" width="800" trigger="click">
                  <icons></icons>
                  <el-button slot="reference">选择</el-button>
                </el-popover>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="系统标识：" prop="column">
                <el-input placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="备注：" prop="column">
                <el-input type="textarea" placeholder="请输入备注"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row>
            <el-col :span="20">
              <el-form-item>
                <el-button type="primary">保存</el-button>
                <el-button>重置</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import TreeSelect from "@/components/TreeSelect/tree-select";
import Icons from "@/views/icons/index";
export default {
  name: "Edit",
  components: { TreeSelect, Icons },
  data() {
    return {
      ruleForm: [],
      test: "asd"
    };
  },
  created() {
    this.tempRoute = Object.assign({}, this.$route);
    this.setTagsViewTitle();
  },
  methods: {
    setTagsViewTitle() {
      const title = "菜单编辑";
      const route = Object.assign({}, this.tempRoute, { title: `${title}` });
      this.$store.dispatch("tagsView/updateVisitedView", route);
    }
  }
};
</script>

<style>
</style>
