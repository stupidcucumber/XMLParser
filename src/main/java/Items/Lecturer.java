package Items;

public class Lecturer extends Scientist {

    @Override
    public String toString() {
        return "Lecturer{" +
                "name='" + super.getName() + '\'' +
                ", id=" + super.getId() +
                ", degree='" + super.getDegree() + '\'' +
                ", fieldsOfStudy='" + super.getFieldsOfStudy() + '\'' +
                '}';
    }
}
