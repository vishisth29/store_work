import java.util.HashMap;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Scanner;

//The DataStore implementation

/*
The implementation has the store class. We then implement the interfaces and
create a user for the simulation
main function run the array of threads which call the 3 methods

The storage used is ConcurrentHashMap, thread safe and read optimized
We have also made use of the LRU cache, which stores the object it self.


*/

public class store  {

    //Contains the interface definition for the functions
    static objOp temp=new objOp() {


        //Put has a time Complexity of O(1)
        @Override
        public void putObject(String name, int number) {
            object temp = new object(name, number);
            //put the object in the map-> it will serialize as object class is serializable
            map.put((long) number, map.getOrDefault(number, temp));
            System.out.println("Object added");
        }

        @Override
        public void getObject(int number) {
            try {
                if(Cache.getNode(number)!=null){
                    object temp = Cache.getNode(number);
                    System.out.println(temp.name + " " + temp.id);
                }else{
                    Cache.putNode(number,map.get(number));
                    object temp = map.get(number);
                    System.out.println(temp.name + " " + temp.id);}
            } catch (Exception e) {
              //  System.out.println("No Such Object");
            }

        }

        @Override
        public void deleteObject(int number) {
            if (map.containsKey(number)) {
                map.remove(number);
                //check to remove the object from the cache aswell
                Cache.delete(number);
            } else {
                //System.out.println("No Such Object");
            }


        }
    };

    //Used to stimulte a user sending requests
    //Performs all the three operations
    static class user implements Runnable {
        private Integer limit_put = test_case;
        private Integer limit_read = test_case*3;
        private Integer limit_delete = test_case/10;

        //System.out.print(this.Thread.State());
        public void run() {
            int i = 0;

            while (i < limit_put) {
                Random r = new Random();
                int low = 1;
                int high = test_case;
                int result = r.nextInt(high - low) + low;

                temp.putObject("Random_name"+String.valueOf(result), result);

               // System.out.println("Random_name"+String.valueOf(result)+"Added");

                i++;
            }

            i = 0;
            while (i < limit_read) {

                Random r = new Random();
                int low = 1;
                int high = test_case;
                int result = r.nextInt(high - low) + low;

               temp.getObject(result);
                System.out.println(" Got Object");



                i++;
            }

            i = 0;
            while (i < limit_delete) {

                Random r = new Random();
                int low = 1;
                int high = test_case;
                int result = r.nextInt(high - low) + low;

                temp.deleteObject(result);
                System.out.println("Object Deleted");



                i++;
            }


        }


    }

    //Data Storage used is ConcurrentHashMap
    private static ConcurrentHashMap<Long, object> map;
    //LRU Cache object
    private static LRUCache Cache;
    private static int cache_hit=0;
    public static int test_case=0;


    public static void main(String args[]) {

        //intialize the map
        System.out.print("Enter the number of test case->     ");
        Scanner scn=new Scanner(System.in);
        test_case=scn.nextInt();
        map = new ConcurrentHashMap<Long, object>();
        Cache=new LRUCache();

        //create and running threads for simulating
        Runnable r1 = new user();
        Thread[] t_list = new Thread[50];
        for(int i=0; i<50; i++) {
            t_list[i] = new Thread(r1,"Thread  "+i+1);

        }
        for(int i=0; i<50; i++) {
            t_list[i].start();
            System.out.println("New Thread Start->  "+t_list[i].getName());
        }

      //System.out.println("The map size at the end->"+map.size()+"   Number of Cache hits->"+cache_hit);

    }


// LRU Cache Implementation
    static class Node {
        object ob;
        int key;
        Node left;
        Node right;
    }
    public static class LRUCache {

        HashMap<Integer, Node> hashmap;
        Node start, end;
        int LRU_SIZE = test_case/15;

        public LRUCache() {
            hashmap = new HashMap<Integer, Node>();
        }

        public object getNode(int key) {

            if (hashmap.containsKey(key)) {
                cache_hit++;
                //System.out.println("Cache hit");
                Node node = hashmap.get(key);
                removeNode(node);
                addAtTop(node);
                return node.ob;
            }
            //System.out.println("Cache miss");
            return null;
        }

        public void putNode(int key, object ob) {
            if (hashmap.containsKey(key)) {
                Node node = hashmap.get(key);
                node.ob = ob;
                removeNode(node);
                addAtTop(node);
            } else {
                Node newnode = new Node();
                newnode.left = null;
                newnode.right = null;
                newnode.ob = ob;
                newnode.key = key;
                if (hashmap.size() > LRU_SIZE) {
                    hashmap.remove(end.key);
                    removeNode(end);
                    addAtTop(newnode);

                } else {
                    addAtTop(newnode);
                }

                hashmap.put(key, newnode);
            }
        }

        public void addAtTop(Node node) {
            node.right = start;
            node.left = null;
            if (start != null)
                start.left = node;
            start = node;
            if (end == null)
                end = start;
        }

        public void removeNode(Node node) {
            if (node.left != null) {
                node.left.right = node.right;
            } else {
                start = node.right;
            }
            if (node.right != null) {
                node.right.left = node.left;
            } else {
                end = node.left;
            }
        }
        public void delete(int key) {

            if (hashmap.containsKey(key)) {
                Node node = hashmap.get(key);
                removeNode(node);
            }
        }
    }
}

