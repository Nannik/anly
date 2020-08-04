<#import "../parts/base.ftlh" as base>

<#assign headContext>

</#assign>

<@base.base title="Main" headContext=headContext>
    <form action="/category/add" method="post">
        <input type="text" name="name" placeholder="name">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit">
    </form>
</@base.base>