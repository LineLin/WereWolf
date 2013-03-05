﻿// JavaScript Document
var server ;
userNum=0;
pUrl="";
var person;
status="用户身份";
save=1;
poison=1;
userchoose="";
user= new Array(7);
user[0]=0;
user[1]=0;
user[2]=0;
user[3]=0;
user[4]=0;
user[5]=0;
user[6]=0; 
var Game={
	connection   : false,
	iframediv    : false,

	initialize: function() {
		if (navigator.appVersion.indexOf("MSIE") != -1) {
			Game.connection = new ActiveXObject("htmlfile");
			Game.connection.open();
			Game.connection.write("<html>");
			Game.connection.write("<script>document.domain = '"+document.domain+"'");
			Game.connection.write("</html>");
			Game.connection.close();
			Game.iframediv = Game.connection.createElement("div");
			Game.connection.appendChild(Game.iframediv);
			Game.connection.parentWindow.Game = Game;
			Game.iframediv.innerHTML = "<iframe id='Game_iframe' src='"+server+"'></iframe>";

		} else if (navigator.appVersion.indexOf("KHTML") != -1) {
			Game.connection = document.createElement('iframe');
			Game.connection.setAttribute('id',     'Game_iframe');
			Game.connection.setAttribute('src',    server);
			with (Game.connection.style) {
				position   = "absolute";
				left       = top   = "-100px";
				height     = width = "1px";
				visibility = "hidden";
			}
		    document.body.appendChild(Game.connection);

		} else {
			Game.connection = document.createElement('iframe');
			Game.connection.setAttribute('id',     'Game_iframe');
			with (Game.connection.style) {
			    left       = top   = "-100px";
			    height     = width = "1px";
			    visibility = "hidden";
			    display    = 'none';
			}
			Game.iframediv = document.createElement('iframe');
			Game.iframediv.setAttribute('src', server);
			Game.connection.appendChild(comet.iframediv);
			document.body.appendChild(comet.connection);
		}
	},


newUser:function(username){
	    for(var i=0;i<7;i++){
	        if(user[i]==0){
	            user[i]=1;
		       var	userId="user0"+(i+1);
		       $("#"+userId+" .userName").text(username);
		       $("#"+userId+"b").flip({
	            direction:"rl",
	            color:"#4C8DC7",
	            onEnd:function(){$("#"+userId).fadeIn("slow");$("#"+userId+"b").fadeOut("slow");}
		        });
		       showSystemMessage(username+" 加入了游戏");
		       return;
	       }
	    }
	   },

	   moveUser:function(data){
			 var $user=$("#user01");
						if($("#user01 .userName").text()==data){
						   $user=$("#user01");
		                   user[0]=0;
						}
						if($("#user02 .userName").text()==data){
						   $user=$("#user02");
		                   user[1]=0;
						}
						if($("#user03 .userName").text()==data){
						   $user=$("#user03");
		                   user[2]=0;
						}
						if($("#user04 .userName").text()==data){
						   $user=$("#user04");
		                   user[3]=0;
						}
						if($("#user05 .userName").text()==data){
						   $user=$("#user05");
		                   user[4]=0;
						}
						if($("#user06 .userName").text()==data){
						   $user=$("#user06");
		                   user[5]=0;
						}
						if($("#user07 .userName").text()==data){
						   $user=$("#user07");
		                   user[6]=0;
						}
						
			$user.fadeOut("slow");
		},

showRole:function(data){
	status=data;
	pUrl=data+"/";
	$("#dead0,#dead1,#dead2,#dead3,#dead4,#dead5,#dead6,#dead7").remove();
	showSystemMessage("你的角色:");
	var roleImg=$("<img></dimg>");
	switch (data){
		case "1":
			roleImg.attr("src","pictur/role1a.png");
			break;
		case "2":
			roleImg.attr("src","pictur/role2a.png");
			break;
		case "3":
			roleImg.attr("src","pictur/role3a.png");
			break;
		case "4":
			roleImg.attr("src","pictur/role4a.png");
			break;
		case "5":
			roleImg.attr("src","pictur/role5a.png");
			break;
		default:
		 alert("showRole参数错误！");
	}
	roleImg.attr("id","userRole");
	roleImg.css("display","none");
	setTimeout(function(){
		roleImg.fadeIn("slow");
		roleImg.animate({top:"589px",left:"662"});
	},400);
	changeSkin(data);
	$("#container").append(roleImg);
},


showMessage:function(data){
	$("#talk01").append(data+"\n");
	document.getElementById("talk01").scrollTop=999999999999999999999;
	/*document.all.talk01.scrollTop=document.all.talk01.scrollTop+10;*/
},

readyStatus:function(data,statue){
	      if(statue=="0"){
	            if($("#user00 .userName").text()==data){
				    $("#dead0").remove();
				}
				if($("#user01 .userName").text()==data){
				    $("#dead1").remove();
				}
				if($("#user02 .userName").text()==data){
				    $("#dead2").remove();
				}
				if($("#user03 .userName").text()==data){
				    $("#dead3").remove();
				}
				if($("#user04 .userName").text()==data){
				    $("#dead4").remove();
				}
				if($("#user05 .userName").text()==data){
				    $("#dead5").remove();
				}
				if($("#user06 .userName").text()==data){
				    $("#dead6").remove();
				}
				if($("#user07 .userName").text()==data){
				    $("#dead7").remove();
				}
			 }
			  if(statue=="1"){
				if($("#user00 .userName").text()==data){
				    $("#user00").append("<img id='dead0' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user01 .userName").text()==data){
				    $("#user01").append("<img id='dead1' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user02 .userName").text()==data){
				    $("#user02").append("<img id='dead2' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user03 .userName").text()==data){
				    $("#user03").append("<img id='dead3' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user04 .userName").text()==data){
				    $("#user04").append("<img id='dead4' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user05 .userName").text()==data){
				    $("#user05").append("<img id='dead5' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user06 .userName").text()==data){
				    $("#user06").append("<img id='dead6' class='userImage'  src='pictur/ready.png' />");
				}
				if($("#user07 .userName").text()==data){
				    $("#user07").append("<img id='dead7' class='userImage'  src='pictur/ready.png' />");
				}
			  }
},



talk:function(){ 
	showSystemMessage("请发言！");
	 $("#talk03b").attr("id","talk03");
	 if(document.getElementById("test").innerHTML==""){
		 time(30);
		 }
},


 order:function(stage,data) {
	if(stage=="1"){
		if(data!="null"){
			showSystemMessage(data+" 被杀");
			    if($("#user00 .userName").text()==data){
				    $("#user00").append("<img id='dead0' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user01 .userName").text()==data){
				    $("#user01").append("<img id='dead1' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user02 .userName").text()==data){
				    $("#user02").append("<img id='dead2' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user03 .userName").text()==data){
				    $("#user03").append("<img id='dead3' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user04 .userName").text()==data){
				    $("#user04").append("<img id='dead4' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user05 .userName").text()==data){
				    $("#user05").append("<img id='dead5' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user06 .userName").text()==data){
				    $("#user06").append("<img id='dead6' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user07 .userName").text()==data){
				    $("#user07").append("<img id='dead7' class='userImage'  src='pictur/die.png' />");
				}
	    }
		setTimeout(function(){
		$("#dark").fadeToggle("slow");
		showSystemMessage("天黑，守卫行动");
		if(status=="1"){
		setTimeout(function(){showSystemMessage("请选择你要守卫的人！");
		time(30);
	    setChooseView(); },2000);}
		},2000);
	}
	if(stage=="2"){
		  showSystemMessage("狼人杀人");
		  if(status=="2"){
		      if(data=="null"){
			   setTimeout(function(){showSystemMessage('请与队友统一意见，再选择！');
			   setTimeout(function(){Game.talk(); },2000);	
			   setChooseView();
               },2000);	
		     }else{
			   showSystemMessage("选择不统一，请重选！");
			   setTimeout(function(){Game.talk(); },2000);	
			   setChooseView();
			}
		}
	}
	if(stage=="3"){
		showSystemMessage("女巫用药");
		   if(status=="3"&&(poison!=0||save!=0)){
			   setTimeout(function(){
			   time(30);
			   if(data!="null"){
		   	       if(save!=0){
			            if(confirm(data+" 被杀，是否救活？")){
					        save=0;
							clearTimeout(sto);
	                        document.getElementById("test").innerHTML="";
							builtAjax(person,null,data);
						  }else if(poison!=0){
				                  if(confirm("请选择是否使用毒药")){
									   showSystemMessage("请选择你要对其使用毒药的人！");
			                            setChooseView();
					                    poison=0;
					                }else{
									clearTimeout(sto);
	                                document.getElementById("test").innerHTML="";
									builtAjax(person,null,null);
									}
			                     }   
			      }else if(poison!=0){
				                   if(confirm("请选择是否使用毒药")){
						                showSystemMessage("请选择你要对其使用毒药的人！");
			                            setChooseView();
					                    poison=0;
					                }else{
									clearTimeout(sto);
	                                document.getElementById("test").innerHTML="";
									builtAjax(person,null,null);
									}
			                     }
			   }else{if(poison!=0){
				                   if(confirm("请选择是否使用毒药")){
						                showSystemMessage("请选择你要对其使用毒药的人！");
			                            setChooseView();
					                    poison=0;
					                }else{
									clearTimeout(sto);
	                                document.getElementById("test").innerHTML="";
									builtAjax(person,null,null);
									}
			                     }}},2000);
		     }
	}
	if(stage=="4"){
		showSystemMessage("预言家预言");
		   if(status=="4"){
			   setTimeout(function(){showSystemMessage("请选择预知其身份的人！");
			   time(30);
		   	  setChooseView();},2000);
		    }
	}
	if(stage=="5"){
		var name=data.split(",");
		var name1="";
		if(name[0]=="Y") {
			name1="其身份是狼人";
		}else{
			name1="其身份是人类";
			}
		   if(status=="4"){
		   	  showSystemMessage(name1);
		    }
		   setTimeout(function(){showSystemMessage("天亮！");},2000);
		   setTimeout(function(){showSystemMessage("昨晚 "+data.substring(2)+" 被杀！");
		   $("#dark").fadeToggle("slow");},4000);
			var i=1;
			for(;i<name.length;i++){
				if($("#user00 .userName").text()==name[i]){
				    $("#user00").append("<img id='dead0' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user01 .userName").text()==name[i]){
				    $("#user01").append("<img id='dead1' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user02 .userName").text()==name[i]){
				    $("#user02").append("<img id='dead2' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user03 .userName").text()==name[i]){
				    $("#user03").append("<img id='dead3' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user04 .userName").text()==name[i]){
				    $("#user04").append("<img id='dead14' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user05 .userName").text()==name[i]){
				    $("#user05").append("<img id='dead5' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user06 .userName").text()==name[i]){
				    $("#user06").append("<img id='dead6' class='userImage'  src='pictur/die.png' />");
				}
				if($("#user07 .userName").text()==name[i]){
				    $("#user07").append("<img id='dead7' class='userImage'  src='pictur/die.png' />");
				}
				}
	}
	if(stage=="6"){
		if(data!="null"){
			var name=data.split(" and ");
			 showSystemMessage("结果不唯一，请重新投票！");
				time(30);
			 var i=0;
			for(;i<name.length;i++){
				if($("#user00 .userName").text()==name[i]){
				    var userName=$("#user00 .userName").text();
	  $("#user00").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user00"));
				}
				if($("#user01 .userName").text()==name[i]){
				   var userName=$("#user01 .userName").text();
	  $("#user01").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user01"));
				}
				if($("#user02 .userName").text()==name[i]){
				   var userName=$("#user02 .userName").text();
	  $("#user02").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user02"));
				}
				if($("#user03 .userName").text()==name[i]){
				    var userName=$("#user03 .userName").text();
	  $("#user03").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user03"));
				}
				if($("#user04 .userName").text()==name[i]){
				    var userName=$("#user04 .userName").text();
	  $("#user04").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user04"));
				}
				if($("#user05 .userName").text()==name[i]){
				    var userName=$("#user05 .userName").text();
	  $("#user05").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user05"));
				}
				if($("#user06 .userName").text()==name[i]){
				    var userName=$("#user06 .userName").text();
	  $("#user06").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user06"));
				}
				if($("#user07 .userName").text()==name[i]){
				   var userName=$("#user07 .userName").text();
	  $("#user07").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user07"));
				}
				}
		}else{
		   time(30);
		   showSystemMessage("请选择你要投死的玩家！");
		   setChooseView();
		}
	}
	if(stage=="7"){
	     var name=data.split(" ");
		 var src="";
		 for(var i=0;i<name.length;i++){
			 var num=name[i].substring(name[i].length-1,name[i].length);
			 var name1=name[i].substring(0,name[i].length-1);
			    if(num==1){
					src="pictur/role1a.png";
				}
			    if(num==2){
					src="pictur/role2a.png";
				}

			    if(num==3){
					src="pictur/role3a.png";
				}

			    if(num==4){
					src="pictur/role4a.png";
				}

			    if(num==5){
					src="pictur/role5a.png";
				}
				if($("#user00 .userName").text()==name1){
				    $("#user00").append("<img id='dead1' class='userImage'  src='"+src+"' />");
				}
				if($("#user01 .userName").text()==name1){
				    $("#user01").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
				if($("#user02 .userName").text()==name1){
				    $("#user02").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
				if($("#user03 .userName").text()==name1){
				    $("#user03").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
				if($("#user04 .userName").text()==name1){
				    $("#user04").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
				if($("#user05 .userName").text()==name1){
				    $("#user05").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
				if($("#user06 .userName").text()==name1){
				    $("#user06").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
				if($("#user07 .userName").text()==name1){
				    $("#user07").append("<img id='dead1' class='userImage'  src='"+src+"'  />");
				}
		 }
	}
	if(stage=="8"){
		showSystemMessage(data);
		}
 }
};


if (window.addEventListener) {
	window.addEventListener("load", Game.initialize, false);
} else if (window.attachEvent) {
	window.attachEvent("onload", Game.initialize);
}

function setServlet(name) {
	server = "http://192.168.1.131:8080/WereWolf/GameServlet?user=" + name;
	person = name ;
}
function onImg(obj){
	obj.style.border='2px solid #d6d4cb';
};

function outImg(obj){
	
	obj.style.border='0px solid #d6d4cb';
};

$(document).ready(function(){	
	$("#darkButton").click(function(){
		$("#dark").fadeToggle("slow");
	});
	$("#newUser").click(function(){
		newUser("123");
	});
	/*$(".user").click(function(){
		var $user=$(this);
		moveUser($user);
	});*/
	$("#sMB").click(function(){
		showSystemMessage("showSystemMessage");
	});
	$("#showRole").click(function(){
		showRole("1");
	});
	
	/*$("#talk03").click(function(){
		var data=$("#talk02").val();
		$("#talk02").val("");
		$("#talk02").focus();
		showMessage(data);

    });*/


	/*$("#talk03").
	if(document.activeElement.id=="talk03"){
		alert("yes");
	}*/
});
 




function showSystemMessage(messageText){
	/*alert("showSystemMessage");*/
	var messageDiv=$("<div></div>");
	var text=$("<text></text>").text(messageText);
	messageDiv.attr("id","messageDiv");
	messageDiv.css("background","url(pictur/"+pUrl+"showSystemMessage.png)");
	text.css("display","none");
	/*messageDiv.prepend("messageText");*/
	
	messageDiv.animate({width:"1024px"},"fast",function(){text.fadeIn("fast");});
	setTimeout(function(){text.fadeOut("fast");messageDiv.animate({width:"0px"},"fast",function(){messageDiv.remove();});},2000);
	messageDiv.prepend(text);
	$("#container").append(messageDiv);
	//messageDiv.remove();
}


function changeSkin(role){
	var uNColor="";
	var t1Color="";
	var t3Color="";
	switch(role){
		case "1":
			uNColor="#dea04b";
			t1Color="#ead1c5";
			t3Color="#eadea2";
			break;
		case "2":
			uNColor="#203c6e";
			t1Color="#e4d58c";
			t3Color="#b7ba6b";
			break;
		case "3":
			uNColor="#b44e2e";
			t1Color="#ebd8e2";
			t3Color="#fedcbd";
			break;
		case "4":
			uNColor="#236e8e";
			t1Color="#c0eaa2";
			t3Color="#ecb330";
			break;
		case "5":
			uNColor="#a0bd2b";
			t1Color="#bcd9e3";
			t3Color="#edd340";
			break;
		default:
		 alert("changeSkin参数错误！");
	}
	$("#container").css("background","url(pictur/"+pUrl+"background.png)");
	$(".user").css("background","url(pictur/"+pUrl+"user.png)");
	$("#talk").css("background","url(pictur/"+pUrl+"talk.png)");
	$("#talk2b").css("background","url(pictur/"+pUrl+"talk2b.png)");
	$("#messageDiv").css("background","url(pictur/"+pUrl+"showSystemMessage.png)");


	$(".userName").css("background-color",uNColor);
	$("#talk01").css("background-color",t1Color);
	$("#talk03").css("background-color",t3Color);
	/*$("#talk03").css("-webkit-box-shadow","0px 9px 0px rgba(237,190,64,1), 0px 9px 25px rgba(0,0,0,.7)");
    $("#talk03").css("-moz-box-shadow","0px 9px 0px rgba(237,190,64,1), 0px 9px 25px rgba(0,0,0,.7)");*/
    /*$("#talk03").css("display","block");
    $("#talk03").css("box-shadow","0 9px 0 0 rgba(237,190,64,1), 0 9px 25px 0 rgba(0,0,0,.7)");*/
    /*document.getElementById("talk03").style."box-shadow"=0px 9px 0px rgba(255,255,255,1), 0px 9px 25px rgba(0,0,0,.7)";*/
    /*$("#talk03").css("position","relative");
    $("#talk03").css("top","359px");*/
    /*addCssRule("#talk03:active", "box-shadow:0px 3px 0px rgba(229,155,84,1), 0px 3px 6px rgba(0,0,0,.9);");*/
}




function enterSend(event1){
	var keyNum=0;
	if (event1.shiftKey==1){
		if(window.event){ // IE
			keyNum = event1.keyCode;
		}
		else if(event1.which){ // Netscape/Firefox/Opera
			keyNum = event1.which;
		}
		if(keyNum==13){
			var data=$("#talk02").val();
			$("#talk02").val("");
			$("#talk02").focus();
			showMessage(data);
		}
	}
}

///////////////////////////////////////////////////////////////////////////////////////////
function sendm(){
	var mes=$("#talk02").val();
	if(mes == null || mes =="null")
		return ;
	$("#talk02").val("");
	Game.showMessage(mes);
	builtAjax(person,mes,null);
}

function outImg2(obj){
	obj.style.border='#FF0 2px dashed';
}

$(document).ready(function(){
	/*$("#talk03").click(function(){
		var mes=$("#talk02").val();
		$("#talk02").val("");
		showMessage(mes);
		builtAjax(person,mes,null);
    });*/
	$("#ready1").click(function(){
		$("#talk03").attr("id","#talk03b");
		$("#ready2").show();
		$("#ready1").hide();
		builtAjax(person,null,"ready");
	 });
	$("#ready2").click(function(){
		$("#talk03b").attr("id","#talk03");
		$("#ready1").show();
		$("#ready2").hide();
		builtAjax(person,null,"unready");
	 });
});




function builtAjax(user,message,data){
	$.ajax({
		 type: "GET",
		 url: "MessageControl", 
		 data: "user="+user+"&message="+message+"&data="+data
	 });
}

function setChooseView(){
	if(!document.getElementById("dead0")){
		var userName=$("#user00 .userName").text();
	  $("#user00").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user00"));
	}
	if(!document.getElementById("dead1")){
		var userName=$("#user01 .userName").text();
      $("#user01").attr("onclick","newconfirm('确定选择："+userName+"')");
	 borderChange($("#user01"));
	}
	if(!document.getElementById("dead2")){
		var userName=$("#user02 .userName").text();
	  $("#user02").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user02"));
	}
	if(!document.getElementById("dead3")){
		var userName=$("#user03 .userName").text();
	  $("#user03").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user03"));
	}
	if(!document.getElementById("dead4")){
		var userName=$("#user04 .userName").text();
	  $("#user04").attr("onclick","newconfirm('确定选择："+userName+"')");
	 borderChange($("#user04"));
	}
	if(!document.getElementById("dead5")){
		var userName=$("#user05 .userName").text();
	  $("#user05").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user05"));
	}
	if(!document.getElementById("dead6")){
		var userName=$("#user06 .userName").text();
	  $("#user06").attr("onclick","newconfirm('确定选择："+userName+"')");
	   borderChange($("#user06"));
	}
    if(!document.getElementById("dead7")){
		var userName=$("#user07 .userName").text();
	  $("#user07").attr("onclick","newconfirm('确定选择："+userName+"')");
	  borderChange($("#user07"));
	}
}

function borderChange(obj){
	obj.css({"border":"#FF0 2px dashed"}).attr("onmouseout","outImg2(this)");
}

$(document).ready(function(){
	$("#abc").click(function(){
		readyStatus("123",1);
	});
	$("#stage0").click(function(){
		readyStatus("123",0);
		//status=1;
		//order(7,"1231");
	});
	$("#stage1").click(function(){
		status=1;
		order(1,"123");
	});
	$("#stage2").click(function(){
		status=2;
		order(2,"repeat");
		//order(2,null);
	});
	$("#stage3").click(function(){
		status=3;
		order(3,"user3");
	});
	$("#stage4").click(function(){
		status=4;
		order(4,null);
	});
	$("#stage5").click(function(){
		status=4;
		order(5,"Y,自己,444");
	});
	$("#stage6").click(function(){
		order(6,"自己 and 1333");
	});
});

///////////////////////////////////////////////////////////////////////

sto="";
function time(t){
	var te=t-1;
	if(te<11){
		document.getElementById("test").innerHTML="<font color='red' size='15'>"+te+"</font>";
	}else
	    document.getElementById("test").innerHTML="<font color=#FFF size='6'>"+te+"</font>";
	if(te!=0){
	sto=setTimeout("time("+te+")",1000);
	}
	if(te==0){
	    $("#talk03").attr("id","talk03b");
	   $("#user00").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
      $("#user01").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user02").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user03").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user04").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user05").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user06").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user07").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  document.getElementById("test").innerHTML="";
	   builtAjax(person,null,null);
	}
}

/////////////////////////////////////////////////////////////////////////'

$(document).ready(function() {
	
	$('#yes').click(function() {
		$('.overlay-container').fadeOut().end().find('.window-container').removeClass('window-container-visible');
		
	  $("#user00").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
      $("#user01").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user02").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user03").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user04").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user05").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user06").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  $("#user07").attr("onclick","").attr("onmouseout","outImg(this)").css({"border":"0px solid #007FAA"});
	  clearTimeout(sto);
	  document.getElementById("test").innerHTML="";
	  $("#talk03").attr("id","talk03b");
	  builtAjax(person,null,userchoose);
	});
	
	$('#no').click(function() {
		$('.overlay-container').fadeOut().end().find('.window-container').removeClass('window-container-visible');
	});
});

function newconfirm(data){
    userchoose=data.substring(5);
	$('.window-container').find('h3').empty();
	$('.window-container').find('h3').append(data);
	$('.overlay-container').fadeIn(function() {
			$('.window-container').addClass('window-container-visible');
		});
}

