# Read Optimized Data Store
Vishisth Chaturvedi

-Developed it as a stimulation which works on the 3 mentioned function of the Object Store. 

## Project Structure->
There are 3 classes->
object.java
store.java
objOp.java


#### Implementation Details->

I have made use of ConcurrentHashMap in Java. It is thread safe, but this safety is on the bucket level. This helps with performance as only a bucket is locked at one perticular time, not the complete Map.

As the implementation is Read Optimized we have implemented a Cache. The cache makes use of the LRU scheme.

The program takes an input size(eg 100000), and then simulates the Put, Get and Delete operations.

Defined a Object Class which emulates a person with String name and unique int ID.(This is used for the purpose of satisfying the hashmap key value). Another method could be timestamp.

the Concurrent map uses the int id as key and stores the object itself after it is serialized. The object class implements Serializable. 

objOp provides the interface for the 3 methods


#### Running Instruction->
1)run the make make file-> 
        In the CMD run the command ->make   
        And then run the program java store

2)Use the Dockerfile->
                                    Run the command -> docker build -t store-example .
                                    After the build complets
                                    Run the command -> docker images and look for the name with store-example
                                    Finally run the command -> docker run First three char of the image id        
                            


                                                Avg                 Worst Case
#### Time Complexity->  
                                Put             O(1)                   O(1+logc)(Java uses tree for Collision)
                                Get             O(1)                    O(1)
                                Remove          O(1)                    O(n)

Link->(http://kickjava.com/src/java/util/HashMap.java.htm)






