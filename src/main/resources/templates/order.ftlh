<#import "parts/base.ftlh" as base>
<#include "parts/security.ftlh">

<#assign headContext>

</#assign>

<#assign scripts>

</#assign>

<@base.base title="Profile" headContext=headContext scripts=scripts>
    <div class="row">
        <div class="col-7">
            <form action="/order" method="post">
                <label>
                    <input type="tel" name="phoneNumber" placeholder="phone number">
                </label>

                <label>
                    <select name="deliveryType" required>
                        <option value="pickup">Pickup</option>
                        <option value="Courier">Courier</option>
                    </select>
                </label>

                <label id="address">
                    <input type="text" name="address" placeholder="Address">
                </label>

                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit">
            </form>
        </div>
        <div class="col-5">
            <h3>Card</h3>
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
                <#list user.card as product>
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
</@base.base>