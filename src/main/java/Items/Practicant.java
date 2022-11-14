package Items;

public class Practicant extends Scientist {
    @Override
    public String toString() {
        return "Practicant{" +
                "name='" + super.getName() + '\'' +
                ", id=" + super.getId() +
                ", degree='" + super.getDegree() + '\'' +
                ", fieldsOfStudy='" + super.getFieldsOfStudy() + '\'' +
                '}';
    }
}
