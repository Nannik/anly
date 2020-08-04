<#include "parts/security.ftlh">
<#import "parts/base.ftlh" as base>

<#if !categoryId??>
    <#assign categoryId = 0>
</#if>

<#assign headContext>
    <link href="https://cdn.jsdelivr.net/npm/froala-editor@3.1.0/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
</#assign>

<#assign scripts>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/froala-editor@3.1.0/js/froala_editor.pkgd.min.js"></script>
    <script src="/static/app/js/adminPage.js"></script>
</#assign>

<@base.base title="Admin Panel" headContext=headContext scripts=scripts>

    <div class="row">
        <div class="col">
            <table class="table table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Number</th>
                    <th scope="col">Price</th>
                    <th scope="col">Status</th>
                    <th scope="col">Submit</th>
                </tr>
                </thead>
                <tbody>
                <#list orders as order>
                    <tr>
                        <form action="/changeOrderStatus" method="post">
                            <th>${order.id}</th>
                            <th>${order.orderNumber}</th>
                            <th>${order.price}</th>
                            <th>
                                <select name="status">
                                    <#list 1..4 as i>
                                        <option <#if i==order.status>selected</#if> value="${i}">${i}</option>
                                    </#list>
                                </select>
                            </th>
                            <th><input type="submit"></th>

                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                        </form>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <form action="/mailAll" method="post">
                <input type="text" name="subject" placeholder="subject" required>
                <textarea id="text" name="text" required></textarea>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit">
            </form>
        </div>
    </div>
</@base.base>