<template>
  <div>
      <el-form inline label-width="100px">
          <el-form-item label="海报名称">
              <el-input v-model="pageInfo.posterName" @keyup.enter.native="handleSearch(1)"/>
          </el-form-item>

          <el-form-item label="海报类型">
              <el-select v-model="pageInfo.posterType" clearable @change="handleSearch(1)">
                  <el-option v-for="item in posterTypes" :key="item.id" :value="item.value" :label="item.name"/>
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
      <el-button type="success" style="margin-bottom: 15px" @click="openPosterInfoDialog()">新增海报</el-button>
      <el-table border :data="tableData" v-loading="loading">
          <el-table-column label="序号" align="center" type="index" width="60px"/>
          <el-table-column prop="createTime" align="center" label="创建时间"/>
          <el-table-column prop="createName" align="center" label="创建人"/>

          <el-table-column prop="posterName" align="center" label="海报名称"/>
          <el-table-column prop="posterType" align="center" label="海报类型"/>
          <el-table-column align="center" label="是否放置二维码">
              <template slot-scope="scope">
                  <el-tag v-if="scope.row.isPlaceQrcode == 1" type="success">是</el-tag>
                  <el-tag v-if="scope.row.isPlaceQrcode == 2" type="warning">否</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="qrcodeScaling" align="center" label="二维码缩放比例"/>
          <el-table-column prop="widthPosition" align="center" label="二维码水平位置"/>
          <el-table-column prop="heightPosition" align="center" label="二维码高度位置"/>

          <el-table-column prop="isEnable" align="center" label="活动状态">
              <template slot-scope="scope">
                  <el-tag v-if="scope.row.isEnable == 1" type="success">启用中</el-tag>
                  <el-tag v-if="scope.row.isEnable == -1" type="warning">禁用中</el-tag>
              </template>
          </el-table-column>

          <el-table-column align="center" label="示例图">
              <template slot-scope="scope">
                  <div>
                    <el-image
                        style="width: 100px; height: 100px;"
                        :src="scope.row.posterUrl"
                        :preview-src-list="[scope.row.posterUrl]">
                    </el-image>
                  </div>
              </template>
              
          </el-table-column>
          <el-table-column align="center" label="操作" width="250px">
              <template slot-scope="scope">
                  <el-button size="mini" type="info" @click="openPosterInfoDialog(scope.row.id)">编 辑</el-button>
                  <el-button size="mini" type="primary" @click="generate(scope.row.id)">检验样板</el-button>
                  <el-button size="mini" type="success" plain v-if="scope.row.isEnable == -1" @click="enablePoster(scope.row.id)">启 用</el-button>
                  <el-button size="mini" type="warning" plain v-if="scope.row.isEnable == 1" @click="unEnablePoster(scope.row.id)">停 用</el-button>
                  <el-button size="mini" type="danger" plain @click="deletePoster(scope.row.id)">删 除</el-button>
              </template>
          </el-table-column>
      </el-table>

       <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />

        <poster-info-view ref="posterInfoView" @reselect="handleSearch" :posterTypes="posterTypes"/>
        <poster-sample-view ref="posterSampleView"/>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getActivityPosterList, deleteActivityPoster, enableActivityPoster, unEnableActivityPoster} from '@/api/operating-windows/activity-reward.js'
import PosterInfoView from './poster-info'
import PosterSampleView from './poster-sample'
export default {
    components:{
        Pagination,
        PosterInfoView,
        PosterSampleView
    },

    props:{
        posterTypes: Array
    },

    data(){
        return{
            loading: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                posterName:"",
                posterType: "",
                isEnable: "",

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

        openPosterInfoDialog(id){
            this.$refs['posterInfoView'].openDialog(id);
        },

        generate(id){
            this.$refs['posterSampleView'].openSample(id);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            
            this.loading = true;
            getActivityPosterList(this.pageInfo).then((res) => {
                this.tableData = res.content
                this.tableData.forEach((ele) => {
                    this.posterTypes.forEach((posterType) => {
                        if(ele.posterType == posterType.value){
                            ele.posterType = posterType.name
                        }
                    })
                })
                this.pageInfo.page = res.data.number
                this.pageInfo.total = res.data.total
            }).finally(() => {
                setTimeout(()=> {
                    this.loading = false
                }, 500)
            })
        },
        enablePoster(id){
            enableActivityPoster({id}).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type:"success",
                        message:"操作成功"
                    })
                    this.handleSearch();
                }
            })
        },

        unEnablePoster(id){
            unEnableActivityPoster({id}).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type:"success",
                        message:"操作成功"
                    })
                    this.handleSearch();
                }
            })
        },

        deletePoster(id){
            this.$confirm('是否要删除此活动奖励?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteActivityPoster(id).then((res) => {
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
            this.pageInfo.posterName = ""
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
