angular.module('bank', [
  'templates-app',
  'templates-common',
  'bank.home',
  'bank.register',
  'bank.common.http',
  'bank.login',
  'bank.useraccount',
  'ui.router',
  'ngCookies',
  'bank.logout'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('/home');
    $httpProvider.defaults.withCredentials = true;
  })

  .constant('bankEndpoints', {
    LOGIN: '/r/login/',
    REGISTER: '/r/register/',
    USER: '/r/account/',
    LOGOUT: '/r/logout/',
    OPERATION: '/r/operation/',
    ONLINEUSERS: '/r/onlineusers/'
  })


  .service('userGateway', function (httpRequest, bankEndpoints) {
    return {
      getCurrentUser: function () {
        return httpRequest.get(bankEndpoints.USER);
      },
      getOnlineUsers: function () {
        return httpRequest.get(bankEndpoints.ONLINEUSERS);
      }
    };
  })

  .controller('AppCtrl', function AppCtrl($scope, userGateway, $rootScope,logoutService) {
    var vm = this;

    $scope.$on('$stateChangeSuccess', function (event, toState) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });

    vm.getCurrentUserEmail = function () {
      userGateway.getCurrentUser().then(
        function onSuccess(currentUserEmail) {
          $rootScope.currentUser = currentUserEmail;
        }
      );
    };

    vm.getActiveSessions = function () {
      userGateway.getOnlineUsers().then(
        function onSuccess(onlineUsers) {
          $rootScope.onlineUsers = onlineUsers;
        }
      );
    };

    vm.logout = function () {
      logoutService.logoutUser();
    };
  });

