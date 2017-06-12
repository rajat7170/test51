(function() {
    'use strict';

    angular
        .module('test51App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vendor', {
            parent: 'entity',
            url: '/vendor?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test51App.vendor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vendor/vendors.html',
                    controller: 'VendorController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vendor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('vendor-detail', {
            parent: 'vendor',
            url: '/vendor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test51App.vendor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vendor/vendor-detail.html',
                    controller: 'VendorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vendor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Vendor', function($stateParams, Vendor) {
                    return Vendor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vendor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vendor-detail.edit', {
            parent: 'vendor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendor.new', {
            parent: 'vendor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                companyName: null,
                                address: null,
                                city: null,
                                state: null,
                                pincode: null,
                                mobile: null,
                                bioDlNo: null,
                                nonBioDlNo: null,
                                estb: null,
                                certification: null,
                                webSite: null,
                                aboutCompany: null,
                                email: null,
                                category: null,
                                tin: null,
                                alternateMobile: null,
                                commentId: null,
                                ratings: null,
                                linkedWord: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: 'vendor' });
                }, function() {
                    $state.go('vendor');
                });
            }]
        })
        .state('vendor.edit', {
            parent: 'vendor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: 'vendor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendor.delete', {
            parent: 'vendor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendor/vendor-delete-dialog.html',
                    controller: 'VendorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: 'vendor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
