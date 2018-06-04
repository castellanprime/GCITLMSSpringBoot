/**
 *  Branch Controller
 */

lmsApp.controller("branchController", function($scope, endpointConfig, lmsConstants){
	// Load branches
	endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
		$scope.branches = data;
	})

	// Edit branches
	$scope.editBranchModal = function(branch){
		$scope.branch = branch;
	}
	
	$scope.updateBranch = function(branch){
		console.log($scope.branches);
		endpointConfig.editObject(lmsConstants.SPECIFIC_BRANCH+$scope.branch.branchId, 
				$scope.branch).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
				$scope.branches = data;
			})
		});
	}
	
	
	// Refresh Branch list on clicking close
	$scope.resetBranch=function(){
		endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
			$scope.branches = data;
		})
	}
	
	// Create new branch
	$scope.createBranch = function(branchName, branchAddress){
		let branch = {
			"branchName": branchName,
			"branchAddress": branchAddress,
		};
		endpointConfig.saveObject(lmsConstants.ALL_BRANCHES, branch).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
				$scope.branches = data;
			})
		});
	}
	
	//Delete branch
	$scope.deleteBranch = function(branchId){
		endpointConfig.deleteObject(lmsConstants.SPECIFIC_BRANCH+branchId).then(function(id){
			endpointConfig.getAllObjects(lmsConstants.ALL_BRANCHES).then(function(data){
				$scope.branches = data;
			})
		});
	}
});