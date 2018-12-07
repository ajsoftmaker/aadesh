'use strict';

angular.module('myWebApp')
	.controller('tadashboardController', tadashboardController);

tadashboardController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function tadashboardController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	$scope.tenant = $rootScope.info;
	$scope.tenant.tenantJoiningDate = new Date($scope.tenant.tenantJoiningDate);
	
	$scope.logout = function() {
		$state.go("main");
	}
	
//	$scope.desktop = function() {
//		$state.go("sadashboard");
//	}
	
	$scope.pinRequest = function() {
		$state.go("tapinrequest");
	}
	
	console.log($rootScope.info)
	
	
}