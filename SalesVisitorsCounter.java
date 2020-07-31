package test;

public class SalesVisitorsCounter {

	/**
	 * 
	 * @param n
	 * @param k
	 * @param numberOfHouses
	 * @return int - count of salesman as per k
	 */
	public static int numberOfVisitors(int n, int k, int[][] numberOfHouses) {

		int[][] houses = new int[110][110]; // each entry (i,j) in this matrix
											// denotes a house.
		int count = 0;

		for (int i = 0; i < numberOfHouses.length; i++) {
			int[] v = numberOfHouses[i];

			for (int x = v[0]; x <= v[2]; x++) {
				for (int y = v[1]; y <= v[3]; y++) {
					houses[x][y]++; // increment the count for salesman region.
				}
			}
		}

		for (int x = 0; x <= 109; x++) {
			for (int y = 0; y <= 109; y++) {
				if (houses[x][y] >= k) {
					count++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println("Test case 1");
		int[][] regionBoundary = { { 0, 0, 4, 4 }, { 1, 1, 2, 5 } };
		System.out.println(numberOfVisitors(2, 1, regionBoundary));
		System.out.println("===================================");

		System.out.println("test case 2");
		int[][] regionBoundary1 = { { 0, 0, 4, 4 }, { 1, 1, 2, 5 } };
		System.out.println(numberOfVisitors(2, 2, regionBoundary1));
		System.out.println("===================================");
	}
}
