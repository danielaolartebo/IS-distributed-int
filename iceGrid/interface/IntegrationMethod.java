public interface IntegrationMethod {
    double integrateTrapezium(DoubleFunction<Double> f, double lowerBound, double upperBound, int numIntervals);

    double integrateSimpson(DoubleFunction<Double> f, double lowerBound, double upperBound, int numIntervals);

    double montecarlo(DoubleFunction<Double> f, double lowerBound, double upperBound);
}