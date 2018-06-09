// var baseHost = "http://localhost:8080/";
var baseHost = "https://ftt-catalog.herokuapp.com/"
// var baseHost = "http://produtos1-2018s1.sa-east-1.elasticbeanstalk.com/";

var app = angular.module('catalogProducts');

app.controller('HomeCtrl', function($scope, productService, cartService) {
	$scope.products = [];
	$scope.categories = [];
	$scope.productsResult = [];
	$scope.categoryResult = "Destaques";

	$scope.parseProducts = function (result) {
		$scope.products = [];
		console.info(result);
		for(var i = 0; i < result.length; i++){
			if (result[i].price > 0 && result[i].stock > 0) {
				result[i].name = result[i].name
				$scope.products.push(result[i]);
			}
		}
		$scope.productsResult = $scope.products;

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

	$scope.parseProductsByCategory = function(id, name) {
        $scope.categoryResult = name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
        $scope.products = [];

        for (var i = 0; i < $scope.productsResult.length; i++) {
            if ($scope.productsResult[i].categoryId != null && $scope.productsResult[i].categoryId == id) {
                $scope.products.push($scope.productsResult[i]);
            }
        }
        //$scope.$apply();
    }

    $scope.$on('headerClickRefresh', function (event, args) {
        $scope.showProductsResult();
    });

    $scope.showProductsResult = function() {
        $scope.categoryResult = "Destaques";
        $scope.products = $scope.productsResult;
    }


	$scope.onLoad = function (argument) {
		$.ajax({url:  baseHost + "products", success: function(result){
			$scope.parseProducts(result);
		}});

		$.ajax({url: baseHost + "categories", success: function(result){
			$scope.parseCategories(result);
		}});
	};

});
