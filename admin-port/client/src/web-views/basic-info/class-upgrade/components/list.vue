/**
 * 升班列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="app-container class-upgrade">
        <div class="operation-box">

            <el-button class="filter-item"
                       size="mini"
                       type="primary"
                       @click="hadnleOneKeyUpgradeClass">一键升级所有班级</el-button>
        </div>
        <!-- 卡片模块 -->
        <div style="display: flex;justify-content: space-between;flex-flow: row wrap;">
            <div v-for="(item, index) in classData"
                 :key="index"
                 style="width: 32.5%;margin: 5px 0;">
                <el-card class="box-card">
                    <div slot="header"
                         class="clearfix"
                         style="display: flex;justify-content: space-between;">
                        <span>{{ item.name }}</span>
                        <!-- <el-button style="float: right;"
                                   class="filter-item"
                                   size="mini"
                                   type="primary"
                                   plain
                                   @click="handleUpgradeClass">升班</el-button> -->
                        <el-radio-group v-model="item.check">
                            <el-radio label="0">普通班</el-radio>
                            <el-radio label="1">毕业班</el-radio>
                        </el-radio-group>
                        <span>{{ item.value }}</span>
                        <span>{{ item.check }}</span>
                    </div>
                    <div style="display: flex;justify-content: flex-start;flex-flow: row wrap;">
                        <el-tag type="success"
                                effect="plain"
                                v-for="(res, keys) in item.children"
                                :key="keys"> {{ res.name }}</el-tag>
                    </div>
                </el-card>
            </div>
        </div>

        <!-- 弹窗信息 -->
        <model-view ref="modelView"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import ModelView from './model';
import { getClassUpgrade, upgradeClassUpgrade } from '@/api/basic-info'


export default {
    components: {
        ModelView
    },
    props: {
        gradeTypes: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        gradeTypes () {
            this.gradeType = this.gradeTypes
        }
    },
    filters: {
        filterClassLevel (array, value) {
            let name = ''
            // array.forEach(ele => {
            if (array.grade == value) {
                return array.name
            }
            // });
            // return name
        }
    },
    data () {
        return {
            radio: '3',
            loading: false,
            gradeType: [],  // 字典表年级
            fromItem: {
                gradeArr: []
            },
            classData: []  // 班级数据
        };
    },
    methods: {

        /**
         * 升班
         * @return {type} {description}
         */
        handleUpgradeClass () {
            this.$confirm('确定需要升级年级吗？，升级后将在短期内无法再次修改，请谨慎考虑！', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                upgradeClassUpgrade(gradeArr).then(res => {
                    if (res.code === 0) {
                        this.$message({
                            message: "升级成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.handleSearch()
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消升级年级'
                });
            });
        },

        /**
         * 一键升班
         * @return {type} {description}
         */
        hadnleOneKeyUpgradeClass () {
            let gradeArr = []
            this.classData.forEach(ele => {
                if (ele.check == 1) {
                    gradeArr.push(ele.value)
                }
            });
            this.$confirm('确定需要一键升级所有年级吗？，升级后将在短期内无法再次修改，请谨慎考虑！', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                upgradeClassUpgrade(gradeArr).then(res => {
                    if (res.code === 0) {
                        this.$message({
                            message: "升级成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.handleSearch()
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消升级年级'
                });
            });
        },

        /**
       * 获取列表信息
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch () {
            this.loading = true
            getClassUpgrade().then(res => {
                if (res.code == 0) {
                    let that = this
                    this.gradeType.forEach(function (item) {
                        const obj = {
                            name: '',
                            value: '',
                            check: '0',
                            children: []
                        }
                        obj.name = item.label
                        obj.value = item.value
                        obj.children = res.data.filter(child => item.value == child.grade);
                        that.classData.push(obj)
                    });
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getInstitutionalDetail(id).then(res => {
                    if (res.code === 0) {
                        this.$refs['modelView'].handEditInfoModel(res.data);
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
        },
    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style lang="scss">
.class-upgrade {
    .el-tag.el-tag--success {
        margin: 5px 10px;
    }
}
</style>

<style lang="scss" scoped>
.operation-box {
    background: #fafafa;
    padding: 5px 0;
    margin-top: 5px;
    border-radius: 3px;
}
</style>
