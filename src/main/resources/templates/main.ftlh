<#include "parts/security.ftlh">
<#import "parts/base.ftlh" as base>

<#if !categoryId??>
    <#assign categoryId = 0>
</#if>

<#assign headContext>
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-173570722-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', '---ID---');
    </script>
</#assign>

<#assign adminDropdownContext>
    <a class="dropdown-item" href="/category/add">AddCategory</a>
    <#if categoryId != 0>
        <a class="dropdown-item" href="/category/edit/${categoryId}">EditCategory</a>
        <form class="dropdown-item" action="/category/delete" method="post">
            <input type="hidden" name="id" value="${categoryId}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="DeleteCategory">
        </form>
    </#if>
    <a class="dropdown-item" href="/product/add">AddProduct</a>
</#assign>


<@base.base title="Main" headContext=headContext useAdminPanel=true adminDropdownContext=adminDropdownContext>

    <div class="row">
        <div class="col">
            <table class="table table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                    <th scope="col">Category</th>
                    <th scope="col">#</th>
                    <th scope="col">#</th>
                    <#if isAdmin><th scope="col">#</th></#if>
                </tr>
                </thead>
                <tbody>
                    <#list products as product>
                        <tr>
                            <th>${product.id}</th>
                            <th><a href="/product/${product.id}">${product.name}</a></th>
                            <th>${product.description}</th>
                            <th>${product.price}</th>
                            <th>${product.category.name}</th>
                            <th>
                                <#if user?? && user.desired?map(product -> product.id)?seq_contains(product.id)>
                                    <a href="/removeFromDesired?productId=${product.id}">
                                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-heart-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
                                        </svg>
                                    </a>
                                <#else >
                                    <a href="/addToDesired?productId=${product.id}">
                                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-heart" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
                                        </svg>
                                    </a>
                                </#if>

                            <th>
                                <#if user?? && user.card?map(product -> product.id)?seq_contains(product.id)>
                                    <a href="/removeAllFromCard?productId=${product.id}">
                                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-cart-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                                        </svg>
                                    </a>
                                <#else >
                                    <a href="/addToCard?productId=${product.id}">
                                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-cart-plus" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M8.5 5a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M8 7.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0v-2z"/>
                                            <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                                        </svg>
                                    </a>
                                </#if>
                            </th>
                            <#if isAdmin>
                                <th>
                                    <form action="/product/delete" method="post">
                                        <input type="hidden" name="id" value="${product.id}">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                                        <button type="submit">Delete</button>
                                    </form>
                                </th>
                            </#if>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</@base.base>