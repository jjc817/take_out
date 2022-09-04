const baseUrl = "http://localhost:8081/api";
//const baseUrl = "http://192.168.43.189:8081/api";
// const baseUrl = "http://192.168.159.1:8081/api";

export const getBaseUrl = () =>{
	return baseUrl;
}
export const getWxLogin = () => {
    return new Promise((resolve, reject) => {
        wx.login({
            timeout: 5000,
            success: (res) => {
                resolve(res)
            },
            fail: (err) => {
                reject(err)
            }
        })
    });
}


export const getUserProfile = () => {
    return new Promise((resolve, reject) => {
        wx.getUserProfile({
            desc: '获取用户信息',
            success: (res) => {
                resolve(res)
                wx.setStorageSync('userInfo', res.userInfo)
            },
            fail: (err) => {
                reject(err)
            }
        })
    });
}




let ajaxTimes = 0;

export const requestUtil = (params) => {
    const token = wx.getStorageSync('token')
    let header = {...params.header};
    console.log("params.url",params.url)
    if(params.url.includes("/")){
        header["token"] = wx.getStorageSync('token')
		header["userId"] = wx.getStorageSync('userId')
    }
    var start = new Date().getTime();
    console.log("ajaxTimes=" + ajaxTimes)
    ajaxTimes++;
    // 显示加载中 效果
   
    
    // while (true) if (new Date().getTime() - start > 1 * 1000) break;
    return new Promise((resolve, reject) => {
        wx.request({
            ...params,
            header ,
            url: baseUrl + params.url,
            success: (res) => {
                resolve(res.data)
            },
            fail: (err) => {
                reject(err)
            },
            complete: () => {
                
            }
        })
    });
}