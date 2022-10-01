/**
 * 消息列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
      <el-form-item label="操作记录">
        <el-input
          type="text"
          v-model="pageInfo.content"
          placeholder="请输入操作记录"
        />
      </el-form-item>

      <el-form-item label="关联单号">
        <el-input
          type="text"
          v-model="pageInfo.contentId"
          placeholder="请输入关联单号"
        />
      </el-form-item>

      <el-form-item label="操作类型">
        <el-select v-model="pageInfo.contentType" clearable>
          <el-option
            v-for="item in noticeTypes"
            :key="item.value"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="操作员">
        <el-input
          type="text"
          v-model="pageInfo.opertorName"
          placeholder="请输入操作员"
        />
      </el-form-item>

      <el-form-item label="操作时间">
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
        prop="contentType"
        align="center"
        width="160"
        label="操作类型"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.contentType == 'SELECT'">查询</span>
          <span v-if="scope.row.contentType == 'ADD'">新增</span>
          <span v-if="scope.row.contentType == 'UPDATE'">编辑</span>
          <span v-if="scope.row.contentType == 'DELETE'">删除</span>
          <span v-if="scope.row.contentType == 'DISABLED'">停用</span>
          <span v-if="scope.row.contentType == 'ENABLED'">启用</span>
          <span v-if="scope.row.contentType == 'PUBLISH'">发布</span>
          <span v-if="scope.row.contentType == 'UP'">置顶</span>
          <span v-if="scope.row.contentType == 'PUTON'">上架</span>
          <span v-if="scope.row.contentType == 'LOGIN'">登录</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="content"
        min-width="200"
        label="操作记录"
      ></el-table-column>

      <el-table-column
        prop="contentId"
        min-width="100"
        label="关联单号"
      ></el-table-column>

      <el-table-column
        prop="opertorName"
        align="center"
        width="150"
        label="操作员"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        align="center"
        width="160"
        label="操作时间"
      ></el-table-column>

      <el-table-column
        prop="respMessage"
        align="center"
        width="160"
        label="状态"
      ></el-table-column>

      
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
  getLogList
} from "@/api/notice-notice/notice-list";
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
          value: 'SELECT',
          name: "查询",
        },
        {
          value: 'ADD',
          name: "新增",
        },
        {
          value: 'UPDATE',
          name: "编辑",
        },
        {
          value: 'DELETE',
          name: "删除",
        },
        {
          value: 'DISABLED',
          name: "停用",
        },
        {
          value: 'ENABLED',
          name: "启用",
        },
        {
          value: 'PUBLISH',
          name: "发布",
        },
        {
          value: 'UP',
          name: "置顶",
        },
        {
          value: 'PUTON',
          name: "上架",
        },
        {
          value: 'LOGIN',
          name: "登录",
        },
      ],

      loading: false,
      tableData: [],
      pageInfo: {
        contentType: "",
        content: "",
        contentId: "",
        opertorName: "",
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
      getLogList(this.pageInfo)
        .then((res) => {
          this.tableData = res.content.logList;
          this.pageInfo.total = parseInt(res.content.data.total);
          this.pageInfo.page = parseInt(res.content.data.number);
          this.pageInfo.limit = parseInt(res.content.data.pageSize);
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
      this.pageInfo.contentType = "";
      this.pageInfo.content = "";
      this.pageInfo.createTime = "";
      this.pageInfo.contentId = "";
      this.pageInfo.opertorName = "";
    },


  },
  mounted() {
    this.handleSearch();
  },
};
</script>

