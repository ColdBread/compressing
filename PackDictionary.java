import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PackDictionary {
	static String dictionary = "D:/Practice06-01/Dictionary.txt"; // dictionary
																	// to pack
	

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dictionary), "UTF-8"));
		ArrayList<Pair> dict = new ArrayList<Pair>();
		StringBuffer dictionaryLine = new StringBuffer();

		//String dictionaryLine = "";
		String s;
		String myDictionary = "D:/Practice06-01/PackedDictionary.txt";
		FileWriter writer = new FileWriter(myDictionary, true);
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		int currentPos = 0;
		int blockPos = 0;
		ArrayList<String> block = new ArrayList<String>();
		int log = 0;
		int percentage = 1;
		while ((s = in.readLine()) != null) {
			if (block.size() < 3) {
				block.add(s);
				log++;

			} else {
				log++;
				block.add(s);
				String prefix = getMaxPrefix(block);
				dictionaryLine.append(String.valueOf(block.get(0).length()));
				dictionaryLine.append(prefix);
				dictionaryLine.append("*");
				dictionaryLine.append(block.get(0).substring(prefix.length(), block.get(0).length()));
				blockPos += prefix.length() + 1 + String.valueOf(block.get(0).length()).length()
						+ (block.get(0).length() - prefix.length());
				for (int i = 1; i < block.size(); i++) {
					dictionaryLine.append(String.valueOf(block.get(i).length() - prefix.length()));
					dictionaryLine.append(block.get(i).substring(prefix.length(), block.get(i).length())) ;

					blockPos += String.valueOf(block.get(i).length() - prefix.length()).length()
						+ (block.get(i).length() - prefix.length());
				}
				dict.add(new Pair(currentPos,blockPos));
				currentPos = blockPos;
				if(log%42000==0){
					long time =  System.currentTimeMillis()-startTime;
					//startTime = time;
					System.out.println("Completed "+(percentage++)+"% " + time + "ms");
				}
				block = new ArrayList<String>();

			}

		}
		String output = dictionaryLine.toString();
		bufferWriter.write(output);
		bufferWriter.close();
		in.close();

	}

	private static String getMaxPrefix(ArrayList<String> block) {
		String maxPrefix = block.get(0); // use
		String secondWord = block.get(1); // used
		String thirdWord = block.get(2); // usefull
		String fourthWord = block.get(3); // ye
		int endIndex = maxPrefix.length();
		while (endIndex > -1) {
			if (secondWord.startsWith(maxPrefix)) {
				break;
			} else {
				endIndex -= 1;
				if (endIndex < 1)
					return maxPrefix = "";
				maxPrefix = maxPrefix.substring(0, endIndex);
			}
		}
		while (endIndex > -1) {
			if (thirdWord.startsWith(maxPrefix)) {
				break;
			} else {
				endIndex -= 1;
				if (endIndex < 1)
					return maxPrefix = "";
				maxPrefix = maxPrefix.substring(0, endIndex);
			}
		}
		while (endIndex > -1) {
			if (fourthWord.startsWith(maxPrefix)) {
				break;
			} else {
				endIndex -= 1;
				if (endIndex < 1)
					return maxPrefix = "";
				maxPrefix = maxPrefix.substring(0, endIndex);
			}
		}
		return maxPrefix;
	}

}
class Pair{
	int start;
	int end;
	Pair(int s,int e){
		this.start = s;
		this.end = e;
	}
}
