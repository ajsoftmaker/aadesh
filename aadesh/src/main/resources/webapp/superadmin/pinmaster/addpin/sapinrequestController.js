'use strict';

angular.module('myWebApp')
	.controller('sapinrequestController', sapinrequestController);

sapinrequestController.$inject = ['$http', '$rootScope', '$scope', '$state', 'restAPIService', 'dialogs','$location'];

function sapinrequestController ($http, $rootScope, $scope, $state, restAPIService, dialogs,$location) {
	$scope.tenantsData = [];
	
	
	$scope.tenant = {
			tenantFirstName : "",
			tenantMiddleName : "",
			tenantLastName : "",
			tenantEmail : "",
			tenantPhone : "",
			tenantLandmark : "",
			tenantCity : "",
			tenantBankAcNo : "",
			tenantBankName : "",
			tenantBankBranch : "",
			tenantIfscCode : "",
			tenantPan : "",
			tenantParent : "",
			tenantJoiningDate : "",
			tenantTotalAmount : 0,
			tenantPaidAmount : 0
	};
	$scope.tenant.tenantJoiningDate = ""+new Date();
	
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
			$scope.tenantCount = $scope.tenantsData.length;
			$scope.tenant.tenantEmail = "ASW"+ pad($scope.tenantCount+1,6);
		}, function(error) {
			dialogs.error("Error", error.data.error, {'size' : 'sm'});
		});
	}
	
