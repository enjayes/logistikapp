
function Job(id) {
    this.id = id;
    this.lieferanten_id = ""; //TODO:
    this.markt_id = localStorage.markt_id;
    this.timestamp_start = new Date();
    this.timestamp_end;
    this.fixtermin;
    this.gespraechspartner;

    //status
    this.pending = true;
    this.finished = false;
    this.checked_out = false; //lieferant hat den job explicit abgeschlossen, d.h. sich ausgeloggt
    this.template_name;

    //job_selector
    this.besuch = false;
    this.bestellung = false;
    this.verraeumung = false;
    this.austausch = false;

    //TODO: lieferantenschein1
    //lieferantenschein1
    this.t_ziel;
    this.t_grund;
    this.t_thematik;

    //lieferantenschein2

    this.cb_auftrag_getaetigt = false;//Auftrag getätigt
    this.cb_mhd = false;//MHD-Kontrolle
    this.cb_ruecknahme = false; //Rücknahme
    this.cb_reklamation = false; //Reklamationsbearbeitung
    this.cb_warenaufbau = false; //Warenaufbau
    this.cb_umbau = false; //Umbau
    this.cb_info_gespraech = false; //Info-Gespräch
    this.cb_nr_abgabe = false; //Nummer-Abgabe
    this.t_vk_euro_abgabe;
    this.t_warengruppe;
    this.cb_verkostung = false; //Verkostung
    this.cb_sortimentsinfo = false; //Sortimentsinfo
    this.cb_aktionsabsprache = false;
    this.cb_bemusterung = false; //Bemusterung
    this.cb_verlosung = false; //Verlosung

    //logout
    this.t_notizen

}