<template>
  <div>
    <el-form inline label-width="120px">
      <el-form-item label="客户ID">
        <el-input v-model="pageInfo.customerID" />
      </el-form-item>

      <el-form-item label="KEY值">
        <el-input v-model="pageInfo.eventKey" />
      </el-form-item>
      <el-button type="info" @click="resetForm">重 置</el-button>
      <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
    </el-form>

    <el-table :data="tableData" border v-loading="loading">
      <el-table-column type="index" label="序号" align="center" width="60px" />
      <el-table-column prop="fromUserName" label="发送方帐号" align="center" />
      <el-table-column prop="loginName" label="会员ID" align="center" />
      <el-table-column prop="createTime" label="时间" align="center" />
      <el-table-column prop="msgType" label="消息类型" align="center" />
      <el-table-column prop="event" label="事件类型" align="center" />
      <el-table-column prop="eventKey" label="事件KEY值" align="center" />
    </el-table>
    <!-- 页码 -->
    <pagination
      v-show="pageInfo.total > 0"
      :total="pageInfo.total"
      :current.sync="pageInfo.page"
      :limit.sync="pageInfo.limit"
      @pagination="pagination"
    />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getWeChatLog } from "@/api/rights-manage/wechat-log";
export default {
  components: {
    Pagination,
  },

  data() {
    return {
      loading: false,
      tableData: [],

      pageInfo: {
        page: 1,
        limit: 20,
        total: 0,
        customerID: "",
        eventKey: "",
      },
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

    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getWeChatLog(this.pageInfo).then((res) => {
        if (res.code == 100) {
          this.tableData = res.content;

          this.pageInfo.page = res.data.number;
          this.pageInfo.total = res.data.total;
        }
      }).finally(() => {
          setTimeout(() =>{
              this.loading = false;
          }, 800)
      })
    },

    resetForm() {
      this.pageInfo.customerID = "";
      this.pageInfo.eventKey = "";
    },
  },

  mounted() {
    this.handleSearch(1);
  },
};
</script>

<style>
</style>