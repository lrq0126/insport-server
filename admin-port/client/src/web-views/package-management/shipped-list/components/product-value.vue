<template>
    <div>
        <el-dialog :visible.sync="productDialog" width="70%">
        <!-- <el-divider content-position="left"><i class="el-icon-tickets"></i>产品&货值信息</el-divider> -->
              <!-- <div
                @click="packValuationView = !packValuationView"
                class="packValuation"
              >
                <div class="packValuationTitle">
                  <span>产品货值信息&保险信息</span>
                </div>
                <div class="packValuationIcon">
                  <i
                    v-if="packValuationView"
                    style="font-size: 20px; color: #ffffff"
                    class="el-icon-arrow-up"
                  ></i>
                  <i
                    v-else
                    style="font-size: 20px; color: #ffffff"
                    class="el-icon-arrow-down"
                  ></i>
                </div>
              </div> -->
              <div v-if="packValuationView" class="packValuationTable">
                <div style="margin-bottom: 20px">
                  <el-table border :data="packInsurancePrices">
                    <el-table-column
                      label="保险名称"
                      align="center"
                      prop="insuranceName"
                    ></el-table-column>

                    <el-table-column
                      label="保险编号"
                      align="center"
                      prop="insuranceNo"
                    ></el-table-column>

                    <el-table-column
                      label="理赔详情"
                      align="center"
                      prop="claimsDetail"
                    >
                      <template slot-scope="scope">
                        <el-button
                          size="mini"
                          type="primary"
                          plain
                          @click="lookClaimsDetail(scope.row.claimsDetail)"
                          >查看详情</el-button
                        >
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="保险价格"
                      align="center"
                      prop="insurancePrice"
                    ></el-table-column>
                    <el-table-column label="价格理赔详情" align="center">
                      <template slot-scope="scope">
                        <el-button
                          size="mini"
                          type="primary"
                          plain
                          @click="lookClaimsDetail(scope.row.priceClaimsDetail)"
                          >查看详情</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </div>

                <el-dialog
                  :visible.sync="dialogClaims"
                  title="理赔详情信息"
                  width="1000px"
                >
                  <el-form style="margin-bottom: 15px">
                    <el-form-item>
                      <textarea
                        v-model="claimsDetail"
                        readonly
                        rows="18"
                        cols="130"
                      ></textarea>
                    </el-form-item>
                    <el-form-item style="margin-left: 450px">
                      <el-button
                        type="primary"
                        @click="dialogClaims = !dialogClaims"
                        >确认</el-button
                      >
                    </el-form-item>
                  </el-form>
                </el-dialog>

                <el-table border :data="packValuations">
                  <el-table-column
                    label="序号"
                    width="50"
                    type="index"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    label="产品名称"
                    align="center"
                    prop="productName"
                  >
                  </el-table-column>
                  <el-table-column
                    label="单价/￥"
                    align="center"
                    prop="unitPrice"
                  >
                  </el-table-column>
                  <el-table-column label="数量" align="center" prop="number">
                  </el-table-column>
                  <el-table-column
                    label="货值/￥"
                    align="center"
                    prop="goodsValue"
                  >
                  </el-table-column>
                  
                </el-table>
              </div>
        </el-dialog>
    </div>
</template>

<script>
import { getProductValueDetail } from "@/api/package-management/waiting-area";
export default {
    
    data() {
        return {
            productDialog: false,

            dialogClaims: false, // 理赔详情对话框

            packValuationView: true,

            packValuations: [],

            packInsurancePrices: [],
            claimsDetail: ""
        }
    },

     methods:{
         lookClaimsDetail(claimsDetail) {
            this.claimsDetail = claimsDetail;
            this.dialogClaims = true;
         },

         openDialog(packId){
             getProductValueDetail(packId).then((res) => {
                 this.packValuations = res.content.packValuations;
                 this.packInsurancePrices = res.content.packInsurancePrices;
             });
             this.productDialog = true;
         }
     }
}
</script>

<style scoped>
.packValuationTable {
    margin-left: 10%;
    margin-top: 1%;
    width: 80%;
  }
</style>