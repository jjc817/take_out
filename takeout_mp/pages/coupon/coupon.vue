<template>
	<view class="container">
		<view class="daily" @click="getCoupon">点击领取每日红包</view>
		<!-- <view class="help">使用说明</view> -->
		<view v-if="couponList.length>0" class="coupon-list">
		    <u-list @scrolltolower="scrolltolower" lowerThreshold="150">
				<block v-for="(item, index) in couponList" :key="item.id">
					<view class="item" :style="'background: ' + (item.status==1?'linear-gradient(to right,#cfa568,#e3bf79)':'linear-gradient(to right,#999,#DDDDDD)')">
					
						<view class="content">
							<view class="left">
								<view class="name">{{item.name||''}}</view>
								<view class="time">有效期至{{item.expireTime||''}}</view>
							</view>
							<view class="right">
								<image v-if="item.status==1" class='mid-img' src='../../static/images/coupon_ksy.png'></image>
								<image v-if="item.status==2" class='mid-img' src='../../static/images/coupon_ysy.png'></image>
								<image v-if="item.status==3" class='mid-img' src='../../static/images/coupon_gq.png'></image>
							</view>
						</view>
						<view class="condition">
							<text class="txt">满￥{{item.requiredAmount}} 减￥{{item.preferentialAmount}}</text>
						</view>
						<view class="btnUse" v-if="item.status==1" @click="toCategory()">去使用</view>
					</view>
				</block>
			</u-list>
		</view>
		<show-empty v-else text="没有优惠券"></show-empty>
	</view>
</template>

