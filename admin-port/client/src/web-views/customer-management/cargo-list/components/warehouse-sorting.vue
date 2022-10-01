<template>
  <div class="base-info-class">
    <div style="width: 15%; float: left">
      <div style="margin-left: 10%; margin-right: 10%">
        <a @click="selectSystem('FLY')">
          <div class="system-style2-class" v-if="systemName == 'FLY'">
            飞 轮
          </div>
          <div class="system-style1-class" v-else>飞 轮</div>
        </a>
      </div>
      <div style="margin-left: 10%; margin-right: 10%">
        <a @click="selectSystem('HUAWEI')">
          <div class="system-style2-class" v-if="systemName == 'HUAWEI'">
            华 威
          </div>
          <div class="system-style1-class" v-else>华 威</div>
        </a>
      </div>
      <div style="margin-left: 10%; margin-right: 10%">
        <a @click="selectSystem('TAOMAO')">
          <div class="system-style2-class" v-if="systemName == 'TAOMAO'">
            淘 猫
          </div>
          <div class="system-style1-class" v-else>淘 猫</div>
        </a>
      </div>
      <div style="margin-left: 10%; margin-right: 10%">
        <a @click="selectSystem('HUASU')">
          <div class="system-style2-class" v-if="systemName == 'HUASU'">
            华 速
          </div>
          <div class="system-style1-class" v-else>华 速</div>
        </a>
      </div>
      <div style="margin-left: 10%; margin-right: 10%">
        <a @click="selectSystem('HUAYOU')">
          <div class="system-style2-class" v-if="systemName == 'HUAYOU'">
            华 邮
          </div>
          <div class="system-style1-class" v-else>华 邮</div>
        </a>
      </div>
      <!-- <div style="margin-left: 10%; margin-right: 10%;">
            <a @click="systemName = 'XIYOU'">
                <div class="system-style2-class" v-if="systemName == 'XIYOU'">
                    西 柚
                </div>
                <div class="system-style1-class" v-else>
                    西 柚
                </div>
            </a>
        </div> -->
    </div>
    <div style="width: 85%; float: left">
      <div class="cargo-list-create">
        <div style="float: left; width: 60%">
          <el-form
            ref="form"
            :loading="formLoading"
            :model="formItem"
            :rules="formItemRules"
            label-width="200px"
          >
            <el-row style="margin-top: 50px">
              <el-col :span="24">
                <el-form-item label="快递单号/唛头：" prop="deliveryOrderNo">
                  <el-input
                    v-model="formItem.deliveryOrderNo"
                    id="deliveryOrderNoInput"
                    placeholder="请输入快递单号/唛头"
                    style="width: 455px"
                    @keyup.enter.native="handleOrderInfo"
                  ></el-input>
                    <!-- @blur="handleOrderInfo" -->
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="快递公司：">
                  <el-select
                    v-model="formItem.deliveryName"
                    filterable
                    placeholder="请选择快递公司"
                    style="width: 455px"
                  >
                    <el-option
                      v-for="(item, index) in deliveryCompanyData"
                      :key="index"
                      :value="item.deliveryNo"
                      :label="item.deliveryName"
                    />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="货物名称：" prop="goodsName">
                  <el-input
                    v-model="formItem.goodsName"
                    placeholder="请输入货物名称"
                    style="width: 455px"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-row style="display: contents">
                <el-col :span="6" style="width: auto; margin-right: 15px">
                  <el-form-item label="会员id：">
                    <el-input
                      v-model="formItem.loginNameTest"
                      id="loginNameInput"
                      placeholder="会员id"
                      @keyup.enter.native="changeLoginName"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6" style="width: auto; margin-left: 10px">
                  <el-form-item label="客户：" label-width="70px">
                    <el-input
                      v-model="formItem.customerNoTest"
                      placeholder="请输入客户名称"
                      @keyup.enter.native="changeCustomerNo"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!-- end -->

              <el-col :span="24">
                <el-form-item label="客户：" prop="customerName">
                  <el-select
                    v-model="formItem.customerName"
                    placeholder="请输入客户名称"
                    filterable
                    :disabled="true"
                    style="width: 370px"
                  >
                  </el-select>
                  <el-button
                    :loading="saving"
                    type="info"
                    @click="handleSelectCustomer"
                    >选择客户</el-button
                  >
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="会员id：" prop="loginName">
                  <el-input
                    v-model="formItem.loginName"
                    placeholder="会员id"
                    :disabled="true"
                    style="width: 455px"
                    ref="firstInput"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="包装类型：">
                  <el-radio v-model="formItem.packageType" label="袋子"
                    >袋子</el-radio
                  >
                  <el-radio v-model="formItem.packageType" label="纸箱"
                    >纸箱</el-radio
                  >
                  <el-radio v-model="formItem.packageType" label="蛇皮袋"
                    >蛇皮袋</el-radio
                  >
                  <el-radio v-model="formItem.packageType" label="其他"
                    >其他</el-radio
                  >
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="货物件数：" prop="packageNum">
                  <el-input
                    v-model="formItem.packageNum"
                    type="number"
                    :min="0"
                    placeholder="请输入货物数量"
                    style="width: 270px"
                  ></el-input>
                  <el-button
                    style="margin-left: 50px"
                    type="danger"
                    @click="abnormalInStorage"
                    :disabled="!formItem.deliveryOrderNo"
                    >异常件入库</el-button
                  >
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-col :span="6" style="width: 365px">
                  <el-form-item label="重量：" prop="kg">
                    <el-input
                      v-model="formItem.kg"
                      type="number"
                      :min="0"
                      placeholder="请输入重量"
                      id="weightInput"
                      style="width: 180px"
                      @keyup.enter.native="handleSubmit('form')"
                      ><template slot="append">KG</template></el-input
                    >
                  </el-form-item>
                </el-col>

                <el-col :span="6" style="width: 365px">
                  <el-form-item label="高度：" label-width="75px" prop="height">
                    <el-input
                      v-model="formItem.height"
                      type="number"
                      :min="0"
                      placeholder="请输入高度"
                      style="width: 180px"
                      ><template slot="append">CM</template></el-input
                    >
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="24">
                <el-col :span="6" style="width: 365px">
                  <el-form-item label="长度：" prop="length">
                    <el-input
                      v-model="formItem.length"
                      type="number"
                      :min="0"
                      placeholder="请输入长度"
                      style="width: 180px"
                      ><template slot="append">CM</template></el-input
                    >
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="宽度：" label-width="75px" prop="width">
                    <el-input
                      v-model="formItem.width"
                      type="number"
                      :min="0"
                      placeholder="请输入宽度"
                      style="width: 180px"
                      @keyup.enter.native="handleSubmit('form')"
                      ><template slot="append">CM</template></el-input
                    >
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="24">
                <el-col :span="5" style="width: 365px">
                  <el-form-item label="区：">
                    <el-select
                      v-model="formItem.storageArea"
                      placeholder="请选择区号"
                      @change="changeSelect"
                      style="width: 180px"
                    >
                      <el-option
                        v-for="(item, index) in storageAreaData"
                        :key="index"
                        :value="item.storageArea"
                        :label="item.storageArea"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="排：" label-width="75px">
                    <!-- prop="storageRow" -->
                    <el-select
                      v-model="formItem.storageRow"
                      placeholder="请选择排号"
                      style="width: 180px"
                    >
                      <el-option
                        v-for="item in storageRowData"
                        :key="item"
                        :value="item"
                        :label="item"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="24">
                <el-form-item label="操作人名字：">
                  <el-input
                    v-model="formItem.operatorName"
                    :disabled="true"
                    placeholder="请输入操作人名字"
                    style="width: 455px"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="备注信息：">
                  <el-input
                    type="textarea"
                    v-model="formItem.message"
                    :rows="5"
                    placeholder="请输入备注信息"
                    style="width: 455px"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="" label-width="280px">
                  <audio :src="audioUrl1" id="eventAaa"></audio>
                  <el-button
                    :loading="saving"
                    type="primary"
                    @click="handleSubmit('form')"
                    >入 仓</el-button
                  >
                  <el-button @click="handleReset('form')">重 置</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div style="float: left; width: 40%; margin-top: 20%">
          <el-table :data="customerGoodsData" border style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="location"
              label="货物地址"
              min-width="160"
              align="center"
            ></el-table-column>

            <el-table-column
              prop="goodsSum"
              label="货物数量"
              min-width="150"
              align="center"
            ></el-table-column>

            <!-- <el-table-column
              prop="upTime"
              label="上架时间"
              min-width="150"
              align="center"
            ></el-table-column> -->
          </el-table>
        </div>

        <!-- 弹窗信息 -->
        <create-model
          ref="createModel"
          @updateForm="handleGetFormInfo"
          @listTap="handleCustomerData"
          :select-data="selectData"
        />
      </div>
    </div>
  </div>
