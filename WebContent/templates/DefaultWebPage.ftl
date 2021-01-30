<#include "header.ftl">

<b>Welcome to our Calendar Webapplication</b><br><br>

<form id="selectIdentity" method="POST" name="identity" action="selectGCWebpage?action=showCalendar">
	<legend>Required Information</legend>
	
	<label>Assumed Identity: </label>
	<select id="identity" name="identity" form="selectIdentity">
		<option value="1">User1</option>
		<option value="2">User2</option>
		<option value="3">User3</option>
	</select>
	<button type="submit" id="" name="" value="Submit">Show Calendar</button>
</form>

<h4>Create a new Appointment for your Group<br></h4>

<form method="POST" action="createAppointment?action=createAppointment">
	<fieldset id="createAppointment">
		<legend>Required Information</legend>
		<div>
			<label>Terminname: </label>
			<input type="text" name="name" id="name">
	    </div>

		<div>
			<label>Beschreibung: </label>
			<input type="text" name="description" id="">
	    </div>

		<div>
			<label>Location: </label>
			<input type="text" name="location">
	    </div>
	    <div>
			<label>From: </label>
			<input type="text" name="startTime">
	    </div>
	    <div>
			<label>To: </label>
			<input type="text" name="endTime">
	    </div>
	    <div>
			<label>Deadline: </label>
			<input type="text" name="deadline">
	    </div>
	</fieldset>
	<button type="submit" id="btn_createAppointment" name="createAppointment" value="Submit">Create New Appointment</button>
</form>

<#include "footer.ftl">