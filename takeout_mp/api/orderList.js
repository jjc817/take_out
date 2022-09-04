import {getBaseUrl,requestUtil,getUserProfile,getWxLogin} from '../utils/requestUtils';
import regeneratorRuntime, { async } from '../lib/runtime/runtime';

export const addOrderApi = (data) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/orders/submit',
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

export const orderListApi = () =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/orders/list',
	        method: 'get',
	        
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}
export const orderPagingApi = (data) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/orders/page',
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
export const orderAgainApi = (data) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/orders/again',
	        method: 'post',
	        data:data
	    }).then((res) => {
	        console.log(res.data)
	        resolve(res)
	    }).catch((err) => {
	        reject(err)
	    })
	});
}
export const deleteOrderApi = (id) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/orders/' + id,
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
export const getOrderApi = (id) =>{
	return new Promise((resolve, reject) => {
	    requestUtil({
	        url: '/orders/' + id,
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
