package Parser;

import Items.Scientist;
import Items.StudyClass;

public class Validator {
    private Setting currentSetting;

    public Validator(Setting setting){
        currentSetting = setting;
    }

    public Setting getSetting() {
        return currentSetting;
    }

    public void setSetting(Setting setting) {
        this.currentSetting = setting;
    }


    public boolean validateScientist(Scientist scientist){
        String[] fields = scientist.getFieldsOfStudy().split(",");
        for(int i = 0; i < fields.length; i++){
            fields[i] = fields[i].strip();
            System.out.println(fields[i]);
        }

        boolean isChoosed = false;
        for (String field : fields){
            for(String requiredField : currentSetting.getFieldsOfStudy())
                if(field.equals(requiredField)){
                    isChoosed = true;
                    break;
                }
        }

        boolean isDegree = false;
        for(String degree : currentSetting.getDegree())
            if (degree.equals(scientist.getDegree())) {
                isDegree = true;
                break;
            }

        System.out.println(currentSetting.getFieldsOfStudy().size());
        return (isChoosed || currentSetting.getFieldsOfStudy().size() == 0) && (isDegree || currentSetting.getDegree().size() == 0);
    }
    public boolean validateClass(StudyClass studyClass){
        String[] days = studyClass.getDaysWork().replace(" ", "").split(",");
        boolean isOpen = false;
        for(String requiredDays : currentSetting.getDaysOpen()){
            for (String day : days)
                if (requiredDays.equals(day)) {
                    isOpen = true;
                    break;
                }
        }


        return isOpen || currentSetting.getDaysOpen().size() == 0;
    }
}