<script>
	import '../../api/coupon.js'
	import {
		getBaseUrl,
		requestUtil,
		getWxLogin,
		getUserProfile
	} from '../../utils/requestUtils';
	import regeneratorRuntime, {
		async
	} from '../../lib/runtime/runtime';
	import {
		addCouponApi,
		couponListApi,
		couponPagingApi,
        deleteExpiredCouponApi
	} from '../../api/coupon.js' 
	export default {
		data() {
			return {
				isloading: false,
				loading: true,
				status: "loadmore",
				wh: 0,
				active: 1,
				paging: {
					page: 1,
					pageSize: 10
				},
				couponList: [],
				show: true,
				couponId: ''

			};
		},
		computed: {},
		created() {

		},
		mounted() {

			const sysInfo = uni.getSystemInfoSync()
			this.wh = sysInfo.windowHeight

		},
		onShow() {
			this.loading = true
			this.paging.page = 1
			this.couponList = []
			this.getList()
			setTimeout(() => {
				this.loading = false;
			}, 500);

			// if(this.couponList.length == 0){
			// 	this.show = true
			// }
		},
		onPullDownRefresh() {
			this.status = "loadmore"
			this.paging.page = 1
			this.couponList = []
			this.getList()
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onReachBottom() {

		},
		methods: {
			scrolltolower() {
				if (this.isloading) return
				if (this.status != 'nomore') {
					this.status = 'loading'
					this.getList() 
				}
			},
            async getCoupon(){
				const id = wx.getStorageSync("userId")
				const param = {
					userId: id
				}
             	const res = await addCouponApi(param)
				if(res.code === 0){
					console.log("成功生成优惠券",res)
					wx.showToast({
					    title: '恭喜获得 '+ res.data.name + "!",
					    icon: 'none',
					    duration: 1500
					})
				}else{
					return uni.$showMsg(res.msg)
				}
             },
			initData() {
				this.getList()
			},
			async getList() {
				this.isloading = true
				const res = await couponPagingApi(this.paging)
				this.isloading = false
				console.log("coupon is",res)
				if (res.code === 0) {

					this.couponList.push(...res.data.list)
					
					let records = res.data.list
					if (records.length != 0) {
						this.show = false
					}
					if (this.paging.page >= res.data.pages) {
						this.status = 'nomore';
					}
					this.paging.page = this.paging.page + 1

				} else {
					return uni.$showMsg(res.msg)
				}
				if (this.loading) {
					setTimeout(() => {
						this.loading = false;
					}, 1500);
				}
                const result = await deleteExpiredCouponApi()
				if(result.code === 0){
					console.log(res.msg)
					
				}else{
					console.log("删除过期优惠券失败")
					return uni.$showMsg(res.msg)
				}
			},
			onRefresh() {
				// 清空列表数据
				console.log("正在刷新")
				this.finished = false;

				// 重新加载数据
				// 将 loading 设置为 true，表示处于加载状态
				this.loading = true;
				console.log("正在调用onload")
				this.onLoad()

			},
			toCategory(){
				uni.switchTab({
					url: '/pages/category/category'
				})
			},
		}
	}
</script>

<style lang="scss">
	page {
		background: #f4f4f4;
		min-height: 100%;
	}

	.container {
		background: #f4f4f4;
		min-height: 100%;
		padding-top: 30rpx;
	}

	.coupon-form {
		height: 110rpx;
		width: 100%;
		background: #fff;
		padding-left: 30rpx;
		padding-right: 30rpx;
		padding-top: 20rpx;
		display: flex;
	}

	.input-box {
		flex: 1;
		height: 70rpx;
		color: #333;
		font-size: 24rpx;
		background: #fff;
		position: relative;
		border: 1px solid rgba(0, 0, 0, 0.15);
		border-radius: 4rpx;
		margin-right: 30rpx;
	}

	.input-box .coupon-sn {
		position: absolute;
		top: 10rpx;
		left: 30rpx;
		height: 50rpx;
		width: 100%;
		color: #000;
		line-height: 50rpx;
		font-size: 24rpx;
	}

	.clear-icon {
		position: absolute;
		top: 21rpx;
		right: 30rpx;
		width: 28rpx;
		height: 28rpx;
	}

	.add-btn {
		display: flex;
		justify-content: center;
		align-items: center;
		height: 70rpx;
		border: none;
		width: 168rpx;
		background: #b4282d;
		border-radius: 0;
		line-height: 70rpx;
		color: #fff;
		font-size: 28rpx;
	}

	.add-btn.disabled {
		background: #ccc;
	}

	.help {
		height: 72rpx;
		line-height: 72rpx;
		text-align: right;
		padding-right: 30rpx;
		background: url(https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/15032866437ca8.png) 590rpx center no-repeat;
		background-size: 28rpx;
		color: #999;
		font-size: 24rpx;
	}
	.daily {
		height: 72rpx;
		line-height: 72rpx;
		text-align: right;
		padding-right: 30rpx;
		background-size: 28rpx;
		color: #999;
		font-size: 30rpx;
	}
	.coupon-list {
		width: 100%;
		height: auto;
		overflow: hidden;
		padding-left: 30rpx;
		padding-right: 30rpx;
	}

	.item {
		position: relative;
		height: 290rpx;
		width: 100%;
		border-radius: 8rpx;
		margin-bottom: 30rpx;
		padding-top: 52rpx;
	}

	.btnUse {
		opacity: 1;
		border: 2rpx;
		border-radius: 38rpx;
		font-family: PingFangSC, PingFangSC-Regular;
		letter-spacing: 0;
		position: relative;
		
		height: 48rpx;
		background: #A48143;
		padding-left: 16rpx;
		padding-right: 16rpx;
		position: absolute;
		right: 20rpx;
		color: #fff;
		bottom: 20rpx;
		font-size: 30rpx;
		text-align: center;
		line-height: 48rpx;
	}


	.content {
		margin-top: 24rpx;
		margin-left: 40rpx;
		display: flex;
		margin-right: 40rpx;
		flex-direction: row;
		align-items: center;
	}

	.content .left {
		flex: 1;
	}

	.name {
		font-size: 44rpx;
		color: #fff;
		margin-bottom: 14rpx;
	}

	.time {
		font-size: 24rpx;
		color: rgba(255, 255, 255, 0.8);
		line-height: 30rpx;
	}

	.content .right {
		width: 162rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.content .right .mid-img {
		width: 100rpx;
		height: 100rpx;
	}

	.condition {
		position: absolute;
		width: 100%;
		bottom: 0;
		left: 0;
		height: 78rpx;
		background: rgba(0, 0, 0, .08);
		padding: 24rpx 40rpx;
		display: flex;
		flex-direction: row;
	}

	.condition .txt {
		display: block;
		height: 30rpx;
		flex: 1;
		overflow: hidden;
		font-size: 24rpx;
		line-height: 30rpx;
		color: #fff;
	}


	.condition .icon {
		margin-left: 30rpx;
		width: 24rpx;
		height: 24rpx;
	}
</style>


 