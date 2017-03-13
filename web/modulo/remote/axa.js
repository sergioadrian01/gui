
window.$id = function (id){
    return document.getElementById(id);
};

function checkBrowser(){
    var hasCanvas = false;
    var msg = "";
    try {
        document.createElement("canvas").getContext("2d");
        hasCanvas = true;
    } catch (e) {
        msg = "This browser does not support Canvas.\n\n";
    };

    var noWebSocket = !("WebSocket" in window) && !("MozWebSocket" in window);
    var userAgent = navigator.userAgent;
    var isFirefox = userAgent.indexOf("Firefox") != -1;

    if (noWebSocket){
        msg += "This browser doesn't support WebSocket.\n\n";
        if (isFirefox){
            msg += "Please update to Firefox 6 or later.\n\n";
        }
        else if (userAgent.indexOf("Opera") != -1){
            msg += "Please open 'opera:config#Enable WebSockets' (type it in the link field) make 'Enable WebSockets' selected and restart Opera.\n\n";
        }
        else if (userAgent.indexOf("MSIE") != -1){
            msg += "Please install Google Chrome Frame.\n\n";
        }
    }

    var hasAudio = ("webkitAudioContext" in window) || ("AudioContext" in window) || isFirefox;

    if (!hasAudio && (userAgent.indexOf("Chrome") != -1)){
        msg += "Please enable Web Audio by going to 'about:flags' (type it in the link field), enabling 'Web Audio', clicking the restart button at the bottom of the page.\n\n";
    }

    if (msg.length > 0)
        alert(msg);

    var ready = !noWebSocket && hasCanvas;

    return ready;
};

function initUI(){
    checkBrowser();
}

window.addEventListener('load', initUI, false);

function exeRemoteApp(app,args){

    var waWidth = window.innerWidth;
    var waHeight = 800;
    alert(args);

    var params = {gateway:"10.133.244.181:8081", server:"10.133.244.181", user:"MXI02007436A", pwd:"Temporal1.", startProgram:"app",exe:"||"+app, args:args, mapPrinter:true, mapDisk:true, mapClipboard:true, waWidth:waWidth, waHeight:waHeight };



    var newWin = svGlobal.isMultitask;//open in new window, iPad is not multitask and can not run multiple RemoteApps at the same time

    var r = svManager.getInstance();
    if (r == null){
        r = new svGlobal.Rdp2(params);
        if (!newWin){
            r.addSurface(new svGlobal.LocalInterface());
            r.onclose = function(){
                r.hide();
            };
        }
    }


    if (newWin){
        var rail = window.open("rail.html");
        function onSurfaceReady(surface){
            r.addSurface(surface);
            r.startApp(app, "", "");
        };
        rail.svOnSurfaceReady = onSurfaceReady;
        r.run();
    }else{
        //hide other elements in this page, except the canvas element
        if (r.running()){
            r.startApp(app, "", "");
        }else{
            r.run();
        }
    }


}
