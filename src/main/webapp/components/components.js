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


app.service('productService', function(){

    this.currentObject = {};

    this.setCurrentObject = function(obj) {
        this.currentObject = obj;
    }

    this.getCurrentObject = function() {
        return this.currentObject;
    }
});
