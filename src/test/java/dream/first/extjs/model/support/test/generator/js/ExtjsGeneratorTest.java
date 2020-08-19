/**
 * 
 */
package dream.first.extjs.model.support.test.generator.js;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.yelong.commons.io.FileUtilsE;
import org.yelong.commons.util.ListUtilsE;
import org.yelong.core.model.support.generator.GFieldAndColumnWrapper;
import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.core.model.support.generator.ModelGenerateException;
import org.yelong.core.model.support.generator.pdm.PDMResolverException;
import org.yelong.core.model.support.generator.support.ModelGenerateSupport;

import dream.first.extjs.model.support.generator.js.ExtjsGenerateException;
import dream.first.extjs.model.support.generator.js.ExtjsGenerator;
import dream.first.extjs.model.support.generator.js.impl.defaults.DefaultExtjsGenerator;
import dream.first.extjs.model.support.generator.js.impl.defaults._2.DefaultExtjsGenerator2;

/**
 *
 */
public class ExtjsGeneratorTest {

	public static ExtjsGenerator extjsGenerator = new DefaultExtjsGenerator();

	public static ExtjsGenerator extjsGenerator2 = new DefaultExtjsGenerator2();

	private static final List<String> IGNORE_FIELD_NAME = new ArrayList<>();

	static {
		// IGNORE_FIELD_NAME.add("id");
		IGNORE_FIELD_NAME.add("creator");
		IGNORE_FIELD_NAME.add("createTime");
		IGNORE_FIELD_NAME.add("updator");
		IGNORE_FIELD_NAME.add("updateTime");
		IGNORE_FIELD_NAME.add("state");
		extjsGenerator2.addFieldAndColumnInterceptor(x -> {
			if (x.getFieldName().equals("id")) {
				return new GFieldAndColumnWrapper(x) {

					public boolean isPrimaryKey() {
						return true;
					};

				};
			}
			// 忽略列
			return IGNORE_FIELD_NAME.contains(x.getFieldName()) ? null : x;
		});
	}

	public static GModelAndTable getModelAndTable() throws PDMResolverException {
		List<GModelAndTable> modelAndTables = ModelGenerateSupport.pdmResolver.resolve(
				FileUtilsE.getFile("F:\\developer\\PowerDesigner\\数模\\model-generate-test\\generate-test.pdm"));
		return ListUtilsE.get(modelAndTables, 0);
	}

	@Test
	public void generateModle() throws FileNotFoundException, PDMResolverException, ModelGenerateException {
		ModelGenerateSupport.generatePOJOModel(
				"F:\\developer\\PowerDesigner\\数模\\model-generate-test\\generate-test.pdm",
				"F:\\developer\\PowerDesigner\\数模\\model-generate-test");
	}

	@Test
	public void generate() throws PDMResolverException, ExtjsGenerateException, IOException {
		List<GModelAndTable> modelAndTables = ModelGenerateSupport.pdmResolver.resolve(
				FileUtilsE.getFile("F:\\developer\\PowerDesigner\\数模\\model-generate-test\\generate-test.pdm"));
		for (GModelAndTable gModelAndTable : modelAndTables) {
			extjsGenerator2.generate(gModelAndTable,
					FileUtilsE.createNewFileOverride("F:\\developer\\PowerDesigner\\数模\\model-generate-test",
							gModelAndTable.getModelClassSimpleName() + ".js"));
		}
	}

}
