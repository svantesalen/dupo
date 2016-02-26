package se.klartbra.dupo.control;

import javax.swing.SwingWorker;

import se.klartbra.dupo.model.AllFilesWithCopies;

public class Worker extends SwingWorker<AllFilesWithCopies, String>{

	@Override
	protected AllFilesWithCopies doInBackground() throws Exception {
	    // Start
	    publish("Start");
	    setProgress(1);
	    
	    // More work was done
	    publish("More work was done");
	    setProgress(10);

	    // Complete
	    publish("Complete");
	    setProgress(100);
	    return 1;
	    
		return null;
	}

}
