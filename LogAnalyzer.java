
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for(String line : fr.lines()){
             records.add(WebLogParser.parseEntry(line));
         }
     }
     
     public int countUniqueIPs() {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for(LogEntry le : records) {
             String ipAddr = le.getIpAddress();
             if(!uniqueIPs.contains(ipAddr)) {
                 uniqueIPs.add(ipAddr);
             }
         }
         return uniqueIPs.size();
     }
     
     public void printAllHigherThanNum(int num) {
         ArrayList<LogEntry> higherStatus = new ArrayList<LogEntry>();
         for(LogEntry le : records) {
             int status = le.getStatusCode();
             if(status > num) {
                 higherStatus.add(le);
             }
         }
         
         for(LogEntry le : higherStatus) {
             System.out.println(le);
         }
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
         ArrayList<String> uniqueVisits = new ArrayList<String>();
         for(LogEntry le : records) {
             String currDate = le.getAccessTime().toString();
             if(currDate.contains(someday) && !uniqueVisits.contains(le.getIpAddress())) {
                 uniqueVisits.add(le.getIpAddress());
             }
         }
         return uniqueVisits;
     }
     
     public int countUniqueIPsInRange(int low, int high) {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for(LogEntry le : records) {
             int currStatus = le.getStatusCode();
             if(currStatus >= low && currStatus <= high && !uniqueIPs.contains(le.getIpAddress())) {
                 uniqueIPs.add(le.getIpAddress());
             }
         }
         return uniqueIPs.size();
     }
     
     public HashMap<String,Integer> countVisitsPerIP() {
         HashMap<String,Integer> counts = new HashMap<String,Integer>();
         for(LogEntry le : records) {
             String ip = le.getIpAddress();
             if(!counts.containsKey(ip)) {
                 counts.put(ip, 1);
             } else {
                 counts.put(ip, counts.get(ip) + 1);
             }
         }
         return counts;
     }
     
     public int mostNumberVisitsByIP(HashMap<String,Integer> map) {
         int max = 0;
         for(String ip : map.keySet()) {
             int curr = map.get(ip);
             if(curr > max) {
                 max = curr;
             }
         }
         return max;
     }
     
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> map){
         ArrayList<String> arr = new ArrayList<String>();
         int max = mostNumberVisitsByIP(map);
         for(String ip : map.keySet()) {
             int curr = map.get(ip);
             if(curr == max) {
                 arr.add(ip);
             }
         }
         return arr;
     }
     
     public HashMap<String,ArrayList<String>> iPsForDays() {
         HashMap<String,ArrayList<String>> days = new HashMap<String,ArrayList<String>>();
         for(LogEntry le : records) {
             String currDay = le.getAccessTime().toString().substring(4,10);
             if(!days.containsKey(currDay)) {
                 ArrayList<String> arr = new ArrayList<String>();
                 arr.add(le.getIpAddress());
                 days.put(currDay, arr);
             } else {
                 ArrayList<String> arr = days.get(currDay);
                 arr.add(le.getIpAddress());
                 days.put(currDay, arr);
             }
         }
         return days;
     }
     
     public String dayWithMostIPVisits(HashMap<String,ArrayList<String>> map) {
         String mostVisited = null;
         int max = 0;
         for(String day : map.keySet()) {
             int curr = map.get(day).size();
             if(curr > max) {
                 mostVisited = day;
                 max = curr;
             }
         }
         return mostVisited;
     }
     
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String,ArrayList<String>> map, String day) {
         ArrayList<String> currDay = new ArrayList<String>(map.get(day));
         HashMap<String,Integer> counts = new HashMap<String,Integer>();
         for(String ip : currDay) {
             if(!counts.containsKey(ip)) {
                 counts.put(ip, 1);
             } else {
                 counts.put(ip, counts.get(ip) + 1);
             }
         }
         ArrayList<String> ips = iPsMostVisits(counts);
         return ips;
     }
        
     public void printAll() {
         for(LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     
}
