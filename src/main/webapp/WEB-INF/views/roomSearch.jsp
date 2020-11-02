
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Room Search</h1>
		<hr />
		<div class="form-group searchBar form-horizontal">
			<label for="fName" class="col-md-3 control-label">Search
				Entries:</label>
			<div class="col-md-9">
				<input class="form-control searchInput" id="searchInput"
					required="required" value="" />
			</div>
		</div>

		<div>

			<table class="table table-striped table-bordered">

				<form:form
					action="${pageContext.request.contextPath}/createRoomType/"
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
						<label for="amenities" class="col-md-3 control-label">Amenities</label>
						<div class="checkboxItems">
							<form:checkboxes element="span class='checkboxItems'"
								cssClass="checkboxItems" checkedItems="${roomType.amenities}"
								items="${amenitiesList}" path="amenities" />
						</div>
					</div>
					<div class="form-group">
						<label for="capacity" class="col-md-3 control-label">Capacity</label>
						<div class="col-md-9">
							<form:input path="capacity" cssClass="form-control"
								required="required" value="" />
						</div>
					</div>

					<div class="form-group">
						<!-- Button -->
						<div class="col-md-offset-3 col-md-9">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>

				</form:form>

				<tr class="dataHeader">
					<td>Room Type Id</td>
					<td>Room Type</td>
					<td>Photos</td>
					<td>Daily Price</td>
					<td>Amenities</td>
					<td>Capacity</td>
					<td>Rooms Available</td>
					<td></td>
				</tr>

				<c:forEach var="roomType" items="${roomTypeList}" varStatus="loop">
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
						<td>${roomType.capacity}</td>
						<td>${availableRooms.get(loop.index)}</td>
						<td><a href="">Book Room</a></td>
					</tr>
				</c:forEach>

			</table>

		</div>


	</div>
</body>
</html>