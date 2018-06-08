/**
 *  Book Controller
 */

lmsApp.controller("bookController", function($scope, endpointConfig, lmsConstants){
	// Load books
	endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
		$scope.books = data;
	});
	
	endpointConfig.getAllObjects(lmsConstants.ALL_PUBLISHERS).then(function(data){
		$scope.publishers = data;
	});
	
	endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
		$scope.authors = data;
	});
	
	endpointConfig.getAllObjects(lmsConstants.ALL_GENRES).then(function(data){
		$scope.genres = data;
	});

	// Edit books
	$scope.editBookModal = function(book){
		$scope.book = book;
	}
	
	$scope.updateBookName = function(book){
		console.log($scope.books);
		endpointConfig.editObject(
				lmsConstants.SPECIFIC_BOOK+$scope.book.bookId+"?title="+$scope.book.bookName
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
				$scope.books = data;
			})
		});
	}
	
	$scope.selectedAuthors = [];
	
	$scope.selectAuthor = function(author){
		console.log(author);
		if ($scope.selectedAuthors.length === 0){
			$scope.selectedAuthors.push(...author);
		} else {
			let addedObjs = $scope.selectedAuthors.push(...author);
			console.log($scope.selectedAuthors);
			let scopeIds = $scope.selectedAuthors.map(function(item){
				return item.authorId;
			});
			let addedIds = Array.from(new Set(scopeIds));
			let finalObjs = addedIds.map(function(item){
				let val = $scope.selectedAuthors.find(function(element){
					return element.authorId == item; 
				});
				return val;
			});
			$scope.selectedAuthors = finalObjs;
		} 
		console.log($scope.selectedAuthors);
	}
	
	$scope.updateBookAuthor = function(book){
		//console.log($scope.books);
		for (let a in $scope.selectedAuthors){
			let item = $scope.selectedAuthors[a];
			console.log("Author Id: ", item.authorId);
			endpointConfig.editObject(
					lmsConstants.SPECIFIC_BOOK+$scope.book.bookId+"?authorId="+item.authorId
					).then(function(id){
				endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
					$scope.books = data;
				})
			});
		}
		$scope.selectedAuthors = [];
	}
	
	$scope.selectPublisher = function(publisher){
		$scope.selectedPublisher = publisher;
		console.log($scope.selectedPublisher);
	}
	
	$scope.updateBookPublisher = function(book){
		console.log($scope.books);
		endpointConfig.editObject(
				lmsConstants.SPECIFIC_BOOK+$scope.book.bookId+"?publisherId="+$scope.selectedPublisher.publisherId
			).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
				$scope.books = data;
			})
		});
	}
	
	
	$scope.selectedGenres = [];
	
	$scope.selectGenre = function(genre){
		if ($scope.selectedGenres.length === 0){
			$scope.selectedGenres.push(...genre);
		} else {
			let addedObjs = $scope.selectedGenres.push(...genre);
			console.log($scope.selectedGenres);
			let scopeIds = $scope.selectedGenres.map(function(item){
				return item.genre_id;
			});
			let addedIds = Array.from(new Set(scopeIds));
			let finalObjs = addedIds.map(function(item){
				let val = $scope.selectedGenres.find(function(element){
					return element.genre_id == item; 
				});
				return val;
			});
			$scope.selectedGenres = finalObjs;
		} 
		console.log($scope.selectedGenres);
	}
	
	$scope.updateBookGenre = function(book){
		console.log($scope.books);
		for (let g in $scope.selectedGenres){
			let item = $scope.selectedGenres[g];
			console.log("Genre Id: ", item.genre_id);
			endpointConfig.editObject(
					lmsConstants.SPECIFIC_BOOK+$scope.book.bookId+"?genreId="+item.genre_id
					).then(function(id){
				endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
					$scope.books = data;
				})
			});
		}
		$scope.selectedGenres = [];
	}
	
	// Refresh Book list on clicking close
	$scope.resetBook=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
			$scope.books = data;
		})
	}
	
	// Create new book
	$scope.createBook = function(bookName){
		let bookDto = {
			"title": bookName,
			"publisher": $scope.selectedPublisher,
		};
		console.log(bookDto);
		endpointConfig.saveObject(lmsConstants.ALL_BOOKS, bookDto).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
				$scope.books = data;
			})
		});
	}
	
	//Delete book
	$scope.deleteBook = function(bookId){
		endpointConfig.deleteObject(lmsConstants.SPECIFIC_BOOK+bookId).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BOOKS).then(function(data){
				$scope.books = data;
			})
		});
	}
});