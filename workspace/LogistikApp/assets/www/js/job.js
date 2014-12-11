/**
 * Created by StandardB on 30.11.2014.
 */


function Job (id) {
    this.id = id;
    this.client_id = 1; //TODO:
    markt_id="leonberg"; //TODO configure
    this.timestamp_start =  new Date();
    this.timestamp_end;
    this.fixtermin;

    //status
    this.pending = true;
    this.finished = false;
    checked_out = false; //lieferant hat den job explicit abgeschlossen, d.h. sich ausgeloggt

    //job_selector
    this.besuch = false;
    this.bestellung = false;
    this.verraeumung = false;
    this.austausch = false;

    //TODO: lieferantenschein1
    //lieferantenschein1
    this.t_ziel
    this.t_grund
    this.t_thematik

    //lieferantenschein2

    cb_auftrag_getaetigt=false;//Auftrag getätigt
    cb_mhd=false;//MHD-Kontrolle
    cb_ruecknahme = false; //Rücknahme
    cb_reklamation=false; //Reklamationsbearbeitung
    cb_warenaufbau=false; //Warenaufbau
    cb_umbau = false; //Umbau
    cb_info_gespraech=false //Info-Gespräch
    cb_nr_abgabe=false; //Nummer-Abgabe
    this.t_vk_euro_abgabe;
    this.t_warengruppe
    cb_verkostung = false; //Verkostung
    cb_sortimentsinfo = false; //Sortimentsinfo
    cb_aktionsabsprache=false;
    cb_bemusterung=false; //Bemusterung
    cb_verlosung=false; //Verlosung

    //logout
    this.t_notizen

}