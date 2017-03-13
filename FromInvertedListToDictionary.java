import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class FromInvertedListToDictionary {
	
	// Список усіх шляхів до файлів для обробки
		static ArrayList<String> ls = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		listFilesForFolder(new File("D:/Inverted list"));
		for(int i = 0; i < ls.size(); i++){
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(ls.get(i)), "UTF-8"));
			String s;
			String myDictionary = "D:/Practice06-01/Dictionary.txt";
			FileWriter writer = new FileWriter(myDictionary, true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			while((s = in.readLine()) != null){
				int firstSpace = s.indexOf(" ");
				if(firstSpace !=0){
					
				String word = s.substring(0, firstSpace);
				bufferWriter.write(word+"\n");
				}
				
			}
			bufferWriter.close();
			in.close();
			
			
			
			
		}

	}
	
	private static void listFilesForFolder(final File folder) {

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				ls.add(fileEntry.getAbsolutePath());
			}
		}

	}

}
