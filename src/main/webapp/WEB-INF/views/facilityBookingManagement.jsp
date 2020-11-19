
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Facility Booking Registration</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Manage Facility Bookings</h1>
		<hr />

		<h3>Create Facility Booking</h3>

		<form:form
			action="${pageContext.request.contextPath}/createFacilityBooking/"
			cssClass="form-horizontal" method="post"
			modelAttribute="facilityBooking">

			<div class="form-group">
				<label for="facilityName" class="col-md-3 control-label">Facility</label>
				<div class="col-md-9">
					<form:input path="facilityName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="numberOfPeople" class="col-md-3 control-label">Number
					of People</label>
				<div class="col-md-9">
					<form:input type="number" path="numberOfPeople"
						cssClass="form-control" required="required" value="" />
				</div>
			</div>
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			
			<div class="form-group">
				<label for="correspBookingId" class="col-md-3 control-label">Customer/Booking</label>
				<div class="col-md-9">
					<select name="correspBookingId">
						<c:forEach items="${bookingList}" var="booking">
							<option value="${booking.bookingId}">${booking.bookingId}
								Customer: ${booking.customerUsername} (${booking.bookingDateStart} - ${booking.bookindDateEnd})</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="date" class="col-md-3 control-label">Date</label>
				<div class="col-md-9">
					<form:input path="date" type="date" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="timeStart" class="col-md-3 control-label">Start
					Time</label>
				<div class="col-md-9">
					<form:input path="timeStart" type="time" min='09:00' max='23:00' step='3600' cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="timeEnd" class="col-md-3 control-label">End Time</label>
				<div class="col-md-9">
					<form:input path="timeEnd" type="time" min='09:00' max='23:00' step='00:30:00' cssClass="form-control"
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
				<th>Facility Booking Id</th>
				<th>Facility</th>
				<th>Number of People</th>
				<th>Customer</th>
				<th>Corresp. Booking</th>
				<th>Date</th>
				<th>Time Start</th>
				<th>Time End</th>
				<th colspan="2">Options</th>
			</tr>
			<c:forEach var="facilityBooking" items="${facilityBookingList}">
				<tr class="dataRows">
					<td>${facilityBooking.facilityBookingId}</td>
					<td>${facilityBooking.facilityName}</td>
					<td>${facilityBooking.numberOfPeople}</td>
					<td>${facilityBooking.customerUsername}</td>
					<td>${facilityBooking.correspBookingId}</td>
					<td>${facilityBooking.date}</td>
					<td>${facilityBooking.timeStart}</td>
					<td>${facilityBooking.timeEnd}</td>
					<td><a
						href="${pageContext.request.contextPath}/editFacilityBooking/?id=${facilityBooking.facilityBookingId}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteFacilityBooking/?id=${facilityBooking.facilityBookingId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>



	</div>
</body>
</html>