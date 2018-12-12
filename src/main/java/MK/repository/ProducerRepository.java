package MK.repository;

import MK.model.Producer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;

public class ProducerRepository extends AbstractGenericRepository<Producer> implements ProducerInterface {

    private List<Producer> importProducerFromFile(String filename) {
        InputStream file;
        file = getClass().getResourceAsStream(filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        List<Producer> producers = new ArrayList<>();
        while (sc.hasNextLine()) {
            String data[] = sc.nextLine().split(";");
            Producer producer = Producer.builder()
                    .name(data[0])
                    .build();
            producers.add(producer);
        }
        return producers;
    }

    public void enterDataProducerToDataBase() {
        List<Producer> producers = importProducerFromFile("/producers.txt");
        System.out.println("list=" + producers);
        for (int i = 0; i < producers.size(); i++) {
            saveOrUpdate(producers.get(i));
        }
    }
}
