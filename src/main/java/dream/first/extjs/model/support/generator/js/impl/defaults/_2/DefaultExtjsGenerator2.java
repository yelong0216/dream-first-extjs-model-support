/**
 * 
 */
package dream.first.extjs.model.support.generator.js.impl.defaults._2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.js.ExtjsGenerateException;
import dream.first.extjs.model.support.generator.js.impl.AbstractExtjsGenerator;
import dream.first.extjs.model.support.generator.js.impl.defaults.JSTCode;
import dream.first.extjs.model.support.generator.js.impl.defaults.ModelAndTableToJSTCodeSupport;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @since 2.0
 */
public class DefaultExtjsGenerator2 extends AbstractExtjsGenerator {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DefaultExtjsGenerator2.class);

	private static final String FTL_NAME = "js.ftl";

	protected ModelAndTableToJSTCodeSupport modelAndTableToJSTCodeSupport = new DefaultModelAndTableToJSTCodeSupport2();

	public void generate(GModelAndTable modelAndTable, File jsFile) throws ExtjsGenerateException {
		modelAndTable = execFilter(modelAndTable);
		JSTCode code = modelAndTableToJSTCodeSupport.toJSTCode(modelAndTable);
		genFile(code, jsFile);
	}

	protected void genFile(JSTCode code, File jsFile) throws ExtjsGenerateException {
		try {
			Template template = freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
			Map<String, Object> root = new HashMap<>();
			root.put("code", code);
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jsFile), "utf-8"));
			template.process(root, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new ExtjsGenerateException(e);
		}
	}

	public void setModelAndTableToJSTCodeSupport(ModelAndTableToJSTCodeSupport modelAndTableToJSTCodeSupport) {
		this.modelAndTableToJSTCodeSupport = modelAndTableToJSTCodeSupport;
	}

}
