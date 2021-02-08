package com.coral.mini.program.common.business.service;


import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.File;
import com.coral.mini.program.common.business.entity.User;
import com.coral.mini.program.common.common.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文件接口
 * @author coral
 */
public interface FileService extends BaseService<File,String> {

    /**
     * 根据文件ID获取
     * @param fileId
     * @return
     */
    List<File> findByFileId(String fileId);

    /**
     * 根据文件ID获取
     * @param fileId
     * @return
     */
    void deleteByFileId(String fileId);
}
