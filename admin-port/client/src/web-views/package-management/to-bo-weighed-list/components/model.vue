/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
  <div class="model-view">
    <!--确认打包信息 -->
    <el-dialog
      :visible.sync="dialogPackageInfo"
      :close-on-click-modal="false"
      title="拆包费用信息"
      width="1000px"
      @close="handleReset('searchForm')"
    >
      <!-- 搜索 -->
      <el-form
        ref="searchForm"
        :model="formItem"
        :rules="formItemRules"
        label-width="110px"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="拆包费用金额" prop="money">
              <el-input
                type="text"
                v-model="formItem.money"
                placeholder="请输入拆包费用金额"
                style="width: 300px"
              >
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <!-- 表格 -->
            <el-table
              :data="formItem.storageData"
              border
              max-height="500"
              v-loading="loading"
              style="width: 100%"
            >
              <el-table-column
                label="序号"
                type="index"
                align="center"
                width="50"
              ></el-table-column>

              <el-table-column
                label="客户名称"
                align="center"
                min-width="120"
                prop="customerName"
              ></el-table-column>

              <el-table-column
                label="货物名称"
                align="center"
                min-width="160"
                prop="goodsName"
              >
              </el-table-column>

              <el-table-column
                label="快递单号"
                align="center"
                min-width="160"
                prop="deliveryOrderNo"
              ></el-table-column>

              <el-table-column
                label="仓库位置"
                align="center"
                min-width="200"
                prop="location"
              >
                <el-table-column label="区号" align="center" min-width="100">
                  <template slot-scope="scope">
                    <el-form-item
                      label-width="0"
                      :prop="'storageData.' + scope.$index + '.storageArea'"
                      :rules="{
                        required: true,
                        message: '请选择区号',
                        trigger: 'change',
                      }"
                    >
                      <el-select
                        v-model="scope.row.storageArea"
                        placeholder="请选择区号"
                        @change="changeSelectArea($event, scope.$index)"
                        style="width: 120px"
                      >
                        <el-option
                          v-for="(item, index) in storageAreaData"
                          :key="index"
                          :value="item.storageArea"
                          :label="item.storageArea"
                        />
                      </el-select>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column label="排号" align="center" min-width="100">
                  <template slot-scope="scope">
                    <el-form-item
                      label-width="0"
                      :prop="'storageData.' + scope.$index + '.storageRow'"
                      :rules="{
                        required: true,
                        message: '请选择排号',
                        trigger: 'change',
                      }"
                    >
                      <el-select
                        v-model="scope.row.storageRow"
                        placeholder="请选择排号"
                        style="width: 120px"
                      >
                        <el-option
                          v-for="(item, index) in storageRowData"
                          :key="index"
                          :value="item"
                          :label="item"
                        />
                      </el-select>
                    </el-form-item>
                  </template>
                </el-table-column>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button v-waves type="info" plain @click="dialogPackageInfo = false"
          >取消</el-button
        >
        <el-button
          v-waves
          type="primary"
          @click="handlePackageSubmit('searchForm')"
          >提交</el-button
        >
      </span>
    </el-dialog>

    <!--查看箱子信息 -->
    <el-dialog
      :visible.sync="dialogOrderInfo"
      :close-on-click-modal="false"
      title="查看箱子信息"
      width="90%"
    >
      <!-- 表格 -->
      <el-table
        :data="orderData"
        border
        max-height="500"
        v-loading="loading"
        style="width: 100%"
      >
        <el-table-column
          label="序号"
          type="index"
          align="center"
          width="50"
        ></el-table-column>

        <el-table-column
          label="包裹订单号"
          align="center"
          min-width="160"
          prop="packOrder"
        ></el-table-column>

        <!-- <el-table-column label="快递数量" align="center" min-width="160">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.packOrder"
              type="primary"
              size="mini"
              plain
              @click="handleCheck(scope.row.packOrder)"
              :disabled="
                scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                  ? false
                  : true
              "
              >{{ scope.row.goodsNum }}件</el-button
            >
          </template>
        </el-table-column> -->

        <el-table-column
          label="重量/KG"
          align="center"
          min-width="120"
          prop="actualWeight"
        ></el-table-column>

        <el-table-column
          label="体积/m³"
          align="center"
          min-width="120"
          prop="actualVol"
        ></el-table-column>
        <el-table-column label="体积重/KG"
                                 align="center"
                                 min-width="160"
                                 prop="volWeight"></el-table-column>
        <el-table-column
          label="箱子尺寸/cm"
          align="center"
          min-width="120"
          prop="packLength"
        ></el-table-column>

        <!-- <el-table-column label="操作员"
                                 align="center"
                                 min-width="160"
                                 prop="operatorName"></el-table-column> -->

        <el-table-column
          label="打包时间"
          align="center"
          min-width="160"
          prop="packTime"
        ></el-table-column>
      </el-table>
      <!-- <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogOrderInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handlePackageSubmit('formPackage')">提交</el-button>
            </span> -->
    </el-dialog>

    <!--查看快递包裹信息 -->
    <el-dialog
      :visible.sync="dialogPackInfo"
      :close-on-click-modal="false"
      title="查看快递包裹信息"
      width="90%"
    >
      <!-- 搜索 -->
      <!-- <el-form ref="searchForm"
                     :model="pageInfo"
                     inline
                     label-width="75px">
                <el-form-item label="包裹订单">
                    <span>{{pageInfo.packOrder}}</span>
                </el-form-item>

                <el-form-item label="体积/m³">
                    <span>{{pageInfo.actualVol}}</span>
                </el-form-item>

                <el-form-item label="重量/KG">
                    <span>{{pageInfo.actualWeight}}</span>
                </el-form-item>

                <el-form-item label="打包价格">
                    <span style="color: red">￥{{pageInfo.actualPrice}}</span>
                </el-form-item>

                <el-form-item label="打包时间">
                    <span>{{pageInfo.packTime}}</span>
                </el-form-item>

            </el-form> -->

      <!-- 表格 -->
      <el-table
        :data="courierData"
        border
        max-height="500"
        v-loading="loading"
        style="width: 100%"
      >
        <el-table-column
          label="序号"
          type="index"
          align="center"
          width="50"
        ></el-table-column>

        <el-table-column
          label="快递单号/唛头"
          align="center"
          min-width="160"
          prop="deliveryOrderNo"
        ></el-table-column>

        <el-table-column
          label="包裹名称"
          align="center"
          min-width="160"
          prop="goodsName"
        >
        </el-table-column>

        <el-table-column
          label="包装类型"
          align="center"
          min-width="160"
          prop="packageType"
        >
        </el-table-column>

        <el-table-column
          label="重量/KG"
          align="center"
          min-width="120"
          prop="kg"
        ></el-table-column>

        <el-table-column
          label="体积/m³"
          align="center"
          min-width="120"
          prop="vol"
        ></el-table-column>

        <el-table-column
          label="入库时间"
          align="center"
          min-width="160"
          prop="inStorageTime"
        ></el-table-column>

        <el-table-column
          label="备注信息"
          align="center"
          min-width="160"
          prop="message"
        ></el-table-column>
      </el-table>
      <!-- <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogOrderInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handlePackageSubmit('formPackage')">提交</el-button>
            </span> -->
    </el-dialog>

    <!--修改发票信息 -->
    <el-dialog
      :visible.sync="dialogInvoiceInfo"
      :close-on-click-modal="false"
      title="修改发票信息"
      width="1160px"
    >
      <!-- 内容 -->
      <div class="invoice-box">
        <div class="invoicMain">
          <div
            class="invoiceHeader"
            style="justify-content: space-between; position: relative"
          >
            <ul class="headerLeft">
              <li>
                <img src="../../../../assets/images/login_logo.png" alt="" />
              </li>
            </ul>
            <div class="headerMiddle">
              <h1>Commerical Invoice</h1>
              <h1>发票</h1>
            </div>
            <ul class="headerLeft">
              <li
                style="
                  color: #9c5223;
                  font-size: 18px;
                  font-weight: bold;
                  margin-bottom: 10px;
                "
              >
                订单号：{{ commericalInvoice.orderNumber }}
              </li>
              <li style="color: #9c5223; font-size: 18px; font-weight: bold">
                转运单号：{{ commericalInvoice.agentNumber }}
              </li>
              <li
                style="
                  color: #9c5223;
                  font-size: 18px;
                  font-weight: bold;
                  display: none;
                "
              >
                id:{{ commericalInvoice.id }}
              </li>
              <li
                style="
                  color: #9c5223;
                  font-size: 18px;
                  font-weight: bold;
                  display: none;
                "
              >
                id:{{ commericalInvoice.packId }}
              </li>
            </ul>
          </div>
          <div class="invoiceBody">
            <div class="userInfo" style="height: 40px">
              <!-- <div class="buy">购买方</div> -->
              <ul
                style="
                  border-right: 1px solid rgb(156, 82, 35);
                  text-align: center;
                "
              >
                <li style="line-height: 40px">
                  <label>consignor(发货人)</label>
                </li>
              </ul>
              <ul style="text-align: center">
                <li style="line-height: 40px">
                  <label>Recipient(收件人)</label>
                </li>
              </ul>
            </div>
            <div>
              <div class="GoodsTable" cellpadding="0" cellspacing="0">
                <div
                  class="total"
                  style="border-bottom: 1px solid #9c5223; display: flex"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Company name<br />(公司名)
                  </div>
                  <div style="width: 384px; border-right: 1px solid #9c5223">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.conCompanyName"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Company name<br />(公司名)
                  </div>
                  <div style="width: 383px; border-right: none">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.recCompanyName"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                </div>
                <div
                  class="total"
                  style="border-bottom: 1px solid #9c5223; display: flex"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Sender's name<br />(发件人名)
                  </div>
                  <div style="width: 384px; border-right: 1px solid #9c5223">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.conSenderName"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Receiver<br />(收件人名)
                  </div>
                  <div style="width: 384px; border-right: none">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.recReceiverName"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                </div>
                <div
                  class="total"
                  style="display: flex; border-bottom: 1px solid #9c5223"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    TEL (电话)
                  </div>
                  <div
                    style="
                      width: 384px;
                      border-right: 1px solid #9c5223;
                      display: flex;
                    "
                  >
                    <div style="width: 220px; height: 40px">
                      <el-input
                        type="text"
                        v-model="commericalInvoice.conTel"
                        placeholder=""
                        style="width: 180px; border: none"
                      >
                      </el-input>
                    </div>
                    <div
                      style="
                        width: 108px;
                        padding: 10px 0px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      CODE (邮编)
                    </div>
                    <div style="width: 110px; height: 40px">
                      <el-input
                        type="text"
                        v-model="commericalInvoice.conCode"
                        placeholder=""
                        style="width: 108px; border: none"
                      >
                      </el-input>
                    </div>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    TE (电话)
                  </div>
                  <div style="width: 384px; display: flex">
                    <div style="width: 220px; height: 40px">
                      <el-input
                        type="text"
                        v-model="commericalInvoice.recTel"
                        placeholder=""
                        style="width: 180px; border: none"
                      >
                      </el-input>
                    </div>
                    <div
                      style="
                        width: 108px;
                        padding: 10px 0px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      CODE (邮编)
                    </div>
                    <div style="width: 110px; height: 40px">
                      <el-input
                        type="text"
                        v-model="commericalInvoice.recCode"
                        placeholder=""
                        style="width: 108px; border: none"
                      >
                      </el-input>
                    </div>
                  </div>
                </div>
                <div
                  class="total"
                  style="border-bottom: 1px solid #9c5223; display: flex"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Country<br />(国家)
                  </div>
                  <div style="width: 384px; border-right: 1px solid #9c5223">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.conContry"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Country<br />(国家)
                  </div>
                  <div style="width: 384px; border-right: none">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.recContry"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                </div>
                <div
                  class="total"
                  style="border-bottom: 1px solid #9c5223; display: flex"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Address<br />(地址)
                  </div>
                  <div style="width: 384px; border-right: 1px solid #9c5223">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.conAddress"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Address<br />(地址)
                  </div>
                  <div style="width: 384px; border-right: none">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.recAddress"
                      placeholder=""
                      style="width: 380px; border: none"
                    >
                    </el-input>
                  </div>
                </div>
                <div
                  class="total"
                  style="
                    border-bottom: 1px solid #9c5223;
                    display: flex;
                    justify-content: center;
                    padding: 10px 0;
                  "
                >
                  Description of Goods（货物信息）
                </div>
                <div
                  class="total"
                  style="display: flex; border-bottom: 1px solid #9c5223"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    No.of Items
                  </div>
                  <div
                    style="
                      width: 384px;
                      border-right: 1px solid #9c5223;
                      display: flex;
                    "
                  >
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      Desciription of Goods
                    </div>
                    <div
                      style="
                        width: 108px;
                        padding: 10px 0px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      HS Code
                    </div>
                    <div
                      style="
                        width: 110px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      Material
                    </div>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    purpose
                  </div>
                  <div style="width: 384px; display: flex">
                    <div
                      style="
                        width: 110px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      Qty
                    </div>
                    <div
                      style="
                        width: 108px;
                        padding: 10px 0px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      Unit Value
                    </div>
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      Total Value
                    </div>
                  </div>
                </div>
                <div
                  class="total"
                  style="display: flex; border-bottom: 1px solid #9c5223"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    项目数量
                  </div>
                  <div
                    style="
                      width: 384px;
                      border-right: 1px solid #9c5223;
                      display: flex;
                    "
                  >
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      货物描述(中，英文产品名)
                    </div>
                    <div
                      style="
                        width: 108px;
                        padding: 10px 0px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      海关编码
                    </div>
                    <div
                      style="
                        width: 110px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      材质
                    </div>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    用途
                  </div>
                  <div style="width: 384px; display: flex; position: relative">
                    <div
                      style="
                        width: 110px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      数量
                    </div>
                    <div
                      style="
                        width: 108px;
                        padding: 10px 0px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      单价（USD）
                    </div>
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      总价（USD）
                    </div>
                    <div
                      style="
                        width: 50px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                        position: absolute;
                        right: -65px;
                      "
                    >
                      <el-button
                        type="primary"
                        style="width: 50px; border: none"
                        @click="handleAdd"
                      >
                        新增
                      </el-button>
                    </div>
                  </div>
                </div>
                <div
                  class="total"
                  style="display: flex; border-bottom: 1px solid #9c5223"
                  v-for="(item, index) in arrayGoods"
                  :key="index"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    {{ index + 1 }}
                  </div>
                  <div
                    style="
                      width: 384px;
                      border-right: 1px solid #9c5223;
                      display: flex;
                    "
                  >
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      <el-input
                        type="text"
                        v-model="item.descriptionOfGoods"
                        placeholder=""
                        style="width: 192px; border: none"
                      >
                      </el-input>
                    </div>
                    <div
                      style="
                        width: 108px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      <el-input
                        type="text"
                        v-model="item.hsCode"
                        placeholder=""
                        style="width: 92px; border: none"
                      >
                      </el-input>
                    </div>
                    <div
                      style="
                        width: 110px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      <el-input
                        type="text"
                        v-model="item.material"
                        placeholder=""
                        style="width: 95px; border: none"
                      >
                      </el-input>
                    </div>
                  </div>
                  <div
                    style="
                      width: 110px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                      line-height: 25px;
                    "
                  >
                    <el-input
                      type="text"
                      v-model="item.purpose"
                      placeholder=""
                      style="width: 95px; border: none"
                    >
                    </el-input>
                  </div>
                  <div style="width: 384px; display: flex; position: relative">
                    <div
                      style="
                        width: 110px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      <!-- <el-input type="text"
                                                      v-model="item.qty"
                                                      placeholder=""
                                                      style="width: 95px;border: none;">
                                            </el-input> -->
                      <el-input-number
                        v-model="item.qty"
                        @change="handleChangeTotalCtns"
                        :min="0"
                        :controls="false"
                        style="width: 95px; border: none"
                      ></el-input-number>
                    </div>
                    <div
                      style="
                        width: 108px;
                        height: 40px;
                        border-right: 1px solid #9c5223;
                        border-left: 1px solid #9c5223;
                        text-align: center;
                        line-height: 25px;
                      "
                    >
                      <!-- <el-input type="text"
                                                      v-model="item.unitValue"
                                                      placeholder=""
                                                      style="width: 92px;border: none;">
                                            </el-input> -->
                      <el-input-number
                        v-model="item.unitValue"
                        :min="0"
                        :controls="false"
                        style="width: 92px"
                      ></el-input-number>
                    </div>
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      <!-- <el-input type="text"
                                                      v-model="item.totalValue"
                                                      placeholder=""
                                                      style="width: 192px;border: none;">
                                            </el-input> -->
                      <el-input-number
                        v-model="item.totalValue"
                        @change="handleChangePrice"
                        :min="0"
                        :controls="false"
                        style="width: 192px; border: none"
                      ></el-input-number>
                    </div>
                    <div
                      style="
                        width: 50px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                        position: absolute;
                        right: -65px;
                      "
                    >
                      <el-button
                        type="danger"
                        style="width: 50px; border: none"
                        @click="handleDelete(index)"
                      >
                        删除
                      </el-button>
                    </div>
                  </div>
                </div>
                <div
                  class="total"
                  style="display: flex; border-bottom: 1px solid #9c5223"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Total Ctns<br />（总件数）
                  </div>
                  <div
                    style="
                      width: 685px;
                      display: flex;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    <div
                      style="
                        width: 685px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      <el-input
                        type="text"
                        v-model="commericalInvoice.totalCtns"
                        placeholder=""
                        disabled="disabled"
                        style="width: 684px; border: none"
                      >
                      </el-input>
                    </div>
                    <!-- <div style="width: 108px;padding: 10px 0px;height: 40px;text-align: center;line-height: 25px;"></div>
                                        <div style="width: 110px;height: 40px;line-height: 40px;text-align: center;"></div> -->
                  </div>
                  <!-- <div style="width: 110px;padding: 10px 0px;height: 40px;font-size: 12px;text-align: center;line-height: 25px;"></div> -->
                  <div style="width: 200px; display: flex">
                    <div
                      style="
                        width: 220px;
                        height: 40px;
                        line-height: 40px;
                        text-align: center;
                      "
                    >
                      <el-input
                        type="text"
                        v-model="commericalInvoice.usd"
                        placeholder=""
                        disabled="disabled"
                        style="
                          width: 155px;
                          border: none;
                          background-color: #fff;
                        "
                      >
                      </el-input>
                      USD
                    </div>
                  </div>
                </div>
                <div
                  class="total"
                  style="border-bottom: 1px solid #9c5223; display: flex"
                >
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Date of Exportation<br />(出口日期)
                  </div>
                  <div style="width: 384px; border-right: 1px solid #9c5223">
                    <!-- <el-input type="text"
                                                  v-model="commericalInvoice.exportDate"
                                                  placeholder=""
                                                  style="width: 350px;border: none;">
                                        </el-input> -->
                    <el-date-picker
                      v-model="commericalInvoice.exportDate"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy-MM-dd"
                      style="width: 350px; border: none"
                    >
                    </el-date-picker>
                  </div>
                  <div
                    style="
                      width: 110px;
                      padding: 10px 0px;
                      height: 40px;
                      font-size: 12px;
                      text-align: center;
                      border-right: 1px solid #9c5223;
                    "
                  >
                    Signature<br />(签名)
                  </div>
                  <div style="width: 384px; border-right: none">
                    <el-input
                      type="text"
                      v-model="commericalInvoice.signature"
                      placeholder=""
                      style="width: 350px; border: none"
                    >
                    </el-input>
                  </div>
                </div>
              </div>
            </div>
            <!-- <ul class="goodsInfo">
                <li>
                    <div class="name">货物或应税劳务、服务名称 </div>
                    <div class="spec"> 规格型号 </div>
                    <div class="unit"> 单位 </div>
                    <div class="qty"> 数量</div>
                    <div class="price"> 单价 </div>
                    <div class="money"> 金额 </div>
                    <div class="taxRate">税率 </div>
                    <div class="taxAmount">税额 </div>
                </li>
            </ul> -->
          </div>
          <!-- <ul class="invoicetFooter">
                        <li>
                            <label>收款人：</label>
                        </li>
                        <li>
                            <label>复核：</label>
                        </li>
                        <li>
                            <label>开票人：</label>
                        </li>
                        <li>
                            <label>销售方：（章）</label>
                        </li>
                    </ul> -->
        </div>
        <!-- 表格 -->
        <el-table
          :data="goodsList"
          border
          max-height="500"
          v-loading="loading"
          style="width: 90%; margin: 40px 55px 0 55px"
        >
          <el-table-column
            label="序号"
            type="index"
            align="center"
            width="50"
          ></el-table-column>

          <el-table-column
            label="快递单号/唛头"
            align="center"
            min-width="160"
            prop="deliveryOrderNo"
          ></el-table-column>

          <el-table-column
            label="包裹名称"
            align="center"
            min-width="160"
            prop="goodsName"
          >
          </el-table-column>

          <el-table-column
            label="包装类型"
            align="center"
            min-width="160"
            prop="packageType"
          >
          </el-table-column>

          <el-table-column
            label="包裹数量"
            align="center"
            min-width="120"
            prop="packageNum"
          ></el-table-column>

          <!-- <el-table-column label="体积/m³"
                                     align="center"
                                     min-width="120"
                                     prop="vol"></el-table-column>

                    <el-table-column label="入库时间"
                                     align="center"
                                     min-width="160"
                                     prop="inStorageTime"></el-table-column>

                    <el-table-column label="备注信息"
                                     align="center"
                                     min-width="160"
                                     prop="message"></el-table-column> -->
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          v-waves
          type="warning"
          @click="handleInvoicePrint('formPackage')"
          >打印</el-button
        >
        <el-button
          v-waves
          type="primary"
          @click="handleInvoiceSubmit('formPackage')"
          >保存</el-button
        >
        <el-button v-waves type="info" plain @click="dialogInvoiceInfo = false"
          >关闭</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { getStorageList } from "@/api/customer-management/cargo-list";
