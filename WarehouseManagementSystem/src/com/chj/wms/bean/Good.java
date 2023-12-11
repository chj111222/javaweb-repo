package com.chj.wms.bean;

import java.util.Objects;

public class Good {
    private String good_id;
    private String good_name;
    private String source;
    private String dest;
    private String warehousing_date;
public Good(){}
    public Good(String good_id, String good_name, String source, String dest, String warehousing_date) {
        this.good_id = good_id;
        this.good_name = good_name;
        this.source = source;
        this.dest = dest;
        this.warehousing_date = warehousing_date;
    }

    @Override
    public String toString() {
        return "Good{" +
                "good_id='" + good_id + '\'' +
                ", good_name='" + good_name + '\'' +
                ", source='" + source + '\'' +
                ", dest='" + dest + '\'' +
                ", warehousing_date='" + warehousing_date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return Objects.equals(good_id, good.good_id) && Objects.equals(good_name, good.good_name) && Objects.equals(source, good.source) && Objects.equals(dest, good.dest) && Objects.equals(warehousing_date, good.warehousing_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(good_id, good_name, source, dest, warehousing_date);
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getWarehousing_date() {
        return warehousing_date;
    }

    public void setWarehousing_date(String warehousing_date) {
        this.warehousing_date = warehousing_date;
    }
}
