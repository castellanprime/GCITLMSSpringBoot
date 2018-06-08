/**
 *  Genre Controller
 */

lmsApp.controller("genreController", function($scope, endpointConfig, lmsConstants){
	// Load genres
	endpointConfig.getAllObjects(lmsConstants.ALL_GENRES).then(function(data){
		$scope.genres = data;
	})

	// Edit genres
	$scope.editGenreModal = function(genre){
		$scope.genre = genre;
	}
	
	$scope.updateGenre = function(genre){
		console.log($scope.genres);
		console.log(genre);
		endpointConfig.editObject(lmsConstants.SPECIFIC_GENRE+$scope.genre.genre_id + 
				"?name=" + $scope.genre.genreName).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_GENRES).then(function(data){
				$scope.genres = data;
			})
		});
	}
	
	
	$scope.selectBook = function(book){
		$scope.selectedBook = book;
		console.log($scope.selectedBook);
	}
	
	$scope.removeGenreFromBook = function(book){
		endpointConfig.deleteObject(
				lmsConstants.SPECIFIC_GENRE+$scope.genre.genre_id + "/books/" + $scope.selectedBook.bookId 
				).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
				$scope.genres = data;
			})
		});
	}
	
	// Refresh Genre list on clicking close
	$scope.resetGenre=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_GENRES).then(function(data){
			$scope.genres = data;
		})
	}
	
	// Create new genre
	$scope.createGenre = function(genreName){
		endpointConfig.saveObject(lmsConstants.ALL_GENRES + "?name=" + genreName).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_GENRES).then(function(data){
				$scope.genres = data;
			})
		});
	}
	
	//Delete genre
	$scope.deleteGenre = function(genreId){
		endpointConfig.deleteObject(lmsConstants.SPECIFIC_GENRE+genreId).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_GENRES).then(function(data){
				$scope.genres = data;
			})
		});
	}
});