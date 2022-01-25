package Bob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author urryd
 * Completed 1/20/2021
 * Using JDK 16
 * 
 * This is for comparing CSV files, I am comparing the MAC addresses by uploading them to an Array List and then using the .contain ->
 * function to compare to find where the discrepancies are between the two lists
 * 
 * Potential Problem with fix
 *  1. If your code spits out a new file but it is empty or has no MAC addresses then make sure that your MAC address syntax is uniform or else the code won't work.
 *  	^ Edit Method three to fix this problem, or make sure that the MAC addresses are in the same collumn.
 * Setup
 *  Path Files
 *   1. You want to make sure that the Larger Data Set is in path 1 and the smaller in path
 *   2. For most accurate results run the code twice renaming the first output file
 *   	^ Then you will have the MAC addresses from both data sets that does not have one MAC address in the other
 * 
 * This is a 4 method code
 * 	1. writes .csv column to array list
 * 	2. compares the two array lists, writes to a 3rd array list all of the MAC addresses that are not in both files
 * 	3. unifies the syntax of the MAC address to have dashes between every two characters
 *  4. Spits out .csv file with the 3rd array list containing all of the MAC addresses that are not in both files
 */
public class CSVcompareIP {
	
	public static void main(String[] args) {
		System.out.println("println");
		
		String path1 = "GoodBob.csv";
		//this is the directory path to the first file that you want to compare
		String path2 = "DHCPallow1.csv";
		//this is the directory path to the second file that you want to compare
		String a = "";
		//this is for going to the next line
		int pathCount = 0;
		//this is an extra variable
		ArrayList<String> path1List=new ArrayList<String>();
		ArrayList<String> path2List=new ArrayList<String>();
		ArrayList<String> path3List=new ArrayList<String>();
		//these are the array lists of the MAC addresses
		
		writeToArrayList(path1,a,path1List, pathCount);
		writeToArrayList(path2,a,path2List, pathCount);
		compareArrayLists(path1List, path2List, path3List);
		writeToDocument(path3List);

	}

	//this method writes whatever .csv file you give it, to an array list
	public static void writeToArrayList(String path, String line, ArrayList<String> pathList, int Count) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				
				String MAC = values[1];

				String FixedMAC = removeElement(MAC, Count);
				
				pathList.add(FixedMAC + ",");
			}
			System.out.println("finish while");
			Count++;
			br.close();
		}
		catch (Exception e) {
			System.out.println("hello");
		}
	}
	
	//This compares the Array Lists and then spits what meets the desired comparison filter to Array List 3. List1 is compared to List2.
	public static void compareArrayLists (ArrayList<String> List1, ArrayList<String> List2, ArrayList<String> List3) {
		int i1 = List1.size();
		int i2 = List2.size();
		
		int increment = 0;
		
		for(int i = 0; i < i1; i++) {
			increment = 0;
			for(int o = 0; o < i2; o++) {
				boolean contains = Arrays.asList(List1.get(i)).contains(List2.get(o));
				if (contains == true) {
					increment++;
					//if you want to write to the list what is in both of the files then you turn this on and turn the if statement involving "increment" off
					//List3.add(List2.get(o));
				}
			}
			//If you want to write to the list what isn't in the first file then you leave this open
			if (increment == 0) {
				List3.add(List2.get(i));
			}
		}
	}
	
	//method \/ is to remove the character ":" and change it to "-". That way it is all uniform
	public static String removeElement(String str, int Count) { 
		// Create an empty List of characters
		List<Character> chars = new ArrayList<>();
		  
        // For each character in the String
        // add it to the List
        for (char ch : str.toCharArray()) {    		
        	if (ch == ':') {
        		ch = '-';
        	}
        	chars.add(ch);
        }

        StringBuilder sb = new StringBuilder();
        for (Character ch: chars) {
            sb.append(ch);
        }
                
        String string = sb.toString();
        
        return string;
    }

	//This writes List3 to a .csv file first named temp.txt and then renamed to bob13.csv <- You will need to make sure that there are no files with these names in the directory you are putting these in else the code will not complete.
	private static void writeToDocument(ArrayList<String> path3List) {
		String tempFile = "temp.txt";
		File newFile = new File(tempFile);
		String MAC = "";
		
		try {
			FileWriter fw = new FileWriter(tempFile,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			for (int i = 0; i < path3List.size(); i++) {
				MAC = path3List.get(i);
				pw.println(MAC);
			}
			
			pw.flush();
			pw.close();
			File dump = new File("bob13.csv");
			newFile.renameTo(dump);
			
		}
		catch(Exception e) {
			System.out.println("Error - oof");
			
		}
	}
}
