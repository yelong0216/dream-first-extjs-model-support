package dream.first.extjs.model.support.generator.js.impl.defaults._2;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.FieldAndColumnType;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.support.generator.GModelAndTable;

import dream.first.extjs.model.support.generator.js.impl.defaults.JSTCode;
import dream.first.extjs.model.support.generator.js.impl.defaults.ModelAndTableToJSTCodeSupport;

/**
 * @since 2.0
 */
public class DefaultModelAndTableToJSTCodeSupport2 implements ModelAndTableToJSTCodeSupport {

	@Override
	public JSTCode toJSTCode(GModelAndTable modelAndTable) {
		JSTCode code = new JSTCode();
		String modelClassName;
		String className;
		modelClassName = modelAndTable.getModelClassSimpleName();
		className = modelAndTable.getModelClassName();
		code.setClassNameWithPackage(className);
		code.setClassName(modelClassName);
		code.setClassNameLowerPrefix(
				modelClassName.substring(0, 1).toLowerCase() + modelClassName.substring(1, modelClassName.length()));// 小写的类名
		code.setClassNameLowerCase(modelClassName.toLowerCase());
		code.setClassNameUpperCase(modelClassName.toUpperCase());
		// 请求前缀
		String classNameActionInvocation = "/" + code.getClassNameLowerPrefix();
		code.setClassNameActionInvocation(classNameActionInvocation);
		code.setTableName(modelAndTable.getTableName());
		boolean singleColumn = false;
		int useableColumnCount = modelAndTable.getFieldAndColumns().size();
		String classFields = generateClassFields(modelAndTable);
		String gridColumns = generateGridColumns(modelAndTable);

		// 表单
		String formItems = generateForm(modelAndTable);

		code.setClassFields(classFields.toString());
		code.setGridColumns(gridColumns.toString());
		code.setFormItems(formItems.toString());

		code.setFormWindowWidth(Integer.valueOf(singleColumn ? 450 : 650));
		int totalRow = singleColumn ? useableColumnCount : useableColumnCount / 2 + useableColumnCount % 2;
		int wHeight = singleColumn ? 120 : 150;
		if (totalRow > 1) {
			wHeight = singleColumn ? totalRow * 30 + 60 : wHeight + (totalRow - 1) * 30;
		}
		code.setFormWindowHeight(Integer.valueOf(wHeight));
		return code;
	}

	protected String getFormFieldType(Class<?> fieldType) {
		if (fieldType.isAssignableFrom(Number.class)) {
			return "numberfield";
		}
		if (fieldType.isAssignableFrom(Date.class)) {
			return "datefield";
		}
		return "textfield";
	}

	public String generateClassFields(ModelAndTable modelAndTable) {
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns(FieldAndColumnType.ORDINARY,
				FieldAndColumnType.PRIMARYKEY, FieldAndColumnType.EXTEND);
		return fieldAndColumns.stream().map(FieldAndColumn::getColumn).map(x -> "\"" + x + "\"")
				.collect(Collectors.joining(","));
	}

