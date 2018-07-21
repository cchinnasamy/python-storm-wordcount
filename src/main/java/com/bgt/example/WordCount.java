package com.bgt.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import com.bgt.example.bolt.SplitBolt;
import com.bgt.example.bolt.CountBolt;
import com.bgt.example.spout.SentenceSpout;

// The topology
public class WordCount 
{
    public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		// Spout emits random sentences
		builder.setSpout("SentenceSpout", new SentenceSpout(), 1);
		// Split bolt splits sentences and emits words
		builder.setBolt("SplitBolt", new SplitBolt(), 4).shuffleGrouping("SentenceSpout");

		builder.setBolt("CountBolt", new CountBolt(), 4).fieldsGrouping("SplitBolt", new Fields("word")); 
		
		//New configuration
		Config conf = new Config();
		
		// If there are arguments, we must be on a cluster
		if(args != null && args.length > 0) {
			conf.setNumWorkers(3);
            try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// Otherwise, we are running locally
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("test", conf, builder.createTopology());
		}
    }
}
