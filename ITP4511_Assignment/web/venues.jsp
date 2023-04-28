<%@page import="ict.bean.view.VenueDTO"%>
<%@page import="ict.bean.Venue"%>
<%@page import="ict.bean.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Venues</title>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
        integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
        integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous">
    </script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
</head>
<style>
    .nav-hover:hover {
        font-size: 1.125rem;
        line-height: 1.75rem;
    }

    .modal {
        --mdb-modal-width: 1024px;
    }

    .imgbox-hover:hover {
        transform: scale(1.01);
        cursor: pointer;
    }

    .select-arrow {
        color: var(--mdb-form-outline-select-arrow-color);
        text-align: center;
        font-size: var(--mdb-form-outline-select-arrow-font-size);
        position: absolute;
        top: var(--mdb-form-outline-select-arrow-top);
        right: var(--mdb-form-outline-select-arrow-right)
    }

    .select-arrow:after {
        display: inline-block;
        margin-left: .255em;
        vertical-align: .255em;
        content: "";
        border-top: .3em solid;
        border-right: .3em solid transparent;
        border-bottom: 0;
        border-left: .3em solid transparent
    }
</style>
<script>
    $(document).ready(function () {
        var params = new window.URLSearchParams(window.location.search);
        var action = params.get('action');
        action == "edit" ? editVenue() : "";
        $(".form-control").focus(function () {
            $(".form-label").addClass("bg-white");
            $(this).addClass("border-2 border-primary active");
        });

        $(".form-control").blur(function () {
            $(".form-label").removeClass("bg-white");
            $(this).addClass("border-2 border-primary active");
            if ($(this).val() === "") {
                $(this).removeClass("border-2 border-primary active");
            } else {
                $(this).removeClass("border-2 border-primary");
            }
        });

        $("#search-button").click(function () {
            var search = $("#search-input").val();
            location.href = "searchVenues?action=search&search=" + search;
        });
        //key press enter
        $("#search-input").keypress(function (e) {
            if (e.which === 13) {
                var search = $("#search-input").val();
                location.href = "searchVenues?action=search&search=" + search;
            }
        });

        function editVenue() {
            $('#venueModal').modal('show');
        };

        $("#addvenue").click(function () {
            $('#venueModal').modal('show');
            $('.modal-title').html("Add Venue");
            $('.form-control').val("");
            $('.form-control').removeClass("active");
        });

        $("#venueModal").on('hidden.bs.modal', () => {
            location.href = 'searchVenues';
        })

        $(".form-control").val() !== "" ? $(".form-control").addClass("active") : "";
        var staff = $('#selected-staff').val();
        $("#select-staff").val(staff).change();
        var district = $('#selected-district').val();
        $("#select-district").val(district).change();

        $(".imgbox-hover").click(function () {
            $("#fileinput").click();
        });

        $("#fileinput").change(function () {
            var file = this.files[0];
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#venue-img").attr('src', e.target.result);
                $("#upload-img").addClass("d-none");
                $("#venue-img").removeClass("d-none");
                $("#changeImage").val(true);
            };
            reader.readAsDataURL(file);
        });

        $('.form-check-input').change(function () {
            if ($(this).is(':checked')) {
                $(this).parent().find($('input:radio[name=enable]')).filter('[value=1]').prop('checked', true);
                $(this).parent().find($('input:radio[name=enable]')).filter('[value=0]').prop('checked', false);
            } else {
                $(this).parent().find($('input:radio[name=enable]')).filter('[value=0]').prop('checked', true);
                $(this).parent().find($('input:radio[name=enable]')).filter('[value=1]').prop('checked', false);
            }
            $(this).parent().submit();
        });
    });
</script>
<%
        String role = (String) session.getAttribute("role");
        if (role == null || "Member".equalsIgnoreCase(role)) {
            response.sendRedirect("");
        }
        ArrayList<VenueDTO> venueDTOs = (ArrayList<VenueDTO>) request.getAttribute("venueDTOs");
        VenueDTO venueDTO = (VenueDTO) request.getAttribute("venueDTO");
