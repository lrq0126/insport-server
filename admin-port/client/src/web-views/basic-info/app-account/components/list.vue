/**
 * app帐号列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <div class="filter-container">
            <span class="filter-item">登陆人员名称：</span>
            <el-input style="width: 200px;"
                      class="filter-item"
                      placeholder="请输入关键词"></el-input>

            <span class="filter-item">登陆账号：</span>
            <el-input style="width: 200px;"
                      class="filter-item"
                      placeholder="请输入关键词"></el-input>

            <el-button class="filter-item"
                       type="primary"
                       icon="el-icon-search">查询</el-button>
        </div>

        <el-table :data="tableData"
                  border
                  style="width: 100%">
            <el-table-column prop="company"
                             label="上级归属机构"></el-table-column>
            <el-table-column prop="office"
                             label="归属机构"></el-table-column>
            <el-table-column prop="loginname"
                             label="登陆账号"></el-table-column>
            <el-table-column prop="name"
                             label="登陆人员"></el-table-column>
            <el-table-column label="登陆状态">
                <el-button type="success"
                           plain>允许</el-button>
                <el-button type="warning"
                           plain>禁止</el-button>
            </el-table-column>
            <el-table-column label="操作">
                <router-link :to="'/basic/back-account/edit'">
                    <el-button class="filter-item"
                               type="info"
                               plain
                               icon="el-icon-edit">修改</el-button>
                </router-link>
                <el-button class="filter-item"
                           type="danger"
                           plain
                           icon="el-icon-delete"
                           @click="deleteItem">删除</el-button>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    data () {
        return {
            value: "",
            tableData: [
                {
                    id: "1",
                    name: "‭‬愈国敏",
                    loginname: "‭13707037512‬",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "2",
                    name: "‭‬贺佳",
                    loginname: "18970329949_a",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "3",
                    name: "刘仙桃",
                    loginname: "18720339061_a",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "4",
                    name: "王梅红",
                    loginname: "13627036397_a",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "5",
                    name: "杨瑜",
                    loginname: "18779195297_a",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "6",
                    name: "郭邦印",
                    loginname: "18770378063",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "7",
                    name: "洪焱",
                    loginname: "15946802887",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                },
                {
                    id: "8",
                    name: "纪卫红",
                    loginname: "15870923077",
                    company: "嘉弘小学",
                    office: "嘉弘小学"
                }
            ]
        };
    },
    methods: {
        deleteItem () {
            if (this.multipleSelection.length > 1) {
                this.$message({
                    message: "请选择单个操作！",
                    type: "success"
                });
            } else {
                let id = this.multipleSelection[0].id;
                this.deleteobj(this.tableData, id);

                this.tableData = JSON.parse(JSON.stringify(this.tableData));

                this.$message({
                    message: "删除成功！",
                    type: "success"
                });
            }
        },
        deleteobj (arr, id) {
            if (arr != undefined) {
                for (let a = 0; a < arr.length; a++) {
                    if (arr[a].id == id) {
                        arr.splice(a, 1);
                        return;
                    }

                    this.deleteobj(arr[a].children, id);
                }
            }
        }
    }
};
</script>

