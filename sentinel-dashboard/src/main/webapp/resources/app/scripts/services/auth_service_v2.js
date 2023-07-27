var app = angular.module('sentinelDashboardApp');

app.service('AuthServiceV2', ['$http', function ($http) {
  this.check = function () {
    return $http({
      url: 'v2/auth/check',
      method: 'POST'
    });
  };

  this.login = function (param) {
    return $http({
      url: 'v2/auth/login',
      params: param,
      method: 'POST'
    });
  };

  this.logout = function () {
    return $http({
      url: 'v2/auth/logout',
      method: 'POST'
    });
  };
}]);
