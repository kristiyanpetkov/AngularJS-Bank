/**
 ** Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 15.08.16.
 */
angular.module('bank.register', [
  'ui.router',
  'bank.common.http'
])

  .config(function config($stateProvider) {
    $stateProvider.state('register', {
      url: '/register',
      views: {
        "main": {
          controller: 'RegisterCtrl',
          controllerAs: 'vm',
          templateUrl: 'register/register.tpl.html'
        }
      },
      data: {pageTitle: 'REGISTER'}
    });
  })

  .service('registerGateway', function (httpRequest, bankEndpoints) {
    return {
      registerUser: function (user) {
        return httpRequest.post(bankEndpoints.REGISTER, user);
      }
    };
  })

  .controller('RegisterCtrl', function RegisterCtrl(httpRequest, $http, $state, registerGateway) {
    var vm = this;

    vm.register = function (user) {
      vm.successMessage='';
      vm.errorMsg='';
      registerGateway.registerUser(user).then(
        function onSuccess(successData) {
            vm.successMessage = 'Registration successful!';
        }, function onError(errorData) {
          if (errorData.errorCode == 400) {
            vm.errorMsg = 'Input data is not in a valid format! Username and password should be between 6-16 characters and can contain only letters and digits.';
          }
          if (errorData.errorCode == 403) {
            vm.errorMsg = 'User with such an email already exist!';
          }
        });
    };
  });