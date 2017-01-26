(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('MyTestEntityDeleteController',MyTestEntityDeleteController);

    MyTestEntityDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyTestEntity'];

    function MyTestEntityDeleteController($uibModalInstance, entity, MyTestEntity) {
        var vm = this;

        vm.myTestEntity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyTestEntity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
