package se.klartbra.dupo.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileWithCopies {
	private File file;
	private Set<File> copies = new HashSet<>();
	
	/**
	 * CTOR
	 * @param file The file
	 * @param copy The first copy
	 */
	public FileWithCopies(File file, File copy) {
		this.file = file;
		addCopy(copy);
		
	}
	public File getFile() {
		return file;
	}
	public boolean addCopy(File copy) {
		return copies.add(copy);
	}

	public Set<File> getAllParents() {
		Set<File> allParents = new HashSet<>();
		File parent = file.getParentFile();
		if(parent != null) {
			allParents.add(parent);
		}
		for(File copy: copies) {
			parent = copy.getParentFile();
			if(parent != null) {
				allParents.add(parent);
			}
		}
		return allParents;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.file.getAbsolutePath());
		sb.append("\n");
		for(File copy: copies) {
			sb.append(copy.getAbsolutePath());
			sb.append("\n");
		}
		return sb.toString().trim();
	}
}
