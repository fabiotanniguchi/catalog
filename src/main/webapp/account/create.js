// var baseHost = "http://localhost:8080/"
var baseHost = "https://ftt-catalog.herokuapp.com/"
// var baseHost = "http://produtos1-2018s1.sa-east-1.elasticbeanstalk.com/";

var app = angular.module('catalogProducts');

app.controller('CreateAccountCtrl', function($scope, $location) {

    $scope.createAccount = function() {
        if(!$scope.user){
            alert("Preencha todos os campos");
            return;
        }

        if($scope.user.password != $scope.user.samePass){
            alert("As senhas não coincidem");
            return;
        }

        $scope.loading = true;
        $scope.message = "";

        var requestUrl = baseHost + "external/customers";
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(xhttp.readyState == 4){
                var result = xhttp.responseText;
                alert("Conta Criada com sucesso")
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
