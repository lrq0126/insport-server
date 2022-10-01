<template>
    <el-select :value="valueId"
               :clearable="clearable"
               @clear="clearHandle">
        <!-- <el-input placeholder="输入关键字进行过滤"
                  v-model="filterText">
        </el-input> -->
        <el-option :value="valueId"
                   :label="valueTitle">
            <el-tree id="tree-option"
                     ref="selectTree"
                     :accordion="accordion"
                     :data="treeData"
                     :props="props"
                     default-expand-all
                     :node-key="props.value"
                     :default-expanded-keys="defaultExpandedKey"
                     @node-click="handleNodeClick"></el-tree>
        </el-option>
    </el-select>
</template>

<script>
import { getCascadeData } from '@/api/safetyEducation/column/index'
export default {
    data () {
        return {
            filterText: "",
            valueId: this.value, // 初始值
            valueTitle: "",
            defaultExpandedKey: [],
            data: this.options
        };
    },
    watch: {
        filterText (val) {
            this.$refs.selectTree.filter(val);
        },
        value () {
            this.valueId = this.value;
            this.initHandle();
        }
    },
    props: {
        /* 配置项 */
        props: {
            type: Object,
            default: () => {
                return {
                    value: "parentId", // ID字段名
                    label: "name", // 显示名称
                    children: "children" // 子级字段名
                };
            }
        },
        /* 选项列表数据(树形结构的对象数组) */
        options: {
            type: Array
        },
        /* 初始值 */
        value: {
            type: String,
            default: () => {
                return '';
            }
        },
        /* 可清空选项 */
        clearable: {
            type: Boolean,
            default: () => {
                return true;
            }
        },
        /* 自动收起 */
        accordion: {
            type: Boolean,
            default: () => {
                return false;
            }
        },
        item: {
            type: String,
            default: () => {
                return "";
            }
        }
    },
    computed: {
        treeData () {
            let cloneData = JSON.parse(JSON.stringify(this.options)); // 对源数据深度克隆
            return cloneData.filter(father => {
                let branchArr = cloneData.filter(child => father.id == child.parentId); //返回每一项的子级数组
                branchArr.length > 0 ? (father.children = branchArr) : ""; //如果存在子级，则给父级添加一个children属性，并赋值
                return father.parentId == '1'; //返回第一层
            })
        }
    },
    methods: {
        // 初始化值
        initHandle () {
            if (this.valueId) {
                if (this.$refs.selectTree.getNode(this.valueId)) {
                    this.valueTitle = this.$refs.selectTree.getNode(this.valueId).data[this.props.label]; // 初始化显示
                } else {
                    this.valueTitle = '无'
                }
                this.$refs.selectTree.setCurrentKey(this.valueId); // 设置默认选中
                this.defaultExpandedKey = [this.valueId]; // 设置默认展开
            }
            this.$nextTick(() => {
                let scrollWrap = document.querySelectorAll(
                    ".el-scrollbar .el-select-dropdown__wrap"
                )[0];
                let scrollBar = document.querySelectorAll(
                    ".el-scrollbar .el-scrollbar__bar"
                );
                scrollWrap.style.cssText =
                    "margin: 0px; max-height: none; overflow: hidden;";
                scrollBar.forEach(ele => (ele.style.width = 0));
            });
        },
        // 切换选项
        handleNodeClick (node) {
            this.valueTitle = node[this.props.label];
            this.valueId = node[this.props.value];
            this.$emit("getValue", this.valueId);
            this.defaultExpandedKey = [];
        },
        // 清除选中
        clearHandle () {
            this.valueTitle = "";
            this.valueId = null;
            this.defaultExpandedKey = [];
            this.clearSelected();
            this.$emit("getValue", null);
        },
        /* 清空选中样式 */
        clearSelected () {
            let allNode = document.querySelectorAll("#tree-option .el-tree-node");
            allNode.forEach(element => element.classList.remove("is-current"));
        }
    },
    mounted () {
        this.initHandle();
    },
};
</script>

<style>
.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
    height: auto;
    max-height: 274px;
    /* padding: 0 20px; */
    overflow: hidden;
    overflow-y: auto;
}
.el-select-dropdown__item.selected {
    font-weight: normal;
}
ul li >>> .el-tree .el-tree-node__content {
    height: auto;
    padding: 0 20px;
}
.el-tree-node__label {
    font-weight: normal;
}
.el-tree >>> .is-current .el-tree-node__label {
    color: #409eff;
    font-weight: 700;
}
.el-tree >>> .is-current .el-tree-node__children .el-tree-node__label {
    color: #606266;
    font-weight: normal;
}
</style>
