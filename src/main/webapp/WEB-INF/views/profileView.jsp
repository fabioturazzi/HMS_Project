
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Registration</title>

<script src="<c:url value="/static/js/jquery-3.5.1.min.js" />"></script>
<link href="<c:url value="/static/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/static/css/hackermenHMS.css" />"
	rel="stylesheet">
<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>

<script src="<c:url value="/static/js/searchFilters.js" />"></script>


</head>
<body>

	<div class="pageHeader">
		<img src="<c:url value="/static/images/HMS Logo.png" />"
			alt="HMS Hackermen logo" class="hmsLogo" id="hmsLogo" />
		<div class="navBarDiv">
			<nav class="navbar navbar-default">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath}/registration/">Login/Registration</a></li>
					<li><a href="">Room Search (under development)</a></li>
					<li><a href="">My Reservations (under development)</a></li>
					<li class="active"><a
						href="${pageContext.request.contextPath}/userManagement/customer">Manage
							System Data</a></li>
				</ul>
			</nav>
		</div>
	</div>

	<div class="container bootstrap snippet">
		<div class="row">
			<div class="col-sm-10">
				<h1>${user.username}</h1>
			</div>
		</div>
		<div class="row">
		
			<div class="text-center">
				<img class="avatar img-circle img-thumbnail"
					src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" alt="avatar">
				<form method="post" action="" enctype="multipart/form-data">
					<input type="file" id="avatarImage" name="avatar">
					<h6>
						New Avatar
						<button style="color: grey;" type="submit" class="btn btn-light"
							name="updateAvatar">Update</button>
					</h6>
				</form>
			</div>
			<hr>
			<br>
			<ul class="list-group">
				<li class="list-group-item text-muted">Activity <i
					class="fa fa-dashboard fa-1x"></i></li>
				<li class="list-group-item text-right"><span class="pull-left"><strong>Created
					</strong>00/00/0000</span>${ user.registrationDate } </li>
				<li class="list-group-item text-right"><span class="pull-left"><strong>Updated
					</strong></span> ${ user.profileUpdated } </li>
				<li class="list-group-item text-right"><span class="pull-left"><strong><a
							href='${pageContext.request.contextPath}/seebooking'
							style="text-decoration: underline;">Your Bookings</a> </strong></span>0</li>
			</ul>

			<h5>Profile Information</h5>
			
			<button type="submit" class="btn btn-primary" name="action"
				value="edit" data-toggle="modal" data-target="#staticBackdrop"
				onclick="">Edit</button>
			<div class="tab-content">
				<div class="tab-pane active" id="home">
					<hr>
					<div class="form-group">

						<div class="col-xs-6">
							<label for="first_name"><h4>First name</h4></label> <input
								type="text" class="form-control"
								value="${ user.fName }" disabled>
						</div>
					</div>
					<div class="form-group">

						<div class="col-xs-6">
							<label for="last_name"><h4>Last name</h4></label> <input
								type="text" class="form-control"
								value="${ user.lName }" disabled>
						</div>
					</div>

					<div class="form-group">

						<div class="col-xs-6">
							<label for="phone"><h4>Phone Number</h4></label> <input
								type="text" class="form-control"
								value="${ user.phoneNumber }" disabled>
						</div>
					</div>

					<div class="form-group">

						<div class="col-xs-6">
							<label for="email"><h4>Email</h4></label> <input type="email"
								class="form-control" value="${user.email }"
								disabled>
						</div>
					</div>
					
					<div class="form-group">

						<div class="col-xs-6">
							<label for="email"><h4>Address</h4></label> <input type="email"
								class="form-control" value="${user.address }"
								disabled>
						</div>
					</div>
					
					<hr>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal with Edit form -->
	<form class="form" action="" method="POST" id="editAccountForm">
		<div class="modal fade" id="staticBackdrop" data-backdrop="static"
			tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="staticBackdropLabel">Edit Account</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">

						<div class="form-group">
							<div class="col-xs-6">
								<label for="first_name"><h4>Username</h4></label> <input
									type="text" class="form-control" name="username" id="username"
									value="test" min="2"> <input type="text"
									class="form-control" name="userId" id="userId" value="test"
									hidden>
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="first_name"><h4>First name</h4></label> <input
									type="text" class="form-control" name="fName" id="firstName"
									value="" min="2">
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="last_name"><h4>Last name</h4></label> <input
									type="text" class="form-control" name="lName" id="lastName"
									value="" min="2">
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="phone"><h4>Phone Number</h4></label> <input
									type="text" class="form-control" name="phoneNum" id="phoneNum"
									value=" ${user.email }">
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="email"><h4>Email</h4></label> <input type="text"
									class="form-control" name="email" id="email"
									value=" ${user.email }" disabled>
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="password"><h4>Password</h4></label><br> <input
									type="password" class="form-control" name="password"
									id="password" min="8"> <label><input
									type="checkbox" name="resetPass">Reset Password</label>
							</div>
						</div>

						<div class="modal-footer">
							<button type="submit" id="deleteBtn" class="btn btn-danger"
								name="action" value="deleteAccount">Delete</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary" name="action"
								value="saveChanges">Save</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	</div>

</body>
</html>