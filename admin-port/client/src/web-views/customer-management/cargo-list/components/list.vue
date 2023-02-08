/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="100px">
      <el-form-item label="货物名称">
        <el-input
          v-model="pageInfo.goodsName"
          placeholder="请输入货物名称"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="快递单号/唛头" label-width="105px">
        <el-input
          v-model="pageInfo.deliveryOrderNos"
          placeholder="请输入您的快递单号"
          style="width: 200px"
          type="textarea"
          class="filter-item"
        />
      </el-form-item>

      <el-form-item label="会员id" label-width="105px">
        <el-input
          v-model="pageInfo.loginName"
          placeholder="会员id"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="客户名称">
        <el-input v-model="pageInfo.customerName" placeholder="请输入客户名称" @keyup.enter.native="handleSearch(1)"/>
      </el-form-item>

      <el-form-item label="国家/地区">
        <el-select
          v-model="pageInfo.commercialAreaId"
          placeholder="--请选择--"
          clearable
          filterable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="item in commercialAreaData"
            :key="item.id"
            :value="item.id"
            :label="item.commercialAreaName"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="快递公司">
        <el-select
          v-model="pageInfo.deliveryNo"
          placeholder="--请选择--"
          clearable
          filterable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="(item, index) in deliveryCompanyData"
            :key="index"
            :value="item.deliveryNo"
            :label="item.deliveryName"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item label="货物重量">
        <el-input v-model="pageInfo.startKg" style="width: 110px"
                  type="number" 
                  placeholder="最小重量" @change="wieghtChange()"/>
         - <el-input v-model="pageInfo.endKg" style="width: 110px"
                    type="number" 
                    placeholder="最大重量" @change="wieghtChange()"
                    @keyup.enter.native="handleSearch(1)"/>
      </el-form-item>


      <el-form-item label="入库日期" label-width="110px">
        <el-date-picker
          v-model="pageInfo.inStorageTime"
          align="right"
          type="date"
          placeholder="选择日期"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd"
          style="width: 200px"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="最新上架日期" label-width="110px">
        <el-date-picker
          v-model="pageInfo.upStorageTime"
          align="right"
          type="date"
          placeholder="选择日期"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd"
          style="width: 200px"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="超时未出库订单" label-width="110px">
        <el-date-picker
          v-model="pageInfo.outStorageTime"
          align="right"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"
          style="width: 200px"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="货物状态">
        <el-select
          v-model="pageInfo.goodsType"
          placeholder="--请选择--"
          style="width: 200px"
          class="filter-item"
        >
          <el-option value="" label="全部" />
          <el-option :value="1" label="入库" />
          <el-option :value="6" label="待打包" />
          <el-option :value="7" label="已打包-未支付" />
          <el-option :value="8" label="待出库-已支付" />
          <el-option :value="2" label="出库" />
        </el-select>
      </el-form-item>


      <el-form-item label="备注信息" label-width="105px">
        <el-input
          v-model.trim="pageInfo.message"
          placeholder="备注信息"
          style="width: 200px"
          class="filter-item"
          type="textarea"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查 询</el-button
        >&nbsp;
        <el-button @click="handleResetForm('searchForm')">重 置</el-button>
        <el-button
          v-if="filterPermissions('删除货物信息')"
          type="success"
          @click="handleAkeyOutbound"
          >一 键 出 库</el-button
        >
        <el-button type="danger" @click="handleAKeyRemove"
          >批 量 删 除</el-button
        >
        <el-button type="warning" @click="handlePrintGoods"
          >打印发货清单</el-button
        >
      </el-form-item>
    </el-form>
    <audio
      src="http://www.wennever.cn/upload/pdf/out.mp3"
      id="eventAudio"
    ></audio>
    <!-- 表格 -->
    <el-table
      :data="tableData"
      border
      v-loading="loading"
      style="width: 100%"
      :row-style="tableRowStyle"
      :cell-style="columnStyle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        align="center"
        fixed="left"
        width="55"
      ></el-table-column>

      <el-table-column
        type="index"
        label="序号"
        width="50"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="deliveryOrderNo"
        label="快递单号/唛头"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="customerName"
        label="客户"
        min-width="160"
        align="center"
      >
        <!-- <template slot-scope="scope">
          <span>{{ scope.row.customerNo | filterCustomer(customerData) }}</span>
        </template> -->
      </el-table-column>

      <el-table-column
        prop="loginName"
        label="会员id"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="goodsName"
        label="货物名称"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="message"
        label="备注信息"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="inStorageTime"
        label="入库日期"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="upTime"
        label="上架日期"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="uper"
        label="上架员"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="仓库地址" min-width="160" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.location | filterLocation }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="commercialAreaName"
        label="国家/地区"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="deliveryName"
        label="快递公司"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="packageType"
        label="包装类型"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="packageNum"
        label="货物件数"
        min-width="100"
        align="center"
      ></el-table-column>

      <!-- <el-table-column prop="vol"
                             label="体积（m³）"
                             min-width="100"
                             align="center"></el-table-column> -->

      <el-table-column
        prop="length"
        label="长度（CM）"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="width"
        label="宽度（CM）"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="height"
        label="高度（CM）"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="kg"
        label="重量（KG）"
        min-width="100"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="operatorName"
        label="操作人名字"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="outStorageTime"
        label="出库日期"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        fixed="right"
        label="状态"
        min-width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-switch
            v-if="scope.row.goodsType == 1"
            v-model="scope.row.outStorageTime"
            active-color="#13ce66"
            inactive-color="#ddd"
            :disabled="!filterPermissions('入库')"
            active-text="
                               入库"
            @change="handleOutbound(scope.row.goodsNo,scope.row.deliveryOrderNo, scope.$index)"
            inactive-text=""
          >
          </el-switch>
          <el-switch
            v-if="scope.row.goodsType == 2"
            :value="scope.row.goodsType == 2"
            active-color="#13ce66"
            inactive-color="#ddd"
            active-text="出库"
            inactive-text=""
            disabled
          >
          </el-switch>
          <el-switch
            v-if="scope.row.goodsType == 3"
            :value="scope.row.goodsType == 3"
            active-color="#D8BFD8"
            inactive-color="#ddd"
            active-text="预录入"
            inactive-text=""
            disabled
          >
          </el-switch>
          <el-switch
            v-if="scope.row.goodsType == 6"
            :value="scope.row.goodsType == 6"
            active-color="#6495ED"
            inactive-color="#ddd"
            active-text="待打包"
            inactive-text=""
            disabled
          >
          </el-switch>
          <el-switch
            v-if="scope.row.goodsType == 7"
            :value="scope.row.goodsType == 7"
            active-color="#FFFF00"
            inactive-color="#ddd"
            active-text="已打包"
            inactive-text=""
            disabled
          >
          </el-switch>
          <el-switch
            v-if="scope.row.goodsType == 8"
            :value="scope.row.goodsType == 8"
            active-color="#FF8C00"
            inactive-color="#ddd"
            active-text="待出库"
            inactive-text=""
            disabled
          >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" fixed="right" width="350" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="filterPermissions('修改货物信息')"
            type="info"
            size="mini"
            plain
            @click="handleModal(scope.row)"
            :disabled="
              scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                ? false
                : true
            "
            >编辑</el-button
          >
          <el-button type="warning"
            size="mini"
            plain
            @click="printOrder(scope.row.deliveryOrderNo, scope.row.loginName)">打印面单</el-button>
          <el-button
            type="primary"
            size="mini"
            plain
            @click="handleGoodsPicture(scope.row.deliveryOrderNo)">查看图片</el-button>

          <el-button
            v-if="filterPermissions('删除货物信息')"
            type="danger"
            size="mini"
            plain
            @click="handleRemove(scope.row.goodsNo,scope.row.deliveryOrderNo)"
            :disabled="
              scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')
                ? false
                : true
            "
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 页码 -->
    <pagination
      v-show="pageInfo.total > 0"
      :total="pageInfo.total"
      :current.sync="pageInfo.page"
      :limit.sync="pageInfo.limit"
      @pagination="pagination"
    />

    <el-dialog :visible.sync="imageDialog" width="50%" title="货物图片">

      <div v-if="goodsImages" style="display: flex; flex-wrap: wrap;">
        <div v-for="item in goodsImages" :key="item.key" style="width: 33.3%">
          <el-image 
              style="width: 240px; height: 240px"
              :src="item.url"
              :preview-src-list="item.srcList">
          </el-image>
          </div>
      </div>

    </el-dialog>

    <!-- 弹窗信息 -->
    <model-view
      ref="modelView"
      :select-data="selectData"
      @updateList="handleSearch"
    />
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from "./model";
import {
  getSelectCargoList,
  getCargoList,
  removeCargoList,
  aKeyremoveCargoList,
  outboundCargoList,
  aKeyOutboundCargoList,
  getGoodsPicture,
  printDeliveryOrder
} from "@/api/customer-management/cargo-list";
import { printGoodsListing } from "@/api/package-management/unpackaged-task";
import Cookies from "js-cookie";

