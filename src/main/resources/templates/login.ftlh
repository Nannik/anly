<#import "parts/base.ftlh" as base>

<#assign headContext>

</#assign>

<@base.base title="Main" headContext=headContext>
     <form action="/login" method="post">
         <div><label> User Name : <input type="text" name="username"/> </label></div>
         <div><label> Password: <input type="password" name="password"/> </label></div>
         <input type="hidden" name="_csrf" value="${_csrf.token}">
         <div><input type="submit" value="Sign In"/></div>
     </form>

    <a href="/registration">Registration</a>
</@base.base>