
public class QLearning {

    public static void main(String[] args)
    {
        int gridSize = Constants.GRID_SIZE;
        int initValue = 0;

        QLearner learner = new QLearner(gridSize, initValue);

        learner.learn();
    }
}
