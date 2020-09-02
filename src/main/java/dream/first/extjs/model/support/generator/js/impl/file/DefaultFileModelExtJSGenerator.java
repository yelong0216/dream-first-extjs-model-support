/**
 * 
 */
package dream.first.extjs.model.support.generator.js.impl.file;

import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.js.impl.defaults.v2.DefaultExtJSGenerator_v2;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @since 2.0
 */
public class DefaultFileModelExtJSGenerator extends DefaultExtJSGenerator_v2 {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DefaultFileModelExtJSGenerator.class);

	private static final String FTL_NAME = "js.ftl";

	@Override
	protected Template getTemplate() throws Exception {
		return freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
	}

}
