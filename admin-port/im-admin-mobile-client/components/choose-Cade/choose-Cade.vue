<template>
	<view class="boxa">
		<view class="top_kbox">
			<block v-for="(item,i) in newlist" :key="i">
				<view class="ibox" :class="[i== i1?'actives':'']">
					<text class="uni_14">{{item.type}}：</text>
					<view class="border" @tap="alertnum(i)">
						<text class="uni_14">{{item.name}}</text>
						<dn-icon type="tiaozhuan" size="10" :class="[ i != i1 ? '' : 'transform']" style="margin-left: 10upx;"></dn-icon>
					</view>
					<!-- <image v-if="i != i1" class="ii" src="../../static/choose-Cadex.png" mode=""></image>
					<image v-else class="ii" src="../../static/choose-Cades.png" mode=""></image> -->
				</view>
			</block>
		</view>
		<view :class="[show?'list_boxs2':'list_boxs']">
			<view class="lione">
				<block v-for="(item,i) in listchild" :key="i">
					<view class="mli" @tap="chooseOne(i)">
						<text :class="[i== i2?'actives':'']" class="uni_14">{{item}}</text>
						<image v-if="i == i2" class="ii" src="../../static/choose-Cadecc.png" mode=""></image>
					</view>
				</block>
			</view>
			<view class="hideA" @tap="hide">
			</view>
		</view>
	</view>
</template>

<script>
	import dnIcon from '@/components/dn-icon/dn-icon.vue'

	export default {
		props: ['list', 'arr'], //数组  arr
		components: {
			dnIcon,
		},
		data() {
			return {
				i1: null,
				i2: null,
				show: false,
				listchild: [],
				newlist: this.list
			}
		},
		methods: {
			alertnum(i) {
				if (this.i1 != i) {
					this.listchild = [];
					this.i1 = i;
					this.listchild = this.arr[i];
					this.i2 = null;
					this.show = true;
					let ins = this.listchild.indexOf(this.newlist[i].name);
					if (ins > -1) {
						this.i2 = ins
					}
				}

			},
			chooseOne(i) {
				this.i2 = i;
				this.newlist[this.i1].name = this.listchild[i];
				this.$emit('chooseLike', [this.i1, this.i2])
				this.hide()
			},
			hide() {
				this.show = false;
				this.i1 = null;
				this.i2 = null;
			}
		}
	}
</script>

<style scoped>
	.hideA {
		height: calc(100% - 310upx);
	}

	.mli {
		/* border: 1upx solid red; */
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 22upx 0;
	}

	.lione {
		background-color: #fff;
		/* height: 262upx; */
		padding: 10upx 40upx;

	}

	.list_boxs {
		background-color: rgba(84, 80, 80, 0.48);
		position: fixed;
		height: calc(100%);
		width: 100%;
		z-index: 88;
		transition: all .5s;
		transform: translateY(-120%);
	}

	.list_boxs2 {
		background-color: rgba(84, 80, 80, 0.48);
		position: fixed;
		height: calc(100%);
		width: 100%;
		z-index: 88;
		transform: translateY(0);
		transition: all .5s;
	}

	.border {
		padding: 0px 10px;
		border: 1px solid #ddd;
		border-radius: 5px;
	}

	.transform {
		display: inline-block;
		margin-left: 6upx;
		color: #F0AD4E;
		transition: all .5s;
		transform: rotate(-90deg);
		-ms-transform: rotate(-90deg);
		/* IE 9 */
		-moz-transform: rotate(-90deg);
		/* Firefox */
		-webkit-transform: rotate(-90deg);
		/* Safari 和 Chrome */
		-o-transform: rotate(-90deg);
	}

	.ii {
		width: 30upx;
		height: 30upx;
		display: block;
		margin-left: 12upx;
	}

	.actives {
		color: #F0AD4E;
	}

	.ibox {
		display: flex;
		align-items: center;
		margin-left: auto;
		margin-right: auto;
		padding: 0 1.3em;
	}

	.top_kbox {
		display: flex;
		justify-content: space-between;
		align-items: center;
		background-color: #FFFFFF;
		/* padding: 0 5%; */
		/* position: fixed;
		top: 88upx; */
		width: 100%;
		z-index: 99;
		/* #ifdef APP-PLUS */
		top: 0;
		/* #endif */
	}

	.boxa {
		/* padding-top: 84upx; */
		width: 100%;
		position: relative;
	}
</style>
