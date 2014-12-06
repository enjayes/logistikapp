/**
 * Created by StandardB on 30.11.2014.
 */


function Job (id) {
    this.id = id;
    this.timestamp_start =  new Date();
    //this.timestamp_end =;
    //status
    this.pending = true;
    this.finished = false;

    //job_selector
    this.besuch = false;
    this.bestellung = false;
    this.verraeumung = false;
    this.austausch = false;

    //TODO: lieferantenschein 1 und 2
}