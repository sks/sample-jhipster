(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('MyTestEntity', MyTestEntity);

    MyTestEntity.$inject = ['$resource'];

    function MyTestEntity ($resource) {
        var resourceUrl =  'api/my-test-entities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
