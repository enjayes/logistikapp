/** * util.js.
 *
 * >>Description<<
 *
 * @author Norbert
 * @date 06.12.14 - 21:15
 * @copyright  */


var util = {


    isMobileApp: function() {
        if(window.location.hash != "#android"){
          return true;
        }
        else
        {
          return false;
        }
    }
}