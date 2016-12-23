'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('division', {
                parent: 'entity',
                url: '/divisions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Divisions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/division/divisions.html',
                        controller: 'DivisionController'
                    }
                },
                resolve: {
                }
            })
            .state('division.detail', {
                parent: 'entity',
                url: '/division/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Division'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/division/division-detail.html',
                        controller: 'DivisionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Division', function($stateParams, Division) {
                        return Division.get({id : $stateParams.id});
                    }]
                }
            })
            .state('division.new', {
                parent: 'division',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/division/division-dialog.html',
                        controller: 'DivisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    divisionName: null,
                                    comments: null,
                                    isActive: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('division', null, { reload: true });
                    }, function() {
                        $state.go('division');
                    })
                }]
            })
            .state('division.edit', {
                parent: 'division',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/division/division-dialog.html',
                        controller: 'DivisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Division', function(Division) {
                                return Division.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('division', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('division.delete', {
                parent: 'division',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/division/division-delete-dialog.html',
                        controller: 'DivisionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Division', function(Division) {
                                return Division.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('division', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
