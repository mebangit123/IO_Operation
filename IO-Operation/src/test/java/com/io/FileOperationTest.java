package com.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;


public class FileOperationTest {
	private String HOME = "D:/Git/IO_Operation/IO-Operation/watchTest";
	private static String PLAY_WITH_NIO = "TempPlayGround";
	@Test
	public void givenPathWhenCheckedThenConfirm() throws IOException {
		
		Path homePath = Paths.get(HOME);
		Assert.assertTrue(Files.exists(homePath));
		
		Path playPath = Paths.get(HOME + "/"+PLAY_WITH_NIO);
		if(Files.exists(playPath)) FileUtils.deleteFiles(playPath.toFile());
		Assert.assertTrue(Files.notExists(playPath));
		
		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(playPath + "/temp"+cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try { Files.createFile(tempFile); }
			catch (IOException e) {}
			Assert.assertTrue(Files.exists(tempFile));
		});
		
		Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
		Files.newDirectoryStream(playPath).forEach(System.out::println);
		Files.newDirectoryStream(playPath, path -> path.toFile().isFile() &&
												   path.toString().startsWith("temp"))
										  .forEach(System.out::println);
	}
	public void givenADirectoryWhenWatchedListsAllTheActivities() throws IOException {
		Path dir = Paths.get(HOME);
		Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
		new JavaWatchServiceExample(dir).processEvents();
	}
	
}