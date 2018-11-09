package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${tableClass.fullClassName};
import com.lemon.account.service.${tableClass.shortClassName}${serviceSuffix};
import com.lemon.common.base.BaseController;
import com.lemon.common.base.PageList;
import com.lemon.common.base.ResponseJson;

@RestController
@RequestMapping("/${tableClass.lowClassName}")
public class ${tableClass.shortClassName}${controllerSuffix} extends BaseController {
  
  @Autowired
  ${tableClass.shortClassName}${serviceSuffix} ${tableClass.lowClassName}${serviceSuffix};

  @RequestMapping(value = "/page")
  public ResponseJson list(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    PageList<${tableClass.shortClassName}> pageList = ${tableClass.lowClassName}${serviceSuffix}.list(${tableClass.lowClassName});
    return responseSuccess(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    ${tableClass.lowClassName}${serviceSuffix}.insert(${tableClass.lowClassName});
    return responseSuccess();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    ${tableClass.lowClassName}${serviceSuffix}.updateById(${tableClass.lowClassName});
    return responseSuccess();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Long id) {
    ${tableClass.shortClassName} ${tableClass.lowClassName} = ${tableClass.lowClassName}${serviceSuffix}.findById(id);
    return responseSuccess(${tableClass.lowClassName});
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(${tableClass.shortClassName} ${tableClass.lowClassName}) {
    ${tableClass.lowClassName}${serviceSuffix}.deleteById(${tableClass.lowClassName});
    return responseSuccess(${tableClass.lowClassName});
  }

}
