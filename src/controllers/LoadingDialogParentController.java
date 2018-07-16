package controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.OsuDbParser;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public abstract class LoadingDialogParentController {
	@FXML protected Label instructionLabel;
	@FXML protected ProgressBar progressBar;
	protected ExecutorService exec = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true); // allows app to exit if tasks are running
        return t ;
    });
	
	
	protected Task<OsuDbParser> getLoadOsuDbTask(final String fullPathToOsuDb, final String pathToSongsFolder) {
		return new Task<OsuDbParser>() {
			@Override 
			protected OsuDbParser call() throws Exception {
				OsuDbParser osuDb = new OsuDbParser(fullPathToOsuDb, pathToSongsFolder);
				osuDb.setThreadData((workDone, totalWork) -> updateProgress(workDone, totalWork));
				osuDb.startParsing();
				return osuDb;
			}
		};
	}
}
