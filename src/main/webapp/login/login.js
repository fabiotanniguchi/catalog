var app = angular.module('catalogProducts');

app.controller('LoginCtrl', function($scope, authService, baseHost) {

    $scope.login = function() {

			if (!$scope.user) {
				M.toast({html: "Preencha todos os campos para fazer login"}, outDuration = 1000);
				return;
			}

			$scope.loading = true;
			$scope.message = "";

			var requestUrl = baseHost + "external/customers/login?email=" + $scope.user.email + "&password=" + $scope.user.password;
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function(){
				if(xhttp.readyState == 4){
						var result = xhttp.responseText;
						console.info(result)
						$scope.success(result);
				}else{
					$scope.fail(result);
				}
			};
			xhttp.open("GET", requestUrl, true);
			xhttp.send();
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
      }
});
