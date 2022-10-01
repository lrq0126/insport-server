<template>
    <div>
        <el-upload class="avatar-uploader"
                   :action="uploadUrl"
                   name="file"
                   :data="data"
                   :headers="requestHeader"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload"
                   :show-file-list="false">
            <img v-if="imgValue"
                 :src="hzscPath + imgValue"
                 class="avatar">
            <i v-else
               class="el-icon-plus avatar-uploader-icon">
            </i>
        </el-upload>
    </div>
</template>

<script>
import config from '@/config'
import { getToken } from '@/utils/auth.js'
export default {
    props: {
        value: {
            type: String,
            required: true
        },
        uploadUrl: {
            type: String,
            required: true
        },
        data: {
            type: Object
        }
    },
    data () {
        return {
            requestHeader: {
                Authorization: 'Bearer ' + getToken()
            },
            hzscPath: ''
        }
    },
    computed: {
        imgValue: {
            get () {
                return this.value
            },
            set (val) {
                this.$emit('input', val)
            }
        }
    },
    created() {
        this.initEnvironmentPath()
    },
    methods: {
         initEnvironmentPath () {
            if (process.env.NODE_ENV === 'development') {
                this.hzscPath = config.baseUrl.dev + '/base/static'
            } else {
                this.hzscPath = config.baseUrl.pro + '/base/static'
            }
        },
        handleAvatarSuccess (res, file) {
            // console.log(res)
            if (res.code === 0) {
                this.$emit('input', res.data.imgUrl)
            }
        },
        beforeAvatarUpload (file) {
            const isJPG = file.type.indexOf('image') === 0
            const isLt1M = file.size / 1024 / 1024 < 1
            if (!isJPG) {
                this.$message.error('支持图片格式!')
            }
            if (!isLt1M) {
                this.$message.error('上传头像图片大小不能超过1MB!')
            }
            return isJPG && isLt1M
        }
    }
}
</script>

<style lang="scss" scoped>
</style>