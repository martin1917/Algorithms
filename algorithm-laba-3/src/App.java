import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {
    static final int N = 10000;
	
    public static void main(String[] args) {
        launch(args);
    }

	// возвращает массив с очками для каждой ф-ции хеширования
    public static int[] getWinnerFunc(List<FuncHashWrapper> functions, int iMax) {
        int[] score = new int[]{0,0,0,0,0,0};  //очки для хеш-функций
        for(int i = 0; i < iMax; i++) {
            List<Long> arr = generateList(1000, 0, 65000);
            int[] collisions = new int[]{0,0,0,0,0,0};  //кол-во коллизий для каждой хеш-функции

            for(int j = 0; j < functions.size() + 1; j++) {
                List <Long>[] lists = new List[1000];
                FuncHashWrapper funcWrapper = j == functions.size() ? functions.get((int)(Math.random() * functions.size())) : functions.get(j);

                for(Long item : arr) {
                    int hash = (int) funcWrapper.getFunc().invoke(item);

                    if (lists[hash] == null) {
                        lists[hash] = new ArrayList<>();
                    }
                    lists[hash].add(item);
                }

                int countColissions = 0;
                for (int k = 0; k < 1000; k++) 
                    if (lists[k]!=null)
                        countColissions+=lists[k].size()-1;


                collisions[j] = countColissions;
            }

            int min = collisions[0];
            for (int j = 0; j < collisions.length; j++)
                if(collisions[j] < min)
                    min = collisions[j];

            List<Integer> pos = new ArrayList<Integer>(5);
            for (int j = 0; j < collisions.length; j++)
                if(collisions[j] == min)
                    pos.add(j);

            for(Integer p : pos) {
                score[p] += 1;
            }

        }

        return score;
    }
    
	// возвращет данные для отображения на форме
    public static double [] getInfo(FuncHashWrapper funcWrapper){
        List<Long> arr1 = generateList(N, 0, 10000);
        List<Long> arr2 = generateList(N, 0, 20000);
        double [] all = new double[6];
        double [] foo = openAddr(funcWrapper, arr1, arr2);
        double [] chain = chain(funcWrapper, arr1, arr2);
        for (int i = 0; i < 3; i++) {
            all[i] = foo[i];
            all[i+3] = chain[i];
        }
        return all;
    }
    
	// метод открытой адресации
    public static double [] openAddr(FuncHashWrapper funcWrapper, List<Long> arr1, List<Long> arr2) {
        long[] arrHash = new long[N];
        for(int i = 0; i < arrHash.length; i++)
            arrHash[i] = -1;  // -1 - метка о том, что в этот элемент можно записывать
        
        // заполнение (метод открытой адресации)
        for (int i = 0; i < arr1.size(); i++) {
            int hash = (int)funcWrapper.getFunc().invoke(arr1.get(i));
            boolean find = false;

            for(int j = hash; j < arrHash.length; j++) {  //пробегаемся от найденного индекса до конца
                if(arrHash[j] == -1){
                    find = true;
                    arrHash[j] = arr1.get(i);
                    break;
                }
            }
            if(!find) {									//пробегаемся от начала до найденного индекса
                for(int j = 0; j < hash; j++) {
                    if(arrHash[j] == -1){
                        arrHash[j] = arr1.get(i);
                        break;
                    }
                }
            }
        }

        // поиск (метод открытой адресации)
        int countFind = 0;  //кол-во найденых
        int countNum = 0;   //кол-во сравнений
        long lastTime  = System.currentTimeMillis();
        for (int i = 0; i < arr2.size(); i++) {
            int hash = (int)funcWrapper.getFunc().invoke(arr2.get(i));
            boolean find = false;

            for(int j = hash; j < arrHash.length; j++) {
                countNum++;
                if(arrHash[j] == arr2.get(i)){
                    find = true;
                    countFind++;
                    break;
                }
            }
            if(!find) {
                for(int j = 0; j < hash; j++) {
                    countNum++;
                    if(arrHash[j] == arr2.get(i)){
                        countFind++;
                        break;
                    }
                }
            }
        }
        long time = System.currentTimeMillis()-lastTime;
        return new double[]{time, (double)countNum/arr2.size(), countFind};
    }

	// метод цепочек
    public static double [] chain(FuncHashWrapper funcWrapper, List<Long> arr1, List<Long> arr2){
        List <Long>[] lists = new List[N];
        //заполнение (метод цепочек)
        for (int i = 0; i < N; i++) {
            Long item = arr1.get(i);
            int hash = (int) funcWrapper.getFunc().invoke(item);

            if (lists[hash] == null) {
                lists[hash] = new ArrayList<>();
            }
            lists[hash].add(item);
        }

        // поиск (метод цепочек)
        int countFind = 0;  //кол-во найденых
        int countNum = 0;   //кол-во сравнений
        long lastTime  = System.currentTimeMillis();
        for (int i = 0; i < arr2.size(); i++) {
            int hash = (int)funcWrapper.getFunc().invoke(arr2.get(i));
            countNum++;
            if (lists[hash]!=null){
                List<Long> list = lists[hash];
                for (int i1 = 0; i1 < list.size(); i1++) {
                    Long l = list.get(i1);
                    countNum++;
                    if (l.longValue() == arr2.get(i)) {
                        countFind++;
                        break;
                    }
                }
            }
        }
        long time = System.currentTimeMillis()-lastTime;
        return new double[]{time, (double) countNum/arr2.size(), countFind};
    }
    
	//создание списка размера size, где каждый элемент <@ [min; max]
    public static List<Long> generateList(int size, int min, int max) {
        List<Long> list = new ArrayList<Long>(size);

        for(int i = 0; i < size; i++) {
            list.add((long)(Math.random()*(max-min) + 1 - min));
        }

        return list;
    }

	// возвращает список хеш-функций
    public static List<FuncHashWrapper> getHashFunction() {
        List<FuncHashWrapper> functions = new ArrayList<FuncHashWrapper>(5);

        // метод деления
        FuncHash h0 = (key) -> {
            return key % 997;
        };

        // метод середины квадратов
        FuncHash h1 = (key) -> {
            String num = Long.toString(key * key);
            int value = 1000;  // кол-во записей
            int count = (int)Math.log10(value);  //кол-во необходимых цифр

            int start = Math.max(0, num.length() / 2 - (count / 2));
            int end = Math.min(num.length() / 2 + (count / 2) - (count + 1) % 2, num.length() - 1);
            long res = Long.parseLong(num.substring(start, end+1));

            return res;
        };

        // метод свертывания
        FuncHash h2 = (key) -> {
            String num = Long.toString(key);
            int value = 1000;  //кол-во записей
            int count = (int)Math.log10(value); //кол-во необходимых цифр

            int sum = 0;
            int end = num.length();
            int start;
            while(end > 0) {
                start = Math.max(end - count, 0);
                int token = Integer.parseInt(num.substring(start, end));
                sum += token;
                end -= count;
            }

            return sum % (long)Math.pow(10, count);
        };

        // метод умножения
        FuncHash h3 = (key) -> {
            double A = (Math.sqrt(5) - 1)/ 2;
            int m = 1000;

            long res = (int)(m * ((key*A)%1));

            return res;
        };

        // метод замены СС
        FuncHash h4 = (key) -> {
            int q = 11;
            long res = 0;

            int k = 0;
            while(key > 0) {
                res += key%10 * Math.pow(q, k);
                key /= 10;
                k++;
            }

            return res%1000;
        };

        functions.add(new FuncHashWrapper("метод деления", h0));
        functions.add(new FuncHashWrapper("метод середины квадратов", h1));
        functions.add(new FuncHashWrapper("метод свертывания", h2));
        functions.add(new FuncHashWrapper("метод умножения", h3));
        functions.add(new FuncHashWrapper("метод замены СС", h4));   

        return functions;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
         
        stage.setTitle("Hash");
        stage.sizeToScene();
         
        stage.show();
    }
}

class FuncHashWrapper {
    private String name;
    private FuncHash func;

    public FuncHashWrapper(String name, FuncHash func) {
        this.name = name;
        this.func = func;
    }


    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public FuncHash getFunc() {
        return this.func;
    }
    public void setFunc(FuncHash func) {
        this.func = func;
    }
}

@FunctionalInterface
interface FuncHash {
    long invoke(long key);
}
