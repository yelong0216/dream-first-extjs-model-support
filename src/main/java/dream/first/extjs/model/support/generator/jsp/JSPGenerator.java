/**
 * 
 */
package dream.first.extjs.model.support.generator.jsp;

import java.io.File;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.support.generator.GModelAndTable;

/**
 * @since 2.0
 */
public interface JSPGenerator {

	/**
	 * 生成JSP文件
	 * 
	 * @param modelAndTable 模型表
	 * @param jspFile jsp文件存放的目录
	 * @throws JSPGenerateException jsp文件生成异常
	 */
	void generate(GModelAndTable modelAndTable, File jspFile) throws JSPGenerateException;

	/**
	 * 生成JSP文件
	 * 
	 * @param modelClass model class
	 * @param jspFile jsp文件存放的目录
	 * @throws JSPGenerateException jsp文件生成异常
	 */
	void generate(Class<? extends Modelable> modelClass, File jspFile) throws JSPGenerateException;

}
