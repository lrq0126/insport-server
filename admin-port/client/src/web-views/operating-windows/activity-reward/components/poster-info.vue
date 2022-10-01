<template>
  <div>
      <el-dialog :visible.sync="viewDialog" title="海报设置" :close-on-click-modal="false">
          <el-form :model="formItem" ref="form" :rules="formItemRules" label-width="130px">
              <el-form-item label="海报名称" prop="posterName">
                  <el-input style="width: 400px" v-model="formItem.posterName"/>
              </el-form-item>
              <el-form-item label="海报类型" prop="posterName">
                  <el-select v-model="formItem.posterType" :disabled="isEdit">
                      <el-option v-for="item in posterTypes" :key="item.id" :value="item.value" :label="item.name"/>
                  </el-select>
              </el-form-item>
              <el-form-item>
                  <el-upload
                    style="width: 500px"
                    action=""
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-change="BeforeUpload"
                    :auto-upload="false"
                    :file-list="fileList"
                    :limit="1"
                    list-type="picture-card">
                    <el-button slot="trigger" size="small" type="primary">选择图片</el-button>
                    <!-- <img v-if="imageUrl" :src="imageUrl" class="avatar"><i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
                    <div slot="tip" class="el-upload__tip">只能上传1张jpg/png文件，且不超过500kb</div>
                  </el-upload>
              </el-form-item>

              <el-form-item label="转跳地址" v-if="formItem.posterType == 'trajectoryAdvertising'">
                <el-input v-model="formItem.redirectsAddress" />
              </el-form-item>

              <el-form-item label="是否添加二维码">
                  <el-radio v-model="formItem.isPlaceQrcode" label="1">是</el-radio>
                  <el-radio v-model="formItem.isPlaceQrcode" label="2">否</el-radio>
              </el-form-item>
              <el-form-item v-if="formItem.isPlaceQrcode == 1" :min="0" label="二维码缩放倍率" prop="qrcodeScaling">
                  <el-input-number v-model="formItem.qrcodeScaling" :step="0.1" :precision="2"/>
              </el-form-item>
              
              <el-form-item v-if="formItem.isPlaceQrcode == 1" :min="0" label="二维码水平位置" prop="widthPosition">
                  <el-input-number v-model="formItem.widthPosition" :step="1"/>
              </el-form-item>
              <el-form-item v-if="formItem.isPlaceQrcode == 1" :min="0" label="二维码垂直位置" prop="heightPosition">
                  <el-input-number v-model="formItem.heightPosition" :step="1"/>
              </el-form-item>
        
              <el-form-item label-width="200px">
                  <el-button @click="viewDialog = !viewDialog">取 消</el-button>
                  <el-button type="primary" @click="submit('form')">提 交</el-button>
              </el-form-item>
          </el-form>
      </el-dialog>
  </div>
</template>

<script>
import {saveActivityPoster, getActivityPoster} from '@/api/operating-windows/activity-reward.js'
export default {
    components:{

    },
    props:{
        posterTypes: Array
    },

    data(){
        return{
            viewDialog: false,
            isChangImg: 1,
            isEdit: false,
            formItem:{
                id: "",
                posterName: "",
                posterType: "",
                redirectsAddress: "",
                isEnable: "-1",
                isPlaceQrcode: "1",
                qrcodeScaling: 1,
                widthPosition: 0,
                heightPosition: 0,
                files: ""
            },  
            fileList: [],
            image: "",
            formItemRules: {
                posterName: [
                    { required: true, message: "海报名称不能为空", trigger: "blur" },
                ],
                posterType: [
                    { required: true, message: "请选择海报类型", trigger: "blur" },
                ],
                qrcodeScaling: [
                    { required: true, message: "二维码缩放比例不能为空", trigger: "blur" },
                ],
                widthPosition: [
                    { required: true, message: "二维码水平位置不能为空", trigger: "blur" },
                ],
                heightPosition: [
                    { required: true, message: "二维码垂直位置不能为空", trigger: "blur" },
                ],
                
            },

        }
    },

    methods:{

        openDialog(id){
            this.viewDialog = true;
            this.resetFormItem();
            if(id){
                this.formItem.id = id;
                this.isEdit = true;
                this.getPoster();
            }
        },

        handleRemove(file, fileList) {
            console.log(file, fileList);
            this.isChangImg = 1;
            this.image = "";
        },
        handlePreview(file) {
            console.log(file);
            // this.image = "";
        },

        BeforeUpload(file){
                let isLt2M = file.size / 1024 / 1024 < 0.6;
                let isImg = (file.raw.type == 'image/png' || file.raw.type == 'image/jpeg');

                if (!isImg) {
                    this.$message.error('图片只能是 JPG 格式!');
                    return false;
                }
                
                if (!isLt2M) {
                    this.$message.error('图片大小不能超过 500KB!');
                    return false;
                }
                console.log(file);
                if(isImg && isLt2M){
                    var reader = new FileReader();
                    reader.readAsDataURL(file.raw)
                    reader.onload = ()=>{
                        this.image = reader.result
                    }

                }

                return isImg && isLt2M;
        },


        getPoster(){
            getActivityPoster(this.formItem.id).then((res) => {
                this.formItem = res.content
                this.formItem.isPlaceQrcode = String(res.content.isPlaceQrcode)
                let fileStr = {
                    name: this.formItem.posterName,
                    url: this.formItem.posterUrl
                }
                this.fileList.push(fileStr);
                this.isChangImg = -1;
            })
        },

        submit(form){
            if(this.isChangImg == 1 && !this.image){
                this.$message.error('请添加/修改图片');
            }else{
               this.$refs[form].validate((valid) => {
                    if (valid) {
                        this.formItem.files = this.image
                        this.formItem.isChangImg = this.isChangImg
                        saveActivityPoster(this.formItem).then((res) => {
                            if(res.code == 100){
                                this.viewDialog = false;
                                this.$message({
                                    type:"success",
                                    message:"保存成功"
                                })
                                this.$emit('reselect');
                            }else{
                                this.$message({
                                    type:"error",
                                    message: res.content
                                })
                            }
                        })
                    }
                }) 
            }
        },

        resetFormItem(){
            this.formItem={
                id: "",
                posterName: "",
                isEnable: "",
                isPlaceQrcode: "1",
                qrcodeScaling: 1,
                widthPosition: 0,
                heightPosition: 0,
                files: ""
            },
            this.isEdit = false;
            this.isChangImg = 1
            this.image = ""
            this.fileList = []
        },

    },


}
</script>

<style>
     .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 30%;
        height: 30%;
        display: block;
    }
</style>