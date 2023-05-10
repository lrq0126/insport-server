/**
 * 后台账号添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div style="margin-top: 50px">
        <el-dialog :visible.sync="modelDialog">
            <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
                <el-form-item label="国家名称：" prop="sddName">
                    <el-input v-model="formItem.sddName" style="width: 300px" placeholder="请输入国家名称"></el-input>
                </el-form-item>
                
                <el-form-item label="使用货币：" prop="currencyId">
                    <el-select v-model="formItem.currencyId" placeholder="请选择国家使用的货币" style="width: 200px">
                        <el-option v-for="item in currencyData" :key="item.id" :value="item.id" :label="item.sddName" />
                    </el-select>
                    <el-button type="primary" size="mini" style="margin-leftp: 20px; width: 100px" @click="openCurrencyInfo()">添加货币类型</el-button>
                </el-form-item>

                <el-form-item label="备注信息：">
                    <el-input type="textarea" v-model="formItem.sddRemark" :rows="4" style="width: 300px" placeholder="请输入备注信息"></el-input>
                </el-form-item>

                    <el-form-item label-width="260px">
                        <el-button :loading="saving"
                                    type="primary"
                                    @click="handleSubmit('form')">保 存</el-button>
                        <el-button @click="handleReset('form')">重 置</el-button>
                    </el-form-item>
            </el-form>

            <currency-model-view ref="currencyModelView" @uploadCurrencyData="getCurrency"/>

        </el-dialog>
        
    </div>
</template>

<script>
import { updateCountry, getCountryInfo } from '@/api/channel-management/countries-list'
import CurrencyModelView from './currency-model';

import { getCurrency } from '@/api/receipt-management/dict-manage'

export default {
  components: { 
    CurrencyModelView 
  },
    name: "Create",
    props:{
        currencyData: {
            type: Array,
            default() {
                return [];
            },
        }
    },
    data () {
        return {
            saving: false,
            modelDialog: false,
            formItem: {
                id: "",
                sddName: '',
                sddRemark: '',
                currencyId: ""
            },
            formItemRules: {
                sddName: [
                    { required: true, message: '国家名称不能为空', trigger: 'blur' }
                ],
                currencyId: [
                    { required: true, message: '请选择国家使用的货币', trigger: 'blur' }
                ],
            },
        };
    },
    methods: {
        handEditInfoModel(id){
            this.modelDialog = true;
            getCountryInfo(id).then((res) => {
                this.formItem = res.content;
            })
        },

        openCurrencyInfo(){
            this.$refs['currencyModelView'].openDictInfo();
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
                    updateCountry(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '编辑完成',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.$emit('updateList');
                            this.modelDialog = false;
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.sddRemark = ''
            this.$refs[from].resetFields()
        },

        getCurrency(){
            getCurrency().then((res) => {
                this.currencyData = res.content
            })
        },
    }
};
</script>

<style>
</style>
