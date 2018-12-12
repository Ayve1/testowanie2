package MK;

import MK.model.Producer;
import MK.service.Operations;
import MK.service.ProducerOperations;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertEquals;

public class ProducerTest {
    @Test
    public void addGetProducerTest() {
        ProducerOperations o = new ProducerOperations();

        Producer testProducer = Producer.builder().name("name").build();

        o.addProducer(testProducer);

        o.findProducer(testProducer.getId());

        assertEquals(testProducer.getName(), "name");
    }

    @Test(expected = NullPointerException.class)
    public void addNullProducerTest() {
        ProducerOperations o = new ProducerOperations();

        Producer testProducer = null;

        o.addProducer(testProducer);
    }

    @Test(expected = NoSuchElementException.class)
    public void findWrongIdProducerTest() {
        ProducerOperations o = new ProducerOperations();

        o.findProducer(0L);
    }

    @Test
    public void updateProducerTest(){
        ProducerOperations o = new ProducerOperations();

        Producer testProducer = Producer.builder().name("name").build();

        o.addProducer(testProducer);

        Long id = testProducer.getId();

        Producer beforeUpdateProducer = o.findProducer(id);
        assertEquals(beforeUpdateProducer.getName(), "name");

        testProducer.setName("newName");

        o.updateProducer(testProducer);
        Producer afterUpdateProducer = o.findProducer(id);
        assertEquals(afterUpdateProducer.getName(), "newName");
    }

    @Test(expected = NoSuchElementException.class)
    public void removeProducerTest(){
        ProducerOperations o = new ProducerOperations();

        Producer testProducer = Producer.builder().name("name").build();

        o.addProducer(testProducer);

        Long id = testProducer.getId();

        Producer beforeRemoveProducer = o.findProducer(id);
        assertEquals(beforeRemoveProducer.getName(), "name");

        o.removeProducer(id);

        o.findProducer(id);
    }
}
