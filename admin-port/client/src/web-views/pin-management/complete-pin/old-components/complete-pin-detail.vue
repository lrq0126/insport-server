/**
 * 【拼团订单明细】
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="complete-pin-detail">
    <el-tabs
      v-model="activeName"
      style="margin-top: 15px"
      type="border-card"
      @tab-click="handTabClick"
    >
      <el-tab-pane label="已成团订单明细" name="list">
        <el-divider content-position="left">
          <i class="el-icon-mobile-phone"></i>拼团详情
        </el-divider>
        <div>
          <el-form ref="form" :model="formItem" label-width="130px">
            <el-row>
              <el-col :span="8">
                <el-col :span="22">
                  <el-form-item label="拼团名称：">
                    <el-input
                      v-model="formItem.pinName"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="目的国家：">
                    <el-input
                      v-model="formItem.country"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="22">
                  <el-form-item label="团长名称：">
                    <el-input
                      v-model="formItem.leaderName"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="创建时间：">
                    <el-input
                      v-model="formItem.createTime"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="是否公开：">
                    <el-select v-model="formItem.isPublic" :disabled="true">
                      <el-option label="是" value="0" />
                      <el-option label="否" value="1" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="22">
                  <el-form-item label="目标重量：">
                    <el-input
                      v-model="formItem.targetWeight"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="目标单价：">
                    <el-input
                      v-model="formItem.targetUnitPrice"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="拼团人数上限：">
                    <el-input
                      v-model="formItem.targetMember"
                      :rows="4"
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
                  <el-form-item label="转单号：">
                    <el-input
                      v-model="formItem.agentNumber"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="团长会员ID：">
                    <el-input
                      v-model="formItem.loginName"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="截止日期：">
                    <el-input
                      v-model="formItem.cutOffTime"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="拼团密码：">
                    <el-input
                      v-model="formItem.password"
                      :rows="4"
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
                  <el-form-item label="收件人：">
                    <el-input
                      v-model="formItem.addressee"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="联系方式：">
                    <el-input
                      v-model="formItem.phoneNumber"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="邮政编码：">
                    <el-input
                      v-model="formItem.code"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="公开的地址：">
                    <el-input
                      v-model="formItem.publicAddress"
                      type="textarea"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="收货地址：">
                    <el-input
                      v-model="formItem.address"
                      type="textarea"
                      :rows="4"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-col>

              <el-col :span="24">
                <el-form-item label="驿站图片：">
                  <div
                    class="demo-image__preview"
                    v-if="formItem.images.length > 0"
                  >
                    <el-image
                      v-for="(res, index) in formItem.images"
                      :key="index"
                      style="width: 100px; height: 100px; marginright: 10px"
                      :src="res.picUrl"
                      :preview-src-list="[res.picUrl]"
                    >
                    </el-image>
                  </div>
                  <div class="demo-image__preview" v-else>
                    <el-image>
                      <div slot="error" class="image-slot">暂无图片</div>
                    </el-image>
                  </div>
                </el-form-item>
                <el-form-item label="团长备注：">
                  <el-input
                    v-model="formItem.message"
                    type="textarea"
                    :rows="4"
                    :readonly="true"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <el-divider content-position="left"
          ><i class="el-icon-mobile-phone"></i>拼团成员详情</el-divider
        >
        <!-- 子订单详情 -->
        <div id="pinOrderDetail" style="width: 100%">
          <el-table
            border
            :data="formItem.pinOrderData"
            v-loading="loading"
            style="width: 100%; margin-bottom: 20px"
          >
            <el-table-column
              label="展开详情"
              type="expand"
              align="center"
              width="120"
            >
              <template slot-scope="pinOrder">
                <el-table
                  border
                  v-loading="loading"
                  :data="pinOrder.row.goods"
                  align="center"
                  stripe
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
                    label="货物名称"
                    min-width="160"
                    prop="goodsName"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="快递单号"
                    min-width="160"
                    prop="deliveryOrderNo"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="仓库地址"
                    min-width="160"
                    prop="location"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="包装类型"
                    min-width="160"
                    prop="packageType"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="重量/KG"
                    min-width="160"
                    prop="kg"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="体积/㎡"
                    min-width="160"
                    prop="vol"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="参团日期"
                    min-width="160"
                    prop="goodsPartTime"
                    align="center"
                  >
                  </el-table-column>

                  <el-table-column
                    label="操作"
                    fixed="right"
                    width="180"
                    align="center"
                  >
                    <template slot-scope="goods">
                      <el-button
                        type="danger"
                        icon="el-icon-upload"
                        size="mini"
                        plain
                        @click="
                          kickedGoods(
                            pinOrder.row.customerId,
                            goods.row.id,
                            goods.$index,
                            pinOrder.$index
                          )
                        "
                        >踢出货物</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-table-column>

            <el-table-column
              label="序号"
              type="index"
              fixed="left"
              width="50"
              align="center"
            />
            <el-table-column
              label="订单号"
              min-width="160"
              prop="orderNumber"
              align="center"
            >
            </el-table-column>

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
              prop="goodsNumber"
              align="center"
            >
            </el-table-column>

            <el-table-column
              label="包裹预估重量/KG"
              min-width="160"
              prop="forecastWeight"
              align="center"
            >
            </el-table-column>

            <el-table-column
              label="参团时间"
              min-width="160"
              prop="partTime"
              align="center"
            >
            </el-table-column>
            <el-table-column label="状态" min-width="160" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.packType == 1" type="info"
                  >待打包</el-tag
                >
                <el-tag v-if="scope.row.packType == 2" type="success"
                  >已打包</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column
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
                  v-if="scope.row.packType == 1"
                  @click="printOrderGoods(scope.row.businessNumber)"
                  >打印子清单</el-button
                >

                <el-button
                  type="success"
                  icon="el-icon-upload"
                  size="mini"
                  plain
                  v-if="scope.row.packType == 1"
                  @click="orderPackin(scope.row.id)"
                  >打 包</el-button
                >

                <el-button
                  type="success"
                  icon="el-icon-upload"
                  size="mini"
                  plain
                  v-if="scope.row.packType == 2"
                  @click="orderPackin(scope.row.id)"
                  >编 辑</el-button
                >

                <el-button
                  type="warning"
                  icon="el-icon-upload"
                  size="mini"
                  plain
                  v-if="scope.row.packType == 2"
                  @click="printPackTa(scope.row.id)"
                  >打印子订单</el-button
                >

                <el-button
                  type="danger"
                  icon="el-icon-upload"
                  size="mini"
                  plain
                  @click="kickedMember(scope.row.customerId, scope.row.index)"
                  >踢出成员</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="返回上一层" name="back"></el-tab-pane>
    </el-tabs>
    <div style="margin: 40px 0">
      <el-form>
        <el-form-item label-width="42%">
          <!-- <el-button
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
              > -->
          <el-button :loading="saving" type="primary" @click="handleSubmit()"
            >完成打包</el-button
          >

          <el-button @click="GoBack">返回上一层</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  getPinDetail,
  kickedMember,
  kickedMemberGoods,
  pinConfirmPack,
  printPackTa,
  printOrderGoods
} from "@/api/pin-management/pin-main";

export default {
  name: "completePinDetail",
  components: {},
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
        pinOrderData: [],
        images: [],
      },
      dialogInfo: false,
    };
  },
  filters: {
    // allChecked (length, width, height) {
    //     if (!length) return 0;
    //     if (!width) return 0;
    //     if (!height) return 0;
    //     return (Number(length) * Number(width) * Number(height)) / 1000000;
    // },
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

    /**
     * 子订单开始打包
     */
    printPackTa(orderId) {
        console.log("orderId:" , orderId);
      printPackTa({ orderId }).then((res) => {
        if (res.code === 100) {
          // window.location = 'http://' + res.content.url
          window.open("http://" + res.content.url);
        }
      });
    },

    /**
     * 子订单开始打包
     */
    printOrderGoods(businessNumber) {
        console.log("businessNumber:" , businessNumber);
      printOrderGoods({ businessNumber }).then((res) => {
        if (res.code === 100) {
          // window.location = 'http://' + res.content.url
          window.open("http://" + res.content.url);
        }
      });
    },

    orderPackin(orderId) {
      this.$router.push({
        name: "OrderPacking",
        query: { id: orderId, routerId: this.$route.query.id },
      });
    },
    /**
     * 踢出这个成员
     *      pid : 主订单ID
     * memberId ：当前子订单行的客户Id（即customerId）
     */
    kickedMember(memberId, index) {
      if (index > -1) {
        this.$confirm("确定这个成员吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          let data = {
            pid: this.formItem.id,
            memberId: memberId,
          };
          kickedMember(data).then((res) => {
            if (res.code == 100) {
              this.formItem.pinOrderData.splice(index, 1);
              this.loading = true;
              this.$nextTick(() => {
                setTimeout(() => {
                  this.loading = false;
                }, 800);
              });
            } else {
              this.$message({
                message: res.content,
                type: "error",
                showClose: true,
                duration: 1000 * 1.5 * 2,
              });
            }
          });
        });
      }
    },

    /**
     * 踢出这个货物
     *      pid : 主订单ID
     * memberId ：当前子订单行的客户Id（即customerId）
     *  goodsId ：当前货物行的id
     */
    kickedGoods(memberId, goodsId, index, orderIndex) {
      if (index > -1) {
        this.$confirm("确定移除这个货物吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          let data = {
            pid: this.formItem.id,
            memberId: memberId,
            goodsId: goodsId,
          };
          kickedMemberGoods(data).then((res) => {
            if (res.code == 100) {
              let pinOrder = this.formItem.pinOrderData[orderIndex];
              this.formItem.pinOrderData[orderIndex].forecastWeight =
                pinOrder.forecastWeight - pinOrder.goods[index].kg;
              pinOrder.goodsNumber = pinOrder.goodsNumber - 1;
              pinOrder.goods.splice(index, 1);
              if (pinOrder.goods <= 1) {
                this.formItem.pinOrderData.splice(orderIndex, 1);
              }

              this.loading = true;
              this.$nextTick(() => {
                setTimeout(() => {
                  this.loading = false;
                }, 800);
              });
            } else {
              this.$message({
                message: res.content,
                type: "error",
                showClose: true,
                duration: 1000 * 1.5 * 2,
              });
            }
          });
        });
      }
    },

    handleSubmit() {
      console.log("订单ID：", this.formItem.id);
      let data = {
        id: this.formItem.id,
      };
      pinConfirmPack(data).then((res) => {
        if (res.code == 100) {
          this.$message({
            message: "所有包裹已打包完成",
            type: "success",
            showClose: false,
            duration: 1000 * 1.5 * 2,
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
     * 返回上一层
     * @return {type} {description}
     */
    // TODO 星哥，返回上一层需要修改
    GoBack() {
      this.$router.push({
        name: "CompletePin",
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
     * 获取明细详情
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(pinId) {
      this.loading = true;
      let data = {
        pinId: pinId,
      };
      getPinDetail(data)
        .then((res) => {
          this.formItem = res.content;
          this.formItem.pinOrderData = res.content.pingMainOrder;
          this.formItem.routeType = String(res.content.routeType);
          this.formItem.isPublic = String(res.content.isPublic);
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
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
          name: "CompletePin",
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
      const title = "已成团订单详情";
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
      if (ele.title == "已成团订单详情") {
        this.TagView = ele;
      }
    });
    // 初始化数据
    this.handleSearch(this.$route.query.id);
  },
};
</script>

<style lang="scss" scope>
.complete-pin-detail {
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
