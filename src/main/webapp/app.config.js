var app = angular.module('catalogProducts', ['ngRoute']);

app.config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {

      $routeProvider.
        when('/', {
          templateUrl: '../home.html'
        }).
        when('/login', {
          templateUrl: '../login/login.html'
        }).
        when('/newAccount', {
          templateUrl: '../account/create.html'
        }).
        when('/admin', {
          templateUrl: '../admin/admin.html'
        }).
        when('/product-details/:id', {
          templateUrl: '../product/details.html'
        }).
        when('/shopping-cart', {
            templateUrl: '../shopping-cart/cart.html'
        }).
        otherwise('/');

        $locationProvider.hashPrefix('!');
        $locationProvider.html5Mode(true);
    }
  ]);
