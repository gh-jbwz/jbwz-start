package ${package};

import org.springframework.stereotype.Repository;
import com.jbwz.lemon.server.base.${baseMapper};
import ${tableClass.fullClassName};

@Repository
public interface ${tableClass.shortClassName}${classNameSuffix} extends ${baseMapper}<${tableClass.shortClassName}> {

}




