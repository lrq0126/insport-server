<template>
  <div>
      <el-form inline label-width="100px">
          <el-form-item label="活动名称">
              <el-input v-model="pageInfo.activityName" @keyup.enter.native="handleSearch(1)"/>
          </el-form-item>

          <el-form-item label="活动时间类型">
                <el-select v-model="pageInfo.activityTimeType" clearable @change="handleSearch(1)">
                    <el-option value="1" label="长期活动"/>
                    <el-option value="2" label="限时活动"/>
                </el-select>
          </el-form-item>
          <el-form-item label="状态">
                <el-select v-model="pageInfo.isEnable" clearable @change="handleSearch(1)">
                    <el-option value="1" label="启用中"/>
                    <el-option value="-1" label="停用"/>
                </el-select>
          </el-form-item>
          <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
          <el-button @click="resetPageInfo">重 置</el-button>
      </el-form>
      <el-button type="success" style="margin-bottom: 15px" @click="openActivityRewardDialog()">新增活动详情</el-button>
      <el-table border :data="tableData" v-loading="loading">
          <el-table-column label="序号" align="center" type="index" width="60px"/>
          <el-table-column prop="createTime" align="center" label="创建时间"/>
          <el-table-column prop="createName" align="center" label="创建人"/>
          <el-table-column prop="updateTime" align="center" label="更新时间"/>
          <el-table-column prop="updateName" align="center" label="更新人"/>

          <el-table-column prop="activityName" align="center" label="活动名称"/>
          <el-table-column prop="activityTime" align="center" label="活动时间"/>
          <el-table-column prop="activityTypeName" align="center" label="活动类型"/>
          <!-- <el-table-column prop="activityType" align="center" label="活动类型">
              <template slot-scope="scope">
                  <el-tag v-if="scope.row.activityType == 'toPromote'" type="success">推广返利</el-tag>
                  <el-tag v-if="scope.row.activityType == 'scanCode'" type="warning">扫码注册</el-tag>
              </template>
          </el-table-column> -->

          <el-table-column prop="rewardType" align="center" label="奖励类型">
              <template slot-scope="scope">
                  <el-tag v-if="scope.row.rewardType == 'coupons'"  type="primary">优惠券</el-tag>
                  <el-tag v-if="scope.row.rewardType == 'integral'"  type="warning">积分</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="isEnable" align="center" label="活动状态">
              <template slot-scope="scope">
                  <el-tag v-if="scope.row.isEnable == 1" type="success">启用中</el-tag>
                  <el-tag v-if="scope.row.isEnable == -1" type="warning">禁用中</el-tag>
              </template>
          </el-table-column>

          <el-table-column align="center" label="操作" width="250px">
              <template slot-scope="scope">
                  <el-button size="mini" type="info" @click="openActivityRewardDialog(scope.row.id)">编 辑</el-button>
                  <el-button size="mini" type="success" plain v-if="scope.row.isEnable == -1" @click="enableActivity(scope.row.id, 1)">启 用</el-button>
                  <el-button size="mini" type="warning" plain v-if="scope.row.isEnable == 1" @click="enableActivity(scope.row.id, -1)">停 用</el-button>
                  <el-button size="mini" type="danger" plain @click="deleteActivity(scope.row.id)">删 除</el-button>
              </template>
          </el-table-column>
      </el-table>

       <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />

        <activity-reward-info-view ref="activityRewardInfoView" @reselect="handleSearch" 
            :integralTypes="integralTypes" 
            :activityTypes="activityTypes"/>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getActivityRewardList, deleteActivityReward, enableActivityReward} from '@/api/operating-windows/activity-reward.js'
import ActivityRewardInfoView from './activity-reward-info'
export default {
    components:{
        Pagination,
        ActivityRewardInfoView
    },

    props:{
        integralTypes: Array,
        activityTypes: Array
    },

    data(){
        return{
            loading: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                activityName:"",
                activityTimeType: "",
                isEnable: ""

            },

            tableData: []
        }
    },

    methods:{
        /**
         * 分页
         * @param {object} 
         * @return {type} {description}
         */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page
                this.handleSearch()
            } else {
                this.pageInfo.limit = pageObj.limit
                this.handleSearch(1)
            }
        },

        openActivityRewardDialog(id){
            this.$refs['activityRewardInfoView'].openDialog(id);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            
            this.loading = true;
            getActivityRewardList(this.pageInfo).then((res) => {
                this.tableData = res.content
                
                this.tableData.forEach((ele) => {
                    if(ele.activityTimeType == 1){
                        ele.activityTime = "长期活动"
                    }else{
                        ele.activityTime = ele.startTime + "至" + ele.endTime
                    }

                    this.activityTypes.forEach((activityType) => {
                        if(ele.activityType == activityType.value){
                            ele.activityTypeName = activityType.name
                        }
                    })

                });
                this.pageInfo.page = res.data.number
                this.pageInfo.total = res.data.total
            }).finally(() => {
                setTimeout(()=> {
                    this.loading = false
                }, 500)
            })
        },
        enableActivity(id, enableType){
            enableActivityReward({id, enableType}).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type:"success",
                        message:"操作成功"
                    })
                    this.handleSearch();
                }
            })
        },

        deleteActivity(id){
            this.$confirm('是否要删除此活动奖励?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteActivityReward(id).then((res) => {
                    if(res.code == 100){
                        this.$message({
                            type:"success",
                            message:"删除成功"
                        })
                    }
                    this.handleSearch();
                })
            })
        },

        resetPageInfo(){
            this.pageInfo.activityName = ""
            this.pageInfo.activityTimeType = ""
            this.pageInfo.isEnable = ""
            this.handleSearch(1)
        }
    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style lang="sass" scoped>

</style>
