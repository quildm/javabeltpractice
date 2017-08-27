<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>


    		




    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <h1>Admin dashboard (Hello ${currentUser.getEmail()} )</h1>
    
   <table style="width:100%">
   <tr style="background:#ccc"><td>Name</td><td>Next Due Date</td><td>Ammount Due</td><td>Package Type</td>
   </tr>
    <c:forEach var="u" items="${allUsers}" >
    		<c:if test="${!currentUser.id.equals(u.id) }">
	    		<tr>
	    			<td>${u.email}</td>
				<td><fmt:formatDate pattern="MMMM dd, yyyy" value="${u.dueDate}" /></td>
				<td>&#x24; ${u.getChoosed_package().getCost()}</td>
				<td>${u.getChoosed_package().getName()}</td>
     	</c:if>
    </c:forEach>
    </table>
    
    <h1>Packages</h1>
   <table style="width:100%">
   <tr style="background:#ccc"><td>Package Name</td><td>Package Cost</td><td>Available</td><td>Users</td><td>Actions</td></tr>
    <c:forEach var="p" items="${packages}" >
    		<tr ><td>${p.name}</td>
    		<td>&#x24; ${p.getCost()}</td>
    		<td><c:if test="${p.available}">available</c:if><c:if test="${!p.available}">unavailable</c:if></td>
    		<td>${p.users.size()}</td>
    		<td>
			<c:if test="${p.available}">
				<a href="/admin/pack/${p.id}/deactivate">deactivate</a>
				<a href="/admin/pack/${p.id}/delete">delete</a>
			</c:if><c:if test="${!p.available}">
				<a href="/admin/pack/${p.id}/activate">activate</a>
			</c:if>
		</td></tr>
    </c:forEach>
    </table>
    <!-- ADD new PACKAGE  -->
    	<h2>Create new package:</h2>
		 <form:form method="POST" action="/admin/newpack" modelAttribute="pack">
		 	<form:hidden path="id"/>
		 	
		 	<form:hidden path="available" value="false"/>
		    
		    <form:label path="name">Name: 
		    <form:input path="name"/></form:label>
		    <span style="color:red"><form:errors path="name"/></span>
		    <br/>

		    <form:label path="cost">Cost: 
		    <form:input path="cost" type="text"/></form:label>
		    <span style="color:red"><form:errors path="cost"/></span>
		    <br/>
		    
		    <input type="submit" value="Submit"/>
		</form:form>
    <!--  -->
</body>
</html>