/**
 *  BookLoan Controller
 */

lmsApp.controller("bookLoanController", function($scope, endpointConfig, lmsConstants){
	// Load branches
	endpointConfig.getAllObjects(lmsConstants.ALL_BOOK_LOANS_ADMIN).then(function(data){
		$scope.adminBookLoans = data;
	});
	
	$scope.editBookLoanModal = function(bookLoan){
		$scope.bookLoan = bookLoan;
	}
	
	$scope.selectDate = function(date){
		$scope.selectedDate = date;
	}
	
	$scope.selectTime = function(time){
		$scope.selectedTime = time;
	}
	
	// Refresh Book Loan List on clicking close
	$scope.resetBookLoan=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_BOOK_LOANS_ADMIN).then(function(data){
			$scope.adminBookLoans = data;
		});
	}
	
	$scope.updateBookLoan = function(bookLoan){
		console.log(bookLoan)
		console.log("Selected Date: ", $scope.selectedDate);
		console.log("Selected Time: ", $scope.selectedTime);
		let oldDate = new Date(bookLoan.dueDate);
		let newDate = new Date($scope.selectedDate);
		if (oldDate < newDate){
			let timeToAdd = new Date($scope.selectedTime);
			newDate.setHours(timeToAdd.getHours());
			newDate.setMinutes(timeToAdd.getMinutes());
			newDate.setSeconds(timeToAdd.getSeconds());
			console.log("Date to send: ", newDate);
			let dateString = newDate.toISOString().slice(0, -5);
			console.log("Edited date: ", dateString);
			let dateDto = {
					branchId: bookLoan.branch.branchId,
					bookId: bookLoan.book.bookId,
					cardNo: bookLoan.borrower.cardNo,
					dateOut: bookLoan.dateOut,
					dateIn: bookLoan.dateIn,
					dueDate: dateString
			}
			console.log("Date to pass: ", dateDto);
			endpointConfig.editObject(
					lmsConstants.SPECIFIC_BOOK_LOAN_ADMIN+"dueDate", 
					dateDto).then(function(id){
				endpointConfig.getAllObjects(lmsConstants.ALL_BOOK_LOANS_ADMIN).then(function(data){
					$scope.adminBookLoans = data;
				})
			});
		}
	}
});