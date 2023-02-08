/**
 * 角色页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="公众号菜单列表"
                         name="menuList">
                <keep-alive>
                    <menu-list-pane ref="menuListPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="自动回复规则列表"
                         name="replyList">
                <keep-alive>
                    <reply-list-pane ref="replyListPane"/>
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="用户信息记录"
                         name="replyLogList">
                <keep-alive>
                    <reply-log-list-pane ref="replyLogListPane"/>
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import menuListPane from './components/menu-list'
import replyListPane from './components/reply-list'
import replyLogListPane from './components/reply-log-list'
export default {
    name: 'Tab',
    components: { menuListPane, replyListPane, replyLogListPane},
    data () {
        return {
            activeName: 'menuList',
            createdTimes: 0
        }
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'menuList') {
                this.$refs['menuListPane'].handleSearch(1);
            }else if(data.name === 'replyListPane'){
                this.$refs['replyListPane'].handleSearch(1);
            }else{
                this.$refs['replyLogListPane'].handleSearch(1);
            }
        }
    }
}
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
