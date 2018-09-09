package com.school.base;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

//@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@ContextConfiguration(locations = { "classpath*:/applicationContext-test.xml",
		"classpath*:/cache/applicationContext-cache.xml" })
@TransactionConfiguration(defaultRollback = false)
public class BaseAbstractTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void testName() throws Exception {
		System.out.println("--------init---------");
	}
}
