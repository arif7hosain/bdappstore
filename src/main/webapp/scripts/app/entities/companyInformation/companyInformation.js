'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('companyInformation', {
                parent: 'entity',
                url: '/companyInformations',
                data: {
                    authorities: [],
                    pageTitle: 'CompanyInformations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/companyInformation/companyInformations.html',
                        controller: 'CompanyInformationController'
                    }
                },
                resolve: {
                }
            })
            .state('companyInformation.detail', {
                parent: 'entity',
                url: '/companyInformation/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'CompanyInformation'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/companyInformation/companyInformation-detail.html',
                        controller: 'CompanyInformationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CompanyInformation', function($stateParams, CompanyInformation) {
                        return CompanyInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('companyInformation.new', {
                parent: 'companyInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/companyInformation/companyInformation-dialog.html',
                        controller: 'CompanyInformationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    companyName: null,
                                    shortName: null,
                                    companyInformation: null,
                                    businessDescription: null,
                                    shortDescription: null,
                                    logo: null,
                                    logoContentType: null,
                                    website: null,
                                    companyType: null,
                                    CreatedDate: null,
                                    updatedDate: null,
                                    createBy: null,
                                    updatedBy: null,
                                    activeStatus: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('companyInformation', null, { reload: true });
                    }, function() {
                        $state.go('companyInformation');
                    })
                }]
            })
            .state('companyInformation.edit', {
                parent: 'companyInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/companyInformation/companyInformation-dialog.html',
                        controller: 'CompanyInformationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CompanyInformation', function(CompanyInformation) {
                                return CompanyInformation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('companyInformation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('companyInformation.delete', {
                parent: 'companyInformation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/companyInformation/companyInformation-delete-dialog.html',
                        controller: 'CompanyInformationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CompanyInformation', function(CompanyInformation) {
                                return CompanyInformation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('companyInformation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
