package checkers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private List<GameBoardPiece> board;
    private boolean is_started;
    private boolean is_finished;
    private double available_time;
    private String status;
    private String winner;
    private String whose_turn;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinner() {
        return winner;
    }

    public void setIs_started(boolean is_started) {
        this.is_started = is_started;
    }

    public void setAvailable_time(double available_time) {
        this.available_time = available_time;
    }

    public List<GameBoardPiece> getBoard() {
        return board;
    }

    public void setBoard(List<GameBoardPiece> board) {
        this.board = board;
    }

    public boolean isIs_finished() {
        return is_finished;
    }

    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    public double getAvailable_time() {
        return available_time;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWhose_turn() {
        return whose_turn;
    }

    public void setWhose_turn(String whose_turn) {
        this.whose_turn = whose_turn;
    }

    public boolean isIs_started() {
        return is_started;
    }

    @Override
    public String toString() {
        return "Data{" +
                "status='" + status + '\'' +
                ", winner='" + winner + '\'' +
                ", whose_turn='" + whose_turn + '\'' +
                ", is_started=" + is_started +
                ", is_finished=" + is_finished +
                ", available_time=" + available_time +
                ", board=" + board +
                '}';
    }
}

