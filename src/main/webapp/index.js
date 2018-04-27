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
 // $.ajax({url: "https://ftt-catalog.herokuapp.com/products", success: function(result){
 //        console.info(result)
 //    }});
	 var result = [{"id":"8b7474bb-b6ba-4869-aa5b-523eff8549e7","name":"GOOGLE","description":"Google","price":19.99,"stock":253,"brand":"Google","tags":[],"additionalInfo":null,"createdAt":1523553544579,"updatedAt":1523662502132,"status":"ACTIVE","highlight":true,"categoryId":"f910e243-79ca-4615-b581-3fe664779bb8","imageUrl":"https://www.google.com.br/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png"},{"id":"4400b84c-34d2-4b90-aba5-0ff8f143382d","name":"PRODUTO1TESTE","description":"Teste","price":19.99,"stock":253,"brand":"Vovó Mafalda Industries","tags":[],"additionalInfo":null,"createdAt":1523630248195,"updatedAt":1523630248195,"status":"ACTIVE","highlight":true,"categoryId":"f910e243-79ca-4615-b581-3fe664779bb8","imageUrl":null},{"id":"99efd011-64c7-40f2-81f8-6c84aabb698a","name":"PRODUTO1","description":"Teste","price":19.99,"stock":253,"brand":"Vovó Mafalda Industries","tags":[""],"additionalInfo":null,"createdAt":1523647484485,"updatedAt":1523647484485,"status":"ACTIVE","highlight":true,"categoryId":"f910e243-79ca-4615-b581-3fe664779bb8","imageUrl":null},{"id":"607d4f97-bcf1-4ba7-b1fe-a42fe517c07a","name":"TESTECLIENTE2","description":"TesteCliente2","price":500.0,"stock":253,"brand":"Teste","tags":[],"additionalInfo":null,"createdAt":1523649084000,"updatedAt":1523649223118,"status":"ACTIVE","highlight":true,"categoryId":"f910e243-79ca-4615-b581-3fe664779bb8","imageUrl":null},{"id":"29c66e25-7ad9-4b97-b0de-aeae9191d83c","name":"TESTECLIENTE2","description":"TesteCliente2Put","price":-50.0,"stock":253,"brand":"Teste","tags":[],"additionalInfo":null,"createdAt":1523649089345,"updatedAt":1523650223680,"status":"ACTIVE","highlight":true,"categoryId":"f910e243-79ca-4615-b581-3fe664779bb8","imageUrl":null},{"id":"f0e19ec4-c54b-48b1-baa9-b5d5153fe186","name":"TESTEADICIONALINFO","description":"TesteCliente2Put","price":50.0,"stock":253,"brand":"Teste","tags":["adicionalInfo"],"additionalInfo":{"car":"val2","Caracteristica":"Value"},"createdAt":1523661520314,"updatedAt":1523663683313,"status":"ACTIVE","highlight":true,"categoryId":"f910e243-79ca-4615-b581-3fe664779bb8","imageUrl":null}]
	 parseProducts(result);

// $.ajax({url: "https://ftt-catalog.herokuapp.com/categories", success: function(result){
 //        console.info(result)
 //    }});
	 var result = [{"id":"34d2340a-33ef-4f71-b5ec-88c4fc263ebf","name":"QUINTA-FEIRA","description":"Teste","parentId":null,"additionalInfo":null,"status":"ACTIVE","createdAt":1523553514079,"updatedAt":1523553514079},{"id":"8899bf7f-3434-4840-b097-d1e7bf77ab1b","name":"CATEGORIATESTE","description":"Teste","parentId":null,"additionalInfo":null,"status":"ACTIVE","createdAt":1523630250301,"updatedAt":1523630250301},{"id":"8323abcf-23be-4861-962b-6f5963f05e4a","name":"CATEGORIA1","description":"Teste","parentId":"4400b84c-34d2-4b90-aba5-0ff8f143382d","additionalInfo":null,"status":"ACTIVE","createdAt":1523632159040,"updatedAt":1523632159040},{"id":"e77570fa-f8d6-40bf-a11e-d9b8fa7da69d","name":"CATEGORIA1","description":"Teste","parentId":"8899bf7f-3434-4840-b097-d1e7bf77ab1b","additionalInfo":null,"status":"ACTIVE","createdAt":1523632224764,"updatedAt":1523632224764},{"id":"0126afde-e957-45a5-a6fc-1cb3c68dd8e5","name":"CATEGORIA1","description":"Teste","parentId":"8899bf7f-3434-4840-b097-d1e7bf77ab1b","additionalInfo":{"OIOIOIOIOIOIOIOI":"OIOIOIOIOI"},"status":"ACTIVE","createdAt":1523633916092,"updatedAt":1523633916092},{"id":"84292cd0-49f0-4489-86a6-ed994fcea95d","name":"TESTCLIENTE2","description":"TesteTESTE","parentId":null,"additionalInfo":null,"status":"ACTIVE","createdAt":1523648086125,"updatedAt":1523662595312}]
	 parseCategories(result);
};

onLoad();