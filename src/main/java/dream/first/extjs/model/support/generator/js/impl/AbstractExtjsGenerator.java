/**
 * 
 */
package dream.first.extjs.model.support.generator.js.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.FieldAndColumnType;
import org.yelong.core.model.support.generator.GFieldAndColumn;
import org.yelong.core.model.support.generator.GFieldAndColumnInterceptor;
import org.yelong.core.model.support.generator.GModelAndTable;
import org.yelong.core.model.support.generator.GModelAndTableInterceptor;
import org.yelong.core.model.support.generator.GModelAndTableWrapper;

import dream.first.extjs.model.support.generator.js.ExtjsGenerator;

/**
 * @since 2.0
 */
public abstract class AbstractExtjsGenerator implements ExtjsGenerator {

	protected final List<GFieldAndColumnInterceptor> fFieldAndColumnInterceptors = new ArrayList<>();

	protected final List<GModelAndTableInterceptor> gModelAndTableInterceptors = new ArrayList<>();

	@Override
	public void addFieldAndColumnInterceptor(GFieldAndColumnInterceptor fieldAndColumnInterceptor) {
		this.fFieldAndColumnInterceptors.add(fieldAndColumnInterceptor);
	}

	@Override
	public void addModelAndTableInterceptor(GModelAndTableInterceptor modelAndTableInterceptor) {
		this.gModelAndTableInterceptors.add(modelAndTableInterceptor);
	}

	/**
	 * 对模型和表、字段通过过滤器进行过滤处理
	 * 
	 * @param gModelAndTable 生成器模型表
	 * @return 过滤处理后的生成器模型表
	 */
	protected GModelAndTable execFilter(GModelAndTable gModelAndTable) {
		for (GModelAndTableInterceptor gModelAndTableInterceptor : gModelAndTableInterceptors) {
			gModelAndTable = gModelAndTableInterceptor.process(gModelAndTable);
		}
		if (CollectionUtils.isNotEmpty(fFieldAndColumnInterceptors)) {
			List<FieldAndColumn> fieldAndColumns = gModelAndTable.getFieldAndColumns();
			final List<FieldAndColumn> newGFieldAndColumns = new ArrayList<>(fieldAndColumns.size());
			for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
				for (GFieldAndColumnInterceptor gFieldAndColumnInterceptor : fFieldAndColumnInterceptors) {
					fieldAndColumn = gFieldAndColumnInterceptor.process((GFieldAndColumn) fieldAndColumn);
				}
				if (null != fieldAndColumn) {
					newGFieldAndColumns.add(fieldAndColumn);
				}
			}
			gModelAndTable = new GModelAndTableWrapper(gModelAndTable) {

				@Override
				public List<FieldAndColumn> getFieldAndColumns(FieldAndColumnType... fieldAndColumnTypes) {
					if (ArrayUtils.isEmpty(fieldAndColumnTypes)) {
						return newGFieldAndColumns;
					}
					return newGFieldAndColumns.parallelStream()
							.filter(x -> ArrayUtils.contains(fieldAndColumnTypes, x.getFieldAndColumnType()))
							.collect(Collectors.toList());
				}

				@Override
				public List<FieldAndColumn> getPrimaryKeys() {
					return getFieldAndColumns(FieldAndColumnType.PRIMARYKEY);
				}

				@Override
				public boolean existPrimaryKey() {
					return IteratorUtils.matchesAny(getFieldAndColumns().iterator(), x -> {
						return x.isPrimaryKey();
					});
				}
			};
		}
		return gModelAndTable;
	}

}
