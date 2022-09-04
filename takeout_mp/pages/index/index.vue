<template>
	<view class="container">
		<swiper class="banner" indicator-dots="true" autoplay="true" interval="3000" duration="1000">
			<swiper-item v-for="(item, index) in banner" :key="index">
				<navigator v-if="item.link" :url="item.link">
					<image :src="item.image_url" background-size="cover"></image>
				</navigator>
				<image v-else :src="item.image_url" background-size="cover"></image>
			</swiper-item>
		</swiper>
		<view class="a-section a-topic" v-if="channel.length > 0">
			<view class="m-menu">
				<navigator class="item" v-for="(item, index) in channel" :key="index" :url="item.url">
					<image :src="item.icon_url" background-size="cover"></image>
					<text>{{item.name}}</text>
				</navigator>
			</view>
		</view>
	
	</view>
</template>

<script>
	import '../../api/category.js'
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
		cartListApi,
		categoryListApi,
		dishListApi,
		setmealListApi,
		clearCartApi,
		updateCartApi,
		setMealDishDetailsApi,
		addCartApi,
	} from '../../api/category'
	
	const api = require('../../api/api.js');
	const util = require('../../api/util.js')
	export default {
		data() {
			return {
				banner: [],
				channel: []
			}
		},
		methods: {
			getIndexData: function() {
				let that = this;
				util.request(api.IndexUrlBanner).then(function(res) {
					if (res.errno === 0) {
						that.banner = res.data.banner
					}
				});
				util.request(api.IndexUrlChannel).then(function(res) {
					if (res.errno === 0) {
						that.channel = res.data.channel
					}
				});

			},
		},
		// 增加下拉刷新数据的功能
		onPullDownRefresh() {
			this.getIndexData();
		},
		onShareAppMessage: function() {
			return {
				title: '美食元素',
				path: '/pages/index/index'
			}
		},
		onLoad: function() {
			this.getIndexData();
		}
	}
</script>

<style lang="scss">
	.banner {
		width: 750rpx;
		height: 417rpx;
	}

	.banner image {
		width: 100%;
		height: 417rpx;
	}

	.m-menu {
		display: flex;
		height: 181rpx;
		width: 750rpx;
		flex-flow: row nowrap;
		align-items: center;
		justify-content: space-between;
		background-color: #fff;
	}

	.m-menu .item {
		flex: 1;
		display: block;
		padding: 20rpx 0;
	}

	.m-menu image {
		display: block;
		width: 58rpx;
		height: 58rpx;
		margin: 0 auto;
		margin-bottom: 12rpx;
	}

	.m-menu text {
		display: block;
		font-size: 24rpx;
		text-align: center;
		margin: 0 auto;
		line-height: 1;
		color: #333;
	}

	.a-section {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		background: #fff;
		color: #333;
		margin-top: 20rpx;
	}

	.a-section .h {
		display: flex;
		flex-flow: row nowrap;
		align-items: center;
		justify-content: center;
		height: 130rpx;
	}

	.a-section .h .txt {
		padding-right: 30rpx;
		background-size: 16.656rpx 27rpx;
		display: inline-block;
		height: 36rpx;
		font-size: 33rpx;
		line-height: 36rpx;
	}
</style>