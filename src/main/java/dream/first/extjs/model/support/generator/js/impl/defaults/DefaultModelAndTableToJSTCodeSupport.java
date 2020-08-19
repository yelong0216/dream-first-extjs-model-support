/**
 * 
 */
package dream.first.extjs.model.support.generator.js.impl.defaults;

import java.util.Date;
import java.util.List;

import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.support.generator.GModelAndTable;

/**
 * @since 2.0
 */
public class DefaultModelAndTableToJSTCodeSupport implements ModelAndTableToJSTCodeSupport {

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
		StringBuilder classFields = new StringBuilder();// model fields
		StringBuilder gridColumns = new StringBuilder("Co.gridRowNumberer(),");// columns
		int columnWidth = 1000 / useableColumnCount;
		for (FieldAndColumn fieldAndColumn : modelAndTable.getFieldAndColumns()) {
			classFields.append("\"" + fieldAndColumn.getFieldName() + "\",");
			gridColumns.append("\n\t\t{header : \"" + fieldAndColumn.getColumnName() + "\", dataIndex : \""
					+ fieldAndColumn.getFieldName() + "\", width : " + columnWidth + ", hidden : "
					+ fieldAndColumn.isPrimaryKey());
			gridColumns.append("},");
		}
		classFields.deleteCharAt(classFields.length() - 1);
		gridColumns.deleteCharAt(gridColumns.length() - 1);

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
					"{\n\t\tlayout : \"column\",\n\t\tborder : false,\n\t\tbodyCls : \"panel-background-color\",\n\t\titems : [");
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

	protected String generateFormField(FieldAndColumn fieldAndColumn) {
		String tab = "\t\t\t\t";
		String fieldLabel = fieldAndColumn.getColumnName();
		StringBuilder formField = new StringBuilder();
		formField.append("{\n" + tab + "xtype : \"" + getFormFieldType(fieldAndColumn.getFieldType()) + "\",");
		formField.append("\n" + tab + "id : \"" + fieldAndColumn.getFieldName() + "\",");
		formField.append("\n" + tab + "name : \"model." + fieldAndColumn.getFieldName() + "\",");
		formField.append("\n" + tab + "fieldLabel : \"" + fieldLabel + "\",");
		formField.append("\n" + tab + "allowBlank : " + fieldAndColumn.isAllowBlank() + ",");
		if (!fieldAndColumn.isAllowBlank()) {
			formField.append("\n" + tab + "blankText : \"" + fieldLabel + "为必填项！\",");
		}
		formField.append("\n" + tab + "editable : true,");// 可编辑
		formField.append("\n" + tab + "readOnly : false,");// 是否只读

		Long maxLength = fieldAndColumn.getMaxLength();
		if (null != maxLength && maxLength < Integer.MAX_VALUE) {
			formField.append("\n" + tab + "maxLength: " + maxLength + ",");
			formField.append("\n" + tab + "maxLengthText: \"" + fieldLabel + "最多" + maxLength + "个字！\",");
			formField.append("\n" + tab + "enforceMaxLength: false,");
		}

		Long minLength = fieldAndColumn.getMinLength();
		if (null != minLength && minLength != 0) {
			formField.append("\n" + tab + "minLength: " + fieldAndColumn.getMinLength() + ",");
			formField.append("\n" + tab + "minLengthText: \"" + fieldLabel + "最少" + minLength + "个字！\",");
		}
		if (fieldAndColumn.getFieldType().isAssignableFrom(Date.class)) {
			formField.append("\n" + tab + "format: Co.dateFormat,");
		}
		formField.deleteCharAt(formField.length() - 1);
		formField.append("\n\t\t\t}]\n\t\t},");
		return formField.toString();
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

}
