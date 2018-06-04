// a factory is used to create a service

lmsApp.factory('endpointConfig', function($http){
	return{
		getAllObjects: function(url){
			var config = {
				method: 'GET',
				url: url,
			};
			var returnList = {};
			return $http(config).then(function(response){
				returnList = response.data;
				return returnList;
			}).catch(function(err){
				console.log("GetAll does not work, with error: ", err.data);
			})
		},
		getWithQuery: function(url, value){
			var config = {
				method: 'GET',
				url: url,
				data: value
			};
			var returnList = {};
			return $http(config).then(function(response){
				returnList = response.data;
				return returnList;
			}).catch(function(err){
				console.log("GetAll does not work, with error: ", err.data);
			})
		},
		saveObject: function(url, body){
			var config = {
				method: 'POST',
				url: url,
				data: body
			};
			var returnList = {};
			return $http(config).then(function(response){
				returnList = response.data;
				return returnList;
			}).catch(function(err){
				console.log("SaveObject does not work, with error: ", err.data);
			})
		},
		editObject: function(url, value){
			var config = {
				method: 'PATCH',
				url: url,
				data: value
			};
			var returnList = {};
			return $http(config).then(function(response){
				returnList = response.data;
				return returnList;
			}).catch(function(err){
				console.log("EditObject does not work, with error: ", err.data);
			})
		},
		deleteObject: function(url){
			var config = {
				method: 'DELETE',
				url: url,
			};
			var returnList = {};
			return $http(config).then(function(response){
				returnList = response.data;
				return returnList;
			}).catch(function(err){
				console.log("DeleteObject does not work, with error: ", err.data);
			})
		}
	}
});