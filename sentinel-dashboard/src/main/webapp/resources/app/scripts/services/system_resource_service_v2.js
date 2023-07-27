
var app = angular.module('sentinelDashboardApp');

app.service('SystemResourceServiceV2', ['$http', function ($http) {
  this.getResources = function () {
    return $http({
      url: 'v2/system/resource/getResources.json',
      method: 'GET'
    });
  };
}]);
