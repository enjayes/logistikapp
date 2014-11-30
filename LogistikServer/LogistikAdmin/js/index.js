/**
 * index.js.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 17:10
 * @copyright munichDev UG
 */

//Init Controllers
tabsController.init();

var initAfterServerConnection = function(){
    nachrichtenController.init();
    lieferantenController.init();
    termineController.init();

    $(document).ready(function () {
        //Enable Routing
        setTimeout(function () {
        uiController.ready();
            Router.init();

        },0)
    });
}
serverController.init(initAfterServerConnection);










