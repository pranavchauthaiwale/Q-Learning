
public class QLearner {

    private QCell[][] qTable;
    private int initValue;
    private int gridSize;
    private int currentStateRow;
    private int currentStateCol;
    private int previousStateRow;
    private int previousStateCol;
    private int reward;
    private double learningRate;
    private double discountFactor;

    QLearner(int gridSize, int initValue)
    {
        this.gridSize = gridSize;
        this.initValue = initValue;
        this.learningRate = Constants.LEARNING_RATE;
        this.discountFactor = Constants.DISCOUNT_FACTOR;
        initializeGridWorld();
    }

    public void learn()
    {
        System.out.println("Q-Learning");
        initializeAgent();
        int episodes = Constants.EPISODES;
        printCurrentState();
        System.out.println();
        int counter = 0;
        for (int i = 0; i < 250; i++){
            //while (!isGoalAchieved()){
                moveAgent();
                printGridWorld();
                counter++;
            }

        //}
        System.out.println("Total Steps taken to reach the end state: " + counter);
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

    private void initializeAgent()
    {
        currentStateRow = 0;
        currentStateCol = 0;
        previousStateRow = 0;
        previousStateCol = 0;
    }

    private boolean isGoalAchieved()
    {
        return (currentStateRow == gridSize - 1) && (currentStateCol == gridSize - 1);
    }

    private void moveAgent()
    {
        QCell cell = qTable[currentStateRow][currentStateCol];

        try
        {
            Action action = cell.getNextAction();
            switch (action)
            {
                case MOVE_UP:
                    moveAgentUp();
                    break;
                case MOVE_RIGHT:
                    moveAgentRight();
                    break;
                case MOVE_DOWN:
                    moveAgentDown();
                    break;
                case MOVE_LEFT:
                    moveAgentLeft();
                    break;
            }
        }
        catch (Exception exception) {
            System.out.println("Exception occurred: " + exception.getMessage());
        }
    }

    private void moveAgentRight() {
        System.out.print(Action.MOVE_RIGHT);
        updatePreviousState();
        if (currentStateCol != gridSize - 1) {
            currentStateCol++;
        }
        printCurrentState();
        reward = getReward();
        System.out.println(" Reward: " + reward);
        updateQValue(Action.MOVE_RIGHT, reward);
    }

    private void moveAgentLeft() {
        System.out.print(Action.MOVE_LEFT);
        updatePreviousState();
        if (currentStateCol != 0){
            currentStateCol--;
        }
        printCurrentState();
        reward = getReward();
        System.out.println(" Reward: " + reward);
        updateQValue(Action.MOVE_LEFT, reward);
    }

    private void moveAgentUp() {
        System.out.print(Action.MOVE_UP);
        updatePreviousState();
        if (currentStateRow != 0){
            currentStateRow--;
        }
        printCurrentState();
        reward = getReward();
        System.out.println(" Reward: " + reward);
        updateQValue(Action.MOVE_UP, reward);
    }

    private void moveAgentDown() {
        System.out.print(Action.MOVE_DOWN);
        updatePreviousState();
        if (currentStateRow != gridSize - 1){
            currentStateRow++;
        }
        printCurrentState();
        reward = getReward();
        System.out.println(" Reward: " + reward);
        updateQValue(Action.MOVE_DOWN, reward);
    }

    private void updatePreviousState() {
        previousStateCol = currentStateCol;
        previousStateRow = currentStateRow;
    }

    private int getReward()
    {
        if(isGoalAchieved()) {
            return 100;
        }
        if ((previousStateRow == currentStateRow) && (previousStateCol == currentStateCol)) {
            return -1;
        }
        return 1;
    }

    public void printCurrentState()
    {
        System.out.print(" " + currentStateRow);
        System.out.print(", " + currentStateCol);
    }

    private void updateQValue(Action actionTaken, int reward)
    {
        double newMaximum;
        double previousStateAttribute;
        double newValue;
        QCell newState = qTable[currentStateRow][currentStateCol];
        QCell previousState = qTable[previousStateRow][previousStateCol];

        newMaximum = newState.getMaximumExpected();
        previousStateAttribute = previousState.getAttributeValue(actionTaken);
        newValue = previousStateAttribute + learningRate * (reward + (discountFactor * newMaximum) - previousStateAttribute);

        switch (actionTaken){
            case MOVE_LEFT: previousState.setLeft(newValue);
                            break;
            case MOVE_RIGHT:previousState.setRight(newValue);
                            break;
            case MOVE_UP:   previousState.setUp(newValue);
                            break;
            case MOVE_DOWN: previousState.setDown(newValue);
                            break;
        }
    }
}
