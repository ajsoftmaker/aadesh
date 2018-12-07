'use strict';

angular.module('myWebApp')
	.controller('sapinacceptController', sapinacceptController);

sapinacceptController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function sapinacceptController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$scope.tenantsData = [];
	
	$scope.logout = function() {
		$state.go("main");
	}
	
	$scope.desktop = function() {
		$state.go("sadashboard");
	}
	
	getTenants();
	
	function getTenants() {
		var promise1 = restAPIService.tenantsService().query();
		promise1.$promise.then(function(response) {
			$scope.tenantsData = response;
		}, function(error) {
			dialogs.error("Error", error.data.error, {'size' : 'sm'});
		});
	}
	
	$scope.onEnable = function(tenant) {
		
		var dlg = dialogs.confirm("Are you sure ?","Are you sure you wish to enable this member? All login accounts for this member will be enabled",{'size' : 'sm'});
		dlg.result.then(function(btn) {
			tenant.tenantStatus = "1";
			var promise = restAPIService.tenantService(tenant.id).update(tenant);
			promise.$promise.then(function(response) {
				dialogs.notify("Success",  "Member Enabled Sucessfully Pin: "+tenant.tenantEmail+" Password : admin Mobile : "+tenant.tenantPhone, {'size' : 'sm'});
				getTenants();
				$state.reload();
			}, function(error) {
				dialogs.error("Error", error.data.error, {'size' : 'sm'});
			});
		}, function(btn) {
		});
		
	}
	
	$scope.onDisable = function(tenant) {
		var dlg = dialogs.confirm("Are you sure?","Are you sure you wish to disable this member? All login accounts for this member will be disabled",{'size' : 'sm'});
		dlg.result.then(function(btn) {
			tenant.tenantStatus = "0";
			var promise = restAPIService.tenantService(tenant.id).update(tenant);
			promise.$promise.then(function(response) {
				dialogs.notify("Success",  "Member Disabled Sucessfully", {'size' : 'sm'});
				getTenants();
				$state.reload();
			}, function(error) {
				dialogs.error("Error", error.data.error, {'size' : 'sm'});
			});
		}, function(btn) {
		});

	}
	
	$scope.onReset = function(tenant) {
		tenant.tenantPassword = "admin";
		var promise = restAPIService.tenantService(tenant.id).get();
		promise.$promise.then(function(response) {
			var reset = restAPIService.tenantResetService(tenant.id).update();
			reset.$promise.then(function(response) {
				dialogs.notify("Success",  response.success, {'size' : 'sm'});
				getTenants();
				$state.reload();
			}, function(error) {
				dialogs.error("Error",error.data.error, {'size' : 'sm'});
			});

		}, function(error) {
			dialogs.error("Error", error.data.error, {'size' : 'sm'});
		});
	}
	
	
}