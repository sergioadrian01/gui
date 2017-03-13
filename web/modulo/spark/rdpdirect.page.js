window.onload = function() {

    var info = document.getElementById('joinSelect');
    if (info){
        info.onchange = function(e){
            svManager.getInstance().setJoinMode(e.target.value);
        };
    }

    var control =document.getElementById('requestControl');
    if (control){
        control.onclick = function(e){
            svManager.getInstance().requestControl();
        };
    }

    //here are three ways to create a Rdp instance:
    var q = location.search;
    var r;
    if (q.length < 1){//first way, used by login.html
        r = new svGlobal.Rdp2();
    }
    else{
        q = q.substring(1);
        //is web+rdp: protocol
        var dec = decodeURIComponent(q);
        var isWebRdp = (dec.indexOf('web+rdp:') == 0);
        if (isWebRdp){//second, you can always use http://xxx.xxx.x.x/rdpdirect.html?web+rdp:192.168.8.8 to access 192.168.8.8
            q = dec;
            q = q.substring(8);
            var idx = q.indexOf(':');
            var svr = (idx > 0) ? q.substring(0, idx) : q;
            var port = parseInt((idx > 0) ? q.substring(idx + 1) : '3389');
            var session = new Object();
            try{
                var gwDefined = window.opener != null && ('sparkGateway' in window.opener); 
                session['gateway'] = gwDefined ? window.opener.sparkGateway : location.host;//otherwise we think the gateway address is same as host address
                if (window.opener != null && ('sparkSession' in window.opener)){
                    session['session'] = window.opener.sparkSession;
                }
            }catch(e){

            };
            session['server'] = svr;
            session['port'] = port;
            session['width'] = window.innerWidth;
            session['height'] = window.innerHeight;
            r = new svGlobal.Rdp2(session);
        }
        else{//third, process normal parameters transfered from url like: http://xxx.xxx.x.x/rdpdirect.html?gateway=xx&server=yy&width=ww&height=hh&color=16
            var args = hi5.tool.queryToObj(q);

            var p = (args['useSSL'] == 'true' || 'https:' == location.protocol) ? 'wss://' : 'ws://';
            var w = args['width'] || window.innerWidth;
            var h = args['height'] || window.innerHeight;
            var color = args['server_bpp'] || args['color'];
            var gw = args['gateway'] || hi5.browser.getHost();
            r = new svGlobal.Rdp(p + gw + '/RDP?' + q, w, h, color);

        }
    }

    r.addSurface(new svGlobal.LocalInterface());

    r.run();
};
