package org.helium.web.common.business.service;


import org.helium.web.common.business.BaseService;
import org.helium.web.common.common.vo.SearchVo;
import org.helium.web.common.business.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 日志接口
 * @author coral
 */
public interface LogService extends BaseService<Log,String> {

    /**
     * 分页搜索获取日志
     * @param type
     * @param key
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Log> findByConfition(Integer type, String key, SearchVo searchVo, Pageable pageable);
    /**
     * 删除所有
     */
    void deleteAll();
}
