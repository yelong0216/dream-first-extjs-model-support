package ${model.modelPackage};

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dream.first.extjs.exception.Request404Exception;
import ${model.superClassName};

@Controller
@RequestMapping("${model.modelNamePrefixLowerCase}")
public class ${model.modelName}Controller extends ${model.superClassSimpleName} {

	@RequestMapping("index")
	public String index() {
		return "${model.modelNamePrefixLowerCase}/${model.modelNamePrefixLowerCase}Manage.jsp";
	}
	
	@RequestMapping("detail")
	public String detail() {
		String id = getRequest().getParameter("id");
		if(StringUtils.isBlank(id)) {
			throw new Request404Exception();
		}
		${model.modelName} model = modelService.findById(getModelClass(), id);
		if( null == model ) {
			throw new Request404Exception();
		}
		getRequest().setAttribute("${model.modelNamePrefixLowerCase}", model);
		return "${model.modelNamePrefixLowerCase}/${model.modelNamePrefixLowerCase}Detail.jsp";
	}
	
}
