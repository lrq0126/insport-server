/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="course-review-model-view">
        <!--排课信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="排课信息"
                   width="900px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="24">
                        <el-col :span="12">
                            <el-form-item label="课程名称："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          placeholder="请输入课程名称"
                                          style="width: 300px;"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="上课课室："
                                          prop="name">
                                <el-cascader v-model="formItem.type"
                                             :options="options"
                                             clearable
                                             style="width: 300px;"></el-cascader>
                            </el-form-item>
                        </el-col>
                    </el-col>

                    <el-col :span="24">
                        <el-col :span="12">
                            <el-form-item label="限报人数："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入限报人数"
                                          style="width: 300px;"><template slot="append">人数</template></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="课程价格："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入课程价格"
                                          style="width: 300px;"><template slot="append">元</template></el-input>
                            </el-form-item>
                        </el-col>

                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="限报日期："
                                      prop="name">
                            <el-date-picker v-model="formItem.time"
                                            type="date"
                                            style="width: 300px;"
                                            placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>

                    </el-col>

                    <el-col :span="22">
                        <el-form-item class="addTime"
                                      label="上课时间：">
                            <el-button type="info"
                                       plain
                                       style="margin-bottom: 5px;"
                                       @click="addTopic">新增时间</el-button>
                            <el-table :data="formItem.specialItemJson"
                                      border
                                      fit
                                      max-height="300"
                                      highlight-current-row
                                      style="width: 100%;margin-top: 20px;">
                                <el-table-column label="周期"
                                                 width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="passScore"
                                                      style="margin-bottom: 0">
                                            <el-select v-model="scope.row.item1"
                                                       placeholder="请选择">
                                                <el-option label="周一"
                                                           value="1">
                                                </el-option>
                                                <el-option label="周二"
                                                           value="2">
                                                </el-option>
                                                <el-option label="周三"
                                                           value="3">
                                                </el-option>
                                                <el-option label="周四"
                                                           value="4">
                                                </el-option>
                                                <el-option label="周五"
                                                           value="5">
                                                </el-option>
                                                <el-option label="周六"
                                                           value="6">
                                                </el-option>
                                                <el-option label="周日"
                                                           value="7">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="上课时间"
                                                 min-width="250"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item prop="content"
                                                      style="margin-bottom: 0">
                                            <el-time-picker is-range
                                                            v-model="scope.row.item2"
                                                            range-separator="至"
                                                            start-placeholder="开始时间"
                                                            end-placeholder="结束时间"
                                                            placeholder="选择时间范围">
                                            </el-time-picker>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="操作"
                                                 width="100"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-button type="danger"
                                                   size="mini"
                                                   plain
                                                   @click="deleteTopic(scope.$index)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提交</el-button>
            </span>
        </el-dialog>

        <!-- 课程详情信息 -->
        <el-dialog :visible.sync="dialogDetailsInfo"
                   class="model-box"
                   title="课程详情信息"
                   width="800px">
            <el-form :model="formItem"
                     :rules="formItemRules"
                     ref="form"
                     label-width="100px"
                     class="demo-ruleForm">
                <el-row>
                    <!-- 课程详情 -->
                    <el-tabs type="border-card">
                        <!-- 课程基本信息 -->
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 课程基本信息</span>
                            <el-col :span="24">
                                <el-col :span="12">
                                    <el-form-item label="适合学员：">
                                        <span>1~2年级学员</span>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="课时数：">
                                        <span>5节课程，每一节课程85分钟</span>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="12">
                                    <el-form-item label="使用设备：">
                                        <span>电脑和IPAD均可</span>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="配套资料：">
                                        <span>《少儿编程-入门级》教材</span>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="24">
                                    <el-form-item label="课程特色："
                                                  prop="reason"
                                                  style="width: 725px">
                                        <span>课程内容将编程和硬件相结合既能学习编程知识，有能硬件实操，给孩子综合学习体验 </span>
                                        <!-- <el-input v-model="formItem.reason"
                                                  type="textarea"
                                                  :rows="4"
                                                  placeholder="请输入机构简介信息"
                                                  maxlength="1000"
                                                  show-word-limit
                                                  style="width: 780px;"></el-input> -->
                                    </el-form-item>
                                </el-col>
                            </el-col>
                        </el-tab-pane>
                        <!-- 课程大纲 -->
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 课程大纲</span>
                            <!-- 表格 -->
                            <el-table :data="syllabusData"
                                      border
                                      v-loading="loading"
                                      style="width: 100%">
                                <el-table-column type="index"
                                                 label="序号"
                                                 align="center"
                                                 width="55"></el-table-column>

                                <el-table-column label="周期"
                                                 align="center"
                                                 width="160"
                                                 prop="week">
                                </el-table-column>

                                <el-table-column label="课题"
                                                 align="center"
                                                 prop="title">
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 任课老师</span>
                            <!-- 表格 -->
                            <el-table :data="teacherData"
                                      border
                                      v-loading="loading"
                                      style="width: 100%">
                                <el-table-column type="index"
                                                 label="序号"
                                                 align="center"
                                                 width="55"></el-table-column>

                                <el-table-column label="姓名"
                                                 align="center"
                                                 width="140"
                                                 prop="name">
                                </el-table-column>

                                <el-table-column label="学历"
                                                 align="center"
                                                 width="140"
                                                 prop="education">
                                </el-table-column>

                                <el-table-column label="毕业学院"
                                                 align="center"
                                                 width="140"
                                                 prop="college">
                                </el-table-column>

                                <el-table-column label="从业经验"
                                                 align="center"
                                                 prop="experience">
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>

                    <!-- 课程评价 -->
                    <el-tabs type="border-card"
                             v-model="editableTabsValue">
                        <el-tab-pane :disabled="true">
                            <span slot="label"><i class="el-icon-date"></i> 好评度：96%</span>
                            <el-col :span="24"
                                    style="display: flex;">
                                <i class="el-icon-s-custom"
                                   style="font-size: 25px;position: relative;top: 4px;"></i>
                                <el-form-item label="18******6546："
                                              label-width="110px">
                                    <div>
                                        <el-rate v-model="value"
                                                 show-text
                                                 :texts="[ '差评', '差评', '中评', '中评', '好评']"></el-rate>
                                        <p style="margin: 0">嗯，很棒，开始还比较担心会不会白买，后来看了里面讲的很详细，受益良多。推荐购买</p>
                                        <span style="background: #eee;padding: 4px 5px;border-radius: 50px;color: #999;">2019-05-24 13:36</span>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-tab-pane>
                        <!-- 全部评价 -->
                        <el-tab-pane name="1">
                            <span slot="label"><i class="el-icon-date"></i> 全部评价（400+）</span>
                            <el-col :span="24"
                                    style="display: flex;">
                                <i class="el-icon-s-custom"
                                   style="font-size: 25px;position: relative;top: 4px;"></i>
                                <el-form-item label="18******6546："
                                              label-width="110px">
                                    <div>
                                        <el-rate v-model="value"
                                                 show-text
                                                 :texts="[ '差评', '差评', '中评', '中评', '好评']"></el-rate>
                                        <p style="margin: 0">嗯，很棒，开始还比较担心会不会白买，后来看了里面讲的很详细，受益良多。推荐购买</p>
                                        <span style="background: #eee;padding: 4px 5px;border-radius: 50px;color: #999;">2019-05-24 13:36</span>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24"
                                    style="display: flex;">
                                <i class="el-icon-s-custom"
                                   style="font-size: 25px;position: relative;top: 4px;"></i>
                                <el-form-item label="18******6546："
                                              label-width="110px">
                                    <div>
                                        <el-rate v-model="value"
                                                 show-text
                                                 :texts="[ '差评', '差评', '中评', '中评', '好评']"></el-rate>
                                        <p style="margin: 0">嗯，很棒，开始还比较担心会不会白买，后来看了里面讲的很详细，受益良多。推荐购买</p>
                                        <span style="background: #eee;padding: 4px 5px;border-radius: 50px;color: #999;">2019-05-24 13:36</span>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-tab-pane>
                        <!-- 好评 -->
                        <el-tab-pane name="2">
                            <span slot="label"><i class="el-icon-date"></i> 好评（400+）</span>
                            <el-col :span="24"
                                    style="display: flex;">
                                <i class="el-icon-s-custom"
                                   style="font-size: 25px;position: relative;top: 4px;"></i>
                                <el-form-item label="18******6546："
                                              label-width="110px">
                                    <div>
                                        <el-rate v-model="value"
                                                 show-text
                                                 :texts="[ '差评', '差评', '中评', '中评', '好评']"></el-rate>
                                        <p style="margin: 0">嗯，很棒，开始还比较担心会不会白买，后来看了里面讲的很详细，受益良多。推荐购买</p>
                                        <span style="background: #eee;padding: 4px 5px;border-radius: 50px;color: #999;">2019-05-24 13:36</span>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-tab-pane>
                        <!-- 中评 -->
                        <el-tab-pane name="3">
                            <span slot="label"><i class="el-icon-date"></i> 中评（400+）</span>
                            <el-col :span="24"
                                    style="display: flex;">
                                <i class="el-icon-s-custom"
                                   style="font-size: 25px;position: relative;top: 4px;"></i>
                                <el-form-item label="18******6546："
                                              label-width="110px">
                                    <div>
                                        <el-rate v-model="value"
                                                 show-text
                                                 :texts="[ '差评', '差评', '中评', '中评', '好评']"></el-rate>
                                        <p style="margin: 0">嗯，很棒，开始还比较担心会不会白买，后来看了里面讲的很详细，受益良多。推荐购买</p>
                                        <span style="background: #eee;padding: 4px 5px;border-radius: 50px;color: #999;">2019-05-24 13:36</span>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-tab-pane>
                        <!-- 差评 -->
                        <el-tab-pane name="4">
                            <span slot="label"><i class="el-icon-date"></i> 差评（400+）</span>
                            <el-col :span="24"
                                    style="display: flex;">
                                <i class="el-icon-s-custom"
                                   style="font-size: 25px;position: relative;top: 4px;"></i>
                                <el-form-item label="18******6546："
                                              label-width="110px">
                                    <div>
                                        <el-rate v-model="value"
                                                 show-text
                                                 :texts="[ '差评', '差评', '中评', '中评', '好评']"></el-rate>
                                        <p style="margin: 0">嗯，很棒，开始还比较担心会不会白买，后来看了里面讲的很详细，受益良多。推荐购买</p>
                                        <span style="background: #eee;padding: 4px 5px;border-radius: 50px;color: #999;">2019-05-24 13:36</span>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-tab-pane>
                    </el-tabs>

                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogDetailsInfo = false">关 闭</el-button>
            </span>
        </el-dialog>

        <!-- 机构信息弹窗 -->
        <el-dialog :visible.sync="dialogAuditInfo"
                   class="model-box"
                   title="机构信息"
                   width="1050px">
            <!-- 表单 -->
            <el-form :model="formItem"
                     :rules="formItemRules"
                     ref="form"
                     label-width="100px"
                     class="demo-ruleForm">
                <el-row>
                    <!-- 机构基本信息 -->
                    <el-tabs type="border-card">
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 机构基本信息</span>
                            <el-col :span="24">
                                <el-col :span="10">
                                    <el-form-item label="机构名称："
                                                  prop="reason">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构名称"
                                                  style="width: 300px;"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="14">
                                    <el-form-item label="机构地址：">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构地址"
                                                  type="textarea"
                                                  :rows="2"
                                                  style="width: 370px;"></el-input>
                                        <el-button type="primary"
                                                   plain
                                                   style="margin-left: 10px;"
                                                   @click="handleViewMap">查看地图</el-button>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="10">
                                    <el-form-item label="场地面积："
                                                  prop="reason">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入场地面积"
                                                  style="width: 300px;"><template slot="append">平方米</template></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="14">
                                    <el-form-item label="详细地址：">
                                        <el-input v-model="formItem.reason"
                                                  type="textarea"
                                                  :rows="2"
                                                  placeholder="请输入详细地址信息"
                                                  style="width: 370px;"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="10">
                                    <el-form-item label="人员规模：">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入人员规模"
                                                  style="width: 300px;"><template slot="append">人数</template></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="14">
                                    <el-form-item label="老师规模：">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入老师规模"
                                                  style="width: 370px;"><template slot="append">人数</template></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="24">
                                    <el-form-item label="机构简介："
                                                  prop="reason"
                                                  style="width: 880px">
                                        <el-input v-model="formItem.reason"
                                                  type="textarea"
                                                  :rows="4"
                                                  placeholder="请输入机构简介信息"
                                                  maxlength="1000"
                                                  show-word-limit
                                                  style="width: 780px;"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                        </el-tab-pane>
                    </el-tabs>
                    <!-- 资质证明 -->
                    <el-tabs type="border-card">
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 资质证明</span>
                            <el-col :span="24">
                                <el-col :span="14">
                                    <el-form-item label="营业执照："
                                                  prop="reason"
                                                  label-width="185px">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构名称"
                                                  style="width: 350px;"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="10">
                                    <el-form-item label=""
                                                  label-width="0">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构信息"
                                                  style="width: 310px;"></el-input>
                                        <el-button type="primary"
                                                   plain
                                                   style="margin-left: 10px;">查看照片</el-button>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="14">
                                    <el-form-item label="办学许可证："
                                                  prop="reason"
                                                  label-width="185px">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构名称"
                                                  style="width: 350px;"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="10">
                                    <el-form-item label=""
                                                  label-width="0">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构信息"
                                                  style="width: 310px;"></el-input>
                                        <el-button type="primary"
                                                   plain
                                                   style="margin-left: 10px;">查看照片</el-button>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="14">
                                    <el-form-item label="民办非企业单位登记证书："
                                                  prop="reason"
                                                  label-width="185px">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构名称"
                                                  style="width: 350px;"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="10">
                                    <el-form-item label=""
                                                  label-width="0">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入机构信息"
                                                  style="width: 310px;"></el-input>
                                        <el-button type="primary"
                                                   plain
                                                   style="margin-left: 10px;">查看照片</el-button>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                        </el-tab-pane>
                    </el-tabs>
                    <!-- 负责人信息 -->
                    <el-tabs type="border-card">
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 负责人信息</span>
                            <el-col :span="24">
                                <el-col :span="10">
                                    <el-form-item label="姓名："
                                                  prop="reason">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入姓名"
                                                  style="width: 300px;"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="14">
                                    <el-form-item label="证件类型：">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入证件类型"
                                                  style="width: 350px;"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="24">
                                <el-col :span="10">
                                    <el-form-item label="联系号码："
                                                  prop="reason">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入证件号码"
                                                  style="width: 300px;"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="14">
                                    <el-form-item label="证件号码：">
                                        <el-input v-model="formItem.reason"
                                                  placeholder="请输入证件号码"
                                                  style="width: 370px;"></el-input>
                                        <el-button type="primary"
                                                   plain
                                                   style="margin-left: 10px;">查看证件照片</el-button>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                        </el-tab-pane>
                    </el-tabs>

                    <!-- 采购要求 -->
                    <el-tabs type="border-card">
                        <el-tab-pane>
                            <span slot="label"><i class="el-icon-date"></i> 采购要求</span>
                            <!-- 表格 -->
                            <el-table :data="tableData"
                                      border
                                      style="width: 100%">
                                <el-table-column prop="identification"
                                                 label="要求项目"
                                                 min-width="160"
                                                 align="center">
                                </el-table-column>

                                <el-table-column prop="identification"
                                                 label="要求说明"
                                                 min-width="160"
                                                 align="center">
                                </el-table-column>

                                <el-table-column prop="identification"
                                                 label="申请说明"
                                                 min-width="160"
                                                 align="center">
                                </el-table-column>

                                <el-table-column label="上传附件"
                                                 width="120"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-button type="primary"
                                                   size="mini"
                                                   plain
                                                   @click="handleModal(scope.row.jobId)"
                                                   :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">下载附件</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>

                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogAuditInfo = false">关 闭</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updatePostManage } from '@/api/basic-info'
