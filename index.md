# Sample Akka Streaming app

A simple pair of applications (client and server) that demonstrate how to set up a stream processing server for a client, written in Akka HTTP

Most of the code is very trivial, grabbed from examples. The only exception is the processing of the CSV on the server side. There is built in support for building nicer parsers than the one I wrote, but I wanted to give a clearer view of what it can do. 

The project is a toy, but a nice sample of how you can do basic streaming.

## Server Akka Streams logic

The akka streams code flattens the byte string into a stream of chars, groups them by new line (simulating - albeit badly - a csv parser), takes and aggregates them into a string. It then splits on `','` to indicate column seperation. We then translate that into a `Record`, and `println` the result

## To run

Its easiest to run using sbt, running the service with `sbt server/run` and then the client with `sbt client/run`. The client reads the `test.csv` file from disk, sends it over the wire to the server running on port 8080, which then converts it into a `Record` class, and then prints it to console.