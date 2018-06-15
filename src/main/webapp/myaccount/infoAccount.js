var app = angular.module('catalogProducts');

app.controller('InfoAccountCtrl', function($scope, $window, cartService, authService, baseHost) {

	$scope.user = {}

	var init = function (argument) {
		$scope.user = authService.getLoggedUser();
	}();

    $scope.logout = function(){
        console.info("logout");
        authService.logout();
        $window.location.href = baseHost;
    }

});
