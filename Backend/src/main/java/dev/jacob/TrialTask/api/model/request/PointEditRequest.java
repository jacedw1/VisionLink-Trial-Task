package dev.jacob.TrialTask.api.model.request;

public class PointEditRequest {

    private String name;
    private Integer x;
    private Integer y;

    public PointEditRequest(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public PointEditRequest() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

}
