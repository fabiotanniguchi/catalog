var app = angular.module('catalogProducts');

app.controller('CreateAccountCtrl', function($scope, $location, baseHost) {

    $scope.createAccount = function() {
        if(!$scope.user){

            M.toast({html: "Preencha todos os campos"}, outDuration = 1000);
            return;
        }

        if($scope.user.password != $scope.user.samePass){
            M.toast({html: "As senhas não coincidem"}, outDuration = 1000);
            return;
        }

        $scope.loading = true;
        $scope.message = "";

        var requestUrl = baseHost + "external/customers";
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(xhttp.readyState == 4){
                var result = xhttp.responseText;
                M.toast({html: "Conta criada com sucesso!"}, outDuration = 1000);
                $scope.loading = false;
                $location.path("/login")
                $scope.$apply();
                
            }
        };
        xhttp.open("POST", requestUrl, true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        console.info(JSON.stringify($scope.user));
        xhttp.send(JSON.stringify($scope.user));
    }
});
