package se.klartbra.dupo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The container for all copies found during a search.
 * 
 * @author svante
 *
 */
public class AllFilesWithCopies {
	private static Logger log = LogManager.getLogger(AllFilesWithCopies.class);

	private Map<File, FileWithCopies> allFileWithCopiesMap  = new HashMap<>();
	private Set<File> allFiles = new HashSet<>();

	/**
	 * CTOR
	 */
	public AllFilesWithCopies() {/* EMPTY*/}
	
	public FileWithCopies get(File file) {
		return allFileWithCopiesMap.get(file);
	}

	public  void add(File file, File copy) {
		if(allFiles.contains(copy)) {
			log.debug("###### Already added: "+copy.getAbsolutePath());
			return;
		}
		FileWithCopies fileWithCopies = get(file);
		if(fileWithCopies == null) {
			fileWithCopies = new FileWithCopies(file, copy);
			allFileWithCopiesMap.put(file, fileWithCopies);
		} else {
			fileWithCopies.addCopy(copy);
		}
		allFiles.add(file);
		allFiles.add(copy);
	}

	/**
	 * To avoid having all children here.
	 * When all files in a FilesWithCopies have parents who also are in a FilesWithCopies, then remove that FilesWithCopies.
	 */
	public void cleanUp() {		
		for(Entry<File, FileWithCopies> entry: allFileWithCopiesMap.entrySet()) {
			if(entry.getKey() != entry.getValue().getFile()) {
				log.error("****** PROGRAM ERROR ******");;
			}
		}		
		concatenateDuplicates();
		if(removeChildrenOfDuplicateParents()) {
			refreshListForAllFiles(); 
		}
	}
	
	/**
	 * Remove a {@link FileWithCopies} if all of it's directories are already represented by another {@link FileWithCopies}, not by themselves but by their parents.
	 * @return
	 */
	private boolean removeChildrenOfDuplicateParents() {
		Set<FileWithCopies> removeSet  = new HashSet<>();
		for(FileWithCopies fileWithCopies: allFileWithCopiesMap.values()) {
			if(allParentsHaveOwnCopies(fileWithCopies)) {
				log.debug("remove since parents already considered duplicates: \n"+ fileWithCopies.toString());
				removeSet.add(fileWithCopies);
			}
		}
		
		if(removeSet.isEmpty()) {
			return false;
		}
		
		for(FileWithCopies fileWithCopies: removeSet) {
			allFileWithCopiesMap.remove(fileWithCopies.getFile());
		}
		return true;
	}
	
	private void refreshListForAllFiles() {
		allFiles = new HashSet<>();
		for(FileWithCopies fileWithCopies: allFileWithCopiesMap.values()) {
			allFiles.addAll(fileWithCopies.getAllCopies());
			allFiles.add(fileWithCopies.getFile());
		}
	}
	

	/**
	 * If a dir is present in 2 FileWithCopies then concatenate those 2.
	 */
	private boolean concatenateDuplicates() {
		Set<FileWithCopies> removeSet  = new HashSet<>();
		Object[] files = allFileWithCopiesMap.values().toArray();
		int size = files.length;
		FileWithCopies fwc1;
		FileWithCopies fwc2;
		for(int i=0; i< size; i++) {
			for(int j=i; j<size; j++) {
				fwc1 = (FileWithCopies)files[i];
				fwc2 = (FileWithCopies)files[j];
				if(fwc1.isCopyOf(fwc2)) {
					fwc1.copies.addAll(fwc2.copies);
					removeSet.add(fwc2);
				}
			}
		}
		if(removeSet.isEmpty()) {
			return false;
		}
		for(FileWithCopies fileWithCopiesToRemove: removeSet) {
			log.error("################### SHOULD NEVER HAPPEN! remove since duplicate: \n"+ fileWithCopiesToRemove.toString());
			allFileWithCopiesMap.remove(fileWithCopiesToRemove);
		}
		return true;	
	}

	/**
	 * Check if the parents (recursively) of a {@link FileWithCopies} are themselves part of another FileWithCopies.
	 * @param fileWithCopies
	 * @return
	 */
	private boolean allParentsHaveOwnCopies(FileWithCopies fileWithCopies) {
		for(File parent: fileWithCopies.getAllParents()) {
			if(!allFiles.contains(parent)) {
				log.debug("This parent is not part of allFiles:"+parent.getAbsolutePath());
				return false;
			}
			log.debug("This parent is part of allFiles:"+parent.getAbsolutePath());
		}
		return true;
	}
	
	public boolean contains(File file) {
		return allFiles.contains(file);
	}
	
	// for test
	public int getTotalNumberOfFiles() {
		return allFiles.size();
	}
	
	public int size() {
		return allFileWithCopiesMap.size();
	}

	public boolean isEmpty() {
		return allFileWithCopiesMap.isEmpty();
	}

	public List<FileWithCopies> toArray() {
		return new ArrayList<>(allFileWithCopiesMap.values());
	}

	@Override
	public String toString() {
		if(isEmpty()) {
			return "no copies found";
		}
		StringBuilder sb = new StringBuilder();
		int i=1;
		for(FileWithCopies fileWithCopies: allFileWithCopiesMap.values()) {
			sb.append("\n->>>>>>>>>>>>>>>"+ (i++) +">>>>>>>>>>>>>>>>>\n");
			sb.append(fileWithCopies.toString());
			sb.append("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		}
		return sb.toString().trim();
	}
	
	// for test (remove)
	public void printAllFiles() {
		for(File file: allFiles) {
			System.out.println("path: "+ file.getAbsolutePath());
		}
	}



}
