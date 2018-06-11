/**
 *  Librarian Controller
 */

lmsApp.controller("librarianController", function($scope, endpointConfig, lmsConstants){
	// Load branches
	endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
		$scope.branches = data;
	});
	
	endpointConfig.getAllObjects(lmsConstants.BRANCH_LIBRARIAN+"/books").then(function(data){
		$scope.books = data;
	});
	
	// Edit borrowers
	$scope.editBranchModal = function(branch){
		$scope.branch = branch;
	}
	
	$scope.updateBranchName = function(branch){
		console.log($scope.branches);
		endpointConfig.editObject(
			lmsConstants.SPECIFIC_BRANCH_LIBRARIAN+$scope.branch.branchId+"?name="+branch.branchName
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
				$scope.branches = data;
			})
		});
	}
	
	$scope.updateBranchAddress = function(branch){
		console.log($scope.branches);
		endpointConfig.editObject(
			lmsConstants.SPECIFIC_BRANCH_LIBRARIAN+$scope.branch.branchId+"?address="+$scope.branch.branchAddress
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
				$scope.branches = data;
			})
		});
	}	
	
	$scope.selectBookToModifyCopiesOf = function(book, branch){
		$scope.bookToAddToBranch = book;
		endpointConfig.getAllObjects(
			lmsConstants.SPECIFIC_BRANCH_LIBRARIAN+$scope.branch.branchId+"/books/"+book.bookId+"/copies"
		).then(function(data){
			$scope.libraryCopies = data;
			console.log($scope.libraryCopies);
		});
	}
	
	$scope.updateBookCopiesInBranch = function(branch, total){
		console.log(branch);
		console.log(total);
		let libraryBookCopies = {};
		if ($scope.libraryCopies.branchId === 0 && $scope.libraryCopies.branchId === 0){
			libraryBookCopies = {
				bookId: $scope.bookToAddToBranch.bookId,
				branchId: branch.branchId,
				noOfCopies: total
			};
			console.log(libraryBookCopies);
			endpointConfig.saveObject(
				lmsConstants.SPECIFIC_BRANCH_LIBRARIAN+branch.branchId
						+"/books", libraryBookCopies
			).then(function(data){
				$scope.branches = data;
			});
		} else {
			libraryBookCopies = {
				bookId: $scope.libraryCopies.bookId,
				branchId: $scope.libraryCopies.branchId,
				noOfCopies: total
			};
			console.log(libraryBookCopies);
			endpointConfig.editObject(
				lmsConstants.SPECIFIC_BRANCH_LIBRARIAN+$scope.libraryCopies.branchId
					+"/books/"+$scope.libraryCopies.bookId+"/copies", libraryBookCopies
			).then(function(data){
				$scope.branches = data;
			});
		}
		$scope.libraryCopies = null;
	}
	
	
	// Refresh Borrower list on clicking close
	$scope.resetBranch=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
			$scope.branches = data;
		});
		$scope.libraryCopies = null;
	}
	
});