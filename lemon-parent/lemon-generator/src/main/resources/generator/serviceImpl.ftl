package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.dao.${tableClass.shortClassName}${daoSuffix};
import com.jbwz.lemon.server.base.AbstractBaseService;
import com.jbwz.lemon.server.service.${tableClass.shortClassName}Service
import ${tableClass.fullClassName};

@Service
public class ${tableClass.shortClassName}${classNameSuffix} extends AbstractBaseService<${tableClass.shortClassName}> implements ${tableClass.shortClassName}Service {


  @Autowired
  ${tableClass.shortClassName}${daoSuffix} ${tableClass.lowClassName}${daoSuffix};

  @Override
  protected BaseDao getDao() {
    return ${tableClass.lowClassName}${daoSuffix};
  }
}




