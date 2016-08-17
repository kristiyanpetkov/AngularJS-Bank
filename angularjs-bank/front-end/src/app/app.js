angular.module('bank', [
  'templates-app',
  'templates-common',
  'bank.home',
  'bank.register',
  'bank.common.http',
  'bank.login',
  'bank.useraccount',
  'ui.router'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
  })

  .constant('bankEndpoints', {
    LOGIN: '/r/login/',
    REGISTER: '/r/register/',
    USER: '/r/account/'
  })


  .controller('AppCtrl', function AppCtrl($scope) {
    $scope.$on('$stateChangeSuccess', function (event, toState) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });
  });

