<#include "header.ftl">

<h3>Calendarinfo:</h3>
<p>${GroupCalendarInfo.groupID}</p>
<p>${GroupCalendarInfo.name}</p>
<p>${GroupCalendarInfo.description}</p>

<h4>Calendar:</h4>

<table id="Calendar">
	<tr>
		<th>#</th>
		<th>Name</th>
		<th>Description</th>
		<th>Location</th>
		<th>Start Time</th>
		<th>End Time</th>
	</tr>
	<#list AppointmentList as ap>
	<tr>
		<td>${ap.id}</td>
		<td>${ap.name}</td>
		<td>${ap.description}</td>
		<td>${ap.location.street}, ${ap.location.postcode}, ${ap.location.town}, ${ap.location.country}</td>
		<td>${ap.startTime.dayString}.${ap.startTime.monthString}.${ap.startTime.yearString}<br>${ap.startTime.hourString}:${ap.startTime.minuteString} Uhr</td>
		<td>${ap.endTime.dayString}.${ap.endTime.monthString}.${ap.endTime.yearString}<br>${ap.endTime.hourString}:${ap.endTime.minuteString} Uhr</td>
	</tr>
	</#list>
</table>
<form action="/VR/index" method="GET">
	<button type="submit" id="" name="" value="Submit">Back to Main Page</button>
</form>
<#include "footer.ftl">