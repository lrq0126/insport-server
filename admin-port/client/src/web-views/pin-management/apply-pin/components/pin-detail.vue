/**
 * 【审批详情】
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="pin-detail">
    <el-tabs
      v-model="activeName"
      style="margin-top: 15px"
      type="border-card"
      @tab-click="handTabClick"
    >
      <el-tab-pane label="拼团申请明细" name="list">
        <div style="float: left; padding-top: 30px">
          <!-- :rules="formItemRules" -->
          <el-form ref="form" :model="formItem" label-width="150px">
            <el-row>
              <el-col :span="8">
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

            <el-row>
              <el-col :span="20">
                <el-form-item label="审核结果：">
                  <el-radio v-model="formItem.auditResult" label="2"
                    >通过</el-radio
                  >
                  <el-radio v-model="formItem.auditResult" label="1"
                    >未通过</el-radio
                  >
                  <el-radio v-model="formItem.auditResult" label="3"
                    >拒绝</el-radio
                  >
                </el-form-item>
              </el-col>

              <el-col :span="20">
                <el-form-item label="审核备注：">
                  <el-input
                    v-model="formItem.auditMessage"
                    placeholder="请输入审核备注信息"
                    type="textarea"
                    :rows="4"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-col :span="20" style="margin-top: 40px">
              <el-row>
                <el-col :span="20">
                  <el-form-item label-width="60%">
                    <el-button
                      :loading="saving"
                      type="primary"
                      @click="submit('formPack')"
                      >提 交</el-button
                    >
                    <el-button @click="GoBack">返回上一层</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="返回上一层" name="back"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {
  getPinDetail,
  operationTeamAudit,
} from "@/api/pin-management/pin-main";

export default {
  name: "pinDetail",
  components: {},
  data() {
    return {
      url: "https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg",
      bool: true,
      TagView: "",
      activeName: "list",
      loading: false,
      saving: false,
      images: [], // 驿站图片信息 在 res.content.images 里
      formItem: {
        id: "",
        pinName: "",
        orderNumber: "",
        agentNumber: "",
        routeId: "",
        address: "",
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
        auditResult: "",
        auditMessage: "",
      },
      dialogInfo: false,
    };
  },
  methods: {
    // 提交
    submit() {
      if (!this.formItem.auditResult) {
        this.$message({
          message: "请选择审核结果",
          type: "error",
          showClose: true,
          duration: 1000 * 1.5 * 2,
        });
        return;
      }
      operationTeamAudit(this.formItem).then((res) => {
        if (res.code == 100) {
          // console.log("审核完成");
          this.GoBack();
          // TODO 审核完成返回列表
        } else {
          this.$message({
            message: "网络延迟，请求失败，请退出登录，在重新操作。",
            type: "warning",
            showClose: true,
            duration: 1000 * 1.5 * 2,
          });
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
          name: "ApplyPin",
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
     * 返回上一层
     * @return {type} {description}
     */
    // TODO 星哥，返回上一层需要修改
    GoBack() {
      this.$router.push({
        name: "ApplyPin",
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
     * 修改Tabs-view内容
     * @return {type} {description}
     */
    setTagsViewTitle() {
      const title = "审批详情";
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
      if (ele.title == "审批详情") {
        this.TagView = ele;
      }
    });
    // 初始化数据
    this.handleSearch(this.$route.query.id);
  },
};
</script>

<style lang="scss" scope>
.pin-detail {
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
  /deep/ .el-divider {
    background: #e89a49;
    .el-divider__text is-left {
      color: #e89a49;
    }
  }
  .el-radio__input.is-disabled + span.el-radio__label {
    color: #606266 !important;
  }
  .el-radio__input.is-checked .el-radio__inner {
    border-color: #1890ff !important;
    background: #1890ff !important;
  }
  .el-radio__input.is-disabled .el-radio__inner::after {
    background-color: #fff;
  }
}
</style>
