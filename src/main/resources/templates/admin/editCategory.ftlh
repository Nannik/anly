<#import "../parts/base.ftlh" as base>

<#assign headContext>

</#assign>

<@base.base title="Main" headContext=headContext>
    <form action="/category/edit" method="post">
        <input type="text" name="name" value="${category.name}">
        <input type="hidden" name="id" value="${category.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit">
    </form>
</@base.base>