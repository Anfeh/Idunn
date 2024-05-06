package com.example.idunn.Datos;

import java.io.Serializable;

public class Measurement  {
    private int arm_size, calf_size, chest_size, forearm_size, height, weight, leg_size, waist;

    public Measurement(int arm_size, int calf_size, int chest_size, int forearm_size, int height, int weight, int leg_size, int waist) {
        this.arm_size = arm_size;
        this.calf_size = calf_size;
        this.chest_size = chest_size;
        this.forearm_size = forearm_size;
        this.height = height;
        this.weight = weight;
        this.leg_size = leg_size;
        this.waist = waist;
    }
    public Measurement(){}
    public int getArm_size() {
        return arm_size;
    }

    public void setArm_size(int arm_size) {
        this.arm_size = arm_size;
    }

    public int getCalf_size() {
        return calf_size;
    }

    public void setCalf_size(int calf_size) {
        this.calf_size = calf_size;
    }

    public int getChest_size() {
        return chest_size;
    }

    public void setChest_size(int chest_size) {
        this.chest_size = chest_size;
    }

    public int getForearm_size() {
        return forearm_size;
    }

    public void setForearm_size(int forearm_size) {
        this.forearm_size = forearm_size;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLeg_size() {
        return leg_size;
    }

    public void setLeg_size(int leg_size) {
        this.leg_size = leg_size;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }
}
