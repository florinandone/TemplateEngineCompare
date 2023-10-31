Server,Database Driver,Database URL,Database Username,Database Password,LDAP URL,LDAP Bind DN,LDAP Bind Password
<#list servers as server>
    "${server.name}","${server.database.driverClassName}","${server.database.url}","${server.database.username}","${server.database.password}","${server.ldap.ldapUrl}","${server.ldap.bindDn}","${server.ldap.bindPassword}"
</#list>

<#include "free_maker_template-imported.ftl">