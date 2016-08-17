angular.module('bank.home', [
  'ui.router'
])

  .config(function config($stateProvider) {
    $stateProvider.state('home', {
      url: '/home?msg',
      views: {
        "main": {
          controller: 'HomeCtrl',
          templateUrl: 'home/home.tpl.html'
        }
      },
      data: {pageTitle: 'Bank Home Page'}
    });
  })


  .controller('HomeCtrl', function HomeController($scope) {
  });

