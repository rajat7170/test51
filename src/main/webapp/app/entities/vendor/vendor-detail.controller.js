(function() {
    'use strict';

    angular
        .module('test51App')
        .controller('VendorDetailController', VendorDetailController);

    VendorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vendor'];

    function VendorDetailController($scope, $rootScope, $stateParams, previousState, entity, Vendor) {
        var vm = this;

        vm.vendor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('test51App:vendorUpdate', function(event, result) {
            vm.vendor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
