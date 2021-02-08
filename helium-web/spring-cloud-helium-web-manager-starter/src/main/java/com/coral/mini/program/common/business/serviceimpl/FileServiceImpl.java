package com.coral.mini.program.common.business.serviceimpl;

import com.coral.mini.program.common.business.dao.FileDao;
import com.coral.mini.program.common.business.entity.File;
import com.coral.mini.program.common.business.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日志接口实现
 *
 * @author coral
 */

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
