
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking Management</title>
<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">

		<h1 id="pageTitle">Manage Bookings</h1>
		<hr />

		<h3>Create Booking</h3>

		<form:form action="${pageContext.request.contextPath}/createBooking/"
			cssClass="form-horizontal" method="post" modelAttribute="booking">

			<div class="form-group">
				<label for="roomNumber" class="col-md-3 control-label">Room
					Number</label>
				<div class="col-md-9">
					<select name="room">
						<c:forEach items="${roomList}" var="room">
							<option value="${room.roomNumber }">${room.roomNumber }
								Type: ${room.roomType }</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="customerId" class="col-md-3 control-label">Customer
					Username</label>
				<div class="col-md-9">
					<select name="customer">
						<c:forEach items="${custormersList}" var="customer">
							<option value="${customer.username}">${customer.username}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>

			<div class="form-group">
				<label for="bookingDateStart" class="col-md-3 control-label">Booking
					From</label>
				<div class="col-md-9">
					<form:input type="date" name="startDate" path="bookingDateStart"
						cssClass="form-control" required="required" />
				</div>
			</div>

			<div class="form-group">
				<label for="bookindDateEnd" class="col-md-3 control-label">Booking
					To</label>
				<div class="col-md-9">
					<form:input type="date" name="endDate" path="bookindDateEnd"
						cssClass="form-control" required="required" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="numbOfPeople" class="col-md-3 control-label">Number of People</label>
				<div class="col-md-9">
					<form:input type="number" min="1" max="10" name="numbOfPeople" path="numbOfPeople"
						cssClass="form-control" required="required" value="1"/>
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>

		</form:form>
		
		<br />
		<br />

		<h3>Search Bookings</h3>
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
				<td>Booking Id</td>
				<td>Room Number</td>
				<td>Customer Username</td>
				<td>Room Type</td>
				<td># of People</td>
				<td>Status</td>
				<td>Paid</td>
				<td>Start Date</td>
				<td>End Date</td>
				<td>Check-in Date</td>
				<td>Check-out Date</td>
				<td>Payment Date</td>
				<td>Booking Creation Date</td>
				<td>Total Costs</td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="booking" items="${bookings}">
				<tr class="dataRows">
					<td>${booking.bookingId}</td>
					<td>${booking.roomNumber}</td>
					<td>${booking.customerUsername}</td>
					<td>${booking.roomType}</td>
					<td>${booking.numbOfPeople}</td>
					<td>${booking.status}</td>
					<td>${booking.paid}</td>
					<td>${booking.bookingDateStart}</td>
					<td>${booking.bookindDateEnd}</td>
					<td>${booking.checkinDate}</td>
					<td>${booking.checkoutDate}</td>
					<td>${booking.paymentDate}</td>
					<td>${booking.dateOfCreation}</td>
					<td>$${booking.totalCost}</td>
					<td><a
						href="${pageContext.request.contextPath}/editBooking/?id=${booking.bookingId}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteBooking/?id=${booking.bookingId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>


	</div>
</body>
</html>