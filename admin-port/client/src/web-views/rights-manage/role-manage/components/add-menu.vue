<template>
    <div>
        <el-dialog :visible.sync="menuDialog" width="40%" append-to-body>
            <el-form :model="formItem" ref="form" label-width="100px" :rules="formItemRules">
                <el-form-item label="菜单名称" prop="describe">
                    <el-input v-model="formItem.describe"/>
                </el-form-item>
                <el-form-item label="菜单类型" prop="level">
                    <el-select v-model="formItem.level">
                        <el-option v-for="item in levels" :key="item.level" :value="item.level" :label="item.name"/>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="formItem.level == 2" label="父级菜单" prop="parentId">
                    <el-select v-model="formItem.parentId">
                        <el-option v-for="item in firstPermission" :key="item.id" :value="item.id" :label="item.describe"/>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button @click="menuDialog = false">取 消</el-button>
                    <el-button type="primary" @click="submit('form')">提 交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import {selectFirstPermission, createPermission} from '@/api/rights-manage/role-manage'
export default {
    components:{

    },

    data(){
        return{
            menuDialog: false,
            formItem:{
                name:"",
                describe:"",
                level: 1,
                parentId:"",
            },
            firstPermission:[],
            levels: [
                {
                    level: 1,
                    name:"菜单"
                },
                {
                    level: 2,
                    name:"页面"
                },
            ],

             formItemRules: {
                describe: [
                    { required: true, message: "权限名不能为空", trigger: "blur" },
                ],
                level: [
                    { required: true, message: "请选择菜单类型", trigger: "blur" },
                ],
                parentId: [
                    { required: true, message: "请选择父级菜单", trigger: "blur" },
                ],
                
             }
        }
    },

    methods:{
        openMenuDialog(){
            this.menuDialog = true;
            this.resFormItem();
            this.getFirstPermission();
        },

        getFirstPermission(){
            selectFirstPermission().then((res) => {
                this.firstPermission = res.content
            });
        },

        submit(form){
            this.$refs[form].validate((valid) => {
                if(valid){
                    if(this.formItem.level == 1){
                        this.formItem.parentId = ""
                    }
                    
                    let data = this.formItem;

                    createPermission(data).then((res) => {
                        if(res.code == 100){
                            this.menuDialog = false;

                            this.$emit('reselect');
                        }else{
                            let message = res.content ? res.content : res.message
                            this.$message({
                                type:"error",
                                message: message
                            })
                        }
                    })
                }
            })
        },

        resFormItem(){
            this.formItem = {
                name:"",
                describe:"",
                level: 1,
                parentId:"",
            }
        }

    }
}
</script>