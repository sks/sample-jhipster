(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('MyTestEntityDialogController', MyTestEntityDialogController);

    MyTestEntityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyTestEntity'];

    function MyTestEntityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyTestEntity) {
        var vm = this;

        vm.myTestEntity = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myTestEntity.id !== null) {
                MyTestEntity.update(vm.myTestEntity, onSaveSuccess, onSaveError);
            } else {
                MyTestEntity.save(vm.myTestEntity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:myTestEntityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.modifedOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
