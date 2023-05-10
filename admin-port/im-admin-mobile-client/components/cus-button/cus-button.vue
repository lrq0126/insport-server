<template>
	<view>
	 <view :class="{type?type:'input-block'}">
				<label >{{label}}</label>
				<input 
				 type="text"
				 :placeholder="placeholder" 						
				 />
	</view>	
	</view>
</template>

<script>
	export default {
		props:['type','label','labelStyle','placeholder']
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="scss" scoped>

</style>
