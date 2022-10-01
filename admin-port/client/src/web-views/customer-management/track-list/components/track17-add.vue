<template>
    <div>

        <el-form label-width="100px">
            <el-form-item label="订单号：">
                <el-button type="success" style="margin-bottom: 20px" @click="addOrderNumber()">添加订单号</el-button>
                <el-button type="warning" plain style="margin-bottom: 20px" @click="dialog = true">批量添加</el-button>
            </el-form-item>
            <el-form-item label="选择所属系统：">
                <el-select v-model="systemCode" @change="updateSystemCode()" clearable>
                    <el-option value="FLY" label="飞轮(FLY)"/>
                    <el-option value="HUAWEI" label="华威(HUAWEI)"/>
                    <el-option value="TAOMAO" label="淘猫(TAOMAO)"/>
                    <el-option value="HUAYOU" label="华邮(HUAYOU)"/>
                    <el-option value="HUASU" label="华速(HUASU)"/>
                </el-select>
                <span style="width: 40%; color: #7a7777;">如有其他系统的单号，可单独修改</span>
            </el-form-item>

            <el-form-item label="选择运输商：">
                <el-select v-model="carrier" filterable clearable placeholder="选择运输商" @change="updateCarrier()">
                    <el-option-group v-for="group in carrierData" :key="group.label" :label="group.label">
                        <el-option 
                            v-for="item in group.options" 
                            :key="item.key" 
                            :value="item.key" 
                            :label="item._name_zh_cn"/>
                    </el-option-group>
                </el-select>
                <span style="width: 40%; color: #7a7777;">如有其他运输商的单号，可单独修改</span>
            </el-form-item>
            
            <el-form-item>
                <el-table :data="tableData" border v-loading="loading">
                    <el-table-column type="index" align="center" label="序号" width="80px"/>
                    <el-table-column prop="transitNumber" align="center" label="转单号">
                        <template slot-scope="scope">
                            <input class="input-class" v-model="scope.row.transitNumber" placeholder="请输入订单号"/>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="所属系统">
                        <template slot-scope="scope">
                            <input class="input-class" v-model="scope.row.systemCode"/>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="运输商">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.carrier" filterable clearable placeholder="选择运输商">
                                <el-option-group v-for="group in carrierData" :key="group.label" :label="group.label">
                                    <el-option 
                                        v-for="item in group.options" 
                                        :key="item.key" 
                                        :value="item.key" 
                                        :label="item._name_zh_cn"/>
                                </el-option-group>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="操作">
                        <template slot-scope="scope">
                            <el-button type="danger" plain size="mini" @click="removeOrderDate(scope.$index)">删 除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" :disabled="tableData.length < 1" @click="confirmRegister()">提交注册</el-button>
                <el-button type="info" :disabled="tableData.length < 1" @click="clearAll()">清空所有</el-button>
            </el-form-item>
        </el-form>
        
        <el-dialog title="批量添加单号"
            :visible.sync="dialog" width="60%"
            :close-on-click-modal="false">
            <el-form>
                <el-form-item>
                    <el-button type="success" @click="downloadTrack17RegisterTemplate()">下载模板</el-button>
                </el-form-item>
                <el-form-item>
                    <span style="color: red">*建议每次增加30票(单次注册60票)，防止页面崩溃*</span>
                    <el-input
                        v-model="textArea"
                        rows="12"
                        type="textarea"
                        placeholder="请在EXCLE文档中按照【订单号】【所属系统】的格式复制数据, 【订单号】不能为空"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="CopyExcelToTable">确定</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog :visible.sync="carrierDialog" width="70%" title="选择运输商">

        </el-dialog>
    </div>
</template>

