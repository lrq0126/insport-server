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
      <el-tab-pane label="打包明细" name="list">
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
                  <el-form-item label="业务号：">
                    <el-input
                      v-model="formItem.businessNumber"
                      :disabled="true"
                      placeholder="请输入业务号"
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
                  <el-form-item label="包裹数量：">
                    <el-input
                      v-model="formItem.packNum"
                      :disabled="true"
                      placeholder="请输入包裹数量"
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
              </el-col>

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
                    <el-select v-model="formItem.routeType" :disabled="true">
                      <el-option label="海运" value="2" />
                      <el-option label="空运" value="1" />
                    </el-select>
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
                    id="deliveryOrderNoFu"
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
                    min-width="180"
                    prop="deliveryOrderNo"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    label="重量/KG"
                    min-width="120"
                    prop="kg"
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
                    type="success"
                    style="margin-bottom: 5px"
                    icon="el-icon-circle-plus-outline"
                    @click="addOrder"
                    >订单分箱</el-button
                  > 
                  <el-button
                    type="primary"
                    plain
                    style="margin-bottom: 5px"
                    icon="el-icon-circle-plus-outline"
                    @click="addTopic"
                    >新增包裹</el-button
                  > 
                  <el-button
                    type="warning"
                    plain
                    style="margin-bottom: 5px"
                    icon="el-icon-circle-plus-outline"
                    @click="openSonPackExcelDialog"
                    >Excel文本导入</el-button
                  >
                  <el-table
                    :data="formItem.sonPack"
                    fit
                    highlight-current-row
                    style="width: 100%; margin-top: 20px"
                    v-loading="sonPackLoading"
                  >
                    <el-table-column
                      label="序号"
                      width="80"
                      type="index"
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
                            v-model="scope.row.actualWeight"
                            type="number"
                            placeholder="请输入实重"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="长/CM" align="center">
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 15px">
                          <el-input
                            v-model="scope.row.length"
                            placeholder="请输入长度"
                            type="number"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="宽/CM" align="center">
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 15px">
                          <el-input
                            v-model="scope.row.width"
                            placeholder="请输入宽度"
                            type="number"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="高/CM" align="center">
                      <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 15px">
                          <el-input
                            v-model="scope.row.height"
                            placeholder="请输入高度"
                            type="number"
                          ></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column label="体积/m³" align="center">
                      <template slot-scope="scope">
                        <span>{{
                          scope.row.length | allChecked(scope.row.width, scope.row.height)
                        }}</span>
                      </template>
                    </el-table-column>

                    <el-table-column label="体积重量" align="center">
                      <template slot-scope="scope">
                        <span v-if="transportRoute.routeType == 1">{{scope.row.length | checkVolWeight(scope.row.width, scope.row.height, transportRoute.volConversion)}}</span>
                        <span v-else>/</span>
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

              <el-col :span="8">
                <el-col :span="24">
                  <el-form-item label="是否敏感货：">
                    <!-- `checked` 为 true 或 false -->
                    <el-checkbox
                      v-model="formItem.isSensitive"
                      true-label="1"
                      false-label="0"
                    />
                    <span style="color: red">(是敏感货物请勾选)</span>
                  </el-form-item>

                  <el-form-item label="预付关税：" v-if="formItem.isTariffs">
                    <el-input
                      v-model="formItem.tariffsPrice"
                      :disabled="true"
                      type="number"
                      style="width: 180px"
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

            <el-col>
              <!-- <el-divider content-position="left"><i class="el-icon-tickets"></i>产品&货值信息</el-divider> -->
              <div
                @click="packValuationView = !packValuationView"
                class="packValuation"
              >
                <div class="packValuationTitle">
                  <span>产品货值信息</span>
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
                    >添加产品明细</el-button
                  >
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
                      <el-input v-model="scope.row.productName"></el-input>
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
                  <el-form-item label-width="50%">
                    <el-button
                      :loading="saving"
                      type="warning"
                      @click="changeChannel()"
                      ><i class="el-icon-edit"></i>更改运输渠道</el-button
                    >
                    <el-button
                      :loading="saving"
                      type="success"
                      @click="editMessage('formPack')"
                      ><i class="el-icon-edit"></i>编辑备注信息</el-button
                    >
                    <el-button :loading="saving" type="primary" @click="handleSubmit('formPack')">确认打包</el-button>

                    <!-- <el-button :loading="saving" type="primary" @click="speedinessPack('formPack')">快速打包</el-button> -->

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
    <!-- <el-dialog :visible.sync="dialogInfo" title="包裹列表信息" width="80%">
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
          label="包裹名称"
          min-width="160"
          prop="goodsName"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="快递单号/唛头"
          min-width="180"
          prop="deliveryOrderNo"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="重量/KG"
          min-width="120"
          prop="kg"
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
    </el-dialog> -->


    <!--更改运输渠道 -->
    <channel-list-modal
      ref="channelListShow"
      @updateForm="handleSearch($route.query.id)"
    />
    <son-pack-excel-model-view ref="sonPackExcelModelView" @updateSonPack="updateSonPack"/>
    <!-- end -->
  </div>
