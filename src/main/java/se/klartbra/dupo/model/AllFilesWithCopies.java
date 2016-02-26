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

public class AllFilesWithCopies {
	private static Logger log = LogManager.getLogger(AllFilesWithCopies.class);

	private Map<File, FileWithCopies> allFileWithCopiesMap  = new HashMap<>();
	private Set<File> allFiles = new HashSet<>();

	public FileWithCopies get(File file) {
		return allFileWithCopiesMap.get(file);
	}

	public  void add(File file, File copy) {
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

		Set<FileWithCopies> removeSet  = new HashSet<>();
		for(FileWithCopies fileWithCopies: allFileWithCopiesMap.values()) {
			if(allParentsHaveOwnCopies(fileWithCopies)) {
				log.debug("remove since parents already considered duplicates: \n"+ fileWithCopies.toString());
				removeSet.add(fileWithCopies);
			}
		}
		for(FileWithCopies fileWithCopies: removeSet) {
			allFileWithCopiesMap.remove(fileWithCopies.getFile());
		}
	}

	private boolean allParentsHaveOwnCopies(FileWithCopies fileWithCopies) {
		for(File parent: fileWithCopies.getAllParents()) {
			if(!allFiles.contains(parent)) {
				return false;
			}
		}
		return true;
	}

	public int size() {
		return allFileWithCopiesMap.size();
	}
	
	public List<FileWithCopies> toArray() {
		return new ArrayList<>(allFileWithCopiesMap.values());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(FileWithCopies fileWithCopies: allFileWithCopiesMap.values()) {
			sb.append("\n---------------------------------\n");
			sb.append(fileWithCopies.toString());
		}
		return sb.toString().trim();
	}



}
