
public class QLearner {

    private QCell[][] qTable;
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
        qTable = new QCell[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++)
        {
            for (int col = 0; col < gridSize; col++)
            {
                qTable[row][col] = new QCell(initValue);
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
            System.out.print("[");
            for (int col = 0; col < gridSize; col++)
            {
                QCell cell = qTable[row][col];
                cell.printCellInformation();
            }
            System.out.print("]");
            System.out.println();

        }
    }
}
