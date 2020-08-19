/**
 * 
 */
package dream.first.extjs.model.support.generator.js;

import java.io.File;

import org.yelong.core.model.support.generator.GFieldAndColumnInterceptor;
import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.core.model.support.generator.GModelAndTableInterceptor;

/**
 * @since 2.0
 */
public interface ExtjsGenerator {

	/**
	 * 执行生成
	 * 
	 * @param modelAndTable 模型表
	 * @param jsFile        生成后存放的文件
	 * @throws ExtjsGenerateException 生成异常
	 */
	void generate(GModelAndTable modelAndTable, File jsFile) throws ExtjsGenerateException;

	/**
	 * 添加模型表过滤器
	 * 
	 * @param gModelAndTableFilter 模型表过滤器
	 * @since 1.0.1
	 */
	void addModelAndTableInterceptor(GModelAndTableInterceptor modelAndTableInterceptor);

	/**
	 * 添加字段列过滤器
	 * 
	 * @param gFieldAndColumnFilter 字段列过滤器
	 * @since 1.0.1
	 */
	void addFieldAndColumnInterceptor(GFieldAndColumnInterceptor fieldAndColumnInterceptor);

}
