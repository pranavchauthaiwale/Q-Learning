
public class QLearner {

    private int[][] gridWorld;
    private int initValue;
    private int gridSize;

    QLearner(int gridSize, int initValue)
    {
        this.gridSize = gridSize;
        this.initValue = initValue;
        initializeGridWorld();
    }

    private void initializeGridWorld()
    {
        gridWorld = new int[gridSize][gridSize];
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

    public void printGridWorld()
    {
        for (int row = 0; row < gridSize; row++)
        {
            System.out.print("[ ");
            for (int col = 0; col < gridSize; col++)
            {
                System.out.print(gridWorld[row][col] + " ");
            }
            System.out.print("]");
            System.out.println();

        }
    }
}
