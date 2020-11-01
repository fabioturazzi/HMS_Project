
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Type Registration</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>
</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Edit Room Type</h1>
		<hr />
		<form:form action="${pageContext.request.contextPath}/editRoomType/"
			cssClass="form-horizontal" method="post" modelAttribute="roomType">

			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>

			<div class="form-group">
				<label for="roomTypeId" class="col-md-3 control-label">Room
					Type Id</label>
				<div class="col-md-9">
					<form:input path="roomTypeId" cssClass="form-control"
						required="required" value="" readonly="true" />
				</div>
			</div>

			<div class="form-group">
				<label for="roomType" class="col-md-3 control-label">Room
					Type</label>
				<div class="col-md-9">
					<form:input path="roomType" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="photos" class="col-md-3 control-label">Photos</label>
				<div class="col-md-9">
					<form:input path="photos" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>


			<div class="form-group">
				<label for="dailyPrice" class="col-md-3 control-label">Daily
					Price</label>
				<div class="col-md-9">
					<form:input path="dailyPrice" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="amenities" class="col-md-3 control-label">Amenities</label>
				<div class="checkboxItems">
					<form:checkboxes element="span class='checkboxItems'" cssClass="checkboxItems" checkedItems="${roomType.amenities}" items="${amenitiesList}" path="amenities" />
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