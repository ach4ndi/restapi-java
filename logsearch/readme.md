## Test 1 - Log Search

How to compile:

    javac .\logsearch.java -cp ./lib/gson-2.10.1.jar


How to run (without any input argument):

    java -cp lib/gson-2.10.1.jar logsearch.java

    Result :
    
    {
      "Google": 2,
      "Mozilla": 40,
      "Safari": 25
    }

How to run (with input argument):

    java -cp lib/gson-2.10.1.jar logsearch.java Google

    Result :
    
    {
      "Google": 2
    }