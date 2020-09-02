/**
 * 
 */
package dream.first.extjs.model.support.generator.js.impl.defaults.v1;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.support.generator.impl.AbstractModelComponentGenerator;
import org.yelong.core.model.support.generator.impl.pojo.TModel;
import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.js.ExtJSGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @since 2.0
 */
public class DefaultExtJSGenerator_v1 extends AbstractModelComponentGenerator implements ExtJSGenerator {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DefaultExtJSGenerator_v1.class);

	private static final String FTL_NAME = "js.ftl";

	protected ModelAndTableToJSTCodeSupport modelAndTableToJSTCodeSupport = new DefaultModelAndTableToJSTCodeSupport_v1();

	@Override
	protected Map<String, Object> buildTemplateParams(TModel tModel) {
		JSTCode code = modelAndTableToJSTCodeSupport.toJSTCode(tModel.getgModelAndTable());
		Map<String, Object> root = new HashMap<>();
		root.put("code", code);
		return super.buildTemplateParams(tModel);
	}

	@Override
	protected Template getTemplate() throws Exception {
		return freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
	}

	public void setModelAndTableToJSTCodeSupport(ModelAndTableToJSTCodeSupport modelAndTableToJSTCodeSupport) {
		this.modelAndTableToJSTCodeSupport = modelAndTableToJSTCodeSupport;
	}

}
