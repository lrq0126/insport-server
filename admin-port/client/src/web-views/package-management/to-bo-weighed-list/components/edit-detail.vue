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
                      min="0"
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
                      min="0"
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
                <div style="margin-bottom: 20px" v-if="insuranceInfo">
                  <p></p>
                  <span style="font-size: 20px">保险名称：{{insuranceInfo.insuranceName}}</span> 
                  <el-button size="mini" type="primary" plain
                      @click="lookClaimsDetail(insuranceInfo.claimsDetail)" >查看保险条例</el-button>
                      <p></p>
                      <div class="insurance-detail-class">
                        <el-table :data="insuranceInfo.insurancePrices" border style="width: 100%">
                            <el-table-column type="index" label="序号" align="center" width="70px"/>
                            <el-table-column label="选择" align="center" width="120px">
                              <template slot-scope="scope">
                                <img v-if="insurancePriceId == scope.row.id" 
                                src="../../../../icons/selected.png" alt="" @click="selectInsurance()"/>
                                <img v-else src="../../../../icons/select.png" alt=""  @click="selectInsurance(scope.row)"/>
                              </template>
                            </el-table-column>
                            <el-table-column label="保险费用" prop="price" align="center"/>
                            <el-table-column label="参保价格" prop="ginsengPrice" align="center"/>
                            <el-table-column label="理赔详情" align="center">
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
                </div>

                
                <div style="margin-bottom: 10px">
                  <el-button
                    type="primary"
                    plain
                    icon="el-icon-circle-plus-outline"
                    @click="addPackValuation()"
                    :disabled="formItem.packType == 3"
                    >添加产品明细</el-button
                  >
                  <span style="margin-left:3%">总货值：<span style="color: red">￥{{formItem.productValue ? formItem.productValue : '-'}}元</span></span>
                  <p></p>
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
                
              </div>
            </el-col>

            <el-col :span="20" style="margin-top: 40px">
              <el-row>
                <el-col :span="20">
                  <el-form-item label-width="50%">
                    <el-button
                      v-if="formItem.packType != 3"
                      :loading="saving"
                      type="warning"
                      @click="changeChannel()"
                      ><i class="el-icon-edit"></i>更改运输渠道</el-button
                    >
                    <el-button
                      :loading="saving"
                      v-if="formItem.packType == 2"
                      type="primary"
                      @click="handleSubmit('formPack')"
                      >确认修改</el-button
                    >
                    <el-button
                      :loading="saving"
                      v-if="formItem.packType == 2"
                      type="success"
                      @click="paymentDelivery()"
                      >付款发货</el-button
                    >
                    <el-button @click="GoBack">返回上一层</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-form>

        <el-dialog :visible.sync="payDialog" width="40%" title="订单付款" @close="resetOrderDetail()">
          <div style="font-size: 20px; margin-left: 30px">
            <p>订单号: {{formItem.orderNumber}}</p>
              <div style="padding-bottom: 40px">
                <div style="width: 20%; float: left; ">
                  运费: 
                </div>
                <div style="width: 80%; float: left; color: red">
                  <div style="float: left; width: 35%; text-align: right">{{priceDetail.freight}}</div>
                  <div style="float: left; width: 65%">元</div>
                </div>
              </div>
              <div v-if="priceDetail.incidental" style="padding-bottom: 40px">
                <div style="width: 20%; float: left; ">
                  附加费:
                </div>
                <div style="width: 80%; float: left; color: red">
                  <div style="float: left; width: 35%; text-align: right">{{priceDetail.incidental}}</div>
                  <div style="float: left; width: 65%">元</div>
                </div>
              </div>
              <div v-if="priceDetail.discount" style="padding-bottom: 40px">
                <div style="width: 20%; float: left; ">
                  优惠金额:
                </div>
                <div style="width: 80%; float: left; color: red">
                  <div style="float: left; width: 35%; text-align: right">-{{priceDetail.discount}}</div>
                  <div style="float: left; width: 65%">元</div>
                </div>
              </div>
              <div v-if="priceDetail.tariffsPrice" style="padding-bottom: 40px">
                <div style="width: 20%; float: left; ">
                  关税金额: 
                </div>
                <div style="width: 80%; float: left; color: red">
                  <div style="float: left; width: 35%; text-align: right">{{priceDetail.tariffsPrice}}</div>
                  <div style="float: left; width: 65%">元</div>
                </div>
              </div>
              <div v-if="priceDetail.insurancePrice" style="padding-bottom: 40px">
                <div style="width: 20%; float: left; ">
                  保险金额: 
                </div>
                <div style="width: 80%; float: left; color: red">
                  <div style="float: left; width: 35%; text-align: right">{{priceDetail.insurancePrice}}</div>
                  <div style="float: left; width: 65%">元</div>
                </div>
              </div>
              <div style="padding-bottom: 50px" v-if="customerIntegrals">
                <div style="width: 20%; float: left;">
                  积分: {{customerIntegrals}}
                </div>
                <div style="width: 80%; float: left; ">
                  <div style="float: left; width: 35%; text-align: right">
                    <span style="font-size: 16px;color: red">可抵扣:</span>
                    <span style="color: red">{{customerIntegrals}}</span>
                  </div>
                  <div style="float: left; width: 10%">
                    <span style="color: red">元</span>
                    </div>
                  <div style="float: left; width: 55%">
                    <img v-if="!checkedIntegrals" alt=""
                      src="../../../../icons/select.png"
                      @click="checkIntegrals()"/>
                    <img v-else alt=""
                      src="../../../../icons/selected.png"
                      @click="unCheckIntegrals()"/>
                  </div>
                </div>
              </div>
              <div style="padding-bottom: 50px">
                <div style="width: 20%; float: left;">
                  优惠券: 
                </div>
                <div style="width: 80%; float: left; " @click="openCouponsList(formItem.customerId)">
                  <div style="float: left; width: 35%; text-align: right">
                    <span v-if="couponsInfo.couponsAmount" style="color: red">-{{couponsInfo.couponsAmount}}</span>
                    <span v-else>请选</span>
                  </div>
                  <div style="float: left; width: 10%">
                    <span v-if="couponsInfo.couponsAmount" style="color: red">元</span>
                    <span v-else>择</span>
                    </div>
                  <div style="float: left; width: 55%">
                    <img src="../../../../icons/next.png" />
                  </div>
                </div>
              </div>
              <p> </p>
              <p>实际支付金额： <span style="color: red">{{payAmount}} 元</span></p>
              <p></p>
              <div style="margin-left: 10%">
                <el-button type="info" @click="payDialog = false">取  消</el-button>
                <el-button type="success" @click="confirmPayment()">确认付款发货</el-button>
              </div>
              
          </div>
        </el-dialog>

        <el-dialog
            title="选择优惠券"
            :visible.sync="couponsDialog"
            width="60%"
            :close-on-click-modal="false"
          >
            <div style="float: right; margin: 5px 20px 20px 0px">
              <el-button type="primary" @click="selectCoupons()"
                >不使用优惠券</el-button
              >
            </div>
            <el-table :data="couponsData" style="width: 100%" border>
              <el-table-column
                align="center"
                label="序号"
                width="70px"
                type="index"
              />
              <el-table-column align="center" label="优惠券名称" prop="couponsName" />
              <el-table-column
                align="center"
                label="优惠券金额"
                prop="couponsAmount"
              />
              <el-table-column align="center" label="过期时间" prop="timeOverdue" />
              <el-table-column align="center" label="操作" width="150px">
                <template slot-scope="scope">
                  <el-button
                    type="success"
                    v-if="formItem.actualPrice > scope.row.minimumAmount"
                    @click="selectCoupons(scope.row)"
                    >选 择</el-button
                  >
                  <el-button v-else disabled>不可用</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-dialog>
      </el-tab-pane>
      <el-tab-pane label="返回上一层" name="back"></el-tab-pane>
    </el-tabs>

    <!--更改运输渠道 -->
    <channel-list-modal
      ref="channelListShow"
      @updateForm="handleSearch($route.query.businessNumber)"
    />
  </div>
