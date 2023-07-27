var app = angular.module('sentinelDashboardApp');

app.controller('AppControllerV2', ['$scope', '$stateParams', 'AppServiceV2', 'ngDialog', 'MachineService',
  function ($scope, $stateParams, AppService,
    ngDialog, MachineService) {
    //初始化
    $scope.app = $stateParams.app;
    $scope.rulesPageConfig = {
      pageSize: 10,
      currentPageIndex: 1,
      totalPage: 1,
      totalCount: 0,
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

    getMachineRules();
    function getMachineRules() {
      if (!$scope.macInputModel) {
        return;
      }
      let mac = $scope.macInputModel.split(':');
        AppService.queryMachineRules($scope.app, mac[0], mac[1]).success(
        function (data) {
          if (data.code === 0 && data.data) {
            $scope.rules = data.data;
            $.each($scope.rules, function (idx, rule) {
              if (rule.highestSystemLoad >= 0) {
                rule.grade = 0;
              }
            });
            $scope.rulesPageConfig.totalCount = $scope.rules.length;
          } else {
            $scope.rules = [];
            $scope.rulesPageConfig.totalCount = 0;
          }
        });
    }

    $scope.getMachineRules = getMachineRules;
    var appDialog;
    $scope.editApp = function (rule) {
      $scope.currentRule = angular.copy(rule);
      $scope.appDialog = {
          title: '编辑应用',
          type: 'edit',
          confirmBtnText: '保存',
          showAdvanceButton: rule.controlBehavior == 0 && rule.strategy == 0
      };
      appDialog = ngDialog.open({
        template: '/app/views/dialog/app-dialog.html',
        width: 680,
        overlay: true,
        scope: $scope
      });
    };

    $scope.addNewApp = function () {
      var mac = $scope.macInputModel.split(':');
      $scope.currentRule = {
        app: ''
      };
      $scope.appDialog = {
          title: '新增应用',
          type: 'add',
          confirmBtnText: '新增',
          showAdvanceButton: true,
      };
      appDialog = ngDialog.open({
        template: '/app/views/dialog/app-dialog.html',
        width: 680,
        overlay: true,
        scope: $scope
      });
    };

    $scope.saveApp = function () {
      if ($scope.appDialog.type === 'add') {
        addNewApp($scope.currentRule);
      } else if ($scope.appDialog.type === 'edit') {
        saveApp($scope.currentRule, true);
      }
    };

    var confirmDialog;
    $scope.deleteApp = function (rule) {
      $scope.currentRule = rule;
      $scope.confirmDialog = {
        title: '删除应用',
        type: 'delete_app',
        attentionTitle: '请确认是否删除应用',
        attention: '',
        confirmBtnText: '删除',
      };
      confirmDialog = ngDialog.open({
        template: '/app/views/dialog/confirm-dialog.html',
        scope: $scope,
        overlay: true
      });
    };


    $scope.confirm = function () {
      if ($scope.confirmDialog.type === 'delete_app') {
        deleteApp($scope.currentRule);
      } else {
        console.error('error');
      }
    };

    function deleteApp(rule) {
        AppService.deleteApp(rule).success(function (data) {
        if (data.code === 0) {
          getMachineRules();
          confirmDialog.close();
        } else if (data.msg != null) {
            alert('失败：' + data.msg);
        } else {
            alert('失败：未知错误');
        }
      });
    }

    function addNewApp(rule) {
        AppService.newApp(rule).success(function (data) {
          if (data.code === 0) {
            getMachineRules();
            appDialog.close();
          } else if (data.msg != null) {
            alert('失败：' + data.msg);
          } else {
            alert('失败：未知错误');
          }
      });
    }

    function saveApp(rule, edit) {
        AppService.saveApp(rule).success(function (data) {
        if (data.code === 0) {
          getMachineRules();
          if (edit) {
            appDialog.close();
          } else {
            confirmDialog.close();
          }
        } else if (data.msg != null) {
          alert('失败：' + data.msg);
        } else {
          alert('失败：未知错误');
        }
      });
    }


    queryAppMachines();
    function queryAppMachines() {
      MachineService.getAppMachines($scope.app).success(
        function (data) {
          if (data.code === 0) {
            // $scope.machines = data.data;
            if (data.data) {
              $scope.machines = [];
              $scope.macsInputOptions = [];
              data.data.forEach(function (item) {
                if (item.healthy) {
                  $scope.macsInputOptions.push({
                    text: item.ip + ':' + item.port,
                    value: item.ip + ':' + item.port
                  });
                }
              });
            }
            if ($scope.macsInputOptions.length > 0) {
              $scope.macInputModel = $scope.macsInputOptions[0].value;
            }
          } else {
            $scope.macsInputOptions = [];
          }
        }
      );
    };
    $scope.$watch('macInputModel', function () {
      if ($scope.macInputModel) {
        getMachineRules();
      }
    });
  }]);
