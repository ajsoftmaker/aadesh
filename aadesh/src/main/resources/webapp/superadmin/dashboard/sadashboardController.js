'use strict';

angular.module('myWebApp')
	.controller('sadashboardController', sadashboardController);

sadashboardController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function sadashboardController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	
	$scope.logout = function() {
		$state.go("main");
	}
	
//	$scope.desktop = function() {
//		$state.go("sadashboard");
//	}
	
	$scope.pinRequest = function() {
		$state.go("sapinrequest");
	}
	
	$scope.managePinRequest = function() {
		$state.go("sapinaccept");
	}
	
}