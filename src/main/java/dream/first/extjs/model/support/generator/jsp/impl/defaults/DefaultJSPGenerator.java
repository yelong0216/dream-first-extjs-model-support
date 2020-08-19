/**
 * 
 */
package dream.first.extjs.model.support.generator.jsp.impl.defaults;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.support.freemarker.FreeMarkerConfigurationFactory;

import dream.first.extjs.model.support.generator.jsp.JSPGenerateException;
import dream.first.extjs.model.support.generator.jsp.JSPGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @since 2.0
 */
public class DefaultJSPGenerator implements JSPGenerator {

	private static Configuration freemarkerConfiguration = FreeMarkerConfigurationFactory
			.createConfigurationByClass(DefaultJSPGenerator.class);

	private static final String FTL_NAME = "jsp.ftl";

	@Override
	public void generate(GModelAndTable modelAndTable, File jspFile) throws JSPGenerateException {
		try {
			Template template = freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
			Map<String, Object> root = new HashMap<>();
			String name = modelAndTable.getModelClassSimpleName();
			name = name.substring(0, 1).toLowerCase() + name.substring(1);
			root.put("modelClassNameLowerPrefix", name);
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jspFile), "utf-8"));
			template.process(root, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new JSPGenerateException(e);
		}
	}

	@Override
	public void generate(Class<? extends Modelable> modelClass, File jspFile) throws JSPGenerateException {
		try {
			Template template = freemarkerConfiguration.getTemplate(FTL_NAME, "UTF-8");
			Map<String, Object> root = new HashMap<>();
			String modelClassName = modelClass.getSimpleName();
			root.put("modelClassNameLowerPrefix", modelClassName.substring(0, 1).toLowerCase()
					+ modelClassName.substring(1, modelClassName.length()));
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jspFile), "utf-8"));
			template.process(root, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new JSPGenerateException(e);
		}
	}

}
