var app = angular.module('catalogProducts');

app.controller('CartCtrl', function($scope, cartService, authService, baseHost) {

	$scope.step = 0;
	$scope.varCep = 0;
	$scope.validateCredit = false;

	$scope.show = function() {
	    $scope.cart = cartService.cleanCart();
	    //console.log("showCart", $scope.cart);
		//$scope.cart = cartService.getCart();
		$scope.orderInfo = {};
		$scope.orderInfo.subTotal = cartService.totalValue();
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
		}
	}

	$scope.getPostalFee = function(cep, tipoEntrega) {
		$scope.varCep = cep;
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
		if(!$scope.cart.payment){
			alert("informe o cartão de crédito");
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
		if(result && result.score > 300){
			alert("Cartão de crédito validado com sucesso")
		}else{
			alert("Não é possivel prosseguir com a compra com esse cartão de credito")
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