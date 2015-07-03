lieferantenController = {
    lieferanten : [],
    ready: false,
    loadCallback: function(l){
        lieferantenController.lieferanten = l;
        var l = lieferantenController.lieferanten
        if(l){
            for (var i = 0; i < l.length; i++) {
                l[i] =  serverController.lieferant.parseDTO(l[i]);
            }
        }
        console.log("lieferantenController.loadCallback");
        console.dir(l);
        lieferantenController.ready = true;
        lieferantenController.saveLieferanten();
    }
    ,
    load: function(){
        lieferantenController.loadLieferanten();
        console.log("lieferantenController.load");
        var serverMessage = new ServerMessage({
            t: serverController.lieferant.messageType.getAll,
            mid: localStorage.markt_id,
            callback: serverController.callbackHandler.register(lieferantenController.loadCallback)
        })
        console.log("load serverMessage.t: "+serverMessage.data.t);
        console.log("load serverMessage.mid: "+serverMessage.data.mid);
        dataController.emit('message',serverMessage );
    },
    login: function(pinSha) {
        var l = lieferantenController.lieferanten
        if(l){
            for (var i = 0; i < l.length; i++) {
                if(loginController.getPinSha(l[i].pin) == pinSha)
                {
                    return l[i];
                }
            }
        }
        return null;
    },
    update: function(lieferant){
        var found = false;
        var l = lieferantenController.lieferanten
        if(l){
            for (var i = 0; i < l.length; i++) {
                if(l[i].id == lieferant.id)
                {
                    l[i] = lieferant;
                    found = true;
                    break;
                }
            }
        }
        else{
            lieferantenController.lieferanten = [];
        }
        if(found==false){
            lieferantenController.lieferanten.push(lieferant);
        }
        console.log("after update:")
        console.dir(lieferantenController.lieferanten);
    },
    deleteLieferant: function(){
        var found = false;
        var l = lieferantenController.lieferanten
        if(l){
            for (var i = 0; i < l.length; i++) {
                if(l[i].id == lieferant.id)
                {
                    lieferantenController.lieferanten.messages.splice(i, 1);
                    break;
                }
            }
        }
        else{
            lieferantenController.lieferanten = [];
        }
    }
    ,
    saveLieferanten: function(){
        localStorage.lieferantenListe = JSON.stringify(lieferantenController.lieferanten);
        console.log("Save lieferantenController.lieferanten!");
        console.log(localStorage.lieferantenListe)
    },
    loadLieferanten: function() {
        console.log("loadLieferanten!");
        console.log(localStorage.lieferantenListe)
        try {
            if (localStorage.lieferantenListe) {
                lieferantenController.lieferanten = JSON.parse(localStorage.lieferantenListe);
            }
        }
        catch(e){}
        if(!lieferantenController.lieferanten){
            lieferantenController.lieferanten = [];
        }
        console.log("lieferanten ->"+lieferantenController.lieferanten);
    }

}

/*
 dataController.emit('message', new ServerMessage({
 t: this.messageType.callNumber,
 n: number,
 text: text,
 callback: serverController.callbackHandler.register(newCallback)
 }));


new ServerMessage({
    t: this.messageType.create,
    n: this.buildDTO(nachricht)
})

 emit('message', new ServerMessage({
 t: this.messageType.callNumber,
 n: number,
 text: text,
 callback: serverController.callbackHandler.register(newCallback)
 }));
    */