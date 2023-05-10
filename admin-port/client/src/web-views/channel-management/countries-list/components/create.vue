/**
 * 后台账号添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div style="margin-top: 50px">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
            <el-form-item label="国家名称：" prop="sddName">
                <el-input v-model="formItem.sddName" style="width: 300px" placeholder="请输入国家名称"></el-input>
            </el-form-item>
            
            <el-form-item label="使用货币：" prop="sddName">
                <el-select v-model="formItem.alternateField" placeholder="请选择国家使用的货币" >
                    <el-option v-for="item in currencyData" :key="item.sddCode" :value="item.id" :label="item.sddName"/>
                </el-select>
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
    </div>
</template>

<script>
import { addCountriesList } from '@/api/channel-management/countries-list'



export default {
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
            
            formItem: {
                sddName: '',
                sddRemark: '',
                alternateField: ""
            },
            formItemRules: {
                sddName: [
                    { required: true, message: '国家名称不能为空', trigger: 'blur' }
                ],
            },
        };
    },
    methods: {

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    addCountriesList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '新增成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.handleReset('form')
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
    }
};
</script>

<style>
</style>
