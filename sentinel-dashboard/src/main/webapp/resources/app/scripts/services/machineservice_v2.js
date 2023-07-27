var app = angular.module('sentinelDashboardApp');

app.service('MachineServiceV2', ['$http', '$httpParamSerializerJQLike',
  function ($http, $httpParamSerializerJQLike) {
    this.getAppMachines = function (app) {
      var param={
        app: app
      };
      return $http({
        url: 'v2/app/machines.json',
        params: param,
        method: 'GET'
      });
    };
    this.removeAppMachine = function (app, ip, port) {
      return $http({
        url: 'app/machine/remove.json',
        method: 'POST',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        data: $httpParamSerializerJQLike({
          ip: ip,
          port: port
        })
      });
    };
  }]
);
