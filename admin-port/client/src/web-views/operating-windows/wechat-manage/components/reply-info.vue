<template>
    <div>
        <el-dialog :visible.sync="replyInfoDialog" title="自动回复规则" width="60%" :close-on-click-modal="false">
            <el-form :model="formItem" ref="form" :rules="formItemRules" label-width="100px">
                <el-form-item label="规则名称" prop="replyRule">
                    <el-input v-model="formItem.replyRule" style="width: 300px" 
                    :readonly="formItem.replyRule == '关注后自动回复' || 
                        formItem.replyRule == '扫码关注后自动回复' || 
                        formItem.replyRule == '其他' "/>
                </el-form-item>
                <el-form-item label="回复类型" prop="replyType">
                    <el-select v-model="formItem.replyType">
                        <el-option v-for="item in replyTypes" :key="item.value" :label="item.name" :value="item.value"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="关键词" prop="keyWord">
                    <el-input v-model="formItem.keyWord" style="width: 300px" 
                    :readonly="formItem.keyWord == '关注后自动回复' || 
                        formItem.keyWord == '扫码关注后自动回复' || 
                        formItem.keyWord == '其他' "/>
                </el-form-item>
                <el-form-item v-if="formItem.replyType == 'text'" label="回复内容" prop="replyContent">
                    <el-input v-model="formItem.replyContent" type="textarea" :rows="4" style="width: 80%"/>
                </el-form-item>
                <el-form-item v-else label="合法mediaID" prop="mediaId">
                    <el-input v-model="formItem.mediaId" style="width: 300px"/>
                    <el-button @click="openWeChatMaterial()">选择微信素材</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="info" plain @click="replyInfoDialog = false">取 消</el-button>
                    <el-button type="primary" plain @click="submit('form')">保 存</el-button>
                </el-form-item>
            </el-form>

            <wechat-material-view ref="wechatMaterialView" @updateForm="updateForm"/>

        </el-dialog>
    </div>
</template>

<script>

import {saveReply, getReply} from "@/api/operating-windows/wechat-manage"
import WechatMaterialView from './wechat-material';
export default {
    components:{
        WechatMaterialView,
    },

    data(){
        return{
            replyInfoDialog: false,

            formItem:{
                id:"",
                replyRule:"",
                replyType:"",
                replyContent:"",
                keyWord:"",
                mediaId:""
            },
            
            formItemRules: {
                replyRule: [
                    { required: true, message: "自动回复规则名称不能为空", trigger: "blur" },
                ],
                replyType: [
                    { required: true, message: "自动回复类型不能为空", trigger: "blur" },
                ],
                replyContent: [
                    { required: true, message: "自动回复内容不能为空", trigger: "blur" },
                ],
                keyWord: [
                    { required: true, message: "关键词不能为空", trigger: "blur" },
                ],
                mediaId: [
                    { required: true, message: "mediaID不能为空", trigger: "blur" },
                ],
            },

            replyTypes: [
                {
                    value: "text",
                    name: "文本"
                },
                {
                    value: "image",
                    name: "图片 "
                },
                {
                    value: "voice",
                    name: "语音"
                },
                {
                    value: "video",
                    name: "视频"
                },
                // {
                //     value: "news",
                //     name: "图文消息"
                // },
            ]
        }
    },

    methods:{
        openReplyInfoDialog(data){
            this.replyInfoDialog = true;
            if(data.id){
                getReply(data.id).then((res) => {
                    this.formItem = res.content
                })
            }else{
                this.resFormItem();
            }
        },

        /**
         * 打开选择微信素材窗口
         */
        openWeChatMaterial(){
            let materialType = 'media_id';
            
            this.$refs['wechatMaterialView'].openWeChatMaterial(materialType);
        },

        updateForm(data){
            this.formItem.mediaId = data.media_id;
        },
        submit(form){
            this.$refs[form].validate((valid) => {
                if(valid){
                    if(this.formItem.replyType == 'text'){
                        this.formItem.mediaId = ""
                    }else{
                        this.formItem.replyContent = ""
                    }

                    saveReply(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.$message({
                                type:"success",
                                message:"编辑成功"
                            })
                            this.replyInfoDialog = false;
                            this.$emit("reselect")
                        }
                    })
                }
            })
            
        },


        resFormItem(){
            this.formItem={
                id:"",
                replyRule:"",
                replyType:"",
                replyContent:"",
                keyWord:"",
                mediaId:""
            }
        }
    },

    mounted(){
        
    }
}
</script>

<style lang="less">

</style>