<#include "security.ftlh">

<#assign defaultAdminDropdownContext>

</#assign>

<#macro base title headContext="" scripts="" useAdminPanel=false adminDropdownContext="">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        ${headContext}

        <meta charset="UTF-8">
        <title>${title}</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <link rel="stylesheet" href="/static/app/css/main.css" />

    </head>
    <body class="background-primary_light-2">
        <div class="container-fluid">
            <@header>
                ${adminDropdownContext}
            </@header>
        </div>
        <div class="container-lg">
            <h2><#if message??>${message}</#if></h2>
            <div class="hidden">
                <#nested>
            </div>
            <@footer></@footer>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

        <script src="/static/app/js/main.js"></script>
        ${scripts}
    </body>
    </html>
</#macro>

<#macro header>
    <div class="row">
        <div class="col">
            <header>
                <nav id="top" class="background-primary">
                    <div class="container-lg">
                        <div class="row">
                            <div class="col"></div>
                            <div class="col-3">
                                <div class="user-panel">
                                    <i class="fas fa-heart"></i>
                                    <i class="fas fa-shopping-cart"></i>
                                    <i class="fas fa-user"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
                <nav class="background-primary categories-nav">
                    <div class="container-lg">
                        <div class="row">
                            <div class="col">
                                <div class="categories">
                                    <#list 1..3 as i>
                                        <a class="category-link" href="#">Category ${i}</a>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
            </header>
        </div>
    </div>
</#macro>

<#macro headerr>
    <nav class="navbar navbar-light bg-light border-bottom mb-3">
        <a class="navbar-brand" href="/">Main</a>
        <ul class="nav nav-pills">
            <#list categories as category>
                <li class="nav-item">
                    <a class="nav-link" href="/category/${category.id}">${category.name}</a>
                </li>
            </#list>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Users</a>
                </li>
            </#if>

            <#if known>
                <li class="nav-item">
                    <form action="/logout" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Log Out" class="nav-link">
                    </form>
                </li>

            <#else>
                <a class="nav-link" href="/login">Login</a>
            </#if>

            <#if user??>
                <#if isAdmin>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Admin
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" href="/admin">Admin panel</a>
                            <a class="dropdown-item" href="/profile">Profile</a>
                            <#nested>
                        </div>
                    </li>

                <#else>
                    <li class="nav-item">
                        <a href="/profile" class="nav-link">${user.name}</a>
                    </li>
                </#if>
            </#if>


        </ul>
    </nav>
</#macro>

<#macro footer>
    <div class="row footer">
        <div class="col">

        </div>
    </div>
</#macro>