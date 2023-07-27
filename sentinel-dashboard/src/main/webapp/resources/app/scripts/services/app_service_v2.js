var app = angular.module('sentinelDashboardApp');

app.service('AppServiceV2', ['$http', function ($http) {
  this.queryMachineRules = function (app, ip, port) {
    var param = {
      app: app,
      ip: ip,
      port: port
    };
    return $http({
      url: 'v2/app/briefinfos.json',
      params: param,
      method: 'GET'
    });
  };


    this.getAllAppList = function () {
        return $http({
            url: 'v2/app/getAllAppList',
            method: 'GET'
        });
    };

  this.newApp = function (rule) {
    var param = {
      app: rule.app
    };

    return $http({
      url: '/v2/app/add.json',
      params: param,
      method: 'POST'
    });
  };

  this.saveApp = function (rule) {
    var param = {
      id: rule.id,
      app: rule.app
    };

    return $http({
      url: '/v2/app/update.json',
      params: param,
      method: 'PUT'
    });
  };

  this.deleteApp = function (rule) {
    var param = {
      id: rule.id,
      app: rule.app
    };

    return $http({
      url: '/v2/app/delete.json',
      params: param,
      method: 'DELETE'
    });
  };
}]);
