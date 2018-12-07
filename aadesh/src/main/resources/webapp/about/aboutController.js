'use strict';

angular.module('myWebApp')
	.controller('aboutController', aboutController);

aboutController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function aboutController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	$scope.scrollTo = function(id) {
		$location.hash(id)
		}
}