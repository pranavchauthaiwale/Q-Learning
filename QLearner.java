
public class QLearner {

    private int[][] gridWorld;
    private int initValue;

    QLearner(int gridSize, int initValue)
    {
        gridWorld = new int[gridSize][gridSize];
        this.initValue = initValue;
        initializeGridWorld(this.initValue);
    }

    private void initializeGridWorld(int initValue)
    {
        int gridSize = Constants.GRID_SIZE;
        for (int row = 0; row < gridSize; row++)
        {
            for (int col = 0; col < gridSize; col++)
            {
                gridWorld[row][col] = initValue;
            }
        }
    }

    public void learn()
    {
        System.out.println("Q-Learning");
    }
}
