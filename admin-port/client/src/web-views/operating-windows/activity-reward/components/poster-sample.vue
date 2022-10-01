<template>
  <div>
    <el-dialog :visible.sync="sampleDialog" title="海报样板" width="30%">
        <el-form>
            <el-form-item>
                <el-image
                    style="width: 50%; height: 50%; marginright: 10px"
                    :src="posterUrl"
                    :preview-src-list="[posterUrl]"
                >
                </el-image>
            </el-form-item>
        </el-form>
      
    </el-dialog>
  </div>
</template>

<script>
import {generatePoster} from '@/api/operating-windows/activity-reward.js'
export default {
    components:{

    },

    data(){
        return{
            sampleDialog: false,
            posterUrl: ""
        }
    },

    methods: {
        openSample(id){
            this.sampleDialog = true;
            generatePoster(id).then((res) => {
                if(res.code == 100){
                    this.posterUrl = "http://" + res.content
                    // window.open("http://" + res.content);
                }
            })
        }
    }
};
</script>