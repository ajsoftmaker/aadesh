'use strict';

angular.module('myWebApp')
	.controller('galleryController', galleryController);

galleryController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function galleryController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	$scope.scrollTo = function(id) {
		$location.hash(id)
		}
}