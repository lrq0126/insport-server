<template>
	<view class="select" :style="selectStyle">
		<input type="text" placeholder="" />
		<view class="options" :style="optionStyle">
			<view class="option">2</view>
			<view class="option">3</view>
			<view class="option">4</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['selectStyle','options','optionStyle'],
		data() {
			return {

			};
		}
	}
</script>

<style lang="less" scoped>
	page {
		width: 100%;
	}

	.select {
		display: flex;
		flex-direction: column;
		width: 100%;

		input {
			width: 100%;
		}
	}
</style>
