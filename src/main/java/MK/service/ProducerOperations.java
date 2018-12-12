package MK.service;

import MK.model.Producer;
import MK.repository.ProducerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProducerOperations {

    private ProducerRepository producerRepository = new ProducerRepository();

    public List<Producer> getProducerList(){
        return this.producerRepository.findAll();
    }

    public void menuAddProducer(Scanner scanner1){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę");
        String name = scanner.nextLine();
        Producer p = Producer.builder().name(name).build();
        System.out.println(addProducer(p));
        System.out.println("Dodano producenta");
    }

    public Producer addProducer(Producer p){
        if(p == null)
            throw new NullPointerException();
        return this.producerRepository.saveOrUpdate(p);
    }

    public void menuFindProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        Producer p = findProducer(id);
        System.out.println(p);
    }

    public Producer findProducer(Long id) {
        Optional p = this.producerRepository.findOne(id);
        return (Producer) p.get();
    }

    public void menuUpdateProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        Producer p = findProducer(id);
        if(p != null) {
            System.out.println(p);
            Scanner newScanner = new Scanner(System.in);
            System.out.println("Podaj nową nazwę");
            String name = newScanner.nextLine();
            p.setName(name);
            updateProducer(p);
            System.out.println("Zaktualizowano");
        } else {
            System.out.println("Błąd");
        }
    }

    public void updateProducer(Producer p){
        this.producerRepository.update(p);
    }

    public void menuRemoveProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        removeProducer(id);
    }

    public void removeProducer(Long id){
        this.producerRepository.delete(id);
    }

}
