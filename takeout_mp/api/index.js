	request: function(url, postData = {}, method = "POST", contentType = "application/x-www-form-urlencoded", isDelay, hideLoading) {
		//接口请求
		let loadding = false;
		utils.delayed && uni.hideLoading();
		clearTimeout(utils.delayed);
		utils.delayed = null;
		if (!hideLoading) {
			utils.delayed = setTimeout(() => {
				uni.showLoading({
					mask: true,
					title: '请稍候...',
					success(res) {
						loadding = true
					}
				})
			}, isDelay ? 1000 : 0)
		}