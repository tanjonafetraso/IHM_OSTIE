var App = angular.module("Inscription", ["ui.router", "oitozero.ngSweetAlert", "ngAnimate"]);
App.controller("InscriptionCtrl", function ($http, $scope) {
    //variable globale
    $scope.inscrirDisable = true;
    $scope.doigtsLibre = [];
    $scope.actionListe = ["Inscription", "Reinscription", "Identification"];
    resetData();
    getSize();
    $scope.TerminerBoutton = true;
    $scope.TerminerHide = true;
    var empreinte;

    var progressBar = $('.progress-bar');
    var isAction = false;
    var cpt = 0;
    var timerEntrer;
    var timerSeconde;
    var btnres = document.getElementById("btnRes");
    var btnAnnuler = document.getElementById("annulerBoutton");
    btnAnnuler.click();

    var isCapture = false;
    var isnext = false;
    var seconde = 0;
    $scope.changerTypeAction = function () {
        //    console.log("index====" + document.getElementById("peselectAction").selectedIndex);
        if ($scope.data.singleSelectAction === "Inscription" || $scope.data.singleSelectAction === "Reinscription") {
            //  alert();
            $scope.hideIdentifier = true;
            $scope.hideInscrir = false;
            $scope.hideEnvoyer = false;
            $scope.hideChoixDoigt = false;
            $scope.hideImage = false;
            btnAnnuler.click();

        } else {
            $scope.hideIdentifier = false;
            $scope.hideInscrir = true;
            $scope.hideEnvoyer = true;
            $scope.hideChoixDoigt = true;
            $scope.hideImage = true;
            btnAnnuler.click();
        }
    };
    function getSize() {
        $http({
            method: "GET",
            url: "getsizeAction",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {

            // alert(resultat.data.sizeUtilisateur);
            var xValues = ["Utilisateurs:" + resultat.data.sizeUtilisateur, "Empreintes:" + resultat.data.sizeEmpreintes];
            var yValues = [resultat.data.sizeUtilisateur, resultat.data.sizeEmpreintes];
            var barColors = [
                "#1fa055",
                "#357ab7"

            ];

            new Chart("myChart", {
                type: "pie",
                data: {
                    labels: xValues,
                    datasets: [{
                            backgroundColor: barColors,
                            data: yValues
                        }]
                },
                options: {
                    title: {
                        display: true,
                        text: "Nombre total d'utilisateur et d'empreinte dans la base de données"
                    }
                }
            });
        });
    }

    //loading page
    $('#myModal').modal({backdrop: 'static', keyboard: false});
    //  $('#myModal1').modal({backdrop: 'static', keyboard: false});
    timerEntrer = setInterval(function () {
        $('#myModal').modal('show');
        cpt++;
        if (cpt > 1) {
            $('#myModal').modal('hide');
            clearInterval(timerEntrer);
//            document.getElementById("peselectAction").selectedIndex = "1";
//            console.log("index====" + document.getElementById("peselectAction").selectedIndex);
            $scope.hideIdentifier = true;
            $scope.hideInscrir = false;
            $scope.hideEnvoyer = false;
            $scope.hideChoixDoigt = false;
            $scope.hideImage = false;
        }
    }, 1000);
    function AnnulerDatat() {
        $http({
            method: "POST",
            url: "resetDataAction1",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {

            $scope.inscrirDisable = true;

        });
    }
    $scope.Annuler = function () {
        //// alert()
        AnnulerDatat();

    };
    //lancerSecondeIdentification 
    function lancerSecondeIdentification() {
        timerSeconde = setInterval(function () {
            $scope.secondIndentification = seconde;
            seconde++;
        }, 1000);
    }

    //stoperSecondeIdentification
    function stoperSecondeIdentification() {
        clearInterval(timerSeconde);
    }


    //progress Bar
//    function showProgressBar(score) {
//        console.log("ici score======" + score);
//
//        var scoreMax = score;
//        var progress = -1;
//        timeProgressBar = setInterval(function () {
//            $scope.score = progress;
//            progress++;
//            console.log("ici data======" + progress);
//            progressBar.css('width', progress + '%');
//            progressBar.attr('aria-valuenow', progress + '%');
//            if (progress === score) {
//                clearInterval(timeProgressBar);
//                $scope.TerminerBoutton = false;
//
//            }
//        }, 20);
//    }

    //select BDD
    $scope.connexionBaseDBB = function () {
        $('#myModal').modal('show');
        $http({
            method: "GET",
            url: "ConnexionDataBASE",
            params: {NameDBB: $scope.data.singleSelect},
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {
            $('#myModal').modal('hide');
            if (resultat.data === 1) {
                swal("Connexion", "Connexion établie", "success");
                getSize();
            } else {
                swal("Erreur", "Echec de connexion ", "error");
            }

        });
    };

    //identification terminer
    $scope.resetIdentification = function () {
        $scope.identifierDom = true;
        $scope.TerminerBoutton = true;
        $scope.TerminerHide = true;
        $scope.resColor = "";
        $scope.resultColoration = "";
//        progressBar.css('width', 0 + '%');
//        progressBar.attr('aria-valuenow', 0 + '%');
        seconde = 0;
        $scope.secondIndentification = 0;
        resetData();
    };
    $scope.VerifierUSer = function () {
        if (isCapture === true) {
            $('#myModal').modal('show');
            $http({
                method: "GET",
                url: "verificationControllerAction",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {
                $('#myModal').modal('hide');
                if (resultat.data.couleur === "Empreinte inconnue" || resultat.data.couleur === null || resultat.data.couleur === undefined || resultat.data.couleur === '') {
                    swal(resultat.data.couleur, "empreinte digitale non identifier", "error");
                } else {
                    var ress = resultat.data.couleur;
                    var ress1 = ress.split("=");
                    swal(ress1[0], "empreinte digitale verifier", "success");
                }
            });
        } else {
            swal("ERREUR", "Pas d'empreinte digitale", "error");
        }
    };
    //Action d'identification
    $scope.Identifier = function () {

        var indexString = empreinte.indexOf('allAmpreinte');
        console.log("SRC========" + empreinte);
        console.log("index" + indexString);

        if ($scope.AFF_ID !== "") {
            if (isCapture === true || isnext === true) {
                if (indexString >= 0) {
                    $scope.identifierDom = true;
                    lancerSecondeIdentification();
                    $('#myModal').modal('show');
                    $http({
                        method: "GET",
                        url: "IdentifierMultipleControllerAction",
                        params: {Aff_Id: $scope.AFF_ID},
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then(function (resultat) {
                        $scope.TerminerBoutton = false;
                        $scope.TerminerHide = false;
                        $scope.resultColoration = "";
                        console.log("ici data======" + resultat.data.couleur);
                        stoperSecondeIdentification();
                        seconde = 0;

                        $scope.resColor = resultat.data.couleur;
                        $('#myModal').modal('hide');
                    });
                } else {
                    $scope.identifierDom = true;
                    lancerSecondeIdentification();
                    $('#myModal').modal('show');
                    $http({
                        method: "GET",
                        url: "IdentifierControllerAction",
                        params: {Aff_Id: $scope.AFF_ID},
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then(function (resultat) {
                        $('#myModal').modal('hide');
                        $scope.TerminerBoutton = false;
                        $scope.TerminerHide = false;
                        $scope.resColor = resultat.data.couleur;
                        stoperSecondeIdentification();
                        seconde = 0;
                        if (resultat.data.couleur === "Empreinte inconnue" || resultat.data.couleur === null || resultat.data.couleur === undefined || resultat.data.couleur === "Reidentifier") {
                            $scope.resultColoration = "red";
                        } else {

                            $scope.resultColoration = "Green";
                        }


                    });
                }

            } else {
                swal("ERREUR", "Pas d'empreinte digitale", "error");
            }
        } else {
            swal("ERREUR", "AFF_ID vide", "error");

        }
    };

    getEmpreinte();
//    getDecissionByAPI();


    //Action evoyer AFF_ID
    $scope.SendAffId = function () {
        var action = "";
        try {
            if ($scope.data.singleSelectAction === "Inscription") {
                action = "1";
            } else if ($scope.data.singleSelectAction === "Reinscription") {
                action = "2";

            } else if ($scope.data.singleSelectAction === "Identification") {
                action = "3";
            } else {
                swal("Erreur", "Il faut selectionner le type d'action ", "error");
            }

            if ($scope.AFF_ID !== "") {
                $('#myModal').modal('show');
                $http({
                    method: "GET",
                    url: "sendAffIDAction",
                    params: {Aff_Id: $scope.AFF_ID, Action: action},
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(function (resultat) {
                    $scope.doigtsLibre = [];
                    $('#myModal').modal('hide');
                    console.log(resultat.data);
                    if (resultat.data.action === null && resultat.data.doigtlibre !== null) {
                        swal("Information", "ce collaborateur peut s'inscrire", "info");
                    }
//                     else {
                    if (resultat.data.doigtlibre === null) {
                        swal("Erreur", "ce collaborateur déjà fait l'incription donc  il doit faire la reinscription ou l'identification", "error");
                        //  swal("Information", "ce collaborateur ne peut pas s'incrire ni reinscrire", "error");
                    } else {

                        var listeDoigt = resultat.data.doigtlibre.split(',');
                        if (resultat.data.doigtlibre === "") {
                            swal("Erreur", "ce collaborateur doit faire l'incription", "error");
                        } else if (listeDoigt.length <= 6 && action !== "2") {
                            swal("Erreur", "ce collaborateur déjà fait l'incription donc  il doit faire la reinscription ou l'identification", "error");
                        } else {
                            for (var i = 0, max = listeDoigt.length; i < max; i++) {
                                console.log(listeDoigt[i]);
                                if (listeDoigt[i] === "POUCE_G" || listeDoigt[i] === "POUCE_D" || listeDoigt[i] === "INDEX_G" || listeDoigt[i] === "INDEX_D") {
                                    $scope.doigtsLibre.push(listeDoigt[i]);
                                }

                                if (resultat.data.action === "INSCRIPTION") {
                                    swal("Information", "ce collaborateur peut s'inscrire", "info");

                                } else if (resultat.data.action === "REINSCRIPTION") {
                                    swal("Information", "ce collaborateur peut se reinscrire", "info");

                                } else if (resultat.data.action === "IDENTIFICATION") {
                                    swal("Information", "ce collaborateur peut s'identifier", "info");

                                }
                            }

                        }
                    }


//                $('#myModal').modal('show');

                    isAction = true;
                });
            } else {
                swal("ERREUR", "Veuillez remplir l'AFF_ID", "error");
            }
        } catch (error) {
            swal("Erreur", "Il faut selectionner le type d'action ", "error");
        }


    };
    $scope.Inscriptioncsvfile = function () {
        var dateString1 = "";
        var dateString2 = "";
        if (($scope.date1 !== undefined) && ($scope.date2 !== undefined)) {
            var datee1 = new Date($scope.date1);
            datee1.setDate(datee1.getDate() + 1);

            var datee2 = new Date($scope.date2);
            datee2.setDate(datee2.getDate() + 1);
            if (datee1 < datee2) {
                var a = datee1.toISOString().split('T');
                var b = datee2.toISOString().split('T');
                dateString1 = a[0];
                dateString2 = b[0];
            } else {
                swal("Erreur", "Date2 doit superieur à date1 ", "error");
            }
        } else if (($scope.date1 === undefined) && ($scope.date2 !== undefined)) {
            swal("Erreur", "il faut selectionner date1 ", "error");
        } else if (($scope.date1 !== undefined) && ($scope.date2 === undefined)) {
            var datee1 = new Date($scope.date1);
            datee1.setDate(datee1.getDate() + 1);
            var a = datee1.toISOString().split('T');
            dateString1 = a[0];
        } else {
            swal("Erreur", "il faut selectionner une date", "error");
        }

        if (dateString1 !== "") {
            $('#myModal').modal('show');
            var csvFileData = [];
          //  alert("tonga");
            $http({
                method: "POST",
                url: "getAllInscription",
                params: {date1: dateString1, date2: dateString2},
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resulat) {
                var datas = resulat.data;

                for (var i = 0; i < datas.length; i++) {
                    console.log(datas[i].date);

                    var rows = [];
                    rows.push(datas[i].aff_id);

                    rows.push(datas[i].doigt);
                    rows.push(datas[i].empreStringe);
                    rows.push(datas[i].qualite);
                    rows.push(datas[i].date);
                    rows.push(datas[i].code_agent);


                    csvFileData.push(rows);
                }
                var csv = 'Aff_Id;Doigt;Empreinte;Qualite;Date;Code_Agent\n';

                //merge the data with CSV  
                csvFileData.forEach(function (row) {
                    csv += row.join(';');
                    csv += "\n";
//                console.log(csvFileData);

                });



                //display the created CSV data on the web browser 
                // document.write(csv);


                var hiddenElement = document.createElement('a');
                hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(csv);
                hiddenElement.target = '_blank';

                //provide the name for the CSV file to be downloaded
                hiddenElement.download = 'InscriptionBDD.csv';
                hiddenElement.click();
                $('#myModal').modal('hide');


            });
        } else {
            swal("Erreur", "il faut selectionner une date", "error");
        }


    };
    $scope.isAuthentificationExtractionBDD = function () {
        if (($scope.adminName === "admin") && ($scope.adminPassword === "adminpassword")) {
            $('#myModalAuthentExtracteBDD').modal('hide');
            $('#myModalExtracteBDD').modal('show');
        }else{
            swal("Erreur", "erreur d'authentification ", "error");
        }
    };
    $scope.Identificationcsvfile = function () {

        var dateString1 = "";
        var dateString2 = "";
        if (($scope.date1 !== undefined) && ($scope.date2 !== undefined)) {
            var datee1 = new Date($scope.date1);
            datee1.setDate(datee1.getDate() + 1);

            var datee2 = new Date($scope.date2);
            datee2.setDate(datee2.getDate() + 1);
            if (datee1 < datee2) {
                var a = datee1.toISOString().split('T');
                var b = datee2.toISOString().split('T');
                dateString1 = a[0];
                dateString2 = b[0];
            } else {
                swal("Erreur", "Date2 doit superieur à date1 ", "error");
            }
        } else if (($scope.date1 === undefined) && ($scope.date2 !== undefined)) {
            swal("Erreur", "il faut selectionner date1 ", "error");
        } else if (($scope.date1 !== undefined) && ($scope.date2 === undefined)) {
            var datee1 = new Date($scope.date1);
            datee1.setDate(datee1.getDate() + 1);
            var a = datee1.toISOString().split('T');
            dateString1 = a[0];
        } else {
            swal("Erreur", "il faut selectionner une date", "error");
        }

        if (dateString1 !== "") {
            $('#myModal').modal('show');
            var csvFileData = [];
          //  alert("tonga");
            $http({
                method: "POST",
                url: "getAllIdentification",
                params: {date1: dateString1, date2: dateString2},
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resulat) {
                var datas = resulat.data;

                for (var i = 0; i < datas.length; i++) {
                    console.log(datas[i].date);

                    var rows = [];
                    rows.push(datas[i].aff_id);
                    rows.push(datas[i].date);
                    rows.push(datas[i].resultat);
                    rows.push(datas[i].code_agent);


                    csvFileData.push(rows);
                }
                var csv = 'Aff_Id;Date;Resultat;Code_Agent\n';

                //merge the data with CSV  
                csvFileData.forEach(function (row) {
                    csv += row.join(';');
                    csv += "\n";
//                console.log(csvFileData);

                });



                //display the created CSV data on the web browser 
                // document.write(csv);


                var hiddenElement = document.createElement('a');
                hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(csv);
                hiddenElement.target = '_blank';

                //provide the name for the CSV file to be downloaded
                hiddenElement.download = 'IdentificationBDD.csv';
                hiddenElement.click();

                $('#myModal').modal('hide');
            });
        } else {
            swal("Erreur", "il faut selectionner une date", "error");
        }
    };
    $scope.nextIdentitication = function () {
        if (isCapture) {
            $http({
                method: "POST",
                url: "nextIdentification",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function () {
                isnext = true;
                resetData1();
                $scope.identifierDom = false;

            });
        } else {
            swal("ERREUR", "Pas d'empreinte digitale", "error");
        }

    };
    //Action reset Data
    function resetDataMultiple() {
        isnext = false;
        $scope.inscrirDisable = true;
        $http({
            method: "POST",
            url: "resetMultipleDataEmpreinteAction",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function () {

        });
    }
    function resetData() {
        $scope.identifierDom = false;
        $scope.AFF_ID = "";
        AnnulerDatat();

        $http({
            method: "POST",
            url: "resetDataAction",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function () {

            resetDataMultiple();
        });
    }
    function resetData1() {
//        $scope.identifierDom = false;
//        $scope.AFF_ID = "";
//        document.getElementById("peselect").selectedIndex = -1;

        $http({
            method: "POST",
            url: "resetDataAction1",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function () {
            //  resetDataMultiple();
        });
    }

    //Action de l'inscription
    $scope.writeEmpreinteAff_Id = function () {
        //$scope.data.singleSelect="POUCE_D";
        // alert("etoooooo="+$scope.data.singleSelect);
        try {
            if (isCapture === true) {
                if ($scope.data.singleSelect === undefined) {
                    swal("ERREUR", "il faut choisir doigt", "error");
                } else {
                    if ($scope.data.singleSelectAction === "Reinscription") {
                        $('#myModal1').modal('show');
                        $http({
                            method: "GET",
                            url: "updateEmpreinte",
                            params: {Doigt: $scope.data.singleSelect},
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        }).then(function (resultat) {
                            btnAnnuler.click();
                            $('#myModal1').modal('hide');
                            if (resultat.data === 1) {

                                swal("REINSCRIPTION", "Reinscription bien réussie", "success");
                                getSize();
                                isAction = false;
                                $scope.doigtsLibre = [];
                                resetData();
                            } else if (resultat.data === 2) {

                                swal("ERREUR", "Il faut envoyer l'AFF_ID avant le reinscription", "error");
                                isAction = false;
                                $scope.doigtsLibre = [];
                                resetData();
                            } else {
                                swal("ERREUR", "Opération non effectuée", "error");
                            }
                        });
                    } else {
                        $('#myModal1').modal('show');
                        $http({
                            method: "GET",
                            url: "writeEmpreinteAff_IdAction",
                            params: {Doigt: $scope.data.singleSelect},
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        }).then(function (resultat) {
                            btnAnnuler.click();
                            $('#myModal1').modal('hide');
                            if (resultat.data === 1) {

                                swal("INSCRIPTION", "Inscription bien réussie", "success");
                                getSize();
                                isAction = false;
                                $scope.doigtsLibre = [];
                                resetData();
                            } else if (resultat.data === 2) {

                                swal("ERREUR", "Il faut envoyer l'AFF_ID avant l'inscription", "error");
                                isAction = false;
                                $scope.doigtsLibre = [];
                                resetData();
                            } else {
                                swal("ERREUR", "Opération non effectuée", "error");
                            }
                        });
                    }
                }
            } else {
                swal("ERREUR", "Pas d'empreinte digitale", "error");
            }
        } catch (e) {
            swal("ERREUR", "connexion perdu", "warning");
        }

    };

    //recuper et afficher l'mpreinte 
    function getEmpreinte() {
        setInterval(function () {
            $http({
                method: "GET",
                url: "getEmpreinte/API",
                headers: {
                    'Content-type': 'application/json'
                }
            }).then(function (resultat) {
                var image = document.getElementById("idImage");
                console.log(resultat.data.dataEmpreinte);
                empreinte = resultat.data.dataEmpreinte;
                if (resultat.data.dataEmpreinte !== undefined && resultat.data.dataEmpreinte !== "" && resultat.data.dataEmpreinte !== null) {
                    $scope.inscrirDisable = false;

//                    image.setAttribute("src", "data:image/bmp;base64," + resultat.data.dataEmpreinte);
                    isCapture = true;
                } else {
                    image.setAttribute("src", "");
                    isCapture = false;
                }
            });
        }, 1000);
    }

    //recuper la decisione de l'API soit inscription soit identification
    function getDecissionByAPI() {
        setInterval(function () {
            $http({
                method: "GET",
                url: "getDecissionByAPIAction",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {
                console.log(resultat.data.deccisionAction)
                if ((resultat.data.deccisionAction !== undefined && resultat.data.deccisionAction !== '' && resultat.data.deccisionAction !== null) && (isAction === false)) {
                    $('#myModal').modal('hide');
                    swal("Ce collaborateur doit s'inscrire", " ", "info");
                    isAction = true;
                    if (resultat.data.doigtLibre.length > 0) {
                        console.log(resultat.data.doigtLibre);

                        var doigts = resultat.dresultat.ata.doigtLibre.split(',');
                        console.log(doigts);
                        var listeDoigtLibre = [];

                        for (var i = 0, max = doigts.length; i < max - 1; i++) {
                            listeDoigtLibre.push(doigts[i]);
                        }
                        //  $scope.doigtsLibre = listeDoigtLibre;
                    }
                }
            });

        }, 3000);
    }
    ;
});