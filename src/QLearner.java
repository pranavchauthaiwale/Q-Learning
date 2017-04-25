import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class QLearner {

    private QCell[][] qTable;
    private int initValue;
    private int gridSize;
    private int reward;
    private int episodes;
    private int currentStateRow;
    private int currentStateCol;
    private int previousStateRow;
    private int previousStateCol;
    private double learningRate;
    private double discountFactor;
    private double explorationProbability;
    private Random randomGenerator;
    private GraphHelper graphHelper;

    QLearner(int gridSize, int initValue)
    {
        this.gridSize = gridSize;
        this.initValue = initValue;
        this.randomGenerator = new Random();
        populateConstants();
        initializeGridWorld();
    }

    public void learn()
    {
        int counter;
        System.out.println("Q-Learning");
        Map<Integer, Integer> totalSteps = new LinkedHashMap<>();
        printCurrentState();
        System.out.println();

        for (int i = 0; i < episodes; i++) {
            counter = 0;
            initializeAgent();
            while (!isGoalAchieved()) {
                moveAgent();
                printGridWorld();
                counter++;
            }
            System.out.println("Total Steps taken to reach the end state: " + counter);
            totalSteps.put(i+1, counter);
        }
        plotStepsPerEpisodeGraph(totalSteps);

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

    private void populateConstants()
    {
        this.learningRate = Constants.LEARNING_RATE;
        this.discountFactor = Constants.DISCOUNT_FACTOR;
        this.explorationProbability = Constants.EXPLORATION_PROBABILITY;
        this.episodes = Constants.EPISODES;
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
            double randomness = ((double)randomGenerator.nextInt(100)) / 100;
            Action action = cell.getNextAction(randomness >= explorationProbability);
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
            return -2;
        }
        return 2;
    }

    private void printCurrentState()
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

    private void plotStepsPerEpisodeGraph(Map<Integer, Integer> totalSteps)
    {
        for (int i : totalSteps.keySet()){
            System.out.println("Episode : " + i + " Steps : " + totalSteps.get(i));
        }

        graphHelper = new GraphHelper(totalSteps);
        graphHelper.plotGraph();
        graphHelper.setVisible(true);
    }
}