</template>

<script>
import {
  pickUpPackingDetail,
  getPackingDetail,
  InitializePickUpPackingDetail,
  printCustomerPackListing,
  printOrderListing,
  onePickUpPackingDetail,
  editPackMessage,
  confirmPack,
  getWaitPackDetail,
  updatePackValuation,
  orderSplit
} from "@/api/package-management/unpackaged-task";
import quillConfig from "@/utils/quill-config.js";
import channelListModal from "./channel-list-modal";

import SonPackExcelModelView from "./son-pack-excel-model";
import { getCountriesList } from "@/api/channel-management/countries-list";

export default {
  name: "Create",
  components: {
    channelListModal,
    SonPackExcelModelView
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
      activeName: "list",
      packValuationView: true,
      dialogClaims: false,
      loading: false,
      sonPackLoading: false,
      saving: false,
      currencyData: [],
      transportRoute: {},
      formItem: {
        id: "",
        customerId: "",
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
        isTariffs: "",
        tariffs: "",
        tariffsPrice:"",

        packageList: [], // 弹窗中包裹列表
        goodsData: [], // 所有货物明细
        selectedPackageList: [], // 弹窗中已被选择的包裹

        sonPackIndex: "", //箱子索引
        packingType: "normal",
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

        // 产品货值
        packValuations: [
          {
            productName: "",
            unitPrice: "",
            number: "",
            goodsValue: "",
          },
        ],

        //保险信息
        packInsurancePrices: [],
      },
      multipleSelection: [],
      index: "", // 选中快递包裹索引值
      dialogInfo: false,

      deliveryOrderNo: "", // 用于扫码拣货

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
    };
  },
  filters: {
    allChecked(length, width, height) {
      if (!length) return 0;
      if (!width) return 0;
      if (!height) return 0;
      
      return (Number(length) * Number(width) * Number(height)) / 1000000;
    },

    checkVolWeight(length, width, height, volConversion) {
      if (!length) return 0;
      if (!width) return 0;
      if (!height) return 0;
      let vol = Number(length) * Number(width) * Number(height);
      
      return parseFloat(vol / volConversion).toFixed(2);
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
    addPackValuation() {
      let packValuation = {
        productName: "",
        unitPrice: 1,
        number: 1,
        goodsValue: 1,
      };
      this.formItem.packValuations.push(packValuation);
    },

    removePackValuation(index) {
      this.formItem.packValuations.splice(index, 1);
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
      let productValue = 0;
      this.formItem.packValuations.forEach((ele) => {
        productValue = productValue + parseFloat(ele.goodsValue)
      })
      this.formItem.productValue = productValue.toFixed(2);

      if (this.formItem.isTariffs == 1) {
        let allGoodsValue = 0;
        this.formItem.packValuations.forEach((ele) => {
          allGoodsValue = allGoodsValue + parseFloat(ele.goodsValue);
        });
        this.formItem.tariffsPrice = (
          (parseFloat(allGoodsValue) * parseFloat(this.formItem.tariffs)) / 100 * this.currencyData.alternateField).toFixed(2);
      }
    },

    lookClaimsDetail(claimsDetail) {
      this.formItem.claimsDetail = claimsDetail;
      this.dialogClaims = true;
    },

    openSonPackExcelDialog(){
      this.$refs['sonPackExcelModelView'].openDialog();
    },
    /**
     * 更新子包裹数据
     */
    updateSonPack(sonPackData){
      this.sonPackLoading = true;

      this.formItem.sonPack = [];

      sonPackData.forEach((ele) => {
        ele.packagesInfo = [];
        // ele.actualVol = ((Number(ele.length) * Number(ele.width) * Number(ele.height)) / 1000000).toFixed(4);

        this.formItem.sonPack.push(ele);

      });
      setTimeout(() => {
        this.sonPackLoading = false;
      }, 300)

    },
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
                }, 300);

                this.formItem.deliveryOrderNo = "";
                var elInput = document.getElementById("deliveryOrderNoFu"); //根据id选择器选中对象
                elInput.focus();
              } else {
                let buttonAudio = document.getElementById("eventAudioError");
                buttonAudio.play();
                this.$message({
                  message: "拣货失败",
                  type: "error",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
                this.formItem.deliveryOrderNo = "";
                var elInput = document.getElementById("deliveryOrderNoFu"); //根据id选择器选中对象
                elInput.focus();
              }
            })
            .catch((e) => {
              let buttonAudio = document.getElementById("eventAudioError");
              buttonAudio.play();
              this.formItem.deliveryOrderNo = "";
              var elInput = document.getElementById("deliveryOrderNoFu"); //根据id选择器选中对象
              elInput.focus();
            })
            .finally(() => {
              this.saving = false;
              // this.handleReset("formItem");
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
            // this.handleReset('formItem')
            // this.handleSearch(this.$route.query.id) // 刷新页面
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
          // this.handleReset("formItem");
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
                  // this.handleSearch(this.$route.query.id) // 刷新页面
                  this.loading = true;
                  setTimeout(() => {
                    this.loading = false;
                    this.formItem.goodsData = res.content.goods;
                  }, 300);
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

    addOrder(){
      orderSplit(this.formItem.id).then((res) => {
        if(res.code == 100){
          this.$message({
            message: "订单分箱成功，已复制订单",
            type: "success"
          })
        }else{
          this.$message({
            message: "订单分箱失败",
            type: "error"
          })
        }
      })
    },

    /**
     * 编辑备注信息
     */
    editMessage(from) {
      let isOk = true;
      if(this.formItem.packValuations.length > 0){
        this.formItem.packValuations.forEach((ele) => {
          if(!ele.productName){
            isOk = false;
            this.$message({
              message: "请输入产品品名",
              type: "error"
            })
          }
          if(!ele.unitPrice || ele.unitPrice == 0){
            isOk = false;
            this.$message({
              message: "请输入产品单价",
              type: "error"
            })
          }
          if(!ele.number || ele.number == 0){
            isOk = false;
            this.$message({
              message: "请输入产品数量",
              type: "error"
            })
          }
        })
      }
      if(isOk){
        // 对货值进行修改
        if(this.formItem.packValuations.length > 0){
          let data = {
            packId: this.formItem.id,
            packValuations: JSON.stringify(this.formItem.packValuations)
          }
          console.log("请求参数：", data);
          updatePackValuation(data);
        }

        editPackMessage(this.formItem).then((res) => {
          if (res.code === 100) {
            this.$message({
              message: "编辑成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.GoBack(); // 返回上一层
          }
        }).finally(() => {
          this.saving = false;
        });
      }
    },

    /**
     * 确认打包
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit() {
      let isSubmit = true;
      if (this.formItem.sonPack.length < 1) {
        this.$message({
          type: "error",
          message: "请填写打包好的箱子数据！",
        });
        isSubmit = false;
      } else {
        this.formItem.sonPack.forEach((ele) => {
          if (!ele.actualWeight || ele.actualWeight <= 0) {
            this.$message({
              type: "error",
              message: "存在不正确的【包裹】【重量】信息，请重新填写！",
            });
            isSubmit = false;
          }
          if (!ele.length || ele.length <= 0) {
            this.$message({
              type: "error",
              message: "存在不正确的【包裹】【长度】信息，请重新填写！",
            });
            isSubmit = false;
          }
          if (!ele.width || ele.width <= 0) {
            this.$message({
              type: "error",
              message: "存在不正确的【包裹】【宽度】信息，请重新填写！",
            });
            isSubmit = false;
          }
          if (!ele.height || ele.height <= 0) {
            this.$message({
              type: "error",
              message: "存在不正确的【包裹】【高度】信息，请重新填写！",
            });
            isSubmit = false;
          }
        });
      }

      this.formItem.sonPack.forEach((ele) => {
        ele.packagesInfo = [];
        ele.actualVol = (
          (Number(ele.length) * Number(ele.width) * Number(ele.height)) /
          1000000
        ).toFixed(4);
      });

      if (this.formItem.isTariffs == 1) {
        if (this.formItem.packValuations.length > 0) {
          this.formItem.packValuations.forEach((ele) => {
            if (!ele.unitPrice || ele.unitPrice < 0) {
              this.$message({
                type: "error",
                message: "存在不正确【产品货值】-【单位价格】，请重新填写",
              });
              isSubmit = false;
            }

            if (!ele.number || ele.number < 0) {
              this.$message({
                type: "error",
                message: "存在不正确【产品货值】-【数量】，请重新填写",
              });
              isSubmit = false;
            }
          });
        } else {
          this.$message({
            type: "error",
            message: "当前渠道需要预付关税，请填写相关的产品货值信息",
          });
          isSubmit = false;
        }
      }
      if (this.formItem.packValuations.length == 1) {
        this.formItem.packValuations.forEach((ele) => {
          if (!ele.productName && !ele.unitPrice && !ele.number) {
            this.formItem.packValuations = "";
          }
        });
      }
      this.formItem.packValuationData = JSON.stringify(
        this.formItem.packValuations
      );

      this.formItem.packSonVos = JSON.stringify(this.formItem.sonPack);
      this.formItem.isPinPacked = 2;

      if (isSubmit) {
        this.saving = true;
        confirmPack(this.formItem)
          .then((res) => {
            if (res.code == 100) {
              this.$message({
                message: "打包完成",
                type: "success",
                showClose: false,
                duration: 1000 * 1.5 * 2,
              });
              this.GoBack();
              // 打印面单
              printOrderListing(this.formItem.businessNumber).then((res) => {
                if (res.code === 100) {
                  // window.location = 'http://' + res.content.url
                  window.open("http://" + res.content.url);
                }
              });
            } else {
              this.$message({
                message: res.content,
                type: "warning",
                showClose: true,
                duration: 1000 * 1.5 * 2,
              });
            }
          })
          .finally(() => {
            this.saving = false;
          });
      }
    },


    /**
     * 快速打包
     */
    speedinessPack(){
      let isSubmit = true;
      if (this.formItem.sonPack.length < 1) {
        this.$message({
          type: "error",
          message: "请填写打包好的箱子数据！",
        });
        isSubmit = false;
      } else {
        this.formItem.sonPack.forEach((ele) => {
          ele.actualWeight = 0;
          ele.length = 1;
          ele.width = 1;
          ele.height = 1;
        });
      }

      this.formItem.sonPack.forEach((ele) => {
        ele.packagesInfo = [];
        ele.actualVol = (
          (Number(ele.length) * Number(ele.width) * Number(ele.height)) /
          1000000
        ).toFixed(4);
      });

      if (this.formItem.isTariffs == 1) {
        if (this.formItem.packValuations.length > 0) {
          this.formItem.packValuations.forEach((ele) => {
            if (!ele.unitPrice || ele.unitPrice < 0) {
              this.$message({
                type: "error",
                message: "存在不正确【产品货值】-【单位价格】，请重新填写",
              });
              isSubmit = false;
            }

            if (!ele.number || ele.number < 0) {
              this.$message({
                type: "error",
                message: "存在不正确【产品货值】-【数量】，请重新填写",
              });
              isSubmit = false;
            }
          });
        } else {
          this.$message({
            type: "error",
            message: "当前渠道需要预付关税，请填写相关的产品货值信息",
          });
          isSubmit = false;
        }
      }
      if (this.formItem.packValuations.length == 1) {
        this.formItem.packValuations.forEach((ele) => {
          if (!ele.productName && !ele.unitPrice && !ele.number) {
            this.formItem.packValuations = "";
          }
        });
      }
      this.formItem.packValuationData = JSON.stringify(
        this.formItem.packValuations
      );

      this.formItem.packSonVos = JSON.stringify(this.formItem.sonPack);
      this.formItem.isPinPacked = 2;

      if (isSubmit) {
        this.saving = true;
        this.formItem.packingType = "speediness"
        confirmPack(this.formItem)
          .then((res) => {
            if (res.code == 100) {
              this.$message({
                message: "快速打包完成，等待称重",
                type: "success",
                showClose: false,
                duration: 1000 * 1.5 * 2,
              });
              this.GoBack();
            } else {
              this.$message({
                message: res.content,
                type: "warning",
                showClose: true,
                duration: 1000 * 1.5 * 2,
              });
            }
          })
          .finally(() => {
            this.saving = false;
          });
      }
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
     * 新增明细
     * @return {type} {description}
     */
    addTopic() {
      const obj = {
        sortNo: this.formItem.sonPack.length + 1,
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
          this.formItem.sonPack.splice(index, 1);
        });
      }
    },

    /**
     * 返回上一层
     * @return {type} {description}
     */
    GoBack() {
      this.$router.push({
        name: "UnpackagedTask",
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
          name: "UnpackagedTask",
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
      // getPackingDetail
      // getWaitPackDetail
      getPackingDetail(businessNumber)
        .then((res) => {
          if (res.code === 100) {
            this.formItem = res.content.customerPack;

            // 货币类型
            this.currencyData = res.content.customerPack.routeVo.currencyData;

            this.formItem.goodsData = res.content.goods; // 打包明细
            this.formItem.country = res.content.customerPack.routeVo.country;
            this.formItem.countryId =
              res.content.customerPack.routeVo.countryId;
            this.formItem.routeType = String(
              res.content.customerPack.routeVo.transportType
            );
            this.formItem.packNum = res.content.goods.length;

            this.formItem.packValuations =
              res.content.customerPack.packValuations;
            this.formItem.isTariffs =
              res.content.customerPack.routeVo.isTariffs;
            this.formItem.tariffs = res.content.customerPack.routeVo.tariffs;
            if (res.content.customerPack.packInsurancePrice) {
              this.formItem.packInsurancePrices = [];
              this.formItem.packInsurancePrices.push(
                res.content.customerPack.packInsurancePrice
              );
            }
            this.transportRoute = res.content.customerPack.routeVo;
            // 初始化已选中信息
            this.selectedPackageList = [];
            // 重置子包裹的信息，防止报错
            this.formItem.sonPack = [
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
            ];
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 初始化货物状态
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    initializeSearch(id) {
      // this.loading = true
      InitializePickUpPackingDetail(id)
        .then((res) => {
          if (res.code === 100) {
          }
        })
        .finally(() => {
          setTimeout(() => {
            // this.loading = false
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

    /**
     * 更改运输渠道
     * @return {type} {description}
     */
    changeChannel() {
      this.$refs["channelListShow"].handListInfoModel(
        this.formItem.countryId,
        this.formItem.id
      );
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
