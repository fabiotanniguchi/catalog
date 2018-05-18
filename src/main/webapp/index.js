var baseHost = "http://localhost:8080/";
// var baseHost = "https://ftt-catalog.herokuapp.com/"

var app = angular.module('catalogProducts');

app.controller('HomeCtrl', function($scope, productService, cartService) {
	$scope.products = [];
	$scope.categories = [];

	$scope.parseProducts = function (result) {
		$scope.products = [];
		console.info(result);
		for(var i = 0; i < result.length; i++){
			if (result[i].price > 0 && result[i].stock > 0) {
				result[i].name = result[i].name
				$scope.products.push(result[i]);
			}
		}
		$scope.$apply();
	}

	$scope.showDetails = function(obj) {
		productService.setCurrentObject(obj);
	}

	$scope.parseCategories = function (result) {
		$scope.categories = [];
		for(var i = 0; i < result.length; i++){
			$scope.categories.push(result[i]);
		}
		$scope.$apply();
	}


	$scope.onLoad = function (argument) {
		$.ajax({url:  "http://localhost:8080/products", success: function(result){
			$scope.parseProducts(result);
		}});

		$.ajax({url: baseHost + "categories", success: function(result){
			$scope.parseCategories(result);
		}});
	};

});
