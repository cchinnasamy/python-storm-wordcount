package com.bgt.example.bolt;

import java.util.Map;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;


/*
This is basically just defining some JVM things for Storm, such as
the output fields, or passing around configuration. Then it invokes the
splitbolt.py using Python.
*/
public class SplitBolt extends ShellBolt implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Call the splitbolt.py using Python
	public SplitBolt() {
		super("python", "splitbolt.py");
	}
	
	// Declare that we emit a 'word'
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	// Nothing to do for configuration
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}