'use strict';

angular.module('myWebApp')
	.controller('mainController', mainController);

mainController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function mainController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	$scope.planData = [
	                   {
	                	   stage:1,
	                	   required :10,
	                	   growth:50,
	                	   sponsor:1
	                   },
	                   {
	                	   stage:2,
	                	   required :60,
	                	   growth:100,
	                	   sponsor:2
	                   },
	                   {
	                	   stage:3,
	                	   required :240,
	                	   growth:150,
	                	   sponsor:1
	                   },
	                   {
	                	   stage:4,
	                	   required :960,
	                	   growth:250,
	                	   sponsor:1
	                   },
	                   {
	                	   stage:5,
	                	   required :3840,
	                	   growth:500,
	                	   sponsor:1
	                   },
	                   {
	                	   stage:6,
	                	   required :15360,
	                	   growth:2000,
	                	   sponsor:1
	                   },
	                   {
	                	   stage:7,
	                	   required :61440,
	                	   growth:6000,
	                	   sponsor:1
	                   },
	                   {
	                	   stage:8,
	                	   required :245760,
	                	   growth:12000,
	                	   sponsor:2
	                   }
	];
	$scope.scrollTo = function(id) {
		$location.hash(id)
		}
}