%>

<body style="background-color: #f2f2f2;">
    <jsp:include page="header.jsp" />

    <section class="p-5">
        <!-- Modal -->
        <div class="modal fade" id="venueModal" tabindex="-1" aria-labelledby="venueLabel" aria-hidden="true">
            <form class="modal-dialog modal-dialog-centered" action="handleVenue" method="post"
                enctype="multipart/form-data">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><%=venueDTO != null ? "Edit" : "Add"%>
                            Venue
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="action" name="action" value="<%=venueDTO != null ? "update" : "add"%>">
                        <input type="hidden" id="venueId" name="id"
                            value="<%=venueDTO != null ? venueDTO.getVenue().getId() : "0"%>">
                        <div>
                            <div class="row">
                                <div class="col-7">
                                    <input type="file" class="d-none" id="fileinput" name="img" accept="image/*">
                                    <input type="hidden" id="changeImage" name="changeImage" value="false">
                                    <div class="imgbox-hover form-outline card h-100 bg-gray text-center"
                                        title="Click to upload image">
                                        <div id="upload-img"
                                            class="card-img d-flex bg-secondary bg-opacity-25 w-100 h-100 justify-content-center align-items-center
                                                 <%=venueDTO != null && venueDTO.getVenue().getImg() != "" ? "d-none" : "d-block"%>">
                                            Click to upload image
                                        </div>
                                        <img id="venue-img"
                                            src="<%=venueDTO != null ? "assets/" + venueDTO.getVenue().getImg() : ""%>"
                                            class="card-img w-100 h-100 <%=venueDTO != null && venueDTO.getVenue().getImg() != "" ? "d-block" : "d-none"%>"
                                            alt="Venue image">
                                    </div>
                                </div>
                                <div class="col-5">
                                    <div class="form-outline mb-4">
                                        <input required type="text" id="venueName" name="name"
                                            class="form-control border"
                                            value="<%=venueDTO != null ? venueDTO.getVenue().getName() : ""%>" />
                                        <label class="form-label" for="venueName">Name</label>
                                    </div>
                                    <div class="row">

                                        <div class="col">
                                            <div class="form-outline mb-4">
                                                <input required type="number" id="capacity" name="capacity"
                                                    class="form-control border"
                                                    value="<%=venueDTO != null ? venueDTO.getVenue().getCapacity() : ""%>" />
                                                <label class="form-label" for="capacity">Capacity</label>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-outline mb-4 position-relative">
                                                <select required class="form-control border" name="type"
                                                    id="select-type" aria-label="select"
                                                    value="<%=venueDTO != null ? venueDTO.getVenue().getType() : ""%>">
                                                    <%
                                                            String[] types = ict.bean.Venue.typeStrings;
                                                            for (int i = 1; i <= types.length; i++) {
                                                                if (venueDTO != null && venueDTO.getVenue().getType() == i) {
                                                                    out.print("<option selected value=\"" + i + "\">" + types[i - 1] + "</option>");
                                                                } else {
                                                                    out.print("<option value=\"" + i + "\">" + types[i - 1] + "</option>");
                                                                }
                                                            }
                                                        %>
                                                </select>
                                                <label class="form-label" for="select-type">Type</label>
                                                <span class="select-arrow position-absolute"
                                                    style="top:25%; right: 1rem;"></span>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-outline mb-4">
                                                <input required type="number" id="hourlyRate" name="hourlyRate"
                                                    class="form-control border"
                                                    value="<%=venueDTO != null ? venueDTO.getVenue().getHourlyRate() : ""%>" />
                                                <label class="form-label" for="hourlyRate">HourlyRate</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-outline mb-4 position-relative">
                                        <input type="hidden" id="selected-staff"
                                            value="<%=venueDTO != null ? venueDTO.getUser().getId() : "0"%>">
                                        <select required class="form-select border form-control" name="staff"
                                            id="select-staff" aria-label="select">
                                            <option value="0">None</option>
                                            <%
                                                    ArrayList<User> staff = (ArrayList<User>) request.getAttribute("staff");
                                                    for (int i = 1; i <= staff.size(); i++) {
                                                        out.print("<option value=\"" + staff.get(i - 1).getId() + "\">" + staff.get(i - 1).getFirstName() + " " + staff.get(i - 1).getLastName() + "</option>");
                                                    }
                                                %>
                                        </select>
                                        <label class="form-label" for="select-staff">Person in charge</label>
                                        <span class="select-arrow position-absolute"
                                            style="top:25%; right: 1rem;"></span>
                                    </div>
                                    <div class="form-outline mb-4 position-relative">
                                        <input type="hidden" id="selected-district"
                                            value="<%=venueDTO != null ? venueDTO.getVenue().getDistrict() : ""%>">
                                        <select required class="border form-control select-input" size="1"
                                            name="district" id="select-district" aria-label="select">
                                            <optgroup label="Hong Kong">
                                                <option value="Central and West District">Central and West District
                                                </option>
                                                <option value="Eastern District">Eastern District</option>
                                                <option value="Southern District">Southern District</option>
                                                <option value="Wan Chai District">Wan Chai District</option>
                                            </optgroup>
                                            <optgroup label="Kowloon">
                                                <option value="Kowloon City District">Kowloon City District</option>
                                                <option value="Kwun Tong District">Kwun Tong District</option>
                                                <option value="Sham Shui Po District">Sham Shui Po District</option>
                                                <option value="Wong Tai Sin District">Wong Tai Sin District</option>
                                                <option value="Yau Tsim Mong District">Yau Tsim Mong District</option>
                                            </optgroup>
                                            <optgroup label="New Territories">
                                                <option value="Island District">Island District</option>
                                                <option value="Kwai Tsing District">Kwai Tsing District</option>
                                                <option value="North District">North District</option>
                                                <option value="Sai Kung District">Sai Kung District</option>
                                                <option value="Sha Tin District">Sha Tin District</option>
                                                <option value="Tai Po District">Tai Po District</option>
                                                <option value="Tsuen Wan District">Tsuen Wan District</option>
                                                <option value="Tuen Mun District">Tuen Mun District</option>
                                                <option value="Yuen Long District">Yuen Long District</option>
                                            </optgroup>
                                        </select>
                                        <label class="form-label" for="select-staff">District</label>
                                        <span class="select-arrow position-absolute"
                                            style="top:25%; right: 1rem;"></span>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <textarea required class="form-control border" name="address" id="address"
                                            rows="2"><%=venueDTO != null ? venueDTO.getVenue().getAddress() : ""%></textarea>
                                        <label class="form-label" for="address">Address</label>
                                    </div>
                                    <div class="form-outline">
                                        <textarea class="form-control border" name="description" id="decription"
                                            rows="4"><%=venueDTO != null ? venueDTO.getVenue().getDescription() : ""%></textarea>
                                        <label class="form-label" for="decription">Description</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit"
                            class="btn btn-primary"><%=venueDTO != null ? "Save changes" : "Add Venue"%></button>
                    </div>
                </div>
            </form>
        </div>
        <!-- Modal -->
        <ict:result />
        <div class="card bg-white p-3">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="m-2">Venues</h5>
                <div class="input-group" style="width: 35%;">
                    <div class="form-outline">
                        <input id="search-input" type="search" class="form-control border rounded-start" />
                        <label class="form-label" for="search-input">Search by name, district, address or person in
                            charge</label>
                    </div>
                    <button id="search-button" type="button" class="btn btn-primary">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>

            <div class="table-responsive text-nowrap mt-3 fs-6">
                <ict:venueTable venues="<%=venueDTOs %>" />
            </div>

        </div>
    </section>
</body>

</html>