<script>
import {registerTrack17OrderData} from '@/api/customer-management/track-list'
import carrierDataJS from './carrierData';
import config from "@/config";
export default {
    components: {
        carrierDataJS
    },
    data () {
        return {
            loading: false,
            systemCode: "",
            carrier: "",
            tableData:[
                {
                    transitNumber: "",
                    systemCode: "",
                    carrier:""
                }
            ],

            carrierDialog: false,
            carrierData:[],
            dialog: false,
            title: ["transitNumber", "systemCode"],
            textArea: "",
        };
    },
    mounted(){
        this.carrierData = carrierDataJS
        // console.log("carrierDataJS: ", carrierDataJS);
    },
    methods: {
        updateSystemCode(){
            console.log(this.systemCode);
            let sysc = this.systemCode
            this.loading = true;
            this.tableData.forEach((ele) =>{
                ele.systemCode = sysc;
            })
            setTimeout(() => {
                this.loading = false;
            }, 500)
        },
        updateCarrier(){
            let carrier = this.carrier
            this.loading = true;
            this.tableData.forEach((ele) =>{
                ele.carrier = carrier;
            })
            setTimeout(() => {
                this.loading = false;
            }, 500)
        },
        addOrderNumber(){
            let orderData = {
                transitNumber: "",
                systemCode: "",
                carrier:""
            }

            this.tableData.push(orderData);
        },

        removeOrderDate(index){
            this.tableData.splice(index, 1);
        },

        confirmRegister(){
            let data = JSON.stringify(this.tableData);
            registerTrack17OrderData(data).then(res => {
                if(res.code == 100){
                    let message = res.content
                    if(message == 'success'){
                        console.log("成功");
                        this.$message({
                            message: '所有订单号已注册成功',
                            type: 'success'
                        });
                    }else{
                        console.log("注册失败");

                        this.$message({
                            message: '部分订单号注册失败',
                            type: 'error'
                        });

                        this.$notify.error({
                            title: '提示',
                            message: "以下订单未注册成功：" + message + "; 已将注册失败的订单复制到剪切板中",
                            duration: 0
                        });

                        const input = document.createElement("input");
                        input.readOnly = 'readonly';
                        input.value = message;
                        document.body.appendChild(input);
                        input.select();
                        input.setSelectionRange(0, input.value.length);
                        document.execCommand('Copy');
                        document.body.removeChild(input);
                    }
                    
                }
            }).finally(() =>{
                this.tableData = [
                    {
                        transitNumber: "",
                        systemCode: "",
                        carrier:""
                    }
                ],
                this.systemCode = ""
            });
        },

        clearAll(){
            this.tableData = [
                {
                    transitNumber: "",
                    systemCode: "",
                    carrier:""
                }
            ],
            this.systemCode = ""
        },

        CopyExcelToTable() {
            let resultData = []; // 需要返回的列表数据
            let source = this.textArea;

            // 首先对源头进行解析
            let rows = source.split("\n"); // 拆成很多行
            for (let i = 0; i < rows.length; i++) {
                if (rows[i] != "") {
                // 如果某一行不是空，再按列拆分
                let columns = rows[i].split("\t"); // 已经按列划分
                let dataone = {}; // 声明一行数组
                for (let j = 0; j < columns.length; j++) {
                    // 读取demo里的第j对应的key值
                    // let keys = ; // key的名
                    dataone[this.title[j]] = columns[j];
                }
                resultData.push(dataone);
                }
            }

            let change = true;
            resultData.forEach((ele) => {
                if (!ele.transitNumber) {
                    this.$message.error("请输入订单号");                    
                    change = false;
                }
            });

            // 关闭显示框，清空text表的数据
            if (change) {
                // this.tableData = [];
                resultData.forEach((ele) => {
                    let data = {
                        transitNumber: ele.transitNumber,
                        systemCode: ele.systemCode ? ele.systemCode : "",
                        carrier:""
                    }
                    this.tableData.push(data);
                })
                // this.tableData = resultData
                this.dialog = false;
                this.textArea = "";
            }
        },

        /**
         * 下载模板
         */
        downloadTrack17RegisterTemplate() {
            const baseUrl =
                process.env.NODE_ENV === "development"
                ? config.baseUrl.dev
                : config.baseUrl.pro;
            window.location.href = baseUrl + "/api/common/downloadTrack17RegisterExcel";
        },
    }
}
</script>

<style lang="scss" scoped>
    .input-class{
        width: 90%;
        line-height: 40px;
        padding-left: 5px;
        border: #cacaca solid 0.5px;
        border-radius: 10px;
        outline: none;
        
    }
</style>