//	function addPinRequest() {
	$scope.addPinRequest = function() {
		console.log("Button clicked")
		$scope.tenant.tenantPhone = ""+$scope.tenant.tenantPhone; 
		$scope.tenant.tenantBankAcNo = ""+$scope.tenant.tenantBankAcNo;
		var promise = restAPIService.tenantsSuperService().save(null,$scope.tenant);
		promise.$promise.then(
				function (response) {
					dialogs.notify("Success", response.success, {'size': 'sm' });
					$state.reload();
				},
				function (error) {
					dialogs.error("Error", error.data.error, {'size': 'sm' });
				}
		);
	}
	
	$scope.tenantFirstNameValid = false;
	$scope.tenantFirstNameSuccess = false;
	$scope.tenantFirstNameError = false;
	$scope.tenantFirstNameFeedback = "";
	
	$scope.validFirstName = function(valid){
		$scope.tenantFirstNameValid = valid;
		if($scope.tenant.tenantFirstName != undefined) {
			if($scope.tenant.tenantFirstName.length <= 0) {
				$scope.tenantFirstNameError = true;
				$scope.tenantFirstNameFeedback = "has-error has-feedback";
			} else {
				$scope.tenantFirstNameSuccess = true;
				$scope.tenantFirstNameError = false;
				$scope.tenantFirstNameFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantFirstNameError = true;
			$scope.tenantFirstNameSuccess = false;
			$scope.tenantFirstNameFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantMiddleNameValid = false;
	$scope.tenantMiddleNameSuccess = false;
	$scope.tenantMiddleNameError = false;
	$scope.tenantMiddleNameFeedback = "";
	
	$scope.validMiddleName = function(valid){
		$scope.tenantMiddleNameValid = valid;
		if($scope.tenant.tenantMiddleName != undefined) {
			if($scope.tenant.tenantMiddleName.length <= 0) {
				$scope.tenantMiddleNameError = true;
				$scope.tenantMiddleNameFeedback = "has-error has-feedback";
			} else {
				$scope.tenantMiddleNameSuccess = true;
				$scope.tenantMiddleNameError = false;
				$scope.tenantMiddleNameFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantMiddleNameError = true;
			$scope.tenantMiddleNameSuccess = false;
			$scope.tenantMiddleNameFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantLastNameValid = false;
	$scope.tenantLastNameSuccess = false;
	$scope.tenantLastNameError = false;
	$scope.tenantLastNameFeedback = "";
	
	$scope.validLastName = function(valid){
		$scope.tenantLastNameValid = valid;
		if($scope.tenant.tenantLastName != undefined) {
			if($scope.tenant.tenantLastName.length <= 0) {
				$scope.tenantLastNameError = true;
				$scope.tenantLastNameFeedback = "has-error has-feedback";
			} else {
				$scope.tenantLastNameSuccess = true;
				$scope.tenantLastNameError = false;
				$scope.tenantLastNameFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantLastNameError = true;
			$scope.tenantLastNameSuccess = false;
			$scope.tenantLastNameFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantPhoneValid = false;
	$scope.tenantPhoneSuccess = false;
	$scope.tenantPhoneError = false;
	$scope.tenantPhoneFeedback = "";
	
	$scope.validPhone = function(valid){
		$scope.tenantPhoneValid = valid;
		if($scope.tenant.tenantPhone != undefined) {
			var tenantPhoneNo = ""+$scope.tenant.tenantPhone;
			if(tenantPhoneNo.length != 10 ) {
				$scope.tenantPhoneSuccess = false;
				$scope.tenantPhoneError = true;
				$scope.tenantPhoneFeedback = "has-error has-feedback";
				
			} else {
				$scope.tenantPhoneSuccess = true;
				$scope.tenantPhoneError = false;
				$scope.tenantPhoneFeedback = "has-success has-feedback";
				
			}
		} else {
			$scope.tenantPhoneError = true;
			$scope.tenantPhoneSuccess = false;
			$scope.tenantPhoneFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantBankAcNoValid = false;
	$scope.tenantBankAcNoSuccess = false;
	$scope.tenantBankAcNoError = false;
	$scope.tenantBankAcNoFeedback = "";
	
	$scope.validBankAc = function(valid){
		if($scope.tenant.tenantBankAcNo != undefined) {
			var tenantBankAcNo = ""+$scope.tenant.tenantBankAcNo;
			if(tenantBankAcNo.length >= 10  && tenantBankAcNo.length <= 16) {
				$scope.tenantBankAcNoSuccess = true;
				$scope.tenantBankAcNoError = false;
				$scope.tenantBankAcNoFeedback = "has-success has-feedback";
				$scope.tenantBankAcNoValid = false;
				$scope.addForm.$invalid = false;
			} else {
				$scope.tenantBankAcNoError = true;
				$scope.tenantBankAcNoSuccess = false;
				$scope.tenantBankAcNoFeedback = "has-error has-feedback";
				$scope.tenantBankAcNoValid = true;
				$scope.addForm.$invalid = true;
			}
		} else {
			$scope.tenantBankAcNoError = true;
			$scope.tenantBankAcNoSuccess = false;
			$scope.tenantBankAcNoFeedback = "has-error has-feedback";
			$scope.tenantBankAcNoValid = true;
			$scope.addForm.$invalid = true;
		}
	}
	
	$scope.tenantLandmarkValid = false;
	$scope.tenantLandmarkSuccess = false;
	$scope.tenantLandmarkError = false;
	$scope.tenantLandmarkFeedback = "";
	
	$scope.validStreet = function(valid){
		$scope.tenantLandmarkValid = valid;
		if($scope.tenant.tenantLandmark != undefined) {
			if($scope.tenant.tenantLandmark.length <= 0) {
				$scope.tenantLandmarkError = true;
				$scope.tenantLandmarkFeedback = "has-error has-feedback";
			} else {
				$scope.tenantLandmarkSuccess = true;
				$scope.tenantLandmarkError = false;
				$scope.tenantLandmarkFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantLandmarkError = true;
			$scope.tenantLandmarkSuccess = false;
			$scope.tenantLandmarkFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantCityValid = false;
	$scope.tenantCitySuccess = false;
	$scope.tenantCityError = false;
	$scope.tenantCityFeedback = "";
	
	$scope.validCity = function(valid){
		$scope.tenantCityValid = valid;
		if($scope.tenant.tenantCity != undefined) {
			if($scope.tenant.tenantCity.length <= 0) {
				$scope.tenantCityError = true;
				$scope.tenantCityFeedback = "has-error has-feedback";
			} else {
				$scope.tenantCitySuccess = true;
				$scope.tenantCityError = false;
				$scope.tenantCityFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantCityError = true;
			$scope.tenantCitySuccess = false;
			$scope.tenantCityFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantBankNameValid = false;
	$scope.tenantBankNameSuccess = false;
	$scope.tenantBankNameError = false;
	$scope.tenantBankNameFeedback = "";
	
	$scope.validBankName = function(valid){
		$scope.tenantBankNameValid = valid;
		if($scope.tenant.tenantBankName != undefined) {
			if($scope.tenant.tenantBankName.length <= 0) {
				$scope.tenantBankNameError = true;
				$scope.tenantBankNameFeedback = "has-error has-feedback";
			} else {
				$scope.tenantBankNameSuccess = true;
				$scope.tenantBankNameError = false;
				$scope.tenantBankNameFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantBankNameError = true;
			$scope.tenantBankNameSuccess = false;
			$scope.tenantBankNameFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantBankBranchValid = false;
	$scope.tenantBankBranchSuccess = false;
	$scope.tenantBankBranchError = false;
	$scope.tenantBankBranchFeedback = "";
	
	$scope.validBankBranch = function(valid){
		$scope.tenantBankBranchValid = valid;
		if($scope.tenant.tenantBankBranch != undefined) {
			if($scope.tenant.tenantBankBranch.length <= 0) {
				$scope.tenantBankBranchError = true;
				$scope.tenantBankBranchFeedback = "has-error has-feedback";
			} else {
				$scope.tenantBankBranchSuccess = true;
				$scope.tenantBankBranchError = false;
				$scope.tenantBankBranchFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantBankBranchError = true;
			$scope.tenantBankBranchSuccess = false;
			$scope.tenantBankBranchFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantIfscCodeValid = false;
	$scope.tenantIfscCodeSuccess = false;
	$scope.tenantIfscCodeError = false;
	$scope.tenantIfscCodeFeedback = "";
	
	$scope.validIfscCode = function(valid){
		$scope.tenantIfscCodeValid = valid;
		if($scope.tenant.tenantIfscCode != undefined) {
			if($scope.tenant.tenantIfscCode.length <= 0) {
				$scope.tenantIfscCodeError = true;
				$scope.tenantIfscCodeFeedback = "has-error has-feedback";
			} else {
				$scope.tenantIfscCodeSuccess = true;
				$scope.tenantIfscCodeError = false;
				$scope.tenantIfscCodeFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantIfscCodeError = true;
			$scope.tenantIfscCodeSuccess = false;
			$scope.tenantIfscCodeFeedback = "has-error has-feedback"; 
		}
	}
	
	$scope.tenantPanValid = false;
	$scope.tenantPanSuccess = false;
	$scope.tenantPanError = false;
	$scope.tenantPanFeedback = "";
	
	$scope.validPan = function(valid){
		$scope.tenantPanValid = valid;
		if($scope.tenant.tenantPan != undefined) {
			if($scope.tenant.tenantPan.length <= 0) {
				$scope.tenantPanError = true;
				$scope.tenantPanFeedback = "has-error has-feedback";
			} else {
				$scope.tenantPanSuccess = true;
				$scope.tenantPanError = false;
				$scope.tenantPanFeedback = "has-success has-feedback";
			}
		} else {
			$scope.tenantPanError = true;
			$scope.tenantPanSuccess = false;
			$scope.tenantPanFeedback = "has-error has-feedback"; 
		}
	}
	
	function pad(number, length) {
		   
	    var str = '' + number;
	    while (str.length < length) {
	        str = '0' + str;
	    }
	   
	    return str;

	}
	
}