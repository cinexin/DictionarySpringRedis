/**
 * 
 */
package org.migue.dictionary.dao;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author migue
 * DAO for Dictionary class
 */

@Repository
public class DictionaryDao {

	private static final String ALL_UNIQUE_WORDS = "all-unique-words";
	
	private StringRedisTemplate redisTemplate;
	
	/* StringRedisTemplate class will allow us to made operations with string data types on the Redis DB...*/
	@Inject
	@Autowired
	public DictionaryDao(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public Long addWordWithItsMeaning(String word, String meaning) {
		Long index = redisTemplate.opsForList().rightPush(word, meaning);
		return index;
	}
	
	
	/* This method return all the meanings for a given word... */
	public List<String> getAllTheMeaningsForAWord(String word) {
		
		List<String> meanings = redisTemplate.opsForList().range(word, 0,-1);
		
		return meanings;
	}
	
	
	/* Method for deleting a word from the dictionary...*/
	public void removeWord(String word) {
		
		redisTemplate.delete(Arrays.asList(word));
	}
	
	/* Method for deleting a bunch of words from the dictionary...*/
	public void removeWords(String...words) {
		redisTemplate.delete(Arrays.asList(words));
	}
	
	
}
