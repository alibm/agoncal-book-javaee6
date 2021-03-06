package org.agoncal.book.javaee6.chapter03.ex53;

import org.agoncal.book.javaee6.chapter03.AbstractPersistentTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 6 with Glassfish 3
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 *         Test class that creates a Customer18
 */
public class News53Test extends AbstractPersistentTest {

    // ======================================
    // =              Unit tests            =
    // ======================================
    @Test
    public void shouldCreateOneNewsWithFourComments() throws Exception {

        News53 news = new News53("Death of Michael Jackson");
        news.addComment(new Comment53("gonzo", "Third comment", 1, "2009-07-03"));
        news.addComment(new Comment53("elvis", "First comment", 3, "2009-07-01"));
        news.addComment(new Comment53("gonzo", "Second comment", 5, "2009-07-02"));
        news.addComment(new Comment53("elvis", "Fourth comment", 2, "2009-07-06"));

        tx.begin();
        em.persist(news);
        tx.commit();

        tx.begin();
        news = em.find(News53.class, news.getId());

        // Without the refresh the test will not work
        // The OrderBy annotation specifies the ordering of the elements of a collection valued association at the point when the association is retrieved."
        // The key here is the phrase "when the association is retrieved".  In this case, when your find is executed, the association is still managed and no retrieval logic occurs.
        em.refresh(news);

        tx.commit();

        assertEquals("Death of Michael Jackson", news.getContent());
        assertEquals(4, news.getComments().size());
        assertEquals("Fourth comment", news.getComments().get(0).getContent());
        assertEquals("Third comment", news.getComments().get(1).getContent());
        assertEquals("Second comment", news.getComments().get(2).getContent());
        assertEquals("First comment", news.getComments().get(3).getContent());
    }
}