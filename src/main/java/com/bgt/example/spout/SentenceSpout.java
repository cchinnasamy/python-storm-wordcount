package com.bgt.example.spout;

import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.spout.ShellSpout;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

/*
This is basically just defining some JVM things for Storm, such as
the output fields, or passing around configuration. Then it invokes the
sentencespout.py using Python.
*/
public class SentenceSpout extends ShellSpout implements IRichSpout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Invoke the python spout
    public SentenceSpout() {
        super("python", "sentencespout.py");
    }

    // Declare that we emit a 'sentence' field
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }

    // No real configuration going on
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}