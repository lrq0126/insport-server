/**
 * 【海运】添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="create-shipping-create">
    <el-form
      ref="form"
      :model="formItem"
      :rules="formItemRules"
      label-width="120px"
    >
      <el-row>
        <!-- 基本信息 -->
        <el-col :span="24">
          <el-divider content-position="left"
            ><i class="el-icon-mobile-phone"></i>基本信息</el-divider
          >
          <el-row>
            <el-col :span="8">
              <el-form-item label="渠道名称：" prop="routeName">
                <el-input
                  v-model="formItem.routeName"
                  placeholder="请输入渠道名称"
                ></el-input>
              </el-form-item>
              <el-form-item label="英文名称：">
                <el-input
                  v-model="formItem.englishName"
                  placeholder="请输入英文名称"
                ></el-input>
              </el-form-item>

              <el-form-item label="是否拼团渠道：">
                <el-radio-group v-model="formItem.isPin">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="运输类型：">
                <el-radio-group v-model="formItem.transportType">
                  <el-radio label="1">空运</el-radio>
                  <el-radio label="2">海运</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="是否需要预付关税：">
                <template>
                  <el-radio-group 
                    v-model="formItem.isTariffs"
                    style="width: 140px">
                    <el-radio label="1">是</el-radio>
                    <el-radio label="0">否</el-radio>
                  </el-radio-group>
                  <span
                    style="
                      margin-left: 10px;
                      font-size: 14px;
                      font-weight: bold;
                      color: #696969;
                    "
                    >货值币别：</span
                  >
                  <el-select
                    v-model="formItem.currency"
                    placeholder="请选择货值币别"
                  >
                    <el-option
                      v-for="item in currencyData"
                      :key="item.sddCode"
                      :value="item.sddCode"
                      :label="item.sddName"
                    />
                  </el-select>
                </template>
              </el-form-item>

              <el-form-item
                v-if="formItem.isTariffs == 1"
                label="预付关税："
                prop="tariffs"
              >
                <el-input
                  v-model="formItem.tariffs"
                  placeholder="请输入预付关税："
                  type="number"
                  ><template slot="append">%</template></el-input
                >
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="国家：" prop="countryId">
                <el-select v-model="formItem.countryId" placeholder="请选择">
                  <el-option
                    v-for="(item, index) in countriesList"
                    :key="index"
                    :label="item.sddName"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="始发仓：" prop="originatingPlace">
                <el-input
                  v-model="formItem.originatingPlace"
                  placeholder="请输入始发仓"
                ></el-input>
              </el-form-item>
              <el-form-item label="目的地：" prop="destination">
                <el-input
                  v-model="formItem.destination"
                  placeholder="请输入目的地"
                ></el-input>
              </el-form-item>

              <el-form-item label="预计送达时间：" prop="transportationTime">
                <el-input
                  v-model="formItem.transportationTime"
                  placeholder="请输入预计送达时间"
                ></el-input>
              </el-form-item>

              <el-form-item label="是否有保险：">
                <el-radio-group v-model="formItem.isInsurance">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item
                label="保险单："
                v-if="formItem.isInsurance == 1"
                prop="insuranceId"
              >
                <el-input
                  v-model="insuranceName"
                  placeholder="请选择保险单："
                  style="width: 300px"
                  :readonly="true"
                ></el-input>
                <el-button type="success" @click="openInsurance"
                  >选择保单</el-button
                >
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="13">
              <el-form-item label="打包事项：">
                <el-input
                  v-model="formItem.packAttention"
                  placeholder="请输入操作员打包该渠道的注意事项"
                  type="textarea"
                  :rows="4"
                ></el-input>
              </el-form-item>

              <el-form-item label="备注信息：">
                <el-input
                  v-model="formItem.remarks"
                  placeholder="请输入备注信息"
                  type="textarea"
                  :rows="4"
                ></el-input>
              </el-form-item>

              <el-form-item label="客户须知：">
                <el-input
                  v-model="formItem.customerRequirements"
                  placeholder="客户须知信息：用于客户选择渠道时，展示给客户需要注意的事项"
                  type="textarea"
                  :rows="4"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <!-- 计算规则 -->
        <el-col :span="24">
          <el-divider content-position="left"
            ><i class="el-icon-mobile-phone"></i>计算规则</el-divider
          >
          <el-row>
            <el-col :span="10">
              <el-form-item
                label="结算币别："
                label-width="200px"
                prop="noteType"
              >
                <el-select
                  v-model="formItem.noteType"
                  placeholder="请选择结算币别"
                >
                  <el-option label="人民币" value="1" />
                </el-select>
              </el-form-item>
              <el-form-item
                label="体积重："
                label-width="200px"
                prop="sideLengthLimit"
              >
                <el-input
                  v-model="formItem.sideLengthLimit"
                  style="width: 350px"
                  placeholder="请输入长度"
                  ><template slot="prepend">边长超过</template
                  ><template slot="append"
                    >CM时需要计算体积重</template
                  ></el-input
                >
              </el-form-item>

              <el-form-item
                label="货物长度限制（空运特有）："
                label-width="200px"
              >
                <el-input
                  v-model="formItem.firstLength"
                  style="width: 275px"
                  placeholder="请输入长度"
                  ><template slot="prepend">第一边不得超过</template
                  ><template slot="append">CM</template></el-input
                >
              </el-form-item>

              <el-form-item label="" label-width="200px">
                <el-input
                  v-model="formItem.secondLength"
                  style="width: 275px"
                  placeholder="请输入长度"
                  ><template slot="prepend">第二边不得超过</template
                  ><template slot="append">CM</template></el-input
                >
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="是否每票一件：">
                <el-radio-group v-model="formItem.isPerTicketPack">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="敏感货物加收：">
                <el-input
                  v-model="formItem.sensitivePrice"
                  style="width: 180px"
                  placeholder="请输入价格"
                  ><template slot="append">元</template></el-input
                >
              </el-form-item>

              <el-row>
                <el-col :span="17">
                  <el-form-item label="叉车费：">
                    <el-input
                      v-model="formItem.greaterWeight"
                      style="width: 265px"
                      placeholder="请输入重量"
                      ><template slot="prepend">超过</template
                      ><template slot="append">KG需叉车费</template></el-input
                    >
                  </el-form-item>
                </el-col>
                <el-col :span="6" style="min-width: 100px">
                  <el-form-item label="" label-width="0">
                    <el-input
                      v-model="formItem.forkliftFee"
                      placeholder="请输入价钱"
                      ><template slot="append">元</template></el-input
                    >
                  </el-form-item>
                </el-col>
              </el-row>

              <el-form-item label="空运重量限制：">
                <el-input
                  v-model="formItem.upperLimitWeight"
                  style="width: 275px"
                  placeholder="请输入重量"
                  ><template slot="prepend">单个货物不得超过</template
                  ><template slot="append">KG</template></el-input
                >
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <!-- end -->

        <!-- 计算明细 -->
        <el-col :span="24">
          <el-divider content-position="left"
            ><i class="el-icon-mobile-phone"></i>计算明细</el-divider
          >
          <el-col :span="8">
            <el-col :span="24">
              <el-form-item
                label="单立方重量上限："
                label-width="145px"
                prop="cubeUpper"
              >
                <el-input
                  v-model="formItem.cubeUpper"
                  style="width: 180px"
                  placeholder="请输入重量"
                  ><template slot="append">KG</template></el-input
                >
              </el-form-item>
            </el-col>
          </el-col>
          <!-- 2020-3-7 -->
          <el-col :span="8">
            <el-col :span="24">
              <el-form-item
                label="体积大需转KG除："
                label-width="160px"
                prop="volConversion"
              >
                <el-input-number
                  v-model="formItem.volConversion"
                  :step="1"
                  :min="0"
                  controls-position="right"
                  step-strictly
                ></el-input-number>
                <!-- <el-input v-model="scope.row.volConversion"
                                          placeholder="请输入数字"></el-input> -->
              </el-form-item>
            </el-col>
          </el-col>
          <!-- end -->
          <el-col :span="23" class="packagePrice">
            <el-form-item label="计算明细：">
              <el-button
                type="primary"
                plain
                style="margin-bottom: 5px"
                v-loading="priceLoading"
                icon="el-icon-circle-plus-outline"
                @click="addTopicBig"
                >新增明细</el-button
              >
              <el-button type="success" plain @click="downloadChannelTemplate()"
                >下载渠道编辑模板</el-button
              >

              <el-button type="warning" plain @click="addPriceExcel"
                >Excel文本输入</el-button
              >

              <el-table
                :data="formItem.packagePrice"
                border
                fit
                highlight-current-row
                style="width: 100%; margin-top: 20px"
              >
                <el-table-column
                  label="序号"
                  width="80"
                  type="index"
                  align="center"
                >
                </el-table-column>

                <el-table-column label="起始体积/m³" align="center">
                  <template slot-scope="scope">
                    <el-form-item
                      style="margin-bottom: 15px"
                      :prop="'packagePrice.' + scope.$index + '.startVolume'"
                      :rules="[
                        {
                          required: true,
                          message: '请输入正确的起始体积数据',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="scope.row.startVolume"
                        type="number"
                        placeholder="请输入起始体积"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column label="结尾体积/m³" align="center">
                  <template slot-scope="scope">
                    <el-form-item
                      style="margin-bottom: 15px"
                      :prop="'packagePrice.' + scope.$index + '.endVolume'"
                      :rules="[
                        {
                          required: true,
                          message: '请输入正确的结尾体积数据',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="scope.row.endVolume"
                        type="number"
                        placeholder="请输入结尾体积"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column label="单位价格（元/m³）" align="center">
                  <template slot-scope="scope">
                    <el-form-item
                      style="margin-bottom: 15px"
                      :prop="'packagePrice.' + scope.$index + '.price'"
                      :rules="[
                        {
                          required: true,
                          message: '请输入正确的单位价格数据',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        v-model="scope.row.price"
                        type="number"
                        placeholder="请输入单位价格"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column label="特殊备注" align="center">
                  <template slot-scope="scope">
                    <el-form-item style="margin-bottom: 0">
                      <el-input
                        v-model="scope.row.remarks"
                        type="textarea"
                        :rows="2"
                        placeholder="请输入特殊备注"
                      ></el-input>
                    </el-form-item>
                  </template>
                </el-table-column>

                <el-table-column label="操作" width="120" align="center">
                  <template slot-scope="scope">
                    <el-button
                      type="danger"
                      size="mini"
                      plain
                      @click="deleteTopicBig(scope.$index)"
                      >删除</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-col>

        <el-col :span="20">
          <el-row>
            <el-col :span="20" style="margin-top: 40px">
              <el-form-item label-width="70%">
                <el-button
                  :loading="saving"
                  type="primary"
                  @click="handleSubmit('form')"
                  >保 存 渠 道</el-button
                >
                <el-button @click="handleReset('form')">重 置</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>

    <vol-price-model-view
      ref="volPriceModelView"
      @updatePriceList="updatePriceList"
    />
    <insurance-model-view
      ref="insuranceModelView"
      @updateInsurance="setInsurance"
    />
  </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import {
  shippingSelectChannelList,
  shippingAddChannelList,
} from "@/api/channel-management/channel-list";
import { getCountriesList } from "@/api/channel-management/countries-list";
import { getCurrency } from "@/api/receipt-management/dict-manage";

import VolPriceModelView from "./vol-price-model";
import InsuranceModelView from "./insurance-model";
import config from "@/config";

export default {
  components: {
    waves,
    VolPriceModelView,
    InsuranceModelView,
  },
  name: "Create",
  data() {
    return {
      saving: false,
      priceLoading: false,
      shippingSelect: [], //进位换算规则
      countriesList: [], // 国家
      currencyData: [], // 货值货币类型
      insuranceName: "",
      formItem: {
        routeName: "",
        englishName: "",
        originatingPlace: "",
        destination: "",
        countryId: "",
        noteType: "",
        transportationTime: "",
        packAttention: "",
        remoteFee: "",
        remarks: "",
        transportType: "2",
        routeType: "2",
        // 公共
        // smallWeightCarryType: '',

        beforeWeightCarry: "",
        beforeWeightCarryType: "",
        afterWeightCarry: "",
        afterWeightCarryType: "",

        customerRequirements: "",
        sideLengthLimit: "1",
        isPin: "0",
        isPerTicketPack: "0",

        isTariffs: "0",
        tariffs: "",
        currency:"CENY000000001",
        isInsurance: "0",
        insuranceId: "",

        greaterWeight: "",
        forkliftFee: "",
        firstLength: "",
        secondLength: "",
        upperLimitWeight: "",
        sensitivePrice: "",
        cubeUpper: "",
        volConversion: "",
        smallPackagePrice: [
          {
            // greaterWeight: 0,
            // forkliftFee: 0,
            // firstLength: 0,
            // secondLength: 0,
            // upperLimitWeight: 0,
            // smallWeightCarryType: 0,
            smallStartWeight: 0,
            smallEndWeight: 0,
            smallPrice: 0,
            smallRemarks: 0,
          },
        ],
        packagePrice: [
          {
            // volConversion: '',
            // sensitivePrice: '',
            // cubeUpper: '',
            // greaterWeight: '',
            // forkliftFee: '',
            // firstLength: '',
            // secondLength: '',
            // upperLimitWeight: '',
            startVolume: "",
            endVolume: "",
            price: "",
            remarks: "",
          },
        ],
      },

      formItemRules: {
        routeName: [
          { required: true, message: "渠道名称不能为空", trigger: "blur" },
        ],
        originatingPlace: [
          { required: true, message: "始发仓不能为空", trigger: "blur" },
        ],
        destination: [
          { required: true, message: "目的地不能为空", trigger: "blur" },
        ],
        noteType: [
          { required: true, message: "请选择结算币别", trigger: "change" },
        ],
        beforeWeightCarryType: [
          { required: true, message: "请选择进位换算规则", trigger: "change" },
        ],
        countryId: [
          { required: true, message: "请选择国家", trigger: "change" },
        ],
        transportationTime: [
          { required: true, message: "预计送达时间不能为空", trigger: "blur" },
        ],
        sideLengthLimit: [
          {
            required: true,
            message: "体积重计算规则不能为空",
            trigger: "blur",
          },
        ],
        sensitivePrice: [
          { required: true, message: "敏感货物加收不能为空", trigger: "blur" },
        ],
        cubeUpper: [
          {
            required: true,
            message: "单立方重量上限不能为空",
            trigger: "blur",
          },
        ],
        volConversion: [
          {
            required: true,
            message: "体积大需转KG除不能为空",
            trigger: "blur",
          },
        ],

        startVolume: [
          {
            required: true,
            message: "请输入正确的起始体积数据",
            trigger: "blur",
          },
        ],
        endVolume: [
          {
            required: true,
            message: "请输入正确的结尾体积数据",
            trigger: "blur",
          },
        ],
        price: [
          {
            required: true,
            message: "请输入正确的单位价格数据",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    /**
     * 获取下拉选择数据信息
     * @param  {nnumber} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch() {
      let obj = {
        sddName: "",
      };
      const p1 = shippingSelectChannelList();
      const p2 = getCountriesList(obj);
      Promise.all([p1, p2]).then((res) => {
        this.shippingSelect = res[0].content.weightRule;
        this.countriesList = res[1].content.country;
      });
    },

    /**
     * 下载模板
     */
    downloadChannelTemplate() {
      const baseUrl =
        process.env.NODE_ENV === "development"
          ? config.baseUrl.dev
          : config.baseUrl.pro;
      window.location.href = baseUrl + "/api/common/downloadChannelTemplateExcel";
    },

    /**
     * 保存
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit(from) {
      this.$refs[from]
        .validate((valid) => {
          if (valid) {
            this.saving = true;
            shippingAddChannelList(this.formItem)
              .then((res) => {
                if (res.code === 100) {
                  this.$message({
                    message: "海运渠道保存成功",
                    type: "success",
                    showClose: true,
                    duration: 1000 * 1.5 * 2,
                  });
                  this.handleReset("form");
                } else {
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
          }
        })
        .finally(() => {
          this.saving = false;
        });
    },

    openInsurance() {
      this.$refs["insuranceModelView"].openDialog();
    },

    setInsurance(data) {
      this.formItem.insuranceId = data.id;
      this.insuranceName = data.insuranceName;
    },

    addPriceExcel() {
      this.$refs["volPriceModelView"].openDialog();
    },

    updatePriceList(data) {
      this.priceLoading = true;
      this.$message({
        message: "Excel转换成功",
        type: "success",
      });

      setTimeout(() => {
        this.priceLoading = false;
      }, 1000);
      this.formItem.packagePrice = data;
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset(from) {
      this.formItem.remarks = "";
      this.formItem.englishName = "";
      this.formItem.greaterWeight = "";
      this.formItem.forkliftFee = "";
      this.formItem.firstLength = "";
      this.formItem.secondLength = "";
      this.formItem.upperLimitWeight = "";

      this.formItem.sensitivePrice = "";

      this.formItem.currency = "";
      this.formItem.insuranceId = "";
      this.insuranceName = "";

      this.formItem.packagePrice = [
        {
          startVolume: "",
          endVolume: "",
          price: "",
          remarks: "",
        },
      ];
      this.$refs[from].resetFields();
    },

    /**
     * 新增小包裹
     * @return {type} {description}
     */
    addTopicSmall() {
      const obj = {
        // greaterWeight: '',
        // forkliftFee: '',
        // firstLength: '',
        // secondLength: '',
        // upperLimitWeight: '',
        // smallWeightCarryType: '',
        smallStartWeight: "",
        smallEndWeight: "",
        smallPrice: "",
        smallRemarks: "",
      };
      this.formItem.smallPackagePrice.push(obj);
    },

    /**
     * 删除小包裹
     * @return {type} {description}
     */
    deleteTopicSmall(index) {
      if (index > -1) {
        this.formItem.smallPackagePrice.splice(index, 1);
      }
    },

    /**
     * 新增大包裹
     * @return {type} {description}
     */
    addTopicBig() {
      const obj = {
        // volConversion: '',
        // sensitivePrice: '',
        // cubeUpper: '',
        // greaterWeight: '',
        // forkliftFee: '',
        // firstLength: '',
        // secondLength: '',
        // upperLimitWeight: '',
        startVolume: "",
        endVolume: "",
        price: "",
        remarks: "",
      };
      this.formItem.packagePrice.push(obj);
    },

    /**
     * 删除大包裹
     * @return {type} {description}
     */
    deleteTopicBig(index) {
      if (index > -1) {
        this.formItem.packagePrice.splice(index, 1);
      }
    },

    /**
     * 获取已有货币字典
     */
    getCurrencyData() {
      getCurrency().then((res) => {
        this.currencyData = res.content;
      });
    },
  },

  mounted() {
    this.getCurrencyData();
    this.handleSearch();
  },
};
</script>

<style lang="scss" scope>
.create-shipping-create {
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
