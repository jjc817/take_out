<template>
	<view>
		<view class="pay_success">
			<view class="divContent">
				<image src="../../static/images/success.png" />
				<view class="divSuccess">下单成功</view>
				<view class="divDesc">预计{{finishTime}}到达</view>
				<view class="divDesc1">后厨正在加紧制作中，请耐心等待~</view>
				
				<view class="btnView" @click="toIndexPage">返回首页</view>
				
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getBaseUrl,
		requestUtil,
		getWxLogin,
		getUserProfile
	} from '../../utils/requestUtils';
	import regeneratorRuntime, {
		async
	} from '../../lib/runtime/runtime';
	export default {
		data() {
			return {
				finishTime: ''
			};
		},
		onUnload() {
			uni.switchTab({
				url:'/pages/category/category'
			})
			
		},
		computed: {},
		created() {
			this.getFinishTime()
		},
		mounted() {},
		methods: {			
			toIndexPage(){
				console.log("执行了")
				uni.switchTab({
					url:'/pages/category/category'
				})
							
			},
			//获取送达时间
			getFinishTime() {
				let now = new Date()
				let hour = now.getHours() + 1
				let minute = now.getMinutes()
				if (hour.toString().length < 2) {
					hour = '0' + hour
				}
				if (minute.toString().length < 2) {
					minute = '0' + minute
				}
				this.finishTime = hour + ':' + minute
			}

		}
	}
</script>

<style>
@import url(./paySuccess.css);
</style>
