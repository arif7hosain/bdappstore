'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('country', {
                parent: 'entity',
                url: '/countrys',
                data: {
                    authorities: [],
                    pageTitle: 'Countrys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/countrys.html',
                        controller: 'CountryController'
                    }
                },
                resolve: {
                }
            })
            .state('country.detail', {
                parent: 'entity',
                url: '/country/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Country'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/country-detail.html',
                        controller: 'CountryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Country', function($stateParams, Country) {
                        return Country.get({id : $stateParams.id});
                    }]
                }
            })
            .state('country.new', {
                parent: 'country',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/country/country-dialog.html',
                        controller: 'CountryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    countryName: null,
                                    capital: null,
                                    comments: null,
                                    isActive: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('country', null, { reload: true });
                    }, function() {
                        $state.go('country');
                    })
                }]
            })
            .state('country.edit', {
                parent: 'country',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/country/country-dialog.html',
                        controller: 'CountryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Country', function(Country) {
                                return Country.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('country', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('country.delete', {
                parent: 'country',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/country/country-delete-dialog.html',
                        controller: 'CountryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Country', function(Country) {
                                return Country.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('country', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
