package io.renren.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.dao.SetmealDao;
import io.renren.common.dao.SetmealDishDao;
import io.renren.common.dto.SetmealDTO;

import io.renren.common.entity.SetmealDishEntity;
import io.renren.common.entity.SetmealEntity;
import io.renren.common.service.SetmealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 套餐
 *
 * @author David 321740709@qq.com
 * @since 1.0.0 2022-08-17
 */
@Service
public class SetmealServiceImpl extends CrudServiceImpl<SetmealDao, SetmealEntity, SetmealDTO> implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private SetmealDishDao setmealDishDao;

    @Override
    public QueryWrapper<SetmealEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SetmealEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public SetmealDTO getByWithDish(Long id) {
        SetmealEntity setmealEntity = setmealDao.selectById(id);
        SetmealDTO setmealDto = new SetmealDTO();
        BeanUtils.copyProperties(setmealEntity,setmealDto);
        LambdaQueryWrapper<SetmealDishEntity> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(SetmealDishEntity::getSetmealId,setmealEntity.getId());
        List<SetmealDishEntity> setmealDishes = setmealDishDao.selectList(dishLambdaQueryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);
        //setmealMapper.selectById(id);

        return setmealDto;
    }

    @Override
    public List<SetmealEntity> findSetmealByCategoryId(SetmealDTO setmealDTO) {
        LambdaQueryWrapper<SetmealEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmealDTO.getCategoryId() != null,SetmealEntity::getCategoryId,setmealDTO.getCategoryId());
        queryWrapper.eq(setmealDTO.getStatus() != null,SetmealEntity::getStatus,setmealDTO.getStatus());
        queryWrapper.orderByDesc(SetmealEntity::getUpdateDate);
        return setmealDao.selectList(queryWrapper);
    }
}