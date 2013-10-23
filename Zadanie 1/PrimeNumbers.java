/*
 *
 * @author Mateusz Zajac
 * KrDZIs3011Io
 * 23.10.2013
 *
 */

import java.util.*;

class PrimeNumbers {
	public static void main(String[] args) {
		// Creating a group of threads
		Thread[] threads = new Thread[3];
		
		// Initializing those threads and firing them
		threads[0] = new ConcurrentPrimesThread(1, Sieve.getPrimesList(1000000, 2000000)); 
		threads[1] = new ConcurrentPrimesThread(2, Sieve.getPrimesList(2000000, 3000000)); 
		threads[2] = new ConcurrentPrimesThread(3, Sieve.getPrimesList(3000000, 4000000)); 
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		// Calling join() method on each thread to waint for them to end everything
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (Exception e) {
				System.out.println("Ouch");
			}
		}	
		
		// Printing author after completion all the other threads tasks
		System.out.println("Creator: Mateusz Zajac, KrDZIs3011Io");
	}
}

// Custom thread class
class ConcurrentPrimesThread extends Thread {
	private int threadNumber;
	ArrayList<Integer> primes;
	private Random randomGenerator;
	
	// Constructor
	public ConcurrentPrimesThread(int no, ArrayList<Integer> primeNumbers) {
		this.threadNumber = no;
		this.primes = primeNumbers;
		randomGenerator = new Random();
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				// Getting random prime number from ArrayList
				int index = randomGenerator.nextInt(primes.size());
				System.out.println("Thread no." + threadNumber + ": " + primes.get(index));
			} catch(Exception e) {
				System.out.println("Ups, something went wrong...");
			}
		}
	}
}

// Simple class with one class method (modified getter).
class Sieve {
	// This method is a implementation of Erasthothenes sieve from polish Wikipedia
	// http://pl.wikipedia.org/wiki/Sito_Eratostenesa
	// because I don't want to reinvent the wheel. Only changed thing is the return
	// In result we get an ArrayList with prime numbers
	public static ArrayList<Integer> getPrimesList(int minValue, int maxValue) {
		boolean[] numbersTable = new boolean[maxValue + 1];
		int size = maxValue - minValue;
		ArrayList<Integer> numbers = new ArrayList<>(size);
	
		// Here is the deal with sieving
		for (int i = 2; i * i <= maxValue; i++) {
			if (numbersTable[i]) {
				continue;
			}
	
			for (int j = 2 * i; j <= maxValue; j += i) {
				numbersTable[j] = true;
			}
		}

		// Here we are checking for primes and adding them at the end of ArrayList
		for (int i = 2; i <= maxValue; i++) {
			if (!numbersTable[i]) {
				if (i > minValue) {
					numbers.add(i);
				}
			}
		}
		
		return numbers;
	}
}