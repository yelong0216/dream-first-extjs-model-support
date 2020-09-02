/**
 * 
 */
package dream.first.extjs.model.support.generator.jsp.impl.detail;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.support.generator.impl.AbstractModelComponentGenerator;
import org.yelong.core.model.support.generator.impl.pojo.TModel;
import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.jsp.JSPGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @since 2.1
 */
public class DetailJSPGenerator extends AbstractModelComponentGenerator implements JSPGenerator {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DetailJSPGenerator.class);

	private static final String FTL_NAME = "detailJSP.ftl";

	@Override
	protected Map<String, Object> buildTemplateParams(TModel tModel) {
		Map<String, Object> root = new HashMap<>();
		root.put("model", tModel);
		return root;
	}

	@Override
	protected Template getTemplate() throws Exception {
		return freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
	}

}
