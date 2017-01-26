(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('MyTestEntityDetailController', MyTestEntityDetailController);

    MyTestEntityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyTestEntity'];

    function MyTestEntityDetailController($scope, $rootScope, $stateParams, previousState, entity, MyTestEntity) {
        var vm = this;

        vm.myTestEntity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:myTestEntityUpdate', function(event, result) {
            vm.myTestEntity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
