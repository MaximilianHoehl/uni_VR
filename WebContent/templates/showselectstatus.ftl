<#include "header.ftl">

<#if success == 1>
	<h2>SECCESS!</h2>
<#else>
	<h2>FAIL!</h2>
</#if>
<br/>
<form action="/VR/index" method="GET">
	<button type="submit" id="" name="" value="Submit">Weiter</button>
</form>

<#include "footer.ftl">