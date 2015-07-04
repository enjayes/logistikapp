
//ist für doie Logik hinter dem Anmeldevorgang zuständig
loginController = {

    lieferant: null,
    loginError: function () {
        notifications.showError("Die Anmeldung war leider nicht erfolgreich!")
    },

    loginQR: function (qrcode) {
        //todo
        loginController.login(qrcode);

    },
    loginNFC: function (nfccode) {
        console.log("NFC: " + nfccode);
        console.log("localStorage.loggedIn: " + localStorage.loggedIn);
        if (localStorage.loggedIn != "true") {
            //todo
            console.log("-> loginController.login");
            loginController.login("", nfccode);

        }

    },
    writeNFClogin: function(pin){
        var loginCallback = function (lieferant) {
            serverController.loadConfig();
            if (lieferant) {
                if (lieferant.id) {
                    localStorage.lieferantID = lieferant.id;
                    switchView("konfi_menue");
                    setTimeout(nfcController.writeNFCTag, 200);
                }
                else {
                    loginController.loginError();
                }
            }
            else {
                loginController.loginError();
            }
        };
        //Create Pin 4 digits
        var pad = "0000";
        pin = pad.substring(0, pad.length - pin.length) + pin.toString();

        var pinSha = "" + CryptoJS.SHA3("dfjo58443pggd9gudf9" + pin, {outputLength: 512});
        localStorage.pinSha = pinSha;
        serverController.lieferant.login(pinSha, loginCallback);
    }
    ,
    login: function (pin, pinShaCode) {

        if (localStorage.waitForLogin == null || localStorage.waitForLogin == undefined) {
            localStorage.waitForLogin = "";
        }

        var loginCallback = function (lieferant) {
            console.log("loginCallback ####################################### LIEFERANT");
            console.log(lieferant);

            serverController.loadConfig();
            //Login hat funktioniert
            if (lieferant) {
                console.log("GET ####################################### LIEFERANT");
                console.log(lieferant);


                if (lieferant.id) {
                    localStorage.lieferantID = lieferant.id;
                    var resumeLogin = false;

                    if (localStorage.waitForLogin.search(lieferant.id + ",") != -1) {
                        resumeLogin = true;
                        var timenow = new Date();
                        var lastlogin = terminController.getTerminStart(lieferant.id);
                        if (lastlogin && lastlogin != undefined && lastlogin != 0) {
                            var time = timenow.getTime() - lastlogin;
                            if (time > 36000000) {//) {
                                resumeLogin = false;
                            }
                        }
                        else {
                            resumeLogin = false;
                        }
                        if (resumeLogin == false) {
                            terminController.deleteTermin(lieferant.id);
                            localStorage.waitForLogin = localStorage.waitForLogin.replace(lieferant.id + ",", "");
                            notifications.showError("Die letzte Sitzung ist abgelaufen!");
                        }
                    }
                    console.log("waitForLogin login: " + localStorage.waitForLogin);
                    loginController.lieferant = lieferant;
                    clientView.lieferant = lieferant;
                    contactController.lieferant = lieferant;
                    if (resumeLogin == false) {
                        terminController.startTermin(lieferant.id);
                        switchView("contact_daten_menu");

                    }
                    else {

                        serverController.job.getTemplates(lieferant.id, templateController.set);
                        localStorage.loggedIn = "true";
                        switchView("job_selector");
                    }
                    serverController.nachricht.get(lieferant.id, function (nachrichten) {
                        //Nachrichten
                        console.dir(nachrichten);
                        notifications.showMessages(nachrichten);

                    })

                    console.dir(lieferant)


                    localStorage.loggedIn = "true";


                }
                else {
                    loginController.loginError();
                }

            }
            else {
                loginController.loginError();
            }
        };

        //Create Pin 4 digits
        if (pinShaCode) {
            var pad = "0000";
            pin = pad.substring(0, pad.length - pin.length) + pin.toString();

            var pinSha = pinShaCode;
            localStorage.pinSha = pinSha;
            serverController.lieferant.login(pinSha, loginCallback);
        }
        else {
            console.log("pin: "+pin);
            var pad = "0000";
            pin = pin+'';
            pin = pad.substring(0, pad.length - pin.length) + pin.toString();
            console.log("padpart: "+pad.substring(0, pad.length - pin.length+1));

          //  alert(pin);
            var pinSha = "" + CryptoJS.SHA3("dfjo58443pggd9gudf9" + pin, {outputLength: 512});
            localStorage.pinSha = pinSha;
            serverController.lieferant.login(pinSha, loginCallback);
        }
    },
    getPinSha: function(pin,pinShaCode){
        if(pin) {
            if (pinShaCode) {
                var pad = "0000";
                pin = pad.substring(0, pad.length - pin.length) + pin.toString();

                return pinShaCode;
            }
            else {
                var pad = "0000";
                pin = pad.substring(0, pad.length - pin.length) + pin.toString();

                var pinSha = "" + CryptoJS.SHA3("dfjo58443pggd9gudf9" + pin, {outputLength: 512});
                return pinSha;
            }
        }
    }
    ,waitForLogin: function () {
        if (configData.markt && loginController.lieferant) {
            phoneController.informAboutLogin(configData.markt, loginController.lieferant)
        }
        if (localStorage.waitForLogin == null || localStorage.waitForLogin == undefined) {
            localStorage.waitForLogin = "";
        }
        localStorage.waitForLogin = localStorage.waitForLogin.replace(localStorage.lieferantID + ",", "");
        localStorage.waitForLogin = localStorage.waitForLogin + localStorage.lieferantID + ",";
        console.log("waitForLogin wait: " + localStorage.waitForLogin);
        //$('#callButton').hide();
        notifications.hideAll();
        clientView.lieferant = null;
        clientView.clearJob();
        contactController.set(null, null);
        localStorage.loggedIn = "false";
    },
    clear: function () {
        // $('#callButton').hide();
        localStorage.loggedIn = "false";
        notifications.hideAll();
        clientView.lieferant = null;
        clientView.clearJob();
        contactController.set(null, null);
        console.log("LOGOUT!")
    },
    logout: function () {
        if (localStorage.waitForLogin == null || localStorage.waitForLogin == undefined) {
            localStorage.waitForLogin = "";
        }
        localStorage.waitForLogin = localStorage.waitForLogin.replace(localStorage.lieferantID + ",", "");
        loginController.clear();

    }
}