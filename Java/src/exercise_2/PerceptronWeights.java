package exercise_2;

public class PerceptronWeights {
    double[] weights;
    int lifetime;

    public PerceptronWeights(double[] weights, int lifetime) {
        this.weights = weights;
        this.lifetime = lifetime;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
