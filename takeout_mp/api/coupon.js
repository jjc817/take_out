import {getBaseUrl,requestUtil,getUserProfile,getWxLogin} from '../utils/requestUtils';
import regeneratorRuntime, { async } from '../lib/runtime/runtime';

export const addCouponApi = (data) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/submit',
	        method: 'post',
	        data: data
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}
// export const addCouponApi = () =>{
// 	return new Promise((resolve, reject) => {
// 	    requestUtil({
// 	        url: '/coupon/submit',
// 	        method: 'post',
	
// 	    }).then((res) => {
// 	        console.log(res.data)
// 	        resolve(res)
// 	    }).catch((err) => {
// 	        reject(err)
// 	    })
// 	});
// }

export const couponListApi = () =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/list',
	        method: 'get',
	        
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}
export const couponPagingApi = (data) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/page',
	        method: 'get',
	        data:{...data}
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}

export const deleteCouponApi = (id) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/' + id,
	        method: 'delete',
	        data:id
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}

export const deleteExpiredCouponApi = () =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/clean',
	        method: 'delete',
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}

export const changeStatusCouponApi = (id) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/change/' + id,
	        method: 'put',
	        data:id
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}

export const availableCouponApi = (data) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/available',
	        method: 'get',
	        data:{...data}
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}

export const getCouponApi = (id) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/coupon/' + id,
	        method: 'get',
	        data:id
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}
