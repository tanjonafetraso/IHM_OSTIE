<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <!--Made with love by Mutiullah Samim--> 

        <!--Bootsrap 4 CDN-->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

        <!--Fontawesome CDN-->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <!--Custom styles-->

    </head>
    <body ng-app="Authentification">
        <nav class="navbar navbar-fixed-top bg-primary" >
            <image src="ressoureces/assests/image/cropped-logo-eqima.png" style="margin-top: 10px;margin-left: 9px;margin-bottom: 9px;" />
        </nav>
        <div class="container" ng-controller="CtrlAuthentification">

            <div class="d-flex justify-content-center h-100">
                <div class="card">
                    <div class="card-header">
                        <h2 class="text-capitalize text-center "><i>AUTHENTIFICATION</i></h2>
                        <h2 class="text-capitalize text-center text-primary ">OSTIE</h2>

                    </div>
                    <div class="card-body">
                        <form>
                            <div class="input-group form-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <input type="text" class="form-control" placeholder="username" ng-model="username">

                            </div>
                            <div class="input-group form-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <input type="password" class="form-control" placeholder="password" ng-model="password">
                            </div>


                        </form>
                    </div>
                    <div class="card-footer">
                        <div class="form-group">
                            <a  ng-click="Authentification()" href=""> <button class="btn float-right btn-primary" >Connexion</button></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <link rel="stylesheet" href="<c:url value="/ressoureces/assests/css/sweet-alert.css"/>"/>
        <link rel="stylesheet" href="<c:url value="/ressoureces/assests/css/animate.min.css"/>"/>
        <link rel="stylesheet" href="<c:url value="/ressoureces/assests/css/CssAuthentification.css"/>"/>
        <script src="<c:url value="/ressoureces/assests/js/Framework/angular.js"/>"></script>
        <script src="<c:url value="/ressoureces/assests/js/Framework/SweetAlert.min.js"/>"></script>
        <script src="<c:url value="/ressoureces/assests/js/Framework/sweet-alert.min.js"/>"></script>
        <script src="<c:url value="/ressoureces/assests/js/Framework/angular-animate.js"/>"></script>
        <script src="<c:url value="/ressoureces/assests/js/Framework/angular-ui-router.js"/>"></script>
        <script src="<c:url value="/ressoureces/assests/js/AngulerController/AuthentificationCtrl.js"/>"></script>

    </body>
</html>