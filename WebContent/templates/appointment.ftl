<#include "header.ftl">

<h2>${appointmentName}</h2>

<table id="suggestions">
	<tr>
		<th>#</th>
		<th>StartTime</th>
		<th>EndTime</th>
		<th>Confirmations</th>
	</tr>
	<#list suggestions as s>
	<tr>
		<td><input style="font-size: 20px; background-repeat: no-repeat; overflow: hidden;" value="${s.id}" name="selectedSuggestion" type="submit"></input></td>
		<td>${s.startTime}</td>
		<td>${s.endTime}</td>
		<td>${s.confirmationNum}</td>
	</tr>
	</#list>
</table>
<form action="/VR/index" method="GET">
	<button type="submit" id="" name="" value="Submit">Back to Main Page</button>
</form>
<#include "footer.ftl">