<template>
    <div>
        <el-form ref="form"
                 :model="pageInfo"
                 label-width="100px"
                 inline>

            <el-form-item label="菜单名称">
                <el-input v-model="pageInfo.menuName" style="width: 150px"/>
            </el-form-item>
            <el-button @click="handleSearch(1)">查询</el-button>
        </el-form>
        
        <div>
            <el-button type="primary" plain @click="openMenuDialog">添加菜单</el-button>
            <el-button style="float: right; margin-right:2%; margin-bottom: 10px" type="success" @click="openSettingMenu">配置公众号菜单</el-button>

            <el-table :data="menuData" 
                v-loading="loading"
                border
                lazy
                row-key="id"
                :default-expand-all="false"
                :tree-props="{children: 'secondaryMenu'}">
                <el-table-column label="序号" align="center" type="index" width="60px"/>
                
                <el-table-column label="菜单名称" align="center" prop="menuName"/>
                <el-table-column label="菜单等级" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.menuLevel == 1" type="success">一级菜单</el-tag>
                        <el-tag v-if="scope.row.menuLevel == 2" type="primary">二级菜单</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="菜单动作类型" align="center" prop="menuType">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.menuType" disabled placeholder="无动作类型">
                            <el-option
                                v-for="item in menuTypes"
                                :key="item.value"
                                :value="item.value"
                                :label="item.name"/>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="链接" align="center" prop="redirectUrl"/>
                <el-table-column label="返回内容" align="center" prop="wechatKey"/>
                <el-table-column label="合法media_id" align="center" prop="mediaId"/>
                <!-- <el-table-column label="合法article_id" align="center" prop="articleId"/> -->
                <el-table-column label="操作"  align="center" width="300px">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.menuLevel == 1" type="primary" size="mini" @click="openMenuDialog(scope.row, true)">添加菜单</el-button>
                        <el-button size="mini" @click="openMenuDialog(scope.row, false)">编 辑</el-button>
                        <el-button size="mini" type="danger" plain @click="deleteMune(scope.row.id)" >删 除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :page.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" /> 
        </div>

        <menu-info-view ref="menuInfo" @reselect="handleSearch(1)" />
        <menu-setting-view ref="menuSetting"/>
    </div>
</template>

<script>
import {getMenuList, deleteMenu} from "@/api/operating-windows/wechat-manage"

import Pagination from "@/components/Pagination";
import MenuInfoView from "./menu-info"
import MenuSettingView from "./menu-setting"
export default {
    components:{
        Pagination,
        MenuInfoView,
        MenuSettingView
    },

    data() {
        return{
            loading: false,

            menuData:[],

            pageInfo: {
               page: 0,
               limit: 10,
               total: 0,
            },

            selectedFirstMenu: [],
            selectedSecondaryMenu: [],
            
            menuTypes: [
                {
                    value: "view",
                    name: "转跳网页",
                },
                {
                    value: "click",
                    name: "返回信息",
                },
                {
                    value: "media_id",
                    name: "返回图片",
                },
                {
                value: "view_limited",
                name: "网页图文消息",
                },
                {
                    value: "article_id",
                    name: "卡片图文消息",
                },
                {
                    value: "article_view_limited",
                    name: "网页图文消息",
                },
            ],

        }
    },

    methods:{
        /**
         * 分页
         * @param {object}
         * @return {type} {description}
         */
        pagination(pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page;
                this.handleSearch();
            } else {
                this.pageInfo.limit = pageObj.limit;
                this.handleSearch(1);
            }
        },


        openMenuDialog(data, isAddSecondary){
            this.$refs['menuInfo'].openMenuInfo(data, isAddSecondary);
        },

        openSettingMenu(){
            this.$refs['menuSetting'].openSettingDialog();
        },

        deleteMune(id){
            this.$confirm('是否要删除此菜单(不影响已发布的菜单内容)?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteMenu(id).then((res) => {
                    if(res.code == 100){
                        this.$message({
                            type:"success",
                            message:"删除成功"
                        })
                        this.handleSearch();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
            
        },
        
        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            getMenuList(this.pageInfo).then(res => {
                if(res.code == 100){
                    this.menuData = res.content
                    this.pageInfo.page = res.data.pageNumber
                    this.pageInfo.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 500);
            });
            
        },

    },

    mounted(){
        this.handleSearch(1);
    }

}
</script>

<style>

</style>