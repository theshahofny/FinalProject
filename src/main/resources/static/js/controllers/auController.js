console.log("found account controller")
angular.module("Fitness").controller("AccountController", accountpage)

accountpage.$inject = [ '$state', '$http', '$location', '$scope' ]

function accountpage($state, $http, $location, $scope) {
	var path = $location.absUrl();
	var length = ($location.absUrl().length) - ($location.path().length);
	console.log(length);
	var url = path.substring(35, length - 1);
	// console.log(url);
	
	var d = new Date();
	var weekday = new Array(7);
	weekday[0]=  "Sunday";
	weekday[1] = "Monday";
	weekday[2] = "Tuesday";
	weekday[3] = "Wednesday";
	weekday[4] = "Thursday";
	weekday[5] = "Friday";
	weekday[6] = "Saturday";

	$scope.sun, $scope.mon, $scope.tues, $scope.wed, $scope.thurs, $scope.fri, $scope.sat = false;

	$scope.day = weekday[d.getDay()];
	
	$scope.regimen = [];

	$scope.createRegimen = function() {
		$state.go('CreateRegimen');
	}
	
	$scope.containsWorkout = function(day){
		for(let i = 0; i < $scope.regimen.length; i++)
			if($scope.regimen[i].day === day)
				return true;
		return false;
	}
	
	

	return $http.get("http://localhost:8080/fitness/home/" + url + "?dummy=comeon").then(function(res) {
		// console.log("http://localhost:8080/fitness/home/" + url)
		// console.log(res.data.success);
		$scope.username = res.data.success.username;

		return $http.get("http://localhost:8080/fitness/home/" + url + "/regimen").then(function(res) {
			// console.log("http://localhost:8080/fitness/home/" + url +
			// "/regimen")
			// console.log(res.data.success.exercises);

			for (i = 0; i < res.data.success.exercises.length; i++) {
				$scope.regimen.push(res.data.success.exercises[i])
				
				if (res.data.success.exercises[i].day == "Sunday") 
					$scope.sun = true;
				else if (res.data.success.exercises[i].day == "Monday") 
					$scope.mon = true;			
				else if (res.data.success.exercises[i].day == "Tuesday") 
					$scope.tues = true;			
				else if (res.data.success.exercises[i].day == "Wednesday") 
					$scope.wed = true;			
				else if (res.data.success.exercises[i].day == "Thursday") 
					$scope.thurs = true;			
				else if (res.data.success.exercises[i].day == "Friday") 
					$scope.fri = true;			
				else if (res.data.success.exercises[i].day == "Saturday") 
					$scope.sat = true;					
			}

		})
	})

}