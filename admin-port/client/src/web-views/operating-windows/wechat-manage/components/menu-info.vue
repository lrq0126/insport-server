<template>
  <div>
    <el-dialog :visible.sync="menuInfoDialog" :close-on-click-modal="false">
      <el-form
        ref="form"
        :model="formItem"
        :rules="formItemRules"
        label-width="100px"
      >
        <el-form-item style="width: 65%" label="菜单名称" prop="menuName">
          <el-input v-model="formItem.menuName" />
        </el-form-item>
        <el-form-item label="菜单等级" prop="menuLevel" >
          <el-select v-model="formItem.menuLevel" :disabled="isAddSecondary">
            <el-option label="一级菜单" value="1" />
            <el-option label="二级菜单" value="2" />
          </el-select>
        </el-form-item>

        <el-form-item v-if="formItem.menuLevel == 2" label="父级菜单" prop="parentId">
          <el-select v-model="formItem.parentId" :disabled="isAddSecondary">
            <el-option
              v-for="item in firstMenuData"
              :key="item.id"
              :value="item.id"
              :label="item.menuName"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="动作类型">
          <el-select v-model="formItem.menuType" clearable>
            <el-option
              v-for="item in menuTypes"
              :key="item.value"
              :value="item.value"
              :label="item.name"
            />
          </el-select>
        </el-form-item>

        <el-form-item v-if="formItem.menuType == 'view'" label="链接">
          <el-input style="width: 75%" v-model="formItem.redirectUrl" />
        </el-form-item>
        <el-form-item v-else-if="formItem.menuType == 'click'" label="返回内容">
          <el-input type="textarea" :rows="3" style="width: 65%" v-model="formItem.wechatKey" />
        </el-form-item>
        <el-form-item v-else label="合法media_id">
          <el-input style="width: 75%" v-model="formItem.mediaId" />
          <el-button @click="openWeChatMaterial()">选择微信素材</el-button>
        </el-form-item>
        <!-- <el-form-item label="合法article_id">
          <el-input v-model="formItem.articleId" />
        </el-form-item> -->
        <el-button @click="menuInfoDialog = false">取 消</el-button>
        <el-button @click="submitFrom('form')">保 存</el-button>
      </el-form>

      <wechat-material-view ref="wechatMaterialView" @updateForm="updateForm"/>

    </el-dialog>
  </div>
</template>

<script>
import {getFirstMenu, getMenuInfo, saveMenu} from "@/api/operating-windows/wechat-manage"
import WechatMaterialView from './wechat-material';
export default {

  components: {
    WechatMaterialView
  },

  data() {
    return {
      menuInfoDialog: false,
      isAddSecondary: false,
      formItem: {
        id: "",
        menuName: "",
        menuLevel: "",
        parentId: "",
        menuType: "",
        redirectUrl: "",
        wechatKey: "",
        mediaId: "",
        articleId: "",
      },

      formItemRules: {
        menuName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" },
        ],
        menuLevel: [
          { required: true, message: "菜单等级不能为空", trigger: "blur" },
        ],
        parentId: [
          { required: true, message: "父级菜单不能为空", trigger: "blur" },
        ],
        menuType: [
          { required: true, message: "请求类型不能为空", trigger: "blur" },
        ],
      },

      firstMenuData: [],

      menuTypes: [
        {
          value: "view",
          name: "转跳网页",
        },
        {
          value: "click",
          name: "返回信息",
        },
        {
          value: "media_id",
          name: "返回图片",
        },
        
        {
          value: "view_limited",
          name: "网页图文消息",
        },

        {
          value: "article_id",
          name: "卡片图文消息",
        },
        // {
        //   value: "article_view_limited",
        //   name: "网页图文消息",
        // },
        
        // {
        //   value: "scancode_push",
        //   name: "扫码推送",
        // },
        // {
        //   value: "scancode_waitmsg",
        //   name: "扫码推送，且弹出<消息接收中>提示",
        // },
        // {
        //   value: "pic_sysphoto",
        //   name: "弹出系统拍照发图",
        // },
        // {
        //   value: "pic_photo_or_album",
        //   name: "弹出拍照或者相册发图",
        // },
        // {
        //   value: "pic_weixin",
        //   name: "弹出微信相册发图器",
        // },
        // {
        //   value: "location_select",
        //   name: "弹出地理位置选择器",
        // },
      ],
    };
  },

  methods: {
    openMenuInfo(data, isAddSecondary) {
      this.menuInfoDialog = true;
      this.resetForm();
      // 如果是添加二级菜单
      if(isAddSecondary){
        this.getFirstMenu();
        this.formItem.parentId = data.id;
        this.formItem.menuLevel = "2";
        this.isAddSecondary = true;
      }else{
        this.isAddSecondary = false;
        // 添加/编辑菜单
        if(data.id){
          getMenuInfo(data.id).then((res) => {
            this.formItem = res.content
            this.formItem.menuLevel = String(res.content.menuLevel)
          });
          this.getFirstMenu();
        }
      }
      
    },

    /**
     * 打开选择微信素材窗口
     */
    openWeChatMaterial(){
      if(this.formItem.menuType){
        this.$refs['wechatMaterialView'].openWeChatMaterial(this.formItem.menuType);
      }else{
        this.$message({
          message: '请先选择 [动作类型] 再进行 [选择素材]',
          type: 'warning'
        });
      }
    },

    updateForm(data){
      this.formItem.mediaId = data.media_id;
    },

    submitFrom(form){
      this.$refs[form].validate((valid) => {
        if (valid) {
          if(this.formItem.menuLevel == 1){
            this.formItem.parentId = "";
            // this.formItem.menuType = "";
          }

          if(this.formItem.menuType == 'view'){
            this.formItem.wechatKey = "";
            this.formItem.mediaId = "";
          }else if(this.formItem.menuType == 'click'){
            this.formItem.redirectUrl = "";
            this.formItem.mediaId = "";
          }else{
            this.formItem.wechatKey = "";
            this.formItem.redirectUrl = "";
          }


          saveMenu(this.formItem).then(res => {
              if(res.code == 100){
                  this.$message({
                      type:"success",
                      message:"操作成功"
                  })

                this.$emit("reselect");
                this.menuInfoDialog = false;
              }
          });
        }
      })
      
    },

    getFirstMenu(){
      getFirstMenu().then(res => {
        this.firstMenuData = res.content
      });
    },
    

    resetForm(){
      this.isAddSecondary = false;
      
      this.formItem = {
        id: "",
        menuName: "",
        menuLevel: "",
        parentId: "",
        menuType: "",
        redirectUrl: "",
        wechatKey: "",
        mediaId: "",
        articleId: "",
      }
    }

  },


  mounted(){
    
  }

};
</script>

<style>
</style>