var app = angular.module('catalogProducts');

  app.controller('formCtrl', function($scope, baseHost) {
      $scope.message = "";
      $scope.userId = "";
      $scope.password = "";

      $scope.loading = false;

      $scope.submit = function(){
          $scope.loading = true;
          $scope.message = "";

          var requestUrl = baseHost + "admuser?user=" + $scope.userId + "&password=" + $scope.password;
          var xhttp = new XMLHttpRequest();
          xhttp.onreadystatechange = function(){
              if(xhttp.readyState == 4){
                  var result = xhttp.responseText;
                  $scope.updateMessage(result);
              }
          };
          xhttp.open("GET", requestUrl, true);
          xhttp.send();
      }

      $scope.updateMessage = function(result){
          $scope.loading = false;

          if(result == "0"){
              $scope.message = "Login realizado com sucesso. Você será redirecionado ao dashboard...";
          }else{
              if(result == "1"){
                  $scope.message = "Usuário não encontrado";
              }else{
                  if(result == "2"){
                      $scope.message = "Senha inválida";
                  }else{
                      $scope.message = "Problema ao consultar login. Caso persista, fale com um gestor.";
                  }
              }
          }

          $scope.$apply();
      }

});
