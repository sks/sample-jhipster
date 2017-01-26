(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('MyTestEntity', MyTestEntity);

    MyTestEntity.$inject = ['$resource', 'DateUtils'];

    function MyTestEntity ($resource, DateUtils) {
        var resourceUrl =  'api/my-test-entities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                        data.modifedOn = DateUtils.convertDateTimeFromServer(data.modifedOn);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
