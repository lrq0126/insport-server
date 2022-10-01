/**
 *顶部导航视图
 * @return {type} {description}
 */

<template>
    <div class="navbar">
        <hamburger id="hamburger-container"
                   :is-active="sidebar.opened"
                   class="hamburger-container"
                   @toggleClick="toggleSideBar" />

        <breadcrumb id="breadcrumb-container"
                    class="breadcrumb-container" />

        <!-- <navbar-tab id="NavbarTab-container"
                    class="NavbarTab-container" /> -->

        <div class="right-menu">
            <template v-if="device!=='mobile'">
                <search id="header-search"
                        class="right-menu-item" />

                <error-log class="errLog-container right-menu-item hover-effect" />

                <screenfull id="screenfull"
                            class="right-menu-item hover-effect" />

                <el-tooltip content="Global Size"
                            effect="dark"
                            placement="bottom">
                    <size-select id="size-select"
                                 class="right-menu-item hover-effect" />
                </el-tooltip>

            </template>

            <el-dropdown class="avatar-container right-menu-item hover-effect"
                         trigger="click"
                         @command="handleClick($event)">
                <div class="avatar-wrapper">
                    <!-- <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar"> -->
                    <img :src="logo"
                         class="user-avatar">
                    <i class="el-icon-caret-bottom" />
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="home">首页</el-dropdown-item>
                    <!-- <router-link to="/profile/index">
            <el-dropdown-item>Profile</el-dropdown-item>
          </router-link>
          <a target="_blank" href="https://github.com/PanJiaChen/vue-element-admin/">
            <el-dropdown-item>Github</el-dropdown-item>
          </a>
          <a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/#/">
            <el-dropdown-item>Docs</el-dropdown-item>
          </a> -->
                    <el-dropdown-item divided
                                      command="logout">
                        <span style="display:block;">退出登录</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb' // 面包屑导航栏
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import NavbarTab from './Tab'  // 标签切换

export default {
    components: {
        Breadcrumb,
        Hamburger,
        NavbarTab,
        ErrorLog,
        Screenfull,
        SizeSelect,
        Search
    },
    data () {
        return {
            logo: require('../../../assets/images/logo.png'),
        }
    },
    computed: {
        ...mapGetters([
            'sidebar',
            'avatar',
            'device'
        ])
    },
    methods: {
        handleClick (name) {
            switch (name) {
                case 'home':
                    this.$router.push({ path: "/" });
                    break
                case 'logout':
                    this.logout()
                    break
            }
        },
        toggleSideBar () {
            this.$store.dispatch('app/toggleSideBar')
        },
        async logout () {
            await this.$store.dispatch('user/logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        }
    }
}
</script>

<style lang="scss">
.el-dropdown-menu--medium .el-dropdown-menu__item {
    text-align: center !important;
}
</style>

<style lang="scss" scoped>
.navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

    .hamburger-container {
        line-height: 46px;
        height: 100%;
        float: left;
        cursor: pointer;
        transition: background 0.3s;
        -webkit-tap-highlight-color: transparent;

        &:hover {
            background: rgba(0, 0, 0, 0.025);
        }
    }

    .breadcrumb-container {
        float: left;
    }

    .NavbarTab-container {
        // position: absolute;
        // left: 50%;
        // top: 50%;
        // transform: translate(-50%, -50%);
        display: inline-block;
        margin-left: 120px;
    }

    .errLog-container {
        display: inline-block;
        vertical-align: top;
    }

    .right-menu {
        float: right;
        height: 100%;
        line-height: 50px;

        &:focus {
            outline: none;
        }

        .right-menu-item {
            display: inline-block;
            padding: 0 8px;
            height: 100%;
            font-size: 18px;
            color: #5a5e66;
            vertical-align: text-bottom;

            &.hover-effect {
                cursor: pointer;
                transition: background 0.3s;

                &:hover {
                    background: rgba(0, 0, 0, 0.025);
                }
            }
        }

        .avatar-container {
            margin-right: 30px;

            .avatar-wrapper {
                margin-top: 5px;
                position: relative;

                .user-avatar {
                    cursor: pointer;
                    width: 45px;
                    height: 40px;
                    border-radius: 10px;
                }

                .el-icon-caret-bottom {
                    cursor: pointer;
                    position: absolute;
                    right: -20px;
                    top: 25px;
                    font-size: 12px;
                }
            }
        }
    }
}
</style>
