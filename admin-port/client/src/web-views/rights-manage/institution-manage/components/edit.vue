/**
 * 机构修改
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <el-form ref="ruleForm"
                 label-width="200px"
                 class="demo-ruleForm">
            <el-row style="margin-top: 50px;">
                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="所属机构："
                                          prop="column">
                                <tree-select :item="test" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="主负责人："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="所属区域*："
                                          prop="column">
                                <tree-select :item="test" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="副负责人："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="机构类型*："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="电话："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="机构名称*："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="邮箱："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="机构编码："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="联系地址："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="备注："
                                          prop="column">
                                <el-input placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row type="flex"
                            justify="end">
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
export default {
    name: "Edit",
    components: { TreeSelect },
    data () {
        return {
            ruleForm: [],
            test: "asd"
        };
    },
    created () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
    },
    methods: {
        setTagsViewTitle () {
            const title = "机构编辑";
            const route = Object.assign({}, this.tempRoute, { title: `${title}` });
            this.$store.dispatch("tagsView/updateVisitedView", route);
        }
    }
};
</script>

<style>
</style>
