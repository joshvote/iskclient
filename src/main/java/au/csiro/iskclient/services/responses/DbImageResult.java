package au.csiro.iskclient.services.responses;

public class DbImageResult {
    public DbImageResult() {
        super();
    }

    private Double score;

    public DbImageResult(Integer id, Double r) {
        super();
        this.id = id;
        this.setScore(r);
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Double getScore() {
        return score;
    }

    public void setScore(Double r) {
        this.score = r;
    }
}
