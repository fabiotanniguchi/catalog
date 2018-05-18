var baseHost = "http://localhost:8080/"
// var baseHost = "https://ftt-catalog.herokuapp.com/"

var app = angular.module('catalogProducts');

app.controller('CreateAccountCtrl', function($scope) {

    $scope.createAccount = function() {
        $scope.loading = true;
        $scope.message = "";

        var requestUrl = baseHost + "external/customers";
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(xhttp.readyState == 4){
                var result = xhttp.responseText;
                console.info(result);
            }
        };
        xhttp.open("POST", requestUrl, true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        console.info(JSON.stringify($scope.user));
        xhttp.send(JSON.stringify($scope.user));
        // TODO: submit $scope.user to back-end

        /*
        Customer1DTO.java
            private Integer id;
            private String name;
            private String email;
            private String password;
            private String samePass;
            private String birthDate;
            private String cpf;
            private String cep;
            private String address;
            private String gender;
            private String telephone;

        Customer1LoginDTO.java
            private String email;
            private String password;

        Customer1ChangePasswordDTO
            private String password;
            private String samePass;

        Customer1Rest.java (@RequestMapping(value = "/external/customers"))
            CREATE  @PostMapping with Customer1DTO
            UPDATE  @PutMapping(value="/{id}") with Customer1DTO
            FETCH   @GetMapping(value="/{id}") with Customer1DTO
            LOGIN   @GetMapping(value="/login") with Customer1LoginDTO
            CHGPWD  @PutMapping(value="/change/{id}") with Customer1ChangePasswordDTO
        */
    }
});
