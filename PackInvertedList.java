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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PackInvertedList {
	// Список усіх шляхів до файлів для обробки
	static ArrayList<String> ls = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		listFilesForFolder(new File("D:/Inverted list"));
		HashMap<String, byte[]> invertedList = new HashMap<String, byte[]>();
		int log = 0;
		int percentage = 1;
		for (int i = 0; i < ls.size(); i++) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(ls.get(i)), "UTF-8"));
			String s;
			String myList = "D:/Practice06-02/InvertedList.txt";
			FileWriter writer = new FileWriter(myList, true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			while ((s = in.readLine()) != null) {
				int firstSpace = s.indexOf(" ");
				if(firstSpace !=0){
				String[] line;
				line = s.split(" ");
				Integer[] gaps = new Integer[line.length - 1];
				gaps[0] = Integer.valueOf(line[1]);
				for (int j = 1; j < gaps.length; j++) {
					gaps[j] = Integer.valueOf(line[j + 1]) - gaps[j - 1];
				}
				byte[] vbc = VBEncodeNumbers(gaps);
				invertedList.put(line[0], vbc);
				StringBuffer posts = new StringBuffer();
				for(int k = 0; k < vbc.length; k ++){
					posts.append(vbc[k]);
				}
				bufferWriter.write(posts +"\n");
				log++;
				if(log%42000==0){
					long time =  System.currentTimeMillis()-startTime;
					//startTime = time;
					System.out.println("Completed "+(percentage++)+"% " + time + "ms");
				}
				}
			}
			bufferWriter.close();
			in.close();

		}

	}

	private static byte[] VBEncodeNumber(Integer n) {
		if (n == 0) {
			return new byte[] { 0 };
		}
		int i = (int) (Math.log(n) / Math.log(128)) + 1;
		byte[] rv = new byte[i];
		int j = i - 1;
		do {
			rv[j--] = (byte) (n % 128);
			n /= 128;
		} while (j >= 0);
		rv[i - 1] += 128;
		return rv;
	}
	private static List<Integer> decode(byte[] byteStream) {
        List<Integer> numbers = new ArrayList<Integer>();
        int n = 0;
        for (byte b : byteStream) {
            if ((b & 0xff) < 128) {
                n = 128 * n + b;
            } else {
                int num = (128 * n + ((b - 128) & 0xff));
                numbers.add(num);
                n = 0;
            }
        }
        return numbers;
    }

	private static byte[] VBEncodeNumbers(Integer[] numbers) {
		byte[] byteStream = new byte[0];
		for (int i = 0; i < numbers.length; i++) {
			byte[] bytes = VBEncodeNumber(numbers[i]);
			int n = bytes.length;
			byteStream = mergeByteArrays(bytes, byteStream);
		}
		return byteStream;
	}

	private static byte[] mergeByteArrays(byte[] bytes, byte[] byteStream) {
		byte[] newByteArray = new byte[byteStream.length + bytes.length];
		for (int i = 0; i < byteStream.length; i++) {
			newByteArray[i] = byteStream[i];
		}
		for (int i = 0, j = byteStream.length; i < bytes.length; i++, j++) {
			newByteArray[j] = bytes[i];
		}

		return newByteArray;
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
