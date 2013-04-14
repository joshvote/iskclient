package au.csiro.iskclient.view;

import java.io.Serializable;

public class Image implements Serializable {
    private String url;
    private Double rating;
    private Integer id;


    public Image(Integer id, String url, Double rating) {
        super();
        this.id = id;
        this.url = url;
        this.rating = rating;
    }



    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public Double getRating() {
        return rating;
    }


    public void setRating(Double rating) {
        this.rating = rating;
    }



}