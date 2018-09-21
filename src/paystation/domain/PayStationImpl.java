package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    Map<Integer, Integer>  myMap= new HashMap<Integer,Integer>();
    private int insertedSoFar=0;
    private int timeBought=0;
    private int totalBought=0;
    private int twentyFiveCounter=0;
    private int fiveCounter=0;
    private int tenCounter=0;

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: {
                fiveCounter++;
                myMap.put(5, fiveCounter);
                break;
            }
            case 10: {
                tenCounter++;
                myMap.put(10, tenCounter);
                break;
            }
            case 25: {
                twentyFiveCounter++;
                myMap.put(25, twentyFiveCounter);
                break;
            }
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
       return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalBought += insertedSoFar;
        myMap.clear();
        reset();
        return r;
    }

/** Cancel the present transaction. Resets the pay station for a 
    *new transaction. 
    * return A Map defining the coins returned to the user. 
    * The key is the coin type and the associated value is the 
    * number of these coins that are returned. 
    * The Map object is never null even if no coins are returned. 
    * The Map will only contain only keys for coins to be returned. 
    * The Map will be cleared after a cancel or buy. 
    */
    @Override
    public Map<Integer, Integer> cancel() {
       Map<Integer, Integer>  map2= new HashMap<Integer,Integer>();
       map2.putAll(myMap);
       myMap.clear();
       return map2;
    }
    /*public void cancel(){
        reset();
    }*/
    
    private void reset() {
        fiveCounter=twentyFiveCounter=tenCounter= timeBought = insertedSoFar = 0;
        
    }
    @Override
    public int empty(){
        int temp = totalBought;
        totalBought=0;
        reset();
        return temp;
    }
    @Override
    public Map<Integer, Integer> printCurrentMap() {
       return myMap;
    }
    
}
