
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Registration</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>
</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Edit Room</h1>
		<hr />
		<form:form action="${pageContext.request.contextPath}/editRoom/"
			cssClass="form-horizontal" method="post" modelAttribute="room">

			<div class="form-group">
				<label for="roomId" class="col-md-3 control-label">Room Id</label>
				<div class="col-md-9">
					<form:input path="roomId" cssClass="form-control"
						required="required" value="" readonly="true" />
				</div>
			</div>

			<div class="form-group">
				<label for="roomNumber" class="col-md-3 control-label">Room
					Number</label>
				<div class="col-md-9">
					<form:input path="roomNumber" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="roomType" class="col-md-3 control-label">Room
					Type</label>
				<div class="col-md-9">
					<form:select path="roomType" cssClass="form-control"
						required="required" value="">
						<form:options items="${roomTypesListItems}"></form:options>
					</form:select>
				</div>
			</div>
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>

			<div class="form-group">
				<label for="floor" class="col-md-3 control-label">Floor</label>
				<div class="col-md-9">
					<form:input type="number" path="floor" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="housekeepingStatus" class="col-md-3 control-label">Housekeeping
					Status</label>
				<div class="col-md-9">
					<form:select path="housekeepingStatus" cssClass="form-control"
						required="required" value="">
						<form:options items="${housekeeping}"></form:options>
					</form:select>
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn btn-primary">Submit</button>

				</div>
			</div>

		</form:form>

	</div>
</body>
</html>