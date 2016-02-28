package se.klartbra.dupo.control.filehandling;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A collection of file operations needed by other classes.
 * 
 * @author svante
 *
 */
public class FileOperations {
	private static Logger log = LogManager.getLogger(FileOperations.class);

	private FileOperations() {}

	/**
	 * Get all sub-directories - recursively.
	 * 
	 * @param directories A list of directories too look in.
	 * @return A list of all sub-directories found recursively.
	 */
	public static List<File> getSubDirectoriesRecursively(List<File> directories) {
		List<File> subDirectories = new ArrayList<>();
		for(File dir: directories) {
			subDirectories.addAll(getSubDirectoriesRecursively(dir));
		}
		return subDirectories;
	}

	/**
	 * Get all sub-directories recursively. 
	 * <p>
	 * NOTE: Might consume a lot of stack so use visualvm to check how it is affected by a "deep" directory.
	 * 
	 * @param dir The dir to investigate.
	 * @return A list of all sub-directories.
	 */
	private static List<File> getSubDirectoriesRecursively(File dir) {
		List<File> subDirectories = new ArrayList<>();
		for(File file: dir.listFiles()) {
			if(file.isDirectory()) {
				subDirectories.add(file);
				subDirectories.addAll(getSubDirectoriesRecursively(file));
			}
		}
		return subDirectories;
	}

	/**
	 * Get sub-directories - non-recursively.
	 * @param dir The dir to investigate
	 * @return A list of all sub-directory found directly in "dir".
	 */
	public static List<File> getSubdirsNonrecursive(File dir) {
		List<File> subDirectories = new ArrayList<>();
		for(File file: dir.listFiles()) {
			if(file.isDirectory()) {
				subDirectories.add(file);
			}
		}
		return subDirectories;
	}

	/**
	 * Get plain files - non-recursively.
	 * @param dir The dir to investigate
	 * @return A list of all files (non-directories) found directly in "dir".
	 */
	public static List<File> getFilesNonrecursively(File dir) {
		List<File> plainFiles = new ArrayList<>();

		for(File file: dir.listFiles()) {
			if(file.isFile()) {
				plainFiles.add(file);
			}
		}
		return plainFiles;
	}


	/**
	 * Close a Closeable object. Catch and log exceptions.
	 * @param c
	 */
	public static void closeExceptionless(Closeable... closeables) {
		for(Closeable c: closeables) {
			try {
				if(c!=null) {
					c.close();
				}
			} catch (IOException e) {
				log.error("Failed to close resource.", e);
			}			
		}
	}

	/**
	 * Check any of the directories are in the others directory tree.
	 * 
	 * @param dir1
	 * @param dir2
	 * @return true if one of the directories are in the others tree, else false.
	 */
	public static boolean inSameTree(File dir1, File dir2) {
		String dirPath1 = dir1.getAbsolutePath()+File.separatorChar;
		String dirPath2 = dir2.getAbsolutePath()+File.separatorChar;
		return dirPath1.startsWith(dirPath2) || dirPath2.startsWith(dirPath1);
	}

	/**
	 * Check if one of the files is a symbolic link to the other.
	 * 
	 * @param file1
	 * @param file2
	 * @return true if same canonical paths
	 * @throws IOException 
	 */
	public static boolean isSymbolicLinkTo(File file1, File file2) throws IOException {
		// Is  link if same canonical path.
		return file1.getCanonicalPath().equals(file2.getCanonicalPath());
	}

}
