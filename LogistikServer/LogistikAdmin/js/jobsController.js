/**
 * jobsController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 09.12.14 - 17:52
 * @copyright munichDev UG
 */


jobsController = {
    jobs: [],

    init: function () {

    },
    ready: function () {

        //Get Lieferanten From Server
        var getJobsFromServer = function (jobs) {
            if(jobs){
                console.log("JOBS!!");
                console.dir(jobs)
                jobsController.jobs = jobs;
            }
        }
        serverController.job.getAll(getJobsFromServer);
    }
}