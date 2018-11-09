package ${package};

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lemon.mybatis.dao.BaseDao;
import com.lemon.account.dao.${tableClass.shortClassName}${daoSuffix};
import com.lemon.mybatis.service.${baseServiceImpl};
import com.lemon.account.service.${tableClass.shortClassName}${baseService};
import ${tableClass.fullClassName};

@Service
public class ${tableClass.shortClassName}${mapperSuffix} extends ${baseServiceImpl!"tk.mybatis.mapper.common.Mapper"}<${tableClass.shortClassName}> implements ${tableClass.shortClassName!"tk.mybatis.mapper.common.Mapper"}${baseService} {

  private static final Logger LOGGER = LogManager.getLogger(${tableClass.shortClassName}${mapperSuffix}.class);
  
  @Autowired
  ${tableClass.shortClassName}${daoSuffix} ${tableClass.lowClassName}${daoSuffix};

  @Override
  protected BaseDao getDao() {
    return ${tableClass.lowClassName}${daoSuffix};
  }
}




