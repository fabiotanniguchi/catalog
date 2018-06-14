var app = angular.module('catalogProducts');

app.controller('ProductDetailsCtrl', function($scope, $rootScope, productService, cartService) {

    $scope.product = {};

    $scope.show = function() {
        $scope.product = productService.getCurrentObject();
        $scope.product.quantity = 1;
    }

    $scope.addProduct = function(product) {
        console.log("addProduct", product)
    	var cart = cartService.addProduct(product, product.quantity);
    	//$rootScope.cartSize = getCartSize().size();
    }
    
});
