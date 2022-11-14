package Items;

import Items.Item;

import java.util.Objects;

public class Scientist extends Item {
    private String degree;
    private String fieldsOfStudy;

    public String getDegree() {
        return degree;
    }

    public String getFieldsOfStudy() {
        return fieldsOfStudy;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setFieldsOfStudy(String fieldsOfStudy) {
        this.fieldsOfStudy = fieldsOfStudy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scientist scientist = (Scientist) o;
        return Objects.equals(this.getId(), scientist.getId()) && Objects.equals(degree, scientist.degree)
                && Objects.equals(fieldsOfStudy, scientist.fieldsOfStudy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(degree, fieldsOfStudy);
    }
}
