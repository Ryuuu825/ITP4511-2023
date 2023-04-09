<!-- <%-- 
   Document   : login
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> -->
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
        integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous">
    </script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
</head>
<style>
    .navbar-link:hover {
        color: #205295 !important;
    }

    .wrong .fa-check {
        display: none;
    }

    .good .fa-times {
        display: none;
    }
</style>
<script>
    $(document).ready(function () {
        var time = 3;
        var timer = setInterval(function () {
            time--;
            if (time == 0) {
                clearInterval(timer);
                window.location.href = "login.jsp";
            }
            $(".text-primary").text(time);
        }, 1000);
    });
</script>

<body>
    <input type="hidden" value="${error}" id="errorMsg" />
    <section class="text-center">
        <!-- Background image -->
        <div class="p-5 bg-image" style="
                 background-image: url('assets/img/HongKong.png');
                 height: 300px;"></div>
        <!-- Background image -->
        <div class="d-flex justify-content-center">
            <div class="card mb-2 mx-md-5 shadow-5-strong" style="
                     margin-top: -150px;
                     background: hsla(0, 0%, 100%, 0.8);
                     backdrop-filter: blur(30px);
                     width: 60%;">
                <div class="card-body py-4 px-md-5" style="margin-top: 4rem;margin-bottom: 4rem;">
                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-6">
                            <!-- Back to sign up -->
                            <div class="mb-4">
                                <div class="">
                                    <i class="far fa-circle-check fa-8x text-success"></i>
                                </div>
                            </div>
                            <h2 class="fw-bold mb-4">Congratulations!</h2>
                            <h4>Account created successfully. </h4>
                            <span>After <b class="text-primary text-decoration-underline fs-5">3</b> seconds, you will be redirected to the login page.</span>
                            <span>You can also click the
                                <a title="Back to sign in" class="text-decoration-underline navbar-link text-gary-800 fw-bold fs-5"
                                    href="login.jsp">Sign up</a> to go to the login page.
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>

</html>