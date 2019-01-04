<#-- 打印表头 -->
<#-- 测试项	参考值	平均值	第一次	第二次	第三次	第四次	第五次	备注 -->
<#macro result_table_head max>
<tr>
<th>测试项</th>
<th>参考值</th>
<th>平均值</th>
<th>备注</th>
<#assign x=0/>
<#list 1..max as item>
<#assign x=x+1/>
<th>${x}</th>
</#list>
</tr>
</#macro>

<#-- 打印表的一行 -->
<#macro result_line name ref ave remark list>
<td>${name}</td>
<td>${ref}</td>
<td>${ave}</td>
<td>${remark}</td>
<#list list as result>
<td>${result}</td>
</#list>
</#macro>

<#-- 作废，不再用 -->
<#macro result_fulltable_by_list max list>
 <table>
 <@result_table_head max/>
 <#list list as key>
 <@result list/>
 </#list>
 </table>
</#macro>

<#-- 作废，不再用 -->
<#macro average list>
<#assign result=0>
<#assign x=0/>
<#list list as item>
  <#assign result=result+item>
  <#assign x=x+1/>
</#list>
${result/x}
</#macro>
