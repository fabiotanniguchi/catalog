var app = angular.module('catalogProducts');

app.component('header', {
    templateUrl: "./components/header.html"
});

app.component('footer', {
    templateUrl: "./components/footer.html"
});

app.component('loading', {
    templateUrl: "./components/loading.html"
});

app.controller('HeaderCtrl', function($scope, authService) {

    $scope.isLogged = false;
    $scope.user = null;

    $scope.init = function() {
        if (authService.isLogged()) {
            $scope.isLogged = true;
            $scope.user = authService.getLoggedUser();
        }
    }
});

app.service('productService', function(){

    this.currentObject = {};

    this.setCurrentObject = function(obj) {
        this.currentObject = obj;
    }

    this.getCurrentObject = function() {
        return this.currentObject;
    }
});

app.service('authService', function(){

    this.currentUser = null;

    this.setLoggedUser = function(user) {
        this.currentUser = user;
    }

    this.isLogged = function(user) {
        return this.currentUser != null;
    }

    this.getLoggedUser = function() {
        return this.currentUser;
    }

    this.logout = function() {
        this.currentUser = null;
    }
});
