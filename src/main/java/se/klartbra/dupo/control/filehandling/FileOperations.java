package se.klartbra.dupo.control.filehandling;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
		File[] files = dir.listFiles();
		if(files != null) {
			for(File file: files) {
				if(file.isDirectory()) {
					subDirectories.add(file);
				}
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
		File[] files = dir.listFiles();
		if(files != null) {
			for(File file: files) {
				if(file.isFile()) {
					plainFiles.add(file);
				}
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
	 * Check if one of the files is a symbolic link to the other, or <br>
	 * if one is a symbolic link and the other is not
	 * @param file1
	 * @param file2
	 * @return true if same canonical paths
	 * @throws IOException 
	 */
	public static boolean isSymbolicLinkTo(File file1, File file2) throws IOException {
		boolean isSymlink1 = Files.isSymbolicLink(file1.toPath());
		boolean isSymlink2 = Files.isSymbolicLink(file2.toPath());

		if(isSymlink1 ^ isSymlink2) {
			// one of them is a symbolic link, the other is not
			// If one points to the other then they have same canonical paths
			return file1.getCanonicalPath().equals(file2.getCanonicalPath());
		}
		return false;
	}

	public static boolean isSymlink(File file) {
		return Files.isSymbolicLink(file.toPath());		
	}

	/**
	 * Check if exactly one of the files is part of a symbolic link.
	 * @param file1
	 * @param file2
	 * @return true if one file is part of a symbolic link, false if none or both
	 * @throws IOException
	 */
	public static boolean isOnePartOfSymboplicLink(File file1, File file2) throws IOException {
		return isPartOfSymbolicLinkPath(file1) ^ isPartOfSymbolicLinkPath(file2);
	}
	private static boolean isPartOfSymbolicLinkPath(File file) throws IOException {
		return !file.getCanonicalPath().equals(file.getAbsolutePath());

	}

	public static boolean hasSameType(File file1, File file2) throws IOException {
		if(file1.isDirectory() && file2.isFile() || file2.isDirectory() && file1.isFile()) {
			return false;
		}
		boolean isSymlink1 = Files.isSymbolicLink(file1.toPath());
		boolean isSymlink2 = Files.isSymbolicLink(file2.toPath());
		return isSymlink1 && isSymlink2 || !isSymlink1 && !isSymlink2;
	}

	public enum Type {
		DIR, FILE;
	}

	public static int getNumberOfFiles(File dir) {
		return getNumberOf(Type.FILE, dir);
	}

	public static int getNumberOfSubDirectories(File dir) {
		return getNumberOf(Type.DIR, dir);
	}

	public static int getNumberOf(Type type, File dir) {
		if(dir.isFile()) {
			log.error("Method called with a plain file as argument. Expected a directory.");
			return 0;
		}
		int count = 0;
		File[] files = dir.listFiles();
		if(files == null) {
			return 0;
		}
		
		for(File file: dir.listFiles()) {
			if(type == Type.DIR  && file.isDirectory() 
					|| type == Type.FILE  && file.isFile()) 
			{
				count ++;
			}
		}

		return count;
	}
	
	public static String toString(List<File> fileList) {
		StringBuilder sb = new StringBuilder();
		for(File f: fileList) {
			sb.append(f.getAbsolutePath());
			sb.append("\n");
		}
		return sb.toString().trim();
	}

}






















