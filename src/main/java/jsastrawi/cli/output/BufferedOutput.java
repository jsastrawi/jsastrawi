package jsastrawi.cli.output;

public class BufferedOutput implements Output {

    private final StringBuilder buffer = new StringBuilder();
    
    @Override
    public void println(String x) {
        buffer.append(x);
        buffer.append("\n");
    }

    @Override
    public void print(String x) {
        buffer.append(x);
    }
    
    public String toString() {
        return buffer.toString();
    }
}
