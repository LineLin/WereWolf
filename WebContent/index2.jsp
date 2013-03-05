<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="style.css"/>
<title>无标题文档</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/jquery-ui.min.js"></script>
<script src="jquery.flip.min.js" ></script>
<script src="ch0.js" >
</script>
<%
   String name1 = new String (request.getParameter("user").getBytes("ISO8859_1"),"utf-8");
%>
<script >
   var name ='<%=name1%>';
	setServlet(name);
</script>
</head>

<body>
<div id="container">
  <div id="dark">
    <img src="pictur/dark.png" />
  </div>
  <div id="user01b"></div>
  <div class="user" id="user01" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户1</text>
    <img  class="userImage" src="pictur/user1.jpg"  />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="user02b"></div>
  <div class="user" id="user02" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户2</text>
    <img  class="userImage" src="pictur/user2.jpg"  />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="user03b"></div>
  <div class="user" id="user03" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户3</text>
    <img  class="userImage" src="pictur/user3.jpg"  />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="user04b"></div>
  <div class="user" id="user04" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户4</text>
    <img  class="userImage" src="pictur/user4.jpg"  />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="user05b"></div>
  <div class="user" id="user05" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户5</text>
    <img  class="userImage" src="pictur/user5.jpg" />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="user06b"></div>
  <div class="user" id="user06" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户6</text>
    <img  class="userImage" src="pictur/user6.jpg"  />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="user07b"></div>
  <div class="user" id="user07" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName">用户7</text>
    <img  class="userImage" src="pictur/user7.jpg"  />
    <img class="role" src="pictur/role.png" />
  </div>
  <div id="talk">
    <img id="logo"src="pictur/logo.png" width="437" height="275" />
    <textarea id="talk01" readonly="readonly"></textarea>
      <img width="434" height="208" id="talk2b" />
      <textarea id="talk02" onkeydown="enterSend(event);"></textarea>
       <a href="javascript:void(0);" id="talk03" class="talkbutton" onclick="sendm()">发送</a>
       <!--<button id="talk03" type="button"/ onclick="sendm()">发送</button>--> 
       
  <button id="ready1" type="button"/>准备</button>
   <button id="ready2" type="button"/>取消准备</button>
  <button id="leave">结束发言</button>
  </div>
  <div class="user" id="user00" onmouseover="onImg(this)" onmouseout="outImg(this)">
    <text class="userName"><%=name1 %></text>
    <img  class="userImage" src="pictur/user8.jpg"  />
  </div>
  
  

  <div class="overlay-container">
	  <div class="window-container ">
		<h3></h3> 
		<span class="close" id="yes">确定</span><span class="close" id="no">取消</span></div>
	</div>
   <!-- <img src="pictur/role1a.png"  id="userRole2"/>  -->

  <!--  <div id="menu">
    <button id="abc" type="button" onclick="timeout(5)">readyStatus</button>
    <button id="stage0" type="button" onclick="timeout(5)">stage0</button>
    <button id="stage1" type="button">stage1</button>
    
    <button id="tB" type="button" onclick="timeout(60)">timeout</button>
  </div>
  -->
  

<div id="time"></div>
  
  <div id="messageDiv"></div> 
</div>

</body>
</html>
