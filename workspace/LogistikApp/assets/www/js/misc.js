/**
 * misc.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 20.11.14 - 15:29
 * @copyright munichDev UG
 */



misc = {
    getUniqueID:function(){
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    },


    isMobileApp: function() {
        if(window.location.hash != "#android"){
            return false;
        }
        else
        {
            return true;
        }
    }
}