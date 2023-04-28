<%@page import="ict.bean.GuestList"%>
<%@page import="ict.bean.Guest"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>


<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Guest List</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />

        <script src="https://cdn.tailwindcss.com"></script>

        <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
        <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"
    ></script>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
        

        <style>
            .btn-primary {
                background-color: #3b71ca !important;
            }

            input {
                border : 1px solid #e0e0e0;
                border-radius: 0.375rem;
            }
        </style>
    </head>
    <style>
        .nav-hover:hover {
            font-size: 1.125rem;
            line-height: 1.75rem;
        }

    </style>
    <script>
        $(document).ready(function () {
            var params = new window.URLSearchParams(window.location.search);
            var bookingId = params.get('bookingId');
            var venueId = params.get('venueId');

            $("#search-button").click(function () {
                var search = $("#search-input").val();
                window.location.href = "viewGuests?action=search&bookingId=" + bookingId + "&venueId=" + venueId + "&search=" + search;
            });

            //key press enter
            $("#search-input").keypress(function (e) {
                if (e.which == 13) {
                    var search = $("#search-input").val();
                    window.location.href = "viewGuests?search&bookingId=" + bookingId + "&venueId=" + venueId + "&search=" + search;
                }
            });

            $(".form-control").focus(function () {
                $(this).parent().find($(".form-label")).addClass("bg-white");
                $(this).addClass("border-2 border-primary active");
            });

            $(".form-control").blur(function () {
                $(this).parent().find($(".form-label")).removeClass("bg-white");
                if ($(this).val() == "") {
                    $(this).removeClass("border-2 border-primary active");
                } else {
                    $(this).removeClass("border-2 border-primary");
                }
            });

            // show the modal
            if (params.get('action') == "edit") {
                $("#editModal").modal("show");
            }
        });
    </script>
    <%
        String role = (String) session.getAttribute("role");
        if (role == null) {
            response.sendRedirect("");
        }
        GuestList guests = (GuestList) request.getSession().getAttribute("guests");
    %>

    <jsp:useBean id="guest" class="ict.bean.Guest" scope="request" />

    <body style="background-color: #f2f2f2;">
        <jsp:include page="header.jsp" />


        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="errorModalLabel">
            <div class="modal-dialog modal-dialog-centered"  style="width:20rem;height: 20rem; ">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="errorModalLabel">
                            Edit Guest
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                            onclick=""></button>
                    </div>
                    <% if (guest != null) { %>
                        <form action="<%=request.getContextPath()%>/editGuest" method="post">
                            <div class="modal-body">
                                <input type="hidden" name="id" value="<%=guest.getId()%>">
                                <input type="hidden" name="action" value="tempListUpdate">

                                <div class="form-outline mt-3 mb-5">
                                    <input id="editGuestName" name="editGuestName" type="text" class="active form-control border rounded-start" value="<%=guest.getName()%>" />
                                    <label class="form-label" for="search">Guest Name</label>
                                </div>

                                <div class="form-outline mb-3">
                                    <input id="editGuestEmail" name="editGuestEmail" type="text" class="active form-control border rounded-start" value="<%=guest.getEmail()%>" />
                                    <label class="form-label" for="search">Guest Email</label>
                                </div>
                                    
                            </div>
                            <div class="modal-footer">
                                <a href="<%=request.getContextPath()%>/viewGuests?action=search&bookingId=<%=request.getParameter("bookingId")%>&venueId=<%=request.getParameter("venueId")%>" class="btn btn-secondary" >Cancel</a>
                                <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">Save</button>
                            </div>
                        </form>
                    <% } %>
                </div>
            </div>
        </div>

        <section class="p-5">
            <div class="fw-bold fs-5 my-3"><a href="findVenue">Find venues </a>/ <a href="<%= request.getContextPath()%>/getCart?action=cart">Cart</a> / <span class="text-decoration-underline">Guests List</span></div>
            </div>
            </div>
            <ict:result />
            <div class="bg-white p-3 border card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="m-2">Guests</h5>
                    <div class="w-full flex flex-row justify-content-end">
                        <div class="input-group" style="width: 30%;">
                            <div class="form-outline">
                                <input id="search-input" type="search" class="form-control border rounded-start" />
                                <label class="form-label" for="search">Search by guest name or email</label>
                            </div>
                            <button id="search-button" type="button" class="btn btn-primary">
                                <i class="fas fa-search"></i>
                            </button>
                        </div>

                        <script>
                            // set listener to close the #guestBox when clicked outside
                            window.addEventListener('mouseup', function(event){
                                var box = document.getElementById('guestBox');
                                
                                // get all elements that are inside the box
                                var elements = box.getElementsByTagName('*');
                                var isInside = false;
                                for (var i = 0; i < elements.length; i++) {
                                    if (event.target == elements[i]) {
                                        isInside = true;
                                        break;
                                    }
                                }

                                if (!isInside) {
                                    box.classList.add('hidden');
                                }
                            });
                        </script>
                        <div class="relative">
                            <div class="btn btn-primary ml-3" onclick="document.getElementById('guestBox').classList.toggle('hidden');">
                                Add Guest
                            </div>

                            <div class="card absolute w-[30rem]  bg-white -left-[22rem] top-[3rem] z-[100] hidden" id="guestBox">

                                <div class="card-header">
                                    <h5 class="m-2">Add Guest</h5>
                                </div>

                                <div class="card-body h-fit overflow-y-auto">

                                    <ul class="list-group list-group-light h-full max-h-[25rem]" id="guestList">
                                        <% if (request.getAttribute("guestsNotOnList") != null && !((ArrayList<Guest>) request.getAttribute("guestsNotOnList")).isEmpty()) { %>
                                            <%
                                                ArrayList<Guest> guestsNotOnList = (ArrayList<Guest>) request.getAttribute("guestsNotOnList");
                                                for (Guest guestNotOnList : guestsNotOnList) {
                                            %>
                                                <form action="<%=request.getContextPath()%>/addGuest" method="post">

                                                    <li class="list-group-item d-flex justify-content-between align-items-center"  style="border:none">
                                                        <input id="guestName" type="hidden" name="name" value="<%=guestNotOnList.getName()%>" />
                                                        <input id="guestEmail" type="hidden" name="email" value="<%=guestNotOnList.getEmail()%>"  />
                                                        <input type="hidden" name="action" value="tempListUpdate">

                                                        <div class="d-flex align-items-center">
                                                            <div class="ms-3">
                                                                <p class="fw-bold mb-1"><%=guestNotOnList.getName()%></p>
                                                                <p class="text-muted mb-0"><%=guestNotOnList.getEmail()%></p>
                                                            </div>
                                                        </div>

                                                        <button class="btn btn-link btn-rounded btn-sm" type="submit">Add</button>

                                                    </li>
                                                </form>
                                        <% } } else { %>
                                            There are no saved guests to add.
                                        <% } %>
                                    </ul>

                                        

                                    <div id="newGuest" class="hidden ">
                                        <form action='<%=request.getContextPath()%>/addGuest' method="post">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="bookingId" value="<%=request.getParameter("bookingId")%>">
                                            <input type="hidden" name="venueId" value="<%=request.getParameter("venueId")%>">

                                            <div class="col-5 w-100">
                                                <div class="form-outline mb-4">
                                                    <input required type="text" id="guestName" name="name"
                                                        class="form-control border" />
                                                    <label class="form-label" for="guestName">Name</label>
                                                </div>
    
                                                <div class="form-outline mb-4">
                                                    <input required type="text" id="guestEmail" name="email"
                                                        class="form-control border" />
                                                    <label class="form-label" for="guestEmail">Email</label>
                                                </div>
                                                <!-- style="margin-left: 75%;"   -->
                                                <button type="submit" class="btn btn-primary">
                                                    Add New
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <div class="card-footer flex flex-row justify-content-end">
                                    <div class="btn btn-secondary mx-2" id="test1" onclick="document.getElementById('guestList').classList.add('hidden');document.getElementById('newGuest').classList.remove('hidden');">
                                        New Guest
                                    </div>
                                    <div class="btn btn-secondary mx-2" id="test2" onclick="document.getElementById('guestList').classList.remove('hidden');document.getElementById('newGuest').classList.add('hidden');">
                                        Existing Guest
                                    </div> 
                      
                                </div>

                            </div>
                        </div>

                        <a class="btn btn-primary ml-3" href="<%=request.getContextPath()%>/getCart?action=cart">
                            Create Guest List
                        </a>
    
                    </div>

                </div>
              
                <div class="table-responsive text-nowrap mt-3 fs-6">

                    <table class="table text-center table-striped">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Email</th>
                                <% if ("Member".equalsIgnoreCase(role)) { %>
                                    <th>Action</th>
                                <% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (guest!=null && guests.getGuests() != null) {
                                for (int i = 1; i <= guests.getGuests().size(); i++) {
                                    Guest a = guests.getGuests().get(i - 1);
                            %>
                                <tr class="align-middle">
                                    <th scope="row"><%=i%></th>
                                    <td><%=a.getName()%></td>
                                    <td><%=a.getEmail()%></td>
                                    <td>
                                        <a class="btn btn-danger btn-rounded btn-sm" role="button" href="<%=request.getContextPath()%>/delGuest?action=delete&guestId=<%=a.getId()%>">Delete</a>
                                    </td>
                                </tr>
                            <% }} %>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </body>

</html>