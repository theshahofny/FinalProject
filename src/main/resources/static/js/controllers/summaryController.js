console.log("found summary controller")
angular.module("Fitness").controller("SummaryController", summarypage)

accountpage.$inject = [ '$state', '$http', '$location', '$scope' ]

function summarypage($state, $http, $location, $scope) {
	var path = $location.absUrl();
	var length = ($location.absUrl().length) - ($location.path().length);
	console.log(length);
	var url = path.substring(35, length - 1);
	// console.log(url);

	$scope.regimen = []
	
	$scope.linkToUpdateHistory = function() {
		$state.go('WorkoutHistory');
	}

	function getDayOfWeek() {
		var d = new Date();
		var weekday = new Array(7);
		weekday[0] = "Sunday";
		weekday[1] = "Monday";
		weekday[2] = "Tuesday";
		weekday[3] = "Wednesday";
		weekday[4] = "Thursday";
		weekday[5] = "Friday";
		weekday[6] = "Saturday";

		return weekday[d.getDay()];
	}

	function Regimen(distance, weight, sets, reps, time, exerciseId, currentExercise, day) {
		this.exerciseId = exerciseId, this.distance = distance, this.weight = weight, this.sets = sets, this.reps = reps, this.time = time,
				this.exerciseId = exerciseId, this.currentExercise = currentExercise, this.day = day;
	}

	$http.get("http://localhost:8080/fitness/home/" + url + "/regimen").then(
			function(res) {
				$scope.regimen = [];
				for (i = 0; i < res.data.success.exercises.length; i++) {
					if (res.data.success.exercises[i].day === getDayOfWeek()) {
						$scope.regimen.push(new Regimen(res.data.success.exercises[i].distance, res.data.success.exercises[i].weight,
								res.data.success.exercises[i].sets, res.data.success.exercises[i].reps, res.data.success.exercises[i].time,
								res.data.success.exercises[i].exercise.id, res.data.success.exercises[i].exercise.exercise_name,
								res.data.success.exercises[i].day));
					}
				}
				console.log($scope.regimen)

			})

	$http.get("http://localhost:8080/fitness/home/" + url + "?dummy=comeon").then(function(res) {
		// console.log("http://localhost:8080/fitness/home/" + url)
		// console.log(res.data.success);
		$scope.username = res.data.success.username;
		$scope.distance = res.data.stats.distance;
		$scope.time = res.data.stats.time;
		$scope.weight = res.data.stats.weight;

	})

}