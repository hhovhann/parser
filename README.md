# Getting Started

### Set up before running application

1. You need to have installed Mysql Server
2. You need to create schema, tables (if schema and tables will not generated in runtime) please check parser\src\main\resources\schema.sql
3. In case if table will not be created you can do that manually parser\src\main\resources\schema.sql
4. You need to have installed in your machine Java 1.8
5. You need to have installed in your machine Maven 3.6.1

#### How to prepare jar file 
mvn package -DskipTests
 

#### How to run jar file from terminal

-- from bash terminal
java -jar target/parser-0.0.1.jar --accessLog="D:\\access.log" --startDate="2017-01-01 13:00:00.000" --duration=hourly --threshold=100

-- from windows terminal
java -jar target\\parser-0.0.1.jar --accessLog="D:\\access.log" --startDate="2017-01-01 13:00:00.000" --duration=hourly --threshold=100

### How the tool works:

    java -jar --accessLog="D:\\access.log" --startDate="2017-01-01 13:00:00.000" --duration=hourly --threshold=100
    The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour) and print them to console AND also load them to another MySQL table with comments on why it's blocked.
	
    java -jar --accessLog="D:\\access.log" --startDate="2017-01-01 13:00:00.000" --duration=hourly --threshold=250
	The tool will find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 to 2017-01-02.13:00:00 (24 hours) and print them to console AND also load them to another MySQL table with comments on why it's blocked.

### Stack of technologies
1. Java version 1.8.0_201
2. SpringBoot version 2.1.5.RELEASE
3. Maven version 3.6.1

### Good to have improved

#### Increasing performance: 
We have more then 100K inserts into access log, which takes long time ether with batch insertion. 
As a solution now I am using batching here but seems not so much difference. Need more time for increasing performance
##### Add Performance tests
##### Add Integration tests
