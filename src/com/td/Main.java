package com.td;

import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String source = "00:01:07,400-234-090\n00:05:01,701-080-080\n00:05:00,400-234-090";
        String[] cases = source.split("\n");
        List<String> phoneNumbers = new ArrayList<>();
        List<List> history = new ArrayList<>();

        for (int i = 0; i < cases.length; i++) {
            String[] eachBill = cases[i].split(",");
            String time = eachBill[0];
            String phoneNumber = eachBill[1];
            phoneNumbers.add(phoneNumber);

            int hour = Integer.valueOf(time.split(":")[0]);
            int minute = Integer.valueOf(time.split(":")[1]);
            int second = Integer.valueOf(time.split(":")[2]);
            int wholeTime = 360*hour + 60*minute + second;
            System.out.println("wholeTime is " + wholeTime);
            List<String> eachHistory = new ArrayList<>();
            eachHistory.add(0, phoneNumber);
            eachHistory.add(1, String.valueOf(wholeTime));
            history.add(eachHistory);
        }

        List<List> newHistory = new ArrayList<>();
        // collect history
        String eachPhoneNumber = null;
        int eachWholeTime = 0;
        List<String> eachOne = null;
        List<String> nextOne = null;
        String nextPhoneNumber = null;
        int nextWholeTime = 0;
        int totalWholeTime = 0;
        boolean insert = false;
        for(int j = 0; j < history.size(); j++) {
            // check if eachOne is empty
            if ( ! history.get(j).isEmpty()) {
                eachOne = history.get(j);
                eachPhoneNumber = eachOne.get(0);
                eachWholeTime = Integer.valueOf(eachOne.get(1));
                for (int k = j; k < history.size(); k++) {
                    if ( k != j ) {
                        // if nextOne is not empty
                        if ( ! history.get(k).isEmpty()) {
                            nextOne = history.get(k);
                            nextPhoneNumber = nextOne.get(0);
                            nextWholeTime = Integer.valueOf(nextOne.get(1));

                            if ( eachPhoneNumber.equals(nextPhoneNumber)) {
                                totalWholeTime = eachWholeTime + nextWholeTime;
                                System.out.println("insert " + eachPhoneNumber);
                                List<String> eachNewHistory = new ArrayList<>();
                                eachNewHistory.add(0, eachPhoneNumber);
                                System.out.println("insert " + totalWholeTime);
                                eachNewHistory.add(1, String.valueOf(totalWholeTime));
                                newHistory.add(eachNewHistory);
                                insert = true;
                                // remove it from previous array
                                int l = k;
                                history.remove(l);
                            }
                        }
                    }
                }
                if ( insert == false) {
                    System.out.println("insert " + eachPhoneNumber);
                    List<String> eachNewHistory = new ArrayList<>();
                    eachNewHistory.add(0, eachPhoneNumber);
                    System.out.println("insert " + eachWholeTime);
                    eachNewHistory.add(1, String.valueOf(eachWholeTime));
                    newHistory.add(eachNewHistory);
                    insert = true;
                }
            }
            insert = false;
        }

        // remove the longest call time
        int maxCallTime = 0;
        for (int a = 0; a < newHistory.size(); a++) {
            String first = String.valueOf(newHistory.get(a).get(1));
            maxCallTime = Integer.valueOf(first);
            for ( int b = a; b < newHistory.size(); b++) {
                String secord = String.valueOf(newHistory.get(b).get(1));
                int secordCallTime = Integer.valueOf(secord);
                if ( maxCallTime > secordCallTime) {
                    newHistory.remove(a);
                } else {
                    newHistory.remove(b);
                }
            }
        }

        double cost = 0;
        for (int a = 0; a < newHistory.size(); a++) {
            String phoneNumber = String.valueOf(newHistory.get(a).get(0));
            String callTime = String.valueOf(newHistory.get(a).get(1));
            // can't remember the price exactly. Missing a condition to calculate different cost based on different call time.
            cost = Math.floor( Integer.valueOf(callTime) / 60) * 180;
            System.out.println("phone number " + phoneNumber + ", call time " + callTime + ", cost " + cost);
        }
    }
}
