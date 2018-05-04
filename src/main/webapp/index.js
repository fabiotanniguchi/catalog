var baseHost = "http://localhost:8080/"
//var baseHost = "https://ftt-catalog.herokuapp.com/"

var parseProducts = function (result) {
	console.info("products", result.length);
	for(var i = 0; i < result.length; i++){
		console.info(result);
		$("#products-galery").append("<li><span>"+result[i].name+"</span><a href='http://placehold.it/640x480.png'><img src='"+(result[i].imageUrl == null ? 'nophoto.png' : result[i].imageUrl)+"' /></a></li>")
	}
}

var parseCategories = function (result) {
	console.info("categories", result.length);
	for(var i = 0; i < result.length; i++){
		console.info(result);
		$("#categories-galery").append("<li><a href='' title=''>"+result[i].name+"<span class='pull-right'>"+i+"</span></a></li>")
	}
}


var onLoad = function (argument) {
	$.ajax({url: baseHost + "products", success: function(result){
		parseProducts(result);
	}});

	$.ajax({url: baseHost + "categories", success: function(result){
		parseCategories(result);
	}});
};

onLoad();
