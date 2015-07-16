package jsastrawi.cli.output;

public class SystemOutput implements Output {

    @Override
    public void println(String x) {
        System.out.println(x);
    }

    @Override
    public void print(String x) {
        System.out.print(x);
    }
    
}
