class Neuron{
    constructor(numberOfInputs, eta=0.1) {
        this.numberOfInputs = numberOfInputs;
        this.eta = eta;
        this.weights = new Array(numberOfInputs).fill().map(() => Math.random()-0.5);
        this.delta = 0;
        this.activation = 0;
        this.sigma = 0;
        this.x = null
    }

    sigmoid = function (x){
        return 1./(1 + Math.exp(-x));
    }

    sigmoidDef = function (){
        return (this.sigma * (1 - this.sigma));
    }

    output = function(x){
        var sum = 0.0;
        for(var i = 0; i < this.numberOfInputs;i++){
            sum += this.weights[i] * parseFloat(x[i]);
        }
        this.sigma = this.sigmoid(sum);
        this.x = sum;
        return this.sigma;
    }

    updateWeights = function (x){
        for(var i = 0; i < this.numberOfInputs;i++){
            this.weights[i] += this.eta * this.delta * parseFloat(x[i]);
        }
    }

    getWeights = function (){
        return this.weights;
    }

}

class Layer{
    constructor(layerSize, prevLayerSize, eta = 0.1) {
        this.layerSize = layerSize;
        this.prevLayerSize = prevLayerSize;
        this.eta = eta;
        this.input = []
        this.neurons = new Array(this.layerSize)
        for(var i = 0; i < this.layerSize; i++){
            this.neurons[i] = new Neuron(this.prevLayerSize,this.eta);
        }
    }

    output = function (x){
        this.input = x
        var result = new Array(this.layerSize);
        for(var i = 0; i < this.layerSize; i++) {
            result[i] = this.neurons[i].output(x)
        }
        return result;
    }

    updateWeights = function (x = this.input){
        for(var i = 0; i < this.layerSize; i++) {
            this.neurons[i].updateWeights(x)
        }
    }
}
/*
* Structure expexted is an array with integers each determining number of neurons in certain layers
* Example 1 4 4 2
* Is 3 layers (2 hidden, 1 input and one output)
* */
class NeuralNetwork{
    constructor(structure,iterations= 2000,eta= 0.1) {
        this.structure = structure;
        this.iterations = iterations;
        this.eta = eta;
        this.output = [];
        this.layers = new Array(this.structure.length);
        this.layers[0] = new Layer(this.structure[0],this.structure[0],this.eta)
        for (var i = 1; i < this.structure.length;i++){
            this.layers[i] = new Layer(this.structure[i], this.structure[i-1], this.eta)
        }
        this.error = new Array(2)
    }

    forward = function (x){
        var i = 0;
        var input = x
        for (i = 0; i < this.structure.length-1;i++){
            input = this.layers[i].output(input);
        }
        this.output = this.layers[i].output(input);

    }

    updateWeights = function (x){
        var input = x
        for (var i = 0; i < this.structure.length;i++){
            this.layers[i].updateWeights(input);
            input = this.layers[i].output(input);
        }
    }

    backward = function (output){
        var last_layer = this.layers.length-1;
        var epsilon = 0;
        // Zaczynamy od ostatniej warstwy
        for (var i = 0; i < this.layers[last_layer].layerSize; i++){
            epsilon += parseFloat(output[i]) - this.output[i];
            this.layers[last_layer].neurons[i].delta = epsilon * this.layers[last_layer].neurons[i].sigmoidDef()
        }
        // Teraz będziemy propagować nasze delty wstecz
        for(var i = last_layer-1;i >= 0; i--){
            for (var j = 0; j < this.layers[i].layerSize;j++){
                epsilon = 0
                // Liczymy epsilon na podstawie poprzedniej warstwy
                for(var k = 0; k < this.layers[i+1].layerSize;k++){
                    epsilon += this.layers[i+1].neurons[k].weights[j] * this.layers[i+1].neurons[k].delta
                }
                // Przekazujemy delte wstecz
                this.layers[i].neurons[j].delta = epsilon * this.layers[i].neurons[j].sigmoidDef()
            }
        }
    }

    train = function (dataX, dataY){
        for (var i = 0; i < this.iterations;i++){
            var index = parseInt(Math.random() * dataX.length)
            var x = dataX[index];
            var y = dataY[index];
            this.forward(x);
            this.backward(y);
            //document.write("ERROR: " + (parseFloat(y[0])-this.output[0]) + "/" + (parseFloat(y[1])-this.output[1]) + "<br>")
            this.updateWeights(x);
        }
    }
}

var input_data = [];
var output_data = [];
var learning_input = new Array(2000);
var learning_output = new Array(2000);
var test_input = new Array(160);
var test_output = new Array(160);


document.getElementById('inputfile_1').addEventListener('change', function() {
    var fr=new FileReader();
    fr.onload=function(){
        input_data = fr.result.split("\n");
    }
    fr.readAsText(this.files[0]);
})

document.getElementById('inputfile_2').addEventListener('change', function() {
    var fr=new FileReader();
    fr.onload=function(){
        output_data = fr.result.split("\n");
    }
    fr.readAsText(this.files[0]);
})

launch = function (){
    for (var i = 0; i < input_data.length;i++){
        if(i >= 160){
            learning_input[i-160] = input_data[i-160].split(',');
            learning_output[i-160] = output_data[i-160].split(',');
        }
        else{
            test_input[i] = input_data[i].split(',');
            test_output[i] = output_data[i].split(',');
        }
    }
    let N = new NeuralNetwork([16, 20, 10, 4, 2], 2000, 0.01);
    N.train(learning_input, learning_output)
    for (var j = 0; j < test_input.length; j++) {
        N.forward(test_input[j])
        document.write("Network output: " + N.output +
            "      Expected output: " + parseFloat(test_output[j][0]) +
            ", " + parseFloat(test_output[j][1]) + " " +
            "<br>")
    }
}