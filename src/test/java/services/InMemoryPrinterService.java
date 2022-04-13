package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryPrinterService implements   PrinterService{
    private List<String> printedLines = new ArrayList<>();

    public void print(String text) {
        printedLines.add(text);
    }

    public List<String> getPrintedLines() {
        return Collections.unmodifiableList(printedLines);
    }

}
