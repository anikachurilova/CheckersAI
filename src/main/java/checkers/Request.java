package checkers;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private List<Integer> step;

    public Request(List<Integer> step) {
        this.step = step;
    }

    public Request(Step step) {
        this.step = new ArrayList<>();
        this.step.add(step.returnChangedPos());
        this.step.add(step.returnChangedPosTo());
    }
    public List<Integer> getMove() {
        return step;
    }
    public void setMove(List<Integer> move) {
        this.step = move;
    }

    @Override
    public String toString() {
        return "Request{" +
                "move=" + step +
                '}';
    }
}