/**
 * misc.
 *
 *
 *
 *
 * @date 20.11.14 - 15:29
 *
 */



misc = {
    titleUnbenannt:"Unbenannt",
    getUniqueID:function(){
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    },
    formatDate:function(dateIn){

            if(typeof dateIn ==  Number)
                dateIn = new Date(dateIn)

            var yy = (dateIn.getFullYear()+"").slice(2,4);
            var mm = dateIn.getMonth()+1; // getMonth() is zero-based
            var dd  = dateIn.getDate();
            var hours  = dateIn.getHours();
            var mins = dateIn.getMinutes();

            return String(dd+"."+mm+"."+yy+"  "+hours+":"+mins); // Leading zeros for mm and dd

    }
}