</template>

<script>
import {
  pickUpPackingDetail,
  printCustomerPackListing,
  printOrderListing,
  onePickUpPackingDetail,
  editPackMessage,buildOrderForYameiApi
} from "@/api/package-management/unpackaged-task";

import {
  editPack,
  waitingAreaDetail,
  paymentOrder 
} from "@/api/package-management/waiting-area";

import { getCustomerIntegrals } from "@/api/customer-information/client-list";
import { getInsuranceByOrderNumber } from "@/api/channel-management/insurance";
import { getCustomerCoupons } from "@/api/customer-information/customer-coupons";

import channelListModal from "./channel-list-modal";


export default {
  name: "Create",
  components: {
    channelListModal,
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
      payDialog: false,
      couponsDialog: false,

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
      checkedIntegrals: false,

      customerIntegrals: 0,
      usedIntegrals: 0,

      priceDetail: {},

      couponsData: [],
      couponsInfo: {},

      insurancePriceId: "",
      insuranceInfo: {},
      
      payAmount: "",

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
     * 创建亚美订单，并且返回转单号保存
     */
    buildOrderForYamei(from) {
      buildOrderForYameiApi(this.formItem)
        .then((res) => {
          if (res.code === 100) {
            this.$message({
              message: "创建成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.GoBack(); // 返回上一层
          }else{
            this.$message({
              message: res.message,
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

      if (this.formItem.packValuations.length > 0) {
        this.formItem.packValuations.forEach((ele) => {
          if (!ele.unitPrice || ele.unitPrice < 0) {
            this.$message({
              type: "error",
              message: "存在不正确【产品货值】单位价格，请重新填写",
            });
            isSubmit = false;
          }

          if (!ele.number || ele.number < 0) {
            this.$message({
              type: "error",
              message: "存在不正确【产品货值】数量，请重新填写",
            });
            isSubmit = false;
          }
        });
        this.formItem.packValuationData = JSON.stringify(
          this.formItem.packValuations
        );
      }

      this.formItem.packSonVos = JSON.stringify(this.formItem.sonPack);
      this.formItem.isPinPacked = 2;
      if (isSubmit) {
        this.saving = true;
        this.formItem.insurancePriceId = this.insurancePriceId
        editPack(this.formItem)
          .then((res) => {
            if (res.code == 100) {
              this.$message({
                message: "编辑完成",
                type: "success",
                showClose: false,
                duration: 1000 * 1.5 * 2,
              });
              this.GoBack();
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
      }
    },



    /**
     * 新增箱子明细
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
     * 删除箱子明细
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

    /**
     * 返回上一层
     * @return {type} {description}
     */
    GoBack() {
      this.$router.push({
        name: "WaitingArea",
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
          name: "WaitingArea",
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
              this.insurancePriceId = res.content.customerPack.packInsurancePrice.insurancePriceId
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
            this.priceDetail = res.content.customerPack.priceDetail;
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
          getInsuranceByOrderNumber(this.formItem.orderNumber).then((res) => {
              if (res.code == 100) {
                this.insuranceInfo = res.content;
              }
            });
            
          getCustomerIntegrals(this.formItem.customerId).then((res) => {
            if (res.code == 100) {
              this.customerIntegrals = res.content;
            }
          });
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
    
    selectInsurance(data){
      if(data){
        this.insurancePriceId = data.id
        this.formItem.insurancePrice = data.price
      }else{
        this.insurancePriceId = ""
        this.formItem.insurancePrice = 0
      }
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
        this.formItem.productValue = allGoodsValue.toFixed(2);
        this.formItem.tariffsPrice = (
          (parseFloat(allGoodsValue) * parseFloat(this.formItem.tariffs)) /
          100 * this.currencyData.alternateField
        ).toFixed(2);
      }
    },

    paymentDelivery(){
      this.payDialog = true;
      
      this.priceDetail.discount = this.formItem.discount
      this.priceDetail.incidental = this.formItem.incidental
      this.priceDetail.tariffsPrice = this.formItem.tariffsPrice
      this.priceDetail.insurancePrice = this.formItem.insurancePrice

      this.calculatePayAmount();
    },

    /** 积分 start */
    checkIntegrals() {
      this.checkedIntegrals = true;
      this.calculatePayAmount();
    },
    unCheckIntegrals() {
      this.checkedIntegrals = false;
      this.calculatePayAmount();
    },

    /** 优惠券操作satrt */
    openCouponsList(customerId) {
      this.couponsDialog = true;

      getCustomerCoupons(customerId).then((res) => {
        if (res.code == 100) {
          this.couponsData = res.content;
        }
      });
    },

    selectCoupons(data) {
      this.couponsDialog = false;
      
      if (data) {
        this.couponsInfo = data;
        this.priceDetail.couponsPrice = this.couponsInfo.couponsAmount;
      } else {
        this.couponsInfo = {};
        this.priceDetail.couponsPrice = 0;
      }
        this.calculatePayAmount();

    },
    
    confirmPayment() {
      this.$confirm("确认已跟客户核对好相关的订单信息？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let orderPayData = {
          customerId: this.formItem.customerId,
          packId: this.formItem.id,
          orderNumber: this.formItem.orderNumber,

          payAmount: this.payAmount,

          tariffsAmount: this.formItem.tariffsPrice,
          discount: this.formItem.discount,
          incidental: this.formItem.incidental,
          insurancePrice: this.insurancePrice,
          usedIntegrals: this.usedIntegrals,
          couponsAmount: this.couponsInfo.couponsAmount,

          insuranceId: this.insuranceInfo ? this.insuranceInfo.id : '',
          insurancePriceId: this.insurancePriceId,
          customerCouponsId: this.couponsInfo ? this.couponsInfo.id : '',
        }
        paymentOrder(orderPayData).then((res) => {
          if(res.code == 100){
            this.$message.success('支付成功');
            this.GoBack();
          }else{
            this.$message.error(res.content);
          }
        })
      });
    },

    // 价格计算
    calculatePayAmount(){
      if(!this.priceDetail.couponsPrice){
        this.priceDetail.couponsPrice = 0
      }
      if(!this.priceDetail.tariffsPrice){
        this.priceDetail.tariffsPrice = 0
      }
      if(!this.priceDetail.incidental){
        this.priceDetail.incidental = 0
      }
      if(!this.priceDetail.insurancePrice){
        this.priceDetail.insurancePrice = 0
      }
      if(!this.customerIntegrals){
        this.customerIntegrals = 0
      }
      this.payAmount = (parseFloat(this.priceDetail.freight)  - parseFloat(this.priceDetail.discount) 
                      + parseFloat(this.priceDetail.incidental) + parseFloat(this.priceDetail.tariffsPrice)
                      + parseFloat(this.priceDetail.insurancePrice) - parseFloat(this.priceDetail.couponsPrice)).toFixed(2)

      if(this.checkedIntegrals){
        if(this.payAmount > this.customerIntegrals){
        this.usedIntegrals = this.customerIntegrals
        }else{
          this.usedIntegrals = this.payAmount
        }
        this.payAmount = (parseFloat(this.payAmount) - parseFloat(this.usedIntegrals)).toFixed(2)
      }

    },

    resetOrderDetail() {
      this.checkedIntegrals = false;
      this.payAmount = "";

      this.usedIntegrals = 0;

      this.priceDetail.couponsPrice = 0;

      this.couponsData = [];
      this.couponsInfo = {};

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

  .insurance-detail-class{
    width: 100%;
    display: block;
    font-size: 20px;
  }
}
</style>
