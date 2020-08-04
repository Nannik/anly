<#include "parts/security.ftlh">
<#import "parts/base.ftlh" as base>

<#assign headContext>
    <script src="/static/app/js/jquery.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.7/dist/jquery.fancybox.min.css" />
    <script src="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.7/dist/jquery.fancybox.min.js"></script>

    <link rel="stylesheet" href="/static/app/css/product.css">
</#assign>

<#if !isEdit??>
    <#assign useAdminPanel = false>
<#else >
    <#assign useAdminPanel = true>
</#if>

<#assign adminDropdownContext>
    <#if product??>
        <#if isEdit>
            <a href="/product/${product.id}">User view</a>
        <#else >
            <a href="/product/${product.id}?edit=true">Edit</a>
        </#if>
    </#if>
</#assign>

<#assign view>
    <#if product??>
        <div class="row">
            <div class="col-xl-6 col-md-4">
                <a class="main-image" data-fancybox="gallery" href="/img/${product.images[0]}"><img src="/img/${product.images[0]}"></a>
                <#list product.images as image>
                    <#if image?index == 0><#continue></#if>

                    <a data-fancybox="gallery" href="/img/${image}"><img src="/img/${image}"></a>
                </#list>
            </div>
            <div class="col-xl-6 col-md-8">
                <h1>${product.name}</h1>
                <h4>${product.price}</h4>

                <hr>

                <div>${product.description}</div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <hr>

                <form action="/product/addMessage" method="post">
                    <input type="text" name="text" placeholder="text">
                    <input type="number" name="rate" value="1">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit">Comment</button>
                </form>

                <#list messages?reverse as message>
                    <div class="message">
                        <input disabled type="text" value="${message.id}">
                        <input disabled type="text" name="username" value="${message.user.name}">
                        <input disabled type="text" value="${message.text}">
                        <input disabled type="text" value="${message.rate}">
                        <button class="reply">Reply</button>

                        <#if username == message.user.name || isAdmin>
                            <form class="delete-form" action="/product/deleteMessage/parent" method="post">
                                <input type="hidden" name="id" value="${message.id}">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <input type="submit" value="Delete">
                            </form>
                        </#if>
                        
                        <form class="hidden" action="/product/addChildMessage" method="post">
                            <input type="text" name="text" placeholder="text">
                            <input type="hidden" name="parentMessageId" value="${message.id}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <button type="submit">Comment</button>
                        </form>

                        <#list message.childMessages?reverse as childMessage>
                            <div class="message child-message">
                                <input disabled type="text" value="${childMessage.id}">
                                <input disabled type="text" name="username" value="${childMessage.user.name}">
                                <input disabled type="text" value="${childMessage.text}">
                                <button class="reply">Reply</button>

                                <#if username == childMessage.user.name || isAdmin>
                                    <form class="delete-form" action="/product/deleteMessage/child" method="post">
                                        <input type="hidden" name="id" value="${childMessage.id}">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                                        <input type="submit" value="Delete">
                                    </form>
                                </#if>
                            </div>
                        </#list>
                    </div>
                </#list>
            </div>
        </div>
    </#if>
</#assign>

<#assign edit>
    <#if product??>
        <form name="_csrf">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>

        <div class="row">
            <div class="col-xl-6 col-md-4 imgs">
                <#list product.images as image>
                    <div class="img-cnv">
                        <input type="text" hidden="" name="images" value="${image}" form="product-form">
                        <img src="/img/${image}">
                        <div class="delete">
                            <svg class="bi bi-x-square-fill" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm9.854 4.854a.5.5 0 0 0-.708-.708L8 7.293 4.854 4.146a.5.5 0 1 0-.708.708L7.293 8l-3.147 3.146a.5.5 0 0 0 .708.708L8 8.707l3.146 3.147a.5.5 0 0 0 .708-.708L8.707 8l3.147-3.146z"></path>
                            </svg>
                        </div>
                    </div>
                </#list>

                <div class="img-cnv img-add">
                    <form name="addFiles" enctype="multipart/form-data">
                        <input type="file" name="files" multiple>
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                    </form>
                    <svg class="bi bi-images" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M12.002 4h-10a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V5a1 1 0 0 0-1-1zm-10-1a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2h-10z"/>
                        <path d="M10.648 8.646a.5.5 0 0 1 .577-.093l1.777 1.947V14h-12v-1l2.646-2.354a.5.5 0 0 1 .63-.062l2.66 1.773 3.71-3.71z"/>
                        <path fill-rule="evenodd" d="M4.502 9a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zM4 2h10a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1v1a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2h1a1 1 0 0 1 1-1z"/>
                    </svg>
                </div>
            </div>

            <div class="col-xl-6 col-md-8">
                <form id="product-form" action="/product/edit" enctype="multipart/form-data" method="post">
                    <h1><input type="text" name="name" placeholder="name" value="${product.name}"></h1>
                    <h4><input type="number" name="price" placeholder="price" value="${product.price}"></h4>

                    <hr>

                    <textarea name="description" placeholder="description">${product.description}</textarea>

                    <hr>

                    <select name="categoryId" id="categoryId">
                        <#list categories as category>
                            <#if product.category.id == category.id>-->
                                <option selected value="${category.id}">${category.name}</option>
                            <#else>
                                <option value="${category.id}">${category.name}</option>
                            </#if>
                        </#list>
                    </select>

                    <input type="hidden" name="id" value="${product.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit">Edit</button>
                </form>

            </div>
        </div>
    </#if>
</#assign>

<#assign add>
    <form name="_csrf">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>

    <div class="row">
        <div class="col-xl-6 col-md-4 imgs">
            <div class="img-cnv img-add">
                <form name="addFiles" enctype="multipart/form-data">
                    <input type="file" name="files" multiple>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </form>
                <svg class="bi bi-images"viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" d="M12.002 4h-10a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V5a1 1 0 0 0-1-1zm-10-1a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2h-10z"/>
                    <path d="M10.648 8.646a.5.5 0 0 1 .577-.093l1.777 1.947V14h-12v-1l2.646-2.354a.5.5 0 0 1 .63-.062l2.66 1.773 3.71-3.71z"/>
                    <path fill-rule="evenodd" d="M4.502 9a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zM4 2h10a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1v1a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2h1a1 1 0 0 1 1-1z"/>
                </svg>
            </div>
        </div>
        <div class="col-xl-6 col-md-8">
            <form id="product-form" action="/product/add" enctype="multipart/form-data" method="post">
                <h1><input type="text" name="name" placeholder="name"></h1>
                <h4><input type="number" name="price" placeholder="price"></h4>

                <hr>

                <textarea name="description" placeholder="description"></textarea>

                <hr>

                <select name="categoryId" id="categoryId">
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </select>

                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit">Add</button>
            </form>

        </div>
    </div>
</#assign>

<@base.base title="Product" headContext=headContext useAdminPanel=useAdminPanel adminDropdownContext=adminDropdownContext>
    <#if product??>
        <#if isEdit>
            ${edit}
        <#else >
            ${view}
        </#if>
    <#else >
        ${add}
    </#if>

    <script src="/static/app/js/product.js"></script>
</@base.base>