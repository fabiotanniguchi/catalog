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

//todo cookiesotre
//app.service('cartService', ['$cookies', function($cookies) {
app.service('cartService', function() {
	this.cart = {};
	
	this.addProduct = function(product, quantity){
		if(this.cart[product.id] == null){
			this.setProduct(product, quantity);
		}else if (quantity >= 0 && this.cart[product.id].quantity + quantity <= product.stock){
			this.cart[product.id].quantity += quantity;
		}else if (quantity < 0 ){
			alert("Unexpected error");
			return;
		}else{
			alert("Alertar sobre quantidade em estoque: " + product.stock);
			return;
		}
		this.persist();
	}
	
	this.setProduct = function(product, quantity){
		if(quantity < 0){ 
			alert("Alertar sobre quantidade negativa");
			return;
		}
		if(quantity > product.stock){
			alert("Alertar sobre quantidade em estoque: " + product.stock);
			return;
		}
		if(quantity == 0){
			this.cart[product.id] = undefined;
		}
		
		this.cart[product.id] = {};
		this.cart[product.id].product = product;
		this.cart[product.id].quantity = quantity;
		
		this.persist();
	}
	
	this.persist = function(){
		//$cookies.putOject("cart", this.cart);
	}
	
	this.getCart = function(){
		return this.cart;
		//return $cookies.getObject("cart");
	}
	
	this.totalValue = function(){
		value = 0;
		for(id in this.cart){
			value += this.cart[id].quantity * this.cart[id].product.price;
		}
		return value;
	}
	
	this.size = function(){
		return Object.keys(this.cart).length;
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
