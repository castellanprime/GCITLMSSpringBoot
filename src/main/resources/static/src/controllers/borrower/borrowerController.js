/**
 *  Borrower Controller
 */

lmsApp.controller("borrowerController", function($scope, endpointConfig, lmsConstants){
	// Load borrowers
	endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
		$scope.borrowers = data;
	});
	
	endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
		$scope.branches = data;
	})
	
	// Edit borrowers
	$scope.editBorrowerModal = function(borrower){
		$scope.borrower = borrower;
	}
	
	$scope.updateBorrowerName = function(borrower){
		console.log($scope.borrowers);
		endpointConfig.editObject(
				lmsConstants.SPECIFIC_BORROWER+$scope.borrower.cardNo+"?name="+$scope.borrower.name
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
				$scope.borrowers = data;
			})
		});
	}
	
	$scope.updateBorrowerAddress = function(borrower){
		console.log($scope.borrowers);
		endpointConfig.editObject(
				lmsConstants.SPECIFIC_BORROWER+$scope.borrower.cardNo+"?address="+$scope.borrower.address
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
				$scope.borrowers = data;
			})
		});
	}
	
	$scope.updateBorrowerPhone = function(borrower){
		console.log($scope.borrowers);
		endpointConfig.editObject(
				lmsConstants.SPECIFIC_BORROWER+$scope.borrower.cardNo+"?phone="+$scope.borrower.phone
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
				$scope.borrowers = data;
			})
		});
	}
	
	
	// Return / Check out book
	$scope.checkOutReturnBookModal = function(borrower){
		$scope.borrowerToEdit = borrower;
	}
	
	$scope.selectedBooksToCheckOut = [];
	
	$scope.selectBranchToCheckOutFrom = function(branch, borrower){
		$scope.selectedBranchToCheckOutFrom = branch;
		console.log($scope.selectedBranchToCheckOutFrom);
	}
	
	$scope.selectBookToCheckOut = function(book, borrower){
		console.log(borrower);
		
		// Check if the added element is already there
		let oldObjs = borrower.currentBookLoans.map(function(item){
			return item.book.bookId;
		});
		console.log("Books: ", oldObjs);
		let oldObjsSet = new Set(oldObjs);
		let bookObjs = book.map(function(item){
			return item.bookId;
		})
		let bookSet = new Set(bookObjs);
		bookObjs = Array.from(new Set([...bookSet].filter(x => !oldObjsSet.has(x))));
		console.log("Filtered book:" , bookObjs);
		let filteredBooks = bookObjs.map(function(item){
			let val = book.find(function(element){
				return element.bookId === item;
			});
			return val;
		});
		book = filteredBooks;
		console.log("Final filtered books:", book);
		
		if ($scope.selectedBooksToCheckOut.length === 0){
			$scope.selectedBooksToCheckOut.push(...book);
		} else {
			let addedObjs = $scope.selectedBooksToCheckOut.push(...book);
			console.log($scope.selectedBooksToCheckOut);
			let scopeIds = $scope.selectedBooksToCheckOut.map(function(item){
				return item.bookId;
			});
			let addedIds = Array.from(new Set(scopeIds));
			let finalObjs = addedIds.map(function(item){
				let val = $scope.selectedBooksToCheckOut.find(function(element){
					return element.bookId == item; 
				});
				return val;
			});
			$scope.selectedBooksToCheckOut = finalObjs;
		} 
		console.log("Final selected book:", $scope.selectedBooksToCheckOut);
	}
	
	$scope.checkOutBook = function(borrower){
		console.log(borrower);
		for (let i in $scope.selectedBooksToCheckOut){
			let bookLoanInputDto = {
				branchId: $scope.selectedBranchToCheckOutFrom.branchId,
				bookId: $scope.selectedBooksToCheckOut[i].bookId
			};
			endpointConfig.saveObject(lmsConstants.SPECIFIC_BORROWER+borrower.cardNo+"/checkout", 
					bookLoanInputDto).then(function(id){
				endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
					$scope.borrowers = data;
					$scope.branches = data;
				})
			});
		}
	}
	
	$scope.selectBookToReturn = function(book){
		$scope.selectedBookToReturn = book;
		console.log($scope.selectedBookToReturn);
	}
	
	$scope.returnBook = function(borrower){
		console.log(borrower);
		let bookLoanInputDto = {
			branchId: $scope.selectedBookToReturn.branch.branchId,
			bookId: $scope.selectedBookToReturn.book.bookId
		};
		console.log(bookLoanInputDto);
		endpointConfig.editObject(lmsConstants.SPECIFIC_BORROWER+borrower.cardNo+"/return", 
				bookLoanInputDto).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
				$scope.borrowers = data;
				$scope.branches = data;
			})
		});
	}
	
	// Refresh Borrower list on clicking close
	$scope.resetBorrower=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
			$scope.borrowers = data;
		})
		$scope.selectedBooksToCheckOut = [];
		$scope.selectedBooksToReturn = null;
	}
	
	// Create new borrower
	$scope.createBorrower = function(borrowerName, borrowerAddress, borrowerPhone){
		let borrower = {
			name: borrowerName,
			address: borrowerAddress,
			phone: borrowerPhone
		};
		endpointConfig.saveObject(lmsConstants.ALL_BORROWERS, borrower).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
				$scope.borrowers = data;
			})
		});
	}
	
	//Delete borrower
	$scope.deleteBorrower = function(borrowerId){
		endpointConfig.deleteObject(lmsConstants.SPECIFIC_BORROWER+borrowerId).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BORROWERS).then(function(data){
				$scope.borrowers = data;
			})
		});
	}
});