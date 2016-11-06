angular
    .module("Fitness")
    .controller("HistoryController", history_controller)

history_controller.$inject = ['$state', '$http', '$location', '$scope']

function history_controller($state, $http, $location, $scope) {

    var path = $location.absUrl();
    var length = ($location.absUrl().length) - ($location.path().length)
    var url = path.substring(0, length - 1) + "/history";
    $scope.list = [];
    
    return $http.get(url).then(function (response) {
    	
    	console.log(response.data.success[0].exercise.category);
    	console.log(response.data.success[0].workout_date);
    	
    	for(i = 0 ; i<response.data.success.length; i++){
    		$scope.list.push(response.data.success[i]);
    	}
    	
    	console.log($scope.list);
     
    })
}