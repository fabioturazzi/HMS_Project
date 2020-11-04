
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		<h1 id="pageTitle">Add RoomType Photos</h1>
		<hr />

		<c:if test="${ message != null }">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>

		<h3>Add Photo for Room Type</h3>

		<form action="${pageContext.request.contextPath}/uploadPhotos/"
			class="form-horizontal" method="post" enctype="multipart/form-data">

			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>

			<div class="form-group">
				<label for="roomTypeId" class="col-md-3 control-label">Room
					Type Id</label>
				<div class="col-md-9">
					<input name="roomTypeId" class="form-control" required="required"
						value="${roomType.roomTypeId}" readonly="readonly" />
				</div>
			</div>

			<div class="form-group">
				<label for="roomType" class="col-md-3 control-label">Room
					Type</label>
				<div class="col-md-9">
					<input name="roomType" class="form-control" required="required"
						value="${roomType.roomType}" readonly="readonly" />
				</div>
			</div>

			<div class="form-group">
				<label for="photos" class="col-md-3 control-label">Existing Photos</label>
				<div class="col-md-9">
					<input name="photos" class="form-control"
						value="<c:if test="${empty roomType.photos[0]}">(No photos available)</c:if><c:if test="${not empty roomType.photos[0]}"><c:forEach var="photo" items="${roomType.photos}" varStatus="loop">${photo}<c:if test="${!loop.last}"> | </c:if></c:forEach></c:if>"
						readonly="readonly" />
				</div>
			</div>
			<div class="form-group">
				<label for="uploadPhotos" class="col-md-3 control-label">Upload
					Photos</label>
				<div class="col-md-9">
					<input name="uploadPhotos" type="file" class="form-control"
						required="required" />
				</div>
			</div>


			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>

		</form>

	</div>
</body>
</html>