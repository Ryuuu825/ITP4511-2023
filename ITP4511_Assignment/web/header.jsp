<% String role = (String) session.getAttribute("role"); %>
<header
    class="z-3 w-100 d-flex flex-row align-items-center justify-content-between p-3"
    style="background-color: #144272; height: 5rem"
    >
    <div
        class="title text-uppercase ms-3 me-auto fw-bold text-white"
        style="font-size: 1.5rem; line-height: 2rem"
        >
        <a href="index.jsp" class="text-white text-decoration-none">
            Event Point Limited
        </a>
    </div>
    <ul
        class="nav justify-content-end fs-6 fw-semibold flex align-items-center mr-5"
        >
        <% if (role == null || role.equals("Member")) { %>
            <li class="nav-item nav-hover">
                <a
                    class="text-white mx-3 text-decoration-none"
                    href="<%=request.getContextPath()%>/findVenue"
                    >
                    <!-- Browse Spaces -->
                    Find venues
                </a>
            </li>
        <% } else { %>
            <li class="nav-item nav-hover">
                <a class="text-white mx-3 text-decoration-none" href="<%=request.getContextPath()%>/searchVenues">
                    <!-- Browse Spaces -->
                    Venue
                </a>
            </li>
        <% } %>

        <% if (role != null && ! role.equals("Member")) { %>
            <li class="nav-item border-start nav-hover">
                <a
                    class="text-white mx-3 text-decoration-none"
                    href="<%=request.getContextPath()%>/searchTimeslots"
                    >
                    <!-- Browse Spaces -->
                    Timeslot
                </a>
            </li>
        <% } %>
        <li class="nav-item border-start nav-hover">
            <% if (role == null || role.equals("Member")) { %>
                <a
                    class="text-white mx-3 text-decoration-none"
                    href="<%=request.getContextPath()%>/member/booking"
                    >
                    My Booking
                </a>
            <% } else { %>
                <a
                    class="text-white mx-3 text-decoration-none"
                    href="<%=request.getContextPath()%>/searchBookings"
                    >
                    Booking
                </a>
            <% } %>
        </li>
        <% if (role != null) { %>
            <li class="nav-item border bg-light rounded-1 py-1">
                <a class="mx-3 text-dark text-decoration-none" href="<%=request.getContextPath()%>/logout">
                    Sign Out
                </a>
            </li>
        <% } else { %>
            <li class="nav-item border bg-light rounded-1 py-1">
                <a class="mx-3 text-dark text-decoration-none" href="<%=request.getContextPath()%>/login.jsp">
                    Log In
                </a>
            </li>
        <% } %>
    </ul>
</header>
