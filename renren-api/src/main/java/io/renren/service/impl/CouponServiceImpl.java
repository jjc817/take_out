package io.renren.service.impl;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.renren.common.exception.RenException;
import io.renren.common.page.PageData;

import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.common.utils.UserUtils;
import io.renren.dao.CouponDao;

import io.renren.dto.CouponDTO;
import io.renren.entity.CouponEntity;

import io.renren.service.CouponService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CouponServiceImpl extends CrudServiceImpl<CouponDao,CouponEntity, CouponDTO> implements CouponService {
    @Autowired
    UserUtils userUtils;
    @Autowired
    CouponDao couponDao;
    @Override
    public QueryWrapper getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<CouponEntity> userPage(Map<String, Object> params) {
        try {
            //现在的时间
            Date date = DateUtil.parse(DateUtil.today()).toJdkDate();
            IPage page = baseDao.selectPage(
                    getPage(params, "status", true),
                    getWrapper(params).eq(StringUtils.isNotBlank(userUtils.getUserId().toString()), "user_id", userUtils.getUserId())

            );
            List<CouponEntity> records = page.getRecords();
            records.removeIf(record -> record.getExpireTime().before(date));
            page.setRecords(records);
            return getPageData(page, CouponEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RenException("优惠券数据加载失败");
        }
    }

    @Override
    public CouponEntity getOne(Long id) {
        CouponEntity couponEntity = couponDao.selectById(id);
        return couponEntity;
    }

    @Override
    public int changeStatus(Long id) {
        CouponEntity couponEntity = couponDao.selectById(id);
        couponEntity.setStatus(2);
        return couponDao.updateById(couponEntity);
    }

    @Override
    @Transactional
    public int cleanExpired() {
        Date date = DateUtil.parse(DateUtil.today()).toJdkDate();
        LambdaQueryWrapper<CouponEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(CouponEntity::getExpireTime, date)
               .or().eq(CouponEntity::getStatus, 3)
               .or().eq(CouponEntity::getExpireTime, null)
               .or().eq(CouponEntity::getStatus, null);
        return couponDao.delete(queryWrapper);
    }

    @Override
    public CouponEntity submit(Map params) {
        CouponEntity couponEntity = new CouponEntity();
//        couponEntity.setUserId(userUtils.getUserId());
        String userId = (String)params.get("userId");

        couponEntity.setUserId(Long.parseLong(userId));

        Random random =new Random();
        int requiredAmount = random.nextInt(31)+20;
        int preferentialAmount = random.nextInt(requiredAmount / 5) + 2 + requiredAmount / 7;
        couponEntity.setStatus(1);
        couponEntity.setName("满"+requiredAmount+"减"+preferentialAmount+"优惠券");

        Date beginTime = DateUtil.parse(DateUtil.today()).toJdkDate();
        Date endTime = DateUtil.parse(String.valueOf(DateUtil.tomorrow())).toJdkDate();
        couponEntity.setStartTime(beginTime);
        couponEntity.setExpireTime(endTime);

        couponEntity.setPreferentialAmount(BigDecimal.valueOf(preferentialAmount));
        couponEntity.setRequiredAmount(BigDecimal.valueOf(requiredAmount));
        couponEntity.setCouponDescribe("红包金额为随机发放");
        if (couponDao.insert(couponEntity)>0) {
            return couponEntity;
        }
        else {
            return null;
        }
    }


}
