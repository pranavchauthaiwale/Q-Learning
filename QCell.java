import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QCell {
    private int up;
    private int down;
    private int left;
    private int right;
    private int maximumExpected;
    private List<Action> maxRewardActions;

    private static List<Action> populateRewardsActionsList()
    {
        List<Action> maxList = new ArrayList<Action>();
        maxList.add(Action.MOVE_RIGHT);
        maxList.add(Action.MOVE_LEFT);
        maxList.add(Action.MOVE_UP);
        maxList.add(Action.MOVE_DOWN);
        return maxList;
    }

    public QCell(int initValue)
    {
        this.up = initValue;
        this.down = initValue;
        this.left = initValue;
        this.right = initValue;
        this.maximumExpected = initValue;
        this.maxRewardActions = populateRewardsActionsList();
    }

    public void setUp(int up) {
        if (up > maximumExpected) {
            maximumExpected = up;
            tryUpdateMaxRewardActionList();
        }
        this.up = up;
    }

    public void setDown(int down) {
        if(down > maximumExpected) {
            maximumExpected = down;
            tryUpdateMaxRewardActionList();
        }
        this.down = down;
    }

    public void setLeft(int left) {
        if (left > maximumExpected) {
            maximumExpected = left;
            tryUpdateMaxRewardActionList();
        }
        this.left = left;
    }

    public void setRight(int right) {
        if (right > maximumExpected) {
            maximumExpected = right;
            tryUpdateMaxRewardActionList();
        }
        this.right = right;
    }

    private void tryUpdateMaxRewardActionList()
    {
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

    public Action getNextAction() throws Exception {
        if (maxRewardActions == null)
        {
            throw new Exception("Actions List Empty");
        }
        if (maxRewardActions.size() == 1)
        {
            return maxRewardActions.get(0);
        }

        return maxRewardActions.get(new Random().nextInt(maxRewardActions.size()));
    }

    public void printCellInformation()
    {
        System.out.print(" {" + up + ", " + right + ", " + down + ", " + left + "} ");
    }
}
