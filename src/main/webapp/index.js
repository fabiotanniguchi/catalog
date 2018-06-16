var app = angular.module('catalogProducts');

app.controller('HomeCtrl', function($scope, productService, cartService, baseHost) {
	$scope.products = [];
	$scope.categories = [];
	$scope.productsResult = [];
	$scope.categoriesResult = [];
	$scope.categoryName = "";
	$scope.categoryID = null;
	$scope.categorySelected = false;

	$scope.parseProducts = function (result) {
		$scope.products = [];
		$scope.productsResult = [];
		$scope.categoryID = null;
		$scope.categorySelected = false;
		//console.info(result);
		for(var i = 0; i < result.length; i++){
			if (result[i].price > 0) {
				$scope.productsResult.push(result[i]);
				if (result[i].highlight == true) {
				    $scope.products.push(result[i]);
				}
			}
		}

        if (!localStorage.getItem("buscar") || localStorage.getItem("buscar") === "undefined"){
            if ($scope.products.length == 0) {
                $scope.products = $scope.productsResult;
                $scope.categoryName = "Todos";
            } else {
                $scope.categoryName = "Destaques";
            }
        } else {
            $scope.searchProducts(localStorage.getItem("buscar"));
            localStorage.removeItem("buscar");
        }

		$scope.$apply();
	}

	$scope.showDetails = function(obj) {
		productService.setCurrentObject(obj);
	}

    $scope.$on('buscarClickRefresh', function (event, name) {
        $scope.searchProducts(name);
    });

    $scope.searchProducts = function(name) {
        name = name.toUpperCase()
        //console.log("buscarIndexTxt", name)

        findProducts = [];
        for (var i = 0; i < $scope.productsResult.length; i++) {
            if ($scope.productsResult[i].name.toUpperCase().includes(name) == true) {
                findProducts.push($scope.productsResult[i]);
            }
        }

        if (findProducts.length == 0) {
            M.Toast.dismissAll();
            M.toast({html: "Nenhum produto encontrado na busca!"}, outDuration = 1000);
            $scope.showProductsHighlight()
        } else {
            $scope.categoryName = "Busca";
            $scope.products = findProducts;
        }
    }

	$scope.parseCategories = function (result) {
		$scope.categories = [];
		for(var i = 0; i < result.length; i++){
		    $scope.categoriesResult.push(result[i]);
		    if (result[i].parentId == null) {
		        $scope.categories.push(result[i]);
		    }
		}
		$scope.$apply();
	}

	$scope.getCategoriesParentId = function(id) {
	    var categoriesFilhas = [];
        for(var i = 0; i < $scope.categoriesResult.length; i++){

            if ($scope.categoriesResult[i].parentId == id) {
                categoriesFilhas.push($scope.categoriesResult[i]);
            }
        }

        return categoriesFilhas;
	}

	$scope.parseProductsByCategory = function(id, name) {
	    console.log("categorias", $scope.categories)
        $scope.categoryName = name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
        $scope.products = [];
        $scope.categoryID = id;
        $scope.categorySelected = true;
        var categoriesFilhas = $scope.getCategoriesParentId(id);

        for (var i = 0; i < $scope.productsResult.length; i++) {
            if ($scope.productsResult[i].categoryId != null && ($scope.productsResult[i].categoryId == id || categoriesFilhas.includes(id))) {
                $scope.products.push($scope.productsResult[i]);
            }
        }

        if ($scope.products.length == 0) {
            M.Toast.dismissAll();
            M.toast({html: "Nenhum produto cadastrado nesta categoria!"}, outDuration = 1000);
            $scope.showProductsHighlight();
            $scope.categorySelected = false;
        }
    }

    $scope.$on('headerClickRefresh', function (event, args) {
        $scope.showProductsHighlight();
    });

    $scope.showProductsHighlight = function() {
        $scope.products = [];
        $scope.categorySelected = false;
        for (var i = 0; i < $scope.productsResult.length; i++) {
            if ($scope.productsResult[i].highlight == true) {
                $scope.products.push($scope.productsResult[i]);
            }
        }

        if ($scope.products.length == 0) {
            $scope.products = $scope.productsResult;
            $scope.categoryName = "Todos";
        } else {
            $scope.categoryName = "Destaques";
        }
    }


	$scope.onLoad = function (argument) {
	//?group_id=products1
		$.ajax({url:  baseHost + "products/group/GRUPOPRODUTOS1", success: function(result){
			$scope.parseProducts(result);
		}});

		$.ajax({url: baseHost + "categories/group/GRUPOPRODUTOS1", success: function(result){

			$scope.parseCategories(result);
		}});
	}

});