import { saveAs } from 'file-saver';
import Axios from 'axios'
import { getToken } from '@/utils/auth'
import config from '@/config'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            editableTabsValue: '1',
            value: null,
            postType: [],  // 发文类型
            loading: false,
            saving: false,
            options: [{
                value: '1',
                label: '一年级',
                children: [{
                    value: '1-1',
                    label: '1班',
                }, {
                    value: '1-2',
                    label: '2班',
                }]
            }, {
                value: '2',
                label: '二年级',
                children: [{
                    value: '2-1',
                    label: '1班',
                }, {
                    value: '2-2',
                    label: '2班',
                }]
            }],
            formItem: {
                name: '',
                typeId: '',
                fileUrl: [],
                file: '',
                schoolId: '',
                remark: '',
                gender: '',
                time: '',
                specialItemJson: [{
                    item1: '',
                    item2: '',
                    item3: '',
                    item4: 10,
                }]
            },
            formItemRules: {
                name: [
                    { required: true, message: '关键字不能为空', trigger: 'blur' },
                ],
                typeId: [
                    { required: true, message: '标题不能为空', trigger: 'blur' },
                ],
                remark: [
                    { required: true, message: "请输入内容!", trigger: "change" }
                ]
            },
            dialogEditInfo: false,
            dialogDetailsInfo: false,
            syllabusData: [{
                week: '第一周',
                title: '我用代码做贺卡：角色'
            }, {
                week: '第二周',
                title: '夺宝奇兵：游戏中国的运动'
            }, {
                week: '第三周',
                title: '丛林世界：数字动画原理'
            }, {
                week: '第四周',
                title: '壮志凌云：游戏控制'
            }, {
                week: '第五周',
                title: '给吸尘器编程：条件和判断'
            }],
            teacherData: [{
                name: '刘三姐',
                education: '硕士',
                college: 'MIT',
                experience: '具有丰富的编程和教学经验'
            }, {
                name: '张明亮',
                education: '硕士',
                college: '清华大学',
                experience: '具有丰富的编程和教学经验'
            }],
            dialogAuditInfo: false,
            serviceSchoolData: [{
                id: 0
            }],
            institutionsCourseData: [{
                id: 0
            }],
            tableData: [{
                id: 0
            }]
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.jobId = data.jobId;
            this.formItem.name = data.name;
            this.formItem.identification = data.identification;
            this.formItem.remarks = data.remarks;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        /**
         * 课程详情信息
         * @return {type} {description}
         */
        handleDetailsModel () {
            this.dialogDetailsInfo = true
        },
        //机构详情信息
        handAuditInfoModel () {
            this.dialogAuditInfo = true;
        },
        /**
         * 查看地图
         * @return {type} {description}
         */
        handleViewMap () {
            this.$router.push({
                name: 'ViewMap'
            })
        },

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    updatePostManage(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                        }
                    }).finally(() => {
                        this.dialogEditInfo = false
                        this.saving = false
                    })
                }
            })
        },

        /**
         * 新时间
         * @return {type} {description}
         */
        addTopic () {
            const obj = {
                item1: '',
                item2: '',
                item3: '',
                item4: 10,
            }
            this.formItem.specialItemJson.push(obj);
        },

        /**
         * 删除项目
         * @return {type} {description}
         */
        deleteTopic (index) {
            if (index > -1) {
                this.formItem.specialItemJson.splice(index, 1);
            }
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.remarks = ''
            this.$refs[from].resetFields()
        },

        /**
        * 下载
        * @return {type} {description}
        */
        download (url) {
            const baseUrl =
                process.env.NODE_ENV === 'development'
                    ? config.baseUrl.dev
                    : config.baseUrl.pro
            return Axios({
                method: 'get',
                url: baseUrl + '/base/fileTransfer/download',
                // responseType必须值,否则文件下载后乱码
                responseType: 'arraybuffer',
                headers: {
                    Authorization: 'Bearer ' + getToken()
                },
                params: {
                    path: url
                }
            }).then(function (response) {
                let body = response.data;
                let contentDisposition = response.headers["content-disposition"];
                let fileName = ''
                // 获取文件名
                for (let key of contentDisposition.split(';')) {
                    let name = key.split('=')[0].trim();
                    if (name === 'filename') {
                        fileName = key.split('=')[1].trim();
                        // 解决中文乱码
                        fileName = decodeURIComponent(escape(fileName));
                        // 去除首尾多余双引号
                        fileName = fileName.replace(/"/g, '');
                    }
                }
                if (body) {
                    let file = new Blob([body], {
                        type: "application/octet-stream"
                    });
                    saveAs(file, fileName)
                } else {
                    // 下载失败
                }
            }).catch(error => {
                console.log(error)
                // this.$log.log(error);
            });
        }
    }
};
</script>

<style lang="scss">
.course-review-model-view {
    .filter-box {
        margin-bottom: 20px;
    }
    // .el-table__body-wrapper {
    //   height: 440px;
    //   overflow: overlay;
    // }
    .table-number {
        float: left;
        font-size: 16px;
        font-weight: 700;
    }
    .el-textarea.is-disabled .el-textarea__inner,
    .el-input.is-disabled .el-input__inner {
        color: #666;
    }
    .model-box {
        .el-dialog {
            margin-bottom: 100px;
        }
    }
    .el-dialog__body {
        padding: 15px 20px 0 20px;
    }
    .el-tabs--border-card {
        margin-bottom: 20px;
    }
    .el-form-item__content {
        display: flex;
        align-items: center;
    }
    .addTime {
        .el-form-item__content {
            display: block !important;
        }
    }
    .el-tabs--border-card > .el-tabs__header .el-tabs__item.is-disabled {
        color: #f38c05;
        font-weight: 700;
    }
}
</style>