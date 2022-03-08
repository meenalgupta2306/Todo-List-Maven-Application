<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
	<h1>Your todos are:</h1>
	<div class="navbar-collapse">
		<ul class="nav navbar-nav">
			<li><a>${isdone} done</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a>${remaining} Remaining</a></li>
		</ul>
	</div>
	
	<table class="table table-striped">
		
		<thead>
			<tr>
				<th></th>
				<th>Description</th>
				<th>Target Date</th>
				<th>Is Completed</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				
				<tr>
					<td> 
						<input type="checkbox"  onclick='window.location.assign("/done?desc=${todo.desc}&id=${todo.id}")'/>
						
					 </td>
					<td>${todo.desc}</td>
					<td><fmt:formatDate pattern="dd/MM/yyyy" value="${todo.targetDate}"/></td>
					<td>${todo.done}</td>
					<td>
						<a href="/update-todo?id=${todo.id}" class="btn btn-success"> Update</a> 
						<a href="/delete-todo?id=${todo.id}" class="btn btn-danger"> Delete</a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br/>
	<div>
		<a class="btn btn-success" href="/add-todo">Add</a>
	</div>
</div>
<%@ include file="common/footer.jspf" %>