package au.csiro.iskclient.services.responses;

public class DbImageResult {
    public DbImageResult() {
        super();
    }

    private Double score;

    public DbImageResult(Integer id, Double r, String url) {
        super();
        this.id = id;
        this.setScore(r);
        this.url = url;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double r) {
        this.score = r;
    }

    private String url;
}
