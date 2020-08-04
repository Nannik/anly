<#import "parts/base.ftlh" as base>

<#assign headContext>

</#assign>

<@base.base title="Main" headContext=headContext>
    <form action="/registration" method="post">
        <input type="text" name="name" placeholder="name">
        <input type="password" name="password" placeholder="password">
        <input type="email" name="email" placeholder="example@gmail.com">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit">
    </form>
</@base.base>