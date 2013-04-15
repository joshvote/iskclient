package au.csiro.iskclient.services.responses;

public class DbImageResult {
    public DbImageResult() {
        super();
    }

    private Double score;
    private int databaseId;
    private int id;

    public DbImageResult(Integer databaseId, Integer id, Double r) {
        super();
        this.id = id;
        this.databaseId = databaseId;
        this.setScore(r);
    }

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

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }


}
