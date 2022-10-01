<template>
  <div>
    <el-dialog :visible.sync="areaDialog" title="货物区信息编辑" width="50%" close-on-click-modal>
        <el-form :model="formItem" ref="form" :rules="formItemRules" label-width="160px">
            <el-form-item label="货架区" prop="shelvesArea">
                <el-input v-model="formItem.shelvesArea" style="width: 300px"/>
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
                <el-input type="textarea" v-model="formItem.remarks" :rows="3" style="width: 500px"/>
            </el-form-item>
            <el-form-item>
                <el-button type="info" plain @click="areaDialog = false">取 消</el-button>
                <el-button type="primary" @click="comfirmArea('form')">提 交</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    
    <el-dialog :visible.sync="rowsDialog" title="货物排批量编辑" width="50%" close-on-click-modal>
        <el-form>
            <el-form-item>
                <el-button type="primary" plain @click="addShelvesRow()" style="margin-bottom: 20px">
                    添加货架排数
                </el-button>
                <el-table :data="rowData" border v-loading="loading">
                    <el-table-column align="center" label="序号" type="index" width="80px"/>
                    <el-table-column align="center" label="货架排号" prop="shelvesRow">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.shelvesRow"/>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="可放置货物数量" prop="shelvesLimit">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.shelvesLimit"/>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="备注" prop="remarks">
                        <template slot-scope="scope">
                            <el-input type="textarea" :rows="2" v-model="scope.row.remarks" placeholder="请填写备注"/>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            <el-form-item>
                <el-button type="info" plain @click="rowsDialog = false">取 消</el-button>
                <el-button type="primary" @click="comfirmRows()">提 交</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    
    <el-dialog :visible.sync="rowDialog" title="货物排信息编辑" width="50%" close-on-click-modal>
        <el-form :model="formItem" ref="form" :rules="formItemRules" label-width="160px">
            <el-form-item label="货架排号" prop="shelvesRow">
                <el-input v-model="formItem.shelvesRow" style="width: 300px"/>
            </el-form-item>
            <el-form-item label="可放置货物数量" prop="shelvesLimit">
                <el-input type="number" v-model="formItem.shelvesLimit" style="width: 300px"/>
            </el-form-item>
            <el-form-item label="备注">
                <el-input type="textarea" v-model="formItem.remarks" :rows="3" style="width: 500px"/>
            </el-form-item>
            <el-form-item>
                <el-button type="info" plain @click="rowDialog = false">取 消</el-button>
                <el-button type="primary" @click="comfirmRow('form')">提 交</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {getShelvesArea, 
        getShelvesRowList, 
        getShelvesRow,
        
        saveShelves,
        saveShelvesRows,
        saveShelvesRow
} from '@/api/warehouse-management/shelves-list';
export default {
    components:{

    },

    data(){
        return {
            loading: false,

            areaDialog: false,
            rowsDialog: false,
            rowDialog: false,

            areaId: "",

            formItem:{
                id: "",
                version: "",
                remarks: "",

                shelvesArea: "",
                shelvesType: "",
                sortingExport: "",

                shelvesRow:"",
                shelvesLimit: "",
            },

            rowData:[],

            formItemRules:{
                shelvesArea:[
                    { required: true, message: '货架号不能为空', trigger: 'blur' },
                ],

                shelvesType:[
                    { required: true, message: '请选择货架类型', trigger: 'blur' },
                ],
                
                shelvesRow:[
                    { required: true, message: '货架排号不能为空', trigger: 'blur' },
                ],
                
                shelvesLimit:[
                    { required: true, message: '货架可放置货物数量不能为空', trigger: 'blur' },
                ],
            }
        }
    },

    methods:{
        openEditArea(areaId){
            this.resetPageInfo();
            this.areaDialog = true;
            getShelvesArea({areaId}).then((res) =>{
                this.formItem = res.content
            })

        },

        openEditRows(areaId){
            this.resetPageInfo();
            this.rowsDialog = true;
            this.loading = true;
            this.areaId = areaId
            getShelvesRowList({areaId}).then((res) =>{
                this.rowData = res.content
            }).finally(() => {
                setTimeout(() => {
                   this.loading = false;
                }, 500)
            })
        },

        openEditRow(rowId){
            this.resetPageInfo();
            this.rowDialog = true;

            getShelvesRow({rowId}).then((res) =>{
                this.formItem = res.content
            })

        },

        comfirmArea(form){
            this.$refs[form].validate((valid) =>{
                if(valid){
                    saveShelves(this.formItem).then(res => {
                        if(res.code == 100){
                            this.$message({
                                message: "货架区域信息编辑成功",
                                type: "success"
                            })
                            this.resetPageInfo();
                            this.areaDialog = false;
                            this.$emit('uploadData');
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

        comfirmRows(){
            let shelvesRows = JSON.stringify(this.rowData)
            saveShelvesRows({shelvesRows}).then(res => {
                if(res.code == 100){
                    this.$message({
                        message: "货架排号信息编辑成功",
                        type: "success"
                    })
                    this.resetPageInfo();
                    this.rowsDialog = false;
                    this.$emit('uploadData');
                }else{
                    this.$message({
                        message: res.message,
                        type: "error"
                    })
                }
            })
        },

        comfirmRow(form){
            this.$refs[form].validate((valid) =>{
                if(valid){
                    saveShelvesRow(this.formItem).then(res => {
                        if(res.code == 100){
                            this.$message({
                                message: "货架排号信息编辑成功",
                                type: "success"
                            })
                            this.resetPageInfo();
                            this.rowDialog = false;
                            this.$emit('uploadData');
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


        addShelvesRow(){
          let shelvesRow = {
            parentId: this.areaId,
            shelvesRow: "",
            shelvesLimit: 20,
            remarks: ""
          }
          this.rowData.push(shelvesRow);  
        },

        deleteRow(index){
            this.rowData.splice(index, 1)
        },

        resetPageInfo(){
            this.areaId = ""
            this.formItem = {
                id: "",
                shelvesArea: "",
                shelvesRow:"",
                shelvesLimit: "",
                version: "",
                remarks: ""
            }

            this.rowData = []
        }
    }
}
</script>

<style>

</style>