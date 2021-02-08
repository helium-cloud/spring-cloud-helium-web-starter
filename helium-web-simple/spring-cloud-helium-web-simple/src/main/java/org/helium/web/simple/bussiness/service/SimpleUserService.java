package org.helium.web.simple.bussiness.service;

import org.helium.web.common.business.BaseDao;
import org.helium.web.common.business.BaseService;
import org.helium.web.simple.bussiness.dao.SimpleUserDao;
import org.helium.web.simple.bussiness.entity.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface SimpleUserService extends BaseService<SimpleUser, String> {

}
