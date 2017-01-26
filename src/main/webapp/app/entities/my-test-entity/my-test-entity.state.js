(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-test-entity', {
            parent: 'entity',
            url: '/my-test-entity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.myTestEntity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-test-entity/my-test-entities.html',
                    controller: 'MyTestEntityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('myTestEntity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('my-test-entity-detail', {
            parent: 'entity',
            url: '/my-test-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.myTestEntity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-test-entity/my-test-entity-detail.html',
                    controller: 'MyTestEntityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('myTestEntity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MyTestEntity', function($stateParams, MyTestEntity) {
                    return MyTestEntity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-test-entity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-test-entity-detail.edit', {
            parent: 'my-test-entity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-test-entity/my-test-entity-dialog.html',
                    controller: 'MyTestEntityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyTestEntity', function(MyTestEntity) {
                            return MyTestEntity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-test-entity.new', {
            parent: 'my-test-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-test-entity/my-test-entity-dialog.html',
                    controller: 'MyTestEntityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                age: null,
                                email: null,
                                company: null,
                                country: null,
                                tag: null,
                                createdOn: null,
                                modifedOn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-test-entity', null, { reload: 'my-test-entity' });
                }, function() {
                    $state.go('my-test-entity');
                });
            }]
        })
        .state('my-test-entity.edit', {
            parent: 'my-test-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-test-entity/my-test-entity-dialog.html',
                    controller: 'MyTestEntityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyTestEntity', function(MyTestEntity) {
                            return MyTestEntity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-test-entity', null, { reload: 'my-test-entity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-test-entity.delete', {
            parent: 'my-test-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-test-entity/my-test-entity-delete-dialog.html',
                    controller: 'MyTestEntityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyTestEntity', function(MyTestEntity) {
                            return MyTestEntity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-test-entity', null, { reload: 'my-test-entity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
