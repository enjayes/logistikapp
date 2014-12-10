/**
 * jobsController.
 *
 *
 *
 *
 * @date 09.12.14 - 17:52
 *
 */


jobsController = {
    jobs: [],

    init: function () {

    },
    ready: function () {

        //Get Lieferanten From Server
        var getJobsFromServer = function (jobs) {
            if(jobs){
                console.log("JOBS!!----------");
                console.dir(jobs)
                console.log("----------------");

                jobsController.jobs = jobs;
            }
        }
        serverController.job.getAll(getJobsFromServer);
    }
}