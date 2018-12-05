var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http) {
    $http.get("../json/table1.json")
        .then(function (result) {
            $scope.names = result.data.records;
        });
});