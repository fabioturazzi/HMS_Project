
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Booking</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>
</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Edit Booking</h1>
		<hr />
		<form:form action="${pageContext.request.contextPath}/editBooking/"
			cssClass="form-horizontal" method="post" modelAttribute="booking">

			<div class="form-group">
				<label for="bookingId" class="col-md-3 control-label">Booking
					Id</label>
				<div class="col-md-9">
					<form:input path="bookingId" value="${booking.bookingId}"
						cssClass="form-control" readonly="true" />
				</div>
			</div>

			<div class="form-group" hidden="true">
				<label for="customerUsername" class="col-md-3 control-label">Customer
					Username</label>
				<div class="col-md-9">
					<form:input path="customerUsername"
						value="${booking.customerUsername}" cssClass="form-control"
						readonly="true" />
				</div>
			</div>

			<!--  
			<div class="form-group" hidden="true">
				<label for="dateOfCreation" class="col-md-3 control-label">Date Of Creation</label>
				<div class="col-md-9">
					<form:input type="date" path="dateOfCreation" value="${booking.dateOfCreation}"
						cssClass="form-control" readonly="true" />
				</div>
			</div> -->

			<div class="form-group">
				<label for="roomNumber" class="col-md-3 control-label">Room</label>
				<div class="col-md-9">
					<select name="roomList">
						<c:forEach items="${roomList}" var="roomList">
							<c:choose>
								<c:when test="${roomList.roomNumber == booking.roomNumber}">
									<option value="${roomList.roomNumber }" selected>${roomList.roomNumber }
										Type: ${roomList.roomType }</option>
								</c:when>
								<c:otherwise>
									<option value="${roomList.roomNumber }">${roomList.roomNumber }
										Type: ${roomList.roomType }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="numbOfPeople" class="col-md-3 control-label">Number
					of People</label>
				<div class="col-md-9">
					<form:input path="numbOfPeople" type="number"
						value="${booking.numbOfPeople}" cssClass="form-control" />
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
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>

		</form:form>

		<h1 id="pageTitle">Customer</h1>
		<hr />

		<table class="table table-striped table-bordered">
			<tr class="dataHeader">
				<td>Customer Id</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Username</td>
				<td>Email</td>
				<td>Phone number</td>
				<td>Address</td>
				<td>Recovery Question</td>
				<td>Recovery Answer</td>
				<td></td>
			</tr>
			<tr class="dataRows">
				<td>${customer.id}</td>
				<td>${customer.fName}</td>
				<td>${customer.lName}</td>
				<td>${customer.username}</td>
				<td>${customer.email}</td>
				<td>${customer.phoneNumber}</td>
				<td>${customer.address}</td>
				<td>${customer.passQuestion}</td>
				<td>${customer.passAnswer}</td>
				<td><a
					href="${pageContext.request.contextPath}/editCustomer/?id=${customer.id}">Edit</a></td>
			</tr>
		</table>

	</div>
</body>
</html>