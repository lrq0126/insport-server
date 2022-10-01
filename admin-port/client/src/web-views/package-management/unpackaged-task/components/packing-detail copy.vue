/**
 * 【打包明细】信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="packing-detail">
        <el-tabs v-model="activeName"
                 style="margin-top: 15px"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="打包明细"
                         name="list">
                <el-form ref="formItem"
                         :model="formItem"
                         :rules="formItemRules"
                         label-width="120px">
                    <el-row>
                        <!-- 订单详情 -->
                        <el-col :span="24">
                            <el-divider content-position="left"><i class="el-icon-mobile-phone"></i>订单详情</el-divider>
                            <el-col :span="8">
                                <el-col :span="24">
                                    <el-form-item label="业务号：">
                                        <el-input v-model="formItem.businessNumber"
                                                  :disabled="true"
                                                  placeholder="请输入业务号"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="客户：">
                                        <el-input v-model="formItem.customerName"
                                                  :disabled="true"
                                                  placeholder="请输入客户"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="包裹数量：">
                                        <el-input v-model="formItem.packNum"
                                                  :disabled="true"
                                                  placeholder="请输入包裹数量"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="下单时间：">
                                        <el-input v-model="formItem.createTime"
                                                  :disabled="true"
                                                  placeholder="请输入下班时间"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>

                            <el-col :span="8">
                                <el-col :span="24">
                                    <el-form-item label="订单号：">
                                        <el-input v-model="formItem.orderNumber"
                                                  :disabled="true"
                                                  placeholder="打包后系统生成"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="国家：">
                                        <el-input v-model="formItem.destination"
                                                  :disabled="true"
                                                  placeholder="国家"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="渠道名称：">
                                        <el-input v-model="formItem.routeName"
                                                  :disabled="true"
                                                  placeholder="请输入渠道名称"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="运输类型：">
                                        <el-select v-model="formItem.routeType"
                                                   :disabled="true"
                                                   placeholder="请选择运输类型">
                                            <el-option label="海运"
                                                       value="2" />
                                            <el-option label="空运"
                                                       value="1" />
                                        </el-select>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="会员ID:">
                                        <el-input v-model="formItem.loginName"
                                                  :disabled="true"
                                                  placeholder="会员ID"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>

                            <el-col :span="8">

                                <el-col :span="24">
                                    <el-form-item label="收件人电话：">
                                        <el-input v-model="formItem.phoneNumber"
                                                  :disabled="true"
                                                  placeholder="请输入渠道名称"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :span="24">
                                    <el-form-item label="收件人名称：">
                                        <el-input v-model="formItem.addressee"
                                                  :disabled="true"
                                                  placeholder="请输入渠道名称"></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-form-item label="收货地址：">
                                    <el-input v-model="formItem.address"
                                              placeholder="请输入收货地址"
                                              type="textarea"
                                              :rows="4"
                                              :disabled="true"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-col>

                        <!-- 扫码拣货 -->
                        <el-col :span="24">
                            <el-divider content-position="left"><i class="el-icon-mobile-phone"></i>扫码拣货</el-divider>
                            <audio src="http://www.wennever.cn/upload/pdf/success.mp3"
                                   id="eventAudio"></audio>
                            <audio src="http://www.wennever.cn/upload/pdf/AllSortSuccess.mp3"
                                   id="allSorAudio"></audio>
                            <audio src="http://www.wennever.cn/upload/pdf/warning.mp3"
                                   id="eventAudioError"></audio>

                            <el-col :span="24">
                                <el-form-item label="扫码拣货："
                                              prop="id"
                                              label-width="30%">
                                    <el-input v-model="formItem.id"
                                              style="width: 50%"
                                              placeholder="请输入订单号"
                                              @keyup.enter.native="pickUp('formItem')"></el-input>
                                    <el-button type="primary"
                                               style="margin-left: 15px"
                                               icon="el-icon-success"
                                               @click="pickUp('formItem')">拣 货</el-button>
                                    <el-button type="info"
                                               plain
                                               style="margin-left: 15px"
                                               @click="handleResetForm('formItem')">重 置</el-button>
                                </el-form-item>
                            </el-col>
                        </el-col>
                    </el-row>
                </el-form>
                <el-form ref="formPack"
                         :model="formItem"
                         :rules="formItemRules"
                         label-width="120px">
                    <el-row>
                        <!-- 打包明细 -->
                        <el-col :span="24">
                            <el-divider content-position="left"><i class="el-icon-mobile-phone"></i>打包明细</el-divider>
                            <el-button type="danger"
                                       plain
                                       style="margin-left: 15px"
                                       @click="handlePrintCustomerPack(formItem.businessNumber)">打印包裹清单</el-button>
                            <el-button type="primary"
                                       plain
                                       style="margin-right: 50px;float: right;"
                                       @click="onePickUp('formItem')">一键拣货</el-button>
                            <el-col :span="23"
                                    style="margin-left: 20px">
                                <el-table :data="formItem.goodsData"
                                          border
                                          fit
                                          v-loading="loading"
                                          highlight-current-row
                                          style="width: 100%; margin-top: 20px">
                                    <el-table-column label="序号"
                                                     type="index"
                                                     fixed="left"
                                                     width="50"
                                                     align="center" />
                                    <el-table-column label="客户名称"
                                                     min-width="160"
                                                     prop="customerName"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="包裹名称"
                                                     min-width="160"
                                                     prop="goodsName"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="快递单号/唛头"
                                                     min-width="160"
                                                     prop="deliveryOrderNo"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="备注信息"
                                                     min-width="160"
                                                     prop="message"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="仓库地址"
                                                     min-width="160"
                                                     align="center">
                                        <template slot-scope="scope">
                                            <span>{{ scope.row.location | filterLocation }}</span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column label="包裹类型"
                                                     min-width="160"
                                                     prop="packageType"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="入库操作员"
                                                     min-width="160"
                                                     prop="operatorName"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="入库日期"
                                                     min-width="160"
                                                     prop="inStorageTime"
                                                     align="center">
                                    </el-table-column>

                                    <el-table-column label="拣货状态"
                                                     fixed="right"
                                                     min-width="120"
                                                     align="center">
                                        <template slot-scope="scope">
                                            <el-switch v-if="scope.row.sortType == 0"
                                                       v-model="scope.row.outStorageTime"
                                                       active-color="#13ce66"
                                                       inactive-color="#ddd"
                                                       active-text="未分拣"
                                                       @change="handleOutbound(scope.row.deliveryOrderNo,scope.$index)"
                                                       inactive-text="">
                                            </el-switch>
                                            <el-switch v-if="scope.row.sortType == 1"
                                                       :value="scope.row.sortType == 1"
                                                       active-color="#13ce66"
                                                       inactive-color="#ddd"
                                                       active-text="已分拣"
                                                       inactive-text=""
                                                       disabled>
                                            </el-switch>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-col>
                        <!-- 打包后包裹信息 -->
                        <el-col :span="24">
                            <el-divider content-position="left"><i class="el-icon-mobile-phone"></i>打包后包裹信息</el-divider>

                            <el-col :span="22"
                                    style="margin-left: 20px">
                                <el-form-item label=""
                                              label-width="0">
                                    <el-button type="primary"
                                               plain
                                               style="margin-bottom: 5px"
                                               icon="el-icon-circle-plus-outline"
                                               @click="addTopic">新增包裹</el-button>
                                    <el-table :data="formItem.specialItemJson"
                                              fit
                                              highlight-current-row
                                              style="width: 100%; margin-top: 20px">
                                        <el-table-column label="序号"
                                                         width="80"
                                                         type="index"
                                                         align="center">
                                        </el-table-column>

                                        <el-table-column label="实重/KG"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <el-form-item style="margin-bottom: 15px"
                                                              :prop="
                            'specialItemJson.' + scope.$index + '.actualWeight'
                          "
                                                              :rules="{
                            required: true,
                            message: '实重不能为空',
                            trigger: 'change',
                          }">
                                                    <el-input v-model="scope.row.actualWeight"
                                                              placeholder="请输入实重"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="长/CM"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <!-- <el-form-item style="margin-bottom: 15px"
                                                              :prop="'specialItemJson.' + scope.$index + '.length'"
                                                              :rules="{required: true, message: '长度不能为空', trigger: 'change'}">
                                                    <el-input v-model="scope.row.length"
                                                              placeholder="请输入长度"></el-input>
                                                </el-form-item> -->
                                                <el-form-item style="margin-bottom: 15px">
                                                    <el-input v-model="scope.row.length"
                                                              placeholder="请输入长度"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="宽/CM"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <!-- <el-form-item style="margin-bottom: 15px"
                                                              :prop="'specialItemJson.' + scope.$index + '.width'"
                                                              :rules="{required: true, message: '宽度不能为空', trigger: 'change'}">
                                                    <el-input v-model="scope.row.width"
                                                              placeholder="请输入宽度"></el-input>
                                                </el-form-item> -->
                                                <el-form-item style="margin-bottom: 15px">
                                                    <el-input v-model="scope.row.width"
                                                              placeholder="请输入宽度"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="高/CM"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <!-- <el-form-item style="margin-bottom: 15px"
                                                              :prop="'specialItemJson.' + scope.$index + '.height'"
                                                              :rules="{required: true, message: '高度不能为空', trigger: 'change'}">
                                                    <el-input v-model="scope.row.height"
                                                              placeholder="请输入高度"></el-input>
                                                </el-form-item> -->
                                                <el-form-item style="margin-bottom: 15px">
                                                    <el-input v-model="scope.row.height"
                                                              placeholder="请输入高度"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="体积/m³"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <span>{{
                          scope.row.length
                            | allChecked(scope.row.width, scope.row.height)
                        }}</span>
                                                <!-- <el-form-item style="margin-bottom: 0">
                                                        <el-input v-model="scope.row.actualVol"
                                                                :disabled="true"
                                                                placeholder="请输入体积"></el-input>
                                                    </el-form-item> -->
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="快递包裹"
                                                         width="120"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <el-form-item style="margin-bottom: 0">
                                                    <el-button type="primary"
                                                               v-if="!scope.row.bool"
                                                               size="mini"
                                                               plain
                                                               @click="
                              handlePackages(
                                formItem.packingId,
                                scope.row.selectIds,
                                scope.$index,
                                scope.row.customerPackNumberId
                              )
                            ">请选择包裹</el-button>
                                                    <el-tooltip placement="top"
                                                                v-else>
                                                        <div slot="content"
                                                             v-for="(res, index) in scope.row.packagesInfo"
                                                             :key="index"
                                                             style="margin: 8px 0; font-size: 16px">
                                                            <span>客户名称：{{ res.customerName }}</span>
                                                            <span style="margin: 0 10px">包裹名称：{{ res.goodsName }}</span>
                                                            <span style="margin: 0 10px">订单编号/唛头：{{ res.deliveryOrderNo }}</span>
                                                            <span>包裹类型：{{ res.packageType }}</span>
                                                        </div>
                                                        <el-button type="success"
                                                                   size="mini"
                                                                   plain
                                                                   @click="
                                handlePackages(
                                  formItem.packingId,
                                  scope.row.selectIds,
                                  scope.$index,
                                  scope.row.customerPackNumberId
                                )
                              ">已选包裹</el-button>
                                                    </el-tooltip>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="包裹数量"
                                                         width="80"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <span>{{ scope.row.packagesInfo.length }}</span>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="操作"
                                                         width="120"
                                                         align="center">
                                            <template slot-scope="scope">
                                                <el-button type="danger"
                                                           size="mini"
                                                           plain
                                                           @click="
                            deleteTopic(
                              scope.row.selectIds,
                              scope.$index,
                              scope.row.customerPackNumberId
                            )
                          ">
                                                    删除</el-button>
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
                                <el-col :span="24">
                                    <el-form-item label="杂费：">
                                        <el-input v-model="formItem.incidental"
                                                  style="width: 180px"
                                                  placeholder="请输入杂费"><template slot="append">元</template></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>

                            <el-col :span="8">
                                <el-col :span="24">
                                    <el-form-item label="备注信息：">
                                        <el-input v-model="formItem.remarks"
                                                  type="textarea"
                                                  :role="5"
                                                  placeholder="请输入备注信息"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>

                            <el-col :span="8">
                                <el-col :span="24">
                                    <el-form-item label="客服备注：">
                                        <el-input v-model="formItem.insideMessage"
                                                  type="textarea"
                                                  :role="5"
                                                  placeholder="客服内部备注，客户无法查看"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>

                        </el-col>

                        <el-col :span="20"
                                style="margin-top: 40px">
                            <el-row>
                                <el-col :span="20">
                                    <el-form-item label-width="50%">
                                        <el-button :loading="saving"
                                                   type="warning"
                                                   @click="changeChannel()"><i class="el-icon-edit"></i>更改运输渠道</el-button>
                                        <el-button :loading="saving"
                                                   type="success"
                                                   @click="editMessage('formPack')"><i class="el-icon-edit"></i>编辑备注信息</el-button>
                                        <el-button :loading="saving"
                                                   type="primary"
                                                   @click="handleSubmit('formPack')">确认打包</el-button>
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
            <el-tab-pane label="返回上一层"
                         name="back"></el-tab-pane>
        </el-tabs>

        <!--包裹列表信息 -->
        <el-dialog :visible.sync="dialogInfo"
                   title="包裹列表信息"
                   width="80%">
            <!-- 表格 -->
            <el-table :data="formItem.packageList"
                      ref="multipleTable"
                      border
                      fit
                      v-loading="loading"
                      highlight-current-row
                      style="width: 100%; margin-top: 20px"
                      @selection-change="handleSelectionChange">
                <el-table-column type="selection"
                                 width="55"></el-table-column>

                <el-table-column label="序号"
                                 type="index"
                                 fixed="left"
                                 width="50"
                                 align="center" />
                <el-table-column label="客户名称"
                                 min-width="160"
                                 prop="customerName"
                                 align="center">
                </el-table-column>

                <el-table-column label="包裹名称"
                                 min-width="160"
                                 prop="goodsName"
                                 align="center">
                </el-table-column>

                <el-table-column label="快递单号/唛头"
                                 min-width="160"
                                 prop="deliveryOrderNo"
                                 align="center">
                </el-table-column>

                <el-table-column label="仓库地址"
                                 min-width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <span>{{ scope.row.location | filterLocation }}</span>
                    </template>
                </el-table-column>

                <el-table-column label="包裹类型"
                                 min-width="160"
                                 prop="packageType"
                                 align="center">
                </el-table-column>

                <el-table-column label="入库操作员"
                                 min-width="160"
                                 prop="operatorName"
                                 align="center">
                </el-table-column>

                <el-table-column label="入库日期"
                                 min-width="160"
                                 prop="inStorageTime"
                                 align="center">
                </el-table-column>
            </el-table>

            <span slot="footer"
                  class="dialog-footer">
                <el-button type="info"
                           plain
                           @click="dialogInfo = false">取 消</el-button>
                <el-button type="primary"
                           @click="handleSelectPackages">确 认</el-button>
            </span>
        </el-dialog>
        <!--更改运输渠道 -->
        <channel-list-modal ref="channelListShow"
                            :countriesDatas="countriesData"
                            @updateForm="handleSearch($route.query.id)" />
        <!-- end -->
    </div>
</template>

<script>
import {
    pickUpPackingDetail,
    getPackingDetail,
    packingDetailUnpackagedTask,
    unselectedPackingDetail,
    deleteSelectedPackingDetail,
    theSelectedPackingDetail,
    InitializePickUpPackingDetail,
    printGoodsListing,
    printCustomerPackListing,
    printOrderListing,
    onePickUpPackingDetail, editPackMessage
} from "@/api/package-management/unpackaged-task";
import quillConfig from "@/utils/quill-config.js";
import channelListModal from './channel-list-modal'
import { getCountriesList } from '@/api/channel-management/countries-list'

export default {
    name: "Create",
    components: {
        channelListModal
    },
    data () {
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
            loading: false,
            saving: false,
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
                packageList: [], // 弹窗中包裹列表
                goodsData: [], // 打包明细
                loginName: '',
                destination: '',
                insideMessage: '',
                specialItemJson: [
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
                        customerPackNumberId: "",
                    },
                ],
                customerPackNumber: [],
                customerPackNumberId: "",
                orderNumber: '',
                phoneNumber: '',
                addressee: '',
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
            index: "", // 选中快递包裹索引值
            dialogInfo: false,
            countryId: '',
            countriesData: [],  // 国家
        };
    },
    filters: {
        allChecked (length, width, height) {
            if (!length) return 0;
            if (!width) return 0;
            if (!height) return 0;
            return (Number(length) * Number(width) * Number(height)) / 1000000;
        },
        filterLocation (value) {
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
        playAudio (data) {
            let buttonAudio;
            if(data){
                buttonAudio = document.getElementById("allSorAudio");
                // buttonAudio.setAttribute('src', audio)
                buttonAudio.play();
            }else{
                buttonAudio = document.getElementById("eventAudio");
                // buttonAudio.setAttribute('src', audio)
                buttonAudio.play();
            }
        },

        /**
         * 拣货
         * @return {type} {description}
         */
        pickUp (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    let obj = {
                        businessNumber: this.formItem.businessNumber,
                        deliveryOrderNo: this.formItem.id,
                    };
                    this.saving = true;
                    debugger
                    pickUpPackingDetail(obj)
                        .then((res) => {
                            console.log(res)
                            if (res.code === 100) {
                                this.playAudio(res.content.isAllSort); // 音频
                                let message;
                                    if(res.content.isAllSort){
                                        message = "全部货物已拣完"
                                    }else{
                                        message = "拣货成功"
                                    }
                                    this.$message({
                                        message: message,
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
                                let buttonAudio = document.getElementById("eventAudioError");
                                buttonAudio.play();
                                this.$message({
                                    message: '拣货失败',
                                    type: 'error',
                                    showClose: true,
                                    duration: 1000 * 1.5 * 2
                                });
                            }
                        }).catch((e) => {
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
        onePickUp (from) {
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
                    this.handleReset("formItem");
                });
        },

        /**
         * 拣货
         * @return {type} {description}
         */
        handleOutbound (id, index) {
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
                                    if(res.content.isAllSort){
                                        message = "全部货物已拣完"
                                    }else{
                                        message = "拣货成功"
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
        handleResetForm (form) {
            this.formItem.id = "";
            this.$refs[form].resetFields();
        },

        /**
         * 多选框事件
         * @param  {type} val {description}
         * @return {type} {description}
         */
        handleSelectionChange (val) {
            console.log(val);
            this.multipleSelection = val;
        },

        /**
         * 点击选择快递【包裹】
         * @return {type} {description}
         */
        handlePackages (customerPackId, goodsNos, index, customerPackNumberId) {
            let obj = {
                customerPackId: customerPackId,
                goodsNos: goodsNos,
                customerPackNumberId: customerPackNumberId,
            };
            this.loading = true;
            this.saving = true;
            unselectedPackingDetail(obj)
                .then((res) => {
                    if (res.code === 100) {
                        this.formItem.packageList = res.content.goods;
                        this.$nextTick(() => {
                            this.dialogInfo = true;
                            this.index = index; // 大箱子索引值
                            setTimeout(() => {
                                this.loading = false;
                            }, 800);
                        });
                    }
                })
                .finally(() => {
                    this.saving = false;
                    if (this.formItem.specialItemJson[index].packagesInfo.length > 0) {
                        this.formItem.specialItemJson[index].packagesInfo.forEach((ele) => {
                            this.formItem.packageList.forEach((row) => {
                                if (ele.goodsNo == row.goodsNo) {
                                    this.$refs.multipleTable.toggleRowSelection(row);
                                }
                            });
                        });
                    }
                });
        },

        /**
         * 选中快递
         * @return {type} {description}
         */
        handleSelectPackages (index) {
            let goodsNos = [];
            this.multipleSelection.forEach((ele) => {
                // 数据处理
                goodsNos.push(ele.goodsNo);
            });

            let obj = {
                goodsNos: goodsNos,
                customerPackNumberId: this.formItem.specialItemJson[this.index]
                    .customerPackNumberId,
                customerPackId: this.formItem.packingId,
            };
            if (this.multipleSelection.length > 0) {
                theSelectedPackingDetail(obj)
                    .then((res) => {
                        if (res.code === 100) {
                            this.dialogInfo = false;
                            let arrGoodsNo = [];
                            this.multipleSelection.forEach((ele) => {
                                arrGoodsNo.push(ele.goodsNo);
                            });
                            this.formItem.specialItemJson[
                                this.index
                            ].selectIds = arrGoodsNo.join(",");
                            this.formItem.specialItemJson[this.index].bool = true;
                            this.formItem.specialItemJson[
                                this.index
                            ].packagesInfo = this.multipleSelection;
                            this.formItem.specialItemJson[this.index].customerPackNumberId =
                                res.content.id;
                        }
                    })
                    .finally(() => {
                        // this.formItem.specialItemJson[this.index].packagesInfo.forEach(ele => {
                        //     ele.inPacked = 1
                        // });
                        this.saving = false;
                    });
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
        editMessage (from) {
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
         * 确认打包
         * @param  {string} from
         * @return {type} {description}
         */
        handleSubmit (from) {
            this.formItem.customerPackNumber = []; // 初始化
            this.formItem.specialItemJson.forEach((ele, index) => {
                // 重组数据
                if (ele.packagesInfo.length > 0) {
                    this.formItem.customerPackNumber.push({
                        actualWeight: ele.actualWeight,
                        actualVol:
                            (Number(ele.length) * Number(ele.width) * Number(ele.height)) /
                            1000000,
                        length: ele.length,
                        width: ele.width,
                        height: ele.height,
                        goodsNos: ele.selectIds,
                        id: ele.customerPackNumberId,
                    });
                    // ele.packagesInfo.forEach(item => {
                    //     this.formItem.customerPackNumber[index].goodsNos.push(item.deliveryOrderNo)
                    // });
                } else {
                    this.formItem.customerPackNumber.push({
                        actualWeight: ele.actualWeight,
                        actualVol: ele.actualVol,
                        length: ele.length,
                        width: ele.width,
                        height: ele.height,
                        goodsNos: "",
                        id: "",
                    });
                }
            });
            this.formItem.id = null;
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true;
                    packingDetailUnpackagedTask(this.formItem)
                        .then((res) => {
                            if (res.code === 100) {
                                this.$message({
                                    message: "打包成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5 * 2,
                                });
                                this.GoBack(); // 返回上一层
                                // 打印面单
                                printOrderListing(this.formItem.businessNumber).then((res) => {
                                    if (res.code === 100) {
                                        // window.location = 'http://' + res.content.url
                                        window.open("http://" + res.content.url);
                                    }
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
        handleReset (from) {
            this.fileList = [];
            this.formItem.remark = "";
            this.formItem.fileUrl = [];
            this.$refs[from].resetFields();
        },

        /**
         * 新增明细
         * @return {type} {description}
         */
        addTopic () {
            const obj = {
                actualWeight: "",
                actualVol: "",
                length: "",
                width: "",
                height: "",
                bool: false,
                selectIds: "",
                goodsNos: [],
                packagesInfo: [],
                customerPackNumberId: "",
            };
            this.formItem.specialItemJson.push(obj);
        },

        /**
         * 删除明细
         * @return {type} {description}
         */
        deleteTopic (goodsNos, index, customerPackNumberId) {
            let obj = {
                goodsNos: goodsNos,
                customerPackNumberId: customerPackNumberId,
            };
            if (index > -1) {
                this.$confirm("确定删除吗？", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(() => {
                    if (goodsNos) {
                        deleteSelectedPackingDetail(obj).then((res) => {
                            if (res.code === 100) {
                                this.formItem.specialItemJson.splice(index, 1);
                            }
                        });
                    } else {
                        this.formItem.specialItemJson.splice(index, 1);
                    }
                });
            }
        },

        /**
         * 返回上一层
         * @return {type} {description}
         */
        GoBack () {
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
        handTabClick (data) {
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
        setTagsViewTitle () {
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
        handleSearch (id) {
            this.loading = true;
            getPackingDetail(id)
                .then((res) => {
                    if (res.code === 100) {
                        this.formItem.packingId = res.content.customerPack.id;
                        this.formItem.customerName = res.content.customerPack.customerName;
                        this.formItem.packNum = res.content.goods.length;
                        this.formItem.createTime = res.content.customerPack.createTime;
                        this.formItem.routeName = res.content.customerPack.routeName;
                        this.formItem.routeType = String(
                            res.content.customerPack.routeType
                        );
                        this.formItem.address = res.content.customerPack.address;
                        this.formItem.message = res.content.customerPack.remarks;
                        this.formItem.phoneNumber = res.content.customerPack.phoneNumber;
                        this.formItem.addressee = res.content.customerPack.addressee;
                        this.formItem.businessNumber =
                            res.content.customerPack.businessNumber;
                        this.formItem.orderNumber = res.content.customerPack.orderNumber ? res.content.customerPack.orderNumber : ''
                        this.formItem.goodsData = res.content.goods; // 打包明细
                        this.formItem.loginName = res.content.customerPack.loginName;
                        this.formItem.destination = res.content.customerPack.destination;
                        this.formItem.insideMessage = res.content.customerPack.insideMessage;
                        this.formItem.remarks = res.content.customerPack.remarks;
                        this.searchCountries()  // 获取国家

                        // 重置子包裹的信息，防止报错
                        this.formItem.specialItemJson = [
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
                                                                customerPackNumberId: "",
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
        initializeSearch (id) {
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
        handlePrintCustomerPack (businessNumber) {
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
        handlePrintPrintOrder (businessNumber) {
            printOrderListing(businessNumber).then((res) => {
                if (res.code === 100) {
                }
            });
        },

        /**
       * 获取国家信息
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        searchCountries () {
            let obj = {
                sddName: ''
            }
            const p1 = getCountriesList(obj)
            Promise.all([p1]).then(res => {
                this.countriesData = res[0].content.country
                res[0].content.country.forEach(ele => {
                    if (ele.sddName == this.formItem.destination) this.countryId = ele.id
                });
            })
        },

        /**
         * 更改运输渠道
         * @return {type} {description}
         */
        changeChannel () {
            this.$refs['channelListShow'].handListInfoModel(this.countryId, this.formItem.packingId)
        }
    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach((ele) => {
            if (ele.title == "打包明细") {
                this.TagView = ele;
            }
        });
        // 初始化数据
        this.handleSearch(this.$route.query.id);
        // this.initializeSearch(this.$route.query.id)
        // this.formItem.customerId = obj.customerPack.customerId
        // this.formItem.packNum = obj.customerPack.packNum
        // this.formItem.createTime = obj.customerPack.createTime
        // this.formItem.routeName = obj.customerPack.routeName
        // this.formItem.routeType = String(obj.customerPack.routeType)
        // this.formItem.address = obj.customerPack.address
        // this.formItem.message = obj.customerPack.message
        // this.formItem.goodsData = obj.goods  // 打包明细
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
