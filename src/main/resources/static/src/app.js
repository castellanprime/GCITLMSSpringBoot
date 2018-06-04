var lmsApp = angular.module("lmsApp", ["ngRoute"]);

lmsApp.config(function($routeProvider) {
	$routeProvider.when("/", {
        redirectTo: "/home"
    }).when("/home", {
    	templateUrl: "views/home.html"
    }).when("/admin", {
    	templateUrl: "views/admin/index.html"
    }).when("/admin/authors", {
    	templateUrl: "views/admin/authors.html"
    }).when("/admin/publishers", {
    	templateUrl: "views/admin/publishers.html"
    }).when("/librarian", {
    	templateUrl: "views/librarian/index.html"
    }).when("/borrower", {
    	templateUrl: "views/borrower/index.html"
    })
});