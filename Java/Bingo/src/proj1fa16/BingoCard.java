package proj1fa16;

public class BingoCard extends BallCage {
	int[][] nums = new int[5][5];
	int[][] taken = new int[5][5];
	
	public BingoCard() {
	for (int i = 0; i < nums.length; i++) {
		nums[i][i] = 10;
		}
	}
}


