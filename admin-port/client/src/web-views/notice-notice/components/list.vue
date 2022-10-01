/**
 * 消息列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
      <el-form-item label="公告标题">
        <el-input
          type="text"
          v-model="pageInfo.noticeTitle"
          placeholder="请输入公告标题"
        />
      </el-form-item>

      <el-form-item label="通知公告">
        <el-input
          type="text"
          v-model="pageInfo.noticeContent"
          placeholder="请输入公告内容"
        />
      </el-form-item>
      <el-form-item label="通知类型">
        <el-select v-model="pageInfo.noticeType" clearable>
          <el-option
            v-for="item in noticeTypes"
            :key="item.value"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="发布人">
        <el-input
          type="text"
          v-model="pageInfo.publisher"
          placeholder="请输入发布人"
        />
      </el-form-item>

      <el-form-item label="发布时间">
        <el-date-picker
          v-model="pageInfo.createTime"
          size="large"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          :picker-options="pickerOptions"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查 询</el-button
        >&nbsp;
        <el-button @click="handleResetForm('searchForm')">重 置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableData" border v-loading="loading" style="width: 100%">
      <el-table-column
        type="index"
        label="序号"
        width="50"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="noticeTitle"
        min-width="50"
        label="公告标题"
      ></el-table-column>

      <el-table-column
        prop="noticeContent"
        min-width="160"
        label="通知内容"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        align="center"
        width="200"
        label="发布时间"
      ></el-table-column>

      <el-table-column
        prop="publisher"
        align="center"
        width="160"
        label="发布人"
      ></el-table-column>

      <el-table-column
        prop="noticeType"
        align="center"
        width="160"
        label="通知类型"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.noticeType == 1">系统通知</span>
          <span v-if="scope.row.noticeType == 2">个人通知</span>
          <span v-if="scope.row.noticeType == 3">分组通知</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="overdueTime"
        align="center"
        width="160"
        label="过期时间"
      ></el-table-column>

      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            plain
            @click="handleModal(scope.row)"
            >查看</el-button
          >
          <el-button
            v-if="filterPermissions('删除通知')"
            type="danger"
            size="mini"
            plain
            @click="handleRemove(scope.row.id)"
            >删除</el-button
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
    <!-- 弹窗信息 -->
    <model-view ref="modelView" @updateList="handleSearch" />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from "./model";
import {
  getNotificationAnnouncement,
  removeNotificationAnnouncement,
  getNoticeDetail
} from "@/api/notice-notice/notice-list";
import { getAllRoles } from "@/api/permissi";
import { setTimeout } from "timers";

export default {
  components: {
    Pagination,
    ModelView,
  },
  data() {
    return {
      // 时间选择器
      pickerOptions: {
        shortcuts: [
          {
            text: "本月",
            onClick(picker) {
              picker.$emit("pick", [new Date(), new Date()]);
            },
          },
          {
            text: "今年至今",
            onClick(picker) {
              const end = new Date();
              const start = new Date(new Date().getFullYear(), 0);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近六个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setMonth(start.getMonth() - 6);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },

      noticeTypes: [
        {
          value: 1,
          name: "系统通知",
        },
        {
          value: 2,
          name: "个人通知",
        },
        {
          value: 3,
          name: "分组通知",
        },
      ],

      loading: false,
      tableData: [],
      pageInfo: {
        noticeTitle: "",
        noticeContent: "",
        createTime: "",
        noticeType: "",
        publisher: "",
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数
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

    /**
     * 获取列表信息
     * @param  {nnumber} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getNotificationAnnouncement(this.pageInfo)
        .then((res) => {
          this.tableData = res.content.list;
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
      this.pageInfo.noticeTitle = "";
      this.pageInfo.noticeContent = "";
      this.pageInfo.createTime = "";
      this.pageInfo.noticeType = "";
      this.pageInfo.publisher = "";
    },

    /**
     * 启动编辑弹窗
     * @return {type} {description}
     */
    handleModal(row) {
      this.$refs["modelView"].handEditInfoModel(row);
    },

    /**
     * 删除
     * @param  {string} id {description}
     * @return {type} {description}
     */
    handleRemove(id) {
      this.$confirm("确定删除通知公告吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        removeNotificationAnnouncement(id).then((res) => {
          if (res.code === 100) {
            this.pageInfo.page = 1;
            this.$message({
              message: "删除成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5,
            });
            this.handleSearch();
          }
        });
      });
    },
  },
  mounted() {
    this.handleSearch();
  },
};
</script>

