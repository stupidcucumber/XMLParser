package Items;

import Items.Item;
import Items.Lecturer;
import Items.Practicant;

import java.util.ArrayList;
import java.util.List;

public class StudyClass extends Item {
    private String description;
    private String daysWork;
    private List<Lecturer> lecturers;
    private List<Practicant> practicants;

    public StudyClass(){
        lecturers = new ArrayList<>();
        practicants = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDaysWork(String days){
        daysWork = days;
    }

    public String getDaysWork() {
        return daysWork;
    }

    public String getDescription() {
        return description;
    }


    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setPracticants(List<Practicant> practicants){
        this.practicants = practicants;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public List<Practicant> getPracticants() {
        return practicants;
    }

    public String listOfPracticantsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Practicant practicant : practicants)
            stringBuilder.append(practicant.getName()).append(" ");

        return stringBuilder.toString();
    }

    public String listOfLecturersToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Lecturer lecturer : lecturers)
            stringBuilder.append(lecturer.getName()).append(" ");

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "StudyClass{" +
                "description='" + description + '\'' +
                ", daysWork='" + daysWork + '\'' +
                ", lecturers=" + lecturers +
                ", practicants=" + practicants +
                '}';
    }
}
