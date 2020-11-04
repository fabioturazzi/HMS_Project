
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
		<h1 id="pageTitle">Manage Rooms</h1>
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
				<td>Room Id</td>
				<td>Room Number</td>
				<td>Room Type</td>
				<td>Floor</td>
				<td>Housekeeping Status</td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="room" items="${roomList}">
				<tr class="dataRows">
					<td>${room.roomId}</td>
					<td>${room.roomNumber}</td>
					<td>${room.roomType}</td>
					<td>${room.floor}</td>
					<td>${room.housekeepingStatus}</td>
					<td><a
						href="${pageContext.request.contextPath}/editRoom/?id=${room.roomId}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteRoom/?id=${room.roomId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>

		<h3>Create Room</h3>

		<form:form action="${pageContext.request.contextPath}/createRoom/"
			cssClass="form-horizontal" method="post" modelAttribute="room">

			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
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