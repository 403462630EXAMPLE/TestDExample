(function() {
	if (window.YTXBridge) { return }
	var messagingIframe
	var sendMessageQueue = []
	var receiveMessageQueue = []
	var messageHandlers = {}
	
	var CUSTOM_PROTOCOL_SCHEME = 'yy'
	var QUEUE_HAS_MESSAGE = '__QUEUE_MESSAGE__'
	
	var responseCallbacks = {}
	var uniqueId = 1
	
	function _createQueueReadyIframe(doc) {
		messagingIframe = doc.createElement('iframe')
		messagingIframe.style.display = 'none'
		doc.documentElement.appendChild(messagingIframe)
	}

	//set default messageHandler
	function init(messageHandler) {
		if (YTXBridge._messageHandler) { throw new Error('YTXBridge.init called twice') }
		YTXBridge._messageHandler = messageHandler
		var receivedMessages = receiveMessageQueue
		receiveMessageQueue = null
		for (var i=0; i<receivedMessages.length; i++) {
			_dispatchMessageFromObjC(receivedMessages[i])
		}
	}

	function send(data, responseCallback) {
		_doSend({ data:data }, responseCallback)
	}
	
	function registerHandler(handlerName, handler) {
		messageHandlers[handlerName] = handler
	}
	
	function callHandler(handlerName, data, responseCallback) {
		_doSend({ handlerName:handlerName, data:data }, responseCallback)
	}
	//sendMessage add message, 触发native处理 sendMessage 
	function _doSend(message, responseCallback) {
		if (responseCallback) {
			var callbackId = 'cb_'+(uniqueId++)+'_'+new Date().getTime()
			responseCallbacks[callbackId] = responseCallback
			message['callbackId'] = callbackId
		}
		sendMessageQueue.push(message)
		console.log("_doSend: iframe.src="+CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE);
		messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE;
	}

	// 提供给native调用,该函数作用:获取sendMessageQueue返回给native,由于android不能直接获取返回的内容,所以使用url shouldOverrideUrlLoading 的方式返回内容
	function _fetchQueue() {
		var messageQueueString = JSON.stringify(sendMessageQueue)
		sendMessageQueue = []

		//add by hq
		//android can't read directly the return data, so we can reload iframe src to communite with java
		console.log("_fetchQueue: iframe.src="+CUSTOM_PROTOCOL_SCHEME + '://return/_fetchQueue/' + messageQueueString);
        messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://return/_fetchQueue/' + encodeURIComponent(messageQueueString);
	}

	//提供给native使用,
	function _dispatchMessageFromObjC(messageJSON) {
		setTimeout(function _timeoutDispatchMessageFromObjC() {
			var message = JSON.parse(messageJSON);
			var messageHandler
			//回调
			if (message.responseId) {
				var responseCallback = responseCallbacks[message.responseId];
				if (!responseCallback) { return; }
				responseCallback(message.responseData);
				delete responseCallbacks[message.responseId];
			} else {
				var responseCallback;
				//直接发送
				if (message.callbackId) {
					var callbackResponseId = message.callbackId;
					responseCallback = function(responseData) {
						_doSend({ responseId:callbackResponseId, responseData:responseData });
					}
				}

				var handler = YTXBridge._messageHandler
				if (message.handlerName) {
					handler = messageHandlers[message.handlerName]
				}
				//查找指定handler
				try {
					handler(JSON.parse(message.data), responseCallback)
				} catch(exception) {
					if (typeof console != 'undefined') {
					    console.log(exception);
                        console.log(message);
						console.log("YTXBridge: WARNING: javascript handler threw.");
					}
				}
			}
		})
	}
	
	//提供给native调用,receiveMessageQueue 在会在页面加载完后赋值为null,所以
	function _handleMessageFromYTX(messageJSON) {
		if (receiveMessageQueue) {
			receiveMessageQueue.push(messageJSON)
		} else {
			_dispatchMessageFromObjC(messageJSON)
		}
	}

	window.YTXBridge = {
		init: init,
		send: send,
		registerHandler: registerHandler,
		callHandler: callHandler,
		_fetchQueue: _fetchQueue,
		addEventListener: registerHandler,
        fireEvent: callHandler,
		_handleMessageFromYTX: _handleMessageFromYTX
	}

	var doc = document
	_createQueueReadyIframe(doc)
	var readyEvent = doc.createEvent('Events')
	readyEvent.initEvent('YTXBridgeReady')
	readyEvent.bridge = YTXBridge
	doc.dispatchEvent(readyEvent)
})();
