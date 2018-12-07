'use strict';

angular.module('myWebApp')
	.controller('productController', productController);

productController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function productController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	$scope.scrollTo = function(id) {
		$location.hash(id)
		}
}