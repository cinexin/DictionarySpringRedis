/**
 * 
 */
package org.migue.test;




import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.migue.LocalRedisConfig;
import org.migue.dictionary.dao.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author migue
 *
 * Test class for "DictionaryDao"
 */

@ContextConfiguration(classes={LocalRedisConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryDaoTest {

	
	
	@After
	public void tearDown()  {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}
	
	@Inject
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Inject
	private StringRedisTemplate redisTemplate;
	
	@Test
	public void testAddWordWithItsMeaningToDictionary() {
		
		
		Long index = dictionaryDao.addWordWithItsMeaning("lollop", "To move forward with a bounding, drooping motion.");		
		assertNotNull (index);
		assertEquals(1L,index.longValue() );
		
		index = dictionaryDao.addWordWithItsMeaning("lollop", "To hang loosely;droop;dangle.");
		assertNotNull (index);
		assertEquals(2L,index.longValue() );
		
	}

	@Test
	public void testAddAnotherWordWithItsMeaningToDictionary() {
		
		
		Long index = dictionaryDao.addWordWithItsMeaning("fain", "nicely; in a good manner");		
		assertNotNull (index);
		assertEquals(1L,index.longValue() );
	
	}

	/* Dummy method for setting up a word for using it in test cases...*/
	private void setupOneWord() {
		
		testAddWordWithItsMeaningToDictionary();
		
	}
	
	
	/* Dummy method for setting up 2 words for using it in test cases...*/
	private void setupTwoWords() {
		setupOneWord();
		testAddAnotherWordWithItsMeaningToDictionary();
	}
	
	/* method for testing the method getAllTheMeaningsForAWord()...*/
	@SuppressWarnings("deprecation") // hasItems() method is deprecated at the time i'm writing this
	@Test
	public void shouldGetAllTheMeaningForAWord() {
		
		setupOneWord();
		List<String> allMeanings = dictionaryDao.getAllTheMeaningsForAWord("lollop");
		assertEquals(allMeanings.size(), 2);
		assertThat(allMeanings, hasItems("To move forward with a bounding, drooping motion.", "To hang loosely;droop;dangle."));
		tearDown();
	}
	
	
	/* method for testing the deletion of a word from a dictionary...*/
	@Test 
	public void shouldDeleteAWordFromDictionary() {
		
		setupOneWord();
		dictionaryDao.removeWord("lollop");
		List<String> allMeanings = dictionaryDao.getAllTheMeaningsForAWord("lollop");
		assertEquals(allMeanings.size(), 0);
		tearDown();
	}
	
	/* method for testing the deletion of multiple words from a dictionary...*/
	@Test
	public void shouldDeleteMultipleWordsFromDictionary() {
		
		setupTwoWords();
		dictionaryDao.removeWords("fain","lollop");
		List<String> allMeaningsForLollop = dictionaryDao.getAllTheMeaningsForAWord("lollop");
		List <String> allMeaningsForFain = dictionaryDao.getAllTheMeaningsForAWord("fain");
		assertEquals(allMeaningsForLollop.size(), 0);
		assertEquals(allMeaningsForFain.size(), 0);
		tearDown();

	}
	
}
