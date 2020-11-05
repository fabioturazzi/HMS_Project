
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Bookings</title>
<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">My Bookings</h1>
		<hr />
		<c:if test="${ message != null }">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>
		<table class="table table-striped table-bordered">

			<tr class="dataHeader">
				<td>Booking Id</td>
				<td hidden>Room Number</td>
				<td hidden>Customer Username</td>
				<td>Room Type</td>
				<td hidden>Status</td>
				<td hidden>Paid</td>
				<td>Start Date</td>
				<td>End Date</td>
				<td hidden>Check-in Date</td>
				<td hidden>Check-out Date</td>
				<td hidden>Payment Date</td>
				<td>Booking Creation Date</td>
				<td>Total Costs</td>
				<td> Delete Booking</td>
			</tr>
			<c:forEach var="booking" items="${bookings}">
				<tr class="dataRows">
					<td>${booking.bookingId}</td>
					<td hidden>${booking.roomNumber}</td>
					<td hidden>${booking.customerUsername}</td>
					<td>${booking.roomType}</td>
					<td hidden>${booking.status}</td>
					<td hidden>${booking.paid}</td>
					<td>${booking.bookingDateStart}</td>
					<td>${booking.bookindDateEnd}</td>
					<td hidden>${booking.checkinDate}</td>
					<td hidden>${booking.checkoutDate}</td>
					<td hidden>${booking.paymentDate}</td>
					<td>${booking.dateOfCreation}</td>
					<td>$${booking.totalCost}</td>
					<td><a class="btn btn-primary"
						href="${pageContext.request.contextPath}/deleteBookingCustomer/?bookingId=${booking.bookingId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>
	</div>
</body>
</html>