
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
		<div class="navBarUsers">
			<nav class="navbar navbar-light">
				<ul class="nav navbar-nav">
					<li class="dataManagement subNavActive"><a
						href="${pageContext.request.contextPath}/userManagement/customer">Manage
							Users</a></li>
					<li class="userNavActive"><a
						href="${pageContext.request.contextPath}/userManagement/customer">Manage
							Users: Customers</a></li>
					<li class=""><a
						href="${pageContext.request.contextPath}/userManagement/staff">Manage
							Users: Staff</a></li>

					<li class="dataManagement"><a href="">Manage Rooms
							(und.dev.)</a></li>
					<li class="dataManagement"><a href="">Manage Facilities
							(und.dev.)</a></li>
					<li class="dataManagement"><a href="">Manage Amenities
							(und.dev.)</a></li>
				</ul>
			</nav>
		</div>
	</div>

	<div class="container">
		<h1>Manage Customers</h1>
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
				<td>Customer Id</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Username</td>
				<td>Email</td>
				<td>Phone number</td>
				<td>Address</td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="customer" items="${customerList}">
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
					<td><a
						href="${pageContext.request.contextPath}/deleteCustomer/?id=${customer.id}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>

		<h3>Create Customer</h3>

		<form:form action="${pageContext.request.contextPath}/createCustomer/"
			cssClass="form-horizontal" method="post" modelAttribute="customer">

			<div class="form-group">
				<label for="fName" class="col-md-3 control-label">First Name</label>
				<div class="col-md-9">
					<form:input path="fName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="lName" class="col-md-3 control-label">Last Name</label>
				<div class="col-md-9">
					<form:input path="lName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>

			<div class="form-group">
				<label for="username" class="col-md-3 control-label">Username</label>
				<div class="col-md-9">
					<form:input path="username" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-md-3 control-label">Password</label>
				<div class="col-md-9">
					<form:input path="password" type="password" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-md-3 control-label">Email</label>
				<div class="col-md-9">
					<form:input path="email" type="email" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>


			<div class="form-group">
				<label for="phoneNumber" class="col-md-3 control-label">Phone
					number</label>
				<div class="col-md-9">
					<form:input path="phoneNumber" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>


			<div class="form-group">
				<label for="address" class="col-md-3 control-label">Address</label>
				<div class="col-md-9">
					<form:input path="address" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<form:button cssClass="btn btn-primary">Submit</form:button>
				</div>
			</div>

		</form:form>

	</div>

	<hr>
	<div class="container bootstrap snippet">
		<div class="row">
			<div class="col-sm-10">
				<h1>
					<?php echo strtoupper($u->getUsername())?>
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3">
				<div class="text-center">
					<img class="avatar img-circle img-thumbnail"
						src="<?php 
                        if ($img == null) {
                            echo "
						http://ssl.gstatic.com/accounts/ui/avatar_2x.png";
                        } else {
                            echo "getImage.php?id=".$img->getImageID();
                        }?>"
						alt="avatar">
					<form method="post" action="<?php echo $_SERVER["PHP_SELF"]?>
						" enctype="multipart/form-data"> <input type="file"
							id="avatarImage" name="avatar">
						<h6>
							New Avatar
							<button style="color: grey;" type="submit" class="btn btn-light"
								name="updateAvatar">Update</button>
						</h6>
					</form>
				</div>
				</hr>
				<br>
				<ul class="list-group">
					<li class="list-group-item text-muted">Activity <i
						class="fa fa-dashboard fa-1x"></i></li>
					<li class="list-group-item text-right"><span class="pull-left"><strong>Created
						</strong></span>
					<?php echo date('Y-M-d', strtotime($u->getRegDate())) ?></li>
					<li class="list-group-item text-right"><span class="pull-left"><strong>Updated
						</strong></span>
					<?php echo date('Y-M-d', strtotime($u->getUpdateDate()))?></li>
					<li class="list-group-item text-right"><span class="pull-left"><strong><a
								href='?action=userCreatedPosts&postId=""'
								style="text-decoration: underline;">Your Posts</a> </strong></span>
					<?php echo $u->getPosts()?></li>
					<li class="list-group-item text-right"><span class="pull-left"><strong><a
								href='?action=savedPosts&postId=""'
								style="text-decoration: underline;">Saved Posts</a> </strong></span>
					<?php echo $u->getSavedPosts()?></li>
				</ul>
			</div>

			<div class="col-sm-9">
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
									value="<?php echo $u->getFirstName()?>" disabled>
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="last_name"><h4>Last name</h4></label> <input
									type="text" class="form-control"
									value="<?php echo $u->getLastName()?>" disabled>
							</div>
						</div>

						<div class="form-group">

							<div class="col-xs-6">
								<label for="phone"><h4>Phone Number</h4></label> <input
									type="text" class="form-control"
									value="<?php echo $u->getPhoneNumber()?>" disabled>
							</div>
						</div>

						<div class="form-group">

							<div class="col-xs-6">
								<label for="email"><h4>Email</h4></label> <input type="email"
									class="form-control" value="<?php echo $u->getEmail()?>"
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
							<h5 class="modal-title" id="staticBackdropLabel">Edit
								Account</h5>
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
										value="test" min="2"> <input
										type="text" class="form-control" name="userId" id="userId"
										value="test" hidden>
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
										value="<?php echo $u->getPhoneNumber()?>">
								</div>
							</div>

							<div class="form-group">
								<div class="col-xs-6">
									<label for="email"><h4>Email</h4></label> <input type="text"
										class="form-control" name="email" id="email"
										value="<?php echo $u->getEmail()?>" disabled>
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