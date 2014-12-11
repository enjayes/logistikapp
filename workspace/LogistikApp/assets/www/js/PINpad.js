
var PinPad = function(selector,callback){

     var that = this;
     this.pinPad = $(selector)[0];

     if(!callback||!this.pinPad)
      return;

    this.pinPad.innerHTML = "<form action='' method='' name='PINform' id='PINform' autocomplete='off'><input id=PINbox type='password' value='' name='PINbox' disabled /><br/><input type='button' class='PINbutton PINnumber' name='1' value='1' id='1'  /><input type='button' class='PINbutton PINnumber' name='2' value='2' id='2'  /><input type='button' class='PINbutton PINnumber' name='3' value='3' id='3'  /><br><input type='button' class='PINbutton PINnumber' name='4' value='4' id='4'  /><input type='button' class='PINbutton PINnumber' name='5' value='5' id='5'  /><input type='button' class='PINbutton PINnumber' name='6' value='6' id='6'  /><br><input type='button' class='PINbutton PINnumber' name='7' value='7' id='7'  /><input type='button' class='PINbutton PINnumber' name='8' value='8' id='8'  /><input type='button' class='PINbutton PINnumber' name='9' value='9' id='9'  /><br><input type='button' class='PINbutton PINclear' name='-' value='Neu' id='-'  /><input type='button' class='PINbutton PINnumber' name='0' value='0' id='0'  /><input type='button' class='PINbutton PINenter' name='+' value='Ok' id='+'  /></form>";

    this.pinBox =   $(that.pinPad).find('#PINbox');

    $(that.pinPad).find(".PINnumber").click(function(){
        that.addNumber(this);
    });

    $(that.pinPad).find(".PINclear").click(function(){
        that.pinBox.val("");
    });

    $(that.pinPad).find(".PINenter").click(function(){
        if (that.pinBox.val()) {
            callback(parseInt(that.pinBox.val()));
            that.pinBox.val("");
        }
    });
    
    this.addNumber = function(element){
       that.pinBox.val(that.pinBox.val() + element.value);
    }

    this.clear = function(){
        that.pinBox.val("");

    }

};


