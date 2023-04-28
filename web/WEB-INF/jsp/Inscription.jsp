<%-- 
    Document   : Inscription
    Created on : Mar 8, 2022, 4:46:21 PM
    Author     : eqima
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include  file="PluginImporter.jsp"%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Inscription1 | OSTIE</title>
    </head>
    <body ng-app="Inscription">
        <div class="container" ng-controller="InscriptionCtrl" >
            <nav class="navbar navbar-fixed-top bg-primary" >

                <div class="col-xs-2">
                    <image src="ressoureces/assests/image/cropped-logo-eqima.png" style="margin-top: 10px;margin-left: 9px;margin-bottom: 9px;" />
                </div>
                <div class="col-xs-1" style="font-size: 50px; top: 10px;color: #006098">{{secondIndentification}}</div>
                <div class="col-xs-1"></div>
                <div class="col-xs-2">
                    <button data-toggle="modal" data-target="#myModalAuthentExtracteBDD" style="margin-top: 25px" class="btn btn-success"> Exporter en CSV  </button> 
                </div>
                <div class="col-xs-2">

                   
                </div>
                <div class="col-xs-1" style="left: 60px;margin-top: 30px;">
                    <!--<button    style="margin-top: 30px" ng-click="connexionBaseDBB()" class="btn btn-success btn-xs">Connexion</button>-->
                    <a    style="margin-top: 30px;color: white" href="http://pp.eqima.org/ihmostie/ressoureces/assests/API/ideqima.exe" >Driver</a>
                </div>
                <div class="col-xs-2" style="margin-top: 30px">
                    <a href="http://pp.eqima.org/ihmostie/ressoureces/assests/API/WebStartScanner.jar" style="margin-top: 10px;margin-left: 70px;margin-bottom: 9px;color: white">
                        <i class="fas fa-save"></i> Download JAR </a>
                </div>
                <div class="col-xs-1" style="margin-top: 30px">
                    <a href="Deconnexion" style="margin-top: 10px;margin-left: 9px;margin-bottom: 9px;color: white">
                        <i class="glyphicon glyphicon-log-out fa-1x" ></i></a>
                </div>

            </nav>

            <div style="padding-top:80px"></div>
            <center><div id="myDIV">
                    <div id="blueDIV">
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="row" >
                                    <div class="col-xs-3" >
                                        <label style="float: left"> Type d'action :</label>
                                    </div>
                                    <div class="col-xs-3">
                                        <select name="action"id="peselectAction" name="singleSelectAction" ng-model="data.singleSelectAction"  ng-change="changerTypeAction()">
                                            <option ng-repeat="action in actionListe" value="{{action}}" >{{action}}</option>
                                            <!--<option ng-repeat="action in actionListe" value="Inscription" >{{action}}</option>-->
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-3"><label style="float: left">AFF_ID:</label></div>
                                    <div class="col-xs-5"><input type="text" class="form form-control " ng-model="AFF_ID" style="width: 250px" /></div>
                                    <div class="col-xs-1" ><button class="btn btn-primary" ng-click="SendAffId()" ng-hide="hideEnvoyer" >Envoyer</button></div>
                                    <div class="col-xs-2">

                                        <%--<c:if test="${!empty sessionScope.CodeAgent}">--%>
                                        <%--<c:out value="${sessionScope.CodeAgent}"/>--%>
                                        <%--</c:if>--%>
                                    </div>
                                </div>


                                <div class="row" >

                                    <div class="col-xs-8">

                                        <div class="row" style="margin-top: 10px" ng-hide="hideChoixDoigt">
                                            <div class="col-xs-4">   <label for="pet-select" style="float: left">Choix doigt :</label></div>
                                            <div class="col-xs-6" style="margin-left: 12px">
                                                <select name="doigt"id="peselect" name="singleSelect" ng-model="data.singleSelect" required style="width: 160px">
                                                    <option ng-repeat="doigte in doigtsLibre" value="{{doigte}}">{{doigte}}</option>
                                                    <!--<option  value="Pouce_G">Pouce_G</option>-->
                                                </select>
                                            </div>
                                        </div>

                                        <div style="margin-left: 90px">
                                            <img  id="idImage"class="img-responsive" src=""height:="270px" width="240px" style="margin-left: 40px;border:2px solid #0056b3;"/>
                                        </div>
                                        <div style="margin-left: 150px">
                                            <b>{{resColor}}</b>
                                            <button id="btnRes" class="btn btn-ms" style="border-radius: 100%;background-color: {{resultColoration}}"></button>
                                        </div>
                                    </div>

                                    <!--                                                <div class="progress" style="width: 250px;margin-bottom: 0px;">
                                                                                                                  <div class="progress-bar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" id="progressbarr" width="270">{{score}}%</div>
                                                                                                              </div>-->




                                </div>
                                <div class="row" style="margin-bottom: 11px;">
                                    <center><button class="btn btn-success" ng-hide="TerminerBoutton" ng-click="resetIdentification()" style="width: 250px">Identification terminée !</button></center>
                                </div>
                                <!--                                <div class="row" style="margin-left: 17px;margin-bottom: 11px;">
                                
                                
                                                                    <center style="margin-left: 0px;">
                                                                        <input type="checkbox" id="vehicle1" name="vehicle1" value="Bike" ng-model="checkAlue">
                                                                        <label for="vehicle1"> Identification groupées</label>
                                                                        <button class="btn btn-success btn-xs"  ng-click="nextIdentitication()" style="width: 85px">Next</button></center>
                                                                </div>-->

                                <div>

                                    <button id="inscrir" class="btn btn-primary btn-ms" ng-click="writeEmpreinteAff_Id()" ng-disabled="inscrirDisable" style="width: 125px" ng-hide="hideInscrir"><i class="fas fa-save"></i> S'inscrire </button>
                                    <button id="idIdentifier" class="btn btn-primary btn-ms" ng-click="Identifier()" ng-disabled="identifierDom"style="width: 125px" ng-hide="hideIdentifier"><i class="fas fa-fingerprint"></i> S'identifier </button>
                                    <button id="annulerBoutton"class="btn btn-primary"ng-click="Annuler()">Annuler</button>


                                </div>
                                <div>
                                    <center style="margin-top: 13px;margin-left: 32px;">
                                        <!--<button class="btn btn-primary btn-ms" ng-click="VerifierUSer()"  style="width: 250px"><i class="fas fa-save"></i> Se vérifier </button>-->
                                        <!--<button class="btn btn-primary btn-ms" ng-click="Identifier()" ng-disabled="identifierDom"style="width: 125px"><i class="fas fa-fingerprint"></i> S'identifier </button>-->
                                    </center>
                                </div>




                                <!-- Modal -->
                                <div class="modal fade" id="myModal" style="justify-content: center !important;opacity: 5">
                                    <div class="modal-dialog " role="document">
                                        <div class="modal-content">
                                            <div class="modal-body">
                                                <button class="btn btn-primary"style="width: 570px"> <i class="fa fa-spinner fa-spin fa-5x fa-fw"></i>Chargement en cours | <b>OSTIE</b></button>


                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="myModal1" style="justify-content: center !important;">
                                    <div class="modal-dialog " role="document">
                                        <div class="modal-content">
                                            <div class="modal-body">
                                                <button class="btn btn-primary"style="width: 570px"> <i class="fa fa-cog fa-spin fa-5x fa-fw"></i>Traitement en cours | <b>OSTIE</b></button>


                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6" style="">
                                <div class="row " >
                                    <div class="col-xs-1"></div>
                                    <div class="col-xs-10 bg-primary" style="margin-top: 20px;height: 30px"> <center>VISUALISATION DANS LA BASE DE DONNEES</center></div>
                                    <div class="col-xs-1"></div>
                                </div>
                                <canvas id="myChart" style="width:100%;max-width:600px; margin-top: 70px;"></canvas>
                                <div class="row " >
                                    <div class="col-xs-1"></div>
                                    <div class="col-xs-10 bg-primary" style="margin-top: 65px;"> <center><a href="https://eqima.org"style="color: #F7FCF6">eqima.org </a><i class="fas fa-copyright" style="padding-top: 13px"> </i>2022</center></div>
                                    <div class="col-xs-1"></div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>



                <div class="modal fade" id="myModalAuthentExtracteBDD">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title">Admin</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-xs-1"></div>
                                    <div class="col-xs-3"><label>Nom d'utilisateur</label></div>
                                    <div class="col-xs-7"><input type="text" class="form form-control" ng-model="adminName"/></div>
                                    <div class="col-xs-1"></div>
                                </div>
                                <div class="row" style="margin-top: 20px">
                                    <div class="col-xs-1"></div>
                                    <div class="col-xs-3"><label>Mot de passe</label></div>
                                    <div class="col-xs-7"><input type="password" class="form form-control" ng-model="adminPassword"/></div>
                                    <div class="col-xs-1"></div>
                                </div>
                                <div class="row" style="margin-top: 20px">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-7"><button class="btn btn-primary" ng-click="isAuthentificationExtractionBDD()">S'authentifier</button></div>
                                    <div class="col-xs-1"></div>
                                </div>
                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                            </div>

                        </div>
                    </div>
                </div>
                                    
                                    
                <div class="modal fade" id="myModalExtracteBDD">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title">Admin</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-xs-1"></div>
                                    <div class="col-xs-3"><label>Date</label></div>
                                    <div class="col-xs-7"><input type="date" class="form form-control" ng-model="date1"/></div>
                                    <div class="col-xs-1"></div>
                                </div>
                                <div class="row"style="margin-top: 20px">
                                    <div class="col-xs-1"></div>
                                    <div class="col-xs-3"><label>Date</label></div>
                                    <div class="col-xs-7"><input type="date" class="form form-control" ng-model="date2"/></div>
                                    <div class="col-xs-1"></div>
                                </div>
                               
                                <div class="row" style="margin-top: 20px">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-5">  <button ng-click="Inscriptioncsvfile()" style="margin-top: 25px" class="btn btn-success"> Exporter inscription en CSV  </button> </div>
                                    <div class="col-xs-5">   <button ng-click="Identificationcsvfile()" style="margin-top: 25px" class="btn btn-success"> Exporter identification en CSV  </button> </div>
                                    
                                </div>
                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                            </div>

                        </div>
                    </div>
                </div>
        </div>





        <!--            <nav class="navbar navbar-fixed-bottom bg-primary ">
                        <center><a href="https://eqima.org"style="color: #F7FCF6">eqima.org </a><i class="fas fa-copyright" style="padding-top: 13px"> </i>2022</center>
                    </nav>-->

        <script src="<c:url value="/ressoureces/assests/js/AngulerController/InscriptionCtrl.js"/>"></script>
    </body>
</html>