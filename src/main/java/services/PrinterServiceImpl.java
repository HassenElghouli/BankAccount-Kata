package services;

public class PrinterServiceImpl implements  PrinterService{
    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
