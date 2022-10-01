<template>
  <div>
    <el-form label-width="160px" :model="formItem" ref="form" :rules="formItemRules">
        <el-form-item label="货架区号" prop="shelvesArea">
            <el-input v-model="formItem.shelvesArea" style="width: 300px" placeholder="请输入货架区域名称"/>
        </el-form-item>
        <el-form-item label="货架类型" prop="shelvesType">
            <el-select v-model="formItem.shelvesType" style="width: 300px" placeholder="请选择货架类型">
                <el-option label="常规货物区" value="general"/>
                <el-option label="大货区" value="big"/>
                <el-option label="异常件区" value="abnormal"/>
            </el-select>
        </el-form-item>
        <el-form-item label="分拣出口" prop="sortingExport">
            <el-select v-model="formItem.sortingExport" style="width: 300px" placeholder="请选择分拣出口">
                <el-option label="A" value="A"/>
                <el-option label="B" value="B"/>
                <el-option label="C" value="C"/>
                <el-option label="D" value="D"/>
                <el-option label="E" value="E"/>
                <el-option label="F" value="F"/>
                <el-option label="G" value="G"/>
                <el-option label="H" value="H"/>
                <el-option label="I" value="I"/>
            </el-select>
        </el-form-item>
        <el-form-item label="备注">
            <el-input type="textarea" :rows="3" v-model="formItem.remarks" style="width: 500px" placeholder="请输入备注"/>
        </el-form-item>
        <el-form-item label="货架排号">
            <el-button type="primary" style="margin: 5px 0 30px 0;" @click="addTableLine()">添 加 排</el-button>
            <el-table :data="shelvesRowData" border style="width: 80%">
                <el-table-column align="center" label="序号" width="80px" type="index"/>
                <el-table-column align="center" label="排号" >
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.shelvesRow"/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="可放置货物数量">
                    <template slot-scope="scope">
                        <el-input type="number" v-model="scope.row.shelvesLimit"/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="备注" width="300px">
                    <template slot-scope="scope">                
                        <el-input type="textarea" :rows="2" v-model="scope.row.remarks" placeholder="请输入备注"/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" @click="removeLine(scope.$index)">删 除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-form-item>
        <el-form-item>
            <el-button type="info" @click="resetPageInfo()">重 置</el-button>
            
            <el-button type="primary" @click="createShelvesArea('form')">提 交</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {saveShelves} from '@/api/warehouse-management/shelves-list'
export default {
    components: {

    },
    data(){
        return {
            formItem: {
                shelvesArea: "",
                shelvesType: "general",
                sortingExport:"",
                remarks: ""
            },

            shelvesRowData: [
                {
                    shelvesRow: "",
                    shelvesLimit: 20,
                    remarks: ""
                }
            ],

            formItemRules:{
                shelvesArea:[
                    { required: true, message: '货架号不能为空', trigger: 'blur' },
                ],
                shelvesType:[
                    { required: true, message: '请选择货架类型', trigger: 'blur' },
                ],
                sortingExport:[
                    { required: true, message: '请选择分拣出口', trigger: 'blur' },
                ]
            }
        }
    },

    methods:{

        createShelvesArea(form){
            this.$refs[form].validate((valid) =>{
                if(valid){
                    this.formItem.shelvesRows = JSON.stringify(this.shelvesRowData)
                    saveShelves(this.formItem).then(res => {
                        if(res.code == 100){
                            this.$message({
                                message: "货架添加成功",
                                type: "success"
                            })
                            this.resetPageInfo()
                        }else{
                            this.$message({
                                message: res.message,
                                type: "error"
                            })
                        }
                    })
                }
            })
        },

        resetPageInfo(){
            this.formItem = {
                shelvesArea: "",
                shelvesType: "general",
                remarks: ""
            }

            this.shelvesRowData = [
                {
                    shelvesRow: "",
                    shelvesLimit: 20,
                    remarks: ""
                }
            ]
        },

        addTableLine(){
          let shelvesRow = {
            shelvesRow: "",
            shelvesLimit: 20
          }
          this.shelvesRowData.push(shelvesRow);  
        },

        removeLine(index){
            this.shelvesRowData.splice(index, 1)
        }
    }
}
</script>

<style>

</style>