</template>

<script>
import createModel from "./create-model";
import Cookies from "js-cookie";
import qs from "qs";
import { getToken } from "@/utils/auth";
import {ocrInWareSorting} from '@/api/customer-management/warehouse-sorting'
export default {
  components: {
    createModel,
  },
  props: {
    selectData: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  watch: {
    selectData() {
      this.deliveryCompanyData = this.selectData.deliveryCompany;
      this.storageAreaData = this.selectData.storage;
    },
  },
  data() {
    const validateLength = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        if (value > 0) {
          callback();
        } else {
          callback(new Error("请输入长度大于0的数字"));
        }
      }
    };
    const validateWidth = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        if (value > 0) {
          callback();
        } else {
          callback(new Error("请输入宽度大于0的数字"));
        }
      }
    };
    const validateHeight = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        if (value > 0) {
          callback();
        } else {
          callback(new Error("请输入高度大于0的数字"));
        }
      }
    };
    const validateKG = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        if (value > 0) {
          callback();
        } else {
          callback(new Error("请输入重量大于0的数字"));
        }
      }
    };

    return {
      systemName: "FLY",
      requestUrl: 'http://jiyun.flycloudstorage.com',

      packageTypes: "袋子",
      bool: true,
      formLoading: false,
      customerData: [], // 客户信息
      deliveryCompanyData: [], // 快递公司
      storageAreaData: [], // 区号
      storageRowData: [], // 排号
      saving: false,
      noticeType: [], // 通知公告
      customerGoodsData: [],
      audioUrl1: "http://www.wennever.cn/upload/pdf/warein.mp3",
      formItem: {
        goodsName: "客户未预报名称",
        storageArea: "",
        storageRow: "",
        customerNo: "",
        customerName: "",
        deliveryOrderNo: "",
        deliveryName: "",
        deliveryNo: "",
        packageNum: 1,
        loginName: "",
        kg: "",
        vol: "",
        length: "1",
        width: "1",
        height: "1",
        operatorCode: Cookies.get("usercode"),
        packageType: "袋子",
        message: "",
        type: "1", // type == 1 是入仓 type == 2 是预录入
        operatorName: Cookies.get("userName"),
        goodsNo: "",
        customerNoTest: "",
        loginNameTest: "",
      },

      formItemRules: {
        deliveryOrderNo: [
          { required: true, message: "快递单号/唛头不能为空", trigger: "blur" },
        ],
        goodsName: [
          { required: true, message: "货物名称不能为空", trigger: "blur" },
        ],
        customerNo: [
          { required: true, message: "客户不能为空", trigger: "change" },
        ],
        loginName: [
          { required: true, message: "会员id：不能为空", trigger: "change" },
        ],
        customerNoTest: [
          { required: true, message: "客户不能为空", trigger: "change" },
        ],
        loginNameTest: [
          { required: true, message: "会员id：不能为空", trigger: "change" },
        ],
        packageNum: [
          { required: true, message: "请输入货物件数", trigger: "blur" },
        ],
        length: [{ validator: validateLength, trigger: "blur" }],
        width: [{ validator: validateWidth, trigger: "blur" }],
        height: [{ validator: validateHeight, trigger: "blur" }],
        kg: [{ validator: validateKG, trigger: "blur" }],
      },
    };
  },

  methods:{
    handleSelectCustomer() {
      let data = {
        systemName: this.systemName,
        customerName: "",
        loginName: ""
      }
      this.$refs["createModel"].handCustomerInfoModel1(data);
    },

    handleCustomerData(data) {
      this.formItem.customerNo = data.customerNo;
      this.formItem.loginName = data.loginName;
      this.formItem.customerName = data.customerName;
      this.formItem.packageNum = 1;
      let obj = {
        customerNo: data.customerNo,
      };
      if (obj) {
        let that = this
        axios({
          method: "post",
          url: this.requestUrl+'/api/getCustomerOrderLocation',
          data: qs.stringify(obj)
        }).then(function (res) {
            if(res.data.content){
                that.customerGoodsData =  res.data.content
            }
        }).catch(err => {
            console.error(err);
            this.customerGoodsData = []
        }).finally(() => {
            this.customerGoodsData = that.customerGoodsData;
        })
      }
      var elInput = document.getElementById("weightInput"); //根据id选择器选中对象
      elInput.focus();
      elInput.value = "asdasd";
    },

    /**
     * 音频
     * @return {type} {description}
     */
    playAudio() {
      let buttonAudio = document.getElementById("eventAaa");
      buttonAudio.play();
    },

    /**
     * 入仓
     * @param  {string} from
     * @return {type} {description}
     */
    handleSubmit(from) {
      if (this.formItem.storageArea == null) {
        this.formItem.storageArea = ""; //初始化值
        this.formItem.storageRow = ""; //初始化值
      }
      this.$refs[from].validate((valid) => {
        if (valid) {
          let weight = parseFloat(this.formItem.kg);
          if (weight > 1000) {
            this.$confirm(
              "货物重量数值偏大：【" + weight + "KG】, 请确认是否正确",
              "提示",
              {
                confirmButtonText: "确认正确",
                cancelButtonText: "取消",
                type: "warning",
              }
            ).then(() => {
              this.comfirmInWare();
            });
          } else {
            this.comfirmInWare();
          }
        }
      });
    },

    /**
     * 异常件入库
     */
    abnormalInStorage() {
      if(this.formItem.kg){
        this.$confirm( "是否确认是异常件","提示",{
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
            let result;
            axios({
                method: "post",
                url: this.requestUrl+'/api/goods/abnormalInStorage',
                data: qs.stringify(this.formItem)
            }).then(function (res) {
                    result = res.data
            }).finally(() => {
                if(result.code == 100){
                    this.$message({
                        message:"异常件入库成功",
                        type:"success"
                    })
                    this.handleReset("form");
                }else{
                    this.$message({
                        message:"异常件入库失败",
                        type:"error"
                    })
                }
            })
        }); 
      }else{
        this.$message({
          message:"请输入货物重量",
          type:"error"
        })
      }
      
      
    },

    // 确认提交入库
    comfirmInWare() {
      this.formItem.type = "1";
      this.saving = true;
      this.formLoading = true;
      let result;

        // axios({
        //     method: "post",
        //     url: this.requestUrl+'/api/goods/warehouseSorting',
        //     // url: 'http://localhost:8620/api/goods/warehouseSorting',
        //     data: qs.stringify(this.formItem),
        // }).then(function (res) {
        //     result = res.data
        // }).catch(err => {
        //     console.error(err);
        //     result = {
        //         code: -100,
        //         message: "入仓分拣错误"
        //     }
        // }).finally(() => {
        //     if (result.code === 100) {
        //         this.$message({
        //             message: "入仓成功",
        //             type: "success",
        //             showClose: true,
        //             duration: 1000 * 1.5 * 3,
        //         });
        //         this.handleReset("form");
        //         // 播放入库成功的声音
        //         setTimeout(() => {
        //             this.playAudio(); // 音频
        //         }, 200);

        //         var elInput = document.getElementById("deliveryOrderNoInput"); //根据id选择器选中对象
        //         elInput.focus();
        //     }else{
        //        this.$message({
        //             message: result.message,
        //             type: "error",
        //             showClose: true,
        //             duration: 1000 * 1.5 * 3,
        //         }); 
        //     }
        //     setTimeout(() => {
        //         this.saving = false;
        //         this.formLoading = false;
        //     }, 800);
        // })
        this.formItem.systemName = this.systemName;

        ocrInWareSorting(this.formItem).then((res) =>{
            if (res.code === 100) {
                this.$message({
                    message: "入仓成功",
                    type: "success",
                    showClose: true,
                    duration: 1000 * 1.5 * 3,
                });
                this.handleReset("form");
                // 播放入库成功的声音
                setTimeout(() => {
                    this.playAudio(); // 音频
                }, 200);

                var elInput = document.getElementById("deliveryOrderNoInput"); //根据id选择器选中对象
                elInput.focus();
            }else{
               this.$message({
                    message: res.message,
                    type: "error",
                    showClose: true,
                    duration: 1000 * 1.5 * 3,
                }); 
            }
            setTimeout(() => {
                this.saving = false;
                this.formLoading = false;
            }, 800);
        })
    },

    /**
     * 改变事件
     * @return {type} {description}
     */
    changeSelect(value) {
      this.formItem.storageRow = "";
      this.storageAreaData.forEach((ele) => {
        if (ele.storageArea == value) {
          this.storageRowData = Number(ele.storageRow);
        }
      });
    },

    /**
     * 重置
     * @param  {string} from
     * @return {type} {description}
     */
    handleReset(from) {
      this.formItem.deliveryNo = "";
      this.formItem.storageArea = "";
      this.formItem.storageRow = "";
      this.formItem.kg = "";
      this.formItem.vol = "";
      this.formItem.length = "1";
      this.formItem.width = "1";
      this.formItem.height = "1";
      this.formItem.message = "";
      this.formItem.operatorCode = Cookies.get("usercode");
      this.formItem.operatorName = Cookies.get("userName");
      this.formItem.customerNo = "";
      this.formItem.loginName = "";
      this.formItem.customerNoTest = "";
      this.formItem.loginNameTest = "";
      this.formItem.goodsNo = "";
      this.formItem.packageType = "袋子";
      this.$refs[from].resetFields();
    },

    /**
     * 根据快递单号获取相关信息
     * @return {type} {description}
     */
    handleOrderInfo() {
      if (this.formItem.deliveryOrderNo.length >= 4) {
        const loading = this.$loading({
          lock: true,
          text: '正在查询包裹信息...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        let result;
        axios({
          method: "post",
          url: this.requestUrl+'/api/goods/find/by/deliveryOrderNo',
          data: qs.stringify(this.formItem)
        }).then((res) => {
          result = res.data
        }).finally(() => {
          setTimeout(() => {
            loading.close();
          }, 200);
          if (result.code == 100) {
            if (result.content != null) {
              if (result.content.goods.length > 0) {
                this.$refs["createModel"].handListInfoModel(result.content.goods);
              }
            } else {
              this.formItem.goodsName = "客户未预报名称";
              this.formItem.storageArea = "";
              this.formItem.storageRow = "";
              this.formItem.customerNo = "";
              this.formItem.deliveryNo = "";
              this.formItem.packageNum = 1;
              this.formItem.vol = this.formItem.length = 1;
              this.formItem.width = 1;
              this.formItem.height = 1;
              this.formItem.message = "";
              this.formItem.goodsNo = "";
              this.storageRowData = []; // 初始化值
              var elInput = document.getElementById("loginNameInput"); //根据id选择器选中对象
              elInput.focus();
            }
          }
          if (result.code == -100) {
            this.formItem.goodsName = "客户未预报名称";
            this.formItem.storageArea = "";
            this.formItem.storageRow = "";
            this.formItem.customerNo = "";
            this.formItem.deliveryNo = "";
            this.formItem.packageNum = 1;
            this.formItem.vol = this.formItem.length = 1;
            this.formItem.width = 1;
            this.formItem.height = 1;
            this.formItem.message = "";
            this.formItem.goodsNo = "";
            this.storageRowData = []; // 初始化值
            var elInput = document.getElementById("loginNameInput"); //根据id选择器选中对象
            elInput.focus();
          }
        })
          
      }
    },

    /**
     * 写入选择快递
     * @return {type} {description}
     */
    handleGetFormInfo(data) {
      let obj = {
        customerNo: data.customerNo,
      };
      if (obj) {
        let that = this
        axios({
          method: "post",
          url: this.requestUrl+'/api/getCustomerOrderLocation',
          data: qs.stringify(obj)
        }).then(function (res) {
            if(res.data.content){
                that.customerGoodsData =  res.data.content
            }
        }).finally(() => {
            this.customerGoodsData = that.customerGoodsData;
        })
      }
      let location = data.location ? data.location.split(",") : "";
      if (location) {
        if (location[0]) {
          this.changeSelect(location[0]); // 赋值下拉选择数据
        } else {
          this.storageRowData = [];
        }
      }
      this.formItem.goodsNo = data.goodsNo; // 订单编号
      this.formItem.deliveryOrderNo = data.deliveryOrderNo; // 快递单号

      this.formItem.goodsName = data.goodsName;
      this.formItem.loginName = data.loginName;
      this.formItem.customerNo = data.customerNo;
      this.formItem.customerName = data.customerName;
      this.formItem.deliveryName = data.deliveryName;
      this.formItem.deliveryNo = data.deliveryNo;
      this.formItem.packageNum = data.packageNum ? data.packageNum : 1;
      this.formItem.kg = data.kg ? data.kg : "";
      this.formItem.vol = data.vol;
      this.formItem.length = data.length ? data.length : 1;
      this.formItem.width = data.width ? data.width : 1;
      this.formItem.height = data.height ? data.height : 1;
      // this.formItem.packageType = data.packageType ? data.packageType : "袋子";
      this.formItem.message = data.message;
      if (location) {
        if (location[1]) {
          this.formItem.storageArea = location[0];
          this.formItem.storageRow = Number(location[1]);
        }
      }
      var elInput = document.getElementById("weightInput"); //根据id选择器选中对象
      elInput.focus();
    },

    changeCustomerNo() {
      let data = {
        systemName: this.systemName,
        customerName: this.formItem.customerNoTest,
        loginName: ""
      }
      this.$refs["createModel"].handCustomerInfoModel1(data);
    },
    changeLoginName() {
      let data = {
        systemName: this.systemName,
        customerName: "",
        loginName: this.formItem.loginNameTest,
      }
      this.$refs["createModel"].handCustomerInfoModel1(data);
    },

    selectSystem(systemName){
        this.systemName = systemName
        switch(systemName){
            case "FLY": this.requestUrl = 'http://jiyun.flycloudstorage.com:8620';
            break;
            case "HUAWEI": this.requestUrl = 'http://jiyun.huawei138.cn:8630';
            break;
            case "TAOMAO": this.requestUrl = 'http://gzdw.gdjiyun.com:9120';
            break;
            case "HUASU": this.requestUrl = 'http://huasu.huawei138.cn:9610';
            break;
            case "HUAYOU": this.requestUrl = 'http://huayou.flycloudstorage.com:9630'; 
            break;
        }
    },

  }
};
</script>

<style lang="scss">
.base-info-class {
  overflow: auto;
}
.system-style1-class {
  text-align: center;
  padding-top: 27px;
  margin: 15px 0 15px 0;
  color: rgb(247, 247, 247);
  font-size: 26px;
  font-weight: bold;
  // width: 60%;
  height: 80px;
  background-color: rgb(55, 148, 219);
  // border: rgb(70, 48, 214) solid 3px;
  border-radius: 10px;
  box-shadow: 5px 5px 5px rgb(119, 123, 123);
}

.system-style2-class {
  text-align: center;
  padding-top: 23px;
  margin: 15px 0 15px 0;
  font-size: 26px;
  font-weight: bold;
  color: rgb(250, 64, 101);
  // width: 60%;
  height: 80px;
  background-color: rgba(247, 247, 6, 0.4);
  border: rgb(250, 64, 101) solid 4px;
  border-radius: 10px;
}
</style>