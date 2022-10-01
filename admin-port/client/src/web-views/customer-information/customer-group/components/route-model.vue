/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <el-dialog
      title="渠道信息"
      :visible.sync="routeDialog"
      :close-on-click-modal=false
      width="80%"
      append-to-body
    >
      <!-- 搜索 -->
      <el-form ref="searchForm" :model="pageInfo" inline label-width="80px" style="float: left">
        <el-form-item label="国家名称">
          <el-select v-model="pageInfo.countryId" clearable @change="handleSearch(1)">
            <el-option v-for="item in countryData"
              :key="item.id"
              :label="item.sddName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="渠道名称">
          <el-input
            type="text"
            v-model="pageInfo.routeName"
            placeholder="请输入渠道名称"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>
        <el-form-item label="渠道类型">
          <el-select v-model="pageInfo.transportType" clearable @change="handleSearch(1)">
            <el-option value="1" label="空运"></el-option>
            <el-option value="2" label="海运"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch(1)">查询</el-button>&nbsp;
          <el-button @click="handleResetForm()">重置</el-button>
        </el-form-item>
        
        <el-form-item style="width: 200px;float: right">
          <el-button type="primary" @click="submitSelect" :disabled="routeSelected.length < 1">添加已选渠道用户</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        border
        v-loading="loading"
        style="width: 100%"
        @selection-change="selectRoute"
      >
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column
          type="index"
          label="序号"
          width="50"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="country"
          label="国家"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="routeName"
          label="渠道名称"
          min-width="120"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="transportType"
          label="渠道类型"
          min-width="120"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.transportType == 1" type="primary">空运</el-tag>
            <el-tag v-if="scope.row.transportType == 2" type="success">海运</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          min-width="120"
          align="center"
        >
            <template slot-scope="scope">
                <el-button size="mini" plain type="primary" @click="submit(scope.row)">选 择</el-button>
            </template>
        </el-table-column>
      </el-table>

      <!-- 页码 -->
      <pagination
        v-show="pageInfo.total > 0"
        :total="pageInfo.total"
        :current.sync="pageInfo.page"
        :limit.sync="pageInfo.limit"
        @pagination="pagination"
      />

      <el-button @click="routeDialog = false" type="info">关 闭</el-button>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getChannelList} from '@/api/channel-management/channel-list';
import {getCountriesListNotParams} from '@/api/channel-management/countries-list';
import {addGroupCustomerByRouteId} from '@/api/customer-information/customer-group';
export default {
  components: {
    Pagination,
  },
  data() {
    return {
      routeDialog: false,
      loading: false,
      tableData: [],
      countryData:[],
      routeSelected: [],
      groupId: "",
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数
        countryId:"",
        routeName:"",
        transportType:""
      }, // 页码传参数

  
    };
  },
  methods: {
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

    selectRoute(val){
      this.routeSelected = val;
    },

    openRouteDialog(groupId){
      this.handleResetForm();
      this.loading = true;
      this.groupId = groupId;
      this.handleSearch(1);
      this.routeDialog = true;
      getCountriesListNotParams().then((res) => {
        if(res.code == 100){
          this.countryData = res.content.country
        }
      });

      setTimeout(() => {
        this.loading = true;
      }, 800);
      
    },

    submitSelect() {
        let routeIdList = "";
        
        this.routeSelected.forEach((ele) => {
          routeIdList = routeIdList ? routeIdList + "," + ele.id : ele.id
        });

        let data = {
         groupId: this.groupId,
         routeIds: routeIdList,
        }

        addGroupCustomerByRouteId(data).then((res) => {
          if(res.code == 100) {
            this.$message({
              type:"success",
              message:res.content
            });
            this.routeDialog = false;
            this.$emit("reSelectTable", this.groupId);
          } 
        });
    },

    submit(row) {
        
        let data = {
         groupId: this.groupId,
         routeIds: row.id,
        }

        addGroupCustomerByRouteId(data).then((res) => {
          if(res.code == 100) {
            this.$message({
              type:"success",
              message:res.content
            });
            this.routeDialog = false;
            this.$emit("reSelectTable", this.groupId);
          } 
        });
    },

    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getChannelList(this.pageInfo)
        .then((res) => {
          this.tableData = res.content.route;
          this.pageInfo.total = parseInt(res.content.page.total);
          this.pageInfo.page = parseInt(res.content.page.number);
          this.pageInfo.limit = parseInt(res.content.page.pageSize);
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm() {
      this.pageInfo.countryId = "";
      this.pageInfo.routeName = "";
      this.pageInfo.transportType = "";
    },

  },
};
</script>

