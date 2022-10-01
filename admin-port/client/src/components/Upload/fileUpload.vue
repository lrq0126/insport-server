/**
 * 文件上传
 * @param  {type}
 * @return {type} {description}
 */

<template>
    <div>
        <el-upload class="upload-demo"
                   :action=" url + 'appraise/appraiseInstitutions/uploadFile'"
                   :on-preview="handlePreview"
                   :on-remove="handleRemove"
                   :before-remove="beforeRemove"
                   multiple
                   :limit="1"
                   :on-exceed="handleExceed"
                   :file-list="fileList">
            <el-button size="small"
                       type="primary">点击上传</el-button>
        </el-upload>
        <!-- <el-upload class="upload-demo"
                   list-type="picture-card"
                   :action=" url + 'appraise/appraiseInstitutions/uploadFile'"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload"
                   :on-change="uploadChange"
                   :on-remove="uploadRemove"
                   :on-error="uploadError"
                   :class="{ hide: hideUpload }"
                   :headers="requestHeader"
                   :limit="1"
                   name="fileImage"
                   ref="upload">
            <el-button size="small"
                       type="primary">点击上传</el-button>
        </el-upload> -->
    </div>
</template>

<script>
import { getToken } from '@/utils/auth.js'
import config from '@/config'

export default {
    props: {
        clearFormVerify: {
            type: Function,
            required: true
        },
        value: {
            type: Number,
            equired: true
        }
    },
    data () {
        return {
            fileList: [],
            url: '',   // 请求路径前缀
            hideUpload: false,     // 隐藏upload
            verifyImg: false,      // 验证图片
            limitCount: 1,         // 图片判断值
            imageUrl: '',          // 图片地址
            requestHeader: {       // 请求头
                Authorization: 'Bearer ' + getToken()
            },
        }
    },
    watch: {
        // 监听数字变化,清空upload
        value (newVal, oldVal) {
            if (newVal > oldVal) {
                this.$refs.upload.clearFiles()
                this.hideUpload = false
            }
        }
    },
    created () {
        if (process.env.NODE_ENV === 'development') {
            this.url = config.baseUrl.dev
        } else {
            this.url = config.baseUrl.pro
        }
    },
    methods: {
        handleRemove (file, fileList) {
            console.log(file, fileList);
        },
        handlePreview (file) {
            console.log(file);
        },
        handleExceed (files, fileList) {
            this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove (file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },

        // 上传成功
        handleAvatarSuccess (res, file, fileList) {
            if (res.code === 0) {
                this.$emit('successImgUrl', res.data.imgUrl)
                this.verifyImg = true
                this.$emit('verifyImg', this.verifyImg)
                this.hideUpload = fileList.length >= this.limitCount
            }
        },
        // 上传变动
        uploadChange (file, fileList) {
            this.verifyImg = true
            this.$emit('verifyImg', this.verifyImg)
            this.clearFormVerify()
            this.hideUpload = fileList.length >= this.limitCount
        },
        // 删除图片
        uploadRemove (file, fileList) {
            this.verifyImg = false
            this.$emit('verifyImg', this.verifyImg)
            this.hideUpload = fileList.length >= this.limitCount
        },
        // 验证图片
        beforeAvatarUpload (file) {
            const isJPG = file.type.indexOf('image') === 0
            const isLt1M = file.size / 1024 / 1024 < 1
            if (!isJPG) {
                this.$message.error('只能上传图片格式!')
            }
            if (!isLt1M) {
                this.$message.error('上传头像图片大小不能超过1MB!')
            }
            return isJPG && isLt1M
        },
        // 上传失败
        uploadError (err) {
            console.log(err)
            if (err.code === 0) {
                this.$message.error('上传失败')
            }
        }
    }
}
</script>
