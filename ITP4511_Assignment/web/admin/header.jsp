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
        <li class="nav-item nav-hover">
            <a
                class="text-white mx-3 text-decoration-none"
                href="<%=request.getContextPath()%>/admin/users.jsp"
            >
                <!-- Browse Spaces -->
                Users
            </a>
        </li>

        <li class="nav-item nav-hover">
            <a
                class="text-white mx-3 text-decoration-none"
                href="<%=request.getContextPath()%>/admin/report.jsp"
            >
                <!-- Browse Spaces -->
                Report
            </a>
        </li>

        <li class="nav-item border bg-light rounded-1 py-1">
            <a class="mx-3 text-dark text-decoration-none" href="login.jsp">
                Sign Out
            </a>
        </li>
    </ul>
</header>
