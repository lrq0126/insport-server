/**
 * 【编辑】信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="packing-detail">
    <el-tabs
      v-model="activeName"
      style="margin-top: 15px"
      type="border-card"
      @tab-click="handTabClick"
    >
      <el-tab-pane label="编辑信息" name="list">
        <!-- 基础信息 -->
        <el-form
          ref="formItem"
          :model="formItem"
          :rules="formItemRules"
          label-width="120px"
        >
          <el-row>
            <!-- 订单详情 -->
            <el-col :span="24">
              <el-divider content-position="left"
                ><i class="el-icon-mobile-phone"></i>订单详情</el-divider
              >
              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="客户：">
                    <el-input
                      v-model="formItem.customerName"
                      :disabled="true"
                      placeholder="请输入客户"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="会员ID">
                    <el-input
                      v-model="formItem.loginName"
                      :disabled="true"
                      placeholder="请输入会员ID"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="箱子数量：">
                    <el-input
                      v-model="formItem.packNum"
                      :disabled="true"
                      placeholder="请输入箱子数量"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="下单时间：">
                    <el-input
                      v-model="formItem.createTime"
                      :disabled="true"
                      placeholder="请输入下单时间"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="打包时间：">
                    <el-input
                      v-model="formItem.packTime"
                      :disabled="true"
                      placeholder="请输入打包时间"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="订单号：">
                    <el-input
                      v-model="formItem.orderNumber"
                      :disabled="true"
                      placeholder="请输入订单号"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="国家：">
                    <el-input
                      v-model="formItem.destination"
                      :disabled="true"
                      placeholder="国家"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="渠道名称：">
                    <el-input
                      v-model="formItem.routeName"
                      :disabled="true"
                      placeholder="请输入渠道名称"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="运输类型：">
                    <el-select
                      v-model="formItem.routeType"
                      :disabled="true"
                      placeholder="请选择运输类型"
                    >
                      <el-option label="海运" value="2" />
                      <el-option label="空运" value="1" />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-form-item label="收件人：">
                  <el-input
                    v-model="formItem.addressee"
                    placeholder="收件人"
                    :disabled="true"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="联系电话：">
                  <el-input
                    v-model="formItem.phoneNumber"
                    placeholder="联系电话"
                    :disabled="true"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="邮编：">
                  <el-input
                    v-model="formItem.code"
                    placeholder="邮编"
                    :disabled="true"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="收货地址：">
                  <el-input
                    v-model="formItem.address"
                    placeholder="请输入收货地址"
                    type="textarea"
                    :rows="4"
                    :disabled="true"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-col>
          </el-row>
        </el-form>
        <!-- 基础信息 END -->
        <el-form
          ref="formPack"
          :model="formItem"
          :rules="formItemRules"
          label-width="120px"
        >
          <el-row>
            <!-- 货物信息 -->
            <el-col :span="24">
              <div style="width: 90%; float:left"  @click="goodsDataView = !goodsDataView">
                <el-divider content-position="left"><i class="el-icon-mobile-phone"></i>货物信息</el-divider>
              </div>
              <div style="width: 10%; float:left"  @click="goodsDataView = !goodsDataView">
                <el-divider content-position="left" v-if="goodsDataView">关闭<i class="el-icon-arrow-up"></i></el-divider>
                <el-divider content-position="left" v-else>展开<i class="el-icon-arrow-down"></i></el-divider>
              </div>
            </el-col>
            <el-col v-if="goodsDataView" :span="22" style="margin-left: 40px">
              <!-- 表格 -->
              <el-table
                :data="formItem.goodsData"
                ref="multipleTable"
                border
                fit
                v-loading="loading"
                highlight-current-row
                style="width: 100%; margin-top: 20px"
              >

                <el-table-column
                  label="序号"
                  type="index"
                  fixed="left"
                  width="50"
                  align="center"
                />
                <el-table-column
                  label="客户名称"
                  min-width="160"
                  prop="customerName"
                  align="center"
                >
                </el-table-column>

                <el-table-column
                  label="包裹名称"
                  min-width="160"
                  prop="goodsName"
                  align="center"
                >
                </el-table-column>

                <el-table-column
                  label="快递单号/唛头"
                  min-width="160"
                  prop="deliveryOrderNo"
                  align="center"
                >
                </el-table-column>

                <el-table-column
                  label="仓库地址"
                  min-width="160"
                  align="center"
                >
                  <template slot-scope="scope">
                    <span>{{ scope.row.location | filterLocation }}</span>
                  </template>
                </el-table-column>

                <el-table-column
                  label="包裹类型"
                  min-width="160"
                  prop="packageType"
                  align="center"
                >
                </el-table-column>

                <el-table-column
                  label="入库操作员"
                  min-width="160"
                  prop="operatorName"
                  align="center"
                >
                </el-table-column>

                <el-table-column
                  label="入库日期"
                  min-width="160"
                  prop="inStorageTime"
                  align="center"
                >
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-form>

        <!-- 箱子信息 & 可编辑信息 -->
        <el-form
          ref="formPack"
          :model="formItem"
          :rules="formItemRules"
          label-width="120px"
        >
          <el-row>
            <!-- 打包后包裹信息 -->
            <el-col :span="24">
              <el-divider content-position="left"
                ><i class="el-icon-mobile-phone"></i>打包后包裹信息</el-divider
              >
              <el-col :span="22" style="margin-left: 20px">
                <el-form-item label="" label-width="0">
                  <el-table
                    :data="formItem.sonPack"
                    fit
                    highlight-current-row
                    style="width: 100%; margin-top: 20px"
                  >
                    <el-table-column
                      label="序号"
                      width="80"
                      type="index"
                      prop="sortNo"
                      align="center"
                    >
                    </el-table-column>

                    <el-table-column label="实重/KG" align="center">
                      <template slot-scope="scope">
                        <el-form-item
                          style="margin-bottom: 15px"
                          :prop="'sonPack.' + scope.$index + '.actualWeight'"
                          :rules="{
                            required: true,
                            message: '实重不能为空',
                            trigger: 'change',
                          }"
                        >
                          <el-input
                            type="number"
                            v-model="scope.row.actualWeight"
                            placeholder="请输入实重"
                            :readonly="formItem.packType == 3"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="长/CM" align="center">
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 15px">
                          <el-input
                            type="number"
                            v-model="scope.row.length"
                            placeholder="请输入长度"
                            :readonly="formItem.packType == 3"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="宽/CM" align="center">
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 15px">
                          <el-input
                            type="number"
                            v-model="scope.row.width"
                            placeholder="请输入宽度"
                            :readonly="formItem.packType == 3"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="高/CM" align="center">
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 15px">
                          <el-input
                            type="number"
                            v-model="scope.row.height"
                            placeholder="请输入高度"
                            :readonly="formItem.packType == 3"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="体积/m³" align="center">
                      <template slot-scope="scope">
                        <span>{{
                          scope.row.length
                            | allChecked(scope.row.width, scope.row.height)
                        }}</span>
                      </template>
                    </el-table-column>
                    
                    <el-table-column label="操作" width="120" align="center">
                      <template slot-scope="scope">
                        <el-button
                          type="danger"
                          size="mini"
                          plain
                          :disabled="formItem.packType == 3"
                          @click="deleteTopic(scope.$index)"
                        >
                          删除</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form-item>
              </el-col>

              <!-- 备注、优惠价格等信息 -->
              <el-col>
                <el-form-item label="是否敏感货：">
                  <!-- `checked` 为 true 或 false -->
                  <el-checkbox
                    :disabled="formItem.packType == 3"
                    v-model="formItem.isSensitive"
                    true-label="1"
                    false-label="0"
                  />
                  <span style="color: red">(是敏感货物请勾选)</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="预报价：" prop="preQuotedPrice">
                    <el-input
                      v-model="formItem.preQuotedPrice"
                      :disabled="true"
                      placeholder="预报价"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>

                  <el-form-item label="预付关税：" v-if="formItem.isTariffs">
                    <el-input
                      v-model="formItem.tariffsPrice"
                      :disabled="true"
                      type="number"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>

                  <el-form-item label="优惠价：" prop="discount">
                    <el-input
                      v-model="formItem.discount"
                      placeholder="请输入优惠价"
                      :disabled="formItem.packType == 3"
                      type="number"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="实际价格：" prop="actualPrice">
                    <el-input
                      v-model="formItem.actualPrice"
                      :disabled="true"
                      placeholder="实际价格"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>

                  <el-form-item
                    label="保险金额："
                    v-if="formItem.isInsurance == 1"
                  >
                    <el-input
                      v-model="formItem.insurancePrice"
                      :disabled="true"
                      type="number"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>

                  <el-form-item label="附加费：" prop="incidental">
                    <el-input
                      v-model="formItem.incidental"
                      placeholder="请输入附加费"
                      :disabled="formItem.packType == 3"
                      type="number"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="转运单：">
                    <el-input
                      v-model="formItem.internationalTransshipmentNo"
                      placeholder="请输入转运单"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="客服备注：">
                    <el-input
                      v-model="formItem.insideMessage"
                      type="textarea"
                      :role="3"
                      placeholder="请输入客服备注信息"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="备注信息：">
                    <el-input
                      v-model="formItem.remarks"
                      type="textarea"
                      :role="4"
                      placeholder="请输入备注信息"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-col>

            <el-col>
              <!-- <el-divider content-position="left"><i class="el-icon-tickets"></i>产品&货值信息</el-divider> -->
              <div
                @click="packValuationView = !packValuationView"
                class="packValuation"
              >
                <div class="packValuationTitle">
                  <span>产品货值信息&保险信息</span>
                </div>
                <div class="packValuationIcon">
                  <i
                    v-if="packValuationView"
                    style="font-size: 20px; color: #ffffff"
                    class="el-icon-arrow-up"
                  ></i>
                  <i
                    v-else
                    style="font-size: 20px; color: #ffffff"
                    class="el-icon-arrow-down"
                  ></i>
                </div>
              </div>
              <div v-if="packValuationView" class="packValuationTable">
                <div style="margin-bottom: 20px">
                  <el-table border :data="formItem.packInsurancePrices">
                    <el-table-column
                      label="保险名称"
                      align="center"
                      prop="insuranceName"
                    ></el-table-column>

                    <el-table-column
                      label="保险编号"
                      align="center"
                      prop="insuranceNo"
                    ></el-table-column>

                    <el-table-column
                      label="理赔详情"
                      align="center"
                      prop="claimsDetail"
                    >
                      <template slot-scope="scope">
                        <el-button
                          size="mini"
                          type="primary"
                          plain
                          @click="lookClaimsDetail(scope.row.claimsDetail)"
                          >查看详情</el-button
                        >
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="保险价格"
                      align="center"
                      prop="insurancePrice"
                    ></el-table-column>
                    <el-table-column label="价格理赔详情" align="center">
                      <template slot-scope="scope">
                        <el-button
                          size="mini"
                          type="primary"
                          plain
                          @click="lookClaimsDetail(scope.row.priceClaimsDetail)"
                          >查看详情</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </div>

                <el-dialog
                  :visible.sync="dialogClaims"
                  title="理赔详情信息"
                  width="1000px"
                >
                  <el-form style="margin-bottom: 15px">
                    <el-form-item>
                      <textarea
                        v-model="formItem.claimsDetail"
                        readonly
                        rows="18"
                        cols="130"
                      ></textarea>
                    </el-form-item>
                    <el-form-item style="margin-left: 450px">
                      <el-button
                        type="primary"
                        @click="dialogClaims = !dialogClaims"
                        >确认</el-button
                      >
                    </el-form-item>
                  </el-form>
                </el-dialog>
                <div style="margin-bottom: 10px">
                  <el-button
                    type="primary"
                    plain
                    icon="el-icon-circle-plus-outline"
                    @click="addPackValuation()"
                    :disabled="formItem.packType == 3"
                    >添加产品明细</el-button>
                    
                  <span style="margin-left:3%">总货值：<span style="color: red">￥{{formItem.productValue}}元</span></span>
                </div>
                <el-table border :data="formItem.packValuations">
                  <el-table-column
                    label="序号"
                    width="50"
                    type="index"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    label="产品名称"
                    align="center"
                    prop="productName"
                  >
                    <template slot-scope="scope">
                      <el-input
                        v-model="scope.row.productName"
                        :readonly="formItem.packType == 3"
                      ></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="单价/￥"
                    align="center"
                    prop="unitPrice"
                  >
                    <template slot-scope="scope">
                      <el-input
                        v-model="scope.row.unitPrice"
                        type="number"
                        min="0"
                        :readonly="formItem.packType == 3"
                        @input="changeGoodsValue(scope.row, scope.$index)"
                      ></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="数量" align="center" prop="number">
                    <template slot-scope="scope">
                      <el-input
                        v-model="scope.row.number"
                        type="number"
                        min="0"
                        :readonly="formItem.packType == 3"
                        @input="changeGoodsValue(scope.row, scope.$index)"
                      ></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="货值/￥"
                    align="center"
                    prop="goodsValue"
                  >
                    <template slot-scope="scope">
                      <el-input
                        v-model="scope.row.goodsValue"
                        readonly
                      ></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                      <el-button
                        type="danger"
                        plain
                        @click="removePackValuation(scope.$index)"
                        :disabled="formItem.packType == 3"
                        >删除</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </el-col>

            <el-col :span="20" style="margin-top: 40px">
              <el-row>
                <el-col :span="20">
                  <el-form-item label-width="60%">
                    
                    <el-button
                      :loading="saving"
                      v-if="formItem.packType == 3"
                      type="primary"
                      @click="editMessage('formPack')"
                      >修改备注</el-button
                    >

                    <el-button :loading="saving" v-if="formItem.packType == 3" type="warning" plain @click="agentOrderPrint">代理商打单</el-button>
                    <!-- <el-button type="danger"
                                                   plain
                                                   style="margin-left: 15px;"
                                                   @click="handlePrintPrintOrder(formItem.businessNumber)">打印面单</el-button> -->
                    <el-button @click="GoBack">返回上一层</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="返回上一层" name="back"></el-tab-pane>
    </el-tabs>

    <!--更改运输渠道 -->
    <channel-list-modal
      ref="channelListShow"
      @updateForm="handleSearch($route.query.businessNumber)"
    />
    <agent-channel-model-view ref="agentChannelModelView"/>
  </div>
</template>

<script>
import {
  pickUpPackingDetail,
  printCustomerPackListing,
  printOrderListing,
  onePickUpPackingDetail,
  editPackMessage,
} from "@/api/package-management/unpackaged-task";

import {
  editPack,
  waitingAreaDetail,
} from "@/api/package-management/waiting-area";

import channelListModal from "./channel-list-modal";
import AgentChannelModelView from "./agent-channel-model";
export default {
  name: "Create",
  components: {
    channelListModal,
    AgentChannelModelView
  },
  data() {
    /**
     * 自定义验证规则
     */
    const validateEn = (rule, value, callback) => {
      let reg = /[\u4e00-\u9fa5]+/;
      if (value === "") {
        callback(new Error("订单号不能为空"));
      } else if (value == null) {
        callback();
      } else if (value !== "" && reg.test(value)) {
        callback(new Error("只允许字母、数字、下划线"));
      } else {
        callback();
      }
    };
    const validateActualWeight = (rule, value, callback) => {
      let reg = /^[.0-9]+$/;
      if (value === "") {
        callback(new Error("请输入数字实重"));
      } else if (value == null) {
        callback();
      } else if (value !== "" && reg.test(value)) {
        callback(new Error("请输入数字实重"));
      } else {
        callback();
      }
    };
    const validateLength = (rule, value, callback) => {
      let reg = /^[.0-9]+$/;
      if (value === "") {
        callback(new Error("请输入数字长度"));
      } else if (value == null) {
        callback();
      } else if (value !== "" && reg.test(value)) {
        callback(new Error("请输入数字长度"));
      } else {
        callback();
      }
    };
    const validateWidth = (rule, value, callback) => {
      let reg = /^[.0-9]+$/;
      if (value === "") {
        callback(new Error("请输入数字宽度"));
      } else if (value == null) {
        callback();
      } else if (value !== "" && reg.test(value)) {
        callback(new Error("请输入数字宽度"));
      } else {
        callback();
      }
    };
    const validateHeight = (rule, value, callback) => {
      let reg = /^[.0-9]+$/;
      if (value === "") {
        callback(new Error("请输入数字高度"));
      } else if (value == null) {
        callback();
      } else if (value !== "" && reg.test(value)) {
        callback(new Error("请输入数字高度"));
      } else {
        callback();
      }
    };
    return {
      goodsDataView: true,
      packValuationView: true,
      bool: true,
      TagView: "",
      activeName: "list",
      loading: false,
      saving: false,
      dialogClaims: false,
      currencyData: [],
      formItem: {
        id: "",
        packingId: "",
        customerName: "",
        packNum: "",
        createTime: "",
        routeName: "",
        routeType: "",
        address: "",
        message: "",
        remarks: "",
        incidental: "",
        businessNumber: "",
        loginName: "",
        orderNumber: "",
        phoneNumber: "",
        addressee: "",
        countryId: "",
        country: "",
        insideMessage: "",
        isSensitive: "0",

        isInsurance: "0",
        insurancePrice: "",
        isTariffs: "0", // 是否需要关税 1是 0否
        tariffs: "", // 关税比例
        tariffsPrice: "", // 关税金额

        packageList: [], // 弹窗中包裹列表
        goodsData: [], // 所有货物明细
        selectedPackageList: [], // 弹窗中已被选择的包裹

        sonPackIndex: "", //箱子索引
        // 箱子明细
        sonPack: [
          {
            actualWeight: "",
            actualVol: "",
            length: "",
            width: "",
            height: "",
            bool: false,
            selectIds: "",
            goodsNos: [],
            packagesInfo: [],
          },
        ],

        //产品货值信息
        packValuations: [],

        //保险信息
        packInsurancePrices: [],
        claimsDetail: "",
      },
      multipleSelection: [],
      index: "", // 选中快递包裹索引值
      dialogInfo: false,

      formItemRules: {
        id: [
          {
            required: true,
            validator: validateEn,
            trigger: "blur",
          },
        ],

        actualWeight: [
          { required: true, message: "请输入实重", trigger: "blur" },
        ],

        typeId: [
          {
            required: true,
            message: "账号不能为空",
            trigger: "blur",
          },
        ],
        typeId: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur",
          },
        ],
        typeId: [
          {
            required: true,
            message: "确认密码不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  filters: {
    allChecked(length, width, height) {
      if (!length) return 0;
      if (!width) return 0;
      if (!height) return 0;
      return (Number(length) * Number(width) * Number(height)) / 1000000;
    },
    filterLocation(value) {
      if (value) {
        let location = value.split(",");
        return location[0] + "区" + location[1] + "排";
      } else {
        return "";
      }
    },
  },
  methods: {
    /**
     * 音频
     * @return {type} {description}
     */
    playAudio(data) {
      let buttonAudio;
      if (data) {
        buttonAudio = document.getElementById("allSorAudio");
        // buttonAudio.setAttribute('src', audio)
        buttonAudio.play();
      } else {
        buttonAudio = document.getElementById("eventAudio");
        // buttonAudio.setAttribute('src', audio)
        buttonAudio.play();
      }
    },




    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm(form) {
      this.formItem.id = "";
      this.$refs[form].resetFields();
    },

    /**
     * 多选框事件
     * @param  {type} val {description}
     * @return {type} {description}
     */
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },



    /**
     * 编辑备注信息
     */
    editMessage(from) {
      editPackMessage(this.formItem)
        .then((res) => {
          if (res.code === 100) {
            this.$message({
              message: "编辑成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.GoBack(); // 返回上一层
          }else{
            this.$message({
              message: res.content,
              type: "error",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
          }
        })
        .finally(() => {
          this.saving = false;
        });
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset(from) {
      this.fileList = [];
      this.formItem.remark = "";
      this.formItem.fileUrl = [];
      this.$refs[from].resetFields();
    },


    /**
     * 返回上一层
     * @return {type} {description}
     */
    GoBack() {
      this.$router.push({
        name: "WaitingShipped",
      });
      this.$store.dispatch("tagsView/delView", this.TagView).then(({ visitedViews }) => {
          if (this.isActive(this.TagView)) {
            this.toLastView(visitedViews, this.TagView);
          }
        });
    },

    /**
     * 选项卡切换
     * @param  {type} data {description}
     * @return {type} {description}
     */
    handTabClick(data) {
      if (data.name === "back") {
        
        this.$router.push({
          name: "WaitingShipped",
        });

        this.$store
          .dispatch("tagsView/delView", this.TagView)
          .then(({ visitedViews }) => {
            if (this.isActive(this.TagView)) {
              this.toLastView(visitedViews, this.TagView);
            }
          });
      }
    },

    /**
     * 修改Tabs-view内容
     * @return {type} {description}
     */
    setTagsViewTitle() {
      const title = "打包明细";
      const route = Object.assign({}, this.tempRoute, {
        title: `${title}`,
      });
      this.$store.dispatch("tagsView/updateVisitedView", route);
    },

    /**
     * 获取明细详情
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(businessNumber) {
      this.loading = true;
      waitingAreaDetail(businessNumber)
        .then((res) => {
          if (res.code === 100) {

             // 货币类型
            this.currencyData = res.content.customerPack.routeVo.currencyData;

            this.formItem = res.content.customerPack;
            this.formItem.goodsData = res.content.customerPack.goods; // 打包明细
            this.formItem.country = res.content.customerPack.routeVo.country;
            this.formItem.countryId =
              res.content.customerPack.routeVo.countryId;
            this.formItem.routeType = String(
              res.content.customerPack.routeVo.transportType
            );
            this.formItem.isSensitive = String(
              res.content.customerPack.isSensitive
            );

            this.formItem.isInsurance =
              res.content.customerPack.routeVo.isInsurance;
            this.formItem.isTariffs =
              res.content.customerPack.routeVo.isTariffs;
            this.formItem.tariffs = res.content.customerPack.routeVo.tariffs;

            this.formItem.packValuations =
              res.content.customerPack.packValuations;

            if (res.content.customerPack.packInsurancePrice) {
              this.formItem.packInsurancePrices = [];
              this.formItem.packInsurancePrices.push(
                res.content.customerPack.packInsurancePrice
              );
            }

            this.formItem.packNum =
              res.content.customerPack.customerPackNumbers.length;
            // 初始化已选中信息
            this.selectedPackageList = [];
            // 重置子包裹的信息，防止报错
            this.formItem.sonPack =
              res.content.customerPack.customerPackNumbers;

            // 组装已选货物信息
            let pingPackIndex = 0;
            this.formItem.sonPack.forEach((pack) => {
              pack.bool = true;

              pack.packagesInfo =
                res.content.customerPack.customerPackNumbers[
                  pingPackIndex
                ].goodsVoList;
              pack.goodsIds = "";
              res.content.customerPack.customerPackNumbers[
                pingPackIndex
              ].goodsVoList.forEach((goods) => {
                pack.goodsIds =
                  pack.goodsIds == ""
                    ? goods.id
                    : pack.goodsIds + "," + goods.id;
              });

              pingPackIndex++;
            });
          }

          this.selectedPackageList = res.content.customerPack.goods;
          this.formItem.packageList = res.content.customerPack.goods;
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 包裹清单打印
     * @return {type} {description}
     */
    handlePrintCustomerPack(businessNumber) {
      printCustomerPackListing(businessNumber).then((res) => {
        if (res.code === 100) {
          window.open("http://" + res.content.url);
        }
      });
    },

    /**
     * 面单打印
     * @return {type} {description}
     */
    handlePrintPrintOrder(businessNumber) {
      printOrderListing(businessNumber).then((res) => {
        if (res.code === 100) {
        }
      });
    },

    agentOrderPrint(){
      let baseData = {
          orderNumber: this.formItem.orderNumber,
          addressee: this.formItem.addressee,
          phoneNumber: this.formItem.phoneNumber,
          code: this.formItem.code,
          address: this.formItem.address,
          remarks: this.formItem.remarks,
          productValueData: this.formItem.packValuations,
          sonPackNum: this.formItem.packNum
      }
      this.$refs['agentChannelModelView'].openAgentChannelDialog(baseData);
    },

    lookClaimsDetail(claimsDetail) {
      this.formItem.claimsDetail = claimsDetail;
      this.dialogClaims = true;
    },

    addPackValuation() {
      let packValuation = {
        productName: "",
        unitPrice: 0,
        number: 0,
        goodsValue: 0,
      };
      this.formItem.packValuations.push(packValuation);
    },

    removePackValuation(index) {
      console.log("index:", index);
      this.formItem.packValuations.splice(index, 1);

      if (this.formItem.isTariffs == 1) {
        let allGoodsValue = 0;
        this.formItem.packValuations.forEach((ele) => {
          allGoodsValue = allGoodsValue + ele.goodsValue;
        });
        this.formItem.tariffsPrice = (
          (parseFloat(allGoodsValue) * parseFloat(this.formItem.tariffs)) /
          100
        ).toFixed(2);
        console.log("this.formItem.tariffsPrice：", this.formItem.tariffsPrice);
      }
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
      this.formItem.packValuations[index].goodsValue = (
        parseFloat(rowData.unitPrice) * parseFloat(rowData.number)
      ).toFixed(2);

      if (this.formItem.isTariffs == 1) {
        let allGoodsValue = 0;
        this.formItem.packValuations.forEach((ele) => {
          allGoodsValue = allGoodsValue + parseFloat(ele.goodsValue);
        });
        this.formItem.tariffsPrice = (
          (parseFloat(allGoodsValue) * parseFloat(this.formItem.tariffs)) /
          100 * this.currencyData.alternateField
        ).toFixed(2);
      }
    },
  },

  mounted() {
    this.tempRoute = Object.assign({}, this.$route);
    this.setTagsViewTitle();
    this.$store.state.tagsView.visitedViews.forEach((ele) => {
      if (ele.title == "打包明细") {
        this.TagView = ele;
      }
    });
    // 初始化数据
    this.handleSearch(this.$route.query.businessNumber);
  },
};
</script>

<style lang="scss" scope>
.packing-detail {
  margin: 10px;
  .quill-editor {
    .ql-container {
      height: 400px;
    }
  }
  .packValuation {
    height: 40px;
    background: #b0c4de;
    .packValuationTitle {
      float: left;
      width: 80%;
      margin-left: 3%;
      margin-top: 10px;
    }
    .packValuationIcon {
      margin-top: 10px;
      width: 10%;
      text-align: right;
      float: right;
      margin-right: 3%;
    }
  }
  .packValuationTable {
    margin-left: 10%;
    margin-top: 1%;
    width: 80%;
  }

  .title {
    span {
      font-weight: 700;
      margin: 15px;
      display: inline-block;
    }
  }
  .el-input-group__append,
  .el-input-group__prepend {
    padding: 0 8px;
  }
  .el-input.is-disabled .el-input__inner {
    color: #333;
  }
  .el-textarea.is-disabled .el-textarea__inner {
    color: #333;
  }
  .el-divider--horizontal {
    margin: 40px 0;
    .el-divider__text {
      color: #e89a49;
      font-size: 14px;
      font-weight: 700;
    }
  }
}
</style>
