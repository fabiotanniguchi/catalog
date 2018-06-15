var app = angular.module('catalogProducts');

app.controller('EditAccountCtrl', function($scope, cartService, authService, baseHost) {
	var init = function () {
		$scope.user = authService.getLoggedUser();
		console.info($scope.user);
		setTimeout(function() {
			$scope.$apply();
		},1000);
	}();

	$scope.save = function(){
		if($scope.user.password != $scope.user.samePass){
			M.toast({html: "As senhas digitadas não correspondem"}, outDuration = 1000);
			return;
		}
		console.info("save", $scope.user);
		var requestUrl = baseHost + "external/customers/" + $scope.user.id
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
		  if(xhttp.readyState == 4){
		      var result = xhttp.responseText;
		      $scope.success(result);
		  }else{
			  $scope.fail(result);
		  }
		};
		xhttp.open("PUT", requestUrl, true);
		xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
		xhttp.send(JSON.stringify($scope.user));
	}

	$scope.success = function(result){
		console.info(result);
		authService.setLoggedUser(btoa(JSON.stringify($scope.user)));
		M.toast({html: "Informações atualizadas com sucesso"}, outDuration = 1000);
	}

	$scope.fail = function(result){
		console.info(result);
	}
});
