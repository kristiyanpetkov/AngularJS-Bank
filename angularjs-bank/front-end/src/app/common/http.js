/**
 * Created by kristiqn.l.petkov@gmail.com on 18.08.16.
 */
angular.module('bank.common.http', [])

  .service('httpRequest', function ($http, $q) {

    this.get = function (url, params, data) {
      return this.send('GET', url, data, params);
    };

    this.post = function (url, data) {
      return this.send('POST', url, data);
    };

    this.put = function (url, data) {
      return this.send('PUT', url, data);
    };

    this.del = function (url, params, data) {
      return this.send('DELETE', url, data, params);
    };

    this.send = function (method, url, data, params) {
      var deferred = $q.defer();

      $http({method: method, url: url, data: data, params: params})
        .success(function (data) {
          deferred.resolve(data);
        })

        .error(function (data, status) {

          if (status === 404 && !data) {
            deferred.resolve();
          } else {
            deferred.reject(data);
          }
        });

      return deferred.promise;
    };
  });