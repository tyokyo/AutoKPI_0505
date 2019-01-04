<#include "/lib/common.ftl"> 
<#include "/lib/head.ftl"> 

<html>
<@html_head/>
<body>
<h1>AutoKPI Report</h1>
<table>
 <@result_table_head max/>
<#list result?keys as key>
<tr>
 <#assign name=key>
 <#assign ref=reference[key]>
 <#assign av=ave[key]>
 <#assign rem=remark[key]>
 <#assign res=result[key]>
 <@result_line name ref av rem res/>
</tr>
</#list>
</table>

</body>