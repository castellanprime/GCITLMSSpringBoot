/**
 *  Author Controller
 */

lmsApp.controller("authorController", function($scope, endpointConfig, lmsConstants){
	// Load authors
	endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
		$scope.authors = data;
	})

	// Edit authors
	$scope.editAuthorModal = function(author){
		$scope.author = author;
	}
	
	$scope.updateAuthor = function(author){
		console.log($scope.authors);
		endpointConfig.editObject(lmsConstants.SPECIFIC_AUTHOR+$scope.author.authorId + "?name=" + $scope.author.authorName).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
				$scope.authors = data;
			})
		});
	}
	
	
	// Refresh Author list on clicking close
	$scope.resetAuthor=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
			$scope.authors = data;
		})
	}
	
	// Create new author
	$scope.createAuthor = function(authorName){
		endpointConfig.saveObject(lmsConstants.ALL_AUTHORS + "?name=" + authorName).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
				$scope.authors = data;
			})
		});
	}
	
	//Delete author
	$scope.deleteAuthor = function(authorId){
		endpointConfig.deleteObject(lmsConstants.SPECIFIC_AUTHOR+authorId).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_AUTHORS).then(function(data){
				$scope.authors = data;
			})
		});
	}
});