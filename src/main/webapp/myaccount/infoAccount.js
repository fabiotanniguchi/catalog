var app = angular.module('catalogProducts');

app.controller('InfoAccountCtrl', function($scope, $window, $http, cartService, authService, baseHost) {

    $scope.user = {}
    $scope.orders = {}

	var init = function (argument) {
		$scope.user = authService.getLoggedUser();
	}();

    $scope.logout = function(){
        console.info("logout");
        authService.logout();
        $window.location.href = baseHost;
    }

    $scope.init = function() {
        $http({
            method: 'GET',
            url: baseHost + 'orders?userId=-LERzitEBJwXfPwqDAcg'
          }).then(function successCallback(response) {
              $scope.orders = response.data;
              console.info($scope.orders);
            }, function errorCallback(response) {
                console.info(response)      
            });

        $('.modal').modal();
    }

    $scope.openModal = function() {
        $('.modal').modal('open');
    }

});
