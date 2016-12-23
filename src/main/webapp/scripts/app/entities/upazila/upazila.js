'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('upazila', {
                parent: 'entity',
                url: '/upazilas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Upazilas'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/upazila/upazilas.html',
                        controller: 'UpazilaController'
                    }
                },
                resolve: {
                }
            })
            .state('upazila.detail', {
                parent: 'entity',
                url: '/upazila/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Upazila'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/upazila/upazila-detail.html',
                        controller: 'UpazilaDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Upazila', function($stateParams, Upazila) {
                        return Upazila.get({id : $stateParams.id});
                    }]
                }
            })
            .state('upazila.new', {
                parent: 'upazila',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/upazila/upazila-dialog.html',
                        controller: 'UpazilaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    upazilaName: null,
                                    comments: null,
                                    isActive: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('upazila', null, { reload: true });
                    }, function() {
                        $state.go('upazila');
                    })
                }]
            })
            .state('upazila.edit', {
                parent: 'upazila',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/upazila/upazila-dialog.html',
                        controller: 'UpazilaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Upazila', function(Upazila) {
                                return Upazila.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('upazila', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('upazila.delete', {
                parent: 'upazila',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/upazila/upazila-delete-dialog.html',
                        controller: 'UpazilaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Upazila', function(Upazila) {
                                return Upazila.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('upazila', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
