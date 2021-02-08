package com.coral.mini.program.common.business.dao;

import com.coral.mini.program.common.business.BaseDao;
import com.coral.mini.program.common.business.entity.File;
import com.coral.mini.program.common.business.entity.User;

import java.util.List;

/**
 * 用户数据处理层
 * @author coral
 */
public interface FileDao extends BaseDao<File,String> {

    /**
     * 根据文件ID获取
     * @param fileId
     * @return
     */
    List<File> findByFileId(String fileId);
    /**
     * 根据文件删除
     * @param fileId
     * @return
     */
    void deleteByFileId(String fileId);

}
