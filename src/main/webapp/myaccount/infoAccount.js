var app = angular.module('catalogProducts');

app.controller('InfoAccountCtrl', function($scope, $window, $http, cartService, authService, baseHost) {

    $scope.user = {}
    $scope.orders = {}
    $scope.modalOrder = undefined

	var init = function (argument) {
		$scope.user = authService.getLoggedUser();
	}();

    $scope.logout = function(){
        console.info("logout");
        authService.logout();
        localStorage.clear();
        $window.location.href = baseHost;
    }

    $scope.init = function() {
        var user = authService.getLoggedUser();
        if (user != null) {
            $http({
                method: 'GET',
                url: baseHost + '/orders?userId=' + user.id
              }).then(function successCallback(response) {
                  $scope.orders = response.data;
                  console.info($scope.orders);
                }, function errorCallback(response) {
                    console.info(response)
                });

            $('.modal').modal();
        }
    }

    $scope.openModal = function(order) {
        $scope.modalOrder = order;
        $('.modal').modal('open');
    }

});
