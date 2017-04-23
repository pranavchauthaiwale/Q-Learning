import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QCell {
    private double up;
    private double down;
    private double left;
    private double right;
    private double maximumExpected;
    private List<Action> maxRewardActions;
    private List<Action> randomActions;
    private Random randomGenerator;

    private static List<Action> populateRewardsActionsList()
    {
        List<Action> maxList = new ArrayList<Action>();
        maxList.add(Action.MOVE_RIGHT);
        maxList.add(Action.MOVE_LEFT);
        maxList.add(Action.MOVE_UP);
        maxList.add(Action.MOVE_DOWN);
        return maxList;
    }

    public QCell(double initValue)
    {
        this.up = initValue;
        this.down = initValue;
        this.left = initValue;
        this.right = initValue;
        this.maximumExpected = initValue;
        this.maxRewardActions = populateRewardsActionsList();
        this.randomActions = populateRewardsActionsList();
        randomGenerator = new Random();
    }

    public void setUp(double up) {
        this.up = up;
        tryUpdateMaxRewardActionList();
    }

    public void setDown(double down) {
        this.down = down;
        tryUpdateMaxRewardActionList();
    }

    public void setLeft(double left) {
        this.left = left;
        tryUpdateMaxRewardActionList();
    }

    public void setRight(double right) {
        this.right = right;
        tryUpdateMaxRewardActionList();
    }

    public double getMaximumExpected() {
        return maximumExpected;
    }

    public double getAttributeValue(Action action)
    {
        double attributeValue = 0;
        switch (action){
            case MOVE_LEFT: attributeValue = this.left;
                            break;
            case MOVE_RIGHT:attributeValue = this.right;
                            break;
            case MOVE_UP:   attributeValue = this.up;
                            break;
            case MOVE_DOWN: attributeValue = this.down;
                            break;
        }
        return attributeValue;
    }

    public Action getNextAction(boolean randomAction) throws Exception {
        if (randomAction) {
            return randomActions.get(randomGenerator.nextInt(4));
        }
        if (maxRewardActions.size() == 0) {
            throw new Exception("Actions List Empty");
        }
        if (maxRewardActions.size() == 1) {
            System.out.print("List Size:" + maxRewardActions.size() + " Max: " + maximumExpected + " ");
            return maxRewardActions.get(0);
        }
        System.out.print("List Size:" + maxRewardActions.size() + " Max: " + maximumExpected + " ");
        return maxRewardActions.get(new Random().nextInt(maxRewardActions.size()));
    }

    public void printCellInformation()
    {
        System.out.print(" {" + up + ", " + right + ", " + down + ", " + left + "} ");
    }

    private void tryUpdateMaxRewardActionList()
    {
        maximumExpected = findMaximum();
        maxRewardActions.clear();
        if (up == maximumExpected){
            maxRewardActions.add(Action.MOVE_UP);
        }
        if (down == maximumExpected){
            maxRewardActions.add(Action.MOVE_DOWN);
        }
        if (left == maximumExpected){
            maxRewardActions.add(Action.MOVE_LEFT);
        }
        if (right == maximumExpected){
            maxRewardActions.add(Action.MOVE_RIGHT);
        }
    }

    private double findMaximum(){

        double maximum = up;
        if (right > maximum){
            maximum = right;
        }
        if (down >  maximum){
            maximum = down;
        }
        if (left > maximum) {
            maximum = left;
        }
        return maximum;
    }

}
