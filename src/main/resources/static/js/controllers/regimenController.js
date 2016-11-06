angular.module("Fitness").controller("regimenController", regimen)

regimen.$inject = ['$http', '$state', '$location', "Exercises", "$scope", "$stateParams", "$timeout"]

function regimen($http, $state, $location, Exercises, $scope, $stateParams, $timeout) {

    var ctrl = this;

    angular.element(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $(".jumper").on("click", function (e) {

            e.preventDefault();
            $("body, html").animate({
                scrollTop: $($(this).attr('href')).offset().top
            }, 600);
        });
    });



    ctrl.successful = false;

    ctrl.arms = [];
    ctrl.legs = [];
    ctrl.cardio = [];
    ctrl.chest = [];
    ctrl.shoulders = [];
    ctrl.back = [];
    ctrl.neck = [];
    ctrl.plyometrics = [];
    ctrl.core = [];
    ctrl.distance = "";
    ctrl.weight = "";
    ctrl.sets = "";
    ctrl.reps = "";
    ctrl.time = "";
    ctrl.day = "";
    ctrl.exerciseId = "";
    ctrl.exerciseList = [];
    ctrl.initExerciseList = [];
    ctrl.currentExercise = "";
    ctrl.check = false;
    ctrl.exerciseDisplay = [ctrl.exerciseId, ctrl.currentExercise];

    var path = $location.absUrl();
    var length = ($location.absUrl().length) - ($location.path().length)
    ctrl.url = path.substring(35, length - 1) + "/regimen";
    ctrl.list = [];

    var config = {
        headers: {
            'Content-Type': undefined
        }
    }

    ctrl.getRegimenList = function () {
        $http.get("http://localhost:8080/fitness/home/" + ctrl.url).then(function (res) {
            for (i = 0; i < res.data.success.exercises.length; i++) {
                var reg = new Regimen(res.data.success.exercises[i].distance, res.data.success.exercises[i].weight, res.data.success.exercises[i].sets, res.data.success.exercises[i].reps, res.data.success.exercises[i].time, res.data.success.exercises[i].exercise.id,
                    res.data.success.exercises[i].exercise.exercise_name, res.data.success.exercises[i].day);
                ctrl.exerciseList.push(reg);
                ctrl.initExerciseList.push(reg);

            }
            ctrl.exerciseList.sort(Regimen.compare);
        })
    }

    ctrl.getRegimenList();

    ctrl.type = function (id) {
        for (var i = 0; i < ctrl.arms.length; i++) {
            if (id === ctrl.arms[i].id)
                ctrl.check = false;
        }
        for (var i = 0; i < ctrl.legs.length; i++) {
            if (id === ctrl.legs[i].id)
                ctrl.check = false;
        }
        for (var i = 0; i < ctrl.chest.length; i++) {
            if (id === ctrl.chest[i].id)
                ctrl.check = false;
        }
        for (var i = 0; i < ctrl.shoulders.length; i++) {
            if (id === ctrl.shoulders[i].id)
                ctrl.check = false;
        }
        for (var i = 0; i < ctrl.core.length; i++) {
            if (id === ctrl.core[i].id)
                ctrl.check = false;
        }
        for (var i = 0; i < ctrl.neck.length; i++) {
            if (id === ctrl.neck[i].id)
                ctrl.check = false;
        }
        for (var i = 0; i < ctrl.back.length; i++) {
            if (id === ctrl.back[i].id)
                ctrl.check = false;
        }

        for (var i = 0; i < ctrl.cardio.length; i++) {
            if (id === ctrl.cardio[i].id)
                ctrl.check = true;
        }
        for (var i = 0; i < ctrl.plyometrics.length; i++) {
            if (id === ctrl.plyometrics[i].id)
                ctrl.check = false;
        }
    }

    ctrl.add = function () {
        ctrl.exerciseList.push(new Regimen(ctrl.distance, ctrl.weight, ctrl.sets, ctrl.reps, ctrl.time, ctrl.exerciseId,
            ctrl.currentExercise, ctrl.day));
        console.log(ctrl.exerciseList)
        ctrl.distance = "";
        ctrl.weight = "";
        ctrl.sets = "";
        ctrl.reps = "";
        ctrl.time = "";
        ctrl.currentExercise = "";
        ctrl.day = "";
        ctrl.exerciseId = "";

        ctrl.exerciseList.sort(Regimen.compare);
    };

    ctrl.remove = function (index) {

        ctrl.exerciseList.splice(index, 1);

    };

    ctrl.submitForm = function (evt) {
        evt.stopPropagation();
        var _data = {
            regimens: ctrl.exerciseList
        }

        console.log(ctrl.exerciseList);

        $http.post("http://localhost:8080/fitness/home/" + ctrl.url, _data).then(function (response) {

            ctrl.exerciseList = [];
            ctrl.successful = true;
            console.log(ctrl.successful)
            $timeout(function () {
                $state.go('ViewRegimen')
            }, 1500);
            return response.data
        });

    }

    function Regimen(distance, weight, sets, reps, time, exerciseId, currentExercise, day) {
        this.exerciseId = exerciseId, this.distance = distance, this.weight = weight, this.sets = sets, this.reps = reps, this.time = time,
            this.exerciseId = exerciseId, this.currentExercise = currentExercise, this.day = day;
    };

    Regimen.prototype.ordinal = function () {
        switch (this.day) {
        case "Sunday":
            return 0;
        case "Monday":
            return 1;
        case "Tuesday":
            return 2;
        case "Wednesday":
            return 3;
        case "Thursday":
            return 4;
        case "Friday":
            return 5;
        case "Saturday":
            return 6;
        default:
            return 0;
        }
    }

    Regimen.compare = function (one, another) {
        return one.ordinal() - another.ordinal()
    }


    ctrl.addExercise = function ($event) {
        console.log($event.currentTarget)
        console.log(angular.element($event.currentTarget).data('id'))
        ctrl.exerciseId = angular.element($event.currentTarget).data('id')
        ctrl.currentExercise = angular.element($event.currentTarget).data('name')

        ctrl.type(ctrl.exerciseId);

        $('html, body').animate({
            scrollTop: $("#formJump").offset().top
        }, 2000);

    };

    Exercises.all().then(function (exercises) {
        ctrl.list = exercises;
        for (i = 0; i < exercises.length; i++) {
            if (exercises[i].category === "ARMS") {
                ctrl.arms.push(exercises[i]);
            } else if (exercises[i].category === "CARDIO") {
                ctrl.cardio.push(exercises[i]);
            } else if (exercises[i].category === "LEGS") {
                ctrl.legs.push(exercises[i]);
            } else if (exercises[i].category === "CHEST") {
                ctrl.chest.push(exercises[i]);
            } else if (exercises[i].category === "SHOULDERS") {
                ctrl.shoulders.push(exercises[i]);
            } else if (exercises[i].category === "BACK") {
                ctrl.back.push(exercises[i]);
            } else if (exercises[i].category === "NECK") {
                ctrl.neck.push(exercises[i]);
            } else if (exercises[i].category === "PLYOMETRICS") {
                ctrl.plyometrics.push(exercises[i]);
            } else if (exercises[i].category === "CORE") {
                ctrl.core.push(exercises[i]);
            }
        }
    })

}