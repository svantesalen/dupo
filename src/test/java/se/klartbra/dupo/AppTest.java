package se.klartbra.dupo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.klartbra.dupo.control.Controller;
import se.klartbra.dupo.control.filehandling.CopyFinder;

/*
 * Test cases

 * symbolic links: to file, to dir, to empty file, to empty dir
 * TC1. two empty dir
 * TC2. two dir with empty file (same name + different)
 * TC3. two dir with empty sub-dir
 * TC4. two dir with empty sub-dir and an empty file (or many)
 * TC5. two dir with empty file, sub-dir and symbolic link 
 * TC6 one non-empty file (same and different)
 * TC7 one file one dir (same and different)
 * TC8 same file and symbolic links to a third directory (treat as copies)
 * TC9 same sub-dir (deep) (sub dir must not be listed as copies)
 * TC10 same contents + naming of directories and files on top level differs(treat as copies)

 * TC11 same contents + naming of directories and files on lower level differs(treat as different)
 * TC12 different contents + one sub-dir is equal (with same name + with different name)
 * TC13 test cases where the capitalization differs (file + sub-dir)
 * TC14 hard links and soft links
 * 
 */


public class AppTest  {

	private static final File testDirFile = new File("/home/svante/test");



	/**
	 *  TC1. two empty dir
	 */
	@Test
	public void tc1() {
		String tcDirName ="TC1";
		String dir1Name = "ed1";
		String dir2Name = "ed2";
		File parent = new File(testDirFile, tcDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));		
	}

	/**
	 * TC2. two dir with empty file (same name + different) and a third with different dir
	 */
	@Test
	public void tc2() {
		String tcDirName ="TC2";
		String dir1Name = "d1";
		String dir2Name = "d2";
		String dir3Name = "d3";
		File parent = new File(testDirFile, tcDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		File dir3 = new File(parent, dir3Name);

		CopyFinder copyFinder = find(dir1, dir2, dir3);

		System.out.println(copyFinder.getAllFilesWithCopies().toString());
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir3));	
	}
	
	
	/**
	 * TC3. two dir with empty sub-dir
	 * - same
	 */
	@Test
	public void tc3() {
		String parentDirName ="TC3";
		String dir1Name = "d1";
		String dir2Name = "d2";
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));		
		}
	
	/**
	 * Similar to TC3, but each dir has a tree with no files (just empty sub directories).
	 */
	@Test
	public void tc3_1() {
		String parentDirName ="TC3";
		String dir1Name = "d1_deep";
		String dir2Name = "d2_deep";
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}

	/**		System.out.println(copyFinder.getAllFilesWithCopies().toString());

	 * TC4. two dir with empty sub-dir and an empty file (or many) - same names
	 * Expected result: equal
	 */
	@Test
	public void tc4() {
		String parentDirName ="TC4";
		String dir1Name = "d1";
		String dir2Name = "d2";
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}

	/**
	 * TC5. two dir with empty file, sub-dir and symbolic link 
	 * Expected result: equal
	 */
	@Test
	public void tc5() {
		String parentDirName ="TC5";
		String dir1Name = "d1";
		String dir2Name = "d2";
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}

	/**
	 * TC6.1 one non-empty file (same)
	 * Expected result: equal
	 */
	@Test
	public void tc6_1() {
		String parentDirName ="TC6.1";
		String dir1Name = "d1";
		String dir2Name = "d2";
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}


	/**
	 * TC6.2 one non-empty file (different)
	 * Expected result: NOT equal
	 */
	@Test
	public void tc6_2() {
		String parentDirName ="TC6.2";
		String dir1Name = "d1";
		String dir2Name = "d2";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 0);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 0);
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}

	/**
	 * TC7.1 one file one dir
	 * Expected result: equal
	 */
	@Test
	public void tc7_1() {
		String parentDirName ="TC7.1";
		String dir1Name = "d1";
		String dir2Name = "d2";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}


	/**
	 * TC7.2 one file one dir 
	 * Expected result: NOT equal, subdir are equal though
	 */
	@Test
	public void tc7_2() {
		String parentDirName ="TC7.2";
		String dir1Name = "d1";
		String dir2Name = "d2";
		String dir3Name = "d3";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		File dir3 = new File(parent, dir3Name);

		CopyFinder copyFinder = find(dir1, dir2, dir3);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 5);
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir2));	
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir3));	
}
	

	/**
	 * TC8 same file and symbolic links to a third directory (treat as copies)
	 * Expected result: equal
	 */
	@Test
	public void tc8() {
		String parentDirName ="TC8";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));

		System.out.println("###### done ######");
	}


	/**
	 *  TC9 same sub-dir (deep) <br>
	 *  - sub dir must not be listed as copies <br>
	 *  - same on top level
	 * Expected result: equal
	 */
	@Test
	public void tc9() {
		String parentDirName ="TC9";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);

		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));
		System.out.println("###### done ######");
	}

	/**
	 * TC10 same contents + naming of directories and files on top level differs(treat as copies)
	 * compare next1/d1 and next2/d1 (not next1 and next2)
	 * Expected result: equal
	 */
	@Test
	public void tc10() {
		String parentDirName ="TC10";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d1";
		
		File grandParent = new File(testDirFile, parentDirName);
		File parent1 = new File (grandParent, "next1");
		File parent2 = new File (grandParent, "next2");
		File dir1 = new File (parent1, dir1Name);
		File dir2 = new File (parent2, dir2Name);
		
		CopyFinder copyFinder = find(dir1, dir2);

		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));
		
		System.out.println("###### done ######");
	}
	
	/**
	 * TC11 same contents + naming of directories and files on lower level differs
	 * Expected result: different
	 */
	@Test
	public void tc11() {
		String parentDirName ="TC11";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";

		File parent = new File(testDirFile, parentDirName);		
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		
		CopyFinder copyFinder = find(dir1, dir2);

		assertTrue(copyFinder.getAllFilesWithCopies().size() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 10);
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir2));
		
		System.out.println("###### done ######");
	}
	
	/**
	 *  TC12 different contents + one sub-dir is equal 
	 *  with same name + with different name)
	 *  - top dir not equal
	 *  - sameDir are equal
	 * Expected result: different
	 */
	@Test
	public void tc12() {
		String parentDirName ="TC12";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";

		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		
		CopyFinder copyFinder = find(dir1, dir2);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir2));
		System.out.println("###### done ######");
	}

	
	/**
	 *  TC12 different contents + one sub-dir is equal 
	 *  with same name + with different name)
	 *  - top dir not equal
	 *  - sameDir are equal
	 * Expected result: different
	 */
	@Test
	public void tc13() {
		String parentDirName ="TC13";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";
		String dir3Name = "d3";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		File dir3 = new File(parent, dir3Name);

		CopyFinder copyFinder = find(dir1, dir2, dir3);
		
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
 		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 3);
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir2));
		System.out.println("###### done ######");
	}
	
	/**
	 * TC14 hard links and soft links
	 * Expected result: equal
	 */
	@Test
	public void tc14() {
		String parentDirName ="TC14";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		
		CopyFinder copyFinder = find(dir1, dir2);

		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 2);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));
		
		System.out.println("###### done ######");
	}
	
	/**
	 * TC14 hard links and soft links
	 * - different since one of the dir contains a soft link where the other contains a real dir
	 * Expected result: different
	 */
	@Test
	public void tc15() {
		String parentDirName ="TC15";
		System.out.println("###### "+parentDirName+ " ######");
		String dir1Name = "d1";
		String dir2Name = "d2";
		
		File parent = new File(testDirFile, parentDirName);
		File dir1 = new File(parent, dir1Name);
		File dir2 = new File(parent, dir2Name);
		
		CopyFinder copyFinder = find(dir1, dir2);

		assertTrue(copyFinder.getAllFilesWithCopies().size() == 0);
		assertTrue(copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles() == 0);
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertFalse(copyFinder.getAllFilesWithCopies().contains(dir2));
		
		System.out.println("###### done ######");
	}
	

	
	private CopyFinder find(File... dirs) {
		List<File> dirList = new ArrayList<>();
		for(File dir: dirs) {
			System.out.println("dir="+dir.getAbsolutePath());
			dirList.add(dir);
			
		}
		Controller.getInstance().addSubDirectories(dirList);
		CopyFinder copyFinder = new CopyFinder();
		copyFinder.findCopies(dirList);
 		int numberOfFiles = copyFinder.getAllFilesWithCopies().getTotalNumberOfFiles();
		System.out.println("\nCopies found: \n"+copyFinder.getAllFilesWithCopies().toString());
		System.out.println("\n"+numberOfFiles+" number of files: \n");
		copyFinder.getAllFilesWithCopies().printAllFiles();
		return copyFinder;
	}

	
}
