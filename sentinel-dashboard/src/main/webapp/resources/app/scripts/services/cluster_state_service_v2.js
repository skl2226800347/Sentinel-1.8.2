/**
 * Cluster state control service.
 *
 * @author Eric Zhao
 */
angular.module('sentinelDashboardApp').service('ClusterStateServiceV2', ['$http', function ($http) {

    this.fetchClusterUniversalStateSingle = function(app, ip, port) {
        var param = {
            app: app,
            ip: ip,
            port: port
        };
        return $http({
            url: '/v2/cluster/state_single',
            params: param,
            method: 'GET'
        });
    };

    this.fetchClusterUniversalStateOfApp = function(app) {
        return $http({
            url: '/v2/cluster/state/' + app,
            method: 'GET'
        });
    };

    this.fetchClusterServerStateOfApp = function(app) {
        return $http({
            url: '/v2/cluster/server_state/' + app,
            method: 'GET'
        });
    };

    this.fetchClusterClientStateOfApp = function(app) {
        return $http({
            url: '/v2/cluster/client_state/' + app,
            method: 'GET'
        });
    };

    this.modifyClusterConfig = function(config) {
        return $http({
            url: '/v2/cluster/config/modify_single',
            data: config,
            method: 'POST'
        });
    };

    this.applyClusterFullAssignOfApp = function(app, clusterMap) {
        return $http({
            url: '/v2/cluster/assign/all_server/' + app,
            data: clusterMap,
            method: 'POST'
        });
    };

    this.applyClusterSingleServerAssignOfApp = function(app, request) {
        return $http({
            url: '/v2/cluster/assign/single_server/' + app,
            data: request,
            method: 'POST'
        });
    };

    this.applyClusterServerBatchUnbind = function(app, machineSet) {
        return $http({
            url: '/v2/cluster/assign/unbind_server/' + app,
            data: machineSet,
            method: 'POST'
        });
    };
}]);
