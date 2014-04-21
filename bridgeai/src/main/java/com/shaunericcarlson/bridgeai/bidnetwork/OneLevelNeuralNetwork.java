package com.shaunericcarlson.bridgeai.bidnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class OneLevelNeuralNetwork implements NeuralNetworkInterface {
	public static double ETA = 0.17;
	public static double MOMENTUM_RATE = 0.12;
	public static final double HIGH = 0.9;
	public static final double LOW = 0.1;
	
	private static final Random RAND = new Random();
	private static final double RANGE_MIN = -0.05;
	private static final double RANGE_MAX = 0.05;
	
	private double[] inputs;
	private double[] hiddens;
	private double[] outputs;
	
	private double[][] inputToHiddenWeights;
	private double[][] hiddenToOutputWeights;
	
	private double[][] prevDeltaInputToHiddenWeights;
	private double[][] prevDeltaHiddenToOutputWeights;
	
	private double[] hiddenBiases;
	private double[] outputBiases;
	
	private double[] prevDeltaHiddenBiases;
	private double[] prevDeltaOutputBiases;
	
	public OneLevelNeuralNetwork(int numberOfInputs, int numberOfHiddens, int numberOfOutputs) {
		this.inputs = new double[numberOfInputs];
		this.inputToHiddenWeights = this.initializeWeightMatrix(numberOfInputs, numberOfHiddens, true);
		this.prevDeltaInputToHiddenWeights = this.initializeWeightMatrix(numberOfInputs, numberOfHiddens, false);
		
		this.hiddens = new double[numberOfHiddens];
		this.hiddenToOutputWeights = this.initializeWeightMatrix(numberOfHiddens, numberOfOutputs, true);
		this.prevDeltaHiddenToOutputWeights = this.initializeWeightMatrix(numberOfHiddens, numberOfOutputs, false);
		this.hiddenBiases = this.initializeBiasWeights(numberOfHiddens, true);
		this.prevDeltaHiddenBiases = this.initializeBiasWeights(numberOfHiddens, false);
		
		this.outputs = new double[numberOfOutputs];
		this.outputBiases = this.initializeBiasWeights(numberOfOutputs, true);
		this.prevDeltaOutputBiases = this.initializeBiasWeights(numberOfOutputs, false);
	}
	
	public OneLevelNeuralNetwork(BufferedReader r) throws IOException {
        ArrayList<ArrayList<Double>> hiddensToInputs = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> outputsToHiddens = new ArrayList<ArrayList<Double>>();
	    String line = r.readLine();
	    line = r.readLine();
	    while (!line.equals("")) {
	        ArrayList<Double> subList = new ArrayList<Double>();
    	    while (!line.equals("")) {
    	        subList.add(Double.parseDouble(line));
    	        line = r.readLine();
    	    }
    	    
    	    hiddensToInputs.add(subList);
    	    line = r.readLine();
	    }
	    
	    line = r.readLine();
        while (line != null && !line.equals("")) {
            ArrayList<Double> subList = new ArrayList<Double>();
            while (line != null && !line.equals("")) {
                subList.add(Double.parseDouble(line));
                line = r.readLine();
            }
            
            outputsToHiddens.add(subList);
            line = r.readLine();
        }
	    
        this.inputs = new double[hiddensToInputs.get(0).size() - 1];
        this.hiddens = new double[hiddensToInputs.size()];
        this.outputs = new double[outputsToHiddens.size()];
        
        this.inputToHiddenWeights = new double[this.inputs.length][this.hiddens.length];
        this.hiddenBiases = new double[this.hiddens.length];
        for (int i = 0; i < hiddensToInputs.size(); i++) {
            int j = 0;
            for (j = 0; j < hiddensToInputs.get(i).size() - 1; j++) {
                this.inputToHiddenWeights[j][i] = hiddensToInputs.get(i).get(j);
            }
            
            this.hiddenBiases[i] = hiddensToInputs.get(i).get(j);
        }
        
        this.hiddenToOutputWeights = new double[this.hiddens.length][this.outputs.length];
        this.outputBiases = new double[this.outputs.length];
        for (int i = 0; i < outputsToHiddens.size(); i++) {
            int j = 0;
            for (j = 0; j < outputsToHiddens.get(i).size() - 1; j++) {
                this.hiddenToOutputWeights[j][i] = outputsToHiddens.get(i).get(j);
            }
            
            this.outputBiases[i] = outputsToHiddens.get(i).get(j);
        }
	}

	private void adjustHiddenWeights(double[] deltaK) {
		for (int i = 0; i < this.hiddens.length; i++) {
			double error = 0.0;
			for (int j = 0; j < this.outputs.length; j++) {
				error += this.hiddenToOutputWeights[i][j] * deltaK[j];
			}
			
			double deltaH = this.hiddens[i] * (1 - this.hiddens[i]) * error;
			for (int j = 0; j < this.inputs.length; j++) {
				double deltaW = (OneLevelNeuralNetwork.ETA * deltaH * this.inputs[j]) + (OneLevelNeuralNetwork.MOMENTUM_RATE * this.prevDeltaInputToHiddenWeights[j][i]);
				this.inputToHiddenWeights[j][i] += deltaW;
				this.prevDeltaInputToHiddenWeights[j][i] = deltaW;
			}
			
			double deltaBiasW = (OneLevelNeuralNetwork.ETA * deltaH * 1.0) + (OneLevelNeuralNetwork.MOMENTUM_RATE * this.prevDeltaHiddenBiases[i]);
			this.hiddenBiases[i] += deltaBiasW;
			this.prevDeltaHiddenBiases[i] = deltaBiasW;
		}
	}

	private double[] adjustOutputWeights(double[] actualOutput, double[] targetOutput) {
		double[] deltaK = new double[this.outputs.length];
		for (int i = 0; i < this.outputs.length; i++) {
			double error = targetOutput[i] - actualOutput[i];
			deltaK[i] = actualOutput[i] * (1 - actualOutput[i]) * error;
			for (int j = 0; j < this.hiddens.length; j++) {
				double deltaW = (OneLevelNeuralNetwork.ETA * deltaK[i] * this.hiddens[j]) + (OneLevelNeuralNetwork.MOMENTUM_RATE * this.prevDeltaHiddenToOutputWeights[j][i]);
				this.hiddenToOutputWeights[j][i] += deltaW;
				this.prevDeltaHiddenToOutputWeights[j][i] = deltaW;
			}
			
			double deltaBiasW = (OneLevelNeuralNetwork.ETA * 1.0 * deltaK[i]) + (OneLevelNeuralNetwork.MOMENTUM_RATE * this.prevDeltaOutputBiases[i]);
			this.outputBiases[i] += deltaBiasW;
			this.prevDeltaOutputBiases[i] = deltaBiasW;
		}
		
		return deltaK;
	}
	
	public void backpropagation(double[] targetOutput) {
        double[] deltaK = this.adjustOutputWeights(this.getOutputs(), targetOutput);
        this.adjustHiddenWeights(deltaK);
	}
	
	/**
	 * Creates a matrix of bias weights from one set of nodes to another.
	 * Either initializes them to random values or to zero.
	 * @param numOfLeftNodes
	 * @param numOfRightNodes
	 * @param random
	 * @return
	 */
	private double[][] initializeWeightMatrix(int numOfLeftNodes, int numOfRightNodes, boolean random) {
		double[][] matrix = new double[numOfLeftNodes][numOfRightNodes];
		for (int i = 0; i < numOfLeftNodes; i++) {
			for (int j = 0; j < numOfRightNodes; j++) {
				matrix[i][j] = (random) ? RANGE_MIN + (RANGE_MAX - RANGE_MIN) * RAND.nextDouble() : 0;
			}
		}
		
		return matrix;
	}
	
	/**
	 * Creates an array of bias weights, either randomizing them or initializing
	 * them to zero.
	 * @param len
	 * @param random
	 * @return
	 */
	private double[] initializeBiasWeights(int len, boolean random) {
		double[] biases = new double[len];
		for (int i = 0; i < len; i++) {
			biases[i] = (random) ? RANGE_MIN + (RANGE_MAX - RANGE_MIN) * RAND.nextDouble() : 0;
		}
		
		return biases;
	}
	
	/**
	 * Runs computation of output values from the values currently stored
	 * in inputs.
	 */
	private void compute() {
		//calculate hidden values
		for (int i = 0; i < this.hiddens.length; i++) {
			double total = 1.0 * this.hiddenBiases[i]; //bias weight
			for (int j = 0; j < this.inputs.length; j++) {
				total += this.inputs[j] * this.inputToHiddenWeights[j][i];
			}
			
			this.hiddens[i] = this.sigmoid(total);
		}
		
		//calculate output values
		for (int i = 0; i < this.outputs.length; i++) {
			double total = 1.0 * this.outputBiases[i]; //bias weight
			for (int j = 0; j < this.hiddens.length; j++) {
				total += this.hiddens[j] * this.hiddenToOutputWeights[j][i];
			}
			
			this.outputs[i] = this.sigmoid(total);
		}

	}
	
	/**
	 * Computes the output values for a given set of inputs.
	 * @param inputs
	 * @return
	 */
	public double[] computeOutputs(double[] inputs) {
		if (inputs.length != this.inputs.length) throw new RuntimeException("Invalid number of inputs");
		for (int i = 0; i < inputs.length; i++) this.inputs[i] = inputs[i];
		this.compute();
		
		return this.getOutputs();
	}
	
	public double getError(double[] targetOutputs) {
		if (targetOutputs.length != this.outputs.length) throw new RuntimeException("Invalid number of targetOutputs");
		double error = 0.0;
		for (int i = 0; i < targetOutputs.length; i++) {
			error += Math.pow((targetOutputs[i] - this.outputs[i]), 2);
		}
		
		return error;
	}
	
	/**
	 * Gets the outputs from the last calculation.  Does NOT recompute
	 * calculation of inputs, that is only done through computeOutputs.
	 * @return
	 */
	public double[] getOutputs() {
		double[] outputs = new double[this.outputs.length];
		for (int i = 0; i < this.outputs.length; i++) outputs[i] = this.outputs[i];
		
		return outputs;
	}

	/**
	 * Calculates 1 / (1 + e^(-val))
	 * @param val
	 * @return
	 */
	private double sigmoid(double val) {
		return (1 / (1 + Math.exp(-1 * val)));
	}
	
	public void train(double[] inputs, double[] targetOutput) {
		this.computeOutputs(inputs);
		this.backpropagation(targetOutput);
		this.compute();
	}

	/**
	 * Format:
	 * Line 1: numberOfInputNodes numberOfHiddenNodes numberOfOutputNodes
	 * Next block: All weights coming into a hidden node from the inputs, with 
	 * 				the bias weight being the last number, followed by a blank
	 * 				to signal the end for that hidden node and start of next one.
	 * Next block: Two empty lines, then all weights coming into the output
	 * 				nodes, with the bias weight being the last number followed
	 * 				by a blank line to signal end of output node and start of
	 * 				next one.
	 */
	@Override
	public String toString() {
		String out = this.inputs.length + " " + this.hiddens.length + " " + this.outputs.length + "\n";
		//weights coming into hiddens
		for (int i = 0; i < this.hiddens.length; i++) {
			for (int j = 0; j < this.inputs.length; j++) {
				out += this.inputToHiddenWeights[j][i] + "\n";
			}
			
			out += this.hiddenBiases[i] + "\n\n";
		}
		
		//weights coming into outputs
		out += "\n";
		for (int i = 0; i < this.outputs.length; i++) {
			for (int j = 0; j < this.hiddens.length; j++) {
				out += this.hiddenToOutputWeights[j][i] + "\n";
			}
			
			out += this.outputBiases[i] + "\n\n";
		}
		
		return out;
	}
}
