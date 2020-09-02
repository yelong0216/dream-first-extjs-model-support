/**
 * 
 */
package dream.first.extjs.model.support.test.generator.js;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.yelong.commons.io.FileUtilsE;
import org.yelong.commons.util.ListUtilsE;
import org.yelong.core.model.support.generator.GFieldAndColumnWrapper;
import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.core.model.support.generator.pdm.PDMResolverException;
import org.yelong.core.model.support.generator.support.ModelGenerateSupport;

import dream.first.extjs.model.support.generator.js.ExtJSGenerator;
import dream.first.extjs.model.support.generator.js.impl.defaults.v1.DefaultExtJSGenerator_v1;
import dream.first.extjs.model.support.generator.js.impl.defaults.v2.DefaultExtJSGenerator_v2;

/**
 *
 */
public class ExtjsGeneratorTest {

	public static ExtJSGenerator extjsGenerator = new DefaultExtJSGenerator_v1();

	public static ExtJSGenerator extjsGenerator2 = new DefaultExtJSGenerator_v2();

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
	public void generateModle() throws Exception {
		ModelGenerateSupport.generatePOJOModel(
				"F:\\developer\\PowerDesigner\\数模\\model-generate-test\\generate-test.pdm",
				"F:\\developer\\PowerDesigner\\数模\\model-generate-test");
	}

	@Test
	public void generate() throws Exception {
		List<GModelAndTable> modelAndTables = ModelGenerateSupport.pdmResolver.resolve(
				FileUtilsE.getFile("F:\\developer\\PowerDesigner\\数模\\model-generate-test\\generate-test.pdm"));
		for (GModelAndTable gModelAndTable : modelAndTables) {
			extjsGenerator2.generate(gModelAndTable,
					FileUtilsE.createNewFileOverride("F:\\developer\\PowerDesigner\\数模\\model-generate-test",
							gModelAndTable.getModelClassSimpleName() + ".js"));
		}
	}

}
