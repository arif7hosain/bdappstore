'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tempCompany', {
                parent: 'entity',
                url: '/temp_company',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TempCompanys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempCompany/tempCompanys.html',
                        controller: 'TempCompanyController'
                    }
                },
                resolve: {
                }
            })
            .state('tempCompany.detail', {
                parent: 'entity',
                url: '/tempCompany/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TempCompany'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempCompany/tempCompany-detail.html',
                        controller: 'TempCompanyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TempCompany', function($stateParams, TempCompany) {
                        return TempCompany.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tempCompany.new', {
                parent: 'tempCompany',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tempCompany/tempCompany-dialog.html',
                        controller: 'TempCompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    username: null,
                                    email: null,
                                    password: null,
                                    branchName: null,
                                    BranchType: null,
                                    companyName: null,
                                    shortName: null,
                                    companyInformation: null,
                                    businessDescription: null,
                                    facebook: null,
                                    googlePlus: null,
                                    youtube: null,
                                    linkedin: null,
                                    twitter: null,
                                    website: null,
                                    city: null,
                                    shortDescription: null,
                                    logo: null,
                                    logoContentType: null,
                                    companyType: null,
                                    addressType: null,
                                    officePhone: null,
                                    contactNumber: null,
                                    postalCode: null,
                                    house: null,
                                    RoadNo: null,
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
                        $state.go('tempCompany', null, { reload: true });
                    }, function() {
                        $state.go('tempCompany');
                    })
                }]
            })
            .state('tempCompany.edit', {
                parent: 'tempCompany',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tempCompany/tempCompany-dialog.html',
                        controller: 'TempCompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TempCompany', function(TempCompany) {
                                return TempCompany.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tempCompany', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tempCompany.delete', {
                parent: 'tempCompany',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tempCompany/tempCompany-delete-dialog.html',
                        controller: 'TempCompanyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TempCompany', function(TempCompany) {
                                return TempCompany.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tempCompany', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
