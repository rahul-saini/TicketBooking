# TicketBooking
Ticket booking homework

Build command:
#mvn clean install

After build go inside the target folder and execute command:
#java -jar ticketbooking-0.0.1-SNAPSHOT.jar
(Java 7 will be required to run the project)

Running from commandline:
#curl http://localhost:9999/movie/getAvailableSeats/1
(It will return number of available seats at level 1)

#curl http://localhost:9999/movie/getAvailableSeats/
(It will return total number of available seats)

#curl -X POST http://localhost:9999/movie/findAndHoldSeatsWithMaxLevel/50/1/a@a.com
(Finds and holds 50 seats with max level 1 with customer email a@a.com and returns seatHoldId)

#curl -X POST http://localhost:9999/movie/findAndHoldSeats/50/1/2/a@a.com
(Finds and holds 50 seats with min level 1 and max level 2 with customer email a@a.com and returns seatHoldId)

#curl -X POST http://localhost:9999/movie/findAndHoldSeatsWithMinLevel/50/1/a@a.com
(Finds and holds 50 seats with min level 1 with customer email a@a.com and returns seatHoldId)

#curl -X PUT http://localhost:9999/movie/reserveSeats/1/a@a.com
(Reserves the seats for the returned seatHoldId)

JUnit test cases are inside class TicketBookingApplicationTests
The Test data is initialized into HSQL in memory database by DataInitilizerUtil.class
A worker thread TicketReleaseWorkerThread.class runs in background which timely checks and releases the expired holded seats.

Properties:
data.clean.thread.period = <seconds> #This property holds the value for when to execute the thread and check for expired seats.
ticket.expired.seconds = <seconds> #This property holds the value for after how much time holded seats will be expired and released.

All these properties are mentioned in properties file:
src/main/resources/application.properties

Some future enhancements:
# Some other production ready database like ORACLE/MySQL can be used.
# Multiple instances of project can be run on different nodes to support high number of user requests.
# Add Spring security or any other security framework to add authentication/authorization support.

Note: Spring boot is used to run/deploy the RestController for the ease of deployment and it's a good to have for Microservices.



