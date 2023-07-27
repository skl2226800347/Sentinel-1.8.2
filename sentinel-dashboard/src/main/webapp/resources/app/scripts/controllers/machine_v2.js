var app = angular.module('sentinelDashboardApp');

app.controller('MachineCtlV2', ['$scope', '$stateParams', 'MachineServiceV2','AppServiceV2',
  function ($scope, $stateParams, MachineService,AppService) {
    $scope.app = $stateParams.app;
    $scope.propertyName = '';
    $scope.reverse = false;
    $scope.currentPage = 1;
    $scope.machines = [];
    $scope.machinesPageConfig = {
      pageSize: 10,
      currentPageIndex: 1,
      totalPage: 1,
      totalCount: 0,
    };

    $scope.sortBy = function (propertyName) {
      // console.log('machine sortBy ' + propertyName);
      $scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
      $scope.propertyName = propertyName;
    };
    
    $scope.reloadMachines = function() {
      if (!$scope.macInputModel) {
        return;
      }
      MachineService.getAppMachines($scope.macInputModel).success(
        function (data) {
          // console.log('get machines: ' + data.data[0].hostname)
          if (data.code == 0 && data.data) {
            $scope.machines = data.data;
            var healthy = 0;
            $scope.machines.forEach(function (item) {
              if (item.healthy) {
                  healthy++;
              }
              if (!item.hostname) {
                item.hostname = '未知'
              }
            })
            $scope.healthyCount = healthy;
            $scope.machinesPageConfig.totalCount = $scope.machines.length;
          } else {
            $scope.machines = [];
            $scope.healthyCount = 0;
          }
        }
      );
    };

    $scope.macsInputConfig = {
      searchField: ['text', 'value'],
      persist: true,
      create: false,
      maxItems: 1,
      render: {
        item: function (data, escape) {
          return '<div>' + escape(data.text) + '</div>';
        }
      },
      onChange: function (value, oldValue) {
        $scope.macInputModel = value;
      }
    };
    
    $scope.removeMachine = function(ip, port) {
      if (!confirm("confirm to remove machine [" + ip + ":" + port + "]?")) {
        return;
      }
      MachineService.removeAppMachine($scope.app, ip, port).success(
        function(data) {
          if (data.code == 0) {
            $scope.reloadMachines();
          } else {
            alert("remove failed");
          }
        }
      );
    };

    $scope.reloadMachines();
    queryApps();
    function queryApps(){
      AppService.getAllAppList().success(
          function (data) {
            if (data.code == 0) {
              if (data.data) {
                $scope.machines = [];
                $scope.macsInputOptions = [];
                data.data.forEach(function (item) {
                  $scope.macsInputOptions.push({
                    text: item.app,
                    value: item.app
                  });
                });
              }
              if ($scope.macsInputOptions.length > 0) {
                $scope.macInputModel = $scope.macsInputOptions[0].value;
              }
            }else{
              $scope.macsInputOptions = [];
            }
          }
      );
    }
    $scope.$watch('macInputModel', function () {
      if ($scope.macInputModel) {
        $scope.reloadMachines();
      }
    });
    
  }]);
