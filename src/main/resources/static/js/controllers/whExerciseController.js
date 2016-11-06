console.log("in exercises controller");
angular
   .module("Fitness")
   .controller("whExerciseController", getExercises)
   
   getExercises.$inject = ['$state', 'Exercises']

function getExercises($state, Exercises){
	var ctrl = this;
	crtl.list = [];
	
	console.log($state.params.category);
	Exercises.all().then(function(exercises){
		if(exercises[i].category === $state.params.category){
			list.push(exercises[i]);
		}
	})
	
	console.log(list);
	return list;
}