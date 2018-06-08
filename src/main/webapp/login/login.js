// var baseHost = "https://ftt-catalog.herokuapp.com/"
var baseHost = "http://localhost:8080/"

var app = angular.module('catalogProducts');

app.controller('LoginCtrl', function($scope, authService) {

    $scope.login = function() {

		$scope.loading = true;
		$scope.message = "";

		var requestUrl = baseHost + "external/customers/login?email=" + $scope.user.email + "&password=" + $scope.user.password;
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
		  if(xhttp.readyState == 4){
		      var result = xhttp.responseText;
		      $scope.success(result);
		  }else{
			  $scope.fail(result);
		  }
		};
		xhttp.open("GET", requestUrl, true);
		xhttp.send();

        //TODO: call authService.setLoggedUser() after sucessful authentication;
    }

    $scope.success = function(result){
    	$scope.loading = false;
        console.info("success");
        console.info(result);
        localStorage.setItem("user_token", result)
        authService.setLoggedUser(result);
		window.location = "/";

      }

      $scope.fail = function(result){
    	$scope.loading = false;
        console.info("fail");
		// window.location = "/index.html";

      }
});
