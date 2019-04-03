package hello;

public class Job extends AbstractJob {

    private String name;
    private int status;

    public Job() {
    }

    public Job(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }
}
