<#import "../parts/base.ftlh" as base>

<#assign headContext>

</#assign>

<@base.base title="Main" headContext=headContext>
    <form action="/user" method="post">
        <input type="text" name="name" value="${user.name}">
        <input type="email" name="email" value="${user.email}">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="id">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@base.base>