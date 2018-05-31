var lmsApp = angular.module("lmsApp", ["ngRoute"]);

lmsApp.config(function($routeProvider) {
	$routeProvider.when("/", {
        redirectTo: "/home"
    }).when("/home", {
    	templateUrl: "views/home.html"
    })
});