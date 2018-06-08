/**
 *  BookLoan Controller
 */

lmsApp.controller("bookLoanController", function($scope, endpointConfig, lmsConstants){
	// Load branches
	endpointConfig.getAllObjects(lmsConstants.ALL_BOOK_LOANS_ADMIN).then(function(data){
		$scope.adminBookLoans = data;
	});
	
	$scope.updateBookLoan = function(bookLoan, days, hours, mins){
		console.log("Selected Day Action", $scope.currentSelectedDayAction);
		console.log("Selected Min Action", $scope.currentSelectedMinAction);
		console.log("Days: ", days);
		console.log("Hours: ", hours);
		console.log("Mins: ", mins);
		console.log("Action");
	}
});