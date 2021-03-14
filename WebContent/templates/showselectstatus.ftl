<#include "header.ftl">

<#if success == 1>
	<h2>You successfully selected an appointment-suggestion</h2>
<#else>
	<h2>FAIL!</h2>
</#if>
<br/>
<form action="/VR/index" method="GET">
	<button type="submit" id="btn_continue" name="" value="Submit">Continue</button>
</form>

<#include "footer.ftl">