
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

    login: function (pin, pinShaCode) {

        if (localStorage.waitForLogin == null || localStorage.waitForLogin == undefined) {
            localStorage.waitForLogin = "";
        }

        var loginCallback = function (lieferant) {
            serverController.loadConfig();
            //Login hat funktioniert
            if (lieferant) {
                if (lieferant.id) {
                    localStorage.lieferantID = lieferant.id;
                    var resumeLogin = false;

                    if (localStorage.waitForLogin.search(lieferant.id + ",") != -1) {
                        resumeLogin = true;
                    }
                    console.log("waitForLogin login: " + localStorage.waitForLogin);
                    loginController.lieferant = lieferant;
                    if (resumeLogin == false) {
                        $('#contact_daten_menu').show();
                        $('#startScreen').hide();
                        $('#lieferantenLogin').hide();

                    }
                    else {

                        serverController.job.getTemplates(lieferant.id, templateController.set);
                        $("#jobSelector").show();
                        $('#startScreen').hide();
                        $('#lieferantenLogin').hide();

                        localStorage.loggedIn = "true";
                    }
                    serverController.nachricht.get(lieferant.id, function (nachrichten) {
                        //Nachrichten
                        console.dir(nachrichten);
                        notifications.showMessages(nachrichten);

                    })
                    clientView.lieferant = lieferant;
                    console.dir(lieferant)
                    contactController.set(lieferant.id, lieferant);

                    $(".greetingLieferant").html(clientView.getLieferantFullName());
                    localStorage.loggedIn = "true";
                    $('#callButton').show();

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
            pin = pad.substring(0, pad.length - pin.length) + pin;

            var pinSha = pinShaCode;
            localStorage.pinSha = pinSha;
            serverController.lieferant.login(pinSha, loginCallback);
        }
        else {
            var pad = "0000";
            pin = pad.substring(0, pad.length - pin.length) + pin;

            var pinSha = "" + CryptoJS.SHA3("dfjo58443pggd9gudf9" + pin, {outputLength: 512});
            localStorage.pinSha = pinSha;
            serverController.lieferant.login(pinSha, loginCallback);
        }
    },

    waitForLogin: function () {
        if (configData.markt && loginController.lieferant) {
            phoneController.informAboutLogin(configData.markt, loginController.lieferant)
        }
        if (localStorage.waitForLogin == null || localStorage.waitForLogin == undefined) {
            localStorage.waitForLogin = "";
        }
        localStorage.waitForLogin = localStorage.waitForLogin.replace(localStorage.lieferantID + ",", "");
        localStorage.waitForLogin = localStorage.waitForLogin + localStorage.lieferantID + ",";
        console.log("waitForLogin wait: " + localStorage.waitForLogin);
        $('#callButton').hide();
        notifications.hideAll();
        clientView.lieferant = null;
        clientView.clearJob();
        contactController.set(null, null);
        localStorage.loggedIn = "false";
    },
    clear: function () {
        $('#callButton').hide();
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
