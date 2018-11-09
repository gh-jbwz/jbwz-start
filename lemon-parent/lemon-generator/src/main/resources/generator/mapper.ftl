package ${package};

import com.lemon.mybatis.dao.${baseMapper};
import org.apache.ibatis.annotations.Mapper;
import ${tableClass.fullClassName};

@Mapper
public interface ${tableClass.shortClassName}${mapperSuffix} extends ${baseMapper!"tk.mybatis.mapper.common.Mapper"}<${tableClass.shortClassName}> {

}




