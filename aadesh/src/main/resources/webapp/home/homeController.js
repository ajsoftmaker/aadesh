'use strict';

angular.module('myWebApp')
.controller('homeController', homeController);

function homeController ($state, $scope) {
	$scope.reportMode = localStorage.getItem("reportMode");
}