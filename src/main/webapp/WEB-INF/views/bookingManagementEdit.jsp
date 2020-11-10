
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

		<h3>Booking Information</h3>

		<c:if test="${ errorMessage !=null }">
			<div class="alert alert-danger">${errorMessage}</div>
		</c:if>

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


			<div class="form-group" hidden="true">
				<label for="dateOfCreation" class="col-md-3 control-label">Booking
					Created</label>
				<div class="col-md-9">
					<form:input type="date" name="dateOfCreation" path="dateOfCreation"
						cssClass="form-control" required="required" />
				</div>
			</div>

			<div class="form-group" hidden="true">
				<label for="paid" class="col-md-3 control-label">paid</label>
				<div class="col-md-9">
					<form:input name="paid" path="paid" cssClass="form-control"
						required="required" />
				</div>
			</div>

			<div class="form-group">
				<label for="room" class="col-md-3 control-label">Room</label>
				<div class="col-md-9">
					<select name="room">
						<c:forEach items="${roomList}" var="room">
							<c:choose>
								<c:when test="${room.roomNumber == booking.roomNumber}">
									<option value="${room.roomNumber }" selected>${room.roomNumber }
										Type: ${room.roomType }</option>
								</c:when>
								<c:otherwise>
									<option value="${room.roomNumber }">${room.roomNumber }
										Type: ${room.roomType }</option>
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
				<label for="checkinDate" class="col-md-3 control-label">checkoutDate
				</label>
				<div class="col-md-9">
					<form:input type="date" name="checkinDate" path="checkinDate"
						cssClass="form-control" readonly="true" />
				</div>
			</div>

			<div class="form-group">
				<label for="checkoutDate" class="col-md-3 control-label">checkoutDate
				</label>
				<div class="col-md-9">
					<form:input type="date" name="checkoutDate" path="checkoutDate"
						cssClass="form-control" readonly="true" />
				</div>
			</div>

			<div class="form-group">
				<label for="paymentDate" class="col-md-3 control-label">paymentDate
				</label>
				<div class="col-md-9">
					<form:input type="date" name="paymentDate" path="paymentDate"
						cssClass="form-control" readonly="true" />
				</div>
			</div>

			<div class="form-group" name="radioContainer">
				<label for="status" class="col-md-3 control-label">Status</label>
				<div class="col-md-9">

					<c:forEach items="${roomStatus}" var="status">
						<c:choose>
							<c:when test="${booking.status == 'checked-out'}">
								<label><input type="radio" name="status"
									value="${status}" disabled>${status}</label>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${status == 'checked-in' && canCheckin==false}">
										<label><input type="radio" name="status"
											value="${status}" disabled>${status}</label>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${status == booking.status}">
												<label><input type="radio" name="status"
													value="${status}" checked>${status}</label>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when
														test="${status == 'checked-out' && (booking.paid != true || booking.checkinDate == null)}">
														<label><input type="radio" name="status"
															value="${status}" disabled>${status}</label>
													</c:when>
													<c:otherwise>
														<label><input type="radio" name="status"
															value="${status}">${status}</label>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn btn-primary">Submit
						Changes</button>
				</div>
			</div>

		</form:form>

		<h3>Customer Information</h3>
		<hr />

		<table class="table table-striped table-bordered">
			<tr class="dataHeader">
				<th>Customer Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Username</th>
				<th>Email</th>
				<th>Phone number</th>
				<th>Address</th>
				<th></th>
			</tr>
			<tr class="dataRows">
				<td>${customer.id}</td>
				<td>${customer.fName}</td>
				<td>${customer.lName}</td>
				<td>${customer.username}</td>
				<td>${customer.email}</td>
				<td>${customer.phoneNumber}</td>
				<td>${customer.address}</td>
				<td><a
					href="${pageContext.request.contextPath}/editCustomer/?id=${customer.id}">Edit</a></td>
			</tr>
		</table>

	</div>
</body>
</html>