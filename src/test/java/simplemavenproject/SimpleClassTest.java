package simplemavenproject;

import static org.junit.Assert.*;

import org.junit.Test;
import main.SimpleClass;


public class SimpleClassTest {

	@Test
	public void test() {
		SimpleClass sc = new SimpleClass();
		assertEquals(sc.thisMethodShouldReturn42(), 42);
	}


	@Test
	public void anotherTest() {
		SimpleClass sc = new SimpleClass();
		assertEquals(sc.thisMethodShouldReturn42(), 42);
	}

	@Test
	public void yetAnotherTest() {
		SimpleClass sc = new SimpleClass();
		assertEquals(sc.thisMethodShouldReturn42(), 42);
	}	
	
}

