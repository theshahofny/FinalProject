console.log("in exercises controller");
angular
    .module("Fitness")
    .controller("exerciseController", exercise)

exercise.$inject = ['Exercises']

function exercise(Exercises) {
    console.log("here");
    var ctrl = this;


    angular.element(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $(".jumper").on("click", function (e) {

        });

    });


    ctrl.detlOpen = false;
    ctrl.arms = [];
    ctrl.legs = [];
    ctrl.cardio = [];
    ctrl.chest = [];
    ctrl.shoulders = [];
    ctrl.back = [];
    ctrl.neck = [];
    ctrl.plyometrics = [];
    ctrl.core = [];

    ctrl.p2 = false;
    ctrl.display = false;

    ctrl.muscle = "";
    ctrl.name = "";
    ctrl.instruction = "";

    ctrl.detail = function (evt) {
        ctrl.detlOpen = true;
        ctrl.name = angular.element(evt.currentTarget).data('name');
        ctrl.image = angular.element(evt.currentTarget).data('gif')
        ctrl.muscle = angular.element(evt.currentTarget).data('muscle')
        ctrl.instruction = angular.element(evt.currentTarget).data('instructions')
        console.log(ctrl.name, ctrl.image, ctrl.muscle, ctrl.instruction)
        ctrl.display = true;


    }

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

    ctrl.closePopUpp = function () {
        ctrl.p2 = false;
        ctrl.detlOpen = false;
    }
}