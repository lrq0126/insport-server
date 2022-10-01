<template>
  <div>
    <el-dialog :visible.sync="trackDetailDialog" width="40%" title="轨迹详情">
        <p><span style="font-size: 24px">订单号：{{orderNumber}}</span></p>
        <div v-if="trackingInfo" class="info-class">
            <div v-for="item in trackingInfo" :key="item">
               <p style="line-height: 40px; font-size: 22px">运输商：{{item.provider.alias}}</p>
                <div v-for="eventItem in item.events" :key="eventItem" style="margin-bottom: 30px">
                    <div class="icon-class">
                        <div style="margin-top:10px ;height: 60upx; background-color: #FFFFFF;">
                            <img src="../icon/point.png">
                        </div>
                    </div>
                    <div style="width: 90%; float: left; min-height: 130px">
                        <p style="line-height: 20px"><span style="font-size: 18px">{{eventItem.time_utc}}</span></p>
                        <p><span style="font-size: 18px">{{eventItem.description}}</span></p>
                        <p style="line-height: 22px"><span style="font-size: 24px">{{eventItem.location}}</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div v-else>
            <p>暂无订单号轨迹信息</p>
        </div>

    </el-dialog>
  </div>
</template>

<script>
import {getTrack17OrderTracking} from '@/api/customer-management/track-list'
export default {
    components:{

    },
    
    data(){
        return{
            trackDetailDialog: false,
            orderNumber: "",
            trackingInfo:[],
        }
    },

    methods: {
        handleTrackDetail(number){
            this.trackDetailDialog = true;
            this.orderNumber = number
            console.log("查询轨迹");

            getTrack17OrderTracking(number).then((res) => {
                if(res.code == 100){
                    this.trackingInfo = res.content
                }
            })
        }
    }
}
</script>

<style  lang="scss" scope>

.info-class{
    overflow: auto;
    background-color: #FFFFFF;

    .icon-class{
		width: 10%; 
		text-align: center; 
		float: left;
		min-height: 130px;
		background: url('../icon/straight-line.png') center repeat-y;
	}
}

</style>