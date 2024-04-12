package org.gameManager.Data.Info;

public class SpawnInfo {
    private Double x;
    private Double y;
    private Double z;

    public SpawnInfo(String x, String y, String z) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);
    }
    public SpawnInfo(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }


}
