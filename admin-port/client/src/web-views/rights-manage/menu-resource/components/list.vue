/**
 * 菜单列表展示
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="menu-resource">
        <el-col :span="7">
            <tree-table style="max-height:700px;overflow: auto"
                        expand-key="menuName"
                        @radio-click="rowClick"
                        :expand-type="false"
                        :is-fold="false"
                        :tree-type="true"
                        :selectable="false"
                        :columns="columns"
                        :data="data">
                <template slot="status"
                          slot-scope="scope">
                    <el-badge v-if="scope.row.status === 1"
                              is-dot
                              class="item"
                              type="success"></el-badge>
                    <el-badge v-else
                              is-dot
                              class="item"></el-badge>
                    <svg-icon icon-class="documentation" />
                    <!-- <Icon :type="scope.row.icon"
                          size="16" /> -->
                </template>
            </tree-table>
        </el-col>
        <el-col :span="8"
                style="margin-left: 20px;">
            <div class="search-con search-con-top"
                 style="matgin-bottom: 20px;text-align: center;">
                <el-button-group>
                    <el-button type="primary"
                               :disabled="hasAuthority('systemMenuEdit')?false:true"
                               @click="setEnabled(true)">添加</el-button>
                    <el-button type="primary"
                               :disabled="formItem.menuId && hasAuthority('systemMenuEdit')?false:true"
                               @click="setEnabled(false)">编辑</el-button>
                    <el-button type="primary"
                               :disabled="formItem.menuId && hasAuthority('systemMenuEdit')?false:true"
                               @click="confirmModal = true">删除</el-button>
                </el-button-group>
                <el-dialog :visible.sync="confirmModal"
                           title="提示"
                           @on-ok="handleRemove"
                           width="20%">
                    确定删除,菜单资源【{{formItem.menuName}}】吗?{{formItem.children && formItem.children.length > 0 ?
              '存在子菜单,将一起删除.是否继续?' : ''}}
                    <span slot="footer"
                          class="dialog-footer">
                        <el-button @click="confirmModal = false">取 消</el-button>
                        <el-button type="primary"
                                   @click="handleRemove">确 定</el-button>
                    </span>
                </el-dialog>
            </div>
            <el-form ref="menuForm"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="80px">
                <el-form-item label="上级菜单"
                              prop="parentId">
                    <treeselect :disabled="disabled"
                                v-model="formItem.parentId"
                                :options="selectTreeData"
                                :default-expand-level="1"
                                :normalizer="treeSelectNormalizer" />
                </el-form-item>
                <el-form-item label="菜单标识"
                              prop="menuCode">
                    <el-input :disabled="disabled"
                              v-model="formItem.menuCode"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="菜单名称"
                              prop="menuName">
                    <el-input :disabled="disabled"
                              v-model="formItem.menuName"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="页面地址"
                              prop="path">
                    <el-input :disabled="disabled"
                              v-model="formItem.path"
                              placeholder="请输入内容">
                        <el-select :disabled="disabled"
                                   v-model="formItem.scheme"
                                   slot="prepend"
                                   style="width: 80px">
                            <el-option value="/">/</el-option>
                            <el-option value="http://">http://</el-option>
                            <el-option value="https://">https://</el-option>
                        </el-select>
                        <el-select :disabled="disabled"
                                   v-model="formItem.target"
                                   slot="append"
                                   style="width: 120px">
                            <el-option label="窗口内打开"
                                       value="_self"></el-option>
                            <el-option label="新窗口打开"
                                       value="_blank"></el-option>
                        </el-select>
                    </el-input>
                    <span>前端组件所在位置：/view/module/{{formItem.path}}.vue</span>
                </el-form-item>
                <!-- <el-form-item label="图标">
                    <el-input :disabled="disabled"
                              v-model="formItem.icon"
                              placeholder="请输入内容">
                        <Icon size="16"
                              :type="formItem.icon"
                              slot="prepend" />
                        <Poptip width="600"
                                slot="append"
                                placement="left">
                            <Button :disabled="disabled"
                                    icon="ios-search"></Button>
                            <div slot="content">
                                <ul class="icons">
                                    <li class="icons-item"
                                        :title="item"
                                        @click="onIconClick(item)"
                                        v-for="item in selectIcons">
                                        <Icon :type="item"
                                              size="28" />
                                        <p>{{item}}</p>
                                    </li>
                                </ul>
                            </div>
                        </Poptip>
                    </el-input>
                </el-form-item> -->
                <el-form-item label="优先级">
                    <el-input-number :disabled="disabled"
                                     v-model="formItem.priority"></el-input-number>
                </el-form-item>
                <el-form-item label="状态">
                    <el-radio-group v-model="formItem.status">
                        <el-radio :disabled="disabled"
                                  label="0">禁用</el-radio>
                        <el-radio :disabled="disabled"
                                  label="1">启用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input :disabled="disabled"
                              v-model="formItem.menuDesc"
                              type="textarea"
                              placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item style="text-align: center;">
                    <el-button :disabled="disabled"
                               @click="handleSubmit"
                               :loading="saving"
                               type="primary">保存</el-button>
                    <el-button :disabled="disabled"
                               @click="setEnabled(true)"
                               style="margin-left: 8px">重置</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <el-col :span="8"
                style="margin-left: 20px;">
            <menu-action :value="formItem"></menu-action>
        </el-col>
    </div>
</template>

<script>
let id = 1000;
import { getMenus, addMenu, updateMenu, removeMenu } from '@/api/permissi';
import { listConvertTree } from '@/utils/public'
import MenuAction from './menu-action';

export default {
    components: {
        MenuAction
    },
    data () {
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('菜单标识不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            columns: [
                {
                    title: '菜单名称',
                    key: 'menuName',
                    minWidth: '200px'
                },
                {
                    title: '状态',
                    key: 'status',
                    type: 'template',
                    minWidth: '100px',
                    template: 'status'
                },
            ],
            data: [],
            tableData: [{
                id: 3,
                name: '工作台管理',
                hasChildren: true,
                children: [{
                    id: 31,
                    name: '工作台窗口',
                    hasChildren: true
                }, {
                    id: 32,
                    name: '工作台设置',
                    hasChildren: true
                }, {
                    id: 33,
                    name: '工作台选项',
                    hasChildren: true
                }]
            }, {
                id: 4,
                name: '权限管理',
                children: [{
                    id: 41,
                    name: '资源管理',
                }, {
                    id: 42,
                    name: '角色管理',
                }, {
                    id: 43,
                    name: '机构类型',
                }, {
                    id: 44,
                    name: '机构管理',
                }]
            }],
            confirmModal: false,
            disabled: true,
            saving: false,
            visible: false,
            // selectIcons: icons,
            selectTreeData: [{
                menuId: 0,
                menuName: '无'
            }],
            formItem: {
                menuId: '',
                menuCode: '',
                menuName: '',
                icon: 'md-document',
                path: '',
                scheme: '/',
                target: '_self',
                status: 1,
                parentId: '0',
                priority: 0,
                menuDesc: ''
            },
            formItemRules: {
                parentId: [
                    { required: true, message: '上级菜单', trigger: 'blur' }
                ],
                menuCode: [
                    { required: true, validator: validateEn, trigger: 'blur' }
                ],
                menuName: [
                    { required: true, message: '菜单名称不能为空', trigger: 'blur' }
                ]
            },
            multipleSelection: [],
            listLoading: false,
        };
    },
    created () {
    },
    methods: {
        /**
         * 获取菜单资源数据
         * @return {type} {description}
         */
        handGetMenus () {
            let opt = {
                primaryKey: 'menuId',
                parentKey: 'parentId',
                startPid: '0'
            }
            getMenus().then(response => {
                this.data = listConvertTree(response.data, opt);
                this.setSelectTree(this.data);
                // this.data = response.data;
                setTimeout(() => {
                    this.listLoading = false;
                }, 1000);
            })
        },
        treeSelectNormalizer (node) {
            return {
                id: node.menuId,
                label: node.menuName,
                children: node.children
            }
        },

        setSelectTree (data) {
            this.selectTreeData.push(...data);
        },

        rowClick (data) {
            this.disabled = true
            this.handleReset()
            if (data) {
                this.formItem = Object.assign({}, data.row)
            }
            this.formItem.status = this.formItem.status + ''
        },

        /**
         * 操作
         * @param  {type} enabled {description}
         * @return {type} {description}
         */
        setEnabled (enabled) {
            if (enabled) {
                this.handleReset()
            }
            this.disabled = false
        },

        /**
         * 重置
         * @return {type} {description}
         */
        handleReset () {
            const newData = {
                menuId: '',
                menuCode: '',
                menuName: '',
                icon: 'md-document',
                path: '',
                scheme: '/',
                target: '_self',
                status: '1',
                parentId: '0',
                priority: 0,
                menuDesc: ''
            }
            this.formItem = newData
            this.$refs['menuForm'].resetFields()
            this.saving = false
        },

        /**
         * 保存
         * @return {type} {description}
         */
        handleSubmit () {
            this.$refs['menuForm'].validate((valid) => {
                if (valid) {
                    this.saving = true
                    if (this.formItem.menuId) {
                        updateMenu(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.disabled = true
                                this.$message({
                                    message: "保存成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                            }
                            this.handGetMenus()
                        }).finally(() => {
                            this.saving = false
                        })
                    } else {
                        addMenu(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.disabled = true
                                this.$message({
                                    message: "保存成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5
                                });
                            }
                            this.handGetMenus()
                        }).finally(() => {
                            this.saving = false
                        })
                    }
                }
            })
        },

        /**
         * 移除
         * @return {type} {description}
         */
        handleRemove () {
            removeMenu(this.formItem.menuId).then(res => {
                this.handleReset()
                this.handGetMenus()
                if (res.code === 0) {
                    this.confirmModal = false;
                    this.$message({
                        message: "删除成功",
                        type: "success",
                        showClose: true,
                        duration: 1000 * 1.5
                    });
                }
            })
        },

        /**
         * 图标
         * @param  {type} item {description}
         * @return {type} {description}
         */
        onIconClick (item) {
            this.formItem.icon = item
        },



        handleSelectionChange (val) {
            this.multipleSelection = val;
        },
        deleteItem () {
            if (this.multipleSelection.length > 1) {
                this.$message({
                    message: "请选择单个操作！",
                    type: "success"
                });
            } else {
                let id = this.multipleSelection[0].id;
                this.deleteobj(this.tableData, id);
                var dd = this.tableData;
                console.log(dd);
                this.tableData = [];
                this.tableData = dd;
                this.$message({
                    message: "删除成功！",
                    type: "success"
                });
            }
        },
        deleteobj (arr, id) {
            if (arr != undefined) {
                for (let a = 0; a < arr.length; a++) {
                    if (arr[a].id == id) {
                        console.log("ok");
                        arr.splice(a, 1);
                        console.log(this.tableData);
                        return;
                    }

                    this.deleteobj(arr[a].children, id);
                }
            }
        },
    },
    mounted () {
        this.handGetMenus();
    }
};
</script>

<style lang="scss">
.menu-resource {
    .zk-table__cell-inner {
        display: flex;
        align-items: center;
    }
    .el-badge__content.is-dot {
        right: 5px !important;
        top: 0 !important;
    }
    .svg-icon {
        width: 1.2em;
        height: 1.2em;
    }
}
</style>
<style lang="scss" scoped>
.menu-resource {
    .search-con-top {
        margin-bottom: 20px;
    }
}
</style>



