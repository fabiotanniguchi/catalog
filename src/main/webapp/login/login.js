var app = angular.module('catalogProducts');

app.controller('LoginCtrl', function($scope, authService) {

    $scope.login = function() {

		console.info("login");
		window.location = "/index.html";

        //TODO: call authService.setLoggedUser() after sucessful authentication;
    }
});
