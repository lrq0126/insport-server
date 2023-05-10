/**
 * 【拼团订单明细】
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="pining-detail">
    <el-tabs
      v-model="activeName"
      style="margin-top: 15px"
      type="border-card"
      @tab-click="handTabClick"
    >
      <el-tab-pane label="拼邮订单详情" name="list">
        <el-divider content-position="left">
          <i class="el-icon-mobile-phone"></i>拼邮详情
        </el-divider>
        <div>
          <el-form ref="form" :model="formItem" label-width="130px">
            <el-row>
              <el-col :span="8">
                <el-col :span="22">
                  <el-form-item label="拼团名称：">
                    <el-input
                      v-model="formItem.orderName"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="国家：">
                    <el-input
                      v-model="formItem.country"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="22">

                  <el-form-item label="创建时间：">
                    <el-input
                      v-model="formItem.createTime"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                </el-col>
              </el-col>

              <el-col :span="8">
                <el-col :span="22">
                  <el-form-item label="订单号：">
                    <el-input
                      v-model="formItem.orderNumber"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="渠道名称：">
                    <el-input
                      v-model="formItem.routeName"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="运输类型：">
                    <el-select v-model="formItem.routeType" :disabled="true">
                      <el-option label="海运" value="2" />
                      <el-option label="空运" value="1" />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-col>
              <el-col :span="8">
                <el-col :span="22">
                  <el-form-item label="始发仓：">
                    <el-input
                      v-model="formItem.route.originatingPlace"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="目的地：">
                    <el-input
                      v-model="formItem.route.destination"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="运输时效：">
                    <el-input
                      v-model="formItem.route.transportationTime"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <el-divider content-position="left"
          ><i class="el-icon-mobile-phone"></i>成员订单详情</el-divider
        >
        <!-- <el-button type="primary" style="margin-bottom: 20px" @click="addCustomerPack()">添加成员订单</el-button> -->
        <!-- 子订单详情 -->
        <div id="pinOrderDetail" style="width: 100%">
          <el-table
            border
            :data="pinOrderData"
            v-loading="loading"
            style="width: 100%; margin-bottom: 20px"
          >

            <el-table-column
              label="序号"
              type="index"
              fixed="left"
              width="50"
              align="center"
            />
            
            <el-table-column
              label="流水号"
              min-width="160"
              prop="businessNumber"
              align="center"
            >
            </el-table-column>
            <!-- <el-table-column
              label="订单号"
              min-width="160"
              prop="orderNumber"
              align="center"
            >
            </el-table-column> -->

            <el-table-column
              label="会员ID"
              min-width="160"
              prop="loginName"
              align="center"
            >
            </el-table-column>

            <el-table-column
              label="客户名称"
              min-width="160"
              prop="customerName"
              align="center"
            >
            </el-table-column>

            <el-table-column
              label="货物总数"
              min-width="160"
              prop="goodsSum"
              align="center"
            >
              <template slot-scope="scope">
                  <el-button type="primary" plain @click="checkGoods(scope.row.id)">查看货物明细</el-button>
              </template>
            </el-table-column>

            <el-table-column
              label="包裹预估重量/KG"
              min-width="160"
              prop="actualWeight"
              align="center"
            >
            </el-table-column>

            <!-- <el-table-column
              label="操作"
              fixed="right"
              width="340"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="warning"
                  icon="el-icon-upload"
                  size="mini"
                  plain
                  @click="kickedMember(scope.row.customerId, scope.row.index)"
                  >移除</el-button
                >
              </template>
            </el-table-column> -->
          </el-table>
          
        </div>
      </el-tab-pane>
      <el-tab-pane label="返回上一层" name="back"></el-tab-pane>
    </el-tabs>
    <div style="margin: 40px 0">
      <el-form>
        <el-form-item label-width="42%">
          <!-- <el-button :loading="saving" type="success" @click="handleSubmit" :disabled="this.pinOrderData.length < 1">确认打包</el-button> -->
          <el-button @click="GoBack()">返回上一层</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog :visible.sync="goodsDialog" append-to-body width="60%">
      <el-table :data="goodsData" :border="true" v-loading="goodsLoading" style="width: 100%">
          <el-table-column align="center" type="index" label="序号" width="80px"></el-table-column>
          <el-table-column align="center" label="货物名称" width="180px" prop="goodsName"></el-table-column>
          <el-table-column align="center" label="货物单号" width="180px" prop="deliveryOrderNo"></el-table-column>
          <el-table-column align="center" label="下单时间" width="180px" prop="createTime"></el-table-column>
          <el-table-column align="center" label="重量" width="180px" prop="kg"></el-table-column>
          <el-table-column align="center" label="长" width="180px" prop="length"></el-table-column>
          <el-table-column align="center" label="宽" width="180px" prop="width"></el-table-column>
          <el-table-column align="center" label="高" width="180px" prop="height"></el-table-column>
          <el-table-column align="center" label="体积" width="180px" prop="vol"></el-table-column>
      </el-table>
    </el-dialog>

    <!-- <customer-pack-list-view ref="customerPackListView" @updatePinOrderData="updatePinOrderData"/> -->
  </div>
</template>

<script>

import { 
  getPinSpellMailInfo, 
  completeTeamComplete
} from "@/api/pin-management/pin-spell-mail";

import { getGoodsList } from '@/api/package-management/unpackaged-task';
// import CustomerPackListView from "./customer-pack-list";

export default {
  name: "piningDetail",
  components: {
  },
  data() {
    return {
      bool: true,
      TagView: "",
      activeName: "list",
      loading: false,
      saving: false,
      activeNames: ["1", "2"],
      formItem: {
        id: "",
        pinName: "",
        orderNumber: "",
        agentNumber: "",
        routeId: "",
        address: "",
        addressee: "",
        phoneNumber: "",
        publicAddress: "",
        isPublic: "",
        password: "",
        targetWeight: "",
        targetUnitPrice: "",
        targetMember: "",
        cutOffTime: "",
        message: "",
        routeType: "",
        images: [],
      },
      
      pinOrderData: [],
      dialogInfo: false,

      goodsDialog: false,
      goodsLoading: false,
      goodsData: []
    };
  },
  filters: {
    allChecked(length, width, height) {
      if (!length) return 0;
      if (!width) return 0;
      if (!height) return 0;
      return (Number(length) * Number(width) * Number(height)) / 1000000;
    },
  },
  methods: {
    handleChange(val) {
      console.log(val);
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

    addCustomerPack(){
      let customerPackIds;
      this.pinOrderData.forEach((ele) => {
        customerPackIds = customerPackIds ? customerPackIds+","+ele.id : ele.id
      })
      this.$refs['customerPackListView'].openCustomerPack(this.formItem.routeId, customerPackIds)
    },
    updatePinOrderData(data){
        data.forEach((ele) => {
          this.pinOrderData.push(ele);
        })
    },

    checkGoods(customerPackId){
        this.goodsDialog = true;
        this.goodsLoading = true;
        getGoodsList(customerPackId).then((res) => {
            this.goodsData = res.content
            setTimeout(()=>{
                this.goodsLoading = false
            }, 300)
        })
    },
    /**
     * 子订单开始打包
     */
    orderPackin(orderId) {
      this.$router.push({ name: "OrderPacking", query: { id: orderId } });
    },

    /**
     * 踢出这个成员
     *      pid : 主订单ID
     * memberId ：当前子订单行的客户Id（即customerId）
     */
    kickedMember(memberId, index) {
      this.pinOrderData.splice(index, 1);
      this.loading = true;
      this.$nextTick(() => {
        setTimeout(() => {
          this.loading = false;
        }, 400);
      });
    },

    

    handleSubmit() {

      let customerPackIds;
      this.pinOrderData.forEach((ele) => {
        customerPackIds = customerPackIds ? customerPackIds+","+ele.id : ele.id
      })
      let data = {
        id: this.formItem.id,
        customerPackIds: customerPackIds,
      }
      completeTeamComplete(data).then((res) => {
        if (res.code == 100) {
          this.$message({
            message: "拼团成功",
            type: "success",
            showClose: true,
            duration: 1000 * 1.5 * 3,
          });
          this.GoBack();
        } else {
          this.$message({
            message: res.message,
            type: "warning",
            showClose: true,
            duration: 1000 * 1.5 * 3,
          });
        }
      });
    },

    /**
     * 获取明细详情
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(pinId) {

      getPinSpellMailInfo(pinId).then((res) => {
          this.formItem = res.content;
          this.formItem.routeType = String(res.content.route.routeType);
          this.pinOrderData = res.content.customerPacks;
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 返回上一层
     * @return {type} {description}
     */
    GoBack() {
      this.$router.push({
        name: "ArrivalPin",
      });
      this.$store
        .dispatch("tagsView/piningView", this.TagView)
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
          name: "ArrivalPin",
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
      const title = "拼邮订单详情";
      const route = Object.assign({}, this.tempRoute, {
        title: `${title}`,
      });
      this.$store.dispatch("tagsView/updateVisitedView", route);
    },
  },
  mounted() {
    this.tempRoute = Object.assign({}, this.$route);
    this.setTagsViewTitle();
    this.$store.state.tagsView.visitedViews.forEach((ele) => {
      if (ele.title == "拼邮订单详情") {
        this.TagView = ele;
      }
    });
    // 初始化数据
    this.handleSearch(this.$route.query.id);
  },
};
</script>

<style lang="scss" scope>
.pining-detail {
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
.el-collapse-item {
  border: 1px solid #f5c572;
}
.el-collapse-item__header {
  background: #f5c572;
  padding: 0 10px;
  font-weight: 700;
  .el-collapse-item__arrow {
    font-size: 22px;
    color: #fff;
  }
}
.el-collapse-item__content {
  padding-bottom: 0 !important;
  .el-table--border {
    margin-top: 0px !important;
  }
}
</style>