export default {
  components: {
    Pagination,
    ModelView,
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
      // this.customerData = this.selectData.customer
      this.deliveryCompanyData = this.selectData.deliveryCompany
      this.storageData = this.selectData.storage
      this.commercialAreaData = this.selectData.commercialAreaData;
    },
  },
  filters: {
    filterLocation(value) {
      if (value) {
        let location = value.split(",");
        if (location[0]) {
          return location[0] + "区" + location[1] + "排";
        } else {
          return "";
        }
      } else {
        return "";
      }
    },
  },
  data() {
    return {
      bool: true,
      customerData: [], // 客户信息
      deliveryCompanyData: [], // 快递公司
      storageData: [], // 区排

      commercialAreaData: [],

      loading: false,
      tableData: [],
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 30, // 每页条数
        goodsName: "",
        customerNo: "",
        deliveryNo: "",
        deliveryOrderNo: "",
        deliveryOrderNos: "",
        outStorageTime: "",
        goodsType: 1,
        inStorageTime: "",
        upStorageTime: "",
        loginName: "",
        message: "",
        customerName: "",
        startKg:"",
        endKg:"",

        commercialAreaId: '',
        commercialAreaName: ""
      }, // 页码传参数
      multipleSelection: [],

      imageDialog: false,
      goodsImages: [],

      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
    };
  },
  methods: {
    wieghtChange(){
      let startKg = parseFloat(this.pageInfo.startKg)
      let endKg = parseFloat(this.pageInfo.endKg)
      if(!endKg || startKg > endKg){
        this.pageInfo.endKg = this.pageInfo.startKg
      }
    },
    /**
     * 音频
     * @return {type} {description}
     */
    playAudio() {
      let buttonAudio = document.getElementById("eventAudio");
      // buttonAudio.setAttribute('src', audio)
      buttonAudio.play();
    },

    handleGoodsPicture(deliveryOrderNo){
      let _that = this
      axios.get('http://jiyun.flycloudstorage.com/api/images/getGoodsPicture?deliveryOrderNo='+deliveryOrderNo).then(function (res) {
        console.log(res.data);
        if(res.data.code == 100){
           _that.imageDialog = true;
           _that.goodsImages = res.data.content
         }else{
           _that.goodsImages = ''
           _that.$message({
                message: res.data.message,
                type: 'error'
            })
         }
      })

      // getGoodsPicture(deliveryOrderNo).then((res) => {
      //    if(res.code == 100){
      //      this.imageDialog = true;
      //      this.goodsImages = res.content
      //    }else{
      //      this.goodsImages = ''
      //    }
      // })
    },
    printOrder(deliveryOrderNo, loginName){
      let data = {
        deliveryOrderNo: deliveryOrderNo, 
        loginName: loginName
      }
      printDeliveryOrder(data).then((res) => {
        if(res.code == 100){
          window.open('http://' + res.content.url)
        }
      })
    },
    // 修改table tr行的背景色
    tableRowStyle({ row, column, rowIndex, columnIndex }) {
      // var style = ''
      // if ( row.upTime == null || row.upTime == '' ){
      //     console.log("进入了1")
      //     if ( columnIndex == 11 ){
      //         console.log("进入了1211")
      //         style = 'background-color: red'
      //     }
      // }
      // // if ( Date.parse(myDate) > Date.parse(row.createTime)){
      //     // style = 'background-color: red'
      // // }
      // return style;
    },
    columnStyle({ row, column, rowIndex, columnIndex }) {
      var style = "";
      if (row.upTime == null || row.upTime == "") {
        if (columnIndex == 10) {
          style = "background-color: #ffcc99";
        }
      }
      return style;
    },
    /**
     * 多选
     * @param  {type} val {description}
     * @return {type} {description}
     */
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

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
     * 获取岗位列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
        this.pageInfo.loginName = this.pageInfo.loginName.trim();
        this.pageInfo.customerName = this.pageInfo.customerName.trim();
        this.pageInfo.message = this.pageInfo.message.trim();
        this.pageInfo.goodsName = this.pageInfo.goodsName.trim();
      }
      this.pageInfo.deliveryOrderNo = this.pageInfo.deliveryOrderNos.replace(
        /^\s+|\s+$/gm,
        ""
      );
      if (this.pageInfo.deliveryOrderNos) {
        this.pageInfo.deliveryOrderNos = this.pageInfo.deliveryOrderNos.replace(
          /^\s+|\s+$/gm,
          ""
        );
        let reg = new RegExp("\n", "g"); //g代表全部
        let newMsg = this.pageInfo.deliveryOrderNo.replace(reg, "|");
        this.pageInfo.deliveryOrderNo = newMsg.trim();
      }
      this.loading = true;
      getCargoList(this.pageInfo)
        .then((res) => {
          this.tableData = res.content;
          this.pageInfo.total = parseInt(res.map.page.total);
          this.pageInfo.page = parseInt(res.map.page.number);
          this.pageInfo.limit = parseInt(res.map.page.pageSize);
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    getSelected(){
        // 下拉条件查询
        getSelectCargoList().then((res) => {
            // this.customerData = res.content.customer;
            this.deliveryCompanyData = res.content.deliveryCompany;
        });
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm(form) {
      this.pageInfo.goodsName = "";
      this.pageInfo.customerNo = "";
      this.pageInfo.deliveryNo = "";
      this.pageInfo.deliveryOrderNo = "";
      this.pageInfo.deliveryOrderNos = "";
      this.pageInfo.outStorageTime = "";
      this.pageInfo.goodsType = 1;
      this.pageInfo.loginName = "";
      this.pageInfo.message = "";
      this.pageInfo.inStorageTime = "";
      this.pageInfo.upStorageTime = "";
      this.pageInfo.customerName = "";
      this.pageInfo.startKg = "";
      this.pageInfo.endKg = "";
      this.$refs[form].resetFields();
    },

    /**
     * 出库
     * @return {type} {description}
     */
    handleOutbound(id,deliveryOrderNo, index) {
      let obj = {
        goodsNos: id,
        operatorName: Cookies.get("userName"),
        deliveryOrderNo:deliveryOrderNo
      };
      if (this.bool) {
        this.bool = false;
        this.$confirm("货物是否需要出库？确定后将不能修改!", "货物出库提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            outboundCargoList(obj)
              .then((res) => {
                if (res.code === 100) {
                  // this.pageInfo.page = 1
                  this.playAudio(); // 音频
                  this.$message({
                    message: "出库成功",
                    type: "success",
                    showClose: true,
                    duration: 1000 * 1.5 * 2,
                  });
                  this.handleSearch();
                }
                this.bool = true;
              })
              .finally(() => {
                this.tableData[index].outStorageTime = false;
                this.bool = true;
              });
          })
          .catch(() => {
            this.tableData[index].outStorageTime = false;
            this.bool = true;
          });
      }
    },

    /**
     * 一键出库
     * @return {type} {description}
     */
    handleAkeyOutbound() {
      if (this.multipleSelection.length > 0) {
        let bool = true;
        let obj = {
          goodsNos: [],
          operatorName: Cookies.get("userName"),
        };
        this.multipleSelection.forEach((ele) => {
          obj.goodsNos.push(ele.goodsNo);
          if (ele.goodsType == 2) bool = false;
        }); // 数据处理
        if (bool) {
          this.$confirm("确定对选中货物都出库吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            aKeyOutboundCargoList(obj).then((res) => {
              if (res.code === 100) {
                // this.pageInfo.page = 1
                this.playAudio(); // 音频
                this.$message({
                  message: "一键出库成功",
                  type: "success",
                  showClose: true,
                  duration: 1000 * 1.5 * 2,
                });
                this.handleSearch();
              }
            });
          });
        } else {
          this.$message({
            message: "只能勾选未出库货物！",
            type: "error",
            showClose: true,
            duration: 1000 * 1.5 * 2,
          });
        }
      } else {
        this.$message({
          message: "请至少勾选一个选项！",
          type: "warning",
          showClose: true,
          duration: 1000 * 1.5 * 2,
        });
      }
    },

    /**
     * 启动编辑弹窗
     * @return {type} {description}
     */
    handleModal(row) {
      this.$refs["modelView"].handEditInfoModel(row, "warehousing");
      // if (id) {
      //     this.saving = true
      //     getPostManageDetail(id).then(res => {
      //         if (res.code === 0) {
      //         }
      //     }).finally(() => {
      //         this.saving = false
      //     })
      // }
    },

    /**
     * 删除
     * @param  {string} id {description}
     * @return {type} {description}
     */
    handleRemove(id,deliveryOrderNo) {
      let obj = {
        goodsNo: id,
        usercode: Cookies.get("usercode"),
        deliveryOrderNo : deliveryOrderNo
      };
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        removeCargoList(obj).then((res) => {
          if (res.code === 100) {
            this.pageInfo.page = 1;
            this.$message({
              message: "删除成功",
              type: "success",
              showClose: true,
              duration: 1000 * 1.5 * 2,
            });
            this.handleSearch();
          }
        });
      });
    },

    /**
     * 批量删除
     * @return {type} {description}
     */
    handleAKeyRemove() {
      if (this.multipleSelection.length > 0) {
        let obj = {
          goodsNo: [],
          usercode: Cookies.get("usercode"),
        };
        this.multipleSelection.forEach((ele) => {
          obj.goodsNo.push(ele.goodsNo);
        }); // 数据处理
        this.$confirm("确定批量删除，选中货物吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          aKeyremoveCargoList(obj).then((res) => {
            if (res.code === 100) {
              // this.pageInfo.page = 1
              this.$message({
                message: "批量删除成功",
                type: "success",
                showClose: true,
                duration: 1000 * 1.5 * 2,
              });
              this.handleSearch();
            }
          });
        });
      } else {
        this.$message({
          message: "请至少勾选一个选项",
          type: "warning",
          showClose: true,
          duration: 1000 * 1.5 * 2,
        });
      }
    },

    /**
     * 货物清单打印
     * @return {type} {description}
     */
    handlePrintGoods() {
      if (this.multipleSelection.length > 0) {
        let obj = {
          goodsNo: [],
        };
        this.multipleSelection.forEach((ele) => {
          obj.goodsNo.push(ele.goodsNo);
        }); // 数据处理
        this.$confirm("确定打印选中货物清单吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          printGoodsListing(obj).then((res) => {
            if (res.code === 100) {
              window.open("http://" + res.content.url);
            }
          });
        });
      } else {
        this.$message({
          message: "请至少勾选一个选项",
          type: "warning",
          showClose: true,
          duration: 1000 * 1.5 * 2,
        });
      }
    },
  },
  mounted() {
    // this.handleSearch()
    
  },
};
</script>
<style  lang="scss">
  .c{
    display: flex;
  }
</style>

