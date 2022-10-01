<template>
    <div class="agency-information-view-map">
        <baidu-map class="bm-view"
                   center="广州市"
                   ak="wWy2A8K94nhntYTYUHS19RXW">
            <!-- 缩放控件 -->
            <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
            <!-- 地图类型控件 -->
            <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']"
                         anchor="BMAP_ANCHOR_TOP_LEFT"></bm-map-type>
            <!-- 缩略图控件 -->
            <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
                             :isOpen="true"></bm-overview-map>
            <!-- 全景控件 -->
            <!-- <bm-panorama></bm-panorama> -->
        </baidu-map>
    </div>
</template>
<script>
import BaiduMap from 'vue-baidu-map/components/map/Map.vue'
import { BmNavigation, BmMapType, BmOverviewMap, BmPanorama } from 'vue-baidu-map'

export default {
    name: "ViewMap",
    components: {
        BaiduMap,
        BmNavigation,
        BmMapType,
        BmOverviewMap,
        BmPanorama
    },
    data () {
        return {
            keyword: ''
        }
    },
    created () {

    },
    methods: {
        /**
        * 修改Tabs-view内容
        * @return {type} {description}
        */
        setTagsViewTitle () {
            const title = '查看地图'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },

    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
    }

}
</script>

<style lang="scss" scoped>
.agency-information-view-map {
    .bm-view {
        width: 100%;
        height: 44vw;
    }
}
</style>