<#include "header.ftl">

<h3>Select an Appointment for further action</h3>

<h4>Unfinalized Appointments</h4>

<form id="form_table_UA" action="/VR/appointment" method="GET">
<table id="UnfinalizedAppointments">
	<tr>
		<th>#</th>
		<th>Name</th>
		<th>Description</th>
		<th>Location</th>
		<th>Start Time</th>
		<th>End Time</th>
		
	</tr>
	<#list appointmentList as ap>
	<tr>
		<td><input id="btn_UA" style="font-size: 20px; background-repeat: no-repeat; overflow: hidden;" value="${ap.id}" name="clickedAppointmentID" type="submit"></input></td>
		<td>${ap.name}</td>
		<td>${ap.description}</td>
		<td>${ap.location.street}, ${ap.location.postcode}, ${ap.location.town}, ${ap.location.country}</td>
		<td>${ap.startTime.dayString}.${ap.startTime.monthString}.${ap.startTime.yearString}<br>${ap.startTime.hourString}:${ap.startTime.minuteString} Uhr</td>
		<td>${ap.endTime.dayString}.${ap.endTime.monthString}.${ap.endTime.yearString}<br>${ap.endTime.hourString}:${ap.endTime.minuteString} Uhr</td>
		
	</tr>
	</#list>
</table>
</form>
<form action="/VR/index" method="GET">
	<button type="submit" id="btn_backToMain" name="" value="Submit">Back to Main Page</button>
</form>
<#include "footer.ftl">