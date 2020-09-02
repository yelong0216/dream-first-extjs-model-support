/**
 * 
 */
package dream.first.extjs.model.support.generator.controller.impl.defaults;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.model.support.generator.impl.AbstractModelComponentGenerator;
import org.yelong.core.model.support.generator.impl.pojo.TModel;
import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.controller.ControllerGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @since 2.1
 */
public class DefaultControllerGenerator extends AbstractModelComponentGenerator implements ControllerGenerator {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DefaultControllerGenerator.class);

	private static final String FTL_NAME = "defaultController.ftl";

	@Override
	protected Map<String, Object> buildTemplateParams(TModel tModel) {
		String superClassName = tModel.getSuperClassName();
		if (StringUtils.isBlank(superClassName)) {// 默认为MapModel
			tModel.setSuperClassName("dream.first.core.controller.BaseCoreController");
		}
		String superClassPackageName = tModel.getSuperClassSimpleName();
		if (StringUtils.isBlank(superClassPackageName)) {// 默认为MapModel
			tModel.setSuperClassSimpleName("BaseCoreController");
		}
		Map<String, Object> root = new HashMap<>();
		root.put("model", tModel);
		return root;
	}

	@Override
	protected Template getTemplate() throws Exception {
		return freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
	}

}
