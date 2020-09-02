<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=10">
<title>详情</title>

<script src="${r"${"}STATIC_RESOURCES_ROOT_PATH${r"}"}/js/details/jq.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
	src="${r"${"}STATIC_RESOURCES_ROOT_PATH${r"}"}/js/details/vue.js"></script>
<link rel="stylesheet"
	href="${r"${"}STATIC_RESOURCES_ROOT_PATH${r"}"}/js/details/bootstrap.css">
<script src="${r"${"}STATIC_RESOURCES_ROOT_PATH${r"}"}/js/details/bootstrap.js"
	type="text/javascript" charset="utf-8"></script>
<jsp:include page="/common/commonlib.jsp"></jsp:include>
<link rel="stylesheet"
	href="${r"${"}STATIC_RESOURCES_ROOT_PATH${r"}"}/js/details/commonDetail.css">

<style type="text/css">
.col-2 {
	text-align: right;
	padding: 0px;
	max-width: 16.666667% !important;
	margin-right: 5px;
}

.col-4 {
	max-width: 29.666667% !important;
}

.col-10 {
	max-width: 79.666667% !important;
}

.row .col-4, .row .col-10 {
	font-size: 13px;
	color: #000;
	word-wrap: break-word;
	margin-right: 27px;
}

.content {
	padding: 25px;
	min-width: 975px;
}

p#q {
	text-align: left;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<section>
			<div style="background-color: white;">
				<div class="content">
					<div class="row title" style="margin: 0px;" data-toggle="collapse">
						<p class="col-2" id="q">
							<span class="spanIcon"></span> <span class="spanTitle">基本信息</span>
						</p>
					</div>
					<div class="slider collapse show" id="multiCollapseExample1">
						<#assign count = 0>
						<#list model.modelFields as modelField>
						<#if count == 0 >
						<div class="row">
						</#if>
							<p class="col-2">${modelField.fieldAndColumn.columnName}：</p>
							<p class="col-4">
							<c:out value="${r"${"}${model.modelNamePrefixLowerCase}.${modelField.code}${r"}"}" default="--"></c:out>
							</p>
						<#assign count = count + 1>
						<#if count == 2 || !modelField_has_next>
						<#assign count = 0>
						</div>
						</#if>
						</#list>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>

