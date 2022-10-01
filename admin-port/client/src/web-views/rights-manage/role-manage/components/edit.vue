/**
 * 角色修改
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
              <el-form-item label="归属机构：" prop="column">
                <tree-select></tree-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="角色名称：" prop="column">
                <el-input placeholder="请输入角色名称"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="角色说明：" prop="column">
                <el-input placeholder="请输入角色说明"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="数据范围：" prop="column">
                <el-input placeholder="请输入数据范围"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="备注：" prop="column">
                <el-input type="textarea" :rows="2" placeholder="请输入内容"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="20">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="角色授权：" prop="column">
                <el-tree
                  :props="defaultProps"
                  :data="data"
                  show-checkbox
                  @check-change="handleCheckChange"
                ></el-tree>
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
      test: "asd",
      radio: "1",
      data: [
        {
          id: 1,
          label: "后台管理",
          children: [
            {
              id: 11,
              label: "工作台",
              children: []
            },
            {
              id: 12,
              label: "权限管理",
              children: [
                {
                  id: 121,
                  label: "机构类型",
                  children: []
                },
                {
                  id: 122,
                  label: "机构管理",
                  children: []
                },
                {
                  id: 123,
                  label: "资源管理",
                  children: []
                },
                {
                  id: 124,
                  label: "角色管理",
                  children: []
                }
              ]
            },
            {
              id: 13,
              label: "基础信息"
            }
          ]
        },
        {
          id: 2,
          label: "安全管理",
          children: [
            {
              id: 21,
              label: "校车管理"
            },
            {
              id: 22,
              label: "事故隐患管理"
            }
          ]
        },
        {
          id: 3,
          label: "安全教育",
          children: [
            {
              id: 31,
              label: "试题库"
            },
            {
              id: 32,
              label: "教学管理"
            }
          ]
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      }
    };
  },
  created() {
    this.tempRoute = Object.assign({}, this.$route);
    this.setTagsViewTitle();
  },
  methods: {
    setTagsViewTitle() {
      const title = "角色编辑";
      const route = Object.assign({}, this.tempRoute, { title: `${title}` });
      this.$store.dispatch("tagsView/updateVisitedView", route);
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
    handleNodeClick(data) {
      console.log(data);
    }
  }
};
</script>

<style>
</style>
