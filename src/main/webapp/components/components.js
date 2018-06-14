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


app.controller('MainCtrl', function($scope, authService, cartService) {
    $scope.$on('headerClick', function (event, args) {
        $scope.$broadcast('headerClickRefresh');
    });

});

app.controller('HeaderCtrl', function($scope, authService, cartService) {

    $scope.isLogged = false;
    $scope.user = null;

    $scope.cartSize = null;

    $scope.init = function() {
        console.log("init");
    	console.log(authService.isLogged());
    	console.log(authService.getLoggedUser());
        if (authService.isLogged()) {
            $scope.isLogged = true;
            $scope.user = authService.getLoggedUser();
        }
        $scope.cartClick();
        $scope.cartSize = cartService.getCartSize();
    }

    $scope.$on('cartChanged', function(event, args){
        $scope.cartSize = cartService.getCartSize();
    });

    $scope.headerClick = function() {
        $scope.$emit('headerClick', null);
        cartService.cleanCart();
    }

    $scope.cartClick = function() {
        console.log("cartClick");
        //$scope.$emit('cartClick', null);
        return cartService.cleanCart();
    }

});

app.service('productService', function(){

    this.currentObject = {};

    this.setCurrentObject = function(obj) {
        localStorage.setItem("currentObj", JSON.stringify(obj));
        this.currentObject = obj;
    }

    this.getCurrentObject = function() {
        if(!localStorage.getItem("currentObj") || localStorage.getItem("currentObj") === "undefined"){
            this.persist({});
            return {};
        }
        return JSON.parse(localStorage.getItem("currentObj"));
        //return this.currentObject;
    }
});

app.service('cartService', function($rootScope) {
	this.cart = {};

	this.addProduct = function(product, quantity){
        //console.info(product);
        var cart = this.getCart()

		if (quantity == undefined || quantity == null || quantity < 0 ){
            M.Toast.dismissAll();
            M.toast({html: 'Quantidade inválida!'})
            return cart;
        } else if(cart[product.id] == null){
            cart = this.setProduct(product, quantity);
		} else if (quantity == undefined || quantity == null || quantity < 0 ){
            M.Toast.dismissAll();
            M.toast({html: 'Quantidade inválida!'})
            return cart;
        } else if (quantity >= 0 && cart[product.id].quantity + quantity <= product.stock){
			cart[product.id].quantity += quantity;
			$rootScope.$broadcast('cartChanged');
                    M.Toast.dismissAll();
                    M.toast({html: 'Produto adicionado ao carrinho'})
		}else{
            M.Toast.dismissAll();
			M.toast({html: 'Não temos a quantidade suficiente em estoque'})
			return cart;
		}


		this.persist(cart);
	}

	this.setProduct = function(product, quantity){
        var cart = this.getCart()

        if (quantity == undefined || quantity == null) {
            return cart;
        } else if (quantity < 0){
            M.Toast.dismissAll();
            M.toast({html: 'Quantidade inválida!'})
			return cart;
		} else if(quantity > product.stock){
            M.Toast.dismissAll();
			M.toast({html: 'Não temos a quantidade suficiente em estoque'})
			return cart;
		}
		//if(quantity == 0){
          //  console.info(product, quantity);
		//	cart[product.id] = undefined;
		//}

        if (cart[product.id] != null)
        {
            // removeu produto
            if (cart[product.id].quantity > quantity) {
                M.Toast.dismissAll();
                M.toast({html: 'Produto removido do carrinho'})
            } else if (cart[product.id].quantity < quantity) { // adicionou produto
                M.Toast.dismissAll();
                M.toast({html: 'Produto adicionado ao carrinho'})
            }
        } else {
             M.Toast.dismissAll();
             M.toast({html: 'Produto adicionado ao carrinho'})
        }

		cart[product.id] = {};
		cart[product.id].product = product;
		cart[product.id].quantity = quantity;

		$rootScope.$broadcast('cartChanged');

		this.persist(cart);

        return cart;
	}

	this.persist = function(cart){
        var cart = localStorage.setItem("cart", JSON.stringify(cart));
        $rootScope.$broadcast('cartChanged');
        //console.log("persistCart", cart);
        return cart;
		//$cookies.putOject("cart", this.cart);
	}

	this.cleanCart = function() {
	    //console.log("cleanCart");
	    var cart = this.getCart();
	    for (id in cart){
	        if (cart[id].quantity == 0 || cart[id].quantity == null) {
                delete cart[id];
            }
        }

        this.persist(cart);
        return cart;
	}

	this.getCart = function(){
        if(!localStorage.getItem("cart") || localStorage.getItem("cart") === "undefined"){
            this.persist({});
            return {};
        }
        return JSON.parse(localStorage.getItem("cart"));
	}

	this.totalValue = function(){
        var cart = this.getCart()
		value = 0;
		for(id in cart){
			value += cart[id].quantity * cart[id].product.price;
		}
		return value;
	}

	this.getCartSize = function(){
        var cart = this.getCart();
        qty = 0;
		for(id in cart){
		    console.log("getCartSize", cart[id].quantity);
		    //if (cart[id].quantity == null) {
		     //   qty += 1;
		    //} else {
		        qty += cart[id].quantity;
		    //}
		}
		return qty;
	}
});

app.service('authService', function(){

    this.currentUser = null;

    this.setLoggedUser = function(user) {
        localStorage.setItem("user_token", user);
    }

    this.isLogged = function(user) {
        return localStorage.getItem("user_token") != null;
    }

    this.getLoggedUser = function() {
        if(localStorage.getItem("user_token"))
            return JSON.parse(atob(localStorage.getItem("user_token")));

        return null;
    }

	this.getLoggedUserToken = function() {
        return localStorage.getItem("user_token");
    }

    this.logout = function() {
        this.currentUser = null;
        localStorage.setItem("user_token", null);
    }
});
