var app = angular.module('sentinelDashboardApp');

app.service('MetricServiceV2', ['$http', function ($http) {

  this.queryAppSortedIdentities = function (params) {
    return $http({
      url: '/v2/metric/queryTopResourceMetric.json',
      params: params,
      method: 'GET'
    });
  };

  this.queryByAppAndIdentity = function (params) {
    return $http({
      url: '/v2/metric/queryByAppAndResource.json',
      params: params,
      method: 'GET'
    });
  };

  this.queryByMachineAndIdentity = function (ip, port, identity, startTime, endTime) {
    var param = {
      ip: ip,
      port: port,
      identity: identity,
      startTime: startTime.getTime(),
      endTime: endTime.getTime()
    };

    return $http({
      url: '/v2/metric/queryByAppAndResource.json',
      params: param,
      method: 'GET'
    });
  };
}]);
