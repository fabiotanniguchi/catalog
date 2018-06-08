var app = angular.module('catalogProducts');

app.controller('CartCtrl', function($scope, cartService) {

	$scope.show = function() {
		$scope.cart = cartService.getCart();
		$scope.orderInfo = {};
		$scope.orderInfo.subTotal = cartService.totalValue();
	}

	$scope.isEmpty = function() {
		return cartService.getCartSize() == 0;
	}

	$scope.getPostalFee = function() {
		//call postalService
		$scope.orderInfo.postalFee = 20;
		// $scope.$apply();
	}
});

app.controller('CartCtrlStep2', function($scope, cartService, authService) {

	$scope.selected = 0;

	$scope.show = function() {
		$scope.cart = cartService.getCart();
		$scope.cart.address = authService.getLoggedUser().address;
		$scope.orderInfo = {};
		$scope.orderInfo.subTotal = cartService.totalValue();
	}

	$scope.isEmpty = function() {
		return cartService.getCartSize() == 0;
	}

	$scope.getPostalFee = function() {
		//call postalService
		$scope.orderInfo.postalFee = 20;
		$scope.apply();
	}

	$scope.select = function(value) {
		$scope.selected = value;
	}
});
