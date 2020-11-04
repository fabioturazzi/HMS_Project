
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
<script src="<c:url value="/static/js/searchRoomPage.js" />"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Room Search</h1>
		<hr />
		<div>

			<form:form action="${pageContext.request.contextPath}/roomSearch/"
				cssClass="form-horizontal searchForm" method="post"
				modelAttribute="roomType">

				<c:if test="${ errorMessage !=null }">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>

				<div class="form-group">
					<label for="startDateFormControl" id="startDate"
						class="col-md-3 control-label">Start Date</label>
					<div class="col-md-9">
						<form:input type="date" path="startDateFormControl"
							cssClass="form-control" required="required" />
					</div>
				</div>

				<div class="form-group">
					<label for="endDateFormControl" id="endDate"
						class="col-md-3 control-label">End Date</label>
					<div class="col-md-9">
						<form:input type="date" path="endDateFormControl"
							cssClass="form-control" required="required" />
					</div>
				</div>

				<div class="form-group">
					<label for="capacity" id="capacity" class="col-md-3 control-label">Number
						of People</label>
					<div class="col-md-9">
						<form:input type="number" path="capacity" value="1"
							cssClass="form-control" required="required" />
					</div>
				</div>

				<div class="form-group">
					<label for="roomType" class="col-md-3 control-label">Room
						Type</label>
					<div class="col-md-9">
						<form:select path="roomType" cssClass="form-control" value="">
							<form:options items="${roomTypesListItems}"></form:options>
						</form:select>
					</div>
				</div>

				<div class="form-group checkboxGroup">
					<label for="amenities" class="col-md-3 control-label">Amenities</label>
					<div class="checkboxDiv">
						<form:checkboxes element="span class='checkboxItems' id=''"
							cssClass="checkboxItems" items="${amenitiesListSearch}"
							path="amenities" />
					</div>
				</div>

				<div class="form-group searchButtons">
					<!-- Button -->
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" id="submitSearch" class="btn btn-primary">Apply
							Search Filters</button>
					</div>
					<!-- Button -->
					<div class="col-md-offset-3 col-md-9">
						<a id="cleanFilterDiv"
							href="${pageContext.request.contextPath}/roomSearch"
							id="cleanFilterButton" class="btn btn-danger">Clean Search
							Filters</a>
					</div>
				</div>
				<div class="form-group"></div>
			</form:form>
			<c:if test="${ message != null }">
				<div class="alert alert-success successMessage" id="successMessage"
					role="alert">${message}</div>
			</c:if>
			<span id="flag" hidden>false</span>
			<table class="table table-striped table-bordered">
				<tr class="dataHeader">
					<td>Photos</td>
					<td>Room Type</td>
					<td>Amenities</td>
					<td>Capacity</td>
					<td>Daily Price</td>
					<td class="totalCostHead">Total Cost</td>
					<td class="availRoomsHead">Rooms Available</td>
					<td class="bookRoomsHead">Book</td>
				</tr>

				<c:forEach var="roomType" items="${roomTypeList}" varStatus="loop">
					<tr class="dataRows">
						<td hidden>${roomType.roomTypeId}</td>
						<td><c:forEach var="photo" items="${roomType.photos}">
						${photo} |
					</c:forEach></td>
						<td>${roomType.roomType}</td>
						<td><c:forEach var="amenity" items="${roomType.amenities}">
						${amenity} |
					</c:forEach></td>
						<td>${roomType.capacity}</td>
						<td><fmt:formatNumber type="currency" currencySymbol="$"
								value="${roomType.dailyPrice}" /></td>
						<td class="totalCost"><fmt:formatNumber type="currency"
								currencySymbol="$" value="${totalCost.get(loop.index)}" /></td>
						<td class="availRooms">${availableRooms.get(loop.index)}</td>
						<td class="bookRoomsCell"><a class="bookRooms"
							href="${pageContext.request.contextPath}/roomBooking/?roomType=${roomType.roomType}">Book
								a Room</a></td>
					</tr>
				</c:forEach>

			</table>

		</div>


	</div>
</body>
</html>