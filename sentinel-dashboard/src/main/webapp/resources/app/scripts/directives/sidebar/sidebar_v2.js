angular.module('sentinelDashboardApp')
  .directive('sidebar', ['$location', '$stateParams', 'AppService','SystemResourceServiceV2', function () {
    return {
      templateUrl: 'app/scripts/directives/sidebar/sidebar_v2.html',
      restrict: 'E',
      replace: true,
      scope: {
      },
      controller: function ($scope, $stateParams, $location, AppService,SystemResourceServiceV2) {
        $scope.app = $stateParams.app;
        $scope.collapseVar = 0;
        SystemResourceServiceV2.getResources().success(function (data) {
          $scope.resources = data.data;
          $scope.resources = $scope.resources.map(function (item) {
              item.active = true;
              item.isGateway = item.appType === 1 || item.appType === 11 || item.appType === 12;
              item.healthyCount = 0;
              if (item.shown) {
                  return item;
              }
          });
        });
        // app
        /*AppService.getApps().success(
          function (data) {
            if (data.code === 0) {
              let path = $location.path().split('/');
              let initHashApp = path[path.length - 1];
              $scope.apps = data.data;
              $scope.apps = $scope.apps.map(function (item) {
                alert('item.app='+item.app);
                if (item.app === initHashApp) {
                  item.active = true;
                }
                let healthyCount = 0;
                for (let i in item.machines) {
                  if (item.machines[i].healthy) {
                      healthyCount++;
                  }
                }
                item.healthyCount = healthyCount;
                // Handle appType
                item.isGateway = item.appType === 1 || item.appType === 11 || item.appType === 12;
                  alert('item.isGateway='+item.isGateway+'  item.appType:'+item.appType);
                if (item.shown) {
                  return item;
                }
              });
            }
          }
        );*/

        // toggle side bar
        $scope.click = function ($event) {
          let entry = angular.element($event.target).scope().entry;
          entry.active = !entry.active;// toggle this clicked app bar
          $scope.apps.forEach(function (item) { // collapse other app bars
            if (item !== entry) {
              item.active = false;
            }
          });
        };

      }
    };
  }]);
