package com.fusion.util;

import java.util.ArrayList;

public class Algorithm {

	public static int operation(ArrayList x1, ArrayList y1, ArrayList x2, ArrayList y2) {
		int[] x = new int[400];
		int[] y = new int[400];
		int[] lx = new int[400];
		int[] ly = new int[400];

		for (int i = 0; i < x1.size(); i++) {
			x[i] = (Integer) x1.get(i);
			y[i] = (Integer) y1.get(i);
		}

		for (int i = 0; i < x2.size(); i++) {
			lx[i] = (Integer) x2.get(i);
			ly[i] = (Integer) y2.get(i);
		}

		double[][] tempdist = new double[400][400];
		tempdist[0][0] = take(lx[0] - x[0], ly[0] - y[0]);

		for (int j = 1; j < x1.size(); j++) {
			tempdist[0][j] = tempdist[0][j - 1] + take(lx[0] - x[j], ly[0] - y[j]);
		}

		for (int j = 1; j < x2.size(); j++) {
			tempdist[j][0] = tempdist[j - 1][0] + take(lx[j] - x[0], ly[j] - y[0]);
		}

		for (int j = 1; j < x2.size(); j++) {
			for (int k = 1; k < x1.size(); k++) {
				tempdist[j][k] = take(lx[j] - x[k], ly[j] - y[k])
						+ Math.min(Math.min(tempdist[j - 1][k], tempdist[j][k - 1]), 2 * tempdist[j - 1][k - 1]);
			}
		}
		if(x1.size()<x2.size())
			return (int)tempdist[x2.size() - 1][x1.size() - 1]/x1.size();
		else
			return (int)tempdist[x2.size() - 1][x1.size() - 1]/x2.size();
	}

	private static double take(int x, int y) {
		return Math.sqrt(x * x + y * y);
	}
}
