<#include "header.ftl">

<b>Welcome to our Calendar Webapplication</b><br><br>

<form id="selectIdentity" method="POST" name="identity" action="selectGCWebpage?action=showCalendar">
	
	<label>!!DEBUG!! Assumed Identity: </label>
	<select id="identity" name="identity" form="selectIdentity">
		<option value="1">User1</option>
		<option value="2">User2</option>
		<option value="3">User3</option>
		<option value="4">User4</option>
		<option value="5">User5</option>
	</select>
	<button type="submit" id="" name="" value="Submit">Show Calendar</button>
</form>

<h4>Create a new Appointment for your Group<br></h4>

<form method="POST" action="createAppointment?action=createAppointment">
	<label>!!DEBUG!! Assumed Identity: </label>
	<select id="identity" name="identity" form="selectIdentity">
		<option value="1">User1</option>
		<option value="2">User2</option>
		<option value="3">User3</option>
		<option value="4">User4</option>
		<option value="5">User5</option>
	</select>
	<fieldset id="createAppointment">
		
		<div>
			<label>Name: </label>
			<input type="text" name="name" id="name">
	    </div>
		<div>
			<label>Description: </label>
			<input type="text" name="description" id="">
	    </div>
		<div>
			<label>Street: </label>
			<input type="text" placeholder="No Whitespaces!" name="street">
	    </div>
	    <div>
			<label>Postcode: </label>
			<input type="text" placeholder="NNNN" name="postcode">
	    </div>
	    <div>
			<label>Town: </label>
			<input type="text" name="town">
	    </div>
	    <div>
			<label>Country: </label>
			<input type="text" name="country">
	    </div>
	    <div>
			<label>From (date): </label>
			<input type="text" placeholder="DD:MM:YYYY" name="startDate">
	    </div>
	    <div>
			<label>From (time): </label>
			<input type="text" placeholder="HH:MM:SS" name="startTime">
	    </div>
	    <div>
			<label>To (date): </label>
			<input type="text" placeholder="DD:MM:YYYY" name="endDate">
	    </div>
	    <div>
			<label>To (time): </label>
			<input type="text" placeholder="HH:MM:SS" name="endTime">
	    </div>
	    <div>
			<label>Deadline: </label>
			<input type="text" placeholder="DD:MM:YYYY:HH:MM:SS" name="deadline">
	    </div>
	    <div>
			<label>Planned Participants: </label>
			<input type="text" placeholder="ID:ID:ID..." name="plannedParticipants">
	    </div>
	</fieldset>
	<button type="submit" id="btn_createAppointment" name="createAppointment" value="Submit">Create New Appointment</button>
</form>
<form action="/VR/selectAWebPage" method="GET">
<label>!!DEBUG!! Assumed Identity: </label>
	<select id="identity" name="identity" form="selectIdentity">
		<option value="1">User1</option>
		<option value="2">User2</option>
		<option value="3">User3</option>
		<option value="4">User4</option>
		<option value="5">User5</option>
	</select>
	<button type="submit" id="" name="selectAWebPage" value="Submit">Pending Requests</button>
</form>

<#include "footer.ftl">