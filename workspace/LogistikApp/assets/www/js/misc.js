
misc = {
    getUniqueID: function () {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    },


    isMobileApp: function () {
        if (window.location.hash != "#android") {
            return false;
        }
        else {
            return true;
        }
    },

    call: function (number) {
        if (this.isMobileApp()) {
            window.open('skype:' + number + '?call', '_system')
        }
        else {
            windowOpen('skype:' + number + '?call', '_blank');
        }
    },


    openLink: function (url) {
        if (this.isMobileApp()) {
            window.open(url, '_system')
        }
        else {
            windowOpen(url, '_blank');
        }
    }
}