package checkers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private Data data;
    private String status;

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", status='" + status + '\'' +
                '}';
    }

    public Data getData() {
        return data;
    }
}
