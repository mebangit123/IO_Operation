package com.io;

import static java.nio.file.StandardWatchEventKinds.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class JavaWatchServiceExample {
	
	private final WatchService watcher;
	private final Map<WatchKey, Path> dirWatchers;

	public JavaWatchServiceExample(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dirWatchers = new HashMap<>();
		scanAndRegisterDirectories(dir);
	}
	private void registerDirWathcers(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		dirWatchers.put(key, dir);
	}
	private void scanAndRegisterDirectories(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				registerDirWathcers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
	@SuppressWarnings({"rawtypes", "unchecked"})
	void processEvents() {
		while(true) {
			WatchKey key;
			try {
				key = watcher.take();
			}catch (InterruptedException x) {
				return;
			}
			Path dir = dirWatchers.get(key);
			if(dir == null) continue;
			for(WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				Path name = ((WatchEvent<Path>)event).context();
				Path child = dir.resolve(name);
				System.out.format("%s: %s\n", event.kind().name(), child);
				
				if(kind == ENTRY_CREATE) {
					try {
						if(Files.isDirectory(child)) scanAndRegisterDirectories(child);
					}catch (IOException x) {}
				}else if(kind.equals(ENTRY_DELETE)) {
					if(Files.isDirectory(child)) dirWatchers.remove(key);
				}
			}
			boolean valid = key.reset();
			if(!valid) {
				dirWatchers.remove(key);
				if(dirWatchers.isEmpty())break;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		Path dir = Paths.get("D:/Git/IO_Operation/IO-Operation/watchTest");
		Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
		new JavaWatchServiceExample(dir).processEvents();
	}
}
