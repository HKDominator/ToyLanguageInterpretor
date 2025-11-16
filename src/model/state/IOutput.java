package model.state;

import java.util.List;

public interface IOutput {
    String getOutput();
    void appendToOutput(String output);
    void setOutput(String output);
    List<String> getOutputAsList();
}
