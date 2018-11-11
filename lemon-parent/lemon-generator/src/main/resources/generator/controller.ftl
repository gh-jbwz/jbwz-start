package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import ${tableClass.fullClassName};
import com.jbwz.lemon.server.service.${tableClass.shortClassName}${serviceSuffix};
import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;

@RestController
@RequestMapping("/${tableClass.lowClassName}")
public class ${tableClass.shortClassName}${classNameSuffix} extends BaseController {
  
  @Autowired
  ${tableClass.shortClassName}${serviceSuffix} ${tableClass.lowClassName}${serviceSuffix};

  @RequestMapping(value = "/page")
  public ResponseJson list(Pageable pageable,${tableClass.shortClassName} ${tableClass.lowClassName}) {
    Page<${tableClass.shortClassName}> pageList = ${tableClass.lowClassName}${serviceSuffix}.list(pageable,${tableClass.lowClassName});
    return success(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    ${tableClass.lowClassName}${serviceSuffix}.insert(${tableClass.lowClassName});
    return success();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    ${tableClass.lowClassName}${serviceSuffix}.updateById(${tableClass.lowClassName});
    return success();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Integer id) {
    ${tableClass.shortClassName} ${tableClass.lowClassName} = ${tableClass.lowClassName}${serviceSuffix}.findById(id);
    return success(${tableClass.lowClassName});
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    ${tableClass.lowClassName}${serviceSuffix}.deleteById(${tableClass.lowClassName});
    return success(${tableClass.lowClassName});
  }

}
