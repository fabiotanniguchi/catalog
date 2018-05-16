var app = angular.module('catalogProducts');

app.controller('ProductDetailsCtrl', function($scope, productService) {

    $scope.product = {};

    $scope.show = function() {
        $scope.product = productService.getCurrentObject();
    }
});
