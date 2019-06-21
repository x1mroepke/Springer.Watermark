Springer.Watermark
==================

Springer Watermark Test App

Run Tests: sbt testOnly

> sbt testOnly
[info] Loading project definition from /home/michael/Springer.Watermark/project
[info] Set current project to Springer.Watermark (in build file:/home/michael/Springer.Watermark/)
[DEBUG] [06/21/2019 20:36:10.348] [pool-4-thread-5] [EventStream(akka://WatermarkActorTest)] logger log1-Logging$DefaultLogger started
[DEBUG] [06/21/2019 20:36:10.352] [pool-4-thread-5] [EventStream(akka://WatermarkActorTest)] Default Loggers started
-----------------------------------------------------
Please wait... I am receiving messages...
-----------------------------------------------------
[DEBUG] [06/21/2019 20:36:11.037] [WatermarkActorTest-akka.actor.default-dispatcher-4] [akka://WatermarkActorTest/user/watermark-documents] mapOfDocuments holds [1] elements
[DEBUG] [06/21/2019 20:36:11.072] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-documents/watermarkingStatus] Set document with title [title-3946386656695534] to ticket status [NONE]
[DEBUG] [06/21/2019 20:36:11.072] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-documents/watermarkingStatus] Set document with title [title-3946386656695534] to ticket status [Processing]
[DEBUG] [06/21/2019 20:36:11.072] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-documents/watermarkingStatus] Set document with title [title-3946386656695534] to ticket status [Finished]
Book(title-3946386656695534,author-3946386656695534,book content-3946386656695534,Ticket(Finished,3946386675622636),Business)
-----------------------------------------------------
Please wait... I am receiving messages...
-----------------------------------------------------
[DEBUG] [06/21/2019 20:36:16.106] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-book-check-topics] mapOfDocuments holds [2] elements
Book(title-3946391785993927,author-3946391785993927,book content-3946391785993927,Ticket(Finished,3946391786253444),Media)
[DEBUG] [06/21/2019 20:36:16.106] [WatermarkActorTest-akka.actor.default-dispatcher-3] [akka://WatermarkActorTest/user/watermark-book-check-topics/watermarkingStatus] Set document with title [title-3946391785993927] to ticket status [NONE]
[DEBUG] [06/21/2019 20:36:16.107] [WatermarkActorTest-akka.actor.default-dispatcher-3] [akka://WatermarkActorTest/user/watermark-book-check-topics/watermarkingStatus] Set document with title [title-3946391785993927] to ticket status [Processing]
[DEBUG] [06/21/2019 20:36:16.107] [WatermarkActorTest-akka.actor.default-dispatcher-3] [akka://WatermarkActorTest/user/watermark-book-check-topics/watermarkingStatus] Set document with title [title-3946391785993927] to ticket status [Finished]
-----------------------------------------------------
Please wait... I am receiving messages...
-----------------------------------------------------
[DEBUG] [06/21/2019 20:36:26.116] [WatermarkActorTest-akka.actor.default-dispatcher-4] [akka://WatermarkActorTest/user/watermark-journal] mapOfDocuments holds [3] elements
Journal(title-3946401793466687,author-3946401793466687,journal content-3946401793466687,Ticket(Finished,3946401796944946))
[DEBUG] [06/21/2019 20:36:26.117] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-journal/watermarkingStatus] Set document with title [title-3946401793466687] to ticket status [NONE]
[DEBUG] [06/21/2019 20:36:26.117] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-journal/watermarkingStatus] Set document with title [title-3946401793466687] to ticket status [Processing]
[DEBUG] [06/21/2019 20:36:26.118] [WatermarkActorTest-akka.actor.default-dispatcher-2] [akka://WatermarkActorTest/user/watermark-journal/watermarkingStatus] Set document with title [title-3946401793466687] to ticket status [Finished]
[DEBUG] [06/21/2019 20:36:31.156] [pool-4-thread-5-ScalaTest-running-WatermarkSpec] [EventStream(akka://WatermarkActorSystem)] logger log1-Logging$DefaultLogger started
[DEBUG] [06/21/2019 20:36:31.156] [pool-4-thread-5-ScalaTest-running-WatermarkSpec] [EventStream(akka://WatermarkActorSystem)] Default Loggers started
-----------------------------------------------------
Please wait... I am receiving messages...
-----------------------------------------------------
[DEBUG] [06/21/2019 20:36:31.160] [WatermarkActorSystem-akka.actor.default-dispatcher-5] [akka://WatermarkActorSystem/user/get-status-by-ticketid] mapOfDocuments holds [4] elements
[DEBUG] [06/21/2019 20:36:31.188] [WatermarkActorSystem-akka.actor.default-dispatcher-4] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleA] to ticket status [NONE]
Journal(titleA,author,content,Ticket(Finished,3946406840082472))
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-4] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleA] to ticket status [Processing]
Book(titleB,author,content,Ticket(Finished,3946406840147207),Science)
Journal(titleC,author,content,Ticket(Finished,3946406840195213))
Book(titleD,author,content,Ticket(Finished,3946406840213015),Media)
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-4] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleA] to ticket status [Finished]
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-5] [akka://WatermarkActorSystem/user/get-status-by-ticketid] mapOfDocuments holds [5] elements
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleB] to ticket status [NONE]
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleB] to ticket status [Processing]
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-5] [akka://WatermarkActorSystem/user/get-status-by-ticketid] mapOfDocuments holds [6] elements
[DEBUG] [06/21/2019 20:36:31.191] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleB] to ticket status [Finished]
[DEBUG] [06/21/2019 20:36:31.192] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleC] to ticket status [NONE]
[DEBUG] [06/21/2019 20:36:31.192] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleC] to ticket status [Processing]
[DEBUG] [06/21/2019 20:36:31.192] [WatermarkActorSystem-akka.actor.default-dispatcher-3] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleC] to ticket status [Finished]
[DEBUG] [06/21/2019 20:36:31.193] [WatermarkActorSystem-akka.actor.default-dispatcher-5] [akka://WatermarkActorSystem/user/get-status-by-ticketid] mapOfDocuments holds [7] elements
[DEBUG] [06/21/2019 20:36:31.193] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleD] to ticket status [NONE]
[DEBUG] [06/21/2019 20:36:31.193] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleD] to ticket status [Processing]
[DEBUG] [06/21/2019 20:36:31.194] [WatermarkActorSystem-akka.actor.default-dispatcher-2] [akka://WatermarkActorSystem/user/get-status-by-ticketid/watermarkingStatus] Set document with title [titleD] to ticket status [Finished]
Found document titleC for ticket id 3946406840195213
[DEBUG] [06/21/2019 20:36:36.269] [WatermarkActorTest-akka.actor.default-dispatcher-5] [EventStream] shutting down: StandardOutLogger started
[info] WatermarkSpec:
[info] WatermarkActor
[info] - should create watermarks for documents
[info] - should create watermarks for books and check topic
[info] - should create watermarks for journals
[info] - should get document and watermark status by ticket id
[info] ScalaTest
[info] Run completed in 27 seconds, 848 milliseconds.
[info] Total number of tests run: 4
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 4, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 4, Failed 0, Errors 0, Passed 4
[success] Total time: 31 s, completed Jun 21, 2019 8:36:36 PM
michael@michael-UBU18 ~/Springer.Watermark (master)-local-dev



