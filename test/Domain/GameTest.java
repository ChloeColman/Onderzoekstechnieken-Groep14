package Domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Player dealer;
    private Player gokker;
    private Player[] players = new Player[2];
    private Game game;
    private Player expectedWinner;
    private Player expectedLoser;
    int win = 1;
    int loss = 0;

    @Before
    public void setUp() {
        this.dealer = new Player(1, true);
        this.gokker = new Player(0, 17, 1);
        this.players[0] = gokker;
        this.players[1] = dealer;
        Shoe shoe = new Shoe();
        Deck deck = new Deck();
        shoe.addDeck(deck);
        this.game = new Game(players, shoe);
    }

    @After
    public void tearDown() {
        game.pickWinner();
        assertEquals(expectedWinner.getWins(), win);
        assertEquals(expectedLoser.getWins(), loss);
    }

    @Test
    public void testDealerWin() {
        dealer.addCard(10);
        dealer.addCard(9);
        gokker.addCard(8);
        gokker.addCard(3);
        dealerWins();
    }

    @Test
    public void testGokkerWin() {
        dealer.addCard(8);
        dealer.addCard(2);
        gokker.addCard(10);
        gokker.addCard(5);
        gokkerWins();
    }

    @Test
    public void testDraw() {
        dealer.addCard(9);
        dealer.addCard(4);
        gokker.addCard(7);
        gokker.addCard(6);
        dealerWins();
    }

    @Test
    public void testBlackjackDraw() {
        dealer.addCard(10);
        dealer.addCard(1);
        gokker.addCard(10);
        gokker.addCard(1);
        dealerWins();
    }

    @Test
    public void testOnlyAces() {
        dealer.addCard(1);
        dealer.addCard(1);
        gokker.addCard(1);
        gokker.addCard(1);
        dealerWins();
    }

    @Test
    public void dealerBlackjack() {
        dealer.addCard(10);
        dealer.addCard(1);
        gokker.addCard(7);
        gokker.addCard(5);
        dealerWins();
    }

    @Test
    public void gokkerBlackjack() {
        dealer.addCard(2);
        dealer.addCard(4);
        gokker.addCard(1);
        gokker.addCard(10);
        gokkerWins();
    }

    @Test
    public void dealerBust() {
        dealer.addCard(10);
        dealer.addCard(5);
        dealer.addCard(7);
        gokker.addCard(3);
        gokker.addCard(9);
        gokkerWins();
    }

    @Test
    public void gokkerBust() {
        dealer.addCard(3);
        dealer.addCard(5);
        gokker.addCard(10);
        gokker.addCard(4);
        gokker.addCard(9);
        dealerWins();
    }

    @Test
    public void everyoneBust() {
        dealer.addCard(10);
        dealer.addCard(10);
        dealer.addCard(5);
        gokker.addCard(10);
        gokker.addCard(10);
        gokker.addCard(5);
        dealerWins();
    }

    public void dealerWins() {
        expectedWinner = dealer;
        expectedLoser = gokker;
    }

    public void gokkerWins() {
        expectedWinner = gokker;
        expectedLoser = dealer;
    }
}
