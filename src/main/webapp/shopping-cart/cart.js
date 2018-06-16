var app = angular.module('catalogProducts');

app.controller('CartCtrl', function($scope, cartService, authService, baseHost, $location) {

	$scope.step = 0;
	$scope.varCep = null;
	$scope.validateCredit = false;

	$scope.show = function() {
		$scope.cart = cartService.cleanCart();
		//console.log("showCart", $scope.cart);
		//$scope.cart = cartService.getCart();
		$scope.orderInfo = {};
		$scope.orderInfo.subTotal = cartService.totalValue();
		if (authService.getLoggedUser() != null) {
		    $scope.varCep = authService.getLoggedUser().cep;
		} else {
		    $scope.varCep = null
		}
	}

	$scope.isEmpty = function() {
		//console.log("isEmpty", cartService.getCart())
		return cartService.getCartSize() == 0;
	}

	$scope.stepCheck = function(step) {
		return step == $scope.step;
	}

	$scope.flush = function() {
		$scope.step = $scope.step + 1;
		if($scope.step == 1){
			$.ajax({url: baseHost + "/external/postalcode/"+$scope.varCep, success: function(result){
				$scope.cart.address = {};
				$scope.cart.address.street = result.logradouro;
				$scope.cart.address.district = result.bairro;
				$scope.cart.address.city = result.cidade;
				$scope.cart.address.state = result.uf;
				$scope.$apply()
			}})
		}else if($scope.step == 3){
			
			if(cartService.getCartSize() >= 15){
				M.Toast.dismissAll();
				M.toast({html: 'Identificamos que você está comprando muitos itens de uma vez. Para concluir sua compra entre em contato com nosso suporte.'});
				$scope.step = $scope.step - 1;
				return;
			}
			
			console.info("step 2");
			var order = {};
			order.address = $scope.cart.address;
			order.address.postalCode = $scope.varCep;
			order.user = authService.getLoggedUser();
			if($scope.cart.payment == null){
				order.payment = null;
			}else{
				order.payment = $scope.cart.payment;
			}
			order.products = $scope.cart;
			delete order.products.payment;
			delete order.products.address;
			order.orderInfo = {};
			order.postalFee = $scope.orderInfo.postalFee;
			order.expectedDays = $scope.orderInfo.expectedDays;
			order.deliveryType = $scope.deliveryType;

			var products = [];
			for(var key in order.products){
				var product = order.products[key].product;
				product.quantity = order.products[key].quantity;
				products.push(product);
			}
			
			delete order.products;
			order.products = products;
			
			$scope.step = $scope.step - 1;
			var requestUrl = baseHost + "/orders";
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function(){
			  if(xhttp.readyState == 4){
				  var result = xhttp.responseText;
				  if(xhttp.status != 401){
					  M.Toast.dismissAll();
					  M.toast({html: 'Pedido Realizado com sucesso!'})
				  }else{
					  M.Toast.dismissAll();
					  M.toast({html: 'Pagamento Não Autorizado :('})
				  }
			  }else{
				  $scope.fail(result);
			  }
			};
			xhttp.open("POST", requestUrl, true);
			xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
			xhttp.send(JSON.stringify(order));

			localStorage.removeItem("cart");
			$location.path("/myaccount/info")
			
		}
	}


$scope.getPostalFee = function(cep, tipoEntrega) {
	$scope.varCep = cep;
	$scope.deliveryType = tipoEntrega;
	$.ajax({url:  baseHost + "external/logistics/calc?tipoEntrega="+tipoEntrega+"&cepDestino="+cep+"&quantidade="+cartService.getCartSize(), success: function(result){
		$scope.orderInfo.postalFee = parseFloat(result.preco);
		$scope.orderInfo.expectedDays = parseInt(result.prazo);
		$scope.$apply();
	}});
}

$scope.selectPayment = function(value) {
	$scope.selected = value;
}

$scope.validateCreditCard = function(){
	console.info("validateCreditCard");
	if (!$scope.cart.payment){
		M.toast({html: "Informe o cartão de crédito"}, outDuration = 1000);
		return;
	}

	var expirationDate = $scope.cart.payment.expirationDate;
	var monthAndYear = expirationDate.split("/");
	if (monthAndYear[1] < 2018 || (monthAndYear[1] == 2018 && monthAndYear[0] < 6)){
		M.toast({html: "Cartao Expirado"}, outDuration = 1000);
		return;	
	}

	console.info($scope.cart.payment);

	var user = authService.getLoggedUser();

	var requestUrl = baseHost + "external/credit/" + user.cpf;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4){
			var result = xhttp.responseText;
			$scope.success(result);
		}else{
			$scope.fail(result);
		}
	};
	xhttp.open("GET", requestUrl, true);
	xhttp.send();
}

$scope.success = function(result){
	result = JSON.parse(result);
	console.info("success", result);
	if(result.cpf == null || result.score > 300){
		M.toast({html: "Cartão de crédito validado com sucesso"}, outDuration = 1000);
	}else{
		M.toast({html: "Não é possivel prosseguir com a compra com esse cartão de credito"}, outDuration = 1000);
		return
	}
}

$scope.fail = function(result){
	// alert("Houve um problema para validar seu cartão de credito")
}

$scope.onQttChange = function (id, data) {
	if (data.quantity != undefined) {
		var cart = cartService.getCart()
		cart = cartService.setProduct(cart[id].product, data.quantity)
		data.quantity = cart[id].quantity;
		//cartService.persist(cart);
		$scope.orderInfo.subTotal = cartService.totalValue();
		//console.info(cartService.getCart()[id]);
	}
}
});