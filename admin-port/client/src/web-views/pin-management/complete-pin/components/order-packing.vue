/**
 * 【打包明细】信息
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
      <el-tab-pane label="拼团打包明细" name="pinPackDetail">
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
                  <el-form-item label="订单号：">
                    <el-input
                      v-model="formItem.orderNumber"
                      :disabled="true"
                      placeholder="打包后系统生成"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="流水号：">
                    <el-input
                      v-model="formItem.businessNumber"
                      :disabled="true"
                      placeholder="打包后系统生成"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="客户：">
                    <el-input
                      v-model="formItem.customerName"
                      :disabled="true"
                      placeholder="请输入客户"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="会员ID:">
                    <el-input
                      v-model="formItem.loginName"
                      :disabled="true"
                      placeholder="会员ID"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="包裹数量：">
                    <el-input
                      v-model="formItem.goodsNumber"
                      :disabled="true"
                      placeholder="请输入包裹数量"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="下单时间：">
                    <el-input
                      v-model="formItem.createTime"
                      :disabled="true"
                      placeholder="请输入下班时间"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="国家：">
                    <el-input
                      v-model="formItem.country"
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
                      v-model="formItem.transportType"
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
                <el-col :span="24">
                  <el-form-item label="收件人电话：">
                    <el-input
                      v-model="formItem.phoneNumber"
                      :disabled="true"
                      placeholder="请输入渠道名称"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="收件人名称：">
                    <el-input
                      v-model="formItem.addressee"
                      :disabled="true"
                      placeholder="请输入渠道名称"
                    ></el-input>
                  </el-form-item>
                </el-col>

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

            <!-- 扫码拣货 -->
            <el-col :span="24">
              <el-divider content-position="left"
                ><i class="el-icon-mobile-phone"></i>扫码拣货</el-divider
              >
              <audio
                src="http://www.wennever.cn/upload/pdf/success.mp3"
                id="eventAudio"
              ></audio>
              <audio
                src="http://www.wennever.cn/upload/pdf/AllSortSuccess.mp3"
                id="allSorAudio"
              ></audio>
              <audio
                src="http://www.wennever.cn/upload/pdf/warning.mp3"
                id="eventAudioError"
              ></audio>

              <el-col :span="24">
                <el-form-item label="扫码拣货：" label-width="30%">
                  <el-input
                    v-model="formItem.deliveryOrderNo"
                    style="width: 50%"
                    placeholder="请输入订单号"
                    @keyup.enter.native="pickUp('formItem')"
                  ></el-input>
                  <el-button
                    type="primary"
                    style="margin-left: 15px"
                    icon="el-icon-success"
                    @click="pickUp('formItem')"
                    >拣 货</el-button
                  >
                  <el-button
                    type="info"
                    plain
                    style="margin-left: 15px"
                    @click="handleResetForm('formItem')"
                    >重 置</el-button
                  >
                </el-form-item>
              </el-col>
            </el-col>
          </el-row>
        </el-form>
        <el-form
          ref="formPack"
          :model="formItem"
          :rules="formItemRules"
          label-width="120px"
        >
          <el-row>
            <!-- 打包明细 -->
            <el-col :span="24">
              <el-divider content-position="left"
                ><i class="el-icon-mobile-phone"></i>打包明细</el-divider
              >
              <el-button
                type="danger"
                plain
                style="margin-left: 15px"
                @click="handlePrintCustomerPack(formItem.businessNumber)"
                >打印包裹清单</el-button
              >
              <el-button
                type="warning"
                plain
                style="margin-left: 15px"
                @click="addGoods()"
                >添加货物</el-button
              >
              <el-button
                type="primary"
                plain
                style="margin-right: 50px; float: right"
                @click="onePickUp('formItem')"
                >一键拣货</el-button
              >
              <el-col :span="23" style="margin-left: 20px">
                <el-table
                  :data="formItem.goodsData"
                  border
                  fit
                  v-loading="goodsDataLoading"
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
                    label="备注信息"
                    min-width="160"
                    prop="message"
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
                    min-width="120"
                    prop="packageType"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="重量"
                    min-width="120"
                    prop="kg"
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

                  <el-table-column
                    label="拣货状态"
                    fixed="right"
                    min-width="120"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <el-switch
                        v-if="scope.row.sortType == 0"
                        v-model="scope.row.outStorageTime"
                        active-color="#13ce66"
                        inactive-color="#ddd"
                        active-text="未分拣"
                        @change="
                          handleOutbound(
                            scope.row.deliveryOrderNo,
                            scope.$index
                          )
                        "
                        inactive-text=""
                      >
                      </el-switch>
                      <el-switch
                        v-if="scope.row.sortType == 1"
                        :value="scope.row.sortType == 1"
                        active-color="#13ce66"
                        inactive-color="#ddd"
                        active-text="已分拣"
                        inactive-text=""
                        disabled
                      >
                      </el-switch>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-col>
            <!-- 打包后包裹信息 -->
            <el-col :span="24">
              <el-divider content-position="left"
                ><i class="el-icon-mobile-phone"></i>打包后包裹信息</el-divider
              >

              <el-col :span="22" style="margin-left: 20px">
                <el-form-item label="" label-width="0">
                  <el-button
                    type="primary"
                    plain
                    style="margin-bottom: 5px"
                    icon="el-icon-circle-plus-outline"
                    @click="addTopic"
                    >新增包裹</el-button
                  >
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

                    <el-table-column
                      label="快递包裹"
                      width="120"
                      align="center"
                    >
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 0">
                          <el-button
                            type="primary"
                            v-if="!scope.row.bool"
                            size="mini"
                            plain
                            @click="handlePackages(+scope.$index)"
                            >请选择包裹</el-button
                          >
                          <el-tooltip placement="top" v-else>
                            <div
                              slot="content"
                              v-for="(res, index) in scope.row.packagesInfo"
                              :key="index"
                              style="margin: 8px 0; font-size: 16px"
                            >
                              <span>客户名称：{{ res.customerName }}</span>
                              <span style="margin: 0 10px"
                                >包裹名称：{{ res.goodsName }}</span
                              >
                              <span style="margin: 0 10px"
                                >订单编号/唛头：{{ res.deliveryOrderNo }}</span
                              >
                              <span>包裹类型：{{ res.packageType }}</span>
                            </div>
                            <el-button
                              type="success"
                              size="mini"
                              plain
                              @click="handlePackages(scope.$index)"
                              >已选包裹</el-button
                            >
                          </el-tooltip>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="包裹数量" width="80" align="center">
                      <template slot-scope="scope">
                        <span>{{ scope.row.packagesInfo.length }}</span>
                      </template>
                    </el-table-column>

                    <el-table-column label="操作" width="120" align="center">
                      <template slot-scope="scope">
                        <el-button
                          type="danger"
                          size="mini"
                          plain
                          @click="deleteTopic(scope.$index)"
                        >
                          删除</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form-item>
              </el-col>

              <!-- <el-col :span="8">
                                <el-col :span="24">
                                    <el-form-item label="实际总重量："
                                                  prop="name">
                                        <el-input v-model="formItem.name"
                                                  placeholder="请输入实际总重量"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="实际总体积："
                                                  prop="name">
                                        <el-input v-model="formItem.name"
                                                  placeholder="请输入实际总体积"></el-input>
                                    </el-form-item>
                                </el-col>

                            </el-col> -->
              <el-col :span="8">
                <el-col>
                  <el-form-item label="是否敏感货：">
                    <!-- `checked` 为 true 或 false -->
                    <el-checkbox
                      v-model="formItem.isSensitive"
                      true-label="1"
                      false-label="0"
                    />
                    <span style="color: red">(是敏感货物请勾选)</span>
                  </el-form-item>

                  <el-form-item label="优惠金额：">
                    <el-input
                      v-model="formItem.discount"
                      style="width: 180px"
                      placeholder="请输入优惠金额"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>
                  <el-form-item label="附加费：">
                    <el-input
                      v-model="formItem.incidental"
                      style="width: 180px"
                      placeholder="请输入附加费"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="备注信息：">
                    <el-input
                      v-model="formItem.remarks"
                      type="textarea"
                      :role="5"
                      placeholder="请输入备注信息"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="客服备注：">
                    <el-input
                      v-model="formItem.insideMessage"
                      type="textarea"
                      :role="5"
                      placeholder="客服内部备注，客户无法查看"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-col>

            <el-col :span="20" style="margin-top: 40px">
              <el-row>
                <el-col :span="20">
                  <el-form-item label-width="50%">
                    <el-button
                      :loading="saving"
                      type="success"
                      @click="editMessage('formPack')"
                      ><i class="el-icon-edit"></i>编辑备注信息</el-button
                    >
                    <!-- <el-button
                      :loading="saving"
                      type="primary"
                      @click="handleSubmit('formPack')"
                      >确认打包</el-button> -->

                    <el-button
                      type="primary"
                      icon="el-icon-upload"
                      :loading="saving"
                      plain
                      v-if="formItem.packType == 1"
                      @click="handleSubmit()"
                      >确认打包</el-button
                    >

                    <el-button
                      type="primary"
                      icon="el-icon-upload"
                      :loading="saving"
                      plain
                      v-if="formItem.packType == 2"
                      @click="handleSubmit()"
                      >确认修改</el-button
                    >
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

    <!--包裹列表信息 -->
    <el-dialog :visible.sync="dialogInfo" title="包裹列表信息" width="80%">
      <!-- 表格 -->
      <el-table
        :data="formItem.packageList"
        ref="multipleTable"
        border
        fit
        v-loading="loading"
        highlight-current-row
        style="width: 100%; margin-top: 20px"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>

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
          label="货物名称"
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

        <el-table-column label="仓库地址" min-width="160" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.location | filterLocation }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="包裹类型"
          min-width="120"
          prop="packageType"
          align="center"
        >
        </el-table-column>
        <el-table-column label="重量" min-width="120" prop="kg" align="center">
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

      <span slot="footer" class="dialog-footer">
        <el-button type="info" plain @click="dialogInfo = false"
          >取 消</el-button
        >
        <el-button type="primary" @click="handleSelectPackages"
          >确 认</el-button
        >
      </span>
    </el-dialog>

    <!-- 弹窗信息 -->
    <add-goods-view ref="addGoodsView" @goodsList="resetGoodsData" />
    <!-- end -->
  </div>
</template>

<script>
import {
  pickUpPackingDetail,
  printCustomerPackListing,
  printOrderListing,
  onePickUpPackingDetail,
  editPackMessage,
  confirmPack,
} from "@/api/package-management/unpackaged-task";

import { editPack } from "@/api/package-management/waiting-area";

import AddGoodsView from "./add-goods";
import { getPinOrderDetail } from "@/api/pin-management/pin-main";

export default {
  name: "Create",
  components: {
    AddGoodsView,
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
      bool: true,
      TagView: "",
      activeName: "pinPackDetail",
      loading: false,
      goodsDataLoading: false,
      saving: false,
      formItem: {
        deliveryOrderNo: '', // 货物订单号， 用于扫码拣货

        pid: "", // 拼团ID
        id: "", // 子订单ID

        // 订单详情展示字段 start
        businessNumber: "",
        orderNumber: "",
        customerName: "",
        loginName: "",
        goodsNumber: "",
        createTime: "",
        country: "",
        routeName: "",
        transportType: "",
        address: "",
        phoneNumber: "",
        addressee: "",
        // 订单详情展示字段 end

        // 后续填写的附加费、备注等 start
        remarks: "",
        incidental: "",
        insideMessage: "",
        // 后续填写的附加费、备注等 end

        customerPackNumber: [],

        goodsData: [], // 打包明细  快递包裹数据
        packageList: [], // 弹窗中包裹列表

        // 确认打包请求入参
        customerId: "",
        discount: "",
        internationalTransshipmentNo: "",
        // 箱子数据
        // 箱子数据集合
        sonPack: [
          {
            sortNo: 1,
            actualWeight: "",
            actualVol: "",
            length: "",
            width: "",
            height: "",
            bool: false,
            selectIds: "",
            goodsIds: "",
            packagesInfo: [],
          },
        ],
      },
      formItemRules: {
        id: [
          {
            required: true,
            validator: validateEn,
            trigger: "blur",
          },
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

      multipleSelection: [],

      sonPackIndex: "", // 选中快递包裹索引值
      selectedPackageList: [], // 弹窗中已被选择的包裹

      dialogInfo: false,
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
     * 拣货
     * @return {type} {description}
     */
    pickUp(from) {
      this.$refs[from].validate((valid) => {
        if (valid) {
          let obj = {
            businessNumber: this.formItem.businessNumber,
            deliveryOrderNo: this.formItem.deliveryOrderNo,
          };
          this.saving = true;
          debugger;
          pickUpPackingDetail(obj)
            .then((res) => {
              console.log(res);
              if (res.code === 100) {
                this.playAudio(res.content.isAllSort); // 音频
                let message;
                if (res.content.isAllSort) {
                  message = "全部货物已拣完";
                } else {
                  message = "拣货成功";
                }
                this.$message({
                  message: message,
                  type: "success",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
                this.loading = true;
                setTimeout(() => {
                  this.loading = false;
                  this.formItem.goodsData = res.content.goods;
                }, 800);
                this.formItem.deliveryOrderNo = '';
              } else {
                let buttonAudio = document.getElementById("eventAudioError");
                buttonAudio.play();
                this.$message({
                  message: "拣货失败",
                  type: "error",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
              }
            })
            .catch((e) => {
              let buttonAudio = document.getElementById("eventAudioError");
              buttonAudio.play();
            })
            .finally(() => {
              this.saving = false;
              this.handleReset("formItem");
            });
        }
      });
    },

    /**
     * 一键拣货
     * @return {type} {description}
     */
    onePickUp(from) {
      let obj = {
        businessNumber: this.formItem.businessNumber,
      };
      this.saving = true;
      onePickUpPackingDetail(obj)
        .then((res) => {
          if (res.code === 100) {
            this.playAudio(false); // 音频
            this.$message({
              message: "拣货成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.loading = true;
            setTimeout(() => {
              this.loading = false;
              this.formItem.goodsData = res.content.goods;
            }, 800);
          } else {
            // this.$message({
            //     message: '拣货失败',
            //     type: 'error',
            //     showClose: true,
            //     duration: 1000 * 1.5 * 2
            // });
          }
        })
        .finally(() => {
          this.saving = false;
          this.handleReset("formItem");
        });
    },

    /**
     * 拣货
     * @return {type} {description}
     */
    handleOutbound(id, index) {
      if (this.bool) {
        this.bool = false;
        this.$confirm("包裹是否需要拣货？确定后将不能修改!", "拣货提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            let obj = {
              businessNumber: this.formItem.businessNumber,
              deliveryOrderNo: id,
            };
            pickUpPackingDetail(obj)
              .then((res) => {
                if (res.code === 100) {
                  this.playAudio(res.content.isAllSort); // 音频
                  let message;
                  if (res.content.isAllSort) {
                    message = "全部货物已拣完";
                  } else {
                    message = "拣货成功";
                  }
                  this.$message({
                    message: message,
                    type: "success",
                    showClose: true,
                    duration: 1000 * 1.5 * 2,
                  });
                  this.loading = true;
                  setTimeout(() => {
                    this.loading = false;
                    this.formItem.goodsData = res.content.goods;
                  }, 800);
                } else {
                  this.formItem.id = "";
                }
                this.bool = true;
              })
              .finally(() => {
                this.formItem.goodsData[index].outStorageTime = false;
                this.bool = true;
              });
          })
          .catch(() => {
            this.formItem.goodsData[index].outStorageTime = false;
            this.bool = true;
          });
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

    // TODO 子包裹数据组装，箱子重量、体积、长宽高、和对应的选择的包裹数据，全部组装好再一次性提交(即确认打包的时候再提交，装箱的时候不做数据交互)
    /**
     * 点击选择快递【包裹】
     * @return {type} {description}
     */
    handlePackages(index) {
      this.loading = true;
      this.saving = true;
      this.formItem.packageList = this.formItem.goodsData;

      if (this.selectedPackageList.length > 0) {
        // 踢出已选中的货物
        let differeData = [...this.formItem.packageList].filter((x) =>
          [...this.selectedPackageList].every((y) => y.id !== x.id)
        );
        this.formItem.packageList = differeData;

        // 加上当前箱子已选中的货物
        if (this.formItem.sonPack[index].packagesInfo.length > 0) {
          this.formItem.sonPack[index].packagesInfo.forEach((ele) => {
            this.formItem.packageList.push(ele);
            this.multipleSelection.push(ele);
            setTimeout(() => {
              this.$refs.multipleTable.toggleRowSelection(ele);
            }, 100);
          });
        }
      }

      this.$nextTick(() => {
        this.dialogInfo = true;
        this.sonPackIndex = index; // 大箱子索引值
        setTimeout(() => {
          this.loading = false;
        }, 800);
      });

      this.saving = false;
    },

    /**
     * 选中快递
     * @return {type} {description}
     */
    handleSelectPackages() {
      if (this.multipleSelection.length > 0) {
        let packagesInfo =
          this.formItem.sonPack[this.sonPackIndex].packagesInfo;

        // 选中记录,去除之前选择的数据，后面再重新装载
        if (this.selectedPackageList.length > 0) {
          let differeData = [...this.selectedPackageList].filter((x) =>
            [...packagesInfo].every((y) => y.id !== x.id)
          );

          this.selectedPackageList = differeData;
        }
        // 每次确定重置箱子内的货物信息，然后再从新装载, 防止数量叠加
        this.formItem.sonPack[this.sonPackIndex].packagesInfo = [];
        let goodsIds = "";
        this.multipleSelection.forEach((ele) => {
          this.selectedPackageList.push(ele);
          goodsIds = goodsIds == "" ? ele.id : goodsIds + "," + ele.id;
          this.formItem.sonPack[this.sonPackIndex].packagesInfo.push(ele);
        });
        this.formItem.sonPack[this.sonPackIndex].goodsIds = goodsIds;
        this.formItem.sonPack[this.sonPackIndex].bool = true;

        // 确定箱子的货物之后，将箱子索引重置为空
        this.sonPackIndex = "";
        // 最后隐藏弹窗
        this.dialogInfo = false;
        this.saving = false;
      } else {
        this.$message({
          message: "至少勾选一个快递包裹!",
          type: "warning",
          showClose: true,
          duration: 1000 * 1.5 * 3,
        });
      }
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
          }
        })
        .finally(() => {
          this.saving = false;
        });
    },

    /**
     * TODO 确认打包
     *  参数：
     *            pid:  拼团订单ID
     *             id： 当前数据id
     *     customerId： 客户id
     *     incidental： 附加费
     *        remarks： 备注
     *  insideMessage： 客服备注
     *     packSonVos：{   子箱子数据
     *              sortNo：序号
     *              length：长
     *               width：宽
     *              height：高
     *        actualWeight：重量
     *           actualVol：体积
     *            goodsIds：货物ID集合字符串类型（例如1,2,3）
     *              }
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit() {
      let sonPackSort = 1;
      this.formItem.sonPack.forEach((ele) => {
        ele.sortNo = sonPackSort;
        ele.actualVol = (
          (Number(ele.length) * Number(ele.width) * Number(ele.height)) /
          1000000
        ).toFixed(4);
        ele.goodsIds = "";
        ele.packagesInfo.forEach((goods) => {
          ele.goodsIds =
            ele.goodsIds == "" ? goods.id : ele.goodsIds + "," + goods.id;
        });

        sonPackSort++;

        ele.packagesInfo = [];
        ele.goodsVoList = [];
        console.log("goodsIds", ele.goodsIds);
      });

      let packSonVos = JSON.stringify(this.formItem.sonPack);
      this.formItem.packSonVos = packSonVos;
      this.formItem.isPinPacked = 1;

      if (this.formItem.packType == 1) {
        confirmPack(this.formItem).then((res) => {
          if (res.code == 100) {
            this.$message({
              message: "订单打包完成",
              type: "success",
              showClose: false,
              duration: 1000 * 1.5 * 2,
            });
            this.GoBack();
          }
        });
      } else if (this.formItem.packType == 2) {
        editPack(this.formItem).then((res) => {
          if (res.code == 100) {
            this.$message({
              message: "订单信息修改完成",
              type: "success",
              showClose: false,
              duration: 1000 * 1.5 * 2,
            });
            this.GoBack();
          }
        });
      } else {
        this.$message({
          message: "该订单状态已变更，请核查信息",
          type: "success",
          showClose: false,
          duration: 1000 * 1.5 * 2,
        });
      }
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset(from) {},

    /**
     * 新增明细
     * @return {type} {description}
     */
    addTopic() {
      const obj = {
        sortNo: "",
        actualWeight: "",
        actualVol: "",
        length: "",
        width: "",
        height: "",
        bool: false,
        selectIds: "",
        goodsIds: "",
        packagesInfo: [],
      };
      this.formItem.sonPack.push(obj);
    },

    /**
     * 删除明细
     * @return {type} {description}
     */
    deleteTopic(index) {
      if (index > -1) {
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          let differeData = [...this.selectedPackageList].filter((x) =>
            [...this.formItem.sonPack[index].packagesInfo].every(
              (y) => y.id !== x.id
            )
          );
          

          this.selectedPackageList = differeData;

          this.formItem.sonPack.splice(index, 1);
        });
      }
    },

    // 刷新货物信息
    resetGoodsData(goodsList) {
      if (goodsList.length > 0) {
        // this.selectedPackageList 这个对象的内容保持不变；
        // this.formItem.goodsData 这个对象的内容要插入goodsList的数据；
        console.log("添加货物 --之前   被选中的货物数", this.selectedPackageList.length);
        
        this.goodsDataLoading = true;
        goodsList.forEach((goods) => {
          goods.sortType = 1;
          this.formItem.goodsData.push(goods);
        });

        console.log("添加货物 --之后   被选中的货物数", this.selectedPackageList.length);
        
        console.log("添加货物后的货物总数", this.formItem.goodsData.length);
        setTimeout(() => {
          this.goodsDataLoading = false;
        }, 600);
      }
    },

    /**
     * 返回上一层
     * @return {type} {description}
     */
    GoBack() {
      this.$router.push({
        name: "CompletePinDetail",
        query: { id: this.$route.query.routerId },
      });
      this.$store
        .dispatch("tagsView/delView", this.TagView)
        .then(({ visitedViews }) => {
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
          name: "CompletePinDetail",
          query: { id: this.$route.query.routerId },
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
      const title = "拼团打包明细";
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
    handleSearch(id) {
      console.log("页面加载中。。。。")
      this.loading = true;
      this.goodsDataLoading = true;
      getPinOrderDetail(id)
        .then((res) => {
          if (res.code === 100) {
            this.formItem = res.content;
            this.formItem.goodsData = res.content.goods; // 打包明细
            this.formItem.country = res.content.routeVo.country;

            if (res.content.packType == 1) {
              // 初始化已选中信息
              this.selectedPackageList = [];
              // 重置子包裹的信息，防止报错
              this.formItem.sonPack = [
                {
                  sortNo: "",
                  actualWeight: "",
                  actualVol: "",
                  length: "",
                  width: "",
                  height: "",
                  bool: false,
                  selectIds: "",
                  goodsIds: "",
                  packagesInfo: [],
                },
              ];
            } else {
              this.formItem.sonPack = res.content.pingPack;
              this.selectedPackageList = res.content.goods; // 已选中的货物
              this.formItem.packageList = res.content.goods; // 弹窗表格指向
              // 组装已选货物信息
              let pingPackIndex = 0;
              this.formItem.sonPack.forEach((pack) => {
                pack.bool = true;
                pack.packagesInfo =
                  res.content.pingPack[pingPackIndex].goodsVoList;
                pack.length = res.content.pingPack[pingPackIndex].actualL;
                pack.width = res.content.pingPack[pingPackIndex].actualW;
                pack.height = res.content.pingPack[pingPackIndex].actualH;
                pack.actualVol = res.content.pingPack[pingPackIndex].actualVol;

                pingPackIndex++;
              });
            }
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
            this.goodsDataLoading = false;
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

    addGoods() {
      let data = {
        customerId: this.formItem.customerId,
        packId: this.formItem.id,
      };

      this.$refs["addGoodsView"].handEditInfoModel(data);
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
  },
  mounted() {
    this.tempRoute = Object.assign({}, this.$route);
    this.setTagsViewTitle();
    this.$store.state.tagsView.visitedViews.forEach((ele) => {
      if (ele.title == "拼团打包明细") {
        this.TagView = ele;
      }
    });
    // 初始化数据
    this.handleSearch(this.$route.query.id);
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
