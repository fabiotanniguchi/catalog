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

app.controller('HeaderCtrl', function($scope, authService, cartService) {

    $scope.isLogged = false;
    $scope.user = null;

    $scope.cartSize = null;

    $scope.init = function() {
        if (authService.isLogged()) {
            $scope.isLogged = true;
            $scope.user = authService.getLoggedUser();
        }
        $scope.cartSize = cartService.getCartSize();
    }

    // $scope.$watch('cartSize', function(){
    // 	$scope.apply();
    // });

    $scope.$on('cartChanged', function(event, args){
        $scope.cartSize = cartService.getCartSize();
    });

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
app.service('cartService', function($rootScope) {
	this.cart = {};

	this.addProduct = function(product, quantity){
		if(this.cart[product.id] == null){
			this.setProduct(product, quantity);
		}else if (quantity >= 0 && this.cart[product.id].quantity + quantity <= product.stock){
			this.cart[product.id].quantity += quantity;
		}else if (quantity < 0 ){
            M.Toast.dismissAll();
			M.toast({html: 'Quantidade inválida!'})
			return;
		}else{
            M.Toast.dismissAll();
			M.toast({html: 'Não temos a quantidade suficiente em estoque'})
			return;
		}

        $rootScope.$broadcast('cartChanged');
        M.Toast.dismissAll();
        M.toast({html: 'Produto adicionado ao carrinho'})
		this.persist();
	}

	this.setProduct = function(product, quantity){
		if(quantity < 0){
            M.Toast.dismissAll();
            M.toast({html: 'Quantidade inválida!'})
			return;
		}
		if(quantity > product.stock){
            M.Toast.dismissAll();
			M.toast({html: 'Não temos a quantidade suficiente em estoque'})
			return;
		}
		if(quantity == 0){
            console.info(product, quantity);
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

	this.getCartSize = function(){
        qty = 0;
		for(id in this.cart){
			qty += this.cart[id].quantity;
		}
		return qty;
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
