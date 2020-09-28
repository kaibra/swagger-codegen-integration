# swagger-codegen-integration

A small example on how to integration-test [swagger-codegen](https://github.com/swagger-api/swagger-codegen) with [testcontainers](https://www.testcontainers.org/).

## Why?
[swagger-codegen](https://github.com/swagger-api/swagger-codegen) itself has a quite inconvenient workflow for integration tests.   
It dependes on (`September 2020`)
* executing bash-scripts to generate sources
* commiting generated sources to the repository
* running a mvn command on the commandline which only verifies some of the generated-srouces

## How does this approach work?
The idea is, that every integration test is written in code and does not involve bash-scripts and multiple separated processes.   
This is possible by making use of [testcontainers](https://www.testcontainers.org/).

* A test extends a BaseIntegrationTestClass
* This class provides a temporary directory, to which the test itself can write sources
* once the test has written all sources to the directory, a test is run which is defined in the abstract class
* this test starts a testcontainer, mounts all required files into the container and compiles / tests the sources from the command line

 
Example: [JavaCodegenITest.java](https://github.com/kaibra/swagger-codegen-integration/blob/master/src/test/java/de/kaibra/swaggercodegenintegration/languages/java/JavaCodegenITest.java)
