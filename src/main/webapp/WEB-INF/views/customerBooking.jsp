
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- Import Currency --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
	</div>
	<h1 class="text-center">&#9992; Booking Confirmation</h1>
	<div class="container cover">
		<br>
		<div class="col-md-5 booking-form">
			<h3>Your Details</h3>
			<div class="row form-group" hidden>
				<div class="col-xs-6">
					<h4>ID</h4>
					<input type="text" class="form-control"
						value="&#9998; ${ customerBook.id }" disabled>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-6">
					<h4>First name</h4>
					<input type="text" class="form-control"
						value="&#9998; ${ customerBook.fName }" disabled>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-6">
					<h4>Last name</h4>
					<input type="text" class="form-control"
						value="&#9998; ${ customerBook.lName }" disabled>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-6">
					<h4>Phone Number</h4>
					<input type="text" class="form-control"
						value="&#9990; ${ customerBook.phoneNumber }" disabled>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-6">
					<h4>Email</h4>
					<input type="email" class="form-control"
						value="&#9993; ${customerBook.email }" disabled>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-6">
					<h4>Address</h4>
					<input type="text" class="form-control"
						value="&#9750; ${customerBook.address }" disabled>
				</div>
			</div>
			<button type="submit" class="btn btn-primary" name="action"
				value="edit" data-toggle="modal" data-target="#staticBackdrop"
				onclick="">Edit</button>
		</div>

		<div class="col-md-5 booking-form room-confirmation">
			<h3>Your Stay</h3>
			<div class="row">
				<div class="col">
					<div class="list-group">
						<div class="list-group-item">
							<h4>
								&#128467; <b>${startDate}</b>&#10153;<b>${endDate}</b>
							</h4>
							<h4>${nights} nights</h4>
						</div>
						<div class="list-group-item">
							<h4>
								&#128719; <b>Room Type:</b> ${roomType}
							</h4>
							<h4>
								<b>&#8680; Room Amenities:</b> ${amenities}
							</h4>
						</div>
						<div class="list-group-item">
							<h4>
								<b>TOTAL: </b> (Taxes not included) <b><fmt:formatNumber
										type="currency" value="${price}" /></b>
							</h4>
						</div>
					</div>
				</div>


				<!-- Hidden Form to submit booking Information -->
				<form:form action="${pageContext.request.contextPath}/submitBooking/"
					method="post" modelAttribute="booking">
					<div class="row">
						<div class="col form-group" hidden>
							<label for="bookingId">bookingId</label>
							<div>
								<form:input type="number" class="form-control" path="bookingId" />
							</div>
						</div>

						<div class="col form-group" hidden>
							<label for="roomNumber">roomNumber</label>
							<div>
								<form:input type="number" path="roomNumber" class="form-control" />
							</div>
						</div>

						<div class="col form-group" hidden>
							<label for="customerUsername">customerUsername</label>
							<div>
								<form:input type="text" path="customerUsername"
									class="form-control" />
							</div>
						</div>
					</div>

					<div class="col form-group" hidden>
						<label for="numbOfPeople">numbOfPeople</label>
						<div>
							<form:input type="number" path="numbOfPeople"
								class="form-control" />
						</div>
					</div>

					<div class="col form-group" hidden>
						<label for="status">status</label>
						<div>
							<form:input type="text" path="status" class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="paid">Paid</label>
						<div>
							<form:input type="text" path="paid" class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="bookingDateStart">bookingDateStart</label>
						<div>
							<form:input type="date" path="bookingDateStart"
								class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="bookindDateEnd">bookindDateEnd</label>
						<div>
							<form:input type="date" path="bookindDateEnd"
								class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="checkinDate">checkinDate</label>
						<div>
							<form:input type="date" path="checkinDate" class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="checkoutDate">checkoutDate</label>
						<div>
							<form:input type="date" path="checkoutDate" class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="paymentDate">paymentDate</label>
						<div>
							<form:input type="date" path="paymentDate" class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="dateOfCreation">dateOfCreation</label>
						<div>
							<form:input type="date" path="dateOfCreation"
								class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="totalCost">totalCost</label>
						<div>
							<form:input type="text" path="totalCost" class="form-control" />
						</div>
					</div>
					<div class="col form-group" hidden>
						<label for="roomType">roomType</label>
						<div>
							<form:input type="text" path="roomType" class="form-control" />
						</div>
					</div>
					<%-- SUBMIT BOOKING --%>
					<div class="form-group">
						<!-- Button -->
						<span> <form:button class="btn btn-primary mb-2">Confirm Booking</form:button>
						</span> <a class="col btn btn-primary"
							href="${pageContext.request.contextPath}/roomSearch">Back to
							room search </a>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<!-- Modal with Edit form -->
	<form:form action="${pageContext.request.contextPath}/profileBook"
		cssClass="form-horizontal" method="post" modelAttribute="customerBook">
		<div class="modal fade" id="staticBackdrop" data-backdrop="static"
			tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="staticBackdropLabel">
							Edit <b>${customerBook.lName }'s</b> Account
						</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">

						<div class="form-group" hidden>
							<div class="col-xs-6">
								<h4>User ID</h4>
								<form:input path="id" type="text" class="form-control"
									value="${customerBook.id }" min="2" />

							</div>
						</div>

						<div class="form-group" hidden>
							<div class="col-xs-6">
								<h4>Registration Date</h4>
								<form:input path="registrationDate" type="date"
									class="form-control" value="${ customerBook.registrationDate }" />

							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<h4>Username</h4>
								<form:input path="username" type="text" class="form-control"
									value="${customerBook.username }" min="2" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<h4>First name</h4>
								<form:input path="fName" type="text" class="form-control"
									id="firstName" value="${customerBook.fName }" min="2" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<h4>Last name</h4>
								<form:input path="lName" type="text" class="form-control"
									id="lastName" value="${user.lName }" min="2" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<h4>Phone Number</h4>
								<form:input path="phoneNumber" type="text" class="form-control"
									id="phoneNumber" value="${customerBook.phoneNumber }" min="2" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<h4>Email</h4>
								<form:input path="email" type="email" class="form-control"
									id="email" value="${customerBook.email }" min="2" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<h4>Address</h4>
								<form:input path="address" type="text" class="form-control"
									id="address" value="${customerBook.address }" min="2" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6">
								<h4>Password Question</h4>
								<form:input path="passQuestion" type="text" class="form-control"
									id="passQuestion" value="${customerBook.passQuestion }" min="2" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6">
								<h4>Password Answer</h4>
								<form:input path="passAnswer" type="text" class="form-control"
									id="passAnswer" value="${customerBook.passAnswer }" min="2" />
							</div>
						</div>
						<div class="form-group">
							<div class="text-center">
								<h4>
									<b>On saving this form, it will redirect you to room search
										page</b>
								</h4>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-primary" name="action"
								value="saveChanges">Save</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>