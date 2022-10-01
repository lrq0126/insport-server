<template>
  <div>
    <el-dialog
      :visible.sync="agentChannelDialog"
      width="90%"
      :close-on-click-modal="false"
      title="选择代理商渠道"
    >
      <el-dialog
        :visible.sync="channelInfoDialog"
        width="85%"
        :close-on-click-modal="false"
        append-to-body
        title="新增代理商渠道"
      >
        <el-form
          :model="channelInfo"
          ref="channelInfoForm"
          label-width="120px"
          :rules="formRules"
        >
          <el-form-item label="代理商" prop="channelName">
            <el-select
              placeholder="请选择"
              clearable
              filterable
              v-model="channelInfo.agentCode"
            >
              <el-option
                v-for="item in agentData"
                :key="item.id"
                :label="item.agentName"
                :value="item.agentCode"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="渠道名称" prop="channelName">
            <el-input style="width: 400px" v-model="channelInfo.channelName" />
          </el-form-item>

          <el-form-item label="渠道代码" prop="channelCode">
            <el-input style="width: 400px" v-model="channelInfo.channelCode" />
          </el-form-item>
          <el-form-item label="渠道类型" prop="channelType">
            <el-input style="width: 400px" v-model="channelInfo.channelType" />
          </el-form-item>

          <el-form-item label="渠道别名" prop="channelEname">
            <el-input style="width: 400px" v-model="channelInfo.channelEname" />
          </el-form-item>

          <el-form-item label="最大重量" prop="maxWeight">
            <el-input-number
              :precision="2"
              :step="0.1"
              v-model="channelInfo.maxWeight"
            />
          </el-form-item>

          <el-form-item label="品名" prop="productName">
            <el-input v-model="channelInfo.productName" />
          </el-form-item>

          <el-form-item label="备注信息">
            <el-input v-model="channelInfo.message" />
          </el-form-item>
          <el-form-item>
            <el-button
              type="info"
              @click="channelInfoDialog = !channelInfoDialog"
              >取 消</el-button
            >
            <el-button type="warning" @click="resetChannelInfo"
              >重 置</el-button
            >
            <el-button type="success" @click="confirm('channelInfoForm')"
              >确 定</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>
      <el-divider>
        <span @click="productValueView = !productValueView"
          >订单产品信息
          <i class="el-icon-caret-top" v-if="productValueView" />
          <i class="el-icon-caret-bottom" v-else
        /></span>
      </el-divider>
      <div v-if="productValueView">
        <el-table
          :data="packOrderData.productValueData"
          style="margin-left: 5%; width: 90%"
          border
        >
          <el-table-column
            type="index"
            label="序号"
            align="center"
            width="60"
          />
          <el-table-column prop="productName" label="产品名称" align="center" />
          <el-table-column prop="unitPrice" label="单价" align="center" />
          <el-table-column prop="number" label="数量" align="center" />
          <el-table-column prop="goodsValue" label="货值" align="center" />
        </el-table>
      </div>
      <el-divider> 每箱产品明细 </el-divider>

      <div>
        <el-table
          :data="sonPackProductData"
          style="margin-left: 5%; width: 90%"
          border
        >
          <el-table-column
            type="index"
            label="序号"
            align="center"
            width="60"
          />
          <el-table-column label="名称" align="center">
            <template slot-scope="scope">
              <span>箱子{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="productName" label="产品名称" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productName"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="productEName" label="产品英文名称" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.productEName"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="unitPrice" label="单价" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unitPrice" type="number" :precision="2"
                        min="0" 
                        @input="changeGoodsValue(scope.row, scope.$index)"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="number" label="数量" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.number" type="number" :precision="2"
                        min="0" 
                        @input="changeGoodsValue(scope.row, scope.$index)"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="goodsValue" label="货值" align="center">
            <!-- <template slot-scope="scope">
              <el-input v-model="scope.row.goodsValue"></el-input>
            </template> -->
          </el-table-column>
          <el-table-column prop="remarks" label="备注" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remarks"></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-divider> 收件人信息 </el-divider>
      <el-form label-width="150px">
        <el-row>
          <el-col :span="11">
            <el-form-item label="收件人">
              <el-input readonly v-model="packOrderData.addressee" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="联系方式">
              <el-input readonly v-model="packOrderData.phoneNumber" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="地址">
              <el-input
                type="textarea"
                :rows="3"
                readonly
                v-model="packOrderData.address"
              />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="备注">
              <el-input
                type="textarea"
                :rows="3"
                readonly
                v-model="packOrderData.remarks"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="7">
            <el-form-item label="邮编">
              <el-input
                placeholder="请输入邮编号码"
                v-model="packOrderData.code"
              />
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="州/区">
              <el-input
                placeholder="请输入州/区"
                v-model="packOrderData.province"
              />
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="城市">
              <el-input placeholder="请输入城市" v-model="packOrderData.city" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-divider></el-divider>

      <el-form inline v-model="dialogInfo" ref="form">
        <el-form-item label="代理商：">
          <el-select
            placeholder="请选择"
            clearable
            filterable
            v-model="dialogInfo.agentCode"
            @change="handleSearch(1)"
          >
            <el-option
              v-for="item in agentData"
              :key="item.id"
              :label="item.agentName"
              :value="item.agentCode"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="渠道名称：">
          <el-input v-model="dialogInfo.channelName" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
          <el-button type="success" @click="openCreateAgentChannel"
            >新增代理商渠道</el-button
          >
        </el-form-item>
      </el-form>
      <el-table
        v-loading="loading"
        width="100%"
        border
        :data="agentChannelData"
      >
        <el-table-column
          type="index"
          label="序号"
          width="60px"
          align="center"
        />
        <el-table-column
          label="代理商"
          prop="agentName"
          width="180px"
          align="center"
        />
        <el-table-column
          label="渠道名称"
          prop="channelName"
          width="180px"
          align="center"
        />
        <el-table-column
          label="代理商渠道代码"
          prop="channelCode"
          width="180px"
          align="center"
        />
        <el-table-column
          label="代理商渠道类型"
          prop="channelType"
          width="180px"
          align="center"
        />
        <el-table-column
          label="渠道别名"
          prop="channelEname"
          width="180px"
          align="center"
        />
        <el-table-column
          label="系统渠道代码"
          prop="insideCode"
          width="180px"
          align="center"
        />
        <el-table-column
          label="最大重量"
          prop="maxWeight"
          width="140px"
          align="center"
        />
        <el-table-column
          label="是否启用"
          prop="isEnable"
          width="140px"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isEnable == 1" type="success"
              >启用中</el-tag
            >
            <el-tag v-else type="danger">已禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="备注信息"
          prop="message"
          width="140px"
          align="center"
        />
        <el-table-column
          label="品名"
          prop="productName"
          width="140px"
          align="center"
        />
        <el-table-column
          label="操作"
          width="250px"
          align="center"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="warning"
              @click="printAgentOrder(scope.row)"
              :disabled="scope.row.isEnable == 2"
              >创 建</el-button
            >

            <el-button
              size="mini"
              type="info"
              plain
              @click="editChannel(scope.row)"
              >编 辑</el-button
            >

            <el-button
              size="mini"
              v-if="scope.row.isEnable == 2"
              type="success"
              plain
              @click="enable(scope.row)"
              >启 用</el-button
            >
            <el-button
              size="mini"
              v-else
              type="danger"
              plain
              @click="enable(scope.row)"
              >禁 用</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 页码 -->
      <pagination
        v-show="dialogInfo.total > 0"
        :total="dialogInfo.total"
        :current.sync="dialogInfo.page"
        :limit.sync="dialogInfo.limit"
        @pagination="pagination"
      />

      <el-button
        style="margin-left: 90%"
        type="info"
        plain
        @click="agentChannelDialog = !agentChannelDialog"
        >关 闭</el-button
      >
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";

import {
  getAgentList,
  getAgentChannel,
  createAgentChannel,
  editAgentChannel,
  enableAgentChannel,
  printAgentOrder,
} from "@/api/financial-management/agent.js";
import { error } from "jquery";
export default {
  components: {
    Pagination,
  },
  data() {
    return {
      loading: false,
      agentChannelDialog: false,
      channelInfoDialog: false,
      productValueView: true,
      channelInfo: {
        id: "",
        agentCode: "",
        channelName: "",
        channelCode: "",
        channelEname: "",
        channelType: "",
        maxWeight: "",
        message: "",
        isEnable: "1",
        productName: "",
      },

      dialogInfo: {
        page: 1,
        limit: 10,
        total: 0,
        channelName: "",
        agentCode: "",
      },
      packOrderData: {
        agentId: "",
        channelId: "",
        channelCode: "",
        productValueData: [],
      },

      sonPackProductData: [],

      agentData: [],
      agentChannelData: [],

      formRules: {
        agentCode: [
          { required: true, message: "代理商不能为空", trigger: "blur" },
        ],
        channelName: [
          {
            required: true,
            message: "代理商渠道名称不能为空",
            trigger: "blur",
          },
        ],
        channelCode: [
          { required: true, message: "渠道编码不能为空", trigger: "blur" },
        ],
        channelType: [
          { required: true, message: "渠道类型不能为空", trigger: "blur" },
        ],
        channelEname: [
          { required: true, message: "渠道别名称不能为空", trigger: "blur" },
        ],
        maxWeight: [
          { required: true, message: "渠道最大重量不能为空", trigger: "blur" },
        ],
        isEnable: [
          { required: true, message: "启用状态不能为空", trigger: "blur" },
        ],
        productName: [
          {
            required: true,
            message: "代理商渠道名称不能为空",
            trigger: "blur",
          },
        ],
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
        this.dialogInfo.page = pageObj.page;
        this.handleSearch();
      } else {
        this.dialogInfo.limit = pageObj.limit;
        this.handleSearch(1);
      }
    },

    openAgentChannelDialog(data) {
      this.resetAgent();
      this.packOrderData = data;
      if (data.sonPackNum > 0) {
        let i = 1;
        while (i < data.sonPackNum) {
          let sonPackProduct = {
            packName: "",
            productName: "",
            productEName: "",
            number: "",
            unitPrice: "",
            goodsValue: "",
            remarks: ""
          };
          this.sonPackProductData.push(sonPackProduct);
          i++;
        }
      }
      this.agentChannelDialog = true;
    },

    getAgentList() {
      getAgentList().then((res) => {
        this.agentData = res.content;
      });
    },
    resetAgent() {
      this.sonPackProductData = [];
      this.agentChannelData = [];
      this.productValueView = true;
      this.dialogInfo.agentCode = "";
      this.dialogInfo.channelName = "";
    },

    // 代理商渠道对应的实现方法
    handleSearch(page) {
      if (page) {
        this.dialogInfo.page = page;
      }
      this.loading = true;
      getAgentChannel(this.dialogInfo)
        .then((res) => {
          if (res.code == 100) {
            this.agentChannelData = res.content;

            this.dialogInfo.page = res.data.number;
            this.dialogInfo.limit = res.data.pageSize;
            this.dialogInfo.total = res.data.total;
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 500);
        });
    },

    openCreateAgentChannel() {
      this.channelInfoDialog = true;
      this.resetChannelInfo();
    },

    editChannel(channel) {
      this.channelInfo = channel;
      this.channelInfoDialog = true;
    },

    confirm(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.channelInfo.id) {
            editAgentChannel(this.channelInfo).then((res) => {
              if (res.code == 100) {
                this.$message({
                  type: "success",
                  message: "代理商渠道编辑成功",
                });
                this.channelInfoDialog = false;
                this.handleSearch();
              }
            });
          } else {
            createAgentChannel(this.channelInfo).then((res) => {
              if (res.code == 100) {
                this.$message({
                  type: "success",
                  message: "代理商渠道添加成功",
                });
                this.channelInfoDialog = false;
                this.handleSearch();
              }
            });
          }
        }
      });
    },

    enable(row) {
      let data = {
        id: row.id,
        isEnable: row.isEnable,
      };
      enableAgentChannel(data).then((res) => {
        if (res.code == 100) {
          this.$message({
            type: "success",
            message: "启禁用状态变更成功",
          });
          this.handleSearch();
        }
      });
    },

    resetChannelInfo() {
      this.channelInfo = {
        channelName: "",
        channelCode: "",
        channelType: "",
        channelEname: "",
        maxWeight: "",
        message: "",
        isEnable: "1",
        productName: "",
      };
    },

    changeGoodsValue(rowData, index) {
      if (!rowData.unitPrice || rowData.unitPrice < 0) {
        rowData.unitPrice = "";
        return;
      }
      if (!rowData.number || rowData.number < 0) {
        rowData.number = "";
        return;
      }
      this.sonPackProductData[index].goodsValue = (
        parseFloat(rowData.unitPrice) * parseFloat(rowData.number)
      ).toFixed(2);
    },

    printAgentOrder(data) {
      this.packOrderData.channelId = data.id;
      this.packOrderData.channelCode = data.channelCode;
      this.agentData.forEach((ele) => {
        if (ele.agentCode === this.dialogInfo.agentCode) {
          this.packOrderData.agentId = ele.id;
        }
      });
      this.packOrderData.sonPackProductDetail = JSON.stringify(this.sonPackProductData);
      printAgentOrder(this.packOrderData).then((res) => {
        if (res.content.statusCode == 200) {
          this.$message({
            type: "success",
            message: "创建订单成功",
          });
          if ( res.content.labelUrl ){
            window.open(res.content.labelUrl);
          }
          this.agentChannelDialog = false;
          this.handleSearch();
        } else {
          this.$message({
            type: "error",
            message: res.content.message,
          });
        }
      });
    },
  },

  mounted() {
    this.getAgentList();
  },
};
</script>