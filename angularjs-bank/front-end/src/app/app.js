angular.module('bank', [
  'templates-app',
  'templates-common',
  'bank.home',
  'bank.register',
  'bank.common.http',
  'bank.login',
  'bank.useraccount',
  'ui.router',
  'ngCookies'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('/home');
    $httpProvider.defaults.withCredentials = true;
  })

  .constant('bankEndpoints', {
    LOGIN: '/r/login/',
    REGISTER: '/r/register/',
    OPERATION: '/r/operation/',
    USER: '/r/account/'
  })

  .service('userGateway', function (httpRequest, bankEndpoints) {
    return {
      getCurrentUser: function () {
        return httpRequest.get(bankEndpoints.USER);
      }
    };
  })

  .controller('AppCtrl', function AppCtrl($scope, userGateway) {
    $scope.$on('$stateChangeSuccess', function (event, toState) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });

    $scope.getCurrentUserEmail = function () {
      userGateway.getCurrentUser().then(
        function onSuccess(currentUserEmail) {
          $scope.currentUser = currentUserEmail;
        }
      );
    };
  });

