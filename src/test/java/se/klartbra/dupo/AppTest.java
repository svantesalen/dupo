package se.klartbra.dupo;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
		System.out.println("testDirFile="+testDirFile.getAbsolutePath());
		String tcDirName ="TC1";
		String dir1Name = "d1";
		String dir2Name = "d2";
		File tcDir = new File(testDirFile, tcDirName);
		File dir1 = new File(tcDir, dir1Name);
		File dir2 = new File(tcDir, dir2Name);
		System.out.println("d1="+dir1.getAbsolutePath());
		System.out.println("d2="+dir2.getAbsolutePath());
		List<File> dirList = new ArrayList<>();
		dirList.add(dir2);
		CopyFinder copyFinder = new CopyFinder();
		assertTrue(copyFinder.findCopies(dir1, dirList, 0));
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}

	/**
	 * TC2. two dir with empty file (same name + different) and a third with different dir
	 */
	@Test
	public void tc2() {
		System.out.println("testDirFile="+testDirFile.getAbsolutePath());
		String tcDirName ="TC2";
		String dir1Name = "d1";
		String dir2Name = "d2";
		String dir3Name = "d3";
		File tcDir = new File(testDirFile, tcDirName);
		File dir1 = new File(tcDir, dir1Name);
		File dir2 = new File(tcDir, dir2Name);
		File dir3 = new File(tcDir, dir3Name);
		System.out.println("d1="+dir1.getAbsolutePath());
		System.out.println("d2="+dir2.getAbsolutePath());
		System.out.println("d3="+dir3.getAbsolutePath());
		List<File> dirList = new ArrayList<>();
		dirList.add(dir1);
		dirList.add(dir2);
		dirList.add(dir3);
		CopyFinder copyFinder = new CopyFinder();
		copyFinder.findCopies(dirList);
		System.out.println(copyFinder.getAllFilesWithCopies().toString());
		assertTrue(copyFinder.getAllFilesWithCopies().size() == 1);
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir1));
		assertTrue(copyFinder.getAllFilesWithCopies().contains(dir2));	
	}}
