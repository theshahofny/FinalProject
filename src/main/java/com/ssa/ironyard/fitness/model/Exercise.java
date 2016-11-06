package com.ssa.ironyard.fitness.model;

public class Exercise implements DomainObject {
    Integer id;
    String exercise_name;
    Category cat;
    String muscles;
    String image;
    String instructions;
    boolean isLoaded = false;

    public Exercise(String excercise_name) {
        this.exercise_name = excercise_name;
    }
    
    public Exercise(Integer id) {
        this.id = id;
    }

    public Exercise() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public Category getCategory() {
        return this.cat;
    }

    public void setCategory(Category category) {
        this.cat = category;
    }

    public String getMuscles() {
        return muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Exercise other = (Exercise) obj;

        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Exercise other = (Exercise) obj;
        if (this.cat != other.cat)
            return false;
        if (exercise_name == null) {
            if (other.exercise_name != null)
                return false;
        } else if (!exercise_name.equals(other.exercise_name))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (instructions == null) {
            if (other.instructions != null)
                return false;
        } else if (!instructions.equals(other.instructions))
            return false;
        if (isLoaded != other.isLoaded)
            return false;
        if (muscles == null) {
            if (other.muscles != null)
                return false;
        } else if (!muscles.equals(other.muscles))
            return false;
        return true;
    }

    @Override
    public Exercise clone() {
        try {
            Exercise e = (Exercise) super.clone();
            return e;
        } catch (Exception e) {

        }
        return null;
    }

    public static enum Category {
        ARMS("Arms", "AR"), BACK("Back", "BA"), CHEST("Chest", "CH"), CARDIO("Cardio", "CA"), NECK("Neck",
                "NE"), SHOULDERS("Shoulders",
                        "SH"), CORE("Core", "CO"), PLYOMETRICS("Plymetrics", "PL"), LEGS("Legs", "LE");

        private final String display, data;

        private Category(String display, String data) {
            this.display = display;
            this.data = data;
        }

        public static Category getInstance(String data) {
            data = data.toUpperCase();
            for (Category category : values()) {
                if (category.data.equals(data))
                    return category;
            }
            return null;
        }

        public String getData() {
            return this.data;
        }
        
        public String getDisplay(){
            return this.display;
        }

        @Override
        public String toString() {
            return this.display;
        }

    }

    @Override
    public String toString() {
        return "Exercise [id=" + id + ", exercise_name=" + exercise_name + ", cat=" + cat + ", muscles=" + muscles
                + ", image=" + image + ", instructions=" + instructions + ", isLoaded=" + isLoaded + "]";
    };

}
