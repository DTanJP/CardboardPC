package fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Simple methods provided for reading files
 * 
 * @author David Tan
 */
public class WritingWorker {

	/** Constructor **/
	private WritingWorker() throws UnsupportedOperationException {}

	/** Creates an empty file: If it already exists, don't overwrite it **/
	public static void CreateFile(String file) {
		try (BufferedWriter worker = new BufferedWriter(new FileWriter(file, true))){
		} catch (IOException ioexception) {
		}
	}

	/**
	 * overwriteFile
	 * 
	 * @param file - The file that is being created/written in
	 * @param line - The line of String being written inside the file
	 * 
	 * Overwrites the file instead of adding onto it.
	 * Writes 1 line.
	 */
	public static void OverwriteFile(File file, String line) {
		try (BufferedWriter worker = new BufferedWriter(new FileWriter(file, false))){
			worker.write(line);
			worker.newLine();
		} catch (IOException ioexception) {
		}
	}

	/**
	 * overwriteFile
	 * 
	 * @param file - The file that is being created/written in
	 * @param line - The line of String being written inside the file
	 * 
	 * Overwrites the file instead of adding onto it.
	 * Writes multiple lines.
	 */
	public static void OverwriteFile(File file, String... line) {
		try (BufferedWriter worker = new BufferedWriter(new FileWriter(file, false))){
			for(String s : line){
				worker.write(s);
				worker.newLine();
			}
		} catch (IOException ioexception) {
		}
	}

	/**
	 * writeFile
	 * 
	 * @param file - The file that is being created/written in
	 * @param line - The line of String being written inside the file
	 * 
	 * Writes a single line to the file. Does NOT clears the file. It adds onto the file.
	 * Does NOT start a new line
	 */
	public static void WriteFile(File file, String line) {
		try (BufferedWriter Jwrite = new BufferedWriter(new FileWriter(file, true))){
			Jwrite.write(line);
		} catch (IOException ioexception) {
		}
	}

	/**
	 * writelnFile
	 * 
	 * @param file - The file that is being created/written in
	 * @param line - The line of String being written inside the file
	 * 
	 * Writes a single line to the file. Does NOT clears the file. It adds onto the file.
	 * Start a new line afterwards
	 */
	public static void WritelnFile(File file, String line) {
		try (BufferedWriter Jwrite = new BufferedWriter(new FileWriter(file, true))){
			Jwrite.write(line);
			Jwrite.newLine();
		} catch (IOException ioexception) {
		}
	}
	
	/**
	 * writeFile
	 * 
	 * @param file - The file that is being created/written in
	 * @param line - The lines of String being written inside the file
	 * 
	 * Writes multiple lines to the file. Does NOT clears the file. It adds onto the file.
	 */
	public static void WriteFile(File file, String... line) {
		try (BufferedWriter worker = new BufferedWriter(new FileWriter(file, true))){
			for(int i=0; i<line.length; i++){
				if(line[i] != null) {
					worker.write(line[i]);
					worker.newLine();
				}
			}
		} catch (IOException ioexception) {
		}
	}
	
	/**
	 * copyFile
	 * 
	 * @param src - The file that is being copied
	 * @param dst - The file that is being created/overwritten
	 * 
	 * Copies a file and creates/overwrites a file and fill that with the content of the src file.
	 */
	public static void CopyFile(File src, File dst) {
		OverwriteFile(dst, ReadingWorker.FileStringArray(src));
	}

	/**
	 * Clears out a file of its contents
	 * 
	 * @param file the file to be cleared
	 */
	public static void ClearFile(File file) {
		if(file.exists() && !file.isDirectory()) {
			try (BufferedWriter worker = new BufferedWriter(new FileWriter(file, false))){
			} catch (IOException ioexception) {
			}
		}
	}
}
