package com.shaunericcarlson.bridgeai.bidnetwork;

import java.util.Collection;

public class OneLevel {
	private final static double ERROR_TOLERANCE = 0.0001;
	private NeuralNetwork nn;
	private Collection<BidTrainingData> bids;
	
	public OneLevel(Collection<BidTrainingData> bids) {
		int hiddens = (24 + 9) / 2;
		this.nn = new NeuralNetwork(24, hiddens, 9);
		this.bids = bids;
	}
	
	private double getError() {
		double error = 0.0;
		for (BidTrainingData btd : this.bids) {
			double[] output = this.nn.computeOutputs(btd.getInputs());
			double[] target = btd.getOutputs();
			
			for (int i = 0; i < output.length; i++) {
			    error += Math.pow(target[i] - output[i], 2);
			}
		}
		
		return error;
	}
	
	public void train() {
		int count = 0;
		while (this.getError() > OneLevel.ERROR_TOLERANCE) {
			count++;
			for (BidTrainingData btd : this.bids) {
				this.nn.train(btd.getInputs(), btd.getOutputs());
			}
		}
		System.out.println(count);
	}
	
	public String toString() {
		return this.nn.toString();
	}
}
