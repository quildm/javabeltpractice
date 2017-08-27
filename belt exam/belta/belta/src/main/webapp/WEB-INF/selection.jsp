<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>

    <c:if test="${logoutMessage != null}">
    		<span style="color:green">
        <c:out value="${logoutMessage}"></c:out>
        </span>
    </c:if>

    <c:if test="${errorMessage != null}">
	    	<span style="color:red">
	        <c:out value="${errorMessage}"></c:out>
	    </span>
    </c:if>
    

    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        
    <c:if test="${isAdmin}">
    		<a href="/admin">Admin Dashboard</a>
    </c:if>
        
        <input type="submit" value="Logout!" />
    </form>
    
    <h1>Welcome Page <c:out value="${currentUser.email}"></c:out></h1>
    <b>Please choose a subscription and due date:</b>
    
     <form method="POST" action="/selection/subscribe" >
     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
     	<p>Due Date:
     	<select name="dueDate">
     		<c:forEach var="d" items=" ${days}" varStatus="loop">
     			<option value="${loop.index+1}">${loop.index+1}</option>
     		</c:forEach>
     	</select></p>
     	<p>Package
     	<select name="package_id">
     		<c:forEach var="p" items="${packages}">
     			<c:if test="${p.available}">
     				<option value="${p.id}">${p.name}</option>
     			</c:if>
     		</c:forEach>
     	</select></p>
     	<input type="submit" value="Sign Up!">
     </form>
    

</body>
</html>