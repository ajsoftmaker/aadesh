'use strict';

angular.module('myWebApp')
	.controller('enquiryController', enquiryController);

enquiryController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function enquiryController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$rootScope.apiUrl = "/api/";
	$scope.scrollTo = function(id) {
		$location.hash(id)
		}
}