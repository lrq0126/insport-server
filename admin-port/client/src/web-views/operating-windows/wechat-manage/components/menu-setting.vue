<template>
    <div>
        <el-dialog :visible.sync="settingMenuDialog" title="公众号菜单配置" width="30%" :close-on-click-modal="false">
            <el-alert title="微信公众号菜单只支持3个一级菜单" type="warning" :closable="false"/>
            <el-tree
                :data="menuData"
                :props="defaultProps"
                show-checkbox
                class="tree-info"
                :indent="25"
                style="font-size: 20px; line-height: 50px"
                @check-change="handleCheckChange">
            </el-tree>
            
            <el-button style="margin-left: 15%" type="info" plain @click="settingMenuDialog = false">取 消</el-button>
            <el-button type="primary" @click="submitSetting">确认发布配置</el-button>
        </el-dialog>
    </div>
</template>

<script>
import {getSettingMenu, enableMenu} from "@/api/operating-windows/wechat-manage"
export default {
    components:{

    },

    data(){
        return{
            settingMenuDialog: false,

            menuData: [],
            defaultProps: {
                children: 'secondaryMenu',
                label: 'menuName'
            },

            selectedMenuIds: [], // 被选中的菜单
        }
    },

    methods:{
        openSettingDialog(){
            this.settingMenuDialog = true;
            this.getSettingMenu();
        },

        getSettingMenu(){
            getSettingMenu().then((res) => {
                this.menuData = res.content
            });
        },

        /**
         * 选中菜单
         * data：选中的数据
         * checked： 节点本身是否被选中
         */
        handleCheckChange(data, checked) {
            if(checked){
                this.selectedMenuIds.push(data.id);
            }else{
                if(this.selectedMenuIds.includes(data.id)){
                    this.selectedMenuIds.splice(this.selectedMenuIds.indexOf(data.id), 1);
                }
            }
        },

        submitSetting(){
            this.$confirm('是否启动当前菜单配置(公众号适用时间跟随微信设定)?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = JSON.stringify(this.selectedMenuIds)
                enableMenu({ids}).then((res) => {
                    if(res.code == 100){
                        this.$message({
                            type:"success",
                            message:"菜单发布成功"
                        })
                    }else{
                        let message = res.content
                        this.$message({
                            type:"error",
                            message: message
                        })
                    }
                })
            })

            
        }

    }
}
</script>

<style lang="postcss">
    .tree-info{

        margin-top: 10px;
        margin-left: 5%;
        line-height: 30px;
        margin-bottom: 10px;
    }
</style>