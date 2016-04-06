cordova.define("myplugin.reflect", function(require, exports, module) {
	var exec = require('cordova/exec');
	module.exports = {
		callActivity : function(succCallBack, failCallBack, args) {
			// 回调, 回调, 在cordova的config.xml注册的activity的name, "运行标识填死为call", 参数 
			exec(succCallBack, failCallBack, "PluginForActivity", "call", args);
		}
	};
});
