<#import "parts/base.ftlh" as base>
<#include "parts/security.ftlh">

<#assign headContext>

</#assign>

<#assign scripts>
    <script src="/static/app/js/profile.js"></script>
</#assign>

<@base.base title="Profile" headContext=headContext scripts=scripts>
    <div class="row">
        <div class="col">
            <#if !user.isActivated()>
                <h3>Account is not activated <a href="/sendActivationCode">send activation code</a></h3>
            </#if>
            <div class="name">Username: ${user.name}</div>
            <#if user.email??><div class="email">Email: ${user.email}</div></#if>

            <a href="/changePassword">Change password</a>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <h3>Desired</h3>
            <table class="table table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                    <th scope="col">Category</th>
                </tr>
                </thead>
                <tbody>
                <#list user.desired as product>
                    <tr>
                        <th>${product.id}</th>
                        <th><a href="/product/${product.id}">${product.name}</a></th>
                        <th>${product.description}</th>
                        <th>${product.price}</th>
                        <th>${product.category.name}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <h3>Card</h3>
            <a href="/order">To order</a>

            <table class="table table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                    <th scope="col">Category</th>
                    <th scope="col">Size</th>
                    <th scope="col">#</th>
                    <th scope="col">#</th>
                </tr>
                </thead>
                <tbody>
                <#list user.getUniqueProductsInCard() as product>
                    <tr>
                        <th>${product.id}</th>
                        <th><a href="/product/${product.id}">${product.name}</a></th>
                        <th>${product.description}</th>
                        <th>${product.price}</th>
                        <th>${product.category.name}</th>
                        <th>${user.getProductInCardQuantity(product)}</th>
                        <th><a href="/addToCard?productId=${product.id}">+</a></th>
                        <th><a href="/removeFromCard?productId=${product.id}">-</a></th>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <h3>Orders</h3>
            <table class="table table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Number</th>
                    <th scope="col">Price</th>
                    <th scope="col">Size</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                <#list user.orders as order>
                    <tr>
                        <th>${order.id}</th>
                        <th>${order.orderNumber}</th>
                        <th>${order.price}</th>
                        <th>${order.products?size}</th>
                        <th>${order.status}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>


</@base.base>