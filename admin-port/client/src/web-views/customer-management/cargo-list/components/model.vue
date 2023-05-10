/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="cargo-list-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   class="dialog-box"
                   width="600px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="125px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="快递单号/唛头："
                                      prop="deliveryOrderNo">
                            <el-input v-model="formItem.deliveryOrderNo"
                                      placeholder="请输入快递单号/唛头"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="快递公司：">
                            <el-select v-model="formItem.deliveryNo"
                                       clearable
                                       filterable
                                       placeholder="请选择快递公司"
                                       style="width: 400px;">
                                <el-option v-for="(item,index) in deliveryCompanyData"
                                           :key="index"
                                           :value="item.deliveryNo"
                                           :label="item.deliveryName" />
                            </el-select>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="货物名称："
                                      prop="goodsName">
                            <el-input v-model="formItem.goodsName"
                                      placeholder="请输入货物名称"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="客户："
                                      prop="customerNo">
                            <el-select v-model="formItem.customerNo"
                                       placeholder="请输入客户名称"
                                       :disabled="true"
                                       filterable
                                       style="width: 313px;">
                                <el-option v-for="(item,index) in customerData"
                                           :key="index"
                                           :value="item.customerNo"
                                           :label="item.customerName" />
                            </el-select>
                            <el-button :loading="saving"
                                       type="info"
                                       @click="handleSelectCustomer">选择客户</el-button>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="会员id："
                                      prop="loginName">
                            <el-input v-model="formItem.loginName"
                                      placeholder="会员id"
                                      :disabled="true"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="包装类型："
                                      prop="packageType">
                            <el-select v-model="formItem.packageType"
                                       placeholder="请选择包装类型"
                                       style="width: 400px;">
                                <el-option value="蛇皮袋"
                                           label="蛇皮袋" />
                                <el-option value="纸箱"
                                           label="纸箱" />
                                <!-- <el-option value="木箱"
                                           label="木箱" />
                                <el-option value="文件袋"
                                           label="文件袋" /> -->
                                <el-option value="袋子"
                                           label="袋子" />
                                <!-- <el-option value="航空箱"
                                           label="航空箱" /> -->
                                <el-option value="其他"
                                           label="其他" />

                            </el-select>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="货物件数："
                                      prop="packageNum">
                            <el-input v-model="formItem.packageNum"
                                      type="number"
                                      :min="0"
                                      placeholder="请输入货物数量"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <!-- <el-col :span="24">
                        <el-col :span="11">
                            <el-form-item label="重量：">
                                <el-input v-model="formItem.kg"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入重量"
                                          style="width: 170px;"><template slot="append">KG</template></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="体积："
                                          label-width="95px">
                                <el-input v-model="formItem.vol"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入体积"
                                          style="width: 170px;"><template slot="append">m³</template></el-input>
                            </el-form-item>
                        </el-col>
                    </el-col> -->
                    <el-col :span="24">
                        <el-col :span="11">
                            <el-form-item label="重量："
                                          prop="kg">
                                <el-input v-model="formItem.kg"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入重量"
                                          style="width: 170px;"><template slot="append">KG</template></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="11">
                            <el-form-item label="高度："
                                          label-width="95px"
                                          prop="height">
                                <el-input v-model="formItem.height"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入高度"
                                          style="width: 170px;"><template slot="append">CM</template></el-input>
                            </el-form-item>
                        </el-col>
                    </el-col>

                    <el-col :span="24">
                        <el-col :span="11">
                            <el-form-item label="长度："
                                          prop="length">
                                <el-input v-model="formItem.length"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入长度"
                                          style="width: 170px;"><template slot="append">CM</template></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="宽度："
                                          label-width="95px"
                                          prop="width">
                                <el-input v-model="formItem.width"
                                          type="number"
                                          :min="0"
                                          placeholder="请输入宽度"
                                          style="width: 170px;"><template slot="append">CM</template></el-input>
                            </el-form-item>
                        </el-col>
                    </el-col>

                    <el-col :span="24">
                        <el-col :span="5" style="width: 365px">
                            <el-form-item label="国家/地区：" prop="commercialAreaId" v-if="cookiesAreaId == 1">
                                <el-select v-model="formItem.commercialAreaName"
                                placeholder="请选择国家/地区"
                                @change="changeCommercialArea(formItem.commercialAreaName)"
                                style="width: 420px">
                                <el-option
                                    v-for="item in commercialAreaData"
                                    :key="item.commercialAreaName"
                                    :value="item.commercialAreaName"
                                    :label="item.commercialAreaName"
                                />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-col>

                    <!-- 入库 -->
                    <el-col :span="24"
                            v-if="marking == 'warehousing'">
                        <el-col :span="11">
                            <el-form-item label="区："
                                          prop="storageArea">
                                <el-select v-model="formItem.storageArea"
                                           clearable
                                           placeholder="请选择区号"
                                           @change="changeSelect"
                                           style="width: 170px;">
                                    <el-option v-for="(item,index) in storageAreaData"
                                               :key="index"
                                               :value="item.storageArea"
                                               :label="item.storageArea" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="排："
                                          prop="storageRow"
                                          label-width="95px">
                                <el-select v-model="formItem.storageRow"
                                           clearable
                                           placeholder="请选择排号"
                                           style="width: 170px;">
                                           <el-option v-for="item in storageRowData"
                                                :key="item.id"
                                                :value="item.shelvesRow"
                                                :label="item.shelvesRow"
                                            />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-col>
                    <!-- 预录入 -->
                    <el-col :span="24" v-if="marking == 'inAdvance'">
                        <el-col :span="5" style="width: 365px">
                            <el-form-item label="区：" prop="storageArea" >
                                <el-select filterable
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
                            <el-form-item label="排：" label-width="75px" prop="storageRow">
                                <el-select
                                v-model="formItem.storageRow"
                                placeholder="请选择排号"
                                style="width: 180px"
                                >
                                <el-option
                                    v-for="item in storageRowData"
                                    :key="item.id"
                                    :value="item.shelvesRow"
                                    :label="item.shelvesRow"
                                />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="操作人名字：">
                            <el-input v-model="formItem.operatorName"
                                      :disabled="true"
                                      placeholder="请输入操作人名字"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="备注信息：">
                            <el-input type="textarea"
                                      v-model="formItem.message"
                                      :rows="5"
                                      placeholder="请输入备注信息"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button>
            </span>
        </el-dialog>

        <!-- 弹窗信息 -->
        <create-model ref="createModel"
                      @listTap="handleCustomerData" />
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import createModel from './create-model'
import { updateCargoList, getStorageList } from '@/api/customer-management/cargo-list'
import Cookies from 'js-cookie'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    components: {
        createModel
    },
    props: {
        selectData: {
            type: Object,
            default () {
                return {}
            }
        }
    },
    watch: {
        selectData () {
            this.customerData = this.selectData.customer
            this.deliveryCompanyData = this.selectData.deliveryCompany
            this.storageAreaData = this.selectData.storage
            this.commercialAreaData = this.selectData.commercialAreaData
        }
    },
    data () {
        /**
         * 自定义验证区号，排号
         */
        const validateLength = (rule, value, callback) => {
            if (value === '') {
                callback()
            } else {
                if (value > 0) {
                    callback()
                } else {
                    callback(new Error('请输入长度大于0的数字'))
                }
            }
        }
        const validateWidth = (rule, value, callback) => {
            if (value === '') {
                callback()
            } else {
                if (value > 0) {
                    callback()
                } else {
                    callback(new Error('请输入宽度大于0的数字'))
                }
            }
        }
        const validateHeight = (rule, value, callback) => {
            if (value === '') {
                callback()
            } else {
                if (value > 0) {
                    callback()
                } else {
                    callback(new Error('请输入高度大于0的数字'))
                }
            }
        }
        const validateKG = (rule, value, callback) => {
            if (value === '') {
                callback()
            } else {
                if (value > 0) {
                    callback()
                } else {
                    callback(new Error('请输入重量大于0的数字'))
                }
            }
        }
        return {
            marking: '',  //标记
            customerData: [],  // 客户信息
            deliveryCompanyData: [],  // 快递公司
            storageAreaData: [],  // 区号
            storageRowData: [],  // 排号
            saving: false,
            noticeType: [],  // 通知公告
            commercialAreaData: [],
            cookiesAreaId : Cookies.get("commercialAreaId"),
            formItem: {
                goodsNo: '',
                goodsName: '',
                storageArea: '',
                storageRow: '',
                customerNo: '',
                deliveryOrderNo: '',
                deliveryNo: '',
                packageNum: '',
                loginName: '',
                kg: '',
                vol: '',
                length: '',
                width: '',
                height: '',
                operatorCode: Cookies.get('usercode'),
                packageType: '',
                message: '',
                commercialAreaId: '',
                commercialAreaName: "",
                operatorName: Cookies.get('userName')
            },
            formItemRules: {
                deliveryOrderNo: [
                    { required: true, message: '快递单号/唛头不能为空', trigger: 'blur' },
                ],
                goodsName: [
                    { required: true, message: '货物名称不能为空', trigger: 'blur' },
                ],
                customerNo: [
                    { required: true, message: '客户不能为空', trigger: 'change' },
                ],
                packageType: [
                    { required: true, message: '请选择包装类型', trigger: 'change' },
                ],
                packageNum: [
                    { required: true, message: '请输入货物件数', trigger: 'blur' },
                ],
                // storageArea: [
                //     { required: true, message: '请选择区号', trigger: 'change' },
                // ],
                // storageRow: [
                //     { required: true, message: '请选择排号', trigger: 'change' },
                // ],
                commercialAreaId: [
                    { required: true, message: '请选择国家/地区', trigger: 'change' },
                ],
                length: [
                    { validator: validateLength, trigger: 'blur' },
                ],
                width: [
                    { validator: validateWidth, trigger: 'blur' },
                ],
                height: [
                    { validator: validateHeight, trigger: 'blur' },
                ],
                kg: [
                    { validator: validateKG, trigger: 'blur' },
                ],
            },
            dialogEditInfo: false,
        };
    },
    methods: {
        handleSelectCustomer () {
            this.$refs['createModel'].handCustomerInfoModel()
        },

        handleCustomerData (data) {
            console.log(data)
            this.formItem.customerNo = data.customerNo
            this.formItem.loginName = data.loginName
        },

        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data, marking) {

            this.marking = marking
            let location = data.location ? data.location.split(',') : ''
            if (location) {
                if (location[0]) {
                    this.changeSelect(location[0])  // 赋值下拉选择数据
                } else {
                    this.storageRowData = []
                }
            } else {
                this.storageRowData = []
                this.formItem.storageArea = '';
                this.formItem.storageRow = '';
            }
            this.formItem.goodsNo = data.goodsNo;
            this.formItem.goodsName = data.goodsName;
            this.formItem.customerNo = data.customerNo;
            this.formItem.deliveryOrderNo = data.deliveryOrderNo;
            this.formItem.deliveryNo = data.deliveryNo;
            this.formItem.packageNum = data.packageNum;
            this.formItem.loginName = data.loginName;
            this.formItem.kg = data.kg ? data.kg : '';
            this.formItem.length = data.length ? data.length : '';
            this.formItem.width = data.width ? data.width : '';
            this.formItem.height = data.height ? data.height : '';
            // this.formItem.vol = data.vol;
            this.formItem.operatorCode = Cookies.get('usercode');
            this.formItem.packageType = data.packageType;
            this.formItem.message = data.message;
            this.formItem.operatorName = Cookies.get('userName');
            this.formItem.commercialAreaId = data.commercialAreaId
            this.formItem.commercialAreaName = data.commercialAreaName
            
            console.log(location);
            if (location) {
                if (location[1]) {
                    this.formItem.storageArea = location[0];
                    this.formItem.storageRow = location[1];
                }
            }

            this.getStorageList();

            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        getStorageList(){
            getStorageList().then((res) => {
                this.storageAreaData = res.content.storage;
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
                    updateCargoList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
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
        * 改变事件
        * @return {type} {description}
        */
        changeSelect (value) {
            this.formItem.storageRow = ''
            this.storageAreaData.forEach(ele => {
                if (ele.storageArea == value) {
                    this.storageRowData = ele.storageRow
                }
            });
        },
        changeCommercialArea(value){
            this.formItem.commercialAreaName = ""
            this.commercialAreaData.forEach((ele) => {
                if (ele.commercialAreaName == value) {
                this.formItem.commercialAreaId = ele.id;
                this.formItem.commercialAreaName = ele.commercialAreaName;
                }
            });
        },
        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.$refs[from].resetFields()
        },

    }
};
</script>

<style lang="scss">
.cargo-list-model-view {
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
    }
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
    .dialog-box {
        .el-dialog {
            margin-top: 5vh !important;
            margin-bottom: 30px;
        }
    }
    .el-form-item__label {
        padding: 0 6px 0 0;
    }
}
</style>