<template>
    <view class="article-details">
        <view class="press_center_details">
            <view class="press_center_details_01">
            </view>
            <view class="press_center_details_03">
				<!-- <video class="video" controls>
					<source src="http://www.wennever.cn/upload/pdf/newman.mp4" type="video/mp4"></source>
				</video> -->
				<!-- <video src="http://www.wennever.cn/upload/pdf/newman.mp4"></video> -->
              <swiper :autoplay="autoplay"
                      :indicator-dots="indicatorDots"
                      :interval="interval"
                      :circular="circular"
                      :duration="duration"
                      class="swiper-box"
                      style="height: 4000px !important;">
                  <swiper-item v-for="(item,index) in banner"
                               :key="index">
                      <image :src="item.imgurl"
                             class="banner-image"
                             style="height: 1200px !important;width: 100%;"></image>
					  <image src="../../../static/newman2.jpg"
								class="banner-image"
									style="height: 1200px !important;width: 100%;"></image>
					  <image src="../../../static/newman3.jpg"
					  			class="banner-image"
					  				style="height: 1200px !important;width: 100%;"></image>
					  <image src="../../../static/newman4.jpg"
								class="banner-image"
									style="height: 400px !important;width: 100%;"></image>
                  </swiper-item>
              </swiper>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data () {
        return {
            banner: [
				{
					"imgurl": '../../../static/newman.jpg',
                },
				{
					"imgurl": '../../../static/newman2.jpg',
				},
				{
					"imgurl": '../../../static/newman3.jpg',
				},
				{
					"imgurl": '../../../static/newman4.jpg',
				}
			],
            indicatorDots: false,
            autoplay: false,
            circular: true,
            interval: 3000,
            duration: 1200,
            result: '',
            show: false,
            type: 'center',
        };
    },
}
</script>

<style lang="scss">
.article-details {
    .press_center_details {
        padding: 18upx 36upx;
        background: #fff;
        .press_center_details_01 {
            text-align: center;
            line-height: 112upx;
            .h3 {
                font-size: 32upx;
                font-weight: bold;
            }
            .press_center_details_02 {
                margin-left: 56upx;
            }
        }
        .press_center_details_03 {
            line-height: 180%;
            view {
                text-indent: 2rem;
                padding: 10upx 0;
            }
			video{
				width: 100%;
			}
        }
    }
}
</style>
