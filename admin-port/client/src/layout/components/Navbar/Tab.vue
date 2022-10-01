/**
 * 4大模块
 * @return {type} {description}
 */

<style lang="scss">
.Navber-tab {
    .el-tabs__header {
        margin: 0;
    }
    .el-tabs__nav-wrap::after {
        background-color: #fff;
    }
    .el-tabs__nav {
        height: 48px;
    }
    .el-tabs__item {
        line-height: 50px;
        font-size: 21px;
        font-weight: bold;
    }
}
</style>

<template>
    <el-tabs class="Navber-tab"
             v-model="tab.name"
             @tab-click="handleClick">
        <el-tab-pane label="后台管理"
                     name="first"></el-tab-pane>
        <el-tab-pane label="安全资源"
                     name="second"></el-tab-pane>
        <el-tab-pane label="安全防范"
                     name="third"></el-tab-pane>
        <el-tab-pane label="安全管控"
                     name="fourth"></el-tab-pane>
        <el-tab-pane label="安全应急"
                     name="five"></el-tab-pane>
        <el-tab-pane label="督导考核"
                     name="six"></el-tab-pane>
    </el-tabs>
</template>
<script>
import { asyncRoutes } from '@/router'

export default {
    name: 'NavbarTab',
    data () {
        return {
            tab: {
                name: 'first'
            }
        };
    },
    watch: {
        $route (route) {
            // console.log(route.matched[0].name)
            // if (route.matched[0].name == 'Workbench' || route.matched[0].name == 'Rights' || route.matched[0].name == 'Basic') {
            //     this.tab.name = 'first';
            //     this.handleClick(this.tab);
            // } else if (route.matched[0].name == 'Schoolbus' || route.matched[0].name == 'Accidents'
            //     || route.matched[0].name == 'Patrol' || route.matched[0].name == 'Equipment'
            //     || route.matched[0].name == 'SafetyAssessment' || route.matched[0].name == 'MentalHealth' || route.matched[0].name == 'ColumnSetup' || route.matched[0].name == 'ContentManage') {
            //     this.tab.name = 'second';
            //     this.handleClick(this.tab);
            // } else if (route.matched[0].name == 'ItemBank' || route.matched[0].name == 'Teaching'
            //     || route.matched[0].name == 'ColumnSetup' || route.matched[0].name == 'ContentManage'
            //     || route.matched[0].name == 'TestpaperLibrary' || route.matched[0].name == 'SafetyEducation') {
            //     this.tab.name = 'third';
            //     this.handleClick(this.tab);
            // } else if (route.matched[0].systemTab == 'SafetyEmergency') {
            //     this.tab.name = 'fourth';
            //     this.handleClick(this.tab);
            // } else if (route.matched[0].systemTab == 'SafetyEmergency') {
            //     this.tab.name = 'five';
            //     this.handleClick(this.tab);
            // } else if (route.matched[0].systemTab == 'SafetyEmergency') {
            //     this.tab.name = 'six';
            //     this.handleClick(this.tab);
            // }
        }
    },
    created () {
        // sessionStorage.setItem('NavbarTab', 1);
        this.tab.name = sessionStorage.getItem('NavbarTab');
        switch (sessionStorage.getItem('NavbarTab')) {
            case 'six':
                this.handleClick(this.tab);
                break;

            case 'five':
                this.handleClick(this.tab);
                break;

            case 'fourth':
                this.handleClick(this.tab);
                break;

            case 'second':
                this.handleClick(this.tab);
                break;

            case 'third':
                this.handleClick(this.tab);
                break;

            default:
                this.handleClick(this.tab);
                break;
        }
    },
    methods: {
        handleClick (tab) {
            switch (tab.name) {
                case 'six':
                    sessionStorage.setItem('NavbarTab', 'six');
                    asyncRoutes.forEach(element => {
                        if (element.systemTab == "SupervisoryAssessment") {
                            element.hidden = false;
                        } else {
                            element.hidden = true;
                        }
                    });
                    break;

                case 'five':
                    sessionStorage.setItem('NavbarTab', 'five');
                    asyncRoutes.forEach(element => {
                        if (element.systemTab == "SafetyEmergency") {
                            element.hidden = false;
                        } else {
                            element.hidden = true;
                        }
                    });
                    break;

                case 'fourth':
                    sessionStorage.setItem('NavbarTab', 'fourth');
                    asyncRoutes.forEach(element => {
                        if (element.systemTab == "SafetyControls") {
                            element.hidden = false;
                        } else {
                            element.hidden = true;
                        }
                    });
                    break;


                case 'third':
                    sessionStorage.setItem('NavbarTab', 'third');
                    asyncRoutes.forEach(element => {
                        if (element.systemTab == "SafetyProtection") {
                            element.hidden = false;
                        } else {
                            element.hidden = true;
                        }
                    });
                    break;

                case 'second':
                    sessionStorage.setItem('NavbarTab', 'second');
                    asyncRoutes.forEach(element => {
                        if (element.systemTab == "SafetyResources") {
                            element.hidden = false;
                        } else {
                            element.hidden = true;
                        }
                    });
                    break;

                default:
                    sessionStorage.setItem('NavbarTab', 'first');
                    asyncRoutes.forEach(element => {
                        if (element.systemTab == "BackstageManage") {
                            element.hidden = false;
                        } else {
                            element.hidden = true;
                        }
                    });
                    break;
            }
        }
    }
};
</script>