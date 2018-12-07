'use strict';

var myWebApp = angular.module(
		'myWebApp',
		[ 'ui.calendar','angularMoment','ui.router', 'ui.bootstrap', 'datatables', 'ngResource', 'ngTable',
				'ngTableResizableColumns', 'ngBootstrap', 'ngMessages','MassAutoComplete',
				'ngSanitize', 'dialogs.main', 'eqRestAPIs', 'angular-loading-bar','checklist-model','chart.js'])
		.config(config)
		.controller('appController', function($location,$http,$rootScope) {
			var path = $location.path();
			if (path.match(/activate/g) == null && path.match(/resetpassword/g) == null ) {
				if($http.defaults.headers.common.Authorization == null || $http.defaults.headers.common.Authorization == undefined) {
					if(localStorage.getItem("rootScope") !=null || localStorage.getItem("rootScope") != undefined) {
						$http.defaults.headers.common.Authorization = localStorage.getItem("rootScope");
						$rootScope.apiUrl="api/"
						localStorage.removeItem("rootScope");
					}else {
						window.location.href = "#/main";
					}
				}
			}
		}).controller('appCtrl', function($location, $scope) {
			  $scope.scrollTo = function(hash) {
				    $location.hash(hash);
				  };
				});
;

function config($stateProvider, $urlRouterProvider) {
	
	$stateProvider.state('main', {
		url : '/main',
		templateUrl : 'main/main.html',
		controller : "mainController"
	}).state('gallery', {
		url : '/gallery',
		templateUrl : 'gallery/gallery.html',
		controller : "galleryController"
	}).state('about', {
		url : '/about',
		templateUrl : 'about/about.html',
		controller : "aboutController"
	}).state('enquiry', {
		url : '/enquiry',
		templateUrl : 'enquiry/enquiry.html',
		controller : "enquiryController"
	}).state('product', {
		url : '/product',
		templateUrl : 'product/product.html',
		controller : "productController"
	})
	.state('login', {
		url : '/login',
		templateUrl : 'login/login.html',
		controller : "loginController"
	})
	.state('sadashboard', {
		url : '/sadashboard',
		templateUrl : 'superadmin/dashboard/sadashboard.html',
		controller : "sadashboardController"
	})
	.state('sapinrequest', {
		url : '/sapinrequest',
		templateUrl : 'superadmin/pinmaster/addpin/sapinrequest.html',
		controller : "sapinrequestController"
	})
	.state('sapinaccept', {
		url : '/sapinaccept',
		templateUrl : 'superadmin/pinmaster/accept/sapinaccept.html',
		controller : "sapinacceptController"
	})
	.state('tadashboard', {
		url : '/tadashboard',
		templateUrl : 'tenantadmin/dashboard/tadashboard.html',
		controller : "tadashboardController"
	})
	.state('tapinrequest', {
		url : '/tapinrequest',
		templateUrl : 'tenantadmin/pinmaster/addpin/tapinrequest.html',
		controller : "tapinrequestController"
	})
}
