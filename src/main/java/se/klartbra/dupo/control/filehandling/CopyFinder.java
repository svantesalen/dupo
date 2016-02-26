package se.klartbra.dupo.control.filehandling;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.model.AllFilesWithCopies;

public class CopyFinder {
	private static Logger log = LogManager.getLogger(CopyFinder.class);
	private AllFilesWithCopies allFilesWithCopies = new AllFilesWithCopies();

	public AllFilesWithCopies getAllFilesWithCopies() {
		return allFilesWithCopies;
	}

//	/**
//	 * Find directory copies in a list. 
//	 * @param directories
//	 * @return
//	 */
//	//	public List<File> findCopies(List<File> directories) {
//	public void findCopies(List<File> directories) {
//		List<File> copies = new ArrayList<>();
//		for(int i=0; i<directories.size(); i++) {
//			//			copies.addAll(findCopies(directories.get(i), directories, i+1));
//			findCopies(directories.get(i), directories, i+1);
//		}
//		//		return copies;
//	}

	
	public boolean findCopies(File dir, List<File> list, int startIndex) {
		log.debug("Find copies for: "+dir.getAbsolutePath());
		boolean found = false;
		File lastCopy = null;
		for(int i=startIndex; i<list.size(); i++) {
			if(lastCopy !=null) {
				// Avoid unnecessary work: 
				// skip if next dir is a sub-directory of previous copy found.
				String pathPrevCopy = lastCopy.getAbsolutePath()+File.separatorChar;
				String pathNextDir = list.get(i).getAbsolutePath();
				if(pathNextDir.startsWith(pathPrevCopy)) {
					continue;
				} else {
					// Sub-directory list is in depth order, 
					// so no to do further checking until a new copy is found.
					lastCopy = null;
				}
			}			
			if(areCopies(dir, list.get(i))) {
				log.debug("Found a copy: "+list.get(i).getAbsolutePath());
				allFilesWithCopies.add(dir, list.get(i));
				lastCopy = list.get(i);
				found = true;
			}
		}
		return found;
	}	

	/**
	 * Two directories are copies if they contain same files and same sub-directories.
	 * @param dir1
	 * @param dir2
	 * @return true if same
	 */
	private boolean areCopies(File dir1, File dir2) {
		if(FileOperations.inSameTree(dir1, dir2)) {
			return false;
		}
		try {
			if(FileOperations.isSymbolicLinkTo(dir1, dir2)) {
				return false;
			}
		} catch (IOException e) {
			// What to do here? If we treat as a non-link then user might delete the only dir,
			// If we treat it as a link and it is not, then we miss information about a copy.
			// Maybe add to a list with directories we are unsure if they are symbolic links.
			log.error("IOException at checking for symbolic link. Handle as if not a symbolik link.", e);
		}
		List<File> filesInDir1 = FileOperations.getFilesNonrecursively(dir1);
		List<File> filesInDir2 = FileOperations.getFilesNonrecursively(dir2);
		if(!hasSameFiles(filesInDir1, filesInDir2)) {
			return false;
		}
		List<File> subDirsInDir1 = FileOperations.getSubdirsNonrecursive(dir1);
		List<File> subDirsInDir2 = FileOperations.getSubdirsNonrecursive(dir2);
		return hasSameSubDirs(subDirsInDir1, subDirsInDir2);
	}

	private boolean hasSameSubDirs(List<File> subDirsInDir1, List<File> subDirsInDir2) {
		if(subDirsInDir1.size() != subDirsInDir2.size()) {
			return false;
		}
		for(File subDir1: subDirsInDir1) {
			if(!hasCopyOfThisSubDir(subDir1, subDirsInDir2)) {
				return false;
			}
		}
		return true;		 
	}

	private boolean hasCopyOfThisSubDir(File subDir, List<File> subDirList) {
		File dirWithSameName = findFileWithSameName(subDir, subDirList);
		if(dirWithSameName == null || dirWithSameName.isFile()) {
			return false;
		}
		return areCopies(subDir, dirWithSameName);

	}

	private boolean hasSameFiles(List<File> filesInDir1, List<File> filesInDir2) {
		if(filesInDir1.size() != filesInDir2.size()) {
			return false;
		}
		for(File file1: filesInDir1) {
			if(!hasCopyOfThisFile(file1, filesInDir2)) {
				return false;
			}
		}
		return true;
	}

	private boolean hasCopyOfThisFile(File file, List<File> fileList) {
		File fileWithSameName = findFileWithSameName(file, fileList);
		if(fileWithSameName == null || fileWithSameName.isDirectory()) {
			return false;
		}
		if(file.length() != fileWithSameName.length()) {
			return false;
		}
		return hasSameContent(file, fileWithSameName);
	}

	private File findFileWithSameName(File theFile, List<File> fileList) {
		for(File file: fileList) {
			if(file.getName().equals(theFile.getName())) {
				return file;
			}
		}
		return null;
	}

	/**
	 * Bitwise comparison of 2 files.
	 * 
	 * @param file1
	 * @param file2
	 * @return true if the files has same contents..
	 */
	private boolean hasSameContent(File file1, File file2) {
		FileInputStream inputStream1=null;
		FileInputStream inputStream2=null;

		try {
			inputStream1 = new FileInputStream(file1); // NOSONAR
		} catch (FileNotFoundException e) {
			log.error("Could not find "+file1.getAbsolutePath(), e);
			FileOperations.closeExceptionless(inputStream1);
			return false;
		} 
		try {
			inputStream2 = new FileInputStream(file2); // NOSONAR
		} catch (FileNotFoundException e) {
			log.error("Could not find "+file2.getAbsolutePath(), e);
			FileOperations.closeExceptionless(inputStream1, inputStream2);
			return false;
		} 

		InputStream bufferedInStream1 = new BufferedInputStream(inputStream1);
		InputStream bufferedInStream2 = new BufferedInputStream(inputStream2);
		int byte1 = 0 ;
		int byte2 = 0 ;
		while ((byte1 | byte2) >= 0) {
			try {
				byte1 = bufferedInStream1.read();
				byte2 = bufferedInStream2.read();
				if (byte1 != byte2) { 
					log.debug("NOT SAME: "+file1.getCanonicalPath()+" AND "+file2.getCanonicalPath());
					FileOperations.closeExceptionless(bufferedInStream1, bufferedInStream2);
					return false;
				}
			} catch (Exception e) {
				log.error("Exception at comparing file "+file1.getAbsolutePath()+ " with "+ file2.getAbsolutePath(), e);
				log.debug("NOT SAME: "+file1.getAbsolutePath()+" AND "+file2.getAbsolutePath());
				FileOperations.closeExceptionless(bufferedInStream1, bufferedInStream2);
				return false;
			} 
		}
		FileOperations.closeExceptionless(bufferedInStream1, bufferedInStream2);
		log.debug("SAME: "+file1.getAbsolutePath()+" AND "+file2.getAbsolutePath());
		return true;
	}
}
