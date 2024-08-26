var replyManager =(function(){

	var getAll = function(obj, callback){	
		console.log("get All..."+obj);

		$.getJSON("/qreplies/QnA/"+obj, callback)
	
	};
	
	var add = function(obj, callback){
	
		console.log(obj);
		$.ajax({
			url:"/qreplies/" + obj["qid"],
			data: obj,
			type: "post",
			success:callback  
		});
	};
	
	
	var update = function(obj, callback){
		console.log(obj);
		$.ajax({
			url:"/qreplies/" + obj["qid"] + "/"+ obj["qrid"],
			data:  obj,
			type: "put",
 			success:callback
			
		});
	};

	
	var remove = function(obj, callback){
		console.log(obj);
		$.ajax({
			url:"/qreplies/"+obj["qid"]+"/"+obj["qrid"],
			type:"delete",
			success:callback
		});
	};
	
	return{
		"getAll" : getAll,
		"add": add,
		"update" : update,
		"remove" : remove
	};
	
})();