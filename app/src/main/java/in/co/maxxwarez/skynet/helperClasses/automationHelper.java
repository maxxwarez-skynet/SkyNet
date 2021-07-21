package in.co.maxxwarez.skynet.helperClasses;

import android.text.Editable;

import java.util.List;

public class automationHelper {

    public String device;
    public String node;
    public int operator;
    public Float rangeA;
    public long rangeB;
    public boolean state;
    public String source;

    public automationHelper () {

    }

    public automationHelper (String device, String node, int operator, Float rangeA, boolean state, String source) {
        this.device = device;
        this.node = node;
        this.operator = operator;
        this.rangeA = rangeA;
        this.state = state;
        this.source = source;
    }


    public automationHelper (String device, String node, int operator, Float rangeA, int rangeB, boolean state, String source) {
        this.device = device;
        this.node = node;
        this.operator = operator;
        this.rangeA = rangeA;
        this.rangeB = rangeB;
        this.state = state;
        this.source = source;
    }

    public void setDevice (String device) {
        this.device = device;
    }

    public void setNode (String node) {
        this.node = node;
    }

    public void setOperator (int operator) {
        this.operator = operator;
    }

    public void setRangeA (Float rangeA) {
        this.rangeA = rangeA;
    }

    public void setRangeB (long rangeB) {
        this.rangeB = rangeB;
    }

    public void setState (boolean state) {
        this.state = state;
    }

    public void setSource (String source) {
        this.source = source;
    }
}

