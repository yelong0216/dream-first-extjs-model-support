/**
 * 
 */
package dream.first.extjs.model.support.generator.jsp.impl.defaults;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.core.model.support.generator.impl.AbstractModelComponentGenerator;
import org.yelong.core.model.support.generator.impl.pojo.TModel;
import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.jsp.JSPGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @since 2.0
 */
public class DefaultJSPGenerator extends AbstractModelComponentGenerator implements JSPGenerator {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DefaultJSPGenerator.class);

	private static final String FTL_NAME = "jsp.ftl";

	@Override
	protected Map<String, Object> buildTemplateParams(TModel tModel) {
		GModelAndTable modelAndTable = tModel.getgModelAndTable();
		Map<String, Object> root = new HashMap<>();
		String name = modelAndTable.getModelClassSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		root.put("modelClassNameLowerPrefix", name);
		return root;
	}

	@Override
	protected Template getTemplate() throws Exception {
		return freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
	}

}
