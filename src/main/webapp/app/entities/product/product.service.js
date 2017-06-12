(function() {
    'use strict';
    angular
        .module('test51App')
        .factory('Product', Product);

    Product.$inject = ['$resource', 'DateUtils'];

    function Product ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.eta = DateUtils.convertDateTimeFromServer(data.eta);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
