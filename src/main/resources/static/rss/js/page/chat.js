var stompClient = null;
var name = $("#navbarMember").text();
var chat_alert = $("#chat_alert").text();
var chat_chat = $("#chat_chat").text();
var chat_join = $("#chat_join").text();
var chat_exit = $("#chat_exit").text();

function setConnected(connected)
{
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
		setTimeout("sendMessage('join', name, '');", 100);
	} else {
		$("#conversation").hide();
	}
	$("#chatlog").html("");
}

function connect()
{
	// Field
	var socket = new SockJS('/ws');
	
	// Init
	stompClient = Stomp.over(socket);
	
	// Process
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/api/stomp/queue/iot/v1/node/1', function(message) {
			procMessage(JSON.parse(message.body));
		});
	});
}

function disconnect()
{
	if (stompClient !== null) {
		sendMessage('exit', name, '');
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendMessage(cmd, name, content)
{
	/*stompClient.send("/wss/msg/chat", {}, JSON.stringify({
		'cmd' : cmd,
		'name' : name,
		'content' : content
	}));*/
	
	stompClient.send("/api/stomp/pub/iot/v1/node/1", {}, JSON.stringify({
		'sender' : '1',
		'target' : 'SERVER',
		'cmd' : 'test',
		'type' : 'test2',
		'obj' : 'test3',
		'value' : 'test4',
		'values' : 'test5'
	}));
}

function sendChat()
{
	// Field
	var content = $("#msg").val();
	
	// Exception
	if (content.length <= 0)
		return;
	
	// Init
	$("#msg").val('');
	
	// Process
	sendMessage('msg', name, content);
}

function procMessage(message)
{
	last_index = $("#chatlog tr:last").attr("index");
    total_rows = $("#chatlog tr").length;
    if(total_rows >= 15)
    	$("#chatlog tr:first").remove();
    
	if (message.cmd == "join")
		$("#chatlog").append('<tr><td>' + chat_alert + '</td><td>' + message.date + '</td><td class="text-center" colspan="2">' + message.name + ' - ' + chat_join + '</td></tr>');
	else if (message.cmd == "exit")
		$("#chatlog").append('<tr><td>' + chat_alert + '</td><td>' + message.date + '</td><td class="text-center" colspan="2">' + message.name + ' - ' + chat_exit + '</td></tr>');
	else
		$("#chatlog").append('<tr><td>' + chat_chat + '</td><td colspan="3">' + message.sender + '|' + message.target + '|' + message.cmd + '|' + message.type + '|' + message.obj + '|' + message.value + '|' + message.values + '</td></tr>');
}

window.onload = function()
{
	connect();
}

$(function()
{
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#send").click(function() {
		sendChat();
	});
});