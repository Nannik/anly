<#import "parts/base.ftlh" as base>

<#assign headContext>

</#assign>

<@base.base title="Users" headContext=headContext>
    <div class="row">
        <div class="col">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Password</th>
                        <th scope="col">Email</th>
                        <th scope="col">Activated</th>
                        <th scope="col">Roles</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <#list users as user>
                        <tr>
                            <th>${user_index}</th>
                            <td>${user.name}</td>
                            <td>${user.password}</td>
                            <td>${user.email}</td>
                            <td>${user.isActivated()?string("true", "false")}</td>
                            <td>
                                <#list user.roles as role>${role}<#sep>, </#sep></#list>
                            </td>
                            <td>
                                <a href="/user/${user.id}">
                                    <svg class="bi bi-tools" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                        <path fill-rule="evenodd" d="M0 1l1-1 3.081 2.2a1 1 0 0 1 .419.815v.07a1 1 0 0 0 .293.708L10.5 9.5l.914-.305a1 1 0 0 1 1.023.242l3.356 3.356a1 1 0 0 1 0 1.414l-1.586 1.586a1 1 0 0 1-1.414 0l-3.356-3.356a1 1 0 0 1-.242-1.023L9.5 10.5 3.793 4.793a1 1 0 0 0-.707-.293h-.071a1 1 0 0 1-.814-.419L0 1zm11.354 9.646a.5.5 0 0 0-.708.708l3 3a.5.5 0 0 0 .708-.708l-3-3z"/>
                                        <path fill-rule="evenodd" d="M15.898 2.223a3.003 3.003 0 0 1-3.679 3.674L5.878 12.15a3 3 0 1 1-2.027-2.027l6.252-6.341A3 3 0 0 1 13.778.1l-2.142 2.142L12 4l1.757.364 2.141-2.141zm-13.37 9.019L3.001 11l.471.242.529.026.287.445.445.287.026.529L5 13l-.242.471-.026.529-.445.287-.287.445-.529.026L3 15l-.471-.242L2 14.732l-.287-.445L1.268 14l-.026-.529L1 13l.242-.471.026-.529.445-.287.287-.445.529-.026z"/>
                                    </svg>
                                </a>
                            </td>
                            <td>
                                <form action="/user/delete" method="post">
                                    <input type="hidden" name="id" value="${user.id}">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                    <button type="submit" value="Submit">
                                        <svg class="bi bi-trash-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                        </svg>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</@base.base>