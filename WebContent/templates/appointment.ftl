<#include "header.ftl">

<h2>Appointment Suggestions</h2>

<form id="form_table_suggestions" method="POST" action="appointment?action=selectSuggestion">
<table id="suggestions">
	<tr>
		<th>#</th>
		<th>StartTime</th>
		<th>EndTime</th>
		<th>Confirmations</th>
	</tr>
	<#list suggestions as s>
	<tr>
		<td><input id="btn_selectSuggestion" style="font-size: 20px; background-repeat: no-repeat; overflow: hidden;" value="${s.id}" name="selectedSuggestion" type="submit"></input></td>
		<td>${s.startTime}</td>
		<td>${s.endTime}</td>
		<td>${s.confirmations} / ${s.requiredConfirmations}</td>
	</tr>
	</#list>
</table>
</form>
<h3>Suggest another date</h3>
<form action="appointment?action=newSuggestion" method="POST">
	<fieldset id="createSuggestion">
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
   	</fieldset>
	<button type="submit" id="createSuggestion" name="createSuggestion" value="">New Suggestion</button>
</form>
<br/>

<#include "footer.ftl">