
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
		<h1 id="pageTitle">Manage Room Types</h1>
		<hr />
		<div class="form-group searchBar form-horizontal">
			<label for="fName" class="col-md-3 control-label">Search
				Entries:</label>
			<div class="col-md-9">
				<input class="form-control searchInput" id="searchInput"
					required="required" value="" />
			</div>
		</div>


		<c:if test="${ message != null }">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>
		<table class="table table-striped table-bordered">

			<tr class="dataHeader">
				<td>Room Type Id</td>
				<td>Room Type</td>
				<td>Photos</td>
				<td>Daily Price</td>
				<td>Amenities</td>
				<td></td>
				<td></td>
			</tr>

			<c:forEach var="roomType" items="${roomTypeList}">
				<tr class="dataRows">
					<td>${roomType.roomTypeId}</td>
					<td>${roomType.roomType}</td>
					<td><c:forEach var="photo" items="${roomType.photos}">
						${photo} |
					</c:forEach></td>
					<td>${roomType.dailyPrice}</td>
					<td><c:forEach var="amenity" items="${roomType.amenities}">
						${amenity} |
					</c:forEach></td>
					<td><a
						href="${pageContext.request.contextPath}/editRoomType/?id=${roomType.roomTypeId}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteRoomType/?id=${roomType.roomTypeId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>

		<h3>Create Room Type</h3>

		<form:form action="${pageContext.request.contextPath}/createRoomType/"
			cssClass="form-horizontal" method="post" modelAttribute="roomType">

			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
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