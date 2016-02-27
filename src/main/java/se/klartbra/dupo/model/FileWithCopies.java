package se.klartbra.dupo.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains a set of directories that was found equal during a search.
 * @author svante
 *
 */
public class FileWithCopies {
	private File file;
	protected Set<File> copies = new HashSet<>();
	
	// TODO: remove file, should be enough with only copies.
	
	/**
	 * CTOR
	 * @param file Just the directory that was used for comparison.
	 * @param copy The first copy
	 */
	public FileWithCopies(File file, File copy) {
		this.file = file;
		addCopy(copy);	
	}

	/**
	 * Another FileWithCopies is considered a copy of this  FileWithCopies if they have at least one file in common.
	 * @param other
	 * @return
	 */
	public boolean isCopyOf(FileWithCopies other) {
		if(other == this) {
			return false;
		}
		for(File otherFile: other.copies) {
			if(this.copies.contains(otherFile)) {
				return true;
			}
		}
		return false;
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
