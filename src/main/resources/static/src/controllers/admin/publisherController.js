/**
 *  Publisher Controller
 */

lmsApp.controller("publisherController", function($scope, endpointConfig, lmsConstants){
	// Load publishers
	endpointConfig.getAllObjects(lmsConstants.ALL_PUBLISHERS).then(function(data){
		$scope.publishers = data;
	})

	// Edit publishers
	$scope.editPublisherModal = function(publisher){
		$scope.publisher = publisher;
	}
	
	$scope.updatePublisher = function(publisher){
		console.log($scope.publishers);
		endpointConfig.editObject(lmsConstants.SPECIFIC_PUBLISHER+$scope.publisher.publisherId, 
				$scope.publisher).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_PUBLISHERS).then(function(data){
				$scope.publishers = data;
			})
		});
	}
	
	
	// Refresh Publisher list on clicking close
	$scope.resetPublisher=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_PUBLISHERS).then(function(data){
			$scope.publishers = data;
		})
	}
	
	// Create new publisher
	$scope.createPublisher = function(publisherName, publisherAddress, publisherPhone){
		let publisher = {
			"publisherName": publisherName,
			"publisherAddress": publisherAddress,
			"publisherPhone": publisherPhone
		};
		endpointConfig.saveObject(lmsConstants.ALL_PUBLISHERS, publisher).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_PUBLISHERS).then(function(data){
				$scope.publishers = data;
			})
		});
	}
	
	//Delete publisher
	$scope.deletePublisher = function(publisherId){
		endpointConfig.deleteObject(lmsConstants.SPECIFIC_PUBLISHER+publisherId).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_PUBLISHERS).then(function(data){
				$scope.publishers = data;
			})
		});
	}
});