'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publisher', {
                parent: 'account',
                url: '/publisher/create',
                data: {
                    authorities: [],
                    pageTitle: 'Registration'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/create_publisher.html',
                           controller: 'CreatePublisher'
                       }
                   },
                    resolve: {
                    }
            });
    });
