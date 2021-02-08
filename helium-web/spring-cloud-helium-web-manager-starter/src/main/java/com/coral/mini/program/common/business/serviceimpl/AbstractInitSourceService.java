package com.coral.mini.program.common.business.serviceimpl;

import com.coral.mini.program.common.business.controller.PermissionController;
import com.coral.mini.program.common.business.service.InitSourceService;
import com.coral.mini.program.common.common.exception.MpException;
import com.coral.mini.program.common.common.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


public abstract class AbstractInitSourceService<T> implements InitSourceService {

    private static final Logger log = LoggerFactory.getLogger(AbstractInitSourceService.class);

    @Override
    @Transactional
    public void initSource() {
        if (isInit()) {
            log.info("数据已经初始化！");
        } else {
            T sourceModel = initSourceModel(getResourcePath());
            checkSourceModel(sourceModel);
            saveModel(sourceModel);
        }
    }


    private T initSourceModel(String sourcePath) {
        log.info("开始加载初始化配置文件...");
        ClassPathResource resource = new ClassPathResource(sourcePath);
        if (!resource.exists()) {
            throw new MpException("初始化文件不存在!");
        }
        try {
            log.info("开始解析初始化配置文件...");
            T model = JsonUtils.toObject(resource.getInputStream(), getModelClass());
            log.info("初始化文件解析完成...");
            return model;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 获取资源地址
     *
     * @return 资源地址
     */
    protected abstract String getResourcePath();

    /**
     * 获取 对象类型
     *
     * @return class类型
     */
    protected abstract Class<T> getModelClass();

    /**
     * 数据校验
     *
     * @param model 数据对象
     */
    protected abstract void checkSourceModel(T model);

    /**
     * 是否已经初始化
     *
     * @return boolean值
     */
    protected abstract boolean isInit();

    /**
     * 保存数据
     *
     * @param dataModel 数据对象
     */
    protected abstract void saveModel(T dataModel);

}
