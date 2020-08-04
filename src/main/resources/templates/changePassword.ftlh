<#import "parts/base.ftlh" as base>
<#include "parts/security.ftlh">

<#assign headContext>

</#assign>

<#assign scripts>
    <script src="/static/app/js/profile.js"></script>
</#assign>

<@base.base title="ChangePassword" headContext=headContext scripts=scripts>
    <form action="/changePassword" method="post">
        <input type="password" name="lastPassword" placeholder="Last password">
        <input type="password" name="newPassword" placeholder="New Password">
        <input type="password" name="reNewPassword" placeholder="ReEnter">
        <input type="text" name="secretCode" placeholder="Activation code">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Change">
    </form>
</@base.base>