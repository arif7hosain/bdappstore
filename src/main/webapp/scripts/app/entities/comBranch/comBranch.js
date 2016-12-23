'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('comBranch', {
                parent: 'entity',
                url: '/comBranchs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ComBranchs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comBranch/comBranchs.html',
                        controller: 'ComBranchController'
                    }
                },
                resolve: {
                }
            })
            .state('comBranch.detail', {
                parent: 'entity',
                url: '/comBranch/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ComBranch'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comBranch/comBranch-detail.html',
                        controller: 'ComBranchDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ComBranch', function($stateParams, ComBranch) {
                        return ComBranch.get({id : $stateParams.id});
                    }]
                }
            })
            .state('comBranch.new', {
                parent: 'comBranch',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/comBranch/comBranch-dialog.html',
                        controller: 'ComBranchDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    branchName: null,
                                    branchDescription: null,
                                    branchType: null,
                                    facebook: null,
                                    googlePlus: null,
                                    youtube: null,
                                    linkedin: null,
                                    twitter: null,
                                    website: null,
                                    city: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createBy: null,
                                    updatedBy: null,
                                    isActive: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('comBranch', null, { reload: true });
                    }, function() {
                        $state.go('comBranch');
                    })
                }]
            })
            .state('comBranch.edit', {
                parent: 'comBranch',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/comBranch/comBranch-dialog.html',
                        controller: 'ComBranchDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ComBranch', function(ComBranch) {
                                return ComBranch.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('comBranch', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('comBranch.delete', {
                parent: 'comBranch',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/comBranch/comBranch-delete-dialog.html',
                        controller: 'ComBranchDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ComBranch', function(ComBranch) {
                                return ComBranch.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('comBranch', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
