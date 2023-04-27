<% if ( request.getAttribute("attendance") == null ) { 
    // forward to /api/report/bookingarate
    request.getRequestDispatcher("/api/report/bookingarate").forward(request, response);
} %>

<%@page import="ict.bean.view.MemberAttendanceDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<% String role = (String)session.getAttribute("role"); %>
<section>
    <div class="card">
        <div class="card-header flex flex-row items-center">
            <h3 class="text-2xl text-dark">Member Booking Attendance</h3>
            <div class="ml-auto my-5"></div>
        </div>
        <div class="card-body">

            <div class="table-responsive text-nowrap mt-3 fs-6">
                <%
                    MemberAttendanceDTO res = (MemberAttendanceDTO) request.getAttribute("attendance");
                    java.util.HashMap< ict.bean.User , java.lang.Integer > map = null;
                    if (res != null) {
                        map = res.getAttendance();
                    }
                %>
                <% if (res != null && map != null) { %>
                    <table class="table text-center table-striped">
                        <thead>
                            <tr>
                                <th>
                                    #
                                </th>
                                <th>
                                    Name
                                </th>
                                <th>
                                    Email
                                </th>
                                <th>
                                    Attendance
                                </th>
                            </tr>
                        </thead>
    
                        <tbody>
    
                            <% for ( int i = 1 ; i <= map.size() ; i++ ) { ict.bean.User u = (ict.bean.User) map.keySet().toArray()[i-1]; java.lang.Integer a = map.get(u); %>
                            <tr class="align-middle">
                                <th scope="row">
                                    <%= i %>
                                </th>
                                <td>
                                    <%= u.getFirstName() + " " + u.getLastName() %>
                                </td>
                                <td>
                                    <%= u.getEmail() %>
                                </td>
                                <td>
                                    <% if ( a >= 85 ) { %>
                                        <span class="badge bg-success">
                                            <%= a %>%
                                        </span>
                                    <% } else if ( a >= 70 ) { %>
                                        <span class="badge bg-warning">
                                            <%= a %>%
                                        </span>
                                    <% } else { %>
                                        <span class="badge bg-danger">
                                            <%= a %>%
                                        </span>
                                    <% } %>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } %>
            </div>

        </div>
    </div>
</section>