import {
  lookBoxWaitingArea,
  lookCourierWaitingArea,
  unpackingListWaitingArea,
  comfirmUnpackingWaitingArea,
  getCustomerPackIdInfo,
  editTheInvoice,
  saveTheInvoice,
} from "@/api/package-management/waiting-area";

export default {
  name: "ModelView",
  directives: {
    waves,
  },
  props: {
    chartTypes: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  watch: {
    chartTypes() {
      this.chartType = this.chartTypes;
    },
  },
  data() {
    /**
     * 自定义验证规则
     */
    const validateEn = (rule, value, callback) => {
      let reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
      if (value == "") {
        // callback(new Error('充值金额不能为空'))
        callback();
      } else if (value == "0") {
        callback();
      } else if (value !== "" && !reg.test(value)) {
        callback(
          new Error(
            "只允输入数字金额，并且最多保留两位小数（正确示例：100.00）"
          )
        );
      } else {
        callback();
      }
    };
    return {
      loading: false,
      saving: false,
      chartType: [], // 状态
      storageAreaData: [], // 区号
      storageRowData: [], // 排号
      selectData: [], // 区排数据
      tableData: [],
      formItem: {
        storageData: [],
        id: "",
        customerPackId: "",
        goods: [],
        money: "",
      },
      formItemRules: {
        money: [{ validator: validateEn, trigger: "blur" }],
      },
      pageInfo: {
        packOrder: "",
        actualVol: "",
        actualWeight: "",
        actualPrice: "",
        packTime: "",
      },
      dialogPackageInfo: false,
      orderData: [], // 包裹数据
      dialogOrderInfo: false, // 箱子信息
      courierData: [], //快递包裹
      dialogPackInfo: false, // 快递包裹信息
      dialogInvoiceInfo: false, // 发票
      commericalInvoice: {
        id: "",
        packId: "", //关联的包裹id
        conCompanyName: "", //(发货)公司名
        conSenderName: "", //发件人
        recCompanyName: "", //（收货）公司名
        recReceiverName: "", //收件人
        conTel: "", //发件人电话
        conCode: "", //发件人邮编
        recTel: "", //收件人电话
        recCode: "", //收件人邮编
        conContry: "", //发件人国家
        conAddress: "", //发件人地址
        recContry: "", //收件人国家
        recAddress: "", //收件人地址
        totalCtns: 0, //总件数
        exportDate: "", //出口日期
        signature: "", //签名
        usd: 0, //USD
        orderNumber: "", //订单号
        agentNumber: "", //转运单号
      },
      goods: [],
      arrayGoods: [],
      goodsList: [],
    };
  },
  methods: {
    /**
     * 拆包弹窗
     * @return {type} {description}
     */
    handleCaibaoModel(id, businessNumber) {
      this.formItem.customerPackId = id;
      this.loading = true;
      const p1 = getStorageList();
      const p2 = unpackingListWaitingArea(businessNumber);
      Promise.all([p1, p2])
        .then((res) => {
          this.selectData = res[0].content;
          this.storageAreaData = this.selectData.storage;
          this.formItem.storageData = res[1].content.goods;
        })
        .finally(() => {
          this.dialogPackageInfo = true;
          this.loading = false;
        });
    },

    /**
     * 改变事件
     * @return {type} {description}
     */
    changeSelectArea(value, index) {
      // delete this.formItem.storageData[index].storageRow
      if (this.formItem.storageData[index].storageRow) {
        this.formItem.storageData[index].storageRow = "";
      }
      this.storageAreaData.forEach((ele) => {
        if (ele.storageArea == value) {
          this.storageRowData = Number(ele.storageRow);
        }
      });
    },
    // changeSelectRow (value, index) {
    //     console.log(this.formItem.storageDataa)
    //     // this.formItem.storageData[index].storageRow = value
    // },
    // 提交
    handlePackageSubmit(from) {
      this.formItem.goods = []; // 清空
      this.formItem.storageData.forEach((ele) => {
        this.formItem.goods.push({
          id: ele.id,
          location: ele.storageArea + "," + ele.storageRow,
        });
      });
      this.$refs[from].validate((valid) => {
        if (valid) {
          this.saving = true;
          comfirmUnpackingWaitingArea(this.formItem)
            .then((res) => {
              if (res.code === 100) {
                this.$message({
                  message: "确认成功",
                  type: "success",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
                this.$emit("updateList");
                this.$nextTick(() => {
                  this.dialogPackageInfo = false;
                });
              }
            })
            .finally(() => {
              this.saving = false;
            });
        }
      });
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset(from) {
      this.$refs[from].resetFields();
    },

    // 查看包裹信息======================================================================================================
    /**
     * 弹窗
     * @param  {type}  {description}
     * @return {type} {description}
     */
    handleOrderModel(businessNumber) {
      lookBoxWaitingArea(businessNumber)
        .then((res) => {
          if (res.code === 100) {
            this.orderData = res.content.customerPackNumbers;
            this.orderData.forEach((ele) => {
                ele.packLength = "【"+ ele.length +"*"+ele.width+"*" +ele.height +"】"
            })
            
          }
        })
        .finally(() => {
          this.dialogOrderInfo = true;
        });
    },

    /**
     * 查看快递包裹
     * @return {type} {description}
     */
    handleCheck(packId) {
      lookCourierWaitingArea(packId)
        .then((res) => {
          if (res.code === 100) {
            // this.pageInfo = res.content.customerPackNumber
            this.courierData = res.content;
            // console.log("courierData:", this.courierData);
          }
        })
        .finally(() => {
          this.dialogPackInfo = true;
        });
    },

    // =====================================编辑发票
    /**
     * 发票
     * @param  {type}  {description}
     * @return {type} {description}
     */
    handleInvoiceModel(customerPackId) {
      getCustomerPackIdInfo(customerPackId)
        .then((res) => {
          if (res.code === 100) {
            this.commericalInvoice.id = res.content.commericalInvoice.id
              ? res.content.commericalInvoice.id
              : "";
            this.commericalInvoice.packId = res.content.commericalInvoice.packId
              ? res.content.commericalInvoice.packId
              : "";
            this.commericalInvoice.conCompanyName = res.content
              .commericalInvoice.conCompanyName
              ? res.content.commericalInvoice.conCompanyName
              : "";
            this.commericalInvoice.conSenderName = res.content.commericalInvoice
              .conSenderName
              ? res.content.commericalInvoice.conSenderName
              : "";
            this.commericalInvoice.recCompanyName = res.content
              .commericalInvoice.recCompanyName
              ? res.content.commericalInvoice.recCompanyName
              : "";
            this.commericalInvoice.recReceiverName = res.content
              .commericalInvoice.recReceiverName
              ? res.content.commericalInvoice.recReceiverName
              : "";
            this.commericalInvoice.conTel = res.content.commericalInvoice.conTel
              ? res.content.commericalInvoice.conTel
              : "";
            this.commericalInvoice.conCode = res.content.commericalInvoice
              .conCode
              ? res.content.commericalInvoice.conCode
              : "";
            this.commericalInvoice.recTel = res.content.commericalInvoice.recTel
              ? res.content.commericalInvoice.recTel
              : "";
            this.commericalInvoice.recCode = res.content.commericalInvoice
              .recCode
              ? res.content.commericalInvoice.recCode
              : "";
            this.commericalInvoice.conContry = res.content.commericalInvoice
              .conContry
              ? res.content.commericalInvoice.conContry
              : "";
            this.commericalInvoice.conAddress = res.content.commericalInvoice
              .conAddress
              ? res.content.commericalInvoice.conAddress
              : "";
            this.commericalInvoice.recContry = res.content.commericalInvoice
              .recContry
              ? res.content.commericalInvoice.recContry
              : "";
            this.commericalInvoice.recAddress = res.content.commericalInvoice
              .recAddress
              ? res.content.commericalInvoice.recAddress
              : "";
            this.commericalInvoice.totalCtns = res.content.commericalInvoice
              .totalCtns
              ? res.content.commericalInvoice.totalCtns
              : "";
            this.commericalInvoice.exportDate = res.content.commericalInvoice
              .exportDate
              ? res.content.commericalInvoice.exportDate
              : "";
            this.commericalInvoice.signature = res.content.commericalInvoice
              .signature
              ? res.content.commericalInvoice.signature
              : "";
            this.commericalInvoice.usd = res.content.commericalInvoice.usd
              ? res.content.commericalInvoice.usd
              : "";
            this.commericalInvoice.orderNumber = res.content.commericalInvoice
              .orderNumber
              ? res.content.commericalInvoice.orderNumber
              : "";
            this.commericalInvoice.agentNumber = res.content.commericalInvoice
              .agentNumber
              ? res.content.commericalInvoice.agentNumber
              : "";
            this.arrayGoods = res.content.commericalInvoice
              .commericalInvoiceItemsList
              ? res.content.commericalInvoice.commericalInvoiceItemsList
              : [];
            this.goods = [];
            // .forEach(ele => {
            //      this.arrayGoods.push({
            //          descriptionOfGoods: ele.descriptionOfGoods ? ele.descriptionOfGoods : '',    	//货物描述
            //          hsCode: ele.hsCode ? ele.hsCode : '',   				//海关编码
            //          material: ele.material ? ele.material : '',   			//材质
            //          purpose: ele.purpose ? ele.purpose : '',				//用途
            //          qty: ele.qty ? ele.qty : '', 					//数量
            //          unitValue: ele.unitValue ? ele.unitValue : '',			    //单价
            //          totalValue: ele.totalValue ? ele.totalValue : '',             //总价
            //      })
            //  })
            this.goodsList = res.content.commericalInvoice.goodsList;
            this.$nextTick(() => {
              this.dialogInvoiceInfo = true;
            });
          }
        })
        .finally(() => {
          this.saving = false;
        });
    },

    handleAdd() {
      if (this.arrayGoods.length < 5) {
        this.arrayGoods.push({
          descriptionOfGoods: "", //货物描述
          hsCode: "", //海关编码
          material: "", //材质
          purpose: "", //用途
          qty: 0, //数量
          unitValue: "", //单价
          totalValue: 0, //总价
        });
      } else {
        this.$message({
          message: "货物信息最多新增5行。",
          type: "error",
          showClose: true,
          duration: 1000 * 1.5 * 2,
        });
      }
    },

    handleDelete(index) {
      if (index > -1) {
        this.arrayGoods.splice(index, 1);
        this.handleChangePrice();
        this.handleChangeTotalCtns();
      }
    },
    // 自动计算总数量
    /*handleChangeUnitValue () {
            this.arrayGoods.storageRow.totalValue = this.arrayGoods.storageRow.unitValue * this.arrayGoods.storageRow.qty
        },*/

    // 自动计算总数量
    handleChangePrice() {
      this.commericalInvoice.usd = 0;
      this.arrayGoods.forEach((ele) => {
        this.commericalInvoice.usd =
          Number(this.commericalInvoice.usd) + Number(ele.totalValue);
      });
    },

    // 自动计算总数量
    handleChangeTotalCtns() {
      this.commericalInvoice.totalCtns = 0;
      this.arrayGoods.forEach((ele) => {
        this.commericalInvoice.totalCtns =
          Number(this.commericalInvoice.totalCtns) + Number(ele.qty);
      });
    },

    handleInvoiceSubmit() {
      //this.arrayGoods = []  // 清空
      this.arrayGoods.forEach((ele) => {
        this.goods.push(ele);
      });
      let obj = {
        commericalInvoice: this.commericalInvoice,
        goods: this.goods,
      };
      saveTheInvoice(obj)
        .then((res) => {
          if (res.code === 100) {
            this.$message({
              message: "保存成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.$emit("updateList");
            this.$nextTick(() => {
              this.dialogInvoiceInfo = false;
            });
          }
        })
        .finally(() => {
          this.saving = false;
        });
    },

    handleInvoicePrint() {
      // this.arrayGoods = []  // 清空
      this.arrayGoods.forEach((ele) => {
        this.goods.push(ele);
      });
      let obj = {
        commericalInvoice: this.commericalInvoice,
        goods: this.goods,
      };
      editTheInvoice(obj)
        .then((res) => {
          if (res.code === 100) {
            // this.$message({
            //     message: '编辑成功',
            //     type: 'success',
            //     showClose: true,
            //     duration: 1000 * 1.5 * 2
            // });
            this.$emit("updateList");
            this.$nextTick(() => {
              window.open("http://" + res.content.url);
              // this.dialogInvoiceInfo = false;
            });
          }
        })
        .finally(() => {
          this.saving = false;
        });
      // this.$refs[from].validate((valid) => {
      //     if (valid) {
      //         this.saving = true
      //         editTheInvoice(this.formItem).then(res => {
      //             if (res.code === 100) {
      //                 this.$message({
      //                     message: '确认成功',
      //                     type: 'success',
      //                     showClose: true,
      //                     duration: 1000 * 1.5 * 2
      //                 });
      //                 this.$emit('updateList')
      //                 this.$nextTick(() => {
      //                     this.dialogPackageInfo = false;
      //                 })
      //             }
      //         }).finally(() => {
      //             this.saving = false
      //         })
      //     }
      // })
    },
  },
};
</script>

<style lang="scss">
.model-view {
  .filter-box {
    margin-bottom: 20px;
  }
  .table-number {
    float: left;
    font-size: 16px;
    font-weight: 700;
  }
  .el-textarea.is-disabled .el-textarea__inner,
  .el-input.is-disabled .el-input__inner {
    color: #666;
    background-color: #fff;
  }
  .invoice-box {
    .el-input--medium .el-input__inner {
      height: 40px;
      line-height: 40px;
      border: none;
    }

    ul,
    ul li {
      list-style: none;
      margin: 0;
      padding: 0;
    }

    label {
      color: #9c5223;
    }

    .invoicMain {
      width: 1000px;
      margin: 0 auto;
      font-size: 14px;
      color: #000;
    }

    .invoiceHeader {
      height: 126px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .headerLeft li:nth-child(1) {
      text-indent: 1em;
    }

    .headerLeft li img {
      width: 150px;
      height: 100px;
    }

    .headerMiddle h1 {
      font-size: 32px;
      color: #9c5223;
      font-family: "kaiti";
      margin: 5px 12px;
      text-align: center;
    }

    .line {
      height: 2px;
      border-top: #9c5223 1px solid;
      border-bottom: #9c5223 1px solid;
    }

    .headerRight li {
      line-height: 24px;
    }

    .invoiceBody {
      border: 1px solid #9c5223;
    }

    .userInfo {
      width: 100%;
      display: flex;
      align-items: center;
      height: 96px;
      border-bottom: 1px solid #9c5223;
    }

    .userInfo ul {
      width: 50%;
      margin: 0 5px;
      padding: 0;
    }

    .userInfo ul li {
      line-height: 24px;
    }

    .buy {
      width: 30px;
      border-right: 1px solid #9c5223;
      padding: 0 10px;
      text-align: center;
      height: 100%;
      display: flex;
      align-items: center;
      color: #9c5223;
    }

    .password {
      width: 30px;
      padding: 0 10px;
      border-right: 1px solid #9c5223;
      border-left: 1px solid #9c5223;
      text-align: center;
      height: 100%;
      display: flex;
      align-items: center;
      color: #9c5223;
    }

    .pwdInfo {
      flex: 1;
      padding-left: 5px;
    }

    .goodsInfo {
      height: 210px;
      margin: 0;
      padding: 0;
    }

    .goodsInfo li {
      display: flex;
      color: #9c5223;
      text-align: center;
    }

    .name {
      width: 260px;
      border-right: 1px solid #9c5223;
    }

    .spec {
      width: 140px;
      border-right: 1px solid #9c5223;
    }

    .qty {
      width: 108px;
      border-right: 1px solid #9c5223;
    }

    .unit,
    .taxRate {
      width: 65px;
      border-right: 1px solid #9c5223;
    }

    .qty,
    .price {
      width: 160px;
      border-right: 1px solid #9c5223;
    }

    .money {
      flex: 1;
      border-right: 1px solid #9c5223;
    }

    .taxAmount {
      flex: 1;
    }

    .GoodsTable {
      // height: 210px;
      width: 100%;
      border-collapse: collapse;
    }

    .GoodsTable td {
      border-right: 1px solid #9c5223;
      color: #9c5223;
    }

    .GoodsTable tr:nth-child(1),
    .GoodsTable tr:nth-last-child(2) {
      height: 24px;
    }

    .GoodsTable tr:nth-last-child(1) {
      height: 34px;
    }

    .GoodsTable tr:nth-child(1) td {
      text-align: center;
    }

    .GoodsTotal {
      border-top: 1px solid #9c5223;
      border-bottom: 1px solid #9c5223;
    }

    .total td:nth-child(1) {
      text-align: center;
    }

    .invoicetFooter {
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: space-between;
    }

    .invoicetFooter li {
      width: 25%;
    }
  }
}
</style>