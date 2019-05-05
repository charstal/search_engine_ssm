package cn.edu.zucc.caviar.searchengine.common.vec.domain;

public class HiddenNeuron extends Neuron{
    
    public double[] syn1 ; //hidden->out
    
    public HiddenNeuron(int layerSize){
        syn1 = new double[layerSize] ;
    }
    
}