	@SuppressWarnings("unchecked")
	public String generateGridColumns(ModelAndTable modelAndTable) {
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns(FieldAndColumnType.ORDINARY,
				FieldAndColumnType.PRIMARYKEY, FieldAndColumnType.EXTEND);
		List<Object> columns = new ArrayList<Object>();
		columns.add("Co.gridRowNumberer()");
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			columns.add(buildColumn(fieldAndColumn));
		}
		StringBuilder gridColumns = new StringBuilder();// columns
		columns.forEach(x -> {
			gridColumns.append("\n\t\t");
			if (x instanceof Map) {
				// gridColumns.append(gson.toJson(x));
				Map<String, Object> columnAttributes = (Map<String, Object>) x;
				gridColumns.append("{");
				columnAttributes.forEach((k, v) -> {
					gridColumns.append("\"" + k + "\"" + " : " + v + " , ");
				});
				gridColumns.delete(gridColumns.length() - 3, gridColumns.length());
				gridColumns.append("}");
			} else {
				gridColumns.append(x);
			}
			gridColumns.append(",");
		});
		gridColumns.delete(0, 3);
		gridColumns.deleteCharAt(gridColumns.length() - 1);
		return gridColumns.toString();
	}

	public Map<String, Object> buildColumn(FieldAndColumn fieldAndColumn) {
		Map<String, Object> columnAttribute = new LinkedHashMap<String, Object>();
		columnAttribute.put("header", wdqm(fieldAndColumn.getColumnName()));
		columnAttribute.put("dataIndex", wdqm(fieldAndColumn.getFieldName()));
		columnAttribute.put("width", 100);// 默认宽度100
		columnAttribute.put("hidden", fieldAndColumn.isPrimaryKey());
		columnAttribute.put("sortable", false);// 默认不支持排序
		return columnAttribute;
	}

	public String generateForm(ModelAndTable modelAndTable) {
		// 表单
		StringBuilder formItems = new StringBuilder();
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		int fieldAndColumnSize = fieldAndColumns.size();
		outer0: for (int i = 0; i < fieldAndColumns.size();) {
			FieldAndColumn fieldAndColumn = fieldAndColumns.get(i);
			if (fieldAndColumn.isPrimaryKey()) {
				++i;
				continue;
			}
			if (fieldAndColumn.isExtend()) {
				++i;
				continue;
			}
			String cw = null;
			formItems.append(
					"{\n\t\tlayout : \"column\",\n\t\tborder : false,\n\t\tbodyCls : \"panel-background-color\",\n\t\tpadding : defaultPadding,\n\t\titems : [");
			for (int j = 1; j <= 2; j++) {
				cw = ".5";
				formItems.append("{\n\t\t\tcolumnWidth : " + cw
						+ ",\n\t\t\tborder : false,\n\t\t\tbodyCls : \"panel-background-color\",\n\t\t\tlayout : \"form\",\n\t\t\titems : [");
				formItems.append(generateFormField(fieldAndColumn));
				if (j != 2 && i != fieldAndColumnSize - 1) {
					++i;
					fieldAndColumn = fieldAndColumns.get(i);
					if (fieldAndColumn.isPrimaryKey()) {
						formItems.append("]\n\t},");
						continue outer0;
					}
					if (fieldAndColumn.isExtend()) {
						formItems.append("]\n\t},");
						continue outer0;
					}
				} else {
					break;
				}
			}
			formItems.deleteCharAt(formItems.length() - 1);
			formItems.append("]\n\t},");
			++i;
		}
		// 表单隐藏字段
		for (FieldAndColumn primaryKey : modelAndTable.getPrimaryKeys()) {
			formItems.append("{\n\t\txtype : \"hiddenfield\",");
			formItems.append("\n\t\tid : \"" + primaryKey.getFieldName() + "\",");
			formItems.append("\n\t\tname : \"model." + primaryKey.getFieldName() + "\"\n\t},");
		}
		formItems.deleteCharAt(formItems.length() - 1);
		return formItems.toString();
	}

	public String generateFormField(FieldAndColumn fieldAndColumn) {
		String tab = "\t\t\t\t";
		StringBuilder formField = new StringBuilder();
		Map<String, Object> formFieldAttributes = buildFormField(fieldAndColumn);
		formField.append("{");
		formFieldAttributes.forEach((k, v) -> {
			formField.append("\n" + tab + k + " : " + v + ",");
		});
		formField.deleteCharAt(formField.length() - 1);
		formField.append("\n\t\t\t}]\n\t\t},");
		return formField.toString();
	}

	public Map<String, Object> buildFormField(FieldAndColumn fieldAndColumn) {
		Map<String, Object> formFieldAttributes = new LinkedHashMap<String, Object>();
		String fieldLabel = fieldAndColumn.getColumnName();
		formFieldAttributes.put("xtype", wdqm(getFormFieldType(fieldAndColumn.getFieldType())));
		formFieldAttributes.put("id", wdqm(fieldAndColumn.getFieldName()));
		formFieldAttributes.put("name", wdqm("model." + fieldAndColumn.getFieldName()));
		formFieldAttributes.put("fieldLabel", wdqm(fieldLabel));
		formFieldAttributes.put("beforeLabelTextTpl", "pointer");
		formFieldAttributes.put("labelWidth", 100);

		formFieldAttributes.put("allowBlank", fieldAndColumn.isAllowBlank());
		if (!fieldAndColumn.isAllowBlank()) {
			formFieldAttributes.put("blankText", wdqm(fieldLabel + "为必填项！"));
		}
		formFieldAttributes.put("editable", true);
		formFieldAttributes.put("readOnly", false);

		Long maxLength = fieldAndColumn.getMaxLength();
		if (null != maxLength && maxLength <= Integer.MAX_VALUE) {
			formFieldAttributes.put("maxLength", maxLength);
			formFieldAttributes.put("maxLengthText", wdqm(fieldLabel + "最多" + maxLength + "个字！"));
			formFieldAttributes.put("enforceMaxLength", false);
		}
		Long minLength = fieldAndColumn.getMinLength();
		if (null != minLength) {
			formFieldAttributes.put("maxLength", minLength);
			formFieldAttributes.put("maxLengthText", wdqm(fieldLabel + "最少" + minLength + "个字！"));
		}
		if (fieldAndColumn.getFieldType().isAssignableFrom(Date.class)) {
			formFieldAttributes.put("format", wdqm("Co.dateFormat"));
		}
		return formFieldAttributes;
	}

	/**
	 * 通过双引号包裹起来
	 * 
	 * @return \"str\"
	 */
	private static String wdqm(CharSequence str) {
		return "\"" + str + "\"";
	}

	/**
	 * 通过双引号包裹起来
	 * 
	 * @return \"str\"
	 */
	public static String wrapDoubleQuotationMarks(CharSequence str) {
		return "\"" + str + "\"";
	}

}
