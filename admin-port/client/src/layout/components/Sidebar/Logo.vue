/**
 * 系统名称 + 用户名
 * @return {type} {description}
 */

<template>
    <div class="sidebar-logo-container"
         :class="{'collapse':collapse}">
        <transition name="sidebarLogoFade">
            <router-link v-if="collapse"
                         key="collapse"
                         class="sidebar-logo-link"
                         to="/">
                <!-- <img v-if="logo" :src="logo" class="sidebar-logo"> -->
                <span v-if="logo"
                      class="sidebar-title">{{ mixTitle }}</span>
                <!-- <h1 v-else class="sidebar-title">{{ title }} </h1> -->
                <img v-if="logo"
                     :src="logo"
                     class="sidebar-collapse-login-logo">
            </router-link>
            <router-link v-else
                         key="expand"
                         class="sidebar-logo-link"
                         to="/">
                <!-- <img v-if="logo" :src="logo" class="sidebar-logo"> -->
                <span class="sidebar-title">{{ mixTitle }}</span>
                <h1 class="sidebar-title">{{ title }} </h1>
                <div class="sidebar-div">
                    <img :src="logo"
                         class="sidebar-login-logo">
                    <h1 class="sidebar-title">{{userName}}</h1>
                </div>
            </router-link>
        </transition>

    </div>
</template>

<script>
import Cookies from 'js-cookie'
export default {
    name: 'SidebarLogo',
    props: {
        collapse: {
            type: Boolean,
            required: true,
        }
    },
    data () {
        return {
            mixTitle: '飞轮国际',
            title: '云仓管理系统',
            // logo: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80',
            logo: require('../../../assets/images/logo.png'),
            userName: Cookies.get('userName')
        }
    }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
    transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
    opacity: 0;
}

.sidebar-logo-container {
    position: relative;
    width: 100%;
    height: 90px;
    line-height: 50px;
    background: #2b2f3a;
    text-align: center;
    overflow: hidden;

    & .sidebar-logo-link {
        height: 100%;
        width: 100%;

        & .sidebar-abbreviation {
            font-size: 14px;
            font-weight: bold;
            color: #fff;
            margin-right: 8px;
        }

        & .sidebar-logo {
            width: 32px;
            height: 32px;
            vertical-align: middle;
            margin-right: 12px;
        }

        & .sidebar-title {
            display: inline-block;
            margin: 0;
            color: #fff;
            font-weight: 600;
            line-height: 50px;
            font-size: 14px;
            margin: 0 4px;
            font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
            vertical-align: middle;
        }

        & .sidebar-div {
            display: flex;
            align-items: center;
            position: relative;
            top: -9px;
            & .sidebar-login-logo {
                display: block;
                width: 45px;
                height: 40px;
                border-radius: 50px;
                margin: 0 15px 0 30px;
            }
        }

        & .sidebar-collapse-login-logo {
            width: 40px;
            height: 35px;
            border-radius: 50px;
        }
    }

    &.collapse {
        .sidebar-logo {
            margin-right: 0px;
        }
    }
}
</style>
