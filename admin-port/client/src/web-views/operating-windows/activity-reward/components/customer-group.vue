<template>
  <div>
    <el-dialog
      append-to-body
      :visible.sync="customerGroupDialog"
      close-on-click-modal
      title="客户分组列表"
      width="80%"
    >
      <!-- 搜索 -->
      <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
        <el-form-item label="客户名称">
          <el-input
            type="text"
            v-model="pageInfo.customerName"
            placeholder="请输入客户名称"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>
        <el-form-item label="会员ID">
          <el-input
            type="text"
            v-model="pageInfo.loginName"
            placeholder="请输入会员ID"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>

        <el-form-item label="分组名称">
          <el-input
            type="text"
            v-model="pageInfo.groupName"
            placeholder="请输入分组名称"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>
        <el-form-item label="创建人">
          <el-input
            type="text"
            v-model="pageInfo.createName"
            placeholder="请输入创建人名称"
            @keyup.enter.native="handleSearch(1)"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch(1)">查询</el-button
          >&nbsp;
          <el-button @click="handleResetForm('searchForm')">重置</el-button>
        </el-form-item>

      </el-form>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        border
        v-loading="loading"
        style="width: 100%"
      >
        <el-table-column
          type="index"
          label="序号"
          width="50"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="groupName"
          label="分组名称"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="createTime"
          label="创建时间"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="createName"
          label="创建人"
          min-width="120"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="groupCustomerNum"
          label="成员人数"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="remarks"
          label="备注信息"
          min-width="160"
          align="center"
        ></el-table-column>

        <el-table-column label="操作" fixed="right" width="350" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="selectedCustomerGroup(scope.row)"
              >选 择</el-button
            >
            <el-button
              type="primary"
              size="mini"
              plain
              @click="checkGroupMember(scope.row.id)"
              >查看成员</el-button
            >

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

      <customer-group-menber-detail-view ref="editGroupCustomerView" />
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getCustomerCouponsList,
  deleteGroup,
} from "@/api/customer-information/customer-group";
import CustomerGroupMenberDetailView from "./customer-group-menber-detail";

export default {
  components: {
    Pagination,
    CustomerGroupMenberDetailView,
  },
  data() {
    return {
      customerGroupDialog: false,
      loading: false,
      pageInfo: {
        page: 1,
        limit: 10,
        total: 0,

        groupName: "",
        loginName: "",
        customerName: "",
        createName: "",
      },
      tableData: [],
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

    openDialog() {
      this.customerGroupDialog = true;
      this.handleSearch(1);
    },

    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch (page) {
        if (page) {
            this.pageInfo.page = page
        }
        this.loading = true
        getCustomerCouponsList(this.pageInfo).then(res => {
            this.tableData = res.content
            this.pageInfo.total = res.data.total
            this.pageInfo.page = res.data.number
        }).finally(() => {
            setTimeout(() => {
                this.loading = false
            }, 1000)
        })
    },

    /**
    * 表单重置
    * @param  {string} form 
    * @return {type} {description} 
    */
    handleResetForm () {
        this.pageInfo.groupName = ""
        this.pageInfo.loginName = ""
        this.pageInfo.customerName = ""
        this.pageInfo.createName = ""
    }, 

    /**
     * 启动分组成员编辑弹窗
     * @return {type} {description}
     */
    checkGroupMember (id) {
        this.$refs['editGroupCustomerView'].openGroupCustomerDialog(id);
    },

    selectedCustomerGroup(row){
        console.log(row);
        let data = {
            sendObjectId: row.id,
            sendObjectName: row.groupName,
        }
        this.$emit('updateSendObject', data);
        this.customerGroupDialog = false;
    }
  },
};
</script>

<style>

</style>