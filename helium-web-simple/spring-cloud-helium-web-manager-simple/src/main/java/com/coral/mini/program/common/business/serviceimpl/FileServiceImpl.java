package com.coral.mini.program.common.business.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.coral.mini.program.common.business.dao.FileDao;
import com.coral.mini.program.common.business.dao.LogDao;
import com.coral.mini.program.common.business.entity.File;
import com.coral.mini.program.common.business.entity.Log;
import com.coral.mini.program.common.business.service.FileService;
import com.coral.mini.program.common.business.service.LogService;
import com.coral.mini.program.common.common.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志接口实现
 *
 * @author coral
 */
@Slf4j
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public FileDao getRepository() {
        return fileDao;
    }


    @Override
    public List<File> findByFileId(String fileId) {
        return fileDao.findByFileId(fileId);
    }

    @Override
    public void deleteByFileId(String fileId) {
        fileDao.deleteByFileId(fileId);
    }
}
