interview-take-home-test
========================
## First Things First
Hello and welcome! This 'take home' is a way for us to evaluate your skills as a developer. 

Please follow
[these instructions](https://help.github.com/articles/duplicating-a-repository)
for creating a place to work on this test. More explicitly please do not use the built-in GitHub fork functionality. Make your repo private in the github settings, and add users @DevonGleeson
and @DavidWhitlock as contributors, so we can see your results. If you don't have a GitHub account that allows you to create private repositories, and are a recent graduate, or are still in school consider [this](https://education.github.com/discount_requests/new). If none of these are options, please make your commits in a local repository, then email us with a copy of the repository.

### Running
From the command line start the server by running this command:
```bash
./gradlew runServer
```
Once the server us running, in another terminal window, you can start the client with this command and the client will begin accepting user input from the keyboard:
```bash
./gradlew run
```
You can run the unit tests in the system by running this command:
```bash
./gradlew test
```
If you'd prefer to use Intellij IDEA, you could generate the project files using this command line:
```bash
./gradlew idea
```
### The problem
The server accepts arbitrary strings from the client. Currently the strings are just echoed back. I'd like you to add a feature to this system: When the server recieves a string it should return the starting location and length of the longest contigous repeated character. Please add more unit tests for the feature you are writing. 

Example ```aaabbbbbbcccc``` will output ```(3, 6)```.

Bonus points! Make the server multi-threaded.

### Metrics for evaluation
We will be evaluating this project on:
* comprehension (how well you filled out the below questions) 
* unit tests are all passing
* code review of design, comments, maintainability.

### Describe the System
Please edit this README.md to provide a detailed description of what this system does, and how its moving parts work together.

Server description:

/* GENERAL DESCRIPTION */
Takes input from a client and returns a modified version of this input back to the client.
The modified version will be a string containing the location of the character that repeated the most in a row and how many there were.

A SERVER_PORT variable is set to a port value that is available for use.

/* main */
Declares a socketFactory to handle the creation of a serverSocket that is declared using the SERVER_PORT value.
Calls method listenAndRespond with serverSocket as parameter to set up a listener for client responses.
If the serverSocket fails to be created a catch message will be printed.
Finally if the serverSocket is not equal to null the serverSocket will be closed and the server will stop.

/* listenAndRespond */
Creates a Datahandler, PrintWriter, and a BufferedReader to handle all the input and output.
The Datahandler will be implemented from an already established String handle just incase we need to provide another override operation.
The original DataHandler is just a dummy to implement from.
The server will continue to loop until the client is done using the server.
Until then the server will continue to call the method handleClientData to handle the input from the Client.

/* handleClientData */
Take the inputstream from the client and sends it to the handler to modify the data appropriately before returning it to the client.

/* EchoDataHandler */
Take the data passed in from the handleClientData and return a modified version of the data.
The data is modified using the contigous method.

/* contigous */
Takes a string object and iterates through it keeping track of the longest string of the same character.
At the end of iteration it will return a string constructed with the results of the location of this contigous and how long it is.

Client Description:

/* GENERAL DESCRIPTION */
Establishes a connection to an operating server and allows the client to input a string of data.
This data will be return as a modified version. The modified version will be a string containing the location of the character that repeated the most in a row and how many there were.

A SERVER_ADDRESS variable is declared to hold the address of the computer that wishes to establish the connection.

/* main */
Since the Client is in the same directory as the Server it can recognize the variables declared there.
Creates a socket for the client using SocketFactory using the SERVER_PORT from Server.java and SERVER_ADDRESS from previously declared.
Establishes a PrintWriter and BufferedReader for the clients socket to handle input and output.
Calls sendAndReceieve method to handle all the requests by the client to the server and handling the reponse from the server.

/* sendAndRecieve */
Continuely reads input from the clients console until the input is empty or equal to "exit" or "quit".
Takes the input and writes to the stream for the server to read.
Then it outputs the result from reader to the console for the client to see.

### Test Plan
Please edit this README.md to provide a detailed description of additional testing (manual and/or automated) that you would perform for this codebase.

1) Make sure the port number is available for use. Many are restricted for general purpose or specific uses like email.
2) Test if the server deals with multiple clients appropriately
3) Test size of the input to make sure its appropriate for the problem
4) Test the input/output streams to make sure its appropriately passing information and flushing for the next input/output.
