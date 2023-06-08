import java.io.IOException;

import utils.FileWrapper;

public class App {
    public static void main(String[] args) throws Exception {
        FileWrapper fileData = new FileWrapper("../other//data.txt");
        fileData.open();
        fileData.setCount(fileData.getSize());
        fileData.close();

        naturalSort(fileData);
    }

    public static void naturalSort(FileWrapper fileData) throws IOException {
        FileWrapper fileA = new FileWrapper("../other//a.txt");
        FileWrapper fileB = new FileWrapper("../other//b.txt");
        FileWrapper fileC = new FileWrapper("../other//c.txt");

        fileA.openAndClear();
        fileB.openAndClear();
        fileC.openAndClear();

        fileData.open();
        fileA.copyFrom(fileData);
        fileData.close();

        do {
            naturalSortSplit(fileA, fileB, fileC);

        } while (naturalSortCombine(fileB, fileC, fileA));
    }

    public static void naturalSortSplit(FileWrapper fileA, FileWrapper fileB, FileWrapper fileC) throws IOException {
        boolean dir = true;
        int last = -1;

        fileB.clear();
        fileC.clear();
        
        for(int i = 0; i < fileA.getCount(); i++) {
            int num = fileA.getNumberByPosition(i);
            if (num < last) {
                if (dir) {
                    fileB.writeNumber(-2);
                    fileC.writeNumber(num);
                } else {
                    fileB.writeNumber(num);
                    fileC.writeNumber(-2);
                }
                dir = !dir;

            } else {
                if (dir) {
                    fileB.writeNumber(num);
                } else {
                    fileC.writeNumber(num);
                }
            }

            last = num;
        }

        if (dir) {
            fileB.writeNumber(-2);
        } else {
            fileC.writeNumber(-2);
        }
    }

    public static boolean naturalSortCombine(FileWrapper fileB, FileWrapper fileC, FileWrapper fileA) throws IOException {
        int ib = 0;
        int ic = 0;

        int countChain = 0;
        fileA.clear();

        int first = -100;
        int second = -100;
        while(ib < fileB.getCount() && ic < fileC.getCount()) {
            first = fileB.getNumberByPosition(ib);
            second = fileC.getNumberByPosition(ic);
        
            while(first != -2 && second != -2) {
                if (first < second) {
                    fileA.writeNumber(first);
                    ib++;
                    first = fileB.getNumberByPosition(ib);
                } else {
                    fileA.writeNumber(second);
                    ic++;
                    second = fileC.getNumberByPosition(ic);
                }
            }
        
            while (first != -2) {
                fileA.writeNumber(first);
                ib++;
                first = fileB.getNumberByPosition(ib);
            }
            
            while (second != -2) {
                fileA.writeNumber(second);
                ic++;
                second = fileC.getNumberByPosition(ic);
            }

            countChain++;
        
            //перейти к следующим числам после -2
            ib++;
            ic++;
        }

        while (ib < fileB.getCount() - 1) {
            first = fileB.getNumberByPosition(ib);
            fileA.writeNumber(first);
            ib++;
        }
        
        while (ic < fileC.getCount() - 1) {
            second = fileC.getNumberByPosition(ic);
            fileA.writeNumber(second);
            ic++;
        }

        return countChain > 1;
    }
}
