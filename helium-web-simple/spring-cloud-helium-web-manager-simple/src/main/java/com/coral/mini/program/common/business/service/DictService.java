package com.coral.mini.program.common.business.service;

import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.Dict;

import java.util.List;

/**
 * 字典接口
 * @author coral
 */
public interface DictService extends BaseService<Dict,String> {

    /**
     * 排序获取全部
     * @return
     */
    List<Dict> findAllOrderBySortOrder();

    /**
     * 通过type获取
     * @param type
     * @return
     */
    Dict findByType(String type);

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    List<Dict> findByTitleOrTypeLike(String key);
}