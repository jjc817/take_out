/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.ConvertUtils;
import io.renren.modules.sys.dao.SysDictDataDao;
import io.renren.modules.sys.dto.SysDictDataDTO;
import io.renren.modules.sys.entity.SysDictDataEntity;
import io.renren.modules.sys.service.SysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

/**
 * 字典类型
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

    @Override
    public PageData<SysDictDataDTO> page(Map<String, Object> params) {
        IPage<SysDictDataEntity> page = baseDao.selectPage(
            getPage(params, "sort", true),
            getWrapper(params)
        );

        return getPageData(page, SysDictDataDTO.class);
    }

    private QueryWrapper<SysDictDataEntity> getWrapper(Map<String, Object> params){
        String dictTypeId = (String) params.get("dictTypeId");
        String dictLabel = (String) params.get("dictLabel");
        String dictValue = (String) params.get("dictValue");

        QueryWrapper<SysDictDataEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type_id", dictTypeId);
        wrapper.like(StringUtils.isNotBlank(dictLabel), "dict_label", dictLabel);
        wrapper.like(StringUtils.isNotBlank(dictValue), "dict_value", dictValue);

        return wrapper;
    }

    @Override
    public SysDictDataDTO get(Long id) {
        SysDictDataEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysDictDataDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictDataDTO dto) {
        SysDictDataEntity entity = ConvertUtils.sourceToTarget(dto, SysDictDataEntity.class);

        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataDTO dto) {
        SysDictDataEntity entity = ConvertUtils.sourceToTarget(dto, SysDictDataEntity.class);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除
        deleteBatchIds(Arrays.asList(ids));
    }

}