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
    .navbar-brand:hover {
        color: #205295 !important;
    }
</style>
<script>
    $(document).ready(function () {
        $(this).val() != "" ? $(this).addClass('active') : $(this).removeClass('active');
        $('#errorMsg').val() != "" ? $('#errorModal').modal('show') : $('#errorModal').modal('hide');
        $('.form-control').on('focus', function () {
            $(this).addClass('border-primary');
        });
        $('.form-control').on('blur', function () {
            $(this).removeClass('border-primary');
            $(this).val() != "" ? $(this).addClass('active') : $(this).removeClass('active');
        });
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
                <nav class="p-2">
                    <div class="container-fluid">
                        <a title="Back to sign in" class="navbar-brand text-gary-800 fw-bold fs-5" href="login.jsp">
                            <i class="fas fa-circle-left pe-2"></i> Sign in</a>
                    </div>
                </nav>
                <div class="card-body py-4 px-md-5">
                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-6">
                            <h2 class="fw-bold mb-4">Sign up now</h2>
                            <form action="handleRegister" method="post">
                                <input type="hidden" name="action" value="register" />
                                <!-- Username input -->
                                <div class="form-outline mb-4">
                                    <input type="text" name="username" id="username" required aria-required="true"
                                        class="form-control border" />
                                    <label class="form-label" for="username">Username</label>
                                </div>
                                <!-- Password input -->
                                <div class="form-outline mb-4">
                                    <input type="password" name="password" id="password" required aria-required="true"
                                        class="form-control border" />
                                    <label class="form-label" for="password">Password</label>
                                </div>
                                <!-- 2 column grid layout with text inputs for the first and last names -->
                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <div class="form-outline">
                                            <input type="text" name="firstName" id="firstName" required
                                                aria-required="true" class="form-control border" />
                                            <label class="form-label" for="firstName">First name</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-4">
                                        <div class="form-outline">
                                            <input type="text" name="lastName" id="lastName"
                                                class="form-control border" />
                                            <label class="form-label" for="lastName">Last name</label>
                                        </div>
                                    </div>
                                </div>

                                <!-- Email input -->
                                <div class="form-outline mb-4">
                                    <input type="email" name="email" id="email" class="form-control border" />
                                    <label class="form-label" for="email">Email address</label>
                                </div>

                                <!-- Phone input -->
                                <div class="form-outline mb-4">
                                    <input type="tel" id="phone" name="phone" class="form-control border" />
                                    <label for="phone" class="form-label">Phone number</label>
                                </div>

                                <!-- Submit button -->
                                <button type="submit" class="btn btn-primary btn-block mb-4">
                                    Sign up
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>

</html>