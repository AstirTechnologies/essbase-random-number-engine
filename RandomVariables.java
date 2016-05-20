package com.astir.essbase.random;
/**
 * @author dhagell
 * This package brings together CERN high performance scientific computing library named Colt and Oracle Essbase.  
 * Featuring the MersenneTwister pseudo-random number generator provides uniform random numbers along 
 * any number of distributions included.
 */
/**
* MersenneTwister (MT19937) is one of the strongest uniform pseudo-random number generators known so far; 
* at the same time it is quick. Produces uniformly distributed int's and long's in the closed intervals 
* [Integer.MIN_VALUE,Integer.MAX_VALUE] and [Long.MIN_VALUE,Long.MAX_VALUE], respectively, 
* as well as float's and double's in the open unit intervals (0.0f,1.0f) and (0.0,1.0), respectively. 
* The seed can be any 32-bit integer except 0. Shawn J. Cokus commented that perhaps the seed should preferably be odd.
* 
* Quality: MersenneTwister is designed to pass the k-distribution test. 
* It has an astronomically large period of 219937-1 (=106001) and 623-dimensional 
* equidistribution up to 32-bit accuracy. It passes many stringent statistical tests, 
* including the diehard test of G. Marsaglia and the load test of P. Hellekalek and S. Wegenkittl.
*/
/**
* Colt Library
* Open Source Libraries for High Performance Scientific and Technical Computing
* http://acs.lbl.gov/software/colt/
* Version 1.2.0
* License: Packages cern.colt* , cern.jet*, cern.clhep
* Copyright (c) 1999 CERN - European Organization for Nuclear Research.
* Permission to use, copy, modify, distribute and sell this software and its documentation
* for any purpose is hereby granted without fee, provided that the above copyright notice appear
* in all copies and that both that copyright notice and this permission notice appear in supporting
* documentation. CERN makes no representations about the suitability of this software for any purpose.
* It is provided "as is" without expressed or implied warranty.
*/
import java.text.DecimalFormat;
import cern.colt.list.IntArrayList;
import cern.jet.*;
import cern.jet.random.Distributions;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class RandomVariables {
	// Methods operate on a user or system supplied uniform random number generator; they are unsynchronized.
	// for tests and debugging use a random engine with CONSTANT seed --> deterministic and reproducible results
	static cern.jet.random.engine.RandomEngine engine = null; 
	static cern.jet.random.AbstractDistribution dist = null;
	static int seed = (int) Calendar.getInstance().getTimeInMillis();

	/*Other engines*/
	 //generator = new edu.cornell.lassp.houle.RngPack.Ranecu(new java.util.Date());
	 //generator = new edu.cornell.lassp.houle.RngPack.Ranmar(new java.util.Date());
	 //generator = new edu.cornell.lassp.houle.RngPack.Ranlux(new java.util.Date());
    public RandomVariables() {
        super();
    	engine = new cern.jet.random.engine.MersenneTwister64(seed); 
    }  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Gamma distribution
		// define distribution parameters
		//n - the number of trials (also known as sample size).
    	//p - the probability of success.
		double mean = 0.5;
		double variance = 0.25;
		double alpha = mean*mean / variance; 
		double lambda = 1 / (variance / mean); 
		int n = 1;
		double p = 0.5;

		// for tests and debugging use a random engine with CONSTANT seed --> deterministic and reproducible results
		cern.jet.random.engine.RandomEngine engine = new cern.jet.random.engine.MersenneTwister64(seed); 

		// your favourite distribution goes here
		//cern.jet.random.AbstractDistribution dist = new cern.jet.random.Gamma(alpha,lambda,engine);
		cern.jet.random.AbstractDistribution dist = new cern.jet.random.Normal(mean, variance,engine);
		cern.jet.random.AbstractDistribution normdist = new cern.jet.random.Normal(mean, variance,engine);
		cern.jet.random.AbstractDistribution bdist = new cern.jet.random.Binomial(n, p,engine);

		// collect random numbers and print statistics
		int size = 1000;
		cern.colt.list.DoubleArrayList numbers = new cern.colt.list.DoubleArrayList(size);
		IntArrayList wholenumbers = new cern.colt.list.IntArrayList(size);
		for (int i=0; i < size; i++) { 
			numbers.add(dist.nextDouble());
			wholenumbers.add(bdist.nextInt());

		}

		hep.aida.bin.DynamicBin1D bin = new hep.aida.bin.DynamicBin1D();

		bin.addAllOf(numbers);

		System.out.println(bin);
		System.out.println(numbers);
		System.out.println(wholenumbers);
	}
    public static double seedEngine(double fseed) {
    	seed = new Double(fseed).intValue();
    	engine = new cern.jet.random.engine.MersenneTwister64(seed); 
    	return seed;
    }  
    public static double getSeed(double seedTarget) {
    	seedTarget = seed;
    	return seedTarget;

    }  
    
    public static double[] nextInt(double[] target) {
 		// collect random numbers and return to essbase
     	if(dist == null)
    		for (int i=0; i < target.length; i++) { 
    			target[i] = engine.nextInt();
    		}
     	else
    		for (int i=0; i < target.length; i++) { 
    			target[i] = dist.nextInt();
    		}
     	return target;
     } 
     public static double[] nextDouble(double[] target) {
 		// collect random numbers and return to essbase
     	if(dist == null)
    		for (int i=0; i < target.length; i++) { 
    			target[i] = engine.nextDouble();
    		}
     	else
    		for (int i=0; i < target.length; i++) { 
    			target[i] = dist.nextDouble();
    		}
     	return target;
     } 
     public static double getRaw() {
 		// collect random numbers and return to essbase
 		return engine.raw();
     }    
     public static double[] getRaw(double[] target) {
 		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = engine.raw();
		}
		return target;
     }  
     public static double[] nextGamma(double mean,double variance,double[] target) {
		// Gamma distribution
		// define distribution parameters
    	// p(x) = k * x^(alpha-1) * e^(-x/beta) with k = 1/(g(alpha) * b^a)) and g(a) being the gamma function.
    	// Valid parameter ranges: alpha > 0.
    	// Note: For a Gamma distribution to have the mean mean and variance variance, set the parameters as follows:
    	// alpha = mean*mean / variance; lambda = 1 / (variance / mean); 
    	//example:
    	//double mean = 10;
		//double variance = 1.5;
    	 
		double alpha = mean*mean / variance; 
		double lambda = 1 / (variance / mean); 
		//
		// construct a distribution and return the first value
		dist = new cern.jet.random.Gamma(alpha,lambda,engine);
 		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = dist.nextDouble();
		}
		return target;
    }   
         
    public static double[] nextNormal(double mean,double variance,double[] target) {
		/* Normal (aka Gaussian) distribution; See the math definition and animated definition.
		*        
		*         1                       2
		* pdf(x) = ---------    exp( - (x-mean) / 2v ) 
		* sqrt(2pi*v)
		* 
		*                              x
		*                               -
		*         1        | |                 2
		* cdf(x) = ---------    |    exp( - (t-mean) / 2v ) dt
		* sqrt(2pi*v)| |
		*                         -
		*                        -inf.
		* where v = variance = standardDeviation^2.
		*/
    	// construct a distribution and return the first value
		dist = new cern.jet.random.Normal(mean, variance,engine);
 		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = dist.nextDouble();
		}
		return target;
    }   
    public static double[] nextBinomial(int n,double p,double[] target) {
    	// p(x) = k * p^k * (1-p)^(n-k) with k = n! / (k! * (n-k)!).
    	//n - the number of trials (also known as sample size).
    	//p - the probability of success.
		dist = new cern.jet.random.Binomial(n, p,engine);
 		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = dist.nextDouble();
		}
		return target;
    }   
     public static double[] getGeometricPdf(int k, double p,double[] target) {
	 // collect random numbers and return to essbase
	 //k - the argument to the probability distribution function.
	 //p - the parameter of the probability distribution function.
    	 for (int i=0; i < target.length; i++) { 
 		    //Returns the probability distribution function of the discrete geometric distribution.
 			target[i] = Distributions.geometricPdf(k, p);
 		}
 		return target;
     } 
   
    public static double[] nextBurr1(double r, int nr,double[] target) { 
    //Returns a random number from the Burr II, VII, VIII, X Distributions.
    	//r - must be > 0.
    	//nr - the number of the burr distribution (e.g. 2,7,8,10).
    	// collect random numbers and return to essbase
 		for (int i=0; i < target.length; i++) { 
 		    //Returns the probability distribution function of the discrete geometric distribution.
 			target[i] = Distributions.nextBurr1(r, nr, engine);
 		}
 		return target;

    }   
    public static double[] nextBurr2(double r, double k, int nr,double[] target) { 
    // Returns a random number from the Burr III, IV, V, VI, IX, XII distributions.
    	//r - must be > 0.
    	//k - must be > 0.
    	//nr - the number of the burr distribution (e.g. 3,4,5,6,9,12).
    	// collect random numbers and return to essbase
 		for (int i=0; i < target.length; i++) { 
 		    //Returns the probability distribution function of the discrete geometric distribution.
 			target[i] = Distributions.nextBurr2(r, k, nr, engine);
 		}
 		return target;
    }
    public static double[] nextCauchy(double[] target) { 
    	// Returns a cauchy distributed random number from the standard Cauchy distribution C(0,1).
		// p(x) = 1/ (mean*pi * (1+(x/mean)^2)).
 		// collect random numbers and return to essbase
    	for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextCauchy(engine);
		}
		return target;

	}
    public static double[] nextErlang(double variance, double mean,double[] target) { 
    // Returns an erlang distributed random number with the given variance and mean.
 		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextErlang(variance, mean, engine);
		}
		return target;
    }
    public static double[] nextGeometric(double p,double[] target) { 
    // Returns a discrete geometric distributed random number; Definition.
    // p(k) = p * (1-p)^k for k >= 0.
    // p - must satisfy 0 < p < 1.
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextGeometric(p, engine);
		}
		return target;
    }
    public static double[] nextLambda(double lambda13, double lambda14,double[] target) { 
    // Returns a lambda distributed random number with parameters l3 and l4.
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextLambda(lambda13,lambda14,engine);
		}
		return target;
    }
    public static double[] nextLaplace(double[] target) { 
    // Returns a Laplace (Double Exponential) distributed random number from the standard Laplace distribution L(0,1).
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextLaplace(engine);
		}
		return target;
    }
    public static double[] nextLogistic(double[] target) { 
    // Returns a random number from the standard Logistic distribution Log(0,1).
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextLogistic(engine);
		}
		return target;
    }
    public static double[] nextPowLaw(double alpha, double cut,double[] target) { 
    // Returns a power-law distributed random number with the given exponent and lower cutoff.
    // alpha - the exponent
    // cut - the lower cutoff
    	// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextPowLaw(alpha, cut, engine);
		}
		return target;
    }
    public static double[] nextTriangular(double[] target) { 
    // Returns a random number from the standard Triangular distribution in (-1,1).
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextTriangular(engine);
		}
		return target;
    }    
    public static double[] nextWeibull(double alpha, double beta,double[] target)  {
    // Returns a weibull distributed random number.
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextWeibull(alpha, beta, engine);
		}
		return target;
    }    
    public static double[] nextZipfInt(double z,double[] target)  {
    // Returns a zipfian distributed random number with the given skew.
    // z - the skew of the distribution (must be >1.0).
    // Algorithm from page 551 of: Devroye, Luc (1986) `Non-uniform random variate generation', Springer-Verlag: Berlin. ISBN 3-540-96305-7 (also 0-387-96305-7)
		// collect random numbers and return to essbase
		for (int i=0; i < target.length; i++) { 
			target[i] = Distributions.nextZipfInt(z, engine);
		}
		return target;
    }
    
    
}
