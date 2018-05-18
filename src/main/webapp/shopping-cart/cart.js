var app = angular.module('catalogProducts');

app.controller('CartCtrl', function($scope, cartService) {

	$scope.cart = cartService.getCart();
	$scope.totalValue = cartService.totalValue();
	
	$scope.show = function() {
		$scope.cart = cartService.getCart();
		$scope.totalValue = cartService.totalValue();
		$scope.$apply();
	}
	
	$scope.isEmpty = function() {
		return cartService.size() == 0;
	}
	
	$scope.calculaFrete = function() {
		//call postalService
		$scope.frete = 20;
		$scope.apply();
	}
});
