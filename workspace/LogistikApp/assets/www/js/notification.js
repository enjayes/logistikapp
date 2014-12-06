/** * notification.js.
 *
 * >>Description<<
 *
 * @author Norbert
 * @date 06.12.14 - 20:28
 * @copyright  */






var notifications = {


    show: function(title,message, callback) {

        $.toast({
            heading: title,
            text : message,
            stack: 50,
            hideAfter: 6000000

        })

    }



}