<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
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
        
        $(this).val() != "" ? $(this).addClass('active') : $(this).removeClass('active');
        $('#errorMsg').val() != "" ? $('#errorModal').modal('show') : $('#errorModal').modal('hide');
        $('.form-control').on('focus', function () {
            $(this).addClass('border-primary');
            $(".form-label").addClass("bg-white");
            $(this).parent().find('.form-label').addClass('text-primary');
        });
        $('.form-control').on('blur', function () {
            $(this).removeClass('border-primary');
            $(".form-label").removeClass("bg-white");
            $(this).val() != "" ? $(this).addClass('active') : $(this).removeClass('active');
            $(this).parent().find('.form-label').removeClass('text-primary');
        });

        // const requirements = document.querySelectorAll(".requirements");
        let lengBoolean, bigLetterBoolean, numBoolean, specialCharBoolean;
        const specialChars = "!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?`~";
        const numbers = "0123456789";

        // requirements.forEach((element) => element.classList.add("wrong"));
        $(".requirements").addClass("wrong");
        $('#password-alert').addClass("alert-warning");
        $('#password-input').on('focus', function () {
            $('#password-alert').removeClass("d-none");
            if (!$(this).hasClass("is-valid")) {
                $(this).addClass("is-invalid");
            }

            $(this).addClass('text-primary');
        });

        $('#password-input').on('input', function () {
            console.log($(this).val());
            let value = $(this).val();
            if (value.length < 8) {
                lengBoolean = false;
            } else if (value.length > 7) {
                lengBoolean = true;
            }

            if (value.toLowerCase() == value) {
                bigLetterBoolean = false;
            } else {
                bigLetterBoolean = true;
            }

            numBoolean = false;
            for (let i = 0; i < value.length; i++) {
                for (let j = 0; j < numbers.length; j++) {
                    if (value[i] == numbers[j]) {
                        numBoolean = true;
                    }
                }
            }

            specialCharBoolean = false;
            for (let i = 0; i < value.length; i++) {
                for (let j = 0; j < specialChars.length; j++) {
                    if (value[i] == specialChars[j]) {
                        specialCharBoolean = true;
                    }
                }
            }

            if (lengBoolean == true && bigLetterBoolean == true && numBoolean == true &&
                specialCharBoolean == true) {
                $(this).removeClass("is-invalid");
                $(this).addClass("is-valid");

                // requirements.forEach((element) => {
                //     element.classList.remove("wrong");
                //     element.classList.add("good");
                // });
                $(".requirements").removeClass("wrong");
                $(".requirements").addClass("good");
                $('#password-alert').removeClass("alert-warning");
                $('#password-alert').addClass("alert-success");
            } else {
                $(this).removeClass("is-valid");
                $(this).addClass("is-invalid");

                $('#password-alert').addClass("alert-warning");
                $('#password-alert').removeClass("alert-success");

                if (lengBoolean == false) {
                    $('.leng').addClass("wrong");
                    $('.leng').removeClass("good");
                } else {
                    $('.leng').addClass("good");
                    $('.leng').removeClass("wrong");
                }

                if (bigLetterBoolean == false) {
                    $('.big-letter').addClass("wrong");
                    $('.big-letter').removeClass("good");
                } else {
                    $('.big-letter').addClass("good");
                    $('.big-letter').removeClass("wrong");
                }

                if (numBoolean == false) {
                    $('.num').addClass("wrong");
                    $('.num').removeClass("good");
                } else {
                    $('.num').addClass("good");
                    $('.num').removeClass("wrong");
                }

                if (specialCharBoolean == false) {
                    $('.special-char').addClass("wrong");
                    $('.special-char').removeClass("good");
                } else {
                    $('.special-char').addClass("good");
                    $('.special-char').removeClass("wrong");
                }
            }
        });

        $('#password-input').on('blur', function () {
            $('#password-alert').addClass("d-none");
        });

        // validation phone number like 1234-5678
        $('#phone').on('input', function () {
            let value = $(this).val();
            let regex = /^\d{4}-\d{4}$/;
            if (regex.test(value)) {
                $(this).removeClass("is-invalid");
                $(this).addClass("is-valid");
            } else {
                $(this).removeClass("is-valid");
                $(this).addClass("is-invalid");
            }
        });

        // validation email
        $('#email').on('input', function () {
            let value = $(this).val();
            let regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            if (regex.test(value)) {
                $(this).removeClass("is-invalid");
                $(this).addClass("is-valid");
            } else {
                $(this).removeClass("is-valid");
                $(this).addClass("is-invalid");
            }
        });

        // validation username
        $('#username').on('input', function () {
            let value = $(this).val();
            let regex = /^[a-zA-Z0-9._-]{3,16}$/;
            if (regex.test(value)) {
                $(this).removeClass("is-invalid");
                $(this).addClass("is-valid");
            } else {
                $(this).removeClass("is-valid");
                $(this).addClass("is-invalid");
            }
        });

        // validation first name
        $('#firstName').on('input', function () {
            let value = $(this).val();
            let regex = /^[a-zA-Z]{3,16}$/;
            if (regex.test(value)) {
                $(this).removeClass("is-invalid");
                $(this).addClass("is-valid");
            } else {
                $(this).removeClass("is-valid");
                $(this).addClass("is-invalid");
            }
        });

        // validation last name
        $('#lastName').on('input', function () {
            let value = $(this).val();
            let regex = /^[a-zA-Z]{3,16}$/;
            if (regex.test(value)) {
                $(this).removeClass("is-invalid");
                $(this).addClass("is-valid");
            } else {
                $(this).removeClass("is-valid");
                $(this).addClass("is-invalid");
            }
        });

        // validation all input
        $('#sign-up-form').on('submit', function (e) {
            e.preventDefault();
            let inputs = document.querySelectorAll('input');
            let valid = true;
            inputs.forEach((element) => {
                if (element.classList.contains("is-invalid")) {
                    valid = false;
                }
            });
            if (valid == true) {
                this.submit();
            }
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
                <div class="p-2">
                    <div class="d-flex justify-content-start container-fluid">
                        <a title="Back to sign in" class="navbar-link text-gary-800 fw-bold fs-5" href="login.jsp">
                            <i class="fas fa-circle-left pe-2"></i> Sign in</a>
                    </div>
                </div>
                <div class="card-body py-4 px-md-5">
                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-6">
                            <h2 class="fw-bold mb-4">Sign up now</h2>
                            <form action="handleRegister" method="post" id="sign-up-form">
                                <input type="hidden" name="action" value="register" />
                                <!-- Username input -->
                                <div class="form-outline mb-4">
                                    <input type="text" name="username" id="username" required aria-required="true"
                                        class="form-control border" />
                                    <label class="form-label" for="username">Username</label>
                                </div>
                                <!-- Password input -->
                                <div class="form-outline mb-4">

                                    <div class="row">
                                        <div>
                                            <div class="justify-content-start input-group d-flex">
                                                <input type="password" name="password" id="password-input" required
                                                    aria-required="true" class="form-control border" />
                                                <label class="form-label" for="password-input">Password</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="mt-2">
                                        <div class="mt-4 mt-xxl-0 w-auto h-auto">
                                            <div class="alert px-2 py-3 mb-0 d-none" role="alert"
                                                data-mdb-color="warning" id="password-alert">
                                                <ul
                                                    class="d-flex text-start align-items-start flex-column list-unstyled mb-0">
                                                    <li class="requirements leng">
                                                        <i class="fas fa-check text-success me-2"></i>
                                                        <i class="fas fa-times text-danger me-3"></i>
                                                        Your password must have at least 8 chars</li>
                                                    <li class="requirements big-letter">
                                                        <i class="fas fa-check text-success me-2"></i>
                                                        <i class="fas fa-times text-danger me-3"></i>
                                                        Your password must have at least 1 upper letter.</li>
                                                    <li class="requirements num">
                                                        <i class="fas fa-check text-success me-2"></i>
                                                        <i class="fas fa-times text-danger me-3"></i>
                                                        Your password must have at least 1 number.</li>
                                                    <li class="requirements special-char">
                                                        <i class="fas fa-check text-success me-2"></i>
                                                        <i class="fas fa-times text-danger me-3"></i>
                                                        Your password must have at least 1 special char.</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 2 column grid layout with text inputs for the first and last names -->
                                <div class="row mb-4">
                                    <div class="col-md-6">
                                        <div class="form-outline">
                                            <input type="text" name="firstName" id="firstName" required
                                                aria-required="true" class="form-control border" />
                                            <label class="form-label" for="firstName">First name</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-outline">
                                            <input type="text" name="lastName" id="lastName"
                                                class="form-control border"/>
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
                                    <input type="text" id="phone" name="phone" class="form-control border" pattern="^\d{4}-\d{4}$"/>
                                    <label for="phone" class="form-label">Phone number(e.g. 1234-5678)</label>
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