Coe.initialize();
Co.initialize();

var pointer = '<span style="color:red;font-weight:bold">*</span>';
//默认的内边距
var defaultPadding = "0 0 5 0";

var API = {
		save${code.className} : rootPath + "${code.classNameActionInvocation}/save",
		query${code.className} : rootPath + "${code.classNameActionInvocation}/query",
		delete${code.className} : rootPath + "${code.classNameActionInvocation}/delete",
		retrieve${code.className} : rootPath + "${code.classNameActionInvocation}/retrieve",
		detail${code.className} : rootPath + "${code.classNameActionInvocation}/detail"
	};

var ${code.classNameLowerPrefix}Grid;
var ${code.classNameLowerPrefix}GridStore;
var ${code.classNameLowerPrefix}Form;
var ${code.classNameLowerPrefix}FormWindow;

Ext.onReady(function() {
		
	//============================ Model =========================
	Co.defineModel("${code.className}", [${code.classFields}]);
	//============================ Store =========================
	${code.classNameLowerPrefix}GridStore = Co.gridStore("${code.classNameLowerPrefix}GridStore", API.query${code.className}, "${code.className}", {
		autoLoad : false,
		output : "${code.classNameLowerPrefix}Tbar",
		sorters : [{
			property : "createTime",
			direction : "desc"
		}]
	});
		
	//============================ View =========================
	var ${code.classNameLowerPrefix}Tbar = Co.toolbar("${code.classNameLowerPrefix}Tbar", [{
			type : "+", 
			handler : add${code.className},
			showAtContextMenu : true
		},"->",{
			type : "@",
			handler : search${code.className},
			searchField : [],
			searchEmptyText : []
		}
	]);
	
	var ${code.classNameLowerPrefix}Columns = [
		${code.gridColumns},
		{header : "操作", dataIndex : "", width : 100,align : "center", hidden : false,renderer : function(v,obj){
			var record = obj.record;
			var id = record.data.id;
			var operator = "";
			operator += "<span style='cursor:pointer;color: blue;' onclick='edit${code.className}(\""+id+"\")'>修改</span>";
			operator += "&nbsp;&nbsp;&nbsp;";
			operator += "<span style='cursor:pointer;color: blue;' onclick='delete${code.className}(\""+id+"\")'>删除</span>";
			return operator;
		}},
	];
	
	${code.classNameLowerPrefix}Grid = Co.grid("${code.classNameLowerPrefix}Grid", ${code.classNameLowerPrefix}GridStore, ${code.classNameLowerPrefix}Columns, ${code.classNameLowerPrefix}Tbar, null, {
		listeners : {
			itemdblclick : function(view, record) {
//				edit${code.className}(record.data.id);
				show${code.className}Detail(record.data.id);
			}
		}
	});
	
	Co.load(${code.classNameLowerPrefix}GridStore);
	
	${code.classNameLowerPrefix}Form = Co.form(API.save${code.className}, [${code.formItems}]);
	
	${code.classNameLowerPrefix}FormWindow = Co.formWindow("新增", ${code.classNameLowerPrefix}Form, ${code.formWindowWidth}, ${code.formWindowHeight}, "fit", {
		okHandler : save${code.className}
	});
	
	Ext.create("Ext.container.Viewport", {
		layout : "fit",
		items : ${code.classNameLowerPrefix}Grid
	});
	//============================ Function =========================
	function add${code.className}() {
		Co.resetForm(${code.classNameLowerPrefix}Form, true);
		${code.classNameLowerPrefix}FormWindow.setTitle("新增");
		${code.classNameLowerPrefix}FormWindow.show();
	}
	
	function save${code.className}() {
		Co.formSave(${code.classNameLowerPrefix}Form, function(form, action){
			Co.alert("保存成功！", function(){
				${code.classNameLowerPrefix}FormWindow.hide();
				Co.reload(${code.classNameLowerPrefix}GridStore);
			});
		});
	}
	
	function search${code.className}() {
		Co.load(${code.classNameLowerPrefix}GridStore);
	}
});

function edit${code.className}(modelId) {
	if(modelId){
		Co.formLoadWithoutGrid(${code.classNameLowerPrefix}Form, API.retrieve${code.className},{"model.id":modelId}, function(result, opts, selectedId){
			if (true === result.success) {
				${code.classNameLowerPrefix}FormWindow.setTitle("修改");
				${code.classNameLowerPrefix}FormWindow.show();
			} else {
				Co.showError(result.msg || "数据加载失败！");
			}
		});
	}
}
	
function delete${code.className}(modelId) {
	if(modelId){
		Co.confirm("确定删除吗？", function(btn){
			if (btn == "yes") {
				Co.request(API.delete${code.className},{"deleteIds":modelId}, function(result){
					if (result.success === true) {
						Co.alert("删除成功！", function(){
							Co.reload(${code.classNameLowerPrefix}GridStore);
						});
					} else {
						Co.alert(result.msg);
					}
				});
			}
		});
	}
}

//===================详情====================
var ${code.classNameLowerPrefix}DetailWindow = Co.window("详情",[{
	html : "<iframe id = '${code.classNameLowerPrefix}DetailIframe' scrolling = 'auto' frameborder = '0' width = '100%' height = '100%' src =''></iframe>"
}],"80%","80%","fit",{
	listeners : {
		show : function(tab,opts){
			
		}
	},
	closeable : false
})

function show${code.className}Detail(modelId){
	if(modelId){
		${code.classNameLowerPrefix}DetailWindow.show();
		document.getElementById("${code.classNameLowerPrefix}DetailIframe").src = API.detail${code.className}+"?id="+modelId;
	} else {
		Co.showError("请选择记录");
	}
}