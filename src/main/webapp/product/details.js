var app = angular.module('catalogProducts');

app.controller('ProductDetailsCtrl', function($scope, $rootScope, productService, cartService) {

    $scope.product = {};

    $scope.show = function() {
        $scope.product = productService.getCurrentObject();
    }
    
    $scope.addProduct = function(product, quantity) {
    	cartService.addProduct(product, quantity);
//    	$rootScope.cartSize = cartService.size();
//    	$rootScope.$apply();
    }
    
});
