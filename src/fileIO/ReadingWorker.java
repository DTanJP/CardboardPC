package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
/**
 * Some simple methods provided for editing files
 * 
 * @author David Tan
 */
public class ReadingWorker {

	/** Constructor **/
	private ReadingWorker() throws UnsupportedOperationException {}

	/**
	 * Reads the html source code of a website
	 */
	public static String[] ReadSiteSrc(URL siteURL) {
		String[] result;
		List<String> temp = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(siteURL.openStream()))){

			//Collect all the source code lines
			while(in.readLine() != null)
				temp.add(in.readLine());

		} catch (IOException e) {
			e.printStackTrace();
		}
		result = new String[temp.size()];
		temp.toArray(result);
		return result;
	}

	/**
	 * Converts a file into a String[]
	 */
	public static String[] FileStringArray(File file){
		String[] result = null;
		List<String> temp = new ArrayList<>();
		String line = "";

		if(file.exists() && file.isFile()) {
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				while ((line = br.readLine()) != null)
					temp.add(line);
			}catch(Exception e){
			}
			result = new String[temp.size()];
			temp.toArray(result);
		}
		return result;
	}

	/**
	 * Find the first line in a file that contains token
	 */
	public static String FindLine(File file, String token) {
		String line = "";
		if(file.exists() && file.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))){
				while ((line = br.readLine()) != null) {
					if(line.contains(token)){
						return line;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Find all the lines in a file that contains token
	 */
	public static String[] FindAllLines(File file, String token) {
		String[] result = null;
		List<String> temp = new ArrayList<>();

		if(file.exists() && file.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))){
				String line = "";

				while ((line = br.readLine()) != null) {
					if(line.contains(token))
						temp.add(line);
				}

			} catch(Exception e) {
				e.printStackTrace();
			}
			result = new String[temp.size()];
			temp.toArray(result);
		}
		return result;
	}

	/** Reads the whole file and return it as an array of bytes **/
	public byte[] FileContent(File file) {
		byte[] result = null;
		try {
			result = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Count the number of lines inside a file
	 */
	public static int FileLineCount(File file){
		int result = 0;
		if(file.exists() && file.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))){

				while (br.readLine() != null)
					result++;

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Searches a directory
	 * Finds a list of files that matches the extension it's looking for
	 * 
	 * @param directory - The directory it's searching in
	 * @param ext - The file extensions it's looking for
	 */
	public static File[] FindFiles(File directory, String ext){
		File[] result;
		List<File> temp = null;

		if(directory.exists() && directory.isDirectory()){
			temp = new ArrayList<>();
			for(File f : directory.listFiles()){
				if(f.getName().endsWith(ext))
					temp.add(f);
			}
			result = new File[temp.size()];
			temp.toArray(result);
			return result;
		}

		return null;
	}
}
