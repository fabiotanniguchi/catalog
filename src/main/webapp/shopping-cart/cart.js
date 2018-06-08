var baseHost = "http://localhost:8080/";
var app = angular.module('catalogProducts');

app.controller('CartCtrl', function($scope, cartService) {

	$scope.step = 0;
	$scope.varCep = 0;
	
	$scope.show = function() {
		$scope.cart = cartService.getCart();
		$scope.orderInfo = {};
		$scope.orderInfo.subTotal = cartService.totalValue();
	}

	$scope.isEmpty = function() {
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
		//call postalService
		$scope.varCep = cep;
		$.ajax({url:  baseHost + "external/logistics/calc?tipoEntrega="+tipoEntrega+"&cepDestino="+cep+"&quantidade="+cartService.totalItems(), success: function(result){
			$scope.orderInfo.postalFee = parseFloat(result.preco);
			$scope.$apply();
		}});
	}
	
	$scope.selectPayment = function(value) {
		$scope.selected = value;
	}
});