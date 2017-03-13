var sparkConfig={gateway:null,servers:null,users:null,config:function(c,b,a,d,e,f){c=c+"/CONF?type="+b+"&action="+a;if(f)c+=f;var g=document.getElementById("gwPwd").value;if(g)c+="&gwPwd="+encodeURIComponent(g);hi5.tool.openWebSocket(c,e,d)},formatDate:function(c){if(!c)return"";var b=new Date(c);return b.getFullYear()+"-"+(b.getMonth()+1)+"-"+b.getDate()+" "+b.getHours()+":"+b.getMinutes()},parseDate:function(c){var b=c.split(" ");var a=b[0].split("-");var d=b[1].split(":");if(a.length!=3||d.length!=
2)throw"Date format must be yyyy-MM-dd hh:mm";return new Date(a[0],a[1]-1,a[2],d[0],d[1])},showMessage:function(c){document.getElementById("message").innerHTML=c},checkError:function(c){if(c.error){var b=__svi18n.errorCode["S"+c.name];if(c.message)b+=c.message;setTimeout(function(){alert(b)},0);return true}return false},refreshServers:function(){function c(d){var e=sparkConfig.servers.getValue(0);sparkConfig.config(sparkConfig.getGatewayAddr(),"servers","remove",a,null,"&id="+e)}function b(a){var e=
this.getValue(0);sparkConfig.editServer(e)}function a(a){sparkConfig.servers=a;var e=new hi5.DataTable(a);var f=new hi5.DataGrid(document.getElementById("servers.rows"));f.dataTable=e;f.open();document.getElementById("servers.type").value=a.type;document.getElementById("servers.display").checked=a.display;f.onrowclick=function(a){var d=a.target;var b=d["data-action"]||d.name;if(!b)return;e.perform(b)};e.addEvent("beforeremove",c);e.addEvent("onaction",b)}sparkConfig.config(sparkConfig.getGatewayAddr(),
"servers","get",a)},refreshUsers:function(){function c(d){var e=sparkConfig.users.getValue(0);sparkConfig.config(sparkConfig.getGatewayAddr(),"users","remove",a,null,"&name="+e)}function b(a){var e=this.getValue(0);sparkConfig.editUser(e)}function a(a){sparkConfig.users=a;var e=new hi5.DataTable(a);var f=new hi5.DataGrid(document.getElementById("users.rows"));f.dataTable=e;f.open();f.onrowclick=function(a){var d=a.target;var b=d["data-action"]||d.name;if(!b)return;e.perform(b)};e.addEvent("beforeremove",
c);e.addEvent("onaction",b)}sparkConfig.config(sparkConfig.getGatewayAddr(),"users","get",a)},refreshSessions:function(){function c(d){var e=sparkConfig.sessions.getValue(0);sparkConfig.config(sparkConfig.getGatewayAddr(),"sessions","remove",a,null,"&id="+e)}function b(a){var e=this.getValue(0);var b=this.getValue(9);var c="join";switch(b){case "RFB":c="joinvnc";break;case "SSH":c="joinssh";break;case "TELNET":c="jointelnet"}var h=location.protocol+"//"+document.getElementById("gateway").value+"/"+
c+".html?id="+e;window.open(h)}function a(a){sparkConfig.sessions=a;var e=new hi5.DataTable(a);var f=new hi5.DataGrid(document.getElementById("sessions.rows"));e.beforeGetValue=function(a,d){var b=e.cols[a].name;if(b=="thumbnail")return'<img src="'+d+'">';else return d};f.dataTable=e;f.open();f.onrowclick=function(a){var d=a.target;var b=d["data-action"]||d.name;if(!b)return;e.perform(b)};e.addEvent("beforeremove",c);e.addEvent("onaction",b);var g=document.getElementById("sessions.size");g.innerHTML=
a.rows.length}sparkConfig.config(sparkConfig.getGatewayAddr(),"sessions","get",a)},updateSymlink:function(){var c=document.getElementById("symlinkAccess");var b=document.getElementById("symlink.id");var a=document.getElementById("symlink.password");var d=document.getElementById("symlink.server");var e=document.getElementById("gateway").value;var f=location.protocol+"//"+e+"/rdpdirect.html?symlink="+b.value+"&gateway="+e;if(a.value)f+="&pwd="+a.value;if(d.value){var g=sparkConfig.servers.find(0,d.value);
if(g&&g[3])f+="&startProgram=app"}c.href=f},refreshSymlinks:function(){function c(d){var b=sparkConfig.symlinks.getValue(0);sparkConfig.config(sparkConfig.getGatewayAddr(),"symlinks","remove",a,null,"&id="+b)}function b(a){var b=this.getValue(0);sparkConfig.editSymlink(b)}function a(a){sparkConfig.symlinks=a;var e=new hi5.DataTable(a);var f=new hi5.DataGrid(document.getElementById("symlinks.rows"));f.dataTable=e;e.beforeGetValue=function(a,b){var d=e.cols[a].name;if(d=="validFrom"||d=="validTo")return hi5.DateUtils.formatDate(b);
else return b};e.beforeSetValue=function(a,b){var d=e.cols[a].name;if(d=="validFrom"||d=="validTo")return hi5.DateUtils.parseDate(b).getTime();else return b};f.onrowclick=function(a){var b=a.target;var d=b["data-action"]||b.name;if(!d)return;e.perform(d)};f.open();e.addEvent("beforeremove",c);e.addEvent("onaction",b);var g=document.getElementById("symlink.id");var h=document.getElementById("symlink.password");var l=document.getElementById("symlink.server");h.onchange=g.onchange=l.onchange=sparkConfig.updateSymlink}
sparkConfig.config(sparkConfig.getGatewayAddr(),"symlinks","get",a)},initUI:function(){function c(){function b(){}var e="&sType="+a.value+"&display="+d.checked;sparkConfig.config(sparkConfig.getGatewayAddr(),"servers_h","put",b,null,e)}function b(a){if(sparkConfig.gateway.prop.webfeed){document.getElementById("servers.add").style.disabled=true;document.getElementById("users.add").style.disabled=true}switch(a){case 1:if(sparkConfig.servers)return;sparkConfig.refreshServers();break;case 2:if(sparkConfig.users)return;
sparkConfig.refreshUsers();break;case 3:if(!sparkConfig.servers)sparkConfig.refreshServers();if(sparkConfig.symlinks)return;sparkConfig.refreshSymlinks();break;case 4:if(sparkConfig.sessions)return;sparkConfig.refreshSessions()}}var a=document.getElementById("servers.type");var d=document.getElementById("servers.display");a.onchange=c;d.onchange=c;var e=document.getElementById("conf3");e.addEvent("ontabchange",b);var f=hi5.tool.queryToObj();var g=f.port;var h=f.expect;if(!g){document.getElementById("gateway").value=
location.host;document.getElementById("conf0").style.display="block"}else{var l=window.location.host;var m=location.protocol;if(!l)l="localhost";document.getElementById("gateway").value=l;l=m+"//"+l;if(h){h=l+":"+h;document.getElementById("expectAddr").innerHTML=h;document.getElementById("expect").style.display="inline"}if(m=="http:"&&g!="80"||m=="https:"&&g!="443")l+=":"+g;l="<a href='"+l+"'>"+l+"</a>";var n=document.getElementById("sparkAddr");n.innerHTML=l;document.getElementById("conf1").style.display=
"block"}var p=document.getElementById("rfxOpt");if(p)p.onchange=function(a){if(a.target.checked)document.getElementById("colorOpt").selectedIndex=3}},getGatewayAddr:function(){return(location.protocol=="http:"?"ws:":"wss:")+"//"+document.getElementById("gateway").value},getAll:function(){function c(a){var b;var e=document.getElementById("frmGateway").elements;for(var c in a){b=e[c];if(!b)continue;var g=b.type;var h=a[c];if(g=="checkbox")b.checked=h==="true";else if(g!="file")b.value=h}}function b(a){if(sparkConfig.checkError(a))return;
sparkConfig.gateway=a;c(a.prop);document.getElementById("conf3").style.display="block"}sparkConfig.config(sparkConfig.getGatewayAddr(),"gateway","get",b)},saveGateway:function(){function c(a,b,e){var c=a.elements;var g=0;for(var h=c.length;g<h;g++){var l=c[g];if(!l)continue;var m=l.value;if(m===""||l.readonly)continue;var n=l.type;if("button"==n||"submit"==n||"file"==n)continue;var p=l.name;if(n=="checkbox")b[p]=l.checked+"";else b[p]=m}var q=document.getElementById("licenseFile").files;if(q.length>
0)hi5.file.readAsArrayBuffer(q[0],function(a){b.licenseFile=hi5.Base64.enc(a);e()});else e()}function b(a){var b;if(a.error){b=__svi18n.errorCode["S"+a.name];if(a.message)b+=a.message}else b=__svi18n.info.restart;setTimeout(function(){alert(b)},0)}c(document.getElementById("frmGateway"),sparkConfig.gateway.prop,function(){sparkConfig.config(sparkConfig.getGatewayAddr(),"gateway","put",b,JSON.stringify(sparkConfig.gateway))})},editServer:function(c,b){function a(a){if(!a.id)return;var c=a.rdp;if(!c)c=
{};c["isRDP"]=!!a.rdp;c.id=a.id;c.displayName=a.displayName;if(a.server)c.server=a.server;if(a.icon)c.icon=a.icon;if(c.mapDisk&&c.disks){var g=c.disks;var h;var q=Math.min(3,g.length);for(var k=0;k<q;k++){c["dosName"+k]=g[k].dosName;c["devicePath"+k]=g[k].devicePath;h=g[k].actions;c["actRedirect"+k]=(h&1)!=0;c["actDownload"+k]=(h&2)!=0;c["actUpload"+k]=(h&4)!=0}delete c.disks}if(a.vnc){var r=a.vnc;c["vnc.port"]="";c["vnc.password"]="";c["UseCopyRect"]=false;c["share"]=false;c["vnc.mapClipboard"]=
false;c["vnc.recording"]=0;for(k in r)if(k=="port"||k=="password"||k=="mapClipboard"||k=="color"||k=="sessionRecord")c["vnc."+k]=r[k];else c[k]=r[k];c["isVNC"]=true}if(a.ssh){var t=a.ssh;c["ssh.mapClipboard"]=false;c["ssh.fontSize"]="";c["ssh.port"]="";c["ssh.username"]="";c["ssh.recording"]=0;for(k in t)c["ssh."+k]=t[k];c["isSSH"]=true}if(a.telnet){var u=a.telnet;c["telnet.mapClipboard"]=false;c["telnet.fontSize"]="";c["telnet.port"]="";c["telnet.recording"]=0;for(k in u)c["telnet."+k]=u[k];c["isTELNET"]=
true}e=hi5.$("server.id").value;f=hi5.$("server.displayName").value;d.reset();hi5.browser.objToForm(c,d);if(!b)d._dataAction="put";if(e)hi5.$("server.id").value=e;if(f)hi5.$("server.displayName").value=f}var d=document.getElementById("servers.view");var e="";var f="";if(!b)d.reset();var g=d.visible?d:new hi5.Lightbox(d);g.onclose=function(){sparkConfig.refreshServers()};sparkConfig.showMessage("");if(!g.visible())g.show();d._dataAction="post";if(c)sparkConfig.config(sparkConfig.getGatewayAddr(),"server",
"get",a,null,"&id="+c);var h=hi5.$("server.server");if(!h.hide)new hi5.Select(h);h.beforedropdown=function(){var a=h.options;var b=sparkConfig.servers;var c=b.rows;a.length=0;var d=0;for(var e=c.length;d<e;d++)a[d]=new Option(c[d][1],c[d][0])};h.onchange=function(){var b=h.value;sparkConfig.config(sparkConfig.getGatewayAddr(),"server","get",a,null,"&id="+b)}},initServerList:function(c){var b=c.options;var a=sparkConfig.servers;if(!a)return;var d=a.rows;b.length=0;var e=0;for(var f=d.length;e<f;e++)b[e]=
new Option(d[e][1],d[e][0])},editUser:function(c){var b=document.getElementById("users.view");b.reset();var a=hi5.$("user.server");var d=new hi5.Lightbox(b);d.onclose=function(){a.hide();sparkConfig.refreshUsers()};sparkConfig.showMessage("");d.show();b._dataAction="post";if(c){b._dataAction="put";var e={name:c,password:sparkConfig.users.getValue(1),servers:sparkConfig.users.getValue(2),isDomainUser:sparkConfig.users.getValue(3),domainServer:sparkConfig.users.getValue(4),transferCredential:sparkConfig.users.getValue(5)};
hi5.browser.objToForm(e,b)}if(!a.hide)new hi5.Select(a,true);if(!sparkConfig.servers)sparkConfig.refreshServers();a.beforedropdown=function(){sparkConfig.initServerList(a)}},importUsers:function(){function c(a){if(sparkConfig.checkError(a))return;sparkConfig.refreshUsers()}var b=hi5.$("adUser").value;var a=hi5.$("adPwd").value;var d=hi5.$("adDomain").value;var e=hi5.$("adServer").value;var f=hi5.$("adOU").value;var g=hi5.$("adServers");var h=g?g.value:"";if(h)h="&servers="+encodeURIComponent(h);if(!b||
!a){alert("Please enter credentials.");return}sparkConfig.config(sparkConfig.getGatewayAddr(),"users","import",c,null,"&user="+encodeURIComponent(b)+"&pwd="+encodeURIComponent(a)+"&domain="+encodeURIComponent(d)+"&server="+e+"&ou="+encodeURIComponent(f)+h)},saveServer:function(){function c(a){if(a.error)sparkConfig.showMessage(a.message);else{b.reset();b._dataAction="post";if(!sparkConfig.gateway.server)sparkConfig.getAll()}}var b=document.getElementById("servers.view");sparkConfig.showMessage("");
var a=hi5.browser.formToObj(b);if(a.mapDisk){var d=0;var e=[];if(a.dosName0){if(a.actRedirect0)d|=1;if(a.actDownload0)d|=2;if(a.actUpload0)d|=4;e.push({dosName:a.dosName0,devicePath:a.devicePath0,actions:d});delete a.dosName0;delete a.devicePath0}if(a.dosName1){d=0;if(a.actRedirect1)d|=1;e.push({dosName:a.dosName1,devicePath:a.devicePath1,actions:d});delete a.dosName1;delete a.devicePath1}if(a.dosName2){d=0;if(a.actRedirect2)d|=1;e.push({dosName:a.dosName2,devicePath:a.devicePath2,actions:d});delete a.dosName2;
delete a.devicePath2}if(e.length>0)a.disks=e}var f={};f.id=a.id;f.displayName=a.displayName;if(a.server)f.server=a.server;if(a.icon)f.icon=a.icon;if(a.isRDP)f.rdp=a;if(a.isVNC)f.vnc={"port":a["vnc.port"],"password":a["vnc.password"],"encoding":a["encoding"],"quality":a["quality"],"compression":a["compression"],"color":a["vnc.color"],"UseCopyRect":a["UseCopyRect"],"share":a["share"],"sessionRecord":a["vnc.sessionRecord"],"mapClipboard":a["vnc.mapClipboard"]};if(a.isSSH)f.ssh={"mapClipboard":a["ssh.mapClipboard"],
"fontSize":a["ssh.fontSize"],"port":a["ssh.port"],"username":a["ssh.username"],"sessionRecord":a["ssh.sessionRecord"],"password":a["ssh.password"]};if(a.isTELNET)f.telnet={"mapClipboard":a["telnet.mapClipboard"],"fontSize":a["telnet.fontSize"],"sessionRecord":a["telnet.sessionRecord"],"port":a["telnet.port"]};sparkConfig.config(sparkConfig.getGatewayAddr(),"server",b._dataAction||"put",c,JSON.stringify(f));return false},saveUser:function(){function c(a){if(a.error)sparkConfig.showMessage(a.message);
else{b.reset();b._dataAction="post";if(!sparkConfig.gateway.user)sparkConfig.getAll()}}var b=document.getElementById("users.view");sparkConfig.showMessage("");var a=hi5.browser.formToObj(b);if(a.servers){var d=a.servers.split(",");var e=d.length;for(var f=0;f<e;f++)d[f]=d[f].trim();a.servers=d}sparkConfig.config(sparkConfig.getGatewayAddr(),"user",b._dataAction||"put",c,JSON.stringify(a));return false},editSymlink:function(c){var b=document.getElementById("symlinks.view");b.reset();var a=new hi5.Lightbox(b);
a.onclose=function(){sparkConfig.refreshSymlinks()};sparkConfig.showMessage("");a.show();b._dataAction="post";if(c){b._dataAction="put";hi5.browser.objToForm(sparkConfig.symlinks.getObject(),b)}else document.getElementById("symlink.id").value=hi5.tool.uuid();document.getElementById("symlink.id").onchange();var d=hi5.$("symlink.server");if(!d.hide)new hi5.Select(d);if(!sparkConfig.servers)sparkConfig.refreshServers();d.beforedropdown=function(){sparkConfig.initServerList(d)};sparkConfig.updateSymlink()},
saveSymlink:function(){function c(a){if(a.error)sparkConfig.showMessage(a.message);else{b.reset();b._dataAction="post";document.getElementById("symlink.id").value=hi5.tool.uuid()}}var b=document.getElementById("symlinks.view");sparkConfig.showMessage("");var a=hi5.browser.formToObj(b);if(a.validFrom)a.validFrom=hi5.DateUtils.parseDate(a.validFrom).getTime();if(a.validTo)a.validTo=hi5.DateUtils.parseDate(a.validTo).getTime();sparkConfig.config(sparkConfig.getGatewayAddr(),"symlink",b._dataAction||
"put",c,JSON.stringify(a));return false},notify:function(c,b){sparkConfig.config(sparkConfig.getGatewayAddr(),"sessions","notify",null,null,"&msg="+c+"&sendTo="+(b||[]).join(","))}};window.addEventListener("load",sparkConfig.initUI,false);