/**
 * 登录视图
 * @return {type} {description}
 */
<template>
    <div class="login-container">
        <el-form ref="loginForm"
                 :model="loginForm"
                 :rules="loginRules"
                 class="login-form"
                 auto-complete="on"
                 label-position="left">

            <div class="title-container"
                 style="text-align: center">
                <img src="../../assets/images/login_logo.png"
                     alt=""
                     style="width: 110px;height: 90px;margin-top: 20px;">
                <h3 class="title">云仓管理系统</h3>
                <p style="text-align: center;font-size: 13px;color: #999;">广州飞轮云仓物流科技有限公司</p>
            </div>

            <el-form-item prop="username">
                <span class="svg-container">
                    <svg-icon icon-class="user" />
                </span>
                <el-input ref="loginName"
                          v-model="loginForm.loginName"
                          placeholder="请输入您的账号"
                          name="username"
                          type="text"
                          tabindex="1"
                          auto-complete="on" />
            </el-form-item>

            <el-tooltip v-model="capsTooltip"
                        content="Caps lock is On"
                        placement="right"
                        manual>
                <el-form-item prop="password">
                    <span class="svg-container">
                        <svg-icon icon-class="password" />
                    </span>
                    <el-input :key="passwordType"
                              ref="password"
                              v-model="loginForm.password"
                              :type="passwordType"
                              placeholder="请输入您的密码"
                              name="password"
                              tabindex="2"
                              auto-complete="on"
                              @keyup.native="checkCapslock"
                              @blur="capsTooltip = false"
                              @keyup.enter.native="handleLogin" />
                    <span class="show-pwd"
                          @click="showPwd">
                        <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                    </span>
                </el-form-item>
            </el-tooltip>

            <el-button :loading="loading"
                       type="primary"
                       style="width:85%;margin:0 30px 30px 30px;"
                       @click.native.prevent="handleLogin">登 录</el-button>

            <!-- <div style="position:relative">
                <div class="tips">
                    <span>用户名 : admin</span>
                    <span>密码 : any</span>
                </div>
                <div class="tips">
                    <span style="margin-right:18px;">用户名 : editor</span>
                    <span>密码 : any</span>
                </div>

                <el-button class="thirdparty-button"
                           type="primary"
                           @click="showDialog=true">
                    Or connect with
                </el-button>
            </div> -->
        </el-form>

        <el-dialog title="Or connect with"
                   :visible.sync="showDialog">
            Can not be simulated on local, so please combine you own business simulation! ! !
            <br>
            <br>
            <br>
            <social-sign />
        </el-dialog>
    </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import SocialSign from './components/SocialSignin'

export default {
    name: 'Login',
    components: { SocialSign },
    data () {
        const validateUsername = (rule, value, callback) => {
            // if (!validUsername(value)) {
            if (!value) {
                callback(new Error('请输入正确的用户名'))
            } else {
                callback()
            }
        }
        const validatePassword = (rule, value, callback) => {
            if (value.length < 6) {
                callback(new Error('密码不能少于6位'))
            } else {
                callback()
            }
        }
        return {
            loginForm: {
                loginName: '',
                password: ''
            },
            loginRules: {
                loginName: [{ required: true, trigger: 'blur', validator: validateUsername }],
                password: [{ required: true, trigger: 'blur', validator: validatePassword }]
            },
            passwordType: 'password',
            capsTooltip: false,
            loading: false,
            showDialog: false,
            redirect: undefined,
            otherQuery: {}
        }
    },
    watch: {
        $route: {
            handler: function (route) {
                const query = route.query
                if (query) {
                    this.redirect = query.redirect
                    this.otherQuery = this.getOtherQuery(query)
                }
            },
            immediate: true
        }
    },
    created () {
        // window.addEventListener('storage', this.afterQRScan)
    },
    mounted () {
        // if (this.loginForm.loginName === '') {
        //     this.$refs.username.focus()
        // } else if (this.loginForm.password === '') {
        //     this.$refs.password.focus()
        // }
    },
    destroyed () {
        // window.removeEventListener('storage', this.afterQRScan)
    },
    methods: {
        checkCapslock ({ shiftKey, key } = {}) {
            if (key && key.length === 1) {
                if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
                    this.capsTooltip = true
                } else {
                    this.capsTooltip = false
                }
            }
            if (key === 'CapsLock' && this.capsTooltip === true) {
                this.capsTooltip = false
            }
        },
        showPwd () {
            if (this.passwordType === 'password') {
                this.passwordType = ''
            } else {
                this.passwordType = 'password'
            }
            this.$nextTick(() => {
                this.$refs.password.focus()
            })
        },
        handleLogin () {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true
                    setTimeout(() => {
                        this.loading = false
                    }, 3000)
                    this.$store.dispatch('user/login', this.loginForm)
                        .then(() => {
                            this.$router.push({ path: this.redirect || '/workbench', query: this.otherQuery })
                            this.loading = false
                        })
                        .catch(() => {
                            this.loading = false
                        })
                } else {
                    return false
                }
            })
        },
        getOtherQuery (query) {
            return Object.keys(query).reduce((acc, cur) => {
                if (cur !== 'redirect') {
                    acc[cur] = query[cur]
                }
                return acc
            }, {})
        }
        // afterQRScan() {
        //   if (e.key === 'x-admin-oauth-code') {
        //     const code = getQueryObject(e.newValue)
        //     const codeMap = {
        //       wechat: 'code',
        //       tencent: 'code'
        //     }
        //     const type = codeMap[this.auth_type]
        //     const codeName = code[type]
        //     if (codeName) {
        //       this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
        //         this.$router.push({ path: this.redirect || '/' })
        //       })
        //     } else {
        //       alert('第三方登录失败')
        //     }
        //   }
        // }
    }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #333;
$cursor: #333;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
        color: $cursor;
    }
}

/* reset element-ui css */
.login-container {
    .el-form-item__content {
        line-height: 25px;
    }
    .el-input {
        display: inline-block;
        height: 40px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: $light_gray;
            height: 40px;
            caret-color: $cursor;

            &:-webkit-autofill {
                // box-shadow: 0 0 0px 1000px $bg inset !important;
                // -webkit-text-fill-color: $cursor !important;
            }
        }
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgb(232, 240, 254);
        border-radius: 5px;
        color: #454545;
        margin: 40px 30px;
        border: 1px solid #ddd;
    }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #333;

.login-container {
    // background-color: $bg;
    width: 100%;
    min-height: 100%;
    // background-image: linear-gradient(to left, #2d9bfd 30%, #5cc1d4 100%);
    background-image: url(../../assets/images/timg-1.jpg);
    background-size: cover;
    background-position: center;
    position: relative;
    overflow: hidden;

    .login-form {
        // position: relative;
        position: absolute;
        top: 45%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 420px;
        max-width: 100%;
        // padding: 10px 35px 0;
        margin: 0 auto;
        overflow: hidden;
        background: #fff;
        border-radius: 5px;
    }

    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 10px;

        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }

    .svg-container {
        padding: 6px 5px 6px 15px;
        color: $dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }

    .title-container {
        position: relative;
        border-bottom: 1px solid #ddd;
        .title {
            font-size: 22px;
            color: #333;
            margin: 15px auto 15px auto;
            text-align: center;
            font-weight: bold;
        }
    }

    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
        user-select: none;
    }

    .thirdparty-button {
        position: absolute;
        right: 0;
        bottom: 6px;
    }

    @media only screen and (max-width: 470px) {
        .thirdparty-button {
            display: none;
        }
    }
}